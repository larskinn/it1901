package ntnu.it1901.gruppe4.ordergui;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JPanel;

public class OrderMenu extends JPanel {
	private LinkedList<OrderMenuItem> items;
	
	public OrderMenu() {
		items = new LinkedList<OrderMenuItem>();
		
		setBackground(Color.CYAN);
	}
}
