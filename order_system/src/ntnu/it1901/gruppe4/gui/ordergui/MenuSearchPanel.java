package ntnu.it1901.gruppe4.gui.ordergui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.MenuPanel;

public class MenuSearchPanel extends JPanel {
	private SearchBox menuSearch;
	private MenuPanel orderMenu;
	private JScrollPane scrollPane;
	
	public MenuSearchPanel(OperatorOrderSummary orderSummary) {
		menuSearch = new SearchBox();
		orderMenu = new MenuPanel(orderSummary);
		
		setBorder(Layout.panelPadding);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(menuSearch);
		add(Box.createVerticalStrut(Layout.spaceAfterSearchBox));
		
		scrollPane = new JScrollPane(orderMenu);
		scrollPane.setBorder(null);
		add(scrollPane);
		
		//Add all the dishes to the menu
		orderMenu.addDishes(DataAPI.findDishes(""));

		menuSearch.addKeyListener(new KeyAdapter() {
			/*keyReleased() used for searching as getText() does not return the
			 *updated content of the search box when keyTyped() is called 
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				SearchBox source = (SearchBox)e.getSource();
				String boxContent = source.getText();
				char charEntered = e.getKeyChar();
				
				//If the search box is empty, restore the list of results
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