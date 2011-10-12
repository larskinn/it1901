package ntnu.it1901.gruppe4.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ntnu.it1901.gruppe4.db.DataAPI;

/**
 * Data class for addresses of customers
 * 
 * @author David M.
 */

@DatabaseTable(tableName = "address")
public class Address {
	@DatabaseField(canBeNull = false, generatedId = true)
	private int idAddress;

	@DatabaseField(canBeNull = false, foreign = true)
	private Customer idCustomer;

	@DatabaseField(useGetSet = true)
	private String addressLine;

	@DatabaseField(useGetSet = true)
	private int postalCode;

	/**
	 * Constructs an address object
	 */
	public Address() {
	}

	/**
	 * Constructs a address object with a dataID and a customer
	 * 
	 * @param customer
	 *            reference to customer object associated with this address
	 * @param addressLine
	 *            Address line
	 * @param postalCode
	 *            Postal code
	 */
	public Address(Customer customer, String addressLine, int postalCode) {
		this.idCustomer = customer;
		this.addressLine = addressLine;
		this.postalCode = postalCode;
	}

	/**
	 * Returns the unique ID used in database
	 * 
	 * @return the unique ID used in database
	 */
	public Customer getIdCustomer() {
		return idCustomer;
	}

	/**
	 * Returns the address line
	 * 
	 * @return the address line
	 */
	public String getAddressLine() {
		return addressLine;
	}

	/**
	 * Returns the postal code
	 * 
	 * @return the postal code
	 */
	public int getPostalCode() {
		return postalCode;
	}

	/**
	 * Changes the address line
	 * 
	 * @param addressLine
	 *            the address line
	 */
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	/**
	 * Changes the postal code
	 * 
	 * @param postalCode
	 *            the postal code
	 */
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * Changes the customer associated with this address
	 * 
	 * @param customer
	 *            a reference to the customer associated with this address
	 */
	public void setIdCustomer(Customer customer) {
		this.idCustomer = customer;
	}

	public int getIdAddress() {
		return idAddress;
	}

	public boolean isValid() {
		if (idCustomer == null)
			return false;
		if (!idCustomer.isValid())
			return false;
		return true;
	}
	
	/**
	 * Persists the Address to the database by updating an existing Address,
	 * or -- if one doesn't exist -- adding a new Address. 
	 */
	public void save() {
		DataAPI.saveAddress(this);
	}
}