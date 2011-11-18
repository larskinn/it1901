package ntnu.it1901.gruppe4.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.db.OrderMaker;
import ntnu.it1901.gruppe4.deliverygui.MapPanel;

public class OrderHistoryPanel extends JPanel {
	private JPanel innerPanel;
	private OrderSummary orderSummary;
	private MapPanel MapPanel;
	private Mode mode;

	/**
	 * Creates a new {@link OrderHistoryPanel} in the specified {@link Mode}.
	 * 
	 * @param mode The <code>Mode</code> specifying which GUI-view the panel is to be created in.
	 */
	public OrderHistoryPanel(Mode mode) {
		this(mode, null);
	}

	/**
	 * Creates a new {@link OrderHistoryPanel} in the specified {@link Mode}<br>
	 * that adds all items of an {@link Order} to the specified {@link OrderSummary} when clicked.
	 * 
	 * @param mode The <code>Mode</code> specifying which GUI-view the panel is to be created in.
	 * @param orderSummary The <code>OrderSummary</code> on which clicked <code>Orders</code> are shown.
	 */
	public OrderHistoryPanel(Mode mode, OrderSummary orderSummary) {
		this(mode, orderSummary, null);
	}
	
	/**
	 * Creates a new {@link OrderHistoryPanel} in the specified {@link Mode}<br>
	 * that adds all items of an {@link Order} to the specified {@link OrderSummary} and
	 * the specified {@link MapPanel} when clicked.
	 * 
	 * @param mode The <code>Mode</code> specifying which GUI-view the panel is to be created in.
	 * @param orderSummary The <code>OrderSummary</code> on which clicked <code>Orders</code> are shown.
	 * @param MapPanel The <code>MapPanel</code> in which clicked <code>Orders</code> are shown.
	 */
	public OrderHistoryPanel(Mode mode, OrderSummary orderSummary, MapPanel MapPanel) {
		innerPanel = new JPanel();
		this.mode = mode;
		this.orderSummary = orderSummary;
		this.MapPanel = MapPanel;
		
		setBorder(Layout.panelPadding);
		setLayout(new BorderLayout());
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		refresh();
		
		JScrollPane sp = new JScrollPane(innerPanel);
		sp.setBorder(null);
		add(sp, BorderLayout.CENTER);
	}

	public void refresh() {
		int counter = 0;
		innerPanel.removeAll();
		
		for (final Order order : DataAPI.getOrders()) {
			if (mode == Mode.ORDER){
				if (!order.isVisibleToOperator()) continue;
			}
			if (mode == Mode.CHEF){
				if (!order.isVisibleToChef()) continue;
			}
			if (mode == Mode.DELIVERY){
				if (!order.isVisibleToDelivery()) continue;
			}
			
			OrderHistoryItem item = new OrderHistoryItem(order, mode);

			if (orderSummary != null) {
				//This listener is called when an order history item is clicked
				item.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						orderSummary.setOrder(order);

						if (MapPanel != null) {
							//Update the map and the order summary  with the clicked order
							MapPanel.setAddress(DataAPI.getAddress(order));
						}
					}
				});
			}
			
			if (mode == Mode.ORDER && orderSummary != null) {
				item.addDeleteButtonListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//Delete the order contained within the item from the database
						DataAPI.remOrderItems(order);
						DataAPI.remOrder(order);
						orderSummary.setOrder(null);
						refresh();
					}
				});
			}

			if (counter++ % 2 == 0) {
				item.setBackground(Layout.bgColor1);
			} else {
				item.setBackground(Layout.bgColor2);
			}
			innerPanel.add(item);
		}
		
		if (innerPanel.getComponents().length == 0) {
			innerPanel.add(new JLabel("Ingen ordre er tilgjengelig."));
		}
		innerPanel.revalidate();
		innerPanel.repaint();
	}
}