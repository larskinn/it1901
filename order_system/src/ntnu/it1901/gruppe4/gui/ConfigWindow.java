package ntnu.it1901.gruppe4.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ntnu.it1901.gruppe4.db.Settings;
import ntnu.it1901.gruppe4.gui.ordergui.SearchBox;

public class ConfigWindow {
	private JFrame frame;
	
	public ConfigWindow() {
		this(null);
	}
	
	public ConfigWindow(JFrame parentFrame) {
		frame = new JFrame();
		
		addComponents();

		frame.setSize(Layout.configWindowSize);
		frame.setTitle("Valg");
		frame.setLocationRelativeTo(parentFrame);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void addComponents() {
		frame.setLayout(new GridBagLayout());
		
		final JButton save = new JButton("Lagre");
		final JButton cancel = new JButton("Avbryt");
		
		final JLabel deliveryFeePrefix = new JLabel("Fraktpris: ");
		final JLabel freeDeliveryLimitPrefix = new JLabel("Gratis frakt : ");
		final JLabel taxPrefix = new JLabel("MVA (ikke imp.): ");
		
		final SearchBox deliveryFee = new SearchBox();
		final SearchBox freeDeliveryLimit = new SearchBox();
		final SearchBox tax = new SearchBox();
		
		final JLabel deliveryFeeSuffix = new JLabel(" kr");
		final JLabel freeDeliveryLimitSuffix = new JLabel(" kr");
		final JLabel taxSuffix = new JLabel(" %");
		
		final JLabel errorMessage = new JLabel(" ");
		
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				float delivery = 0;
				float limit = 0;
				
				try {
					//Use dot as decimal seperator instead of comma
					String parsableString = deliveryFee.getText().replaceAll(",", ".");
					
					delivery = Float.parseFloat(parsableString);
				}
				catch (NumberFormatException nfe) {
					errorMessage.setText("Spesifisert fraktpris er ugyldig");
					return;
				}
				
				try {
					String parsableString = freeDeliveryLimit.getText().replaceAll(",", ".");
					
					limit = Float.parseFloat(parsableString); 
				}
				catch (NumberFormatException nfe) {
					errorMessage.setText("Spesifisert grense for gratis frakt er ugyldig");
					return;
				}
				
				Settings.setDeliveryFee(delivery);
				Settings.setFreeDeliveryLimit(limit);
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		save.setFont(Layout.summaryTextFont);
		cancel.setFont(Layout.summaryTextFont);
		
		deliveryFeePrefix.setFont(Layout.summaryTextFont);
		freeDeliveryLimitPrefix.setFont(Layout.summaryTextFont);
		taxPrefix.setFont(Layout.summaryTextFont);
		
		deliveryFee.setText(Layout.decimalFormat.format(Settings.getDeliveryFee()));
		freeDeliveryLimit.setText(Layout.decimalFormat.format(Settings.getFreeDeliveryLimit()));
		
		deliveryFeeSuffix.setFont(Layout.summaryTextFont);
		freeDeliveryLimitSuffix.setFont(Layout.summaryTextFont);
		taxSuffix.setFont(Layout.summaryTextFont);
		
		errorMessage.setFont(Layout.errorFont);
		errorMessage.setForeground(Layout.errorColor);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		frame.add(deliveryFeePrefix, gbc);
		
		gbc.gridy++;
		frame.add(freeDeliveryLimitPrefix, gbc);
		
		gbc.gridy++;
		frame.add(taxPrefix, gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		gbc.weightx = 1;
		frame.add(deliveryFee, gbc);
		
		gbc.gridy++;
		frame.add(freeDeliveryLimit, gbc);

		gbc.gridy++;
		frame.add(tax, gbc);
		
		gbc.gridx++;
		gbc.gridy = 0;
		gbc.weightx = 0;
		frame.add(deliveryFeeSuffix, gbc);
		
		gbc.gridy++;
		frame.add(freeDeliveryLimitSuffix, gbc);

		gbc.gridy++;
		frame.add(taxSuffix, gbc);
		
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 1;
		gbc.gridx--;
		gbc.gridy++;
		frame.add(cancel, gbc);
		
		gbc.anchor = GridBagConstraints.NORTHEAST;
		frame.add(save, gbc);
		
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridy++;
		frame.add(errorMessage, gbc);
		
		deliveryFee.grabFocus();
	}
	
	public void addWindowListener(WindowListener listener) {
		frame.addWindowListener(listener);
	}
}
