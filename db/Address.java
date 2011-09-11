/**
 * Data class for addresses of customers
 *
 * @author  David M.
 */
 public class Address
{
    private int dataID;
    private Customer vCustomer;
    private String vAddressLine;
    private int vPostalCode;
    
    /**
     * Constructs a address object without a dataID and a customer
     *
     * @param addrline Address line
     * @param postalcode Postal code
     */
    public Address(String addrline, int postalcode)
    {
        dataID = 0;
        vCustomer = null;
        vAddressLine = addrline;
        vPostalCode = postalcode;
    }
    
    /**
     * Constructs a address object with a dataID and a customer
     *
     * @param id unique ID used in database
     * @param customer reference to customer object associated with this address
     * @param addrline Address line
     * @param postalcode Postal code
     */
    public Address(int id, Customer customer, String addrline, int postalcode)
    {
        dataID = id;
        vCustomer = customer;
        vAddressLine = addrline;
        vPostalCode = postalcode;
    }
    
    /**
     * Returns the unique ID used in database
     *
     * @return the unique ID used in database
     */
    public int getDataID()
    {
        return dataID;
    }
    
    /**
     * Returns the address line
     *
     * @return the address line
     */
    public String getAddressLine()
    {
        return vAddressLine;
    }
    
    /**
     * Returns the postal code
     *
     * @return the postal code
     */
    public int getPostalCode()
    {
        return vPostalCode;
    }
    
    /**
     * Returns a reference to the customer associated with this address
     *
     * @return a reference to the customer associated with this address
     */
    public Customer getCustomer()
    {
        return vCustomer;
    }
    
    /**
     * Changes the unique ID used in database
     *
     * @param id the unique ID used in database
     */
    public void setDataID(int id)
    {
        dataID = id;
    }
    
    /**
     * Changes the address line
     *
     * @param addrline the address line
     */
    public void setAddressLine(String addrline)
    {
        vAddressLine = addrline;
    }
    
    /**
     * Changes the postal code
     *
     * @param postalcode the postal code
     */
    public void setPostalCode(int postalcode)
    {
        vPostalCode = postalcode;
    }
    
    /**
     * Changes the customer associated with this address
     *
     * @param customer a reference to the customer associated with this address
     */
    public void setCustomer(Customer customer)
    {
        vCustomer = customer;
    }
}