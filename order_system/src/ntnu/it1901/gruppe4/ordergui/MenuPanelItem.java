package ntnu.it1901.gruppe4.ordergui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Dish;

public class MenuPanelItem extends JPanel {
	private Dish item;
	private JLabel name, price;
	
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
		gbc.gridy = 0;
		add(name, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		add(price, gbc);
		
		gbc.gridy = 1;
		add(Box.createVerticalStrut(28), gbc);
	}
	
	public Dish getdish() {
		return item;
	}
}