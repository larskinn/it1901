package ntnu.it1901.gruppe4.ordergui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
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
		price = new JLabel(Float.toString(item.getPrice()) + " kr");
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));
		
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.WEST;
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