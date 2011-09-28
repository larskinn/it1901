package ntnu.it1901.gruppe4.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Data class for customers
 * 
 * @author David M.
 */
@DatabaseTable(tableName = "customer")
public class Customer {
	@DatabaseField(canBeNull = false, generatedId = true)
	private int idcustomer;

	@DatabaseField(useGetSet = true, canBeNull = false)
	private String name;

	@DatabaseField(useGetSet = true)
	private String phone;

	public Customer() {
	}

	/**
	 * Constructs a customer object without a dataID
	 * 
	 * @param vName
	 *            Name of the customer
	 * @param vPhone
	 *            Phone number
	 */
	public Customer(String vName, String vPhone) {
		name = vName;
		phone = vPhone;
	}

	/**
	 * Returns the unique ID used in database
	 * 
	 * @return the unique ID used in database
	 */
	public int getIdcustomer() {
		return idcustomer;
	}

	/**
	 * Returns the unique ID used in database
	 * 
	 * @return the unique ID used in database
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the phone number
	 * 
	 * @return the phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Changes the name of the customer
	 * 
	 * @param vName
	 *            the name of the customer
	 */
	public void setName(String vName) {
		name = vName;
	}

	/**
	 * Changes the phone number
	 * 
	 * @param vPhone
	 *            the phone number
	 */
	public void setPhone(String vPhone) {
		phone = vPhone;
	}
}