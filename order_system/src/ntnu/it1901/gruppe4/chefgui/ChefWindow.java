package ntnu.it1901.gruppe4.chefgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;

public class ChefWindow implements ActionListener {

	private JFrame frame;
	private ButtonPanel buttonPanel;
	private OrderSummary orderSummary;
	private OrderHistoryPanel orderHistoryPanel;
	private ResizeListener resizeListener;

	private void handleResize()
	{
		// Dynamically resize the western and eastern panels
		orderHistoryPanel.setPreferredSize(new Dimension(
				(int) (frame.getWidth() * 0.6666), frame.getHeight()));

		orderSummary.setPreferredSize(new Dimension(
				(int) (frame.getWidth() * 0.3333), frame.getHeight()));

		orderHistoryPanel.revalidate();
		orderSummary.revalidate();
	}
	
	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			handleResize();
		}
	}
	
	public ChefWindow() {

		buttonPanel = new ButtonPanel(this);
		orderSummary = new OrderSummary();
		orderHistoryPanel = new OrderHistoryPanel(orderSummary);
		orderSummary.setOrderHistoryPanel(orderHistoryPanel);
		resizeListener = new ResizeListener();

		orderHistoryPanel.addComponentListener(resizeListener);
		orderSummary.addComponentListener(resizeListener);

		frame = new JFrame("Kokkevindu");
		frame.setSize(Layout.initialSize);
		frame.setLayout(new BorderLayout());

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

		frame.setSize(Layout.initialSize);
		frame.setLayout(new BorderLayout());
		frame.add(orderHistoryPanel, BorderLayout.WEST);
		frame.add(orderSummary, BorderLayout.EAST);
		frame.add(buttonPanel, BorderLayout.SOUTH);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				cleanup();
				System.exit(0);
			}
		});
		
		handleResize();
	}

	public static void main(String[] args) {
		DataAPI.open("./data.db");

		new ChefWindow();
	}

	private static void cleanup() {
		DataAPI.close();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
