package ntnu.it1901.gruppe4.gui;

import java.awt.Dimension;

import javax.swing.JTextField;

/**
 * A general text field with a large, bold font.
 * Its width is unlimited and its height is set to that of its font's.
 * 
 * @author Leo
 */
public class SearchBox extends JTextField {
	public SearchBox() {
		setFont(Layout.searchBoxFont);
		setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
	}
}
