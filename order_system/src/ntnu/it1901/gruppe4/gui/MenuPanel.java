package ntnu.it1901.gruppe4.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.Dish;
import ntnu.it1901.gruppe4.gui.ordergui.OperatorOrderSummary;

/**
 * A container for {@link MenuPanelItem}.<br><br>
 * 
 * Use {@link #addDishes} to add {@link Dish Dishes} to the <code>MenuPanel</code>.
 * 
 * @author Leo
 */
public class MenuPanel extends JPanel {
	private OperatorOrderSummary operatorOrderSummary;
	
	/**
	 * Constructs a new {@link MenuPanel}
	 */
	public MenuPanel() {
		this(null);
	}
	
	/**
	 * Constructs a new {@link MenuPanel} that will add {@link MenuPanelItem MenuItems}
	 * to the specified {@link operatorOrderSummary} when clicked.
	 * 
	 * @param operatorOrderSummary The <code>ChefOrderSummary</code> to which clicked <code>MenuItems</code> will be added.
	 */
	public MenuPanel(OperatorOrderSummary orderSummary) {
		this.operatorOrderSummary = orderSummary;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	/**
	 * Converts all dishes in the given collection to {@link MenuPanelItem} and
	 * adds them to the {@link MenuPanel}.
	 *  
	 * @param dishes The dishes to be added to the {@link OrderMenu}.
	 */
	public void addDishes(Collection<Dish> dishes) {
		int counter = 0;
		removeAll();
		
		for (final Dish dish : dishes) {
			MenuPanelItem item = new MenuPanelItem(dish);
			
			//This listener is fired every time an item is clicked
			if (operatorOrderSummary != null) {
				item.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						operatorOrderSummary.addItem(dish);
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
		revalidate();
		repaint();
	}
}