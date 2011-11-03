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
import ntnu.it1901.gruppe4.gui.Mode;
import ntnu.it1901.gruppe4.gui.OrderSummary;

public class MapAndOrderPanel extends JPanel {
	
	private OrderHistoryPanel orderHistoryPanel;
	private MapPanel mapPanel;
	private ResizeListener resizeListener;
	private JScrollPane scrollpane;
	
	/**
	 * 
	 * @author Morten, Leo
	 *
	 */
	
	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			// Resizes the mapPanel to its preferred size.
			mapPanel.setMinimumSize(mapPanel.getPreferredSize());
			
			scrollpane.setPreferredSize(new Dimension(
					getWidth(), (int) (getHeight() - mapPanel.getSize().height)));


			scrollpane.revalidate();
			mapPanel.revalidate();
		}
		
	}
	
	/**
	 * Constructs a MapAndOrderPanel
	 * 
	 * @param summary
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * 
	 */
	
	public MapAndOrderPanel(OrderSummary summary) throws MalformedURLException, IOException {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		mapPanel = new MapPanel();
		orderHistoryPanel = new OrderHistoryPanel(Mode.DELIVERY, summary, mapPanel);
		resizeListener = new ResizeListener();
		scrollpane = new JScrollPane(orderHistoryPanel);
		
		scrollpane.addComponentListener(resizeListener);
		mapPanel.addComponentListener(resizeListener);
		
		add(scrollpane);
		add(mapPanel);
	}
}
