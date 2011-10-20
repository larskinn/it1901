package ntnu.it1901.gruppe4.gui.ordergui;

import java.awt.Dimension;

import javax.swing.JTextField;

import ntnu.it1901.gruppe4.gui.Layout;

public class SearchBox extends JTextField {
	public SearchBox() {
		setFont(Layout.searchBoxFont);
		
		//To prevent this component's height from growing
		setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
	}
}
