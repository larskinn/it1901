package ntnu.it1901.gruppe4.gui.ordergui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;

import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.db.Dish;
import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.db.OrderItem;
import ntnu.it1901.gruppe4.db.OrderMaker;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.Mode;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.OrderSummary;
import ntnu.it1901.gruppe4.gui.OrderSummaryItem;

/**
 * This class contains every detail about the {@link ntnu.it1901.gruppe4.db.Order Order}
 * currently being processed by the operator of the {@link OrderWindow}, as well as methods to edit this <code>Order</code>.
 * 
 * @author Leo
 */
public class OperatorOrderSummary extends OrderSummary {
	private OrderSummaryItem itemBeingEdited = null;
	private JButton saveButton, paidButton;
	private JLabel errorMessage;
	private OrderHistoryPanel orderPanel;

	/**
	 * Creates a new {@link OperatorOrderSummary} for viewing and editing {@link ntnu.it1901.gruppe4.db.Order Orders}.
	 */
	OperatorOrderSummary() {
		super(Mode.ORDER);

		saveButton = new JButton("Lagre");
		paidButton = new JButton("Hentet og betalt");
		errorMessage = new JLabel("Ordren er ikke ferdig utfylt");

		saveButton.setFont(Layout.summaryTextFont);
		paidButton.setFont(Layout.summaryTextFont);
		paidButton.setVisible(false);
		errorMessage.setForeground(Layout.errorColor);
		errorMessage.setFont(Layout.errorFont);
		errorMessage.setVisible(false);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = gbc.gridx = 0;
		gbc.weightx = gbc.weighty = 1;
		buttonPanel.add(saveButton, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		buttonPanel.add(paidButton, gbc);

		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy++;
		buttonPanel.add(errorMessage, gbc);

		/*When the south panel (ie. the customer information) is clicked,
		  the customer is removed from the order */
		southPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCustomer(null);
			}
		});

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveOrder();
			}
		});
		
		paidButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentOrder.setState(Order.DELIVERED_AND_PAID);
				currentOrder.save();
				setOrder(null);
				update();
				orderPanel.refresh();
			}
		});
		
		//Force the button panel to always stretch out
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				buttonPanel.setPreferredSize(new Dimension(getPreferredSize().width - 10,
						buttonPanel.getPreferredSize().height));
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update() {
		super.update();

		if (errorMessage != null) {
			errorMessage.setVisible(false);
		}
		
		if (paidButton != null) {
			if (currentOrder.getOrder().getSelfPickup() 
					&& currentOrder.getOrder().getState() == Order.READY_FOR_DELIVERY) {
				paidButton.setVisible(true);
			}
			else {
				paidButton.setVisible(false);
			}
		}
	}

	/**
	 * Adds all {@link OrderItem OrderItems} to the center panel.
	 * Overrided and rewritten to add a {@link MouseAdapter} to each individual <code>OrderItem</code>.
	 */
	@Override
	protected void drawOrderItems() {
		int counter = 0;
		List<OrderItem> currentItems = currentOrder.getItemList();

		for (final OrderItem i : currentItems) {
			final OrderSummaryItem item = new OrderSummaryItem(i, Mode.ORDER);

			//Activated when the item panel is pressed
			item.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (itemBeingEdited != null) {
						if (itemBeingEdited == item) {
							return;
						}
						itemBeingEdited.changeFunction(false);
					}
					item.changeFunction(true);
					itemBeingEdited = item;
				}
			});

			item.addDeleteButtonListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					removeItem(i);
				}
			});

			if (counter++ % 2 == 0) {
				item.setBackground(Layout.summaryBgColor1);
			}
			else {
				item.setBackground(Layout.summaryBgColor2);
			}
			centerPanel.add(item);
		}
	}

	/**
	 * If {@link OrderMaker#isValid()} returns true, all data in the {@link ChefOrderSummary}
	 * is saved to the SQL database and the <code>ChefOrderSummary</code> is emptied.<br><br>
	 * 
	 * If not, an error message is put above the save button and the method does nothing.
	 * 
	 * @return True if the order was successfully saved, false if it was not.
	 */
	public boolean saveOrder() {
		if (currentOrder.isValid()) {
			currentOrder.save();
			currentOrder = new OrderMaker();
			update();
			setCustomer(null);

			if (orderPanel != null) {
				orderPanel.refresh();
			}
			return true;
		}
		else {
			errorMessage.setVisible(true);
			return false;
		}
	}

	/**
	 * Adds a new dish as an {@link OrderItem} to the {@link ChefOrderSummary} and recalculates the total price.
	 * 
	 * @param dish The dish to add.
	 * @return The new {@link OrderItem} that contains the name, price and the dish.
	 */
	public OrderItem addItem(Dish dish) {
		OrderItem item = currentOrder.addItem(dish);
		update();
		return item;
	}

	/**
	 * Assigns a {@link Customer} to the {@link ChefOrderSummary}.
	 * 
	 * @param customer The <code>Customer</code> to assign to the <code>Order</code>, 
	 * or <code>null</code> to unassign the currently assigned <code>Customer</code>.
	 */
	public void setCustomer(Customer customer) {
		super.assignCustomer(customer);
	}

	/**
	 * Removes an {@link OrderItem} from the {@link ChefOrderSummary} and recalculates the total price.
	 * 
	 * @param item The {@link OrderItem} to remove.
	 */
	public void removeItem(OrderItem item) {
		currentOrder.remItem(item);
		update();
	}

	/**
	 * Adds an {@link OrderHistoryPanel} to the {@link OperatorOrderSummary} that
	 * will be refreshed when an {@link Order} is saved.
	 * 
	 * @param orderPanel The <code>OrderHistoryPanel</code> that will be updated.
	 */
	public void setOrderHistoryPanel(OrderHistoryPanel orderPanel) {
		this.orderPanel = orderPanel;
	}
}