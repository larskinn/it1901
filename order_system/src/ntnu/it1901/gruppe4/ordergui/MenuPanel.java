package ntnu.it1901.gruppe4.ordergui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.DataAPI;

public class MenuPanel extends JPanel {
	private JFrame frame;
	SearchBox menuSearch;
	OrderMenu orderMenu;
	OrderList orderList;
	private JPanel westSide;
	
	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			MenuPanel menuPanel = MenuPanel.this;
			
			//Dynamically resize the western and eastern panels
			westSide.setPreferredSize(new Dimension(
					(int)(menuPanel.frame.getWidth() * 0.6666),
							menuPanel.frame.getHeight()));
			
			orderList.setPreferredSize(new Dimension(
					(int)(menuPanel.frame.getWidth() * 0.3333),
							menuPanel.frame.getHeight()));	
			
			menuPanel.revalidate();
		}
	}
	
	public MenuPanel(JFrame frame) {
		this.frame = frame;
		menuSearch = new SearchBox();
		orderMenu = new OrderMenu();
		orderList = new OrderList();
		westSide = new JPanel(); //Helper container used to group two elements on the left side of the frame
		
		setLayout(new BorderLayout());
		
		westSide.setLayout(new BoxLayout(westSide, BoxLayout.Y_AXIS));
		westSide.add(menuSearch);
		
		//Add some space between the seachBox and the orderMenu and the westSide and the orderList
		westSide.add(Box.createRigidArea(new Dimension(0, 25)));
		
		//HACK: Prevent the menuSearch from becoming huge. Probable fix known, to be implemented later
		orderMenu.setPreferredSize(new Dimension(999, 999));
		westSide.add(orderMenu);
		
		add(westSide, BorderLayout.WEST);
		add(orderList, BorderLayout.EAST);
		
		//Add a listener to every componenet
		//Pretty hacky fix imo. Will probably come back and tweak this later
		ResizeListener resizeListener = new ResizeListener();
		westSide.addComponentListener(resizeListener);
		orderList.addComponentListener(resizeListener);
		this.addComponentListener(resizeListener);
		
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
