package ntnu.it1901.gruppe4;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ntnu.it1901.gruppe4.chefgui.ChefWindow;
import ntnu.it1901.gruppe4.db.DBReset;
import ntnu.it1901.gruppe4.deliverygui.DeliveryWindow;
import ntnu.it1901.gruppe4.gui.ordergui.OrderWindow;

/**
 * The main entry point of the program, and main menu.
 * 
 * @author David M.
 * 
 */

public class Main extends JFrame implements ActionListener {

	private JButton btnOrder;
	private JButton btnChef;
	private JButton btnDelivery;

	private JButton btnReset;

	private static Main splash;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		splash = new Main();
	}

	private Main() {
		btnOrder = new JButton("Bestilling");
		btnChef = new JButton("Kokk / Menyredigering");
		btnDelivery = new JButton("Henting / Levering");

		btnReset = new JButton("Resett database");

		JLabel lblTitle = new JLabel("<html><h1>Gruppe 4 Pizza</h1></html>");

		Dimension dimBigButton = new Dimension(300, 50);

		btnOrder.setMinimumSize(dimBigButton);
		btnChef.setMinimumSize(dimBigButton);
		btnDelivery.setMinimumSize(dimBigButton);

		btnOrder.addActionListener(this);
		btnChef.addActionListener(this);
		btnDelivery.addActionListener(this);
		btnReset.addActionListener(this);

		setMinimumSize(new Dimension(350, 300));

		setLayout(new GridLayout(0, 1));

		add(lblTitle);
		add(btnOrder);
		add(btnChef);
		add(btnDelivery);
		add(btnReset);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.out.print("[Debug] Closing program");
				System.exit(0);
			}
		});
		setLocationRelativeTo(null);
		setTitle("Gruppe 4 pizza");
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object src = arg0.getSource();

		if (src == btnChef) {
			hideSplash();
			new ChefWindow();
		} else if (src == btnDelivery) {
			hideSplash();
			new DeliveryWindow();
		} else if (src == btnOrder) {
			hideSplash();
			new OrderWindow();
		} else if (src == btnReset) {

			int res = JOptionPane.showConfirmDialog(splash,
					"Sett inn eksempeldata?");

			if (res == JOptionPane.YES_OPTION) {
				DBReset.resetDB();
				DBReset.createExampleData();
			} else if (res == JOptionPane.NO_OPTION) {
				DBReset.resetDB();
			}
		}
	}

	/**
	 * Show the splash screen.
	 */
	public static void showSplash() {
		splash.setVisible(true);
	}

	/**
	 * Hide the splash screen.
	 */
	public static void hideSplash() {
		splash.setVisible(false);
	}
}
