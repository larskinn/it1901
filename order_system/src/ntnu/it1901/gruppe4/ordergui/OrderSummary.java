package ntnu.it1901.gruppe4.ordergui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ntnu.it1901.gruppe4.db.Address;
import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Dish;
import ntnu.it1901.gruppe4.db.OrderItem;
import ntnu.it1901.gruppe4.db.OrderMaker;

/**
 * This class contains every detail about the {@link ntnu.it1901.gruppe4.db.Order Order}
 * currently being processed by the operator of the {@link OrderWindow}, as well as methods to edit this <code>Order</code>.<br><br>
 * 
 * All details are written onto the extended {@link JPanel}.
 * 
 * @author Leo
 */
public class OrderSummary extends JPanel {
	private OrderMaker currentOrder;
	private JLabel totalPrice;
	private JButton saveButton;
	private Customer customer;
	
	//Internal panels used for component grouping
	private JPanel centerPanel;
	private JPanel southPanel;
	
	/**
	 * Creates a new {@link OrderSummary}. Only classes in the same package are allowed to do this.
	 */
	OrderSummary() {
		setBorder(Layout.panelPadding);
		
		totalPrice = new JLabel("<html><br>Totalpris: 0.00 kr<br><br></html>");
		saveButton = new JButton("Lagre");
		currentOrder = new OrderMaker();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		
		totalPrice.setFont(Layout.summaryTextFont);
		saveButton.setFont(Layout.summaryTextFont);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
	
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
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saveOrder();
			}
		});
		//Todo: saveButton size is currently 67, 26 - and it cannot be resized for some reason
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
			OrderSummaryItem item = new OrderSummaryItem(i);
			
			//Fired every time an order summary item is clicked
			item.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
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
		
		//Update the total price
		totalPrice = new JLabel("<html><br>Totalpris: " + 
									Layout.decimalFormat.format(currentOrder.getOrder().getTotalAmount()) + 
									" kr<br><br></html>");
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
			text.setText("<html>Denne orderen er ikke knyttet til noen kunde." +
							"<br><br><br><br></html>");
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
		southPanel.add(saveButton);
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
	public boolean saveOrder() {
		if (currentOrder.isValid()) {
			currentOrder.save();
			currentOrder = new OrderMaker();
			update();
			setCustomer(null);
			return true;
		}
		
		JLabel error = new JLabel("Orderen er ikke ferdig utfylt.");
		error.setForeground(Layout.errorColor);
		error.setFont(Layout.errorFont);
		
		southPanel.remove(saveButton);
		southPanel.add(error);
		southPanel.add(saveButton);
		
		southPanel.revalidate();
		southPanel.repaint();
		return false;
	}
	
	/**
	 * Adds a new dish as an {@link OrderItem} to the {@link OrderSummary} and recalculates the total price.
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
	 * Removes an {@link OrderItem} from the {@link OrderSummary} and recalculates the total price.
	 * 
	 * @param item The {@link OrderItem} to remove.
	 */
	public void removeItem(OrderItem item) {
		currentOrder.remItem(item);
		update();
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
}