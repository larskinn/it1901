package ntnu.it1901.gruppe4.deliverygui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.db.OrderMaker;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.OrderSummary;

/**
 * This class contains details about the {@link Order} being looked at by the {@link DeliveryView},<br>
 * as well as a button for marking orders as delivered.
 * 
 * @author Leo, David
 */
public class DeliveryOrderSummary extends OrderSummary {
	private JButton deliveredButton;
	private JButton inTransitButton;
	private OrderHistoryPanel orderHistoryPanel;
	
	/**
	 * Creates a new {@link DeliveryOrderSummary}.
	 */
	DeliveryOrderSummary() {
		super();
		
		inTransitButton = new JButton("Under levering");
		deliveredButton = new JButton("Levert");
		orderHistoryPanel = null;

		totalPrice.setFont(Layout.summaryTextFont);
		
		//Fired when deliverButton is clicked
		deliveredButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deliverOrder();
			}
		});
		inTransitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				transitOrder();
			}
		});
		southPanel.add(inTransitButton);
		southPanel.add(deliveredButton);
	}
	
	/**
	 * Marks the current current {@link Order} as delivered in the database and updates the {@link OrderHistoryPanel}.
	 */
	public void deliverOrder() {
		currentOrder.setState(Order.DELIVERED_AND_PAID);
		currentOrder.save();
		currentOrder = new OrderMaker();
		assignCustomer(null);
		update();
		orderHistoryPanel.refresh();
	}
	
	public void transitOrder() {
		currentOrder.setState(Order.IN_TRANSIT);
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