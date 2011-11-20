package ntnu.it1901.gruppe4.gui.ordergui;

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
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.OrderSummary;
import ntnu.it1901.gruppe4.gui.Receipt;

/**
 * The window where the operator may add or edit orders and customers
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
	private JPanel currentPanel;
	private OperatorOrderSummary operatorOrderSummary;
	private ResizeListener resizeListener;

	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			// Dynamically resize the western and eastern panels
			currentPanel.setPreferredSize(new Dimension(
					(int) (frame.getWidth() * 0.6666), frame.getHeight()));

			operatorOrderSummary.setPreferredSize(new Dimension((int) (frame
					.getWidth() * 0.3333), frame.getHeight()));

			currentPanel.revalidate();
		}
	}

	/**
	 * An enum of view modes for OrderWindow<br>
	 * MENU -- The food menu view, where dishes can be added to the order<br>
	 * CUSTOMER -- The customer list view, where a customer can be applied to the order<br>
	 * HISTORY --- The order history view, where past orders may be reviewed
	 */
	public enum View {
		MENU, CUSTOMER, HISTORY;
	}

	private static void cleanup() {
		// This would make more sense if DataAPI was non-static.
		DataAPI.close();
	}

	/**
	 * Public constructor.
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
		changeView(View.MENU);

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
								changeView(View.MENU);
								break;
							case KeyEvent.VK_F2:
								changeView(View.CUSTOMER);
								break;
							case KeyEvent.VK_F3:
								changeView(View.HISTORY);
								break;
							case KeyEvent.VK_F4:
								operatorOrderSummary.toggleSelfDelivery();
								break;
							case KeyEvent.VK_F5:
								operatorOrderSummary.saveOrder();
								break;
							case KeyEvent.VK_ESCAPE:
								frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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
	 * Change the view of the order window. Options are (from {@link View}):
	 * <p>
	 * MENU -- Shows the food menu view, where dishes can be added to the order<br>
	 * CUSTOMER -- Shows the customer list view, where a customer can be assigned to the order<br>
	 * HISTORY --- Shows the order history view, where past orders may be reviewed
	 * 
	 * @param view
	 *            The view to show. Options are View.MENU, View.CUSTOMER,
	 *            View.HISTORY
	 */
	public void changeView(View view) {
		// If the frame already has a panel providing its view, remove it
		if (currentPanel != null) {
			frame.remove(currentPanel);
		}

		switch (view) {
		case MENU:
			frame.add(menuSearchPanel, BorderLayout.CENTER);
			currentPanel = menuSearchPanel;
			break;
		case CUSTOMER:
			frame.add(customerPanel, BorderLayout.CENTER);
			currentPanel = customerPanel;
			break;
		case HISTORY:
			frame.add(orderHistoryPanel, BorderLayout.CENTER);
			currentPanel = orderHistoryPanel;
			break;
		}
		currentPanel.grabFocus(); // This method should be overrided to pass on
									// focus to the search box
		currentPanel.revalidate(); // Check if the panel has all its components
									// loaded
		frame.repaint(); // Repaint the frame and all its components
	}

	/**
	 * Returns the {@link OrderSummary} object used in this window.
	 * 
	 * @return an {@link OrderSummary}
	 */
	public OrderSummary getCurrentOrderSummary() {
		return operatorOrderSummary;
	}

	//Called when a button in the button panel is pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();

		if (src == buttonPanel.menu) {
			changeView(View.MENU);
		} else if (src == buttonPanel.customer) {
			changeView(View.CUSTOMER);
		} else if (src == buttonPanel.history) {
			changeView(View.HISTORY);
		}
	}
}
