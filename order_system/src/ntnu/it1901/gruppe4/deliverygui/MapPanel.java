package ntnu.it1901.gruppe4.deliverygui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import ntnu.it1901.gruppe4.db.Address;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class is the Panel where the Google Static API Map is implemented. It
 * takes an address given to it by {@link Address} and puts it in the static
 * URL,<br>
 * generating a new picture each time it is called.
 * 
 * @author Morten, Leo
 * 
 */

public class MapPanel extends JPanel {

	String address = "";
	String modAddress = address.replace(' ', '+');
	String newAddress = "|&markers=color:red|" + modAddress + ",Trondheim";

	public MapPanel() {

		String staticAddress = address.replace(' ', '+');
		String newAddress = "|&markers=color:red|" + staticAddress
				+ ",Trondheim";

		BufferedImage image;
		try {
			image = ImageIO
					.read(new URL(
							"http://maps.google.com/maps/api/staticmap?"
									+ staticAddress
									+ ",Trondheim&size=500x500&markers=color:green|Munkegata,Trondheim"
									+ newAddress + "&sensor=false"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			image = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			image = null;
		}
		if (image != null) {
			JLabel label = new JLabel(new ImageIcon(image));
			add(label);
		}
	}

	public void setAddress(Address address) {
		removeAll();
		String staticAddress = address.getAddressLine().replace(' ', '+');
		String markerAddress = "|&markers=color:red|" + staticAddress
				+ ",Trondheim";

		BufferedImage image = null;
		try {
			image = ImageIO
					.read(new URL(
							"http://maps.google.com/maps/api/staticmap?"
									+ staticAddress
									+ ",Trondheim&size=500x500&markers=color:green|Munkegata,Trondheim"
									+ markerAddress + "&sensor=false"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel label = new JLabel(new ImageIcon(image));
		add(label);
		revalidate();
		repaint();
	}

}
