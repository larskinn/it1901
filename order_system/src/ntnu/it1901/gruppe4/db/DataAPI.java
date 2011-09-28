package ntnu.it1901.gruppe4.db;

import java.sql.*;
import java.util.List;
import java.lang.Exception;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * API to communicate with pizza server
 *
 * @author  David M.
 */
public class DataAPI
{
	private Dao<Customer, Integer> customerDao;
	private Dao<Address, Integer> addressDao;

    private JdbcConnectionSource conn = null;
    
    private String url = "";

    /**
     * Creates a new DataAPI object
     *
     * @param file The file that contains the sqlite database
     */
    public DataAPI(String file)
    {
    	url = "jdbc:sqlite:"+file;
    }
    
    /**
     * Opens a connection to a database
     */
    public void open()
    {
    	//String url = "jdbc:sqlite:./data.db";

        System.out.println("[Debug] Opening database "+url+"...");
        
    	try
        {
            //Class.forName("org.sqlite.JDBC");  
            conn = new JdbcConnectionSource(url);
            
            if (conn == null) throw new Exception("Failed to connect to database.");
            
            setupDatabase();
        }
    	catch (Exception e)
        {
            System.err.println("[Error] Failed to open database connection: "+e.getMessage());
        }
    }

    /**
     * Sets up the database
     */
    private void setupDatabase() throws SQLException
    {
    	if (conn != null)
    	{
            System.out.println("[Debug] Initializing tables...");
            
	        customerDao = DaoManager.createDao(conn, Customer.class);
	        addressDao = DaoManager.createDao(conn, Address.class);

	        TableUtils.createTableIfNotExists(conn, Customer.class);
	        TableUtils.createTableIfNotExists(conn, Address.class);
    	}
    	else
    	{
            System.err.println("[Error] Tried to setup database without a connection");
    	}
    }
    
    /**
     * Closes the connection to the database
     */
    public void close()
    {
        System.out.println("[Debug] Closing database");
        
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
            customerDao.create(c);
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
            return customerDao.queryForId(id);
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
            addressDao.create(a);
        }
        catch (SQLException e)
        {
            System.err.println("Error storing address: "+e.getMessage());
        }
    }
    
    /**
     * Fetches address data and stores it in a Address object
     *
     * @param id a unique ID used to identify an address in the database (addressid)
     * @return a reference to a new Address object containing the data
     */
    public Address getAddress(int id)
    {
        try
        {
            if (id == 0) return null;
            return addressDao.queryForId(id);
        }
        catch (SQLException e)
        {
            System.err.println("Error fetching address: "+e.getMessage());
            return null;
        }
    }

    /**
     * Fetches list of address data associated with a customer and stores it in a List<Address> object
     *
     * @param customer the customer whose addresses should be fetched
     * @return a reference to a new List<Address> object containing the data
     */
    public List<Address> getAddresses(Customer customer)
    {
        try
        {
            if (customer == null) return null;
            return addressDao.queryForEq("idcustomer_id", customer.getIdcustomer());
        }
        catch (SQLException e)
        {
            System.err.println("Error fetching addresses: "+e.getMessage());
            return null;
        }
    }

    /**
     * Inserts example data into database
     */
    public void createExampleData()
    {
    	try
    	{
	    	Customer c = new Customer("Eksempel Eksempelsen", "512 256 128");
	    	
	    	Address a1 = new Address(c, "Internettveien 64", 1024);
	    	Address a2 = new Address(c, "Addresseveien 32", 2048);
	    	
	    	List<Customer> cl = customerDao.queryForMatching(c);
	    	
	    	if (cl.size() == 0)
	    	{
		    	customerDao.create(c);
		    	addressDao.create(a1);
		    	addressDao.create(a2);
	            System.out.println("[Debug] Inserted example data");
	    	}
	    	else
	    	{
	            System.out.println("[Debug] Example data already present");
	    	}
    	}
    	catch (SQLException e)
    	{
            System.err.println("Error inserting example data: "+e.getMessage());
    	}
    }
}
