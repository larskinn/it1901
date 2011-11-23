package ntnu.it1901.gruppe4.gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * Expansion of JPanel that adds makes the panel change colors when clicked.
 * 
 * @author David M.
 */
public class ClickablePanel extends JPanel implements MouseListener {

	private Color unhighlightedBackgroundColor;
	private boolean hasHighlight;
	
	public ClickablePanel() {
		unhighlightedBackgroundColor = super.getBackground();
		setHighlight(false);
		addMouseListener(this);
	}
	
	@Override
	public void setBackground(Color color) {
		unhighlightedBackgroundColor = color;
		if (!hasHighlight) {
			super.setBackground(color);
		}
	}
	
	public void setHighlight(boolean hasHighlight) {
		this.hasHighlight = hasHighlight;
		if (hasHighlight) {
			super.setBackground(Layout.bgColorHighlight);
		}
		else {
			super.setBackground(unhighlightedBackgroundColor);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		setHighlight(true);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		setHighlight(false);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0){}

	@Override
	public void mouseExited(MouseEvent arg0){}
}
