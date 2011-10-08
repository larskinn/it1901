package ntnu.it1901.gruppe4.ordergui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Dish;

public class MenuPanelItem extends JPanel {
	private Dish item;
	private JLabel text;
	private DecimalFormat twoDecimals;
	
	public MenuPanelItem(Dish dish) {
		setLayout(new GridBagLayout()); //Wrong layout. Change this later
		
		if (dish == null) {
			//Return an empty menuPanelItem with no borders and no text,
			//but with the same size as any other item
			add(new JLabel(" "));
			return;
		}
		this.item = dish;
		text = new JLabel(item.getName());
		GridBagConstraints gbc = new GridBagConstraints();
		/* Not needed?
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		 */
		gbc.weightx = 0.25;
		gbc.weighty = 0.25;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		twoDecimals = new DecimalFormat("0.00");
		
		add(text, gbc);
		
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Du har valgt " + item.getName() + ", som har prisen " + twoDecimals.format(item.getPrice()) + " kr");
			}
		});
	}
	
	public Dish getdish() {
		return item;
	}
}