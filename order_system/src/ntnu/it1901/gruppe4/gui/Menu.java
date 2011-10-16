package ntnu.it1901.gruppe4.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Dish;
import ntnu.it1901.gruppe4.gui.ordergui.OrderSummary;

/**
 * A container for {@link MenuItem}.<br><br>
 * 
 * Use {@link #addDishes} to add {@link Dish Dishes} to the <code>Menu</code>.
 * 
 * @author Leo
 */
public class Menu extends JPanel {
	private OrderSummary orderSummary;
	
	/**
	 * Constructs a new {@link Menu}
	 */
	public Menu() {
		this(null);
	}
	
	/**
	 * Constructs a new {@link Menu} that will add {@link MenuItem MenuItems}
	 * to the specified {@link OrderSummary} when clicked.
	 * 
	 * @param orderSummary The <code>OrderSummary</code> to which clicked <code>MenuItems</code> will be added.
	 */
	public Menu(OrderSummary orderSummary) {
		this.orderSummary = orderSummary;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	/**
	 * Converts all dishes in the given collection to {@link MenuItem} and
	 * adds them to the {@link Menu}.
	 *  
	 * @param dishes The dishes to be added to the {@link OrderMenu}.
	 */
	public void addDishes(Collection<Dish> dishes) {
		int counter = 0;
		removeAll();
		
		for (final Dish dish : dishes) {
			MenuItem item = new MenuItem(dish);
			
			//This listener is fired every time an item is clicked
			if (orderSummary != null) {
				item.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						orderSummary.addItem(dish);
					}
				});
			}
			
			if (counter++ % 2 == 0) {
				item.setBackground(Layout.bgColor1);
			}
			else {
				item.setBackground(Layout.bgColor2);
			}
			add(item);
		}
	}
}