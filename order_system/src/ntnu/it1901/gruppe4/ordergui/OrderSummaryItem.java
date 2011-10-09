package ntnu.it1901.gruppe4.ordergui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ntnu.it1901.gruppe4.db.OrderItem;

public class OrderSummaryItem extends JPanel {
	private DecimalFormat twoDecimals = new DecimalFormat("0.00");

	public OrderSummaryItem(OrderItem item) {
		GridBagConstraints gbc = new  GridBagConstraints();
		setLayout(new GridBagLayout());
		
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));
		
		gbc.gridy = 0;
		add(Box.createVerticalStrut(5), gbc); //Add some vertical space
		
		gbc.gridy = 1;
		gbc.weightx = 0.25;
		gbc.weighty = 0.25;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		add(new JLabel(item.getName()), gbc);
		
		gbc.anchor = GridBagConstraints.NORTHEAST;
		add(new JLabel(twoDecimals.format(item.getAmount()) + " kr"), gbc);
		
		gbc.gridy = 2;
		add(Box.createVerticalStrut(5), gbc);
	}
	
}
