package ntnu.it1901.gruppe4.gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Order;

/**
 * A panel that shows information about an {@link Order}.
 * 
 * @author Leo
 */
public class OrderHistoryItem extends JPanel {
	private Order order;
	private JLabel text;
	
	public OrderHistoryItem(Order order) {
		this.order = order;
		
		setBorder(Layout.historyItemPadding);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		text = new JLabel("<html><table><tr><td>Dato:" +
							"</td><td>" + Layout.dateFormat.format(order.getOrderTime()) +
							"</td></tr><tr><td>Kunde:" +
							"</td><td>" + DataAPI.getCustomer(DataAPI.getAddress(order)).getName() +
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
