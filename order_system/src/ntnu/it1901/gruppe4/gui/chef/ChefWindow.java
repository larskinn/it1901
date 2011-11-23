package ntnu.it1901.gruppe4.gui.chef;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.Main;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.MenuSearchPanel;
import ntnu.it1901.gruppe4.gui.Mode;
import ntnu.it1901.gruppe4.gui.OrderHistoryItem;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;

/**
 * The window where the chef may review and confirm orders and edit the meny.
 * 
 * @author David
 * @author Leo
 */
public class ChefWindow implements ActionListener {
	private JFrame frame;
	private ButtonPanel buttonPanel;
	private ChefOrderSummary chefOrderSummary;
	private OrderHistoryPanel orderHistoryPanel;
	private MenuSearchPanel menuPanel;
	private JPanel currentTab;
	private ResizeListener resizeListener;

	/**
	 * An enum of the tabs available in the {@link ChefWindow}.
	 * 
	 * <ul>
	 * <li>ORDERS -- The orders list view, where placed orders may be reviewed and confirmed 
	 * <li>MENU -- The food menu view, where the dishes of the food menu may be modified
	 * </ul>
	 */
	public enum Tab {
		ORDERS, MENU;
	}

	/**
	 * Resizes the components in the {@link ChefWindow} properly.
	 */
	private void handleResize() {
		orderHistoryPanel.setPreferredSize(new Dimension((int) (frame
				.getWidth() * 0.6666), frame.getHeight()));

		chefOrderSummary.setPreferredSize(new Dimension(
				(int) (frame.getWidth() * 0.3333), frame.getHeight()));

		orderHistoryPanel.revalidate();
		chefOrderSummary.revalidate();
	}

	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			handleResize();
		}
	}

	/**
	 * Constructs a new {@link ChefWindow} that will immediately displayed 
	 * in the center of the screen.
	 */
	public ChefWindow() {
		DataAPI.open();

		buttonPanel = new ButtonPanel(this);
		chefOrderSummary = new ChefOrderSummary();
		orderHistoryPanel = new OrderHistoryPanel(Mode.CHEF, chefOrderSummary);
		menuPanel = new MenuSearchPanel(Mode.CHEF);

		chefOrderSummary.setOrderHistoryPanel(orderHistoryPanel);
		resizeListener = new ResizeListener();

		orderHistoryPanel.addComponentListener(resizeListener);
		chefOrderSummary.addComponentListener(resizeListener);
		
		//Set the first item shown in the chefOrderSummary to the topmost item
		OrderHistoryItem item = orderHistoryPanel.getTopItem();
		
		if (item != null) {
			chefOrderSummary.setOrder(item.getOrder());
		}
		
		frame = new JFrame("Gruppe 4 Pizza - Kokk");
		frame.setSize(Layout.initialSize);
		frame.setLayout(new BorderLayout());
		frame.add(buttonPanel, BorderLayout.SOUTH);
		setTab(Tab.ORDERS);
		
		//Add a key listener to the chef window
				KeyboardFocusManager.getCurrentKeyboardFocusManager()
						.addKeyEventDispatcher(new KeyEventDispatcher() {
							@Override
							public boolean dispatchKeyEvent(KeyEvent e) {
								if (e.getID() != KeyEvent.KEY_RELEASED) {
									return false;
								}
								
								switch (e.getKeyCode()) {
									case KeyEvent.VK_F1:
										setTab(Tab.ORDERS);
										break;
									case KeyEvent.VK_F2:
										setTab(Tab.MENU);
										break;
									case KeyEvent.VK_ENTER:
										if (currentTab == orderHistoryPanel) {
											chefOrderSummary.deliverOrder();
										}
										break;
									case KeyEvent.VK_ESCAPE:
										if (currentTab == menuPanel) {
											menuPanel.clearSearchBox();
										}
										break;
								}
								return false;
							}
						});

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				cleanup();
				Main.showSplash();
			}
		});

		frame.setSize(Layout.initialSize);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		handleResize();
	}

	/**
	 * Closes the database connection.
	 */
	private static void cleanup() {
		DataAPI.close();
	}

	/**
	 * Views the specified {@link Tab} the {@link ChefWindow}.
	 * 
	 * @param tab
	 *            The <code>Tab</code> which will be displayed.
	 */
	public void setTab(Tab tab) {
		if (currentTab != null) {
			frame.remove(currentTab);
		}
		frame.remove(chefOrderSummary);

		switch (tab) {
			case ORDERS:
				frame.add(orderHistoryPanel, BorderLayout.CENTER);
				frame.add(chefOrderSummary, BorderLayout.EAST);
				currentTab = orderHistoryPanel;
				break;
			case MENU:
				frame.add(menuPanel, BorderLayout.CENTER);
				currentTab = menuPanel;
				break;
		}
		currentTab.grabFocus(); // This method should be overrided to pass on focus to the search box
		currentTab.revalidate(); // Check if the panel has all its components loaded
		frame.repaint(); //Repaint the frame and all its components
	}

	// Fired whenever a button in ButtonPanel is pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();

		if (src == buttonPanel.orders) {
			setTab(Tab.ORDERS);
		} else if (src == buttonPanel.menu) {
			setTab(Tab.MENU);
		}
	}
}
