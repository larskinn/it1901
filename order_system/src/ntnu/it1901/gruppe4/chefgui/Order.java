package ntnu.it1901.gruppe4.chefgui;

import java.awt.Color;
import javax.swing.JTextArea;

import javax.swing.JPanel;

//Gets order from order list, keylistener. Pulls order from database

public class Order extends JPanel {
	public Order() {
		setBackground(Color.DARK_GRAY);
    // Create a text area with some initial text
    JTextArea textarea = new JTextArea("DRSFREAG");

    int rows = 30;
    int cols = 30;
    textarea = new JTextArea("DSGFRAEG", rows, cols);
    
    add(textarea);
    

  }
}




