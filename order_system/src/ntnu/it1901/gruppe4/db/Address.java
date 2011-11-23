package ntnu.it1901.gruppe4.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Data class for the addresses of {@link Customer Customers}.
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
	 * Constructs an {@link Address} object.
	 */
	public Address() {
	}

	/**
	 * Constructs an {@link Address} object with a new ID and a {@link Customer}.
	 * 
	 * @param customer
	 *            Reference to <code>Customer</code> object to be associated with this <code>Address</code>.
	 * @param addressLine
	 *            A <code>String</code> containing the address line.
	 * @param postalCode
	 *            The postal code.
	 */
	public Address(Customer customer, String addressLine, int postalCode) {
		this.idCustomer = customer;
		this.addressLine = addressLine;
		this.postalCode = postalCode;
	}

	/**
	 * Returns the unique ID used in database.
	 * 
	 * @return the unique ID used in database.
	 */
	public Customer getIdCustomer() {
		return idCustomer;
	}

	/**
	 * Returns the address line.
	 * 
	 * @return the address line.
	 */
	public String getAddressLine() {
		return addressLine;
	}

	/**
	 * Returns the postal code.
	 * 
	 * @return the postal code.
	 */
	public int getPostalCode() {
		return postalCode;
	}

	/**
	 * Changes the address line.
	 * 
	 * @param addressLine
	 *            the address line.
	 */
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	/**
	 * Changes the postal code.
	 * 
	 * @param postalCode
	 *            the postal code.
	 */
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * Changes the {@link Customer} associated with this {@link Address}.
	 * 
	 * @param customer
	 *            a reference to the <code>Customer</code> associated with this <code>Address</code>.
	 */
	public void setIdCustomer(Customer customer) {
		this.idCustomer = customer;
	}

	/**
	 * Returns the database id of this address.
	 * 
	 * @return the database id of this address.
	 */
	public int getIdAddress() {
		return idAddress;
	}

	/**
	 * Returns <code>true</code> if the {@link Address} is valid. An <code>Address</code> is valid if it is
	 * connected to a <code>Customer</code>, and that <code>Customer</code> is valid.
	 * 
	 * @return <code>true</code> if <code>Address</code> is valid, <code>false</code> if not
	 */
	public boolean isValid() {
		if (idCustomer == null)
			return false;
		if (!idCustomer.isValid())
			return false;
		return true;
	}
	
	/**
	 * Saves the {@link Address} to the database by updating an existing <code>Address</code>,
	 * or -- if one doesn't exist -- adding a new <code>Address</code>. 
	 */
	public void save() {
		DataAPI.saveAddress(this);
	}
}
