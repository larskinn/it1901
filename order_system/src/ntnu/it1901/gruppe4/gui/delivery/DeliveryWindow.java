package ntnu.it1901.gruppe4.gui.delivery;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JFrame;

import ntnu.it1901.gruppe4.Main;
import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.Mode;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;

/**
 * The window in which a delivery man or woman can view all <code>Orders</code> that
 * are ready for delivery and mark them as in transit and as delivered.
 * <p>
 * This window also contains a map which shows the distance between 
 * the pizza restaurant and a selected <code>Order</code>.
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
	 * Resizes all components in the {@link DeliveryWindow} according to the window's size.
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
	 * Resizes the image on the map according to the window's size.
	 * <p>
	 * This method cannot be called as frequently as {@link #handleResize()} because it requires
	 * Java to download a new map from the internet, which is time consuming.
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

	/**
	 * Constructs a new {@link DeliveryWindow} that will immediately displayed 
	 * in the center of the screen.
	 */
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

		frame.setTitle("Gruppe 4 Pizza - Levering");
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
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		handleResize();
		resizeMap();
	}

	/**
	 * Closes the database connection.
	 */
	private static void cleanup() {
		DataAPI.close();
	}
}
