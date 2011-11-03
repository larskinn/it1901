package ntnu.it1901.gruppe4.deliverygui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JFrame;


import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.Order;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel.Mode;

/**
 * 
 * @author Leo, David, Morten
 */

public class DeliveryWindow implements ActionListener {
	
	private JFrame frame;
	private DeliveryOrderSummary deliveryOrderSummary;
	private OrderHistoryPanel orderHistoryPanel;
	private ResizeListener resizeListener;
	private MapAndOrderPanel mapAndOrderPanel;
	
	
	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			mapAndOrderPanel.setPreferredSize(new Dimension(
					(int) (frame.getWidth() * 0.6666), frame.getHeight()));

			deliveryOrderSummary.setPreferredSize(new Dimension(
					(int) (frame.getWidth() * 0.3333), frame.getHeight()));

			mapAndOrderPanel.revalidate();
			deliveryOrderSummary.revalidate();
		}
	}
	
	public DeliveryWindow() throws MalformedURLException, IOException {

		deliveryOrderSummary = new DeliveryOrderSummary();
		orderHistoryPanel = new OrderHistoryPanel(Mode.DELIVERY, deliveryOrderSummary);
		deliveryOrderSummary.setOrderHistoryPanel(orderHistoryPanel);
		resizeListener = new ResizeListener();
		mapAndOrderPanel = new MapAndOrderPanel(deliveryOrderSummary);
		

		orderHistoryPanel.addComponentListener(resizeListener);
		mapAndOrderPanel.addComponentListener(resizeListener);
		deliveryOrderSummary.addComponentListener(resizeListener);

		frame = new JFrame("Leveringsvindu");
		frame.setSize(Layout.initialSize);
		frame.setLayout(new BorderLayout());

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

		frame.setSize(Layout.initialSize);
		frame.setLayout(new BorderLayout(5, 5));
		frame.add(deliveryOrderSummary, BorderLayout.EAST);
		frame.add(mapAndOrderPanel, BorderLayout.WEST);
		
		frame.pack();

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				cleanup();
				System.exit(0);
			}
		});
		
		//handleResize();
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		DataAPI.open("./data.db");

		new DeliveryWindow();
	}
	
	private static void cleanup() {
		DataAPI.close();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
