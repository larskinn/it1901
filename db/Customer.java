/**
 * Data class for customers
 *
 * @author  David M.
 */
public class Customer
{
    private int dataID;
    private String vName;
    private int vPhone;
    
    /**
     * Constructs a customer object without a dataID
     *
     * @param name Name of the customer
     * @param phone Phone number
     */
    public Customer(String name, int phone)
    {
        dataID = 0;
        vName = name;
        vPhone = phone;
    }
    
    /**
     * Constructs a customer object with a dataID
     *
     * @param id Unique ID used in database
     * @param name Name of the customer
     * @param phone Phone number
     */
    public Customer(int id, String name, int phone)
    {
        dataID = id;
        vName = name;
        vPhone = phone;
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
     * Returns the unique ID used in database
     *
     * @return the unique ID used in database
     */
    public String getName()
    {
        return vName;
    }
    
    /**
     * Returns the phone number
     *
     * @return the phone number
     */
    public int getPhone()
    {
        return vPhone;
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
     * Changes the name of the customer
     *
     * @param name the name of the customer
     */
    public void setName(String name)
    {
        vName = name;
    }
    
    /**
     * Changes the phone number
     *
     * @param phone the phone number
     */
    public void setPhone(int phone)
    {
        vPhone = phone;
    }
}