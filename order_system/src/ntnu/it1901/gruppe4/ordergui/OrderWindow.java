package ntnu.it1901.gruppe4.ordergui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.DataAPI;

public class OrderWindow implements ActionListener {
	private JFrame frame;
	private ButtonPanel buttonPanel;
	private MenuPanel menuPanel;
	private CustomerPanel customerPanel;
	private AddressPanel addressPanel;
	private JPanel currentPanel;

	public enum View {
		MENU, CUSTOMER, ADDRESS;
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
		menuPanel = new MenuPanel(frame);
		customerPanel = new CustomerPanel();
		addressPanel = new AddressPanel();
		buttonPanel = new ButtonPanel(this);
		
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout());
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
			case ADDRESS:
				frame.add(addressPanel, BorderLayout.CENTER);
				currentPanel = addressPanel;
				break;
		}
		currentPanel.revalidate(); //Check if the panel has all its components loaded
		frame.repaint(); //Paint the panel and all its components
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
		else if (src == buttonPanel.address) {
			changeView(View.ADDRESS);
		}
		else if (src == buttonPanel.done) {
			buttonPanel.done.setText("NOT YET IMPLEMENTED");
		}
	}
}
