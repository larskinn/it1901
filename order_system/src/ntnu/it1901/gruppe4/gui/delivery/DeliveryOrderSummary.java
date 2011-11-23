package ntnu.it1901.gruppe4.gui.delivery;

import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.Mode;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.OrderSummary;
import ntnu.it1901.gruppe4.gui.Receipt;

/**
 * This class contains details about the {@link Order} being looked at by the {@link DeliveryWindow},
 * as well as buttons for marking orders as in transit and delivered.
 * 
 * @author Morten
 * @author David
 * @author Leo
 */
public class DeliveryOrderSummary extends OrderSummary {
	private JButton deliveredButton, inTransitButton;
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
			public void mouseReleased(MouseEvent e) {
				deliverOrder();
			}
		});
		
		inTransitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				transitOrder();
			}
		});
	}
	
	/**
	 * Marks the current {@link Order} as delivered in the database,
	 * updates the {@link OrderHistoryPanel} and shows a {@link Receipt}.
	 */
	public void deliverOrder() {
		currentOrder.setState(Order.DELIVERED_AND_PAID);
		currentOrder.save();
		update();
		orderHistoryPanel.refresh();
		new Receipt(this);
	}
	
	/**
	 * Marks the current {@link Order} as in transit in the database and updates the {@link OrderHistoryPanel}.
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