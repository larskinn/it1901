package ntnu.it1901.gruppe4.chefgui;

import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.db.OrderMaker;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.Mode;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.OrderSummary;

/**
 * This class contains details about the {@link Order} being looked at by the {@link ChefWindow},<br>
 * as well as a button for marking orders as delivered.
 * 
 * @author David
 * @author Leo
 */
public class ChefOrderSummary extends OrderSummary {
	private JButton deliveryButton;
	private OrderHistoryPanel orderHistoryPanel;
	
	/**
	 * Creates a new {@link ChefOrderSummary}.
	 */
	ChefOrderSummary() {
		super(Mode.CHEF);
		
		deliveryButton = new JButton("Klar til levering");
		deliveryButton.setFont(Layout.summaryTextFont);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = gbc.weighty = 1;
		buttonPanel.add(deliveryButton, gbc);

		//Called when deliveryButton is clicked
		deliveryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deliverOrder();
			}
		});
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

	/**
	 * Adds an {@link OrderHistoryPanel} to the {@link ChefOrderSummary} that
	 * will be refreshed when an {@link Order} is marked as ready for delivery.
	 * 
	 * @param orderPanel The <code>OrderHistoryPanel</code> that will be updated.
	 */
	public void setOrderHistoryPanel(OrderHistoryPanel orderPanel) {
		orderHistoryPanel = orderPanel;
	}
}