package ntnu.it1901.gruppe4.ordergui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
	private JFrame frame;
	SearchBox menuSearch;
	OrderMenu orderMenu;
	OrderList orderList;
	
	public MenuPanel(JFrame frame) {
		this.frame = frame;
		menuSearch = new SearchBox();
		orderMenu = new OrderMenu();
		orderList = new OrderList();
		
		setLayout(new BorderLayout());
		
		//Helper container used to group two elements on the left side of the frame
		//The 'final' prefix is required for the JPanel to be included in the ComponentAdapter below
		final JPanel westSide = new JPanel();
		westSide.setLayout(new BoxLayout(westSide, BoxLayout.Y_AXIS));
		westSide.add(menuSearch);
		
		//Add some space between the seachBox and the orderMenu and the westSide and the orderList
		westSide.add(Box.createRigidArea(new Dimension(0, 25)));
		
		//HACK: Prevent the menuSearch from becoming huge. Probable fix known, to be implemented later
		orderMenu.setPreferredSize(new Dimension(999, 999));
		westSide.add(orderMenu);
		
		add(westSide, BorderLayout.WEST);
		add(orderList, BorderLayout.EAST);
		
		addComponentListener(new ComponentAdapter() {
			@Override
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
		});
	}
}
