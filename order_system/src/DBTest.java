import ntnu.it1901.gruppe4.db.*;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import ntnu.it1901.gruppe4.db.Address;
import ntnu.it1901.gruppe4.db.Customer;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBTest
{
    private static Dao<Customer, Integer> customerDao;
    private static Dao<Address, Integer> addressDao;


    public static void main(String args[])
    {
        String url = "jdbc:sqlite:./data.db";
        
        JdbcConnectionSource conn;
    
        try
        {
            Class.forName("org.sqlite.JDBC");  
            conn = new JdbcConnectionSource(url);
            
            Customer david = null;
            Address addr = null;
            
            setupDatabase(conn);
            
            david = customerDao.query
            //david = new Customer("David M.", "543");
            //customerDao.create(david);
            
            //addr = new Address(david, "Vei-veien 1a", 7001);
            //addressDao.create(addr);
            
            System.out.println("From "+url+":");
            if (addr != null)
            {
                System.out.println("Addr: "+addr.getAddressline());
                System.out.println("PC: "+addr.getPostalcode());
            }
            if (david != null)
            {
                System.out.println("Name: "+david.getName());
                System.out.println("Phone: "+david.getPhone());
            }
        }
        catch (Exception e)
        {
            System.err.println("Err: "+e.getMessage());
        }
    }
    
    private static void setupDatabase(JdbcConnectionSource conn) throws SQLException
    {
        customerDao = DaoManager.createDao(conn, Customer.class);
        addressDao = DaoManager.createDao(conn, Address.class);
        
        //  TableUtils.createTable(conn, Customer.class);
        //  TableUtils.createTable(conn, Address.class);
    }
}
