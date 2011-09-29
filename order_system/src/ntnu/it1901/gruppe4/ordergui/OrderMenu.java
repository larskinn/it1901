package ntnu.it1901.gruppe4.ordergui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Dish;

public class OrderMenu extends JPanel {
	
	public OrderMenu() {
		setLayout(new GridLayout(0, 1, 30, 30));
		//add(new OrderMenuItem(new Dish("Pizza1", 2, "Dette er en pizza", true)));
		setBackground(Color.CYAN);
	}
	
	
	/**
	 * Converts all dishes in the given collection to {@link OrderMenuItem},
	 * adds them to the {@link OrderMenu} and repaints the panel.
	 *  
	 * @param dishes The dishes to be added to the {@link OrderMenu}.
	 */
	public void addDishes(Collection<Dish> dishes) {
		removeAll();
		
		for (Dish dish : dishes) {
			if (dish != null) {
				add(new OrderMenuItem(dish));
			}
		}
		revalidate();
	}
}