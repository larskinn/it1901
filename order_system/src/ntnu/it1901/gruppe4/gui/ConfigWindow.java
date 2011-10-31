package ntnu.it1901.gruppe4.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ntnu.it1901.gruppe4.db.Settings;

public class ConfigWindow {
	public class ConfigBox extends JTextField {
		private ConfigBox() {
			setFont(Layout.configBoxFont);
			
			//To prevent this component's height from growing
			setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
		}
	}

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
		final JLabel taxPrefix = new JLabel("MVA: ");
		final JLabel addressPrefix = new JLabel("Vår addresse: ");
		
		final ConfigBox deliveryFee = new ConfigBox();
		final ConfigBox freeDeliveryLimit = new ConfigBox();
		final ConfigBox tax = new ConfigBox();
		final ConfigBox address = new ConfigBox();
		
		final JLabel deliveryFeeSuffix = new JLabel(" kr");
		final JLabel freeDeliveryLimitSuffix = new JLabel(" kr");
		final JLabel taxSuffix = new JLabel(" %");
		// needs an empty addressSuffix to please the whims of Leo's layout system
		final JLabel addressSuffix = new JLabel(" ");
		
		final JLabel errorMessage = new JLabel(" ");
		
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				float delivery = 0;
				float limit = 0;
				float percentage = 0;
				
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
				
				try {
					String parsableString = tax.getText().replaceAll(",", ".");
					percentage = Float.parseFloat(parsableString);
				}
				catch (NumberFormatException nfe) {
					errorMessage.setText("Spesifisert MVA-prosentsats er ugyldig");
					return;
				}
				
				Settings.setDeliveryFee(delivery);
				Settings.setFreeDeliveryLimit(limit);
				Settings.setTax(percentage);
				Settings.setRestaurantAddress(address.getText());
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		save.setFont(Layout.configTextFont);
		cancel.setFont(Layout.configTextFont);
		
		deliveryFeePrefix.setFont(Layout.configTextFont);
		freeDeliveryLimitPrefix.setFont(Layout.configTextFont);
		taxPrefix.setFont(Layout.configTextFont);
		addressPrefix.setFont(Layout.configTextFont);
		
		deliveryFee.setText(Layout.decimalFormat.format(Settings.getDeliveryFee()));
		freeDeliveryLimit.setText(Layout.decimalFormat.format(Settings.getFreeDeliveryLimit()));
		tax.setText(Layout.decimalFormat.format(Settings.getTax()));
		address.setText(Settings.getRestaurantAddress());
		
		deliveryFeeSuffix.setFont(Layout.configTextFont);
		freeDeliveryLimitSuffix.setFont(Layout.configTextFont);
		taxSuffix.setFont(Layout.configTextFont);
		
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
		
		gbc.gridy++;
		frame.add(addressPrefix, gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		gbc.weightx = 1;
		frame.add(deliveryFee, gbc);
		
		gbc.gridy++;
		frame.add(freeDeliveryLimit, gbc);

		gbc.gridy++;
		frame.add(tax, gbc);
		
		gbc.gridy++;
		frame.add(address, gbc);
		
		gbc.gridx++;
		gbc.gridy = 0;
		gbc.weightx = 0;
		frame.add(deliveryFeeSuffix, gbc);
		
		gbc.gridy++;
		frame.add(freeDeliveryLimitSuffix, gbc);

		gbc.gridy++;
		frame.add(taxSuffix, gbc);
		
		gbc.gridy++;
		frame.add(addressSuffix, gbc);
		
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
