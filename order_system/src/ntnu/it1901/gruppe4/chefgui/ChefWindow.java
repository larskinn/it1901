package ntnu.it1901.gruppe4.chefgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.TextArea;

public class ChefWindow {
	private JFrame frame;
	private OrderMenu orderMenu;
	private OrderList orderList;
	private Order order; 
	
	public ChefWindow() {
		frame = new JFrame();
		orderMenu = new OrderMenu();
		orderList = new OrderList();
		order = new Order();

		
		//Helper container used to group two elements on the left side of the frame
		final JPanel westSide = new JPanel();
		westSide.setLayout(new BoxLayout(westSide, BoxLayout.Y_AXIS));		
		
		
		//HACK: Prevent the searchBox from becoming the dominating component of the panel
		orderMenu.setPreferredSize(new Dimension(999, 999));
		westSide.add(orderMenu);
		
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout());
		frame.add(order, BorderLayout.EAST);
		frame.add(orderList, BorderLayout.WEST);
		
		
		
		
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				//Dynamically resize the western and eastern panels
				westSide.setPreferredSize(new Dimension(
						(int)(frame.getWidth() * 0.3333), frame.getHeight()));
				orderList.setPreferredSize(new Dimension(
						(int)(frame.getWidth() * 0.6666), frame.getHeight()));
				westSide.revalidate();
				orderList.revalidate();
			}
		});
		
		frame.setTitle("Vindu - Kokk");
		//Center the frame
		frame.setLocationRelativeTo(null); 
		//Change to dispose or close when called from another frame (ie. the splash screen)
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**@return The {@link JFrame} backing this {@link ChefWindow}.*/
	public JFrame getFrame() {
		return frame;
	}
	
	public static void main(String[] args) {
		new ChefWindow();
	}
}
