package ntnu.it1901.gruppe4;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.sound.sampled.ReverbType;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.chefgui.ChefWindow;
import ntnu.it1901.gruppe4.db.DBReset;
import ntnu.it1901.gruppe4.db.DataAPI;
import ntnu.it1901.gruppe4.deliverygui.DeliveryWindow;
import ntnu.it1901.gruppe4.gui.ConfigWindow;
import ntnu.it1901.gruppe4.gui.ordergui.OrderWindow;

/**
 * The main entry point of the program.
 * 
 * @author David M.
 * @author Leo
 * 
 */
public class Main {
	private static SplashScreen splashScreen;

	public static void main(String[] args) {
		splashScreen = new SplashScreen();
	}

	public static void showSplash() {
		splashScreen.setVisible(true);
	}

	public static void hideSplash() {
		splashScreen.setVisible(false);
	}
}

/**
 * The main menu (splash screen) of the program.
 * 
 * @author David M.
 * @author Leo
 * @author Morten
 */
class SplashScreen extends JFrame implements ActionListener {
	private JButton btnOrder;
	private JButton btnChef;
	private JButton btnDelivery;
	private JButton btnReset;
	private JLabel lblMadeBy;

	SplashScreen() {
		Icon iconOrder = new ImageIcon(getClass().getResource(
				"/images/SplashScreenNW.png"));
		Icon iconChef = new ImageIcon(getClass().getResource(
				"/images/SplashScreenNE.png"));
		Icon iconDelivery = new ImageIcon(getClass().getResource(
				"/images/SplashScreenSW.png"));
		Icon iconMadeBy = new ImageIcon(getClass().getResource(
				"/images/SplashScreenSE.png"));
		btnOrder = new JButton(iconOrder);
		btnChef = new JButton(iconChef);
		btnDelivery = new JButton(iconDelivery);
		lblMadeBy = new JLabel(iconMadeBy);
		btnReset = new JButton();

		setTitle("Gruppe 4 pizza");
		setResizable(false);
		setLayout(new GridLayout(2, 2));

		btnOrder.addActionListener(this);
		btnChef.addActionListener(this);
		btnDelivery.addActionListener(this);
		btnReset.addActionListener(this);

		// We want the buttons to have the exact size of their contained images
		btnOrder.setPreferredSize(new Dimension(iconOrder.getIconWidth(),
				iconOrder.getIconHeight()));
		btnChef.setPreferredSize(new Dimension(iconChef.getIconWidth(),
				iconChef.getIconHeight()));
		btnDelivery.setPreferredSize(new Dimension(iconDelivery.getIconWidth(),
				iconDelivery.getIconHeight()));
		lblMadeBy.setPreferredSize(new Dimension(iconMadeBy.getIconWidth(),
				iconMadeBy.getIconHeight()));

		// A menu that will open the config window when pressed
		JMenu settings = new JMenu("Innstillinger");
		settings.setOpaque(false);

		settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DataAPI.open();
				ConfigWindow configWindow = new ConfigWindow(SplashScreen.this);
				setEnabled(false);

				// When the config window is closed, re-enable the splash screen

				configWindow.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						DataAPI.close();
						setEnabled(true);
						setVisible(true);	//	Give focus
					}
				});
			}
		});

		// A menu that will reset the database when pressed
		JMenu reset = new JMenu("Nullstill database");
		reset.setOpaque(false);

		reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int response = JOptionPane.showConfirmDialog(null,
						"Sett inn eksempeldata?");

				if (response == JOptionPane.YES_OPTION) {
					DBReset.resetDB();
					DBReset.createExampleData();
				} else if (response == JOptionPane.NO_OPTION) {
					DBReset.resetDB();
				}
				// The cancel button does nothing
			}
		});

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(settings);
		menuBar.add(reset);
		setJMenuBar(menuBar);

		add(btnOrder);
		add(btnChef);
		add(btnDelivery);
		add(lblMadeBy);
		pack();

		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.out.print("[Debug] Closing program");
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == btnChef) {
			setVisible(false);
			new ChefWindow();
		} else if (src == btnDelivery) {
			setVisible(false);
			new DeliveryWindow();
		} else if (src == btnOrder) {
			setVisible(false);
			new OrderWindow();
		}
	}
}