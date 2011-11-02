package ntnu.it1901.gruppe4.chefgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.gui.ConfigWindow;
import ntnu.it1901.gruppe4.gui.Layout;
import ntnu.it1901.gruppe4.gui.MenuSearchPanel;
import ntnu.it1901.gruppe4.gui.OrderHistoryPanel;

public class ChefWindow implements ActionListener {
	private JFrame frame;
	private ButtonPanel buttonPanel;
	private ChefOrderSummary chefOrderSummary;
	private OrderHistoryPanel orderHistoryPanel;
	private MenuSearchPanel menuPanel;
	private JPanel currentPanel;
	private ResizeListener resizeListener;

	private enum View {
		ORDERS, MENU;
	}

	private void handleResize()
	{
		orderHistoryPanel.setPreferredSize(new Dimension(
				(int) (frame.getWidth() * 0.6666), frame.getHeight()));

		chefOrderSummary.setPreferredSize(new Dimension(
				(int) (frame.getWidth() * 0.3333), frame.getHeight()));

		orderHistoryPanel.revalidate();
		chefOrderSummary.revalidate();
	}
	
	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			handleResize();
		}
	}
	
	public ChefWindow() {
		buttonPanel = new ButtonPanel(this);
		chefOrderSummary = new ChefOrderSummary();
		orderHistoryPanel = new OrderHistoryPanel(OrderHistoryPanel.Mode.CHEF, chefOrderSummary);
		menuPanel = new MenuSearchPanel(MenuSearchPanel.Mode.CHEF);
		
		chefOrderSummary.setOrderHistoryPanel(orderHistoryPanel);
		resizeListener = new ResizeListener();

		orderHistoryPanel.addComponentListener(resizeListener);
		chefOrderSummary.addComponentListener(resizeListener);

		frame = new JFrame("Kokkevindu");
		frame.setSize(Layout.initialSize);
		frame.setLayout(new BorderLayout());
		frame.add(chefOrderSummary, BorderLayout.EAST);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		changeView(View.ORDERS);

		//Adds a menu bar that will open a new config window when pressed
		JMenu menu = new JMenu("Valg");
		menu.setOpaque(false);
		
		//Fired when the menu in the menu bar is clicked
		menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ConfigWindow configWindow = new ConfigWindow(frame);
				frame.setEnabled(false);
				
				//When the config window is closed, enable the parent frame
				configWindow.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						frame.setEnabled(true);
					}
				});
			}
		});
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(frame.getBackground());
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				cleanup();
				System.exit(0);
			}
		});
		
		frame.setSize(Layout.initialSize);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		handleResize();
	}

	public static void main(String[] args) {
		DataAPI.open("./data.db");

		new ChefWindow();
	}

	private static void cleanup() {
		DataAPI.close();
	}
	
	public void changeView(View view) {
		// If the frame already has a panel providing its view, remove it
		if (currentPanel != null) {
			frame.remove(currentPanel);
		}

		switch (view) {
			case ORDERS:
				frame.add(orderHistoryPanel, BorderLayout.CENTER);
				currentPanel = orderHistoryPanel;
				break;
			case MENU:
				frame.add(menuPanel, BorderLayout.CENTER);
				currentPanel = menuPanel;
				break;
		}
		currentPanel.grabFocus(); // This method should be overrided to pass on focus to the search box
		currentPanel.revalidate(); // Check if the panel has all its components loaded
		frame.repaint(); // Repaint the frame and all its components
	}

	// Fired whenever a button in ButtonPanel is pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton)e.getSource();

		if (src == buttonPanel.orders) {
			changeView(View.ORDERS);
		} else if (src == buttonPanel.menu) {
			changeView(View.MENU);
		}
	}
}
