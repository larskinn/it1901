package ntnu.it1901.gruppe4.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ntnu.it1901.gruppe4.db.Settings;

/**
 * A GUI window for editing the variables in the {@link Settings} class.
 *
 * @author Leo
 * @author Lars
 */
public class ConfigWindow {
	public class ConfigBox extends JTextField {
		private ConfigBox() {
			setFont(Layout.configBoxFont);
			
			//To prevent this component's height from growing
			setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
		}
	}

	private JFrame frame;
	private JPanel container;
	
	/**
	 * Creates a new {@link ConfigWindow}.
	 */
	public ConfigWindow() {
		this(null);
	}
	
	/**
	 * Creates a new {@link ConfigWindow} with its initial location set to the middle of its parent frame.
	 *
	 * @param parentFrame The frame which this frame's location will be set relative to.
	 */
	public ConfigWindow(JFrame parentFrame) {
		frame = new JFrame();
		container = new JPanel();
		
		addComponentsToContainer();

		container.setBorder(Layout.panelPadding);
		frame.add(container);
		frame.setTitle("Valg");
		frame.pack();
		frame.setLocationRelativeTo(parentFrame);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void addComponentsToContainer() {
		container.setLayout(new GridBagLayout());
		
		JButton save = new JButton("Lagre");
		JButton cancel = new JButton("Avbryt");
		
		JLabel deliveryFeePrefix = new JLabel("Fraktpris: ");
		JLabel freeDeliveryLimitPrefix = new JLabel("Gratis frakt : ");
		JLabel taxPrefix = new JLabel("MVA: ");
		JLabel addressPrefix = new JLabel("Vår addresse: ");
		
		/* These boxes are marked as 'final' so they
		can be referenced inside anonymous classes */
		final ConfigBox deliveryFee = new ConfigBox();
		final ConfigBox freeDeliveryLimit = new ConfigBox();
		final ConfigBox tax = new ConfigBox();
		final ConfigBox address = new ConfigBox();
		
		JLabel deliveryFeeSuffix = new JLabel(" kr");
		JLabel freeDeliveryLimitSuffix = new JLabel(" kr");
		JLabel taxSuffix = new JLabel(" %");
		
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
				
				//Close the frame in a clean and safe manner
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
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		
		//Insert the delivery fee information in the first row
		container.add(deliveryFeePrefix, gbc);
		
		gbc.gridx++;
		container.add(deliveryFee, gbc);
		
		gbc.gridx++;
		container.add(deliveryFeeSuffix, gbc);
		
		gbc.gridy++;
		container.add(Box.createVerticalStrut(Layout.spaceAfterConfigBox), gbc);
		
		//Insert the free delivery limit information
		gbc.gridy++;
		gbc.gridx = 0;
		container.add(freeDeliveryLimitPrefix, gbc);
		
		gbc.gridx++;
		container.add(freeDeliveryLimit, gbc);
		
		gbc.gridx++;
		container.add(freeDeliveryLimitSuffix, gbc);
		
		gbc.gridy++;
		container.add(Box.createVerticalStrut(Layout.spaceAfterConfigBox), gbc);
		
		//Insert the tax information
		gbc.gridy++;
		gbc.gridx = 0;
		container.add(taxPrefix, gbc);
		
		gbc.gridx++;
		container.add(tax, gbc);
		
		gbc.gridx++;
		container.add(taxSuffix, gbc);
		
		gbc.gridy++;
		container.add(Box.createVerticalStrut(Layout.spaceAfterConfigBox), gbc);
		
		//Insert the home address information
		gbc.gridy++;
		gbc.gridx = 0;
		container.add(addressPrefix, gbc);
		
		gbc.gridx++;
		container.add(address, gbc);
		
		gbc.gridy++;
		container.add(Box.createVerticalStrut(Layout.spaceAfterConfigBox), gbc);
		
		//Insert buttons
		gbc.gridy++;
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		container.add(cancel, gbc);
		
		gbc.anchor = GridBagConstraints.NORTHEAST;
		container.add(save, gbc);
		
		//Insert the empty error label in the last row
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		container.add(errorMessage, gbc);
		
		deliveryFee.grabFocus();
	}
	
	public void addWindowListener(WindowListener listener) {
		frame.addWindowListener(listener);
	}
}
