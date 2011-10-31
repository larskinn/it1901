package ntnu.it1901.gruppe4.deliverygui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel.Mode;

public class DeliveryView implements ActionListener {
	
	private JFrame frame;
	private DeliveryOrderSummary deliveryOrderSummary;
	private OrderHistoryPanel orderHistoryPanel;
	private ResizeListener resizeListener;
	private MapAndOrderPanel mapAndOrderPanel;
	private MapWindow mapWindow;
	
	
	/*static String address = "Starevegen 9";
	static String modAddress = address.replace(' ','+');
	static String newAddress = "|&markers=color:red|"+modAddress+",Trondheim";*/
	
	/*private void handleResize()
	{
		// Dynamically resize the western and eastern panels
		
	}*/
	
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
	
	public DeliveryView() throws MalformedURLException, IOException {

		deliveryOrderSummary = new DeliveryOrderSummary();
		orderHistoryPanel = new OrderHistoryPanel(Mode.DELIVERY, deliveryOrderSummary);
		deliveryOrderSummary.setOrderHistoryPanel(orderHistoryPanel);
		resizeListener = new ResizeListener();
		mapWindow = new MapWindow();
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
		//frame.add(orderHistoryPanel, BorderLayout.WEST);
		frame.add(deliveryOrderSummary, BorderLayout.EAST);
		frame.add(mapAndOrderPanel, BorderLayout.WEST);
		//mapAndOrderPanel.add(orderHistoryPanel, BorderLayout.NORTH);
		//mapAndOrderPanel.add(mapWindow, BorderLayout.SOUTH);
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

		new DeliveryView();
	}
	
	private static void cleanup() {
		DataAPI.close();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
