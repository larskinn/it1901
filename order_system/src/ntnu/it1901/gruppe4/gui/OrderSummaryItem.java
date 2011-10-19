package ntnu.it1901.gruppe4.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.OrderItem;

/**
 * A panel that shows information about an {@link OrderItem}.
 * 
 * @author Leo
 */
public class OrderSummaryItem extends JPanel {
	private JLabel name, price;

	/**
	 * Creates a new {@link OrderSummaryItem} containing the specified {@link OrderItem}.
	 *  
	 * @param item The <code>OrderItem</code> to be associated with the new <code>OrderSummaryItem</code>.
	 */
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
		
		//To prevent this component's height from growing
		setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
	}
	
}
