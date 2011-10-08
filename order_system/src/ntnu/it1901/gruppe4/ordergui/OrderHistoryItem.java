package ntnu.it1901.gruppe4.ordergui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OrderHistoryItem extends JPanel {
	public OrderHistoryItem() {
		add(new JLabel("Under Construction"));
		
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Clicky!");
			}
		});
	}
}
