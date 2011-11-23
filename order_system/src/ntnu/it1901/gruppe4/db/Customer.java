package ntnu.it1901.gruppe4.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Data class for customers.
 * 
 * @author David M.
 */
@DatabaseTable(tableName = "customer")
public class Customer implements Comparable<Customer> {
	@DatabaseField(canBeNull = false, generatedId = true)
	private int idCustomer;

	@DatabaseField(useGetSet = true, canBeNull = false)
	private String name;

	@DatabaseField(useGetSet = true)
	private String phone;

	public Customer() {
	}

	/**
	 * Constructs a {@link Customer} object without a dataID.
	 * 
	 * @param name
	 *            Name of the <code>Customer</code>.
	 * @param phone
	 *            Phone number.
	 */
	public Customer(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}

	/**
	 * Returns the unique ID used in database.
	 * 
	 * @return the unique ID used in database.
	 */
	public int getIdCustomer() {
		return idCustomer;
	}

	/**
	 * Returns the unique ID used in database.
	 * 
	 * @return the unique ID used in database.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the phone number.
	 * 
	 * @return the phone number.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Changes the name of the <code>Customer</code>.
	 * 
	 * @param name
	 *            the name of the <code>Customer</code>.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Changes the phone number.
	 * 
	 * @param phone
	 *            the phone number.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Returns <code>true</code> if this <code>Customer</code> is valid.
	 * 
	 * @return <code>true</code> if this <code>Customer</code> is valid.
	 */
	public boolean isValid() {
		return true;
	}

	/**
	 * Saves the {@link Customer} to the database by updating an existing <code>Customer</code>,
	 * or -- if one doesn't exist -- adding a new <code>Customer</code>.
	 */
	public void save() {
		DataAPI.saveCustomer(this);
	}

	/**
	 * Completely removes the <code>Customer</code> from the database, including their 
	 * {@link Address Addresses}.
	 */
	public void remove() {
		DataAPI.remAddresses(this);
		DataAPI.remCustomer(this);
	}
	
	/**
	 * Returns the name of the {@link Customer} sortable by last name.
	 * <p>
	 * If the name is "First Second Third", the sortable name is "Third, First Second".
	 * 
	 * @return the <code>Customer's</code> name sortable by last name.
	 */
	public String getSortName() {
		String name = this.name.trim();
		int lastSpace = name.lastIndexOf(' ');
		if (lastSpace == -1) {
			// In this case, the original name was an empty string, only whitespace, or one word long
			return name;
		} else {
			return name.substring(lastSpace+1, name.length()) + ", " + name.substring(0, lastSpace);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Customer c) {
		return getSortName().compareTo(c.getSortName());
	}

}
