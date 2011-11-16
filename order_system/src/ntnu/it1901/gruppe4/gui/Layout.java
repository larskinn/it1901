package ntnu.it1901.gruppe4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import ntnu.it1901.gruppe4.gui.ordergui.*;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * A collection of constants used for layout purposes.<br><br>
 * 
 * The intention of this class is to provide every programmer working on the project with<br>
 * an easy interface to edit the layout of the GUI without knowing the complete inner workings of the code.<br><br>
 * 
 * I encourage everyone to play around with these constants as you will, and help me make the layout less Windows 3.1-ish and less grey.
 * 
 * @author Leo
 */
public class Layout {
	/**
	 * The initial size of all windows in the GUI.
	 */
	public static final Dimension initialSize = new Dimension(1024, 768);
	
	/**
	 * The {@link DecimalFormat} used to format every floating point number shown.
	 */
	public static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
	
	/**
	 * The {@link Font} of all text on a {@link CustomerPanelItem}, {@link MenuPanelItem} or {@link OrderHistoryItem}.
	 */
	public static final Font itemFont = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
	/**
	 * The {@link Font} of the description of a {@link MenuPanelItem}.
	 */
	public static final Font itemDescriptionFont = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
	/**
	 * The {@link Font} of all text on a {@link OrderSummaryItem}.
	 */
	public static final Font summaryItemFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	/**
	 * The {@link Font} of the description of a {@link OrderSummaryItem}.
	 */
	public static final Font summaryItemDescriptionFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	/**
	 * The {@link Font} of the summary text, as well as the text on the save button.
	 */
	public static final Font summaryTextFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	/**
	 * The {@link Font} of label and button text in the {@link ConfigWindow}.
	 */
	public static final Font configTextFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	/**
	 * The {@link Font} of all error messages shown.
	 */
	public static final Font errorFont = summaryTextFont;
	/**
	 * The {@link Font} that will be used when entering text into a {@link SearchBox}.
	 */
	public static final Font searchBoxFont = new Font(Font.DIALOG_INPUT, Font.BOLD, 40);
	
	/**
	 * The {@link Font} that will be used when entering text into a {@link ConfigWindow#ConfigBox}.
	 */
	public static final Font configBoxFont = new Font(Font.DIALOG_INPUT, Font.BOLD, 20);

	/**
	 * The foreground {@link Color} of every error message used.
	 */
	public static final Color errorColor = Color.RED;
	/**
	 * The background {@link Color} of a {@link CustomerPanelItem}, {@link MenuPanelItem} or {@link OrderHistoryItem} with an even index.
	 */
	public static final Color bgColor1 = Color.LIGHT_GRAY;
	/**
	 * The background {@link Color} of a {@link CustomerPanelItem}, {@link MenuPanelItem} or {@link OrderHistoryItem} with an odd index.
	 */
	public static final Color bgColor2 = Color.WHITE;
	/**
	 * The background {@link Color} of a {@link OrderSummaryItem} an even index.
	 */
	public static final Color summaryBgColor1 = Color.LIGHT_GRAY;
	/**
	 * The background {@link Color} of a {@link OrderSummaryItem} an odd index.
	 */
	public static final Color summaryBgColor2 = Color.WHITE;

	/**
	 * Number of trailing pixels after a search box.
	 */
	public static final int spaceAfterSearchBox = 10;
	
	/**
	 * Number of trailing pixels each input box in the {@link ConfigWindow}.
	 */
	public static final int spaceAfterConfigBox = 5;
	
	/**
	 * Specifies the density of the components (space between them) in the {@link CustomerPanel} when addding a new customer.
	 */
	public static final double newCustomerDensity = 8;
	/**
	 * Specifies the density of the components (space between them) in the {@link MenuSearchPanel} when addding a new dish.
	 */
	public static final double newDishDensity = 8;
	
	/**
	 * The padding used between the edges of the {@link OrderWindow} and its closest {@link JPanel panels}.
	 */
	public static final Border panelPadding = BorderFactory.createEmptyBorder(10, 5, 10, 3);
	/**
	 * The internal padding used inside every {@link OrderSummaryItem}.
	 */
	public static final Border summaryItemPadding = BorderFactory.createEmptyBorder(5, 2, 5, 2);
	/**
	 * The internal padding used inside every {@link MenuPanelItem}.
	 */
	public static final Border menuItemPadding = BorderFactory.createEmptyBorder(10, 5, 10, 5);
	/**
	 * The internal padding used inside every {@link CustomerPanelItem}.
	 */
	public static final Border customerItemPadding = BorderFactory.createEmptyBorder(10, 5, 10, 5);
	/**
	 * The internal padding used inside every {@link OrderHistoryItem}.
	 */
	public static final Border historyItemPadding = BorderFactory.createEmptyBorder(10, 5, 10, 5);
	/**
	 * The internal padding used inside every {@link DishTypeItem}.
	 */
	public static final Border dishTypeItemPadding = BorderFactory.createEmptyBorder(10, 5, 54, 5);
	
	/**
	 * The relative size of all buttons in the {@link ButtonPanel}.<br><br>
	 * Remember that this size is merely a hint, and the the actual size is relative to the size of the {@link OrderWindow}.
	 */
	public static final Dimension buttonSize = new Dimension(100, 50);
	
	/**
	 * The {@link java.text.DateFormat DateFormat} used to format all dates shown.
	 */
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM-yyyy H:mm");
}