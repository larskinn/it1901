package ntnu.it1901.gruppe4.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Data class for addresses of customers
 * 
 * @author David M.
 */

@DatabaseTable(tableName = "address")
public class Address {
	@DatabaseField(canBeNull = false, generatedId = true)
	private int idaddress;

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
	public Customer getIdcustomer() {
		return idCustomer;
	}

	/**
	 * Returns the address line
	 * 
	 * @return the address line
	 */
	public String getAddressline() {
		return addressLine;
	}

	/**
	 * Returns the postal code
	 * 
	 * @return the postal code
	 */
	public int getPostalcode() {
		return postalCode;
	}

	/**
	 * Changes the address line
	 * 
	 * @param addressLine
	 *            the address line
	 */
	public void setAddressline(String addressLine) {
		this.addressLine = addressLine;
	}

	/**
	 * Changes the postal code
	 * 
	 * @param postalCode
	 *            the postal code
	 */
	public void setPostalcode(int postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * Changes the customer associated with this address
	 * 
	 * @param customer
	 *            a reference to the customer associated with this address
	 */
	public void setIdcustomer(Customer customer) {
		this.idCustomer = customer;
	}

	public int getIdaddress() {
		return idaddress;
	}
}