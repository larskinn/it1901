package ntnu.it1901.gruppe4.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Dish;

/**
 * A panel that shows information about a {@link Dish}.
 * 
 * @author Leo
 */
public class MenuPanelItem extends JPanel {
	private Dish item;
	private JLabel name, price;
	
	/**
	 * Creates a new {@link MenuPanelItem} containing the specified {@link Dish} within.
	 *  
	 * @param dish The <code>Dish</code> to be associated with the new <code>MenuPanelItem</code>.
	 */
	public MenuPanelItem(Dish dish) {
		this.item = dish;
		name = new JLabel(item.getName());
		price = new JLabel(Layout.decimalFormat.format(item.getPrice()) + " kr");
		
		setBorder(Layout.menuItemPadding);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		name.setFont(Layout.itemFont);
		price.setFont(Layout.itemFont);
		
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(name, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		add(price, gbc);

		//To prevent this component's height from growing
		setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
	}
	
	/**
	 * Returns the {@link Dish} contained within this <code>MenuPanelItem</code>.
	 * 
	 * @return The <code>Dish</code> contained within this <code>MenuPanelItem</code>.
	 */
	public Dish getdish() {
		return item;
	}
}