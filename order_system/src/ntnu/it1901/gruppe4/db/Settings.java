package ntnu.it1901.gruppe4.db;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Static class for managing program settings
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
	 * A map of default settings, to be used when initializing the database
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
	 * @return the sum an order has to exceed to qualify for free delivery
	 */
	public static float getFreeDeliveryLimit() {
		return Float.parseFloat(getConfig(FREE_DELIVERY_LIMIT));
	}

	/**
	 * @param limit
	 *            the sum an order has to exceed to qualify for free delivery
	 */
	public static void setFreeDeliveryLimit(float limit) {
		DataAPI.setConfig(FREE_DELIVERY_LIMIT, Float.toString(limit));
	}
	
	/**
	 * @return the price of delivery
	 */
	public static float getDeliveryFee() {
		return Float.parseFloat(getConfig(DELIVERY_FEE));
	}
	
	/**
	 * @param fee
	 *            the price of delivery
	 */
	public static void setDeliveryFee(float fee) {
		DataAPI.setConfig(DELIVERY_FEE, Float.toString(fee));
	}
	
	/**
	 * @return a float representing the tax (merverdiavgift) as a percentage
	 */
	public static float getTax() {
		return Float.parseFloat(getConfig(TAX));
	}
	
	/**
	 * @param percentage
	 *         a float representing the tax (merverdiavgift) as a percentage
	 */
	public static void setTax(float percentage) {
		DataAPI.setConfig(TAX, Float.toString(percentage));
	}
	
	/**
	 * @return the address of the restaurant
	 */
	public static String getRestaurantAddress() {
		return getConfig(RESTAURANT_ADDRESS);
	}
	
	/**
	 * @param address
	 *         the address of the restaurant
	 */
	public static void setRestaurantAddress(String address) {
		DataAPI.setConfig(RESTAURANT_ADDRESS, address);
	}
	
	/**
	 * @return the name of the restaurant
	 */
	public static String getRestaurantName() {
		return getConfig(RESTAURANT_NAME);
	}
	
	/**
	 * @param name
	 *         the name of the restaurant
	 */
	public static void setRestaurantName(String name) {
		DataAPI.setConfig(RESTAURANT_NAME, name);
	}

	/**
	 * @return the database version number
	 */
	public static int getDBVersion() {
		return Integer.parseInt(getConfig(DB_VERSION));
	}
}
