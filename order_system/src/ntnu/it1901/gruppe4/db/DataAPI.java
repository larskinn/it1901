package ntnu.it1901.gruppe4.db;

import java.sql.*;
import java.util.List;
import java.lang.Exception;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;

/**
 * API to communicate with pizza server
 * 
 * @author David M.
 */
public class DataAPI {
	private Dao<Customer, Integer> customerDao;
	private Dao<Address, Integer> addressDao;
	private Dao<Dish, Integer> dishDao;
	private Dao<Order, Integer> orderDao;
	private Dao<OrderItem, Integer> orderItemDao;

	private JdbcConnectionSource conn = null;

	private String url = "";

	/**
	 * Creates a new DataAPI object
	 * 
	 * @param file
	 *            The file that contains the sqlite database
	 */
	public DataAPI(String file) {
		url = "jdbc:sqlite:" + file;
	}

	/**
	 * Opens a connection to a database
	 */
	public void open() {
		// String url = "jdbc:sqlite:./data.db";

		System.out.println("[Debug] Opening database " + url + "...");

		try {
			// Class.forName("org.sqlite.JDBC");
			conn = new JdbcConnectionSource(url);

			if (conn == null)
				throw new Exception("Failed to connect to database.");

			setupDatabase();
		} catch (Exception e) {
			System.err.println("[Error] Failed to open database connection: "
					+ e.getMessage());
		}
	}

	/**
	 * Sets up the database
	 */
	private void setupDatabase() throws SQLException {
		if (conn != null) {
			System.out.println("[Debug] Initializing tables...");

			customerDao = DaoManager.createDao(conn, Customer.class);
			addressDao = DaoManager.createDao(conn, Address.class);
			dishDao = DaoManager.createDao(conn, Dish.class);
			orderDao = DaoManager.createDao(conn, Order.class);
			orderItemDao = DaoManager.createDao(conn, OrderItem.class);

			TableUtils.createTableIfNotExists(conn, Customer.class);
			TableUtils.createTableIfNotExists(conn, Address.class);
			TableUtils.createTableIfNotExists(conn, Dish.class);
			TableUtils.createTableIfNotExists(conn, Order.class);
			TableUtils.createTableIfNotExists(conn, OrderItem.class);
		} else {
			System.err
					.println("[Error] Tried to setup database without a connection");
		}
	}

	/**
	 * Delete all the data
	 */
	public void clearDatabase() {
		if (conn != null) {
			try {
				System.out.println("[Debug] Initializing tables...");
				TableUtils.clearTable(conn, Customer.class);
				TableUtils.clearTable(conn, Address.class);
				TableUtils.clearTable(conn, Dish.class);
				TableUtils.clearTable(conn, Order.class);
				TableUtils.clearTable(conn, OrderItem.class);
			} catch (SQLException e) {
				System.err.println("[Error] While clearing database: "
						+ e.getMessage());
			}
		} else {
			System.err
					.println("[Error] Tried to clear database without a connection");
		}
	}

	/**
	 * Inserts example data into database
	 */
	public void createExampleData() {
		try {
			Customer c = new Customer("Eksempel Eksempelsen", "512 256 128");

			Address a1 = new Address(c, "Internettveien 64", 1024);
			Address a2 = new Address(c, "Addresseveien 32", 2048);

			Dish d1 = new Dish("Pizza Capriciosa", 50, "Skinke & Champignon", true);
			Dish d2 = new Dish("Pizza Pepperoni", 52, "Pepperoni; nom nom", true);

			Order o = new Order(a1);

			OrderItem oi1 = new OrderItem(o, d1);
			OrderItem oi2 = new OrderItem(o, d2);
			OrderItem oi3 = new OrderItem(o, d2);

			List<Customer> cl = customerDao.queryForMatching(c);

			if (cl == null || cl.size() == 0) {
				addCustomer(c);
				addAddress(a1);
				addAddress(a2);
				addDish(d1);
				addDish(d2);
				addOrder(o);
				addOrderItem(oi1);
				addOrderItem(oi2);
				addOrderItem(oi3);
				System.out.println("[Debug] Inserted example data");
			} else {
				System.out.println("[Debug] Example data already present");
			}
		} catch (SQLException e) {
			System.err.println("Error inserting example data: "
					+ e.getMessage());
		}
	}

	/**
	 * Closes the connection to the database
	 */
	public void close() {
		System.out.println("[Debug] Closing database");

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			} finally {
				conn = null;
			}
		}
	}

	// Customer

	/**
	 * Stores a new customer to the database
	 * 
	 * @param c
	 *            a reference to the Customer object containing the data to be
	 *            stored
	 */
	public void addCustomer(Customer c) {
		try {
			customerDao.create(c);
		} catch (SQLException e) {
			System.err.println("Error storing customer: " + e.getMessage());
		}
	}

	/**
	 * Fetches customer data and stores it in a Customer object
	 * 
	 * @param id
	 *            unique ID used to identify a customer in the database
	 *            (idcustomer)
	 * @return a reference to a new Customer object containing the data
	 */
	public Customer getCustomer(int id) {
		try {
			if (id == 0)
				return null;
			return customerDao.queryForId(id);
		} catch (SQLException e) {
			System.err.println("Error fetching customer: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Searches for customers by substring
	 * 
	 * @param search
	 *            The search string
	 * @return a reference to a new Customer object containing the data
	 */
	public List<Customer> findCustomers(String search) {
		try {
			String[] strings = search.split(" ");

			QueryBuilder<Customer, Integer> qb = customerDao.queryBuilder();
			Where<Customer, Integer> where = qb.where();

			// Test for each word in the string sequence.
			//
			// "david m" will search for any name containing "david" and "m"
			//
			// LIKE is not case sensitive

			int i = 0;
			for (; i < strings.length - 1; i++) {
				where.like("name", "%" + strings[i] + "%");
				where.and();
			}
			where.like("name", "%" + strings[i] + "%");

			return customerDao.query(where.prepare());
		} catch (SQLException e) {
			System.err.println("Error searching for customer: "
					+ e.getMessage());
			return null;
		}
	}

	// Address

	/**
	 * Stores a new address to the database
	 * 
	 * @param a
	 *            a reference to the Address object containing the data to be
	 *            stored
	 */
	public void addAddress(Address a) {
		try {
			addressDao.create(a);
		} catch (SQLException e) {
			System.err.println("Error storing address: " + e.getMessage());
		}
	}

	/**
	 * Fetches address data and stores it in a Address object
	 * 
	 * @param id
	 *            a unique ID used to identify an address in the database
	 *            (idaddress)
	 * @return a reference to a new Address object containing the data
	 */
	public Address getAddress(int id) {
		try {
			if (id == 0)
				return null;
			return addressDao.queryForId(id);
		} catch (SQLException e) {
			System.err.println("Error fetching address: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Fetches list of address data associated with a customer and stores it in
	 * a List<Address> object
	 * 
	 * @param customer
	 *            the customer whose addresses should be fetched
	 * @return a reference to a new List<Address> object containing the data
	 */
	public List<Address> getAddresses(Customer customer) {
		try {
			if (customer == null)
				return null;
			return addressDao.queryForEq("idcustomer_id",
					customer.getIdcustomer());
		} catch (SQLException e) {
			System.err.println("Error fetching addresses: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Finds addresses containing the search string
	 * 
	 * @param s
	 *            the search string
	 * @return a reference to a new List<Address> object with the matching
	 *         addresses
	 */

	public List<Address> findAddresses(String s) {
		try {
			return addressDao.query(addressDao.queryBuilder().where()
					.like("addressline", "%" + s + "%").prepare());
		} catch (SQLException e) {
			System.err.println("Error when searching for addresses: "
					+ e.getMessage());
			return null;
		}
	}

	/**
	 * Stores a new dish to the database
	 * 
	 * @param dish
	 *            a reference to the Dish object containing the data to be
	 *            stored
	 */
	public void addDish(Dish dish) {
		try {
			dishDao.create(dish);
		} catch (SQLException e) {
			System.err.println("Error storing dish: " + e.getMessage());
		}
	}

	/**
	 * Fetches dish data and stores it in a Dish object
	 * 
	 * @param id
	 *            a unique ID used to identify an dish in the database (iddish)
	 * @return a reference to a new Dish object containing the data
	 */
	public Dish getDish(int id) {
		try {
			if (id == 0)
				return null;
			return dishDao.queryForId(id);
		} catch (SQLException e) {
			System.err.println("Error fetching dish: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Finds dishes containing the search string
	 * @param s
	 * 			the search string
	 * @return a reference to a new List<Dish> object with the matching dishes
	 */
	public List<Dish> findDishes(String s) {
		try {
			return dishDao.query(dishDao.queryBuilder().where()
					.like("name", "%" + s + "%").prepare());
		} catch (SQLException e) {
			System.err.println("Error when searching for dish: "
					+ e.getMessage());
			return null;
		}
	}

	/**
	 * Stores a new order to the database
	 * 
	 * @param order
	 *            a reference to the Order object containing the data to be
	 *            stored
	 */
	public void addOrder(Order order) {
		try {
			orderDao.create(order);
		} catch (SQLException e) {
			System.err.println("Error storing order: " + e.getMessage());
		}
	}

	/**
	 * Fetches order data and stores it in a Order object
	 * 
	 * @param id
	 *            a unique ID used to identify an dish in the database (idorder)
	 * @return a reference to a new Order object containing the data
	 */
	public Order getOrder(int id) {
		try {
			if (id == 0)
				return null;
			return orderDao.queryForId(id);
		} catch (SQLException e) {
			System.err.println("Error fetching order: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Stores a new orderitem to the database
	 * 
	 * @param orderitem
	 *            a reference to the OrderItem object containing the data to be
	 *            stored
	 */
	public void addOrderItem(OrderItem orderitem) {
		try {
			orderItemDao.create(orderitem);
		} catch (SQLException e) {
			System.err.println("Error storing orderitem: " + e.getMessage());
		}
	}

	/**
	 * Fetches order item data and stores it in a OrderItem object
	 * 
	 * @param id
	 *            a unique ID used to identify an dish in the database
	 *            (idorderitem)
	 * @return a reference to a new OrderItem object containing the data
	 */
	public OrderItem getOrderItem(int id) {
		try {
			if (id == 0)
				return null;
			return orderItemDao.queryForId(id);
		} catch (SQLException e) {
			System.err.println("Error fetching order item: " + e.getMessage());
			return null;
		}
	}
}
