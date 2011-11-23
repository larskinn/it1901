package ntnu.it1901.gruppe4.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Data class for program settings.
 * 
 * @author Lars Kinn Ekroll
 */
@DatabaseTable(tableName = "config")
public class Config {
	@DatabaseField(canBeNull = false, id = true)
	private String key;
	@DatabaseField(useGetSet = true, canBeNull = false)
	private String value;
	
	public Config() { }
	
	/**
	 * Constructs a key-value pair for storing a specific program setting.
	 * @param key   the name of the program setting.
	 * @param value the value of the program setting.
	 */
	public Config(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * @return the unique name of the program setting.
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * @param value the new value of this program setting.
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * @return the current value of this program setting.
	 */
	public String getValue() {
		return value;
	}
}
