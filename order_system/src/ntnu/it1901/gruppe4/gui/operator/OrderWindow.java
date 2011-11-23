package ntnu.it1901.gruppe4.gui.operator;

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
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;

/**
 * The window where a telephone operator in the restaurant can add or edit 
 * {@link ntnu.it1901.gruppe4.db.Order Orders} and {@link ntnu.it1901.gruppe4.db.Customer Customers}.
 * 
 * @author Leo
 * 
 */
public class OrderWindow implements ActionListener {
	private JFrame frame;
	private ButtonPanel buttonPanel;
	private MenuSearchPanel menuSearchPanel;
	private CustomerPanel customerPanel;
	private OrderHistoryPanel orderHistoryPanel;
	private JPanel currentTab;
	private OperatorOrderSummary operatorOrderSummary;
	private ResizeListener resizeListener;

	/**
	 * This listener ensures that all components within the {@link OrderWindow} always have the correct size.
	 * 
	 * @author Leo
	 */
	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			// Dynamically resize the western and eastern panels
			currentTab.setPreferredSize(new Dimension(
					(int) (frame.getWidth() * 0.6666), frame.getHeight()));

			operatorOrderSummary.setPreferredSize(new Dimension((int) (frame
					.getWidth() * 0.3333), frame.getHeight()));

			currentTab.revalidate();
		}
	}

	/**
	 * An enum of the tabs available in the {@link OrderWindow}.
	 * 
	 * <ul>
	 * <li>MENU -- The menu view, where dishes can be added to an <code>Order</code>.
	 * <li>CUSTOMER -- The customer list view, where a customer can be assigned to an 
	 * <code>Order</code> and new customers can be created.
	 * <li>HISTORY -- The order history view, where past <code>Orders</code> may be reviewed
	 * </ul>
	 */
	public enum Tab {
		MENU, CUSTOMER, HISTORY;
	}

	/**
	 * Constructs a new {@link OrderWindow} that will immediately displayed 
	 * in the center of the screen.
	 */
	public OrderWindow() {
		DataAPI.open();

		frame = new JFrame();
		operatorOrderSummary = new OperatorOrderSummary();
		orderHistoryPanel = new OrderHistoryPanel(Mode.ORDER, operatorOrderSummary);
		menuSearchPanel = new MenuSearchPanel(Mode.ORDER, operatorOrderSummary);
		customerPanel = new CustomerPanel(operatorOrderSummary);
		buttonPanel = new ButtonPanel(this);
		resizeListener = new ResizeListener();

		operatorOrderSummary.setOrderHistoryPanel(orderHistoryPanel);
		operatorOrderSummary.addComponentListener(resizeListener);
		menuSearchPanel.addComponentListener(resizeListener);
		customerPanel.addComponentListener(resizeListener);
		orderHistoryPanel.addComponentListener(resizeListener);

		frame.setSize(Layout.initialSize);
		frame.setLayout(new BorderLayout());
		frame.add(operatorOrderSummary, BorderLayout.EAST);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		setTab(Tab.MENU);

		frame.setTitle("Gruppe 4 Pizza - Bestilling");
		frame.setLocationRelativeTo(null); // Center the frame

		// Adds a key listener to the order window
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (e.getID() != KeyEvent.KEY_RELEASED) {
							return false;
						}
						
						switch (e.getKeyCode()) {
							case KeyEvent.VK_F1:
								setTab(Tab.MENU);
								break;
							case KeyEvent.VK_F2:
								setTab(Tab.CUSTOMER);
								break;
							case KeyEvent.VK_F3:
								setTab(Tab.HISTORY);
								break;
							case KeyEvent.VK_F4:
								operatorOrderSummary.toggleSelfDelivery();
								break;
							case KeyEvent.VK_F5:
								operatorOrderSummary.saveOrder();
								break;
							case KeyEvent.VK_ESCAPE:
								if (currentTab == menuSearchPanel) {
									menuSearchPanel.clearSearchBox();
								}
								else if (currentTab == customerPanel) {
									customerPanel.clearSearchBox();
								}
								break;
						}
						return false;
					}
				});

		// Makes sure that the connection to the database is properly closed
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				cleanup();
				Main.showSplash();
			}
		});
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Closes the connection to the database.
	 */
	private static void cleanup() {
		// This would make more sense if DataAPI was non-static.
		DataAPI.close();
	}

	/**
	 * Views the specified {@link Tab} the {@link OrderWindow}.
	 * 
	 * @param tab
	 *            The <code>Tab</code> which will be displayed.
	 */
	public void setTab(Tab tab) {
		if (currentTab != null) {
			frame.remove(currentTab);
		}

		switch (tab) {
		case MENU:
			frame.add(menuSearchPanel, BorderLayout.CENTER);
			currentTab = menuSearchPanel;
			break;
		case CUSTOMER:
			frame.add(customerPanel, BorderLayout.CENTER);
			currentTab = customerPanel;
			break;
		case HISTORY:
			frame.add(orderHistoryPanel, BorderLayout.CENTER);
			currentTab = orderHistoryPanel;
			break;
		}
		currentTab.grabFocus();
		currentTab.revalidate();
		frame.repaint();
	}

	//Called when a button in the button panel is pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();

		if (src == buttonPanel.menu) {
			setTab(Tab.MENU);
		} else if (src == buttonPanel.customer) {
			setTab(Tab.CUSTOMER);
		} else if (src == buttonPanel.history) {
			setTab(Tab.HISTORY);
		}
	}
}
