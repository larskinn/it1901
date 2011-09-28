import ntnu.it1901.gruppe4.db.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    static DataAPI data;

    public static void main(String args[]) throws IOException
    {
        //try
        {
            Customer customer = null;
            Address addr = null;
            
            data = new DataAPI("./test_data.db");	//PS: use different files for actual data. This is for testing DataAPI
            
            data.open();
            data.clearDatabase();
            data.createExampleData();
            
            Customer david = new Customer("David Myklebust", "511112");
            Address davids_addresse = new Address(david, "Her-veien 1", 5122);
            
            data.addCustomer(david);
            data.addAddress(davids_addresse);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.print("Search for customer: ");

            List<Customer> result = data.findCustomers(br.readLine());
            
            System.out.println("Data entries:");

            if (result != null)
            {
	            for (int cid = 0; cid < result.size(); cid++)
	            {
	            	customer = result.get(cid);
	                List<Address> addrList = data.getAddresses(customer);
	            
		            if (customer != null)
		            {
		                System.out.println("Name: "+customer.getName());
		                System.out.println("Phone: "+customer.getPhone());
		            }
		            else System.out.println("customer == null");
		            
		            if (addrList != null)
		            {
		            	System.out.println("Addresses: "+addrList.size());
			            for (int i = 0; i < addrList.size(); i++)
			            {
			            	addr = addrList.get(i);
			            	System.out.println("	#"+(i+1));
				            if (addr != null)
				            {
				                System.out.println("	Address: "+addr.getAddressline());
				                System.out.println("	Postalcode: "+addr.getPostalcode());
				            }
				            else System.out.println("	addr == null");
			            }
		            }
		            else System.out.println("	addrList == null");
	            }
            }
            
            data.close();
        }
        //catch (Exception e)
        //{
        //    System.err.println("Err: "+e.getMessage());
        //}
    }
}
