package ntnu.it1901.gruppe4.deliverygui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ntnu.it1901.gruppe4.Main;
import ntnu.it1901.gruppe4.chefgui.ChefWindow.View;
import ntnu.it1901.gruppe4.db.Address;
import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.gui.ConfigWindow;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.Mode;
import ntnu.it1901.gruppe4.gui.OrderSummary;
import ntnu.it1901.gruppe4.gui.Receipt;

/**
 * 
 * @author Morten 
 * @author David
 * @author Leo 
 */

public class DeliveryWindow {

	private JFrame frame;
	private DeliveryOrderSummary orderSummary;
	private OrderHistoryPanel orderPanel;
	private MapPanel map;
	private ResizeListener resizeListener;

	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			handleResize();
		}
	}
	
	/**
	 * Resizes all components in the {@link DeliveryWindow} according to the frame's size.
	 */
	private void handleResize() {
		map.setPreferredSize(new Dimension((int)(frame.getWidth() * 0.65),
				(int)(frame.getHeight() * 0.55)));
		
		orderPanel.setPreferredSize(new Dimension((int)(frame.getWidth() * 0.65),
				(int)(frame.getHeight() * 0.45)));
		
		orderSummary.setPreferredSize(new Dimension((int)(frame.getWidth() * 0.32),
				frame.getHeight()));

		map.revalidate();
		orderPanel.revalidate();
		orderSummary.revalidate();
	}
	
	/**
	 * Resize the map according to the frame's size.
	 */
	private void resizeMap() {
		Customer c = orderSummary.getCustomer();
		
		if (c == null) {
			map.setAddress(null, new Dimension((int)(frame.getWidth() * 0.65), (int)(frame.getHeight() * 0.55)));
		}
		else {
			map.setAddress(DataAPI.getAddresses(c).get(0),
					new Dimension((int)(frame.getWidth() * 0.65), (int)(frame.getHeight() * 0.55)));
		}
	}

	public DeliveryWindow() {
		DataAPI.open();

		frame = new JFrame();
		orderSummary = new DeliveryOrderSummary();
		map = new MapPanel();
		orderPanel = new OrderHistoryPanel(Mode.DELIVERY, orderSummary, map);
		orderSummary.setOrderHistoryPanel(orderPanel);
		resizeListener = new ResizeListener();

		orderPanel.addComponentListener(resizeListener);
		orderSummary.addComponentListener(resizeListener);
		map.addComponentListener(resizeListener);
		frame.addComponentListener(resizeListener);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resizeMap();
			}
		});

		
		frame.setTitle("Leveringsvindu");
		frame.setSize(Layout.initialSize);
		frame.setLayout(new BorderLayout());
		
		Box orderAndMapBox = Box.createVerticalBox();
		orderAndMapBox.add(orderPanel);
		orderAndMapBox.add(map);
		frame.add(orderAndMapBox, BorderLayout.WEST);
		frame.add(orderSummary, BorderLayout.EAST);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				cleanup();
				Main.showSplash();
			}
		});
		
		//Add a key listener to the chef window
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (e.getID() != KeyEvent.KEY_RELEASED) {
							return false;
						}
						
						switch (e.getKeyCode()) {
							case KeyEvent.VK_ESCAPE:
								frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
								break;
						}
						return false;
					}
				});
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		handleResize();
		resizeMap();
	}

	private static void cleanup() {
		DataAPI.close();
	}
}
