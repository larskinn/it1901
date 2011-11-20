package ntnu.it1901.gruppe4.deliverygui;

import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.Mode;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.OrderSummary;

/**
 * This class contains details about the {@link Order} being looked at by the {@link DeliveryWindow},<br>
 * as well as buttons for marking orders as in transit and delivered.
 * 
 * @author Morten
 * @author David
 * @author Leo
 */
public class DeliveryOrderSummary extends OrderSummary {
	private JButton deliveredButton;
	private JButton inTransitButton;
	private OrderHistoryPanel orderHistoryPanel;
	
	/**
	 * Creates a new {@link DeliveryOrderSummary}.
	 */
	DeliveryOrderSummary() {
		super(Mode.DELIVERY);
		
		inTransitButton = new JButton("Under levering");
		deliveredButton = new JButton("Levert");
		
		inTransitButton.setFont(Layout.summaryTextFont);
		deliveredButton.setFont(Layout.summaryTextFont);

		inTransitButton.setVisible(false);
		deliveredButton.setVisible(false);
		
		/*The two buttons are added to the same column because only one
		 * of them can be visible at the same time.
		 */
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = gbc.weighty = 1;
		buttonPanel.add(inTransitButton, gbc);
		buttonPanel.add(deliveredButton, gbc);
		
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
	}
	
	/**
	 * Marks the current current {@link Order} as delivered in the database and updates the {@link OrderHistoryPanel}.
	 */
	public void deliverOrder() {
		currentOrder.setState(Order.DELIVERED_AND_PAID);
		currentOrder.save();
		update();
		orderHistoryPanel.refresh();
	}
	
	/**
	 * Marks the current current {@link Order} as in transit in the database and updates the {@link OrderHistoryPanel}.
	 */
	public void transitOrder() {
		currentOrder.setState(Order.IN_TRANSIT);
		currentOrder.save();
		update();
		orderHistoryPanel.refresh();
	}

	/**
	 * Adds an {@link OrderHistoryPanel} to the {@link DeliveryOrderSummary} that will
	 * be refreshed when an {@link Order} is marked as delivered or in transit.
	 * 
	 * @param orderPanel The <code>OrderHistoryPanel</code> that will be updated.
	 */
	public void setOrderHistoryPanel(OrderHistoryPanel orderPanel) {
		orderHistoryPanel = orderPanel;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update() {
		super.update();
		
		if (inTransitButton != null && deliveredButton != null) {
			inTransitButton.setVisible(currentOrder.getOrder().getState() == Order.READY_FOR_DELIVERY);
			deliveredButton.setVisible(currentOrder.getOrder().getState() == Order.IN_TRANSIT);
		}
	}
}