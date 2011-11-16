package ntnu.it1901.gruppe4.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	private JButton delete;
	
	public OrderHistoryItem(Order order, Mode mode) {
		this.order = order;
		delete = new JButton("Slett");
		text = new JLabel("<html><table>" +
								"<tr>" +
									"<td>Dato:</td>" +
									"<td>" + Layout.dateFormat.format(order.getOrderTime()) + "</td>" +
									"<td>&nbsp;&nbsp;&nbsp;&nbsp;Totalpris: "+Layout.decimalFormat.format(order.getTotalAmount())+" kr</td>" +
								"</tr><tr>" +
									"<td>Kunde:</td>" +
									"<td>" + DataAPI.getCustomerName(order) + "</td>" +
									"<td> </td>" +
								"</tr>" +
							"</table></html>");
		
		setBorder(Layout.historyItemPadding);
		setLayout(new GridBagLayout());
		text.setFont(Layout.itemFont);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = gbc.gridy = 0;
		add(text, gbc);
		
		if (mode == Mode.ORDER) {
			gbc.anchor = GridBagConstraints.NORTHEAST;
			gbc.weightx = 0;
			gbc.gridx++;
			add(delete, gbc);
		}
		
		//To prevent this component's height from growing
		setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
	}
	
	/**
	 * Adds a listener that will be called when the delete button in this {@link OrderHistoryItem} is clicked.
	 * 
	 * @param listener The listener that will be called when the item is clicked.
	 */
	public void addDeleteButtonListener(ActionListener listener) {
		delete.addActionListener(listener);
	}
	
	/**
	 * Returns the {@link Order} object associated with this item.
	 * 
	 * @return An {@link Order} object.
	 */
	public Order getOrder() {
		return order;
	}
}
