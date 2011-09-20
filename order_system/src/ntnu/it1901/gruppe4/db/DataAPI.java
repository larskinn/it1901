package ntnu.it1901.gruppe4.db;
import java.sql.*;
/**
 * API to communicate with pizza server
 *
 * @author  David M.
 */
public class DataAPI
{
    private Connection conn = null;
    
    /**
     * Opens a connection to a database
     */
    public void open(String username, String password, String url)
    {
        conn = null;
 
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (Exception e)
        {
            if (conn != null)
            {
                try{
                    conn.close();
                }
                catch (SQLException e2){}
                finally{
                    conn = null;
                }
            }
        }
    }
    
    /**
     * Closes the connection to the database
     */
    public void close()
    {
        if (conn != null)
        {
            try{
                conn.close();
            }
            catch (SQLException e){}
            finally{
                conn = null;
            }
        }
    }
    
    /**
     * Returns a Statement that can be used to query the database
     *
     * @return a Statement-object from the Connector/J library
     */
    public Statement createStatement() throws SQLException
    {
        if (conn == null) return null;
        else return conn.createStatement();
    }
    
    //  Customer
    
    /**
     * Stores a new customer to the database
     *
     * @param c a reference to the Customer object containing the data to be stored
     */
    public void addCustomer(Customer c)
    {
        try
        {
            String sql = "INSERT INTO customer (name, phone) VALUES (?, ?)";
            PreparedStatement s = conn.prepareStatement(sql);
            s.setString(1, c.getName());
            s.setInt(2, c.getPhone());
            if (s.executeUpdate() == 1)
            {
                Statement getID = conn.createStatement();
                ResultSet rs = getID.executeQuery("SELECT LAST_INSERT_ID()");
                rs.first();
                c.setDataID(rs.getInt(1));
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error storing customer: "+e.getMessage());
        }
    }
    
    /**
     * Fetches customer data and stores it in a Customer object
     *
     * @param id unique ID used to identify a customer in the database (customerid)
     * @return a reference to a new Customer object containing the data
     */
    public Customer getCustomer(int id)
    {
        try
        {
            if (id == 0) return null;
            String sql = "SELECT name, phone FROM customer WHERE idcustomer=?";
            PreparedStatement s = conn.prepareStatement(sql);
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            
            rs.first();
            String name = rs.getString(1);
            int phone = rs.getInt(2);
            
            return new Customer(id, name, phone);
        }
        catch (SQLException e)
        {
            System.err.println("Error fetching customer: "+e.getMessage());
            return null;
        }
    }
    
    //  Address
    
    /**
     * Stores a new address to the database
     *
     * @param a a reference to the Address object containing the data to be stored
     */
    public void addAddress(Address a)
    {
        try
        {
            String sql = "INSERT INTO address (idcustomer, addressline, postalcode) VALUES (?, ?, ?)";
            PreparedStatement s = conn.prepareStatement(sql);
            s.setInt(1, a.getCustomer().getDataID());
            s.setString(2, a.getAddressLine());
            s.setInt(3, a.getPostalCode());
            if (s.executeUpdate() == 1)
            {
                Statement getID = conn.createStatement();
                ResultSet rs = getID.executeQuery("SELECT LAST_INSERT_ID()");
                rs.first();
                a.setDataID(rs.getInt(1));
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error storing address: "+e.getMessage());
        }
    }
    
    /**
     * Fetches address data and stores it in a Address object
     *
     * @param a unique ID used to identify an address in the database (addressid)
     * @return a reference to a new Address object containing the data
     */
    public Address getAddress(int id)
    {
        try
        {
            if (id == 0) return null;
            String sql = "SELECT idcustomer, addressline, postalcode FROM address WHERE idaddress=?";
            PreparedStatement s = conn.prepareStatement(sql);
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            
            rs.first();
            int customerID = rs.getInt(1);
            Customer customer = getCustomer(customerID);
            String addrline = rs.getString(2);
            int postalcode = rs.getInt(3);
            
            return new Address(id, customer, addrline, postalcode);
        }
        catch (SQLException e)
        {
            System.err.println("Error fetching address: "+e.getMessage());
            return null;
        }
    }
}
