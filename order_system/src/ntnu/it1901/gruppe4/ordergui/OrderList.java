package ntnu.it1901.gruppe4.ordergui;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JPanel;

public class OrderList extends JPanel {
	private LinkedList<OrderListEntry> entries;
	
	public OrderList() {
		setBackground(Color.BLUE);
		
		entries = new LinkedList<OrderListEntry>();
	}
}