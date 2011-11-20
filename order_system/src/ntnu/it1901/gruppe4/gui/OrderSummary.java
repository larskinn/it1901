package ntnu.it1901.gruppe4.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import ntnu.it1901.gruppe4.db.Address;
import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.db.OrderItem;
import ntnu.it1901.gruppe4.db.OrderMaker;
import ntnu.it1901.gruppe4.gui.ordergui.OperatorOrderSummary;

/**
 * This class contains a basic set of fields and methods for viewing
 * {@link ntnu.it1901.gruppe4.db.Order Orders}.
 * 
 * @author Leo
 */
public class OrderSummary extends JPanel {
	protected OrderMaker currentOrder;
	protected JLabel pricePrefix, price;
	private JLabel customerInfo, status;
	private JButton receiptButton;
	private JCheckBox pickupCheckbox;
	protected Customer customer;
	protected Mode mode;

	// Internal panels used for component grouping
	protected JPanel centerPanel;
	protected JPanel southPanel;
	protected JPanel buttonPanel;

	/**
	 * Creates a new {@link OrderSummary} for viewing details about
	 * {@link ntnu.it1901.gruppe4.db.Order Orders}.
	 */
	public OrderSummary(Mode mode) {
		this.mode = mode;

		pricePrefix = new JLabel("<html> <table>"
				+ "<tr> <td> Brutto: </td> </tr>"
				+ "<tr> <td> Frakt: </td> </tr>" + "<tr> <td> MVA: </td> </tr>"
				+ "<tr> <td> Totalpris: </td> </tr>" + "</table> </html>");
		price = new JLabel();
		customerInfo = new JLabel();
		status = new JLabel();
		receiptButton = new JButton("Vis kvittering");
		pickupCheckbox = new JCheckBox("Hentes av kunden (F4)");
		currentOrder = new OrderMaker();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		buttonPanel = new JPanel();

		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		southPanel.setLayout(new GridBagLayout());
		buttonPanel.setLayout(new GridBagLayout());

		pricePrefix.setFont(Layout.summaryTextFont);
		price.setFont(Layout.summaryTextFont);
		customerInfo.setFont(Layout.summaryTextFont);
		status.setFont(Layout.summaryTextFont);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = gbc.weighty = 1;
		southPanel.add(pricePrefix, gbc);

		gbc.anchor = GridBagConstraints.NORTHEAST;
		southPanel.add(price, gbc);

		// Add space between price and receipt button
		gbc.gridy++;
		southPanel.add(Box.createVerticalStrut(7), gbc);

		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy++;
		southPanel.add(receiptButton, gbc);

		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridy++;
		southPanel.add(customerInfo, gbc);

		if (mode == Mode.ORDER) {
			gbc.gridy++;
			southPanel.add(pickupCheckbox, gbc);

			gbc.gridy++;
			southPanel.add(Box.createVerticalStrut(20), gbc);
		}

		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridy++;
		southPanel.add(status, gbc);

		if (mode != Mode.ORDER) {
			// Add some space between status label and buttons
			gbc.gridy++;
			southPanel.add(Box.createVerticalStrut(12), gbc);
		}

		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridy++;
		southPanel.add(buttonPanel, gbc);

		setBorder(Layout.panelPadding);
		setLayout(new BorderLayout());

		JScrollPane sp = new JScrollPane(centerPanel);
		sp.setBorder(null);
		add(sp, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);

		assignCustomer(null);

		receiptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				receiptButton.setEnabled(false);
				Receipt receipt = new Receipt(OrderSummary.this);

				receipt.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						receiptButton.setEnabled(true);
					}
				});
			}
		});

		pickupCheckbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				setSelfPickup(e.getStateChange() == ItemEvent.SELECTED);
			}
		});
	}

	/**
	 * Reloads the current order shown in {@link OrderSummary} from the SQL
	 * database and updates the total price.
	 * <p>
	 * There is usually no need to call on this method explicitly, as all
	 * methods modifying the <code>OrderSummary</code> are required to call this
	 * method before returning.
	 */
	public void update() {
		centerPanel.removeAll();

		// Add order items to the panel
		drawOrderItems();

		// Update the total price on the panel
		price.setText("<html> <table>"
				+ "<tr> <td align='right'> <b>"
				+ Layout.decimalFormat.format(currentOrder.getOrder()
						.getGrossAmount())
				+ "&nbsp;kr </b> </td> </tr>"
				+ "<tr> <td align='right'> <b>"
				+ Layout.decimalFormat.format(currentOrder.getOrder()
						.getDeliveryFee())
				+ "&nbsp;kr </b> </td> </tr>"
				+ "<tr> <td align='right'> <b>"
				+ Layout.decimalFormat.format(currentOrder.getOrder()
						.getTaxAmount())
				+ "&nbsp;kr </b> </td> </tr>"
				+ "<tr> <td align='right'> <b>"
				+ Layout.decimalFormat.format(currentOrder.getOrder()
						.getTotalAmount()) + "&nbsp;kr </b> </td> </tr>"
				+ "</table> </html>");

		// Add customer information to the panel
		if (customer == null) {
			if (currentOrder.getOrder().getSelfPickup()) {
				customerInfo.setText("<html> <br> Ordren hentes av kunden."
						+ "<br> <br> <br> <br> <br> </html>");
			} else {
				customerInfo
						.setText("<html> <br> Ordren er ikke knyttet til noen kunde."
								+ "<br> <br> <br> <br> <br> </html>");
			}
		} else {
			// The system does not currently support more than one address per
			// customer.
			Address address = DataAPI.getAddresses(customer).get(0);

			customerInfo.setText("<html> <br> <table>"
					+ "<tr> <td> Navn:</td> <td> <b>" + customer.getName()
					+ "</td> </tr>" + "<tr> <td> Telefon:</td> <td> <b>"
					+ customer.getPhone() + "</b> </td> </tr>"
					+ "<tr> <td> Adresse:</td> <td> <b>"
					+ address.getAddressLine() + "</b> </td> </tr>"
					+ "<tr> <td> Postnummer:</td> <td> <b>"
					+ address.getPostalCode() + "</b> </td> </tr>"
					+ "</table> </html>");
		}

		// Add the current order status
		status.setText("<html> <br> Status: <b>"
				+ currentOrder.getOrder().getStateName() + "</b> </html>");

		// Update the state of the pickup checkbox
		pickupCheckbox.setSelected(currentOrder.getOrder().getSelfPickup());

		centerPanel.revalidate();
		centerPanel.repaint();
	}

	/**
	 * Adds all {@link OrderItem OrderItems} to the center panel. Override this
	 * method to add some listener to each individual <code>OrderItem</code>.
	 */
	protected void drawOrderItems() {
		int counter = 0;
		List<OrderItem> currentItems = currentOrder.getItemList();

		for (final OrderItem i : currentItems) {
			OrderSummaryItem item = new OrderSummaryItem(i, mode);

			// item.addListener() goes here

			if (counter++ % 2 == 0) {
				item.setBackground(Layout.summaryBgColor1);
			} else {
				item.setBackground(Layout.summaryBgColor2);
			}
			centerPanel.add(item);
		}
	}

	/**
	 * Assigns a {@link Customer} to the {@link OrderSummary}.
	 * 
	 * @param customer
	 *            The <code>Customer</code> to assign to the <code>Order</code>,
	 *            or <code>null</code> to unassign the currently assigned
	 *            <code>Customer</code>.
	 * @return True if the specified <code>Customer</code> was successfully
	 *         assigned / unassigned.
	 */
	protected boolean assignCustomer(Customer customer) {
		if (!currentOrder.canBeChanged()) {
			return false;
		}
		this.customer = customer;

		if (customer == null) {
			if (currentOrder.getOrder().getState() != Order.NOT_SAVED) {
				return false;
			}
			currentOrder.setAddress(null);
		} else {
			currentOrder.setAddress(DataAPI.getAddresses(customer).get(0));
		}
		update();
		return true;
	}

	/**
	 * Marks the contained {@link Order} for pickup.
	 * 
	 * @param selfPickup
	 *            True if the <Order> should be picked up by the
	 *            <code>Customer</code>.
	 */
	protected void setSelfPickup(boolean selfPickup) {
		currentOrder.setSelfPickup(selfPickup);
		update();
	}

	/**
	 * Returns the {@link Customer} assigned to the currently displayed
	 * {@link Order}.
	 * 
	 * @return The {@link Customer} shown in this {@link OrderSummary} or
	 *         <code>null</code> if no <code>Customer</code> is shown.
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Returns the number of items in this {@link OrderSummary}.
	 * 
	 * @return The number of items in this {@link OrderSummary}.
	 */
	public int getItemCount() {
		return currentOrder.getItemCount();
	}

	/**
	 * Returns an unmodifiable list containing all {@link OrderItem} in this
	 * {@link OrderSummary}.<br>
	 * 
	 * @return An unmodifiable list containing the {@link OrderItem}.
	 */
	public List<OrderItem> getItemList() {
		return currentOrder.getItemList();
	}

	/**
	 * Changes the currently displayed {@link Order} to an already existing one,
	 * which when saved will replace the old one.
	 * <p>
	 * Warning: The currently displayed <code>Order</code> will be lost unless
	 * saved.
	 * 
	 * @param order
	 *            The already existing <code>Order</code> to view in the
	 *            <code>OrderSummary</code>.
	 */
	public void setOrder(Order order) {
		if (order == null) {
			currentOrder = new OrderMaker();
			assignCustomer(null);
		} else {
			currentOrder = new OrderMaker(order);
			assignCustomer(DataAPI.getCustomer(DataAPI.getAddress(order)));
		}
		update();
	}

	public Order getOrder() {
		return currentOrder.getOrder();
	}

	/**
	 * Toggle the self delivery checkbox
	 */
	public void toggleSelfDelivery() {
		if (pickupCheckbox.isVisible()) {
			pickupCheckbox.doClick();
		}
	}
}