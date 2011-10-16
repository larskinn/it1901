package ntnu.it1901.gruppe4.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Order;

public class OrderHistoryPanel extends JPanel implements OrderListener {
	private OrderList list;
	
	public OrderHistoryPanel() {
		this(null);
	}
	
	public OrderHistoryPanel(OrderList list) {
		this.list = list;
		
		setBorder(Layout.panelPadding);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void refresh() {
		System.out.println(DataAPI.getOrder(0));
		int counter = 0;
		removeAll();
		
		for (final Order order : DataAPI.getOrders()) {
			OrderHistoryItem item = new OrderHistoryItem(order);
			
			//This listener is called when an order history item is clicked
			if (list != null) {
				item.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						list.setOrder(order);
					}
				});
			}
			
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
	
	@Override
	public void OrderSaved() {
		refresh();
	}
}