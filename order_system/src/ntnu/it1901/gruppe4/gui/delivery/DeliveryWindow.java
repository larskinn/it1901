package ntnu.it1901.gruppe4.gui.delivery;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.xml.crypto.Data;

import ntnu.it1901.gruppe4.Main;
import ntnu.it1901.gruppe4.db.Customer;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.Mode;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;

/**
 * The window in which {@link MapPanel} and {@link DeliveryOrderSummary} are implemented.
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
	/**
	 * Creates a new {@link DeliveryWindow}.
	 *
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
	 * Closes {@link DataAPI}.
	 */
	private static void cleanup() {
		DataAPI.close();
	}
}
