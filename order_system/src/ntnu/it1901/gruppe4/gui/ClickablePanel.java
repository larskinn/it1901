package ntnu.it1901.gruppe4.gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * Expansion of JPanel that adds some fancy style effects for clicking.
 * @author Dimmy
 *
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
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		setHighlight(true);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		setHighlight(false);
	}

}
