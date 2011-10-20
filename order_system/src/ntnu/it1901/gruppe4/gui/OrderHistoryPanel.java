package ntnu.it1901.gruppe4.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Order;

public class OrderHistoryPanel extends JPanel implements OrderListener {
	private OrderSummary orderSummary;

	public enum Mode {
		ORDER, CHEF, DELIVERY;
	};
	
	Mode mode;

	/**
	 * Creates a new {@link OrderHistoryPanel} in the specified {@link Mode}.
	 * 
	 * @param mode The <code>Mode</code> specifying which GUI-view the panel is to be created in.
	 */
	public OrderHistoryPanel(Mode mode) {
		this(mode, null);
		mode = Mode.ORDER;
	}

	/**
	 * Creates a new {@link OrderHistoryPanel} in the specified {@link Mode}<br>
	 * that adds all items of an {@link Order} to the specified {@link OrderSummary} when clicked.
	 * 
	 * @param mode The <code>Mode</code> specifying which GUI-view the panel is to be created in.
	 * @param orderSummary The <code>OrderSummary</code> on which clicked <code>Orders</code> are shown.
	 */
	public OrderHistoryPanel(Mode mode, OrderSummary orderSummary) {
		this.mode = mode;
		this.orderSummary = orderSummary;
		
		setBorder(Layout.panelPadding);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		refresh();
	}

	public void refresh() {
		System.out.println(DataAPI.getOrder(0));
		int counter = 0;
		removeAll();

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
			
			OrderHistoryItem item = new OrderHistoryItem(order);

			// This listener is called when an order history item is clicked
			if (orderSummary != null) {
				item.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						orderSummary.setOrder(order);
					}
				});
			}

			if (counter++ % 2 == 0) {
				item.setBackground(Layout.bgColor1);
			} else {
				item.setBackground(Layout.bgColor2);
			}
			add(item);
		}
		
		if (getComponents().length == 0)
		{
			add(new JLabel("Ingen ordre er tilgjengelig."));
		}
		
		revalidate();
		repaint();
	}

	@Override
	public void OrderSaved() {
		refresh();
	}
}