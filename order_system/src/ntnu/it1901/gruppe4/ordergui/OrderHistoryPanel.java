package ntnu.it1901.gruppe4.ordergui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Order;

public class OrderHistoryPanel extends JPanel implements AncestorListener {
	private OrderSummary currentOrder;
	
	public OrderHistoryPanel(OrderSummary orderSummary) {
		currentOrder = orderSummary;
		
		addAncestorListener(this);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void refresh() {
		System.out.println(DataAPI.getOrder(0));
		int counter = 0;
		removeAll();
		
		for (final Order order : DataAPI.getOrders()) {
			OrderHistoryItem item = new OrderHistoryItem(order);
			
			//This listener is called when an order history item is clicked
			item.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					currentOrder.setCurrentOrder(order);
				}
			});
			
			if (counter++ % 2 == 0) {
				item.setBackground(Layout.bgColor1);
			}
			else {
				item.setBackground(Layout.bgColor2);
			}
			add(item);
		}
		revalidate();
		repaint();
	}

	//This method is called every time this panel is either added to another component or made visible
	@Override
	public void ancestorAdded(AncestorEvent event) {
		refresh();
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {}

	@Override
	public void ancestorMoved(AncestorEvent event) {}
}