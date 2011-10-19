package ntnu.it1901.gruppe4.chefgui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ntnu.it1901.gruppe4.db.Address;
import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Dish;
import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.db.OrderItem;
import ntnu.it1901.gruppe4.db.OrderMaker;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.OrderList;
import ntnu.it1901.gruppe4.gui.OrderListItem;
import ntnu.it1901.gruppe4.gui.OrderListener;

/**
 * This class contains every detail about the {@link ntnu.it1901.gruppe4.db.Order Order}
 * currently being processed by the operator of the {@link OrderWindow}, as well as methods to edit this <code>Order</code>.
 * 
 * @author Leo
 */
public class OrderSummary extends JPanel implements OrderList {
	private OrderMaker currentOrder;
	private JLabel totalPrice;
	private JButton deliverButton;
	private JLabel errorMessage;
	private Customer customer;
	
	private OrderHistoryPanel orderHistoryPanel;
	
	//Internal panels used for component grouping
	private JPanel centerPanel;
	private JPanel southPanel;
	
	/**
	 * Creates a new {@link OrderSummary}. Only classes in the same package are allowed to do this.
	 */
	OrderSummary() {
		totalPrice = new JLabel("<html><br>Totalpris: 0.00 kr<br><br></html>");
		deliverButton = new JButton("Klar til levering");
		errorMessage = new JLabel("Ordren er ikke ferdig utfylt");
		currentOrder = new OrderMaker();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		orderHistoryPanel = null;

		totalPrice.setFont(Layout.summaryTextFont);
		deliverButton.setFont(Layout.summaryTextFont);
		errorMessage.setForeground(Layout.errorColor);
		errorMessage.setFont(Layout.errorFont);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		
		setBorder(Layout.panelPadding);
		setLayout(new BorderLayout());
		JScrollPane sp = new JScrollPane(centerPanel);
		sp.setBorder(null);
		add(sp, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
		//Fired when the south panel (ie. the customer information) is clicked
		southPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCustomer(null);
			}
		});
		
		//Fired when saveButton is clicked
		deliverButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deliverOrder();
			}
		});
		//saveButton size is currently 67, 26 - and it cannot be resized for some reason
		//Update: The button can now be resized with Layout.summaryTextFont
		
		setCustomer(null);
		update();
	}
	
	/**
	 * Reloads the current order shown in {@link OrderSummary} from the SQL database and updates the total price.<br>
	 * There is usually no need to call on this method explicitly, 
	 * as all methods modifying the <code>OrderSummary</code> are required to call this method automatically before returning.
	 */
	public void update() {
		int counter = 0;
		centerPanel.removeAll();
		List<OrderItem> currentItems = currentOrder.getItemList();
		
		for (final OrderItem i : currentItems) {
			OrderListItem item = new OrderListItem(i);
			
			if (counter++ % 2 == 0) {
				item.setBackground(Layout.summaryBgColor1);
			}
			else {
				item.setBackground(Layout.summaryBgColor2);
			}
			centerPanel.add(item);
		}
		
		//Update the total price
		totalPrice = new JLabel("<html><br>Totalpris: " + 
									Layout.decimalFormat.format(currentOrder.getOrder().getTotalAmount()) + 
									" kr<br><br><b>Status: "+currentOrder.getOrder().getStateName()+"</b><br></html>");
		totalPrice.setFont(Layout.summaryTextFont);
		updateSouthPanel();

		centerPanel.revalidate();
		centerPanel.repaint();
	}
	
	/**
	 * Updates the southern panel containing all customer information.
	 */
	private void updateSouthPanel() {
		JLabel text = new JLabel();
		text.setFont(Layout.summaryTextFont);
		
		southPanel.removeAll();
		southPanel.add(totalPrice);

		if (customer == null) {
			text.setText("");
		}
		else {
			//The system does not currently support more than one address per customer.
			Address address = DataAPI.getAddresses(customer).get(0);
			
			text.setText("<html> <table>" +
					"<tr> <td> Navn:</td> <td>" + customer.getName() + "</td> </tr>" +
					"<tr> <td> Telefon:</td> <td>" + customer.getPhone() + "</td> </tr>" +
					"<tr> <td> Adresse:</td> <td>" + address.getAddressLine() + "</td> </tr>" +
					"<tr> <td> Postnummer:</td> <td>" + address.getPostalCode() + "</td> </tr>" +
					"</table> </html>");
		}
		southPanel.add(text);
		southPanel.add(deliverButton);
		southPanel.revalidate();
		southPanel.repaint();
	}
	
	/**
	 * If {@link OrderMaker#isValid()} returns true, all data in the {@link OrderSummary}
	 * is saved to the SQL database and the <code>OrderSummary</code> is emptied.<br><br>
	 * 
	 * If not, an error message is put above the save button and the method does nothing.
	 * 
	 * @return True if the order was successfully saved, false if it was not.
	 */
	public boolean deliverOrder() {
		if (currentOrder.isValid()) {
			currentOrder.setState(Order.READY_FOR_DELIVERY);
			currentOrder.save();
			currentOrder = new OrderMaker();
			update();
			setCustomer(null);
			
			orderHistoryPanel.refresh();
			return true;
		}
		southPanel.remove(errorMessage);
		southPanel.remove(deliverButton);
		southPanel.add(errorMessage);
		southPanel.add(deliverButton);
		
		southPanel.revalidate();
		southPanel.repaint();
		return false;
	}
	
	/**
	 * Assigns a {@link Customer} to the {@link OrderSummary}.
	 * 
	 * @param customer The <code>Customer</code> to assign to the <code>Order</code>, 
	 * or <code>null</code> to unassign the currently assigned <code>Customer</code>.
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
		
		if (customer == null) {
			currentOrder.setAddress(null);
		}
		else {
			currentOrder.setAddress(DataAPI.getAddresses(customer).get(0));
		}
		updateSouthPanel();
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
	 * Returns an unmodifiable list containing all {@link OrderItem} in this {@link OrderSummary}.<br>
	 * If you wish to modify the list, use {@link #addItem}, {@link #removeItem} and {@link #updateItem} (NYI).
	 * 
	 * @return An unmodifiable list containing the {@link OrderItem}.
	 */
	public List<OrderItem> getItemList() {
		return currentOrder.getItemList();
	}
	
	/**
	 * Changes the currently displayed {@link Order} to an already existing one,
	 * which when saved will replace the old one. <br><br>
	 * 
	 * Warning: The currently displayed <code>Order</code> will be lost unless saved.
	 * 
	 * @param order The already existing <code>Order</code> to view in the <code>OrderSummary</code>.
	 */
	@Override
	public void setOrder(Order order) {
		currentOrder = new OrderMaker(order);
		setCustomer(DataAPI.getCustomer(DataAPI.getAddress(order)));
		update();
	}

	public void setOrderHistoryPanel(OrderHistoryPanel orderHistoryPanel) {
		this.orderHistoryPanel = orderHistoryPanel;
	}
}