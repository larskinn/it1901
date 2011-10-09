package ntnu.it1901.gruppe4.ordergui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.db.OrderMaker;

public class OrderWindow implements ActionListener {
	private JFrame frame;
	private ButtonPanel buttonPanel;
	private MenuPanel menuPanel;
	private CustomerPanel customerPanel;
	private OrderHistoryPanel orderHistoryPanel;
	private JPanel currentPanel;
	private OrderSummary orderSummary;
	private ResizeListener resizeListener;
	private OrderMaker orderMaker;
	
	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			//Dynamically resize the western and eastern panels
			currentPanel.setPreferredSize(new Dimension(
					(int)(frame.getWidth() * 0.6666),
							frame.getHeight()));
			
			orderSummary.setPreferredSize(new Dimension(
					(int)(frame.getWidth() * 0.3333),
							frame.getHeight()));	
			
			currentPanel.revalidate();
		}
	}

	public enum View {
		MENU, CUSTOMER, HISTORY;
	}

	public static void main(String[] args) {
		DataAPI.open("./data.db");
		DataAPI.clearDatabase();
		DataAPI.createExampleData();
		
		new OrderWindow();
		DataAPI.close();
	}

	public OrderWindow() {
		frame = new JFrame();
		orderSummary = new OrderSummary();
		menuPanel = new MenuPanel(orderSummary);
		customerPanel = new CustomerPanel(orderSummary);
		orderHistoryPanel = new OrderHistoryPanel();
		buttonPanel = new ButtonPanel(this);
		resizeListener = new ResizeListener();
		orderMaker = new OrderMaker();
		
		orderSummary.addComponentListener(resizeListener);
		menuPanel.addComponentListener(resizeListener);
		customerPanel.addComponentListener(resizeListener);
		orderHistoryPanel.addComponentListener(resizeListener);
		
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout());
		frame.add(orderSummary, BorderLayout.EAST);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		changeView(View.MENU);
		
		frame.setTitle("Bestillingsvindu");
		frame.setLocationRelativeTo(null); //Center the frame
		
		//Change to dispose or close when called from another frame (ie. the splash screen)
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void changeView(View view) {
		//If the frame already has a panel providing its view, remove it
		if (currentPanel != null) {
			frame.remove(currentPanel);
		}
		
		switch (view) {
			case MENU:
				frame.add(menuPanel, BorderLayout.CENTER);
				currentPanel = menuPanel;
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
		currentPanel.revalidate(); //Check if the panel has all its components loaded
		frame.repaint(); //Repaint the frame and all its components
	}
	
	public OrderSummary getOrderSummary() {
		return orderSummary;
	}

	//Fired whenever a button in ButtonPanel is pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton)e.getSource();

		if (src == buttonPanel.menu) {
			changeView(View.MENU);
		}
		else if (src == buttonPanel.customer) {
			changeView(View.CUSTOMER);
		}
		else if (src == buttonPanel.history) {
			changeView(View.HISTORY);
		}
	}
}