package ntnu.it1901.gruppe4.ordergui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.OrderItem;

public class OrderSummaryItem extends JPanel {
	private JLabel name, price;

	public OrderSummaryItem(OrderItem item) {
		name = new JLabel(item.getName());
		price = new JLabel(Layout.decimalFormat.format(item.getAmount()) + " kr");
		
		setBorder(Layout.summaryItemPadding);
		name.setFont(Layout.summaryItemFont);
		price.setFont(Layout.summaryItemFont);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = 0;
		add(name, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		add(price, gbc);
	}
	
}
