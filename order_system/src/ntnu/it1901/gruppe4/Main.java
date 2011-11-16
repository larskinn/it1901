package ntnu.it1901.gruppe4;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.sound.sampled.ReverbType;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.chefgui.ChefWindow;
import ntnu.it1901.gruppe4.db.DBReset;
import ntnu.it1901.gruppe4.deliverygui.DeliveryWindow;
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
 * 
 */
class SplashScreen extends JFrame implements ActionListener {
	private JButton btnOrder;
	private JButton btnChef;
	private JButton btnDelivery;
	private JButton btnReset;
	private JPanel contentPanel;
	private static Image splashImage;
	
	SplashScreen() {
		btnOrder = new JButton();
		btnChef = new JButton();
		btnDelivery = new JButton();
		btnReset = new JButton();
		splashImage = Toolkit.getDefaultToolkit().createImage
				(getClass().getResource("/images/SplashScreen.png"));
		
		contentPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponents(g);

				//Paint the splash image on the background
				g.drawImage(splashImage, 0, 0, splashImage.getWidth(null), splashImage.getHeight(null), null);
			}
		};
		
		setTitle("Gruppe 4 pizza");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		contentPanel.setLayout(new GridLayout(2, 2));
		
		btnOrder.addActionListener(this);
		btnChef.addActionListener(this);
		btnDelivery.addActionListener(this);
		btnReset.addActionListener(this);
		
		btnOrder.setContentAreaFilled(false);
		btnChef.setContentAreaFilled(false);
		btnDelivery.setContentAreaFilled(false);
		btnReset.setContentAreaFilled(false);
		
		contentPanel.add(btnOrder);
		contentPanel.add(btnChef);
		contentPanel.add(btnDelivery);
		add(contentPanel);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.out.print("[Debug] Closing program");
				System.exit(0);
			}
		});
		
		
		int timeout = 0;
		
		//Wait until Java has loaded the splash screen picture
		while(!splashScreenLoaded()) {
			try {
				Thread.sleep(80);
				timeout += 80;
				
				if (timeout > 2000) {
					System.err.println("CRITICAL ERROR: The program timed out while attempting to load 'SplashScreen.png'");
				}
			} catch (InterruptedException e) {}
		}
		
		setSize(splashImage.getWidth(null), splashImage.getHeight(null));
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPanel.revalidate();
		contentPanel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == btnChef) {
			setVisible(false);
			new ChefWindow();
		} 
		else if (src == btnDelivery) {
			setVisible(false);
			new DeliveryWindow();
		} 
		else if (src == btnOrder) {
			setVisible(false);
			new OrderWindow();
		} 
		else if (src == btnReset) {
			int res = JOptionPane.showConfirmDialog(null,
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
	 * Java does not immediately load pictures into RAM.
	 * This method returns whether or not the splash screen has been loaded.
	 * 
	 * @return True if the splash screen has been loaded into RAM.
	 */
	public boolean splashScreenLoaded() {
		return splashImage.getWidth(null) != -1;
	}
}