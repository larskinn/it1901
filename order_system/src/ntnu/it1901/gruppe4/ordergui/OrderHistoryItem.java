package ntnu.it1901.gruppe4.ordergui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Order;

public class OrderHistoryItem extends JPanel {
	private Order order;
	private JLabel text;
	
	public OrderHistoryItem(Order order) {
		this.order = order;
		
		setBorder(Layout.historyItemPadding);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		text = new JLabel("<html><table><tr><td>Bestilt:" +
							"</td><td>" + order.getOrderTime() +
							"</td></tr><tr><td>Kunde:" +
							"</td><td>" + order.getIdAddress().getIdCustomer().getName() +
							"</td></tr></table></html>");
		text.setFont(Layout.itemFont);
		
		add(text);
		
		//To prevent this component's height from growing
		setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
	}
	
	public Order getOrder() {
		return order;
	}
}
