package ntnu.it1901.gruppe4.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Order;

/**
 * A panel that shows information about an {@link Order}.
 * 
 * @author Leo
 * @author David
 */
public class OrderHistoryItem extends JPanel {
	/**
	 * The different colors the lamp displayed on an {@link OrderHistoryItem}
	 * can be.
	 * <p>
	 * Use {@link OrderHistoryItem#setLampColor(LampColor)} to change the lamp
	 * color of an item.
	 */
	enum LampColor {
		BLUE, GREEN, PURPLE, RED, YELLOW;

		/**
		 * Returns the name of the {@link LampColor} in CamelCase to fit the
		 * image links in this program.
		 */
		public String toString() {
			switch (this) {
			case BLUE:
				return "Blue";
			case GREEN:
				return "Green";
			case PURPLE:
				return "Purple";
			case RED:
				return "Red";
			case YELLOW:
				return "Yellow";
			default:
				return "";
			}
		}
	}

	private Order order;
	private JLabel text, delete, lamp;

	public OrderHistoryItem(Order order, Mode mode) {
		this.order = order;
		lamp = new JLabel();
		delete = new JLabel(new ImageIcon(getClass().getResource(
				"/images/RedX.gif")));
		text = new JLabel("<html><table><tr><td>Dato:</td><td>"
				+ Layout.dateFormat.format(order.getOrderTime())
				+ "</td></tr><tr><td>Kunde:</td><td>"
				+ DataAPI.getCustomerName(order)
				+ "</td></tr><tr><td>Levering:</td><td>"
				+ DataAPI.getDeliveryDescription(order)
				+ "</td></tr><tr><td>Totalpris:</td><td>"
				+ Layout.decimalFormat.format(order.getTotalAmount())
				+ "&nbsp;kr</td></tr></table></html>");

		setBorder(Layout.historyItemPadding);
		setLayout(new GridBagLayout());
		text.setFont(Layout.itemFont);
		lamp.setFont(Layout.itemFont);
		setLampColor(LampColor.GREEN);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = gbc.gridy = 0;
		add(lamp, gbc);

		if (mode == Mode.ORDER) {
			gbc.anchor = GridBagConstraints.EAST;
			add(delete, gbc);

			gbc.anchor = GridBagConstraints.WEST;
		}

		gbc.gridy++;
		add(text, gbc);

		// To prevent this component's height from growing
		setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
	}

	/**
	 * Sets the color of the displayed lamp in this {@link OrderHistoryItem} to
	 * the specified {@link LampColor}.
	 * 
	 * @param color
	 *            The desired color.
	 */
	public void setLampColor(LampColor color) {
		lamp.setIcon(new ImageIcon(getClass().getResource(
				"/images/Lamp" + color + ".gif")));
	}

	/**
	 * Sets the text next to the lamp in this {@link OrderHistoryItem} to the
	 * specified String.
	 * 
	 * @param color
	 *            The desired string.
	 */
	public void setLampText(String text) {
		lamp.setText(text);
	}

	/**
	 * Adds a listener that will be called when the delete button in this
	 * {@link OrderHistoryItem} is clicked.
	 * 
	 * @param listener
	 *            The listener that will be called when the item is clicked.
	 */
	public void addDeleteButtonListener(MouseListener listener) {
		delete.addMouseListener(listener);
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
