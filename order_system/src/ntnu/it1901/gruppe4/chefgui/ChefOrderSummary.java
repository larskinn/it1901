package ntnu.it1901.gruppe4.chefgui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.db.OrderMaker;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.OrderSummary;

/**
 * This class contains details about the {@link Order} being looked at by the {@link ChefWindow},<br>
 * as well as a button for marking orders as delivered.
 * 
 * @author Leo, David
 */
public class ChefOrderSummary extends OrderSummary {
	private JButton deliverButton;
	private OrderHistoryPanel orderHistoryPanel;
	
	/**
	 * Creates a new {@link ChefOrderSummary}.
	 */
	ChefOrderSummary() {
		super();
		
		deliverButton = new JButton("Klar til levering");
		orderHistoryPanel = null;

		totalPrice.setFont(Layout.summaryTextFont);
		
		//Fired when deliverButton is clicked
		deliverButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deliverOrder();
			}
		});
		southPanel.add(deliverButton);
	}
	
	/**
	 * Marks the current current {@link Order} as delivered in the database and updates the {@link OrderHistoryPanel}.
	 */
	public void deliverOrder() {
		currentOrder.setState(Order.READY_FOR_DELIVERY);
		currentOrder.save();
		currentOrder = new OrderMaker();
		assignCustomer(null);
		update();
		orderHistoryPanel.refresh();
	}

	public void setOrderHistoryPanel(OrderHistoryPanel orderHistoryPanel) {
		this.orderHistoryPanel = orderHistoryPanel;
	}
}