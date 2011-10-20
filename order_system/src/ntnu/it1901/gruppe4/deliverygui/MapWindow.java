package ntnu.it1901.gruppe4.deliverygui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MapWindow extends JFrame {
	
	static String address = JOptionPane.showInputDialog("Skriv inn en adresse");
	static String staticAddress = address.replace(' ','+');
	
	
	public static void main(String[] args) throws Exception{
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		BufferedImage image = ImageIO.read(new URL("http://maps.google.com/maps/api/staticmap?"+staticAddress+"&size=500x500&markers=color:green|"+staticAddress+"|Munkegata,Trondheim|&sensor=false"));
		JLabel label = new JLabel(new ImageIcon(image));
		panel.add(label);
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
