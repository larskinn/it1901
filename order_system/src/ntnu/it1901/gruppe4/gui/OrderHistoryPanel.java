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

	public OrderHistoryPanel() {
		this(null);
		mode = Mode.ORDER;
	}

	public OrderHistoryPanel(OrderSummary orderSummary) {
		this.orderSummary = orderSummary;

		setBorder(Layout.panelPadding);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		mode = Mode.ORDER;
		refresh();
	}

	public OrderHistoryPanel(OrderSummary orderSummary, Mode mode) {
		this.orderSummary = orderSummary;

		setBorder(Layout.panelPadding);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.mode = mode;
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