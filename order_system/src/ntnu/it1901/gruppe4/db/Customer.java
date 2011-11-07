package ntnu.it1901.gruppe4.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ntnu.it1901.gruppe4.db.DataAPI;

/**
 * Data class for customers
 * 
 * @author David M.
 */
@DatabaseTable(tableName = "customer")
public class Customer {
	@DatabaseField(canBeNull = false, generatedId = true)
	private int idCustomer;

	@DatabaseField(useGetSet = true, canBeNull = false)
	private String name;

	@DatabaseField(useGetSet = true)
	private String phone;

	public Customer() {
	}

	/**
	 * Constructs a customer object without a dataID
	 * 
	 * @param name
	 *            Name of the customer
	 * @param phone
	 *            Phone number
	 */
	public Customer(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}

	/**
	 * Returns the unique ID used in database
	 * 
	 * @return the unique ID used in database
	 */
	public int getIdCustomer() {
		return idCustomer;
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
	 * @param name
	 *            the name of the customer
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Changes the phone number
	 * 
	 * @param phone
	 *            the phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Returns TRUE if this customer is valid
	 * 
	 * @return TRUE if this customer is valid
	 */
	public boolean isValid() {
		return true;
	}

	/**
	 * Persists the Customer to the database by updating an existing Customer,
	 * or -- if one doesn't exist -- adding a new Customer.
	 */
	public void save() {
		DataAPI.saveCustomer(this);
	}

	/**
	 * Completely removes the customer from the database, including their
	 * addresses.
	 */
	public void remove() {
		DataAPI.remAddresses(this);
		DataAPI.remCustomer(this);
	}
}
