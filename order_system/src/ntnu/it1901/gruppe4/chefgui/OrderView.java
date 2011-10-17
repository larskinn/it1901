package ntnu.it1901.gruppe4.chefgui;

import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.db.OrderItem;
import ntnu.it1901.gruppe4.db.OrderMaker;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.OrderList;
import ntnu.it1901.gruppe4.gui.OrderListItem;

/**
 * This class contains every {@link Dish} in the specified {@link Order}.<br><br>
 * This class is immutable, meaning that the {@link Order} within cannot be changed.
 * 
 * @author Leo
 */
public class OrderView extends JPanel implements OrderList {
	private Order order;
	private JLabel totalPrice;
	private JPanel centerPanel;
	
	/**
	 * Creates a new {@link OrderSummary} with the specified {@link Order}.
	 */
	public OrderView() {
		order = null;
		totalPrice = new JLabel();
		centerPanel = new JPanel();

		totalPrice.setFont(Layout.summaryTextFont);
		
		setBorder(Layout.panelPadding);
		setLayout(new GridLayout(2, 1));
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
	}
	
	public void refresh() {
		removeAll();
		
		//Load all order items from the SQL database
		OrderMaker orderMaker = new OrderMaker(order);
		Collection<OrderItem> items = orderMaker.getItemList();
		int counter = 0;
		
		for (OrderItem i : items) {
			OrderListItem item = new OrderListItem(i);
			
			if (counter++ % 2 == 0) {
				item.setBackground(Layout.summaryBgColor1);
			}
			else {
				item.setBackground(Layout.summaryBgColor2);
			}
			centerPanel.add(item);
		}
		
		//Set the total price
		totalPrice = new JLabel("Totalpris: " + 
									Layout.decimalFormat.format(order.getTotalAmount()));
		totalPrice.setFont(Layout.summaryTextFont);
		
		JScrollPane sp = new JScrollPane(centerPanel);
		sp.setBorder(null);
		add(centerPanel);
		add(totalPrice);
	}
	
	@Override
	public void setOrder(Order order) {
		this.order = order;
		refresh();
	}
}