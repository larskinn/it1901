package ntnu.it1901.gruppe4.ordergui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Dish;

public class OrderMenuItem extends JPanel {
	private Dish item;
	private JLabel text;
	private DecimalFormat twoDecimals;
	
	public OrderMenuItem(Dish dish) {
		setLayout(new GridLayout(4, 4)); //Wrong layout. Change this later
		this.item = dish;
		text = new JLabel(item.getName());
		twoDecimals = new DecimalFormat("0.00");
		
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));
		
		add(text);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("No har du trykka på " + item.getName() + ", som har prisen " + twoDecimals.format(item.getPrice()) + " kr");
			}
		});
	}
	
	
}