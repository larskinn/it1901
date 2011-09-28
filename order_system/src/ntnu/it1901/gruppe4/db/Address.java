package ntnu.it1901.gruppe4.db;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Data class for addresses of customers
 *
 * @author  David M.
 */

 @DatabaseTable(tableName = "address")
 public class Address
{
    @DatabaseField(canBeNull = false, generatedId = true)
    private int idaddress;
    
    @DatabaseField(canBeNull = false, foreign = true)
    private Customer idcustomer;
    
    @DatabaseField(useGetSet = true)
    private String addressline;
    
    @DatabaseField(useGetSet = true)
    private int postalcode;
    
    /**
     * Constructs an address object
     */
    public Address()
    {
    }
    
    /**
     * Constructs a address object with a dataID and a customer
     *
     * @param vCustomer reference to customer object associated with this address
     * @param vAddressLine Address line
     * @param vPostalCode Postal code
     */
    public Address(Customer vCustomer, String vAddressLine, int vPostalCode)
    {
        idcustomer = vCustomer;
        addressline = vAddressLine;
        postalcode = vPostalCode;
    }
    
    /**
     * Returns the unique ID used in database
     *
     * @return the unique ID used in database
     */
    public Customer getIdcustomer()
    {
        return idcustomer;
    }
    
    /**
     * Returns the address line
     *
     * @return the address line
     */
    public String getAddressline()
    {
        return addressline;
    }
    
    /**
     * Returns the postal code
     *
     * @return the postal code
     */
    public int getPostalcode()
    {
        return postalcode;
    }
    
    /**
     * Changes the address line
     *
     * @param vAddressLine the address line
     */
    public void setAddressline(String vAddressLine)
    {
        addressline = vAddressLine;
    }
    
    /**
     * Changes the postal code
     *
     * @param vPostalCode the postal code
     */
    public void setPostalcode(int vPostalCode)
    {
        postalcode = vPostalCode;
    }
    
    /**
     * Changes the customer associated with this address
     *
     * @param vCustomer a reference to the customer associated with this address
     */
    public void setIdcustomer(Customer vCustomer)
    {
        idcustomer = vCustomer;
    }
    
    public int getIdaddress()
    {
    	return idaddress;
    }
}