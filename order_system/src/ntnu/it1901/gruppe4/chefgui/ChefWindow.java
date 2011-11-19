package ntnu.it1901.gruppe4.chefgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.Main;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.gui.ConfigWindow;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.MenuSearchPanel;
import ntnu.it1901.gruppe4.gui.Mode;
import ntnu.it1901.gruppe4.gui.OrderHistoryItem;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.Receipt;
import ntnu.it1901.gruppe4.gui.ordergui.OrderWindow.View;

/**
 * The window where the chef may review and confirm orders, and edit the food
 * menu
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
	private JPanel currentPanel;
	private ResizeListener resizeListener;

	/**
	 * An enum of view modes for ChefWindow ORDERS -- The orders list view,
	 * where placed orders may be reviewed and confirmed MENU -- The food menu
	 * view, where the dishes of the food menu may be modified
	 */
	public enum View {
		ORDERS, MENU;
	}

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
		
		frame = new JFrame("Kokkevindu");
		frame.setSize(Layout.initialSize);
		frame.setLayout(new BorderLayout());
		frame.add(buttonPanel, BorderLayout.SOUTH);
		changeView(View.ORDERS);
		
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
										changeView(View.ORDERS);
										break;
									case KeyEvent.VK_F2:
										changeView(View.MENU);
										break;
									case KeyEvent.VK_ENTER:
										if (currentPanel == orderHistoryPanel) {
											chefOrderSummary.deliverOrder();
										}
										break;
									case KeyEvent.VK_ESCAPE:
										frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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

	private static void cleanup() {
		DataAPI.close();
	}

	/**
	 * Change the view of the chef window. Options are: ORDERS -- The orders
	 * list view, where placed orders may be reviewed and confirmed MENU -- The
	 * food menu view, where the dishes of the food menu may be modified
	 * 
	 * @param view
	 *            The view to show. Options are View.ORDERS, View.MENU
	 * 
	 */
	public void changeView(View view) {
		// If the frame already has a panel providing its view, remove it
		if (currentPanel != null) {
			frame.remove(currentPanel);
		}
		frame.remove(chefOrderSummary);

		switch (view) {
			case ORDERS:
				frame.add(orderHistoryPanel, BorderLayout.CENTER);
				frame.add(chefOrderSummary, BorderLayout.EAST);
				currentPanel = orderHistoryPanel;
				break;
			case MENU:
				frame.add(menuPanel, BorderLayout.CENTER);
				currentPanel = menuPanel;
				break;
		}
		currentPanel.grabFocus(); // This method should be overrided to pass on focus to the search box
		currentPanel.revalidate(); // Check if the panel has all its components loaded
		frame.repaint(); //Repaint the frame and all its components
	}

	// Fired whenever a button in ButtonPanel is pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();

		if (src == buttonPanel.orders) {
			changeView(View.ORDERS);
		} else if (src == buttonPanel.menu) {
			changeView(View.MENU);
		}
	}
}
