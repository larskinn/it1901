package ntnu.it1901.gruppe4.ordergui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Dish;

public class MenuPanel extends JPanel {
	JTextField menuSearch;
	OrderMenu orderMenu;

	private OrderSummary currentOrder;
	
	/**
	 * This inner class of {@link MenuPanel} is a container for all {@link MenuPanelItem}.
	 */
	private class OrderMenu extends JPanel {		
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
			int counter = 0;
			removeAll();
			
			for (final Dish dish : dishes) {
				MenuPanelItem item = new MenuPanelItem(dish);
				
				//Fired whenever an item panel is clicked
				item.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						MenuPanel.this.currentOrder.addItem(dish);
					}
				});
				
				if (counter++ % 2 == 0) {
					item.setBackground(Layout.bgColor1);
				}
				else {
					item.setBackground(Layout.bgColor2);
				}
				add(item); //Add the item panel to the frame
			}
			
			//TODO: This is a hack to prevent menuSearch from growing beyond its minimum size.
			//However, this adds a large gap to the bottom of the list, so this needs to be worked out differently.
			add(Box.createVerticalStrut(999));
			revalidate();
			repaint();
		}
	}
	
	public MenuPanel(OrderSummary orderSummary) {
		currentOrder = orderSummary;
		menuSearch = new JTextField();
		orderMenu = new OrderMenu();
		
		setBorder(Layout.panelPadding);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		menuSearch.setFont(Layout.searchBoxFont);
		
		add(menuSearch);
		add(Box.createVerticalStrut(Layout.spaceAfterSearchBox));
		
		JScrollPane sp = new JScrollPane(orderMenu);
		sp.setBorder(null);
		add(sp);
		
		//Add all the dishes to the menu
		orderMenu.addDishes(DataAPI.findDishes(""));

		menuSearch.addKeyListener(new KeyAdapter() {
			/*keyReleased() used for searching as getText() does not return the
			 *updated content of the search box when keyTyped() is called 
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField source = (JTextField)e.getSource();
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