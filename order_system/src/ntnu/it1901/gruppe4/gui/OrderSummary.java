package ntnu.it1901.gruppe4.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ntnu.it1901.gruppe4.db.Address;
import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.db.OrderItem;
import ntnu.it1901.gruppe4.db.OrderMaker;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.OrderSummaryItem;
import ntnu.it1901.gruppe4.gui.OrderListener;

/**
 * This class contains a basic set of fields and methods for viewing {@link ntnu.it1901.gruppe4.db.Order Orders}.
 * 
 * @author Leo
 */
public class OrderSummary extends JPanel {
	protected OrderMaker currentOrder;
	protected JLabel totalPrice;
	private JLabel customerInfo;
	protected Customer customer;
	protected Collection<OrderListener> orderListeners;
	
	//Internal panels used for component grouping
	protected JPanel centerPanel;
	protected JPanel southPanel;
	
	/**
	 * Creates a new {@link ChefOrderSummary} for viewing details about {@link ntnu.it1901.gruppe4.db.Order Orders}.
	 */
	public OrderSummary() {
		this.orderListeners = new ArrayList<OrderListener>();
		totalPrice = new JLabel("<html><br>Totalpris: 0.00 kr<br><br></html>");
		customerInfo = new JLabel();
		currentOrder = new OrderMaker();
		centerPanel = new JPanel();
		southPanel = new JPanel();

		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		
		totalPrice.setFont(Layout.summaryTextFont);
		customerInfo.setFont(Layout.summaryTextFont);
		southPanel.add(totalPrice);
		southPanel.add(customerInfo);
		
		setBorder(Layout.panelPadding);
		setLayout(new BorderLayout());
		JScrollPane sp = new JScrollPane(centerPanel);
		sp.setBorder(null);
		add(sp, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
		assignCustomer(null);
		update();
	}
	
	/**
	 * Reloads the current order shown in {@link OrderSummary} from the SQL database and updates the total price.<br>
	 * There is usually no need to call on this method explicitly, 
	 * as all methods modifying the <code>ChefOrderSummary</code> are required to call this method automatically before returning.
	 */
	public void update() {
		centerPanel.removeAll();
		
		//Add order items to the panel
		updateOrderItems();

		//Add the total price to the panel
		totalPrice.setText("<html><br>Totalpris: " + 
									Layout.decimalFormat.format(currentOrder.getOrder().getTotalAmount()) + 
									" kr<br><br></html>");
		totalPrice.setFont(Layout.summaryTextFont);
		
		//Add customer information to the panel
		if (customer == null) {
			customerInfo.setText("<html>Denne orderen er ikke knyttet til noen kunde." +
							"<br><br><br><br></html>");
		}
		else {
			//The system does not currently support more than one address per customer.
			Address address = DataAPI.getAddresses(customer).get(0);
			
			customerInfo.setText("<html> <table>" +
					"<tr> <td> Navn:</td> <td>" + customer.getName() + "</td> </tr>" +
					"<tr> <td> Telefon:</td> <td>" + customer.getPhone() + "</td> </tr>" +
					"<tr> <td> Adresse:</td> <td>" + address.getAddressLine() + "</td> </tr>" +
					"<tr> <td> Postnummer:</td> <td>" + address.getPostalCode() + "</td> </tr>" +
					"</table> </html>");
		}
		centerPanel.revalidate();
		centerPanel.repaint();
	}
	
	/**
	 * Adds all {@link OrderItem OrderItems} to the center panel.
	 * Override this method to add some listener to each individual <code>OrderItem</code>.
	 */
	protected void updateOrderItems() {
		int counter = 0;
		List<OrderItem> currentItems = currentOrder.getItemList();
		
		for (final OrderItem i : currentItems) {
			OrderSummaryItem item = new OrderSummaryItem(i);
			
			//item.addListener() goes here
			
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
	 * Assigns a {@link Customer} to the {@link ChefOrderSummary}.
	 * 
	 * @param customer The <code>Customer</code> to assign to the <code>Order</code>, 
	 * or <code>null</code> to unassign the currently assigned <code>Customer</code>.
	 */
	protected void assignCustomer(Customer customer) {
		this.customer = customer;
		
		if (customer == null) {
			currentOrder.setAddress(null);
		}
		else {
			currentOrder.setAddress(DataAPI.getAddresses(customer).get(0));
		}
		update();
	}

	/**
	 * Returns the number of items in this {@link ChefOrderSummary}.
	 * 
	 * @return The number of items in this {@link ChefOrderSummary}.
	 */
	public int getItemCount() {
		return currentOrder.getItemCount();
	}

	/**
	 * Returns an unmodifiable list containing all {@link OrderItem} in this {@link ChefOrderSummary}.<br>
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
	 * @param order The already existing <code>Order</code> to view in the <code>ChefOrderSummary</code>.
	 */
	public void setOrder(Order order) {
		currentOrder = new OrderMaker(order);
		assignCustomer(DataAPI.getCustomer(DataAPI.getAddress(order)));
		update();
	}

	/**
	 * Adds a new {@link OrderListener} to this {@link ChefOrderSummary}.
	 * 
	 * @deprecated OrderListeners are no longer used.
	 * 
	 * @param listener The <code>OrderListener</code> to add.
	 */
	public void addOrderListener(OrderListener listener) {
		orderListeners.add(listener);
	}
}