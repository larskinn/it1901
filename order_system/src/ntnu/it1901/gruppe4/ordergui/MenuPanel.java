package ntnu.it1901.gruppe4.ordergui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Dish;

public class MenuPanel extends JPanel {
	SearchBox menuSearch;
	OrderMenu orderMenu;
	
	public class OrderMenu extends JPanel {
		private List<MenuPanelItem> allDishes = new ArrayList<MenuPanelItem>();
		/**
		 * Creates a new OrderMenu. Only the MenuPanel is allowed to do this.
		 */
		private OrderMenu() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		}
		
		/**
		 * Converts all dishes in the given collection to {@link MenuPanelItem},
		 * adds them to the {@link OrderMenu} and repaints the panel.
		 *  
		 * @param dishes The dishes to be added to the {@link OrderMenu}.
		 */
		public void addDishes(Collection<Dish> dishes) {
			removeAll();
			allDishes.clear();
			int dishesAdded = 0;
			
			for (Dish dish : dishes) {
				allDishes.add(new MenuPanelItem(dish));
				if (dishesAdded < 6) {
					add(allDishes.get(dishesAdded));
					dishesAdded++;
				}
			}
			

			while (dishesAdded < 6) {
				add(new MenuPanelItem(null));
				dishesAdded++;
			}
			
			
			revalidate();
			repaint();
		}
	}
	
	public MenuPanel() {
		menuSearch = new SearchBox();
		orderMenu = new OrderMenu();
		setLayout(new BorderLayout());
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(menuSearch);

		//Add some space between the searchBox and the orderMenu
		add(Box.createRigidArea(new Dimension(0, 25)));
		
		//Add all the dishes to the menu
		orderMenu.addDishes(DataAPI.findDishes(""));
		
		//HACK: Prevent the menuSearch from becoming huge. Probable fix known, to be implemented later
		orderMenu.setPreferredSize(new Dimension(999, 999));
		add(orderMenu);

		menuSearch.addKeyListener(new KeyAdapter() {
			/*keyReleased() used for searching as getText() does not return the
			 *updated content of the search box when keyTyped() is called 
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				SearchBox source = (SearchBox)e.getSource();
				String boxContent = source.getText();
				char charEntered = e.getKeyChar();
				
				//If the search box is empty, interrupt and restore the list of results
				if (boxContent.equals("")) {
					orderMenu.addDishes(DataAPI.findDishes(""));
					return;
				}
				
				//Do the search
				orderMenu.addDishes(DataAPI.findDishes(boxContent));
			}
		});
	}
}