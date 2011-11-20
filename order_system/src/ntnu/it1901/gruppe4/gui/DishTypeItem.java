package ntnu.it1901.gruppe4.gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import ntnu.it1901.gruppe4.db.DishType;

/**
 * A panel that shows information about a {@link DishType}.
 * <p>
 * These items are meant to be contained within {@link MenuSearchPanel.MenuPanel}.
 * 
 * @author Leo
 */
public class DishTypeItem extends ClickablePanel {
	private DishType type;
	
	/**
	 * Creates a new item containing a {@link DishType} object.
	 * 
	 * @param type {@link DishType} object to be contained within the new {@link DishTypeItem}.
	 */
	public DishTypeItem(DishType type) {
		this.type = type;
		JLabel text = new JLabel(type.toString());
		
		setBorder(Layout.dishTypeItemPadding);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		text.setFont(Layout.itemFont);
		
		add(text);
		
		setMaximumSize(new Dimension(Short.MAX_VALUE, getPreferredSize().height));
	}
	
	/**
	 * Getter for the enclosed {@link DishType} object.
	 * 
	 * @return The {@link DishType} object contained within the new {@link DishTypeItem}.
	 */
	public DishType getDishType() {
		return type;
	}
}
