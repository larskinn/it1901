package ntnu.it1901.gruppe4.deliverygui;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel.Mode;
import ntnu.it1901.gruppe4.gui.OrderSummary;

public class MapAndOrderPanel extends JPanel {
	
	private OrderHistoryPanel orderHistoryPanel;
	private MapWindow mapWindow;
	private ResizeListener resizeListener;
	private JScrollPane scrollpane;
	
	/**
	 * 
	 * @author Ulv
	 *
	 */
	
	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			// Resizes the mapWindow
			mapWindow.setMinimumSize(mapWindow.getPreferredSize());
			
			scrollpane.setPreferredSize(new Dimension(
					getWidth(), (int) (getHeight() - mapWindow.getSize().height)));


			scrollpane.revalidate();
			mapWindow.revalidate();
		}
		
	}
	
	public MapAndOrderPanel(OrderSummary summary) throws MalformedURLException, IOException {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		mapWindow = new MapWindow();
		orderHistoryPanel = new OrderHistoryPanel(Mode.DELIVERY, summary, mapWindow);
		resizeListener = new ResizeListener();
		scrollpane = new JScrollPane(orderHistoryPanel);
		
		scrollpane.addComponentListener(resizeListener);
		mapWindow.addComponentListener(resizeListener);
		
		add(scrollpane);
		add(mapWindow);
	}
}
