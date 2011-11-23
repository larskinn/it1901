package ntnu.it1901.gruppe4.db;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Static class for managing program settings.
 * 
 * @author Lars Kinn Ekroll
 */
public class Settings {
	/*
	 * Configuration key strings are set here, for convenience
	 */
	private static final String DELIVERY_FEE = "DELIVERY_FEE";
	private static final String FREE_DELIVERY_LIMIT = "FREE_DELIVERY_LIMIT";
	private static final String TAX = "TAX";
	private static final String RESTAURANT_ADDRESS = "RESTAURANT_ADDRESS";
	private static final String RESTAURANT_NAME = "RESTAURANT_NAME";
	private static final String DB_VERSION = "DB_VERSION";

	/**
	 * A map of default settings, to be used when initializing the database.
	 */
	public static final Map<String, String> DEFAULT_SETTINGS;
	static {
		Map<String, String> m = new HashMap<String, String>();
		// Put default configuration key/values here
		m.put(DELIVERY_FEE, "20");
		m.put(FREE_DELIVERY_LIMIT, "500");
		m.put(TAX, "25");
		m.put(RESTAURANT_ADDRESS, "Høgskoleringen 3, Trondheim");
		m.put(RESTAURANT_NAME, "Mario & Luigis pizzeria");
		m.put(DB_VERSION, "4"); // DB Version can only be set here!
		DEFAULT_SETTINGS = Collections.unmodifiableMap(m);
	}
	
	/*
	 * Internal method to avoid NullPointerException when fetching undefined
	 * settings. Only works when the setting is in DEFAULT_SETTINGS.
	 */
	private static String getConfig(String key) {
		String result = DataAPI.getConfig(key);
		if (result == null) {
			return DEFAULT_SETTINGS.get(key);
		}
		return result;
	}
	/**
	 * Returns the sum an {@link Order} has to exceed to qualify for free delivery.
	 * 
	 * @return the sum an {@link Order} has to exceed to qualify for free delivery.
	 */
	public static float getFreeDeliveryLimit() {
		return Float.parseFloat(getConfig(FREE_DELIVERY_LIMIT));
	}

	/**
	 * Sets the sum an {@link Order} has to exceed to qualify for free delivery.
	 * 
	 * @param limit
	 *            the sum.
	 */
	public static void setFreeDeliveryLimit(float limit) {
		DataAPI.setConfig(FREE_DELIVERY_LIMIT, Float.toString(limit));
	}
	
	/**
	 * Returns the price of delivery
	 * 
	 * @return the price of delivery.
	 */
	public static float getDeliveryFee() {
		return Float.parseFloat(getConfig(DELIVERY_FEE));
	}
	
	/**
	 * Sets the price of delivery.
	 * 
	 * @param fee
	 *           the price.
	 */
	public static void setDeliveryFee(float fee) {
		DataAPI.setConfig(DELIVERY_FEE, Float.toString(fee));
	}
	
	/**
	 * Returns a <code>float</code> representing the tax (merverdiavgift) as a percentage.
	 * 
	 * @return a <code>float</code> representing the tax (merverdiavgift) as a percentage.
	 */
	public static float getTax() {
		return Float.parseFloat(getConfig(TAX));
	}
	
	/**
	 * Sets the tax percentage.
	 * 
	 * @param percentage
	 *         a float representing the tax (merverdiavgift) as a percentage.
	 */
	public static void setTax(float percentage) {
		DataAPI.setConfig(TAX, Float.toString(percentage));
	}
	
	/**
	 * Returns the {@link Address} of the restaurant.
	 * 
	 * @return the <code>Address</code> of the restaurant.
	 */
	public static String getRestaurantAddress() {
		return getConfig(RESTAURANT_ADDRESS);
	}
	
	/**
	 * Sets the {@link Address} of the restaurant.
	 * @param address
	 *         the <code>Address</code> of the restaurant.
	 */
	public static void setRestaurantAddress(String address) {
		DataAPI.setConfig(RESTAURANT_ADDRESS, address);
	}
	
	/**
	 * Returns the name of the restaurant.
	 * 
	 * @return the name of the restaurant.
	 */
	public static String getRestaurantName() {
		return getConfig(RESTAURANT_NAME);
	}
	
	/**
	 * Sets the name of the restaurant.
	 * 
	 * @param name
	 *         the name of the restaurant.
	 */
	public static void setRestaurantName(String name) {
		DataAPI.setConfig(RESTAURANT_NAME, name);
	}

	/**
	 * Returns the database version number.
	 * 
	 * @return the database version number.
	 */
	public static int getDBVersion() {
		return Integer.parseInt(getConfig(DB_VERSION));
	}
}
