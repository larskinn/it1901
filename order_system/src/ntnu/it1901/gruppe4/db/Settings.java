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
	private static final String FREE_DELIVERY_LIMIT = "FREE_DELIVERY_LIMIT";
	private static final String DB_VERSION = "DB_VERSION";
	
	/**
	 * A map of default settings, to be used when initializing the database
	 */
	public static final Map<String, String> DEFAULT_SETTINGS;
	static {
		Map<String, String> m = new HashMap<String, String>();
		// Put default configuration key/values here
		m.put(FREE_DELIVERY_LIMIT, 	"500");
		m.put(DB_VERSION, 			"1"); // DB Version can only be set here!
		DEFAULT_SETTINGS = Collections.unmodifiableMap(m);
	}
	
	/**
	 * @return the sum an order has to exceed to qualify for free delivery
	 */
	public static int getFreeDeliveryLimit() {
		return Integer.parseInt(DataAPI.getConfig(FREE_DELIVERY_LIMIT));
	}
	
	/**
	 * @param limit the sum an order has to exceed to qualify for free delivery
	 */
	public static void setFreeDeliveryLimit(int limit) {
		DataAPI.setConfig(FREE_DELIVERY_LIMIT, Integer.toString(limit));
	}
	
	/**
	 * @return the database version number
	 */
	public static int getDBVersion() {
		return Integer.parseInt(DataAPI.getConfig(DB_VERSION));
	}
}
