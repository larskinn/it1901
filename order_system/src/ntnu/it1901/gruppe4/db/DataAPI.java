package ntnu.it1901.gruppe4.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;

/**
 * API to communicate with the SQL database of the system.
 * 
 * @author David M.
 * @author Lars Kinn Ekroll
 */
public class DataAPI {
	private static Dao<Customer, Integer> customerDao;
	private static Dao<Address, Integer> addressDao;
	private static Dao<Dish, Integer> dishDao;
	private static Dao<Order, Integer> orderDao;
	private static Dao<OrderItem, Integer> orderItemDao;
	private static Dao<Config, String> configDao;

	private static JdbcConnectionSource conn = null;

	private static String url = "";

	/**
	 * Opens a connection to a database.
	 * 
	 * @param file
	 *            The filename of the database.
	 */
	public static void open(String file) {
		if (conn != null) {
			System.out.println("[Debug] Connection already open. Closing...");
			close();
		}
		url = "jdbc:sqlite:" + file;
		// String url = "jdbc:sqlite:./data.db";

		System.out.println("[Debug] Opening database " + url + "...");

		try {
			// Class.forName("org.sqlite.JDBC");
			conn = new JdbcConnectionSource(url);

			if (conn == null)
				throw new Exception("Failed to connect to database.");

			setupDatabase();
			setupSettings();
			/*
			 * setupSettings() should only be called here, but since the GUIs
			 * clear the database on startup, it has to be called again later,
			 * from outside this class.
			 */
		} catch (Exception e) {
			System.err.println("[Error] Failed to open database connection: "
					+ e.getMessage());
		}
	}

	/**
	 * Opens the default database.
	 */
	public static void open() {
		open("./data.db");
	}

	/**
	 * Makes sure that the database has all the needed tables.
	 */
	private static void setupDatabase() throws SQLException {
		if (conn != null) {
			System.out.println("[Debug] Initializing tables...");

			customerDao = DaoManager.createDao(conn, Customer.class);
			addressDao = DaoManager.createDao(conn, Address.class);
			dishDao = DaoManager.createDao(conn, Dish.class);
			orderDao = DaoManager.createDao(conn, Order.class);
			orderItemDao = DaoManager.createDao(conn, OrderItem.class);
			configDao = DaoManager.createDao(conn, Config.class);

			TableUtils.createTableIfNotExists(conn, Customer.class);
			TableUtils.createTableIfNotExists(conn, Address.class);
			TableUtils.createTableIfNotExists(conn, Dish.class);
			TableUtils.createTableIfNotExists(conn, Order.class);
			TableUtils.createTableIfNotExists(conn, OrderItem.class);
			TableUtils.createTableIfNotExists(conn, Config.class);
		} else {
			System.err
					.println("[Error] Tried to setup database without a connection");
		}
	}

	/**
	 * Ensures that the program settings exist, by setting any missing settings
	 * to defaults from {@link Settings#DEFAULT_SETTINGS}. 
	 * <p>
	 * Does not overwrite existing settings!
	 */
	public static void setupSettings() {
		for (Map.Entry<String, String> entry : Settings.DEFAULT_SETTINGS
				.entrySet()) {
			try {
				configDao.createIfNotExists(new Config(entry.getKey(), entry
						.getValue()));
			} catch (SQLException e) {
				System.err.println("Error setting default config value: "
						+ e.getMessage());
			}
		}
	}

	/**
	 * Completely clears the database of all data.
	 * <p>
	 * <b>WARNING:</b> This is very destructive and unrecoverable.
	 */
	public static void clearDatabase() {
		if (conn != null) {
			try {
				System.out.println("[Debug] Initializing tables...");
				TableUtils.clearTable(conn, Customer.class);
				TableUtils.clearTable(conn, Address.class);
				TableUtils.clearTable(conn, Dish.class);
				TableUtils.clearTable(conn, Order.class);
				TableUtils.clearTable(conn, OrderItem.class);
				TableUtils.clearTable(conn, Config.class);
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
	 * Inserts example data into database.
	 */
	public static void createExampleData() {
		setupSettings();

		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer("Lars Kinn Ekroll", "98765432"));
		customers.add(new Customer("Eskild Eksempelsen", "51225612"));
		customers.add(new Customer("Kåre Utvåg", "11235713"));
		customers.add(new Customer("Rita Ottervik", "72546111"));

		for (Customer c : customers) {
			c.save();
		}

		List<Address> addresses = new ArrayList<Address>();
		addresses.add(new Address(customers.get(0), "Håkon Sverressons veg 10",
				7051));
		addresses.add(new Address(customers.get(1), "Starevegen 9", 7022));
		addresses.add(new Address(customers.get(2), "Krambugata 12", 7010));
		addresses.add(new Address(customers.get(3), "Rådhuset", 7013));

		for (Address a : addresses) {
			a.save();
		}

		List<Dish> dishes = new ArrayList<Dish>();

		dishes.add(new Dish(
				"Capricciosa",
				160,
				DishType.PIZZA,
				"Tomatsaus, mozzarella, skinke, champignon, artisjokk og oliven",
				true));
		dishes.add(new Dish("Pepperoni", 140, DishType.PIZZA,
				"Tomatsaus, mozzarella og delikate pepperoni-pølser", true));
		dishes.add(new Dish("Napoletana", 140, DishType.PIZZA,
				"Tomatsaus, mozarella og ansjos", true));
		dishes.add(new Dish(
				"Americana",
				190,
				DishType.PIZZA,
				"Tomatsaus, spare ribs, mais og cheddar på en ekstra tykk bunn",
				true));
		dishes.add(new Dish("Coca Cola 1.5 liter", 40, DishType.DRINK, "", true));
		dishes.add(new Dish("Coca Cola 0.5 liter", 25, DishType.DRINK, "", true));
		dishes.add(new Dish("Fanta 1.5 liter", 40, DishType.DRINK, "", true));
		dishes.add(new Dish("Fanta 0.5 liter", 25, DishType.DRINK, "", true));
		dishes.add(new Dish("Rømmedressing", 25, DishType.CONDIMENT,
				"Plastskei ikke inkludert", true));
		dishes.add(new Dish("Plastskei", (float) 1.5, DishType.ACCESSORY, "",
				true));

		for (Dish d : dishes) {
			d.save();
		}

		OrderMaker om = new OrderMaker();
		Order o = om.getOrder();
		o.setIdAddress(addresses.get(0));
		om.addItem(dishes.get(0));
		om.addItem(dishes.get(3));
		om.addItem(dishes.get(4));
		om.addItem(dishes.get(7));
		om.setState(Order.READY_FOR_DELIVERY);
		om.save();

		System.out.println("[Debug] Inserted example data");
	}

	/**
	 * Closes the connection to the database.
	 */
	public static void close() {
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
	 * Stores a {@link Customer} to the database. If its ID matches an existing
	 * <code>Customer</code>, the matching <code>Customer</code> is updated. 
	 * If not, a new <code>Customer</code> is added to the database.
	 * 
	 * @param c
	 *            a reference to the <code>Customer</code> object containing the data to be
	 *            stored.
	 */
	public static void saveCustomer(Customer c) {
		try {
			customerDao.createOrUpdate(c);
		} catch (SQLException e) {
			System.err.println("Error storing customer: " + e.getMessage());
		}
	}

	/**
	 * Fetches customer data and stores it in a {@link Customer Customer object}.
	 * 
	 * @param id
	 *            unique ID used to identify a <code>Customer</code> in the database
	 *            (idCustomer).
	 * @return a reference to a new <code>Customer</code> object containing the data.
	 */
	public static Customer getCustomer(int id) {
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
	 * Fetches customer data and stores it in a {@link Customer} object.
	 * 
	 * @param address
	 *            an {@link Address} object containing a reference to a <code>Customer</code> object
	 *            containing the unique ID of the <code>Customer</code>.
	 * @return a reference to a new <code>Customer</code> object containing the data.
	 */
	public static Customer getCustomer(Address address) {
		try {
			if (address == null)
				return null;
			return customerDao.queryForId(address.getIdCustomer()
					.getIdCustomer());
		} catch (SQLException e) {
			System.err.println("Error fetching customer by address: "
					+ e.getMessage());
			return null;
		}
	}

	/**
	 * Removes <code>Customer</code> from database. Does not remove any addresses; call
	 * {@link #remAddresses(Customer)} first.
	 * 
	 * @param customer
	 *            The <code>Customer</code> to be removed.
	 */
	public static void remCustomer(Customer customer) {
		try {
			if (customer == null)
				return;
			customerDao.delete(customer);
		} catch (SQLException e) {
			System.err.println("Error removing customer: " + e.getMessage());
			return;
		}
	}

	/**
	 * Searches for customers by substring.
	 * 
	 * @param search
	 *            The search string.
	 * @return a reference to a new <code>Customer</code> object containing the data.
	 */
	public static List<Customer> findCustomers(String search) {
		try {
			search = search.trim().replace("  ", " ").replace("  ", " ");

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
				where.eq("phone", strings[i]);
				where.or();
				where.like("name", "%" + strings[i] + "%");
				where.and();
			}
			where.eq("phone", strings[i]);
			where.or();
			where.like("name", "%" + strings[i] + "%");

			List<Customer> customers = customerDao.query(where.prepare());
			Collections.sort(customers);
			return customers;
		} catch (SQLException e) {
			System.err.println("Error searching for customer: "
					+ e.getMessage());
			return null;
		}
	}

	// Address

	/**
	 * Stores an {@link Address} to the database. If the ID matches an existing <code>Address</code>,
	 * the match is updated. If not, a new <code>Address</code> is added to the database.
	 * 
	 * @param a
	 *            a reference to the <code>Address</code> object containing the data to be stored.
	 */
	public static void saveAddress(Address a) {
		try {
			addressDao.createOrUpdate(a);
		} catch (SQLException e) {
			System.err.println("Error storing address: " + e.getMessage());
		}
	}

	/**
	 * Deletes an <code>Address</code> from the database.
	 * 
	 * @param a
	 *            a reference to the <code>Address</code> object to be deleted.
	 */

	public static void deleteAddress(Address a) {
		try {
			addressDao.delete(a);
		} catch (SQLException e) {
			System.err.println("Error deleting address: " + e.getMessage());
		}
	}

	/**
	 * Fetches address data and stores it in a {@link Address} object.
	 * 
	 * @param id
	 *            a unique ID used to identify an <code>Address</code> in the database
	 *            (idAddress).
	 * @return a reference to a new <code>Address</code> object containing the data.
	 */
	public static Address getAddress(int id) {
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
	 * Fetches the {@link Address} associated with an {@link Order}.
	 * 
	 * @param order
	 *            an <code>Order</code> object containing an <code>Address</code> object.
	 * @return a reference to a new <code>Address</code> object containing the data.
	 */
	public static Address getAddress(Order order) {
		try {
			if (order == null)
				return null;
			return addressDao.queryForId(order.getIdAddress().getIdAddress());
		} catch (SQLException e) {
			System.err.println("Error fetching address by order: "
					+ e.getMessage());
			return null;
		}
	}

	/**
	 * Fetches a list of address data associated with a {@link Customer}
	 * and stores it in a <code>List</code> object.
	 * 
	 * @param customer
	 *            the <code>Customer</code> whose addresses should be fetched.
	 * @return a reference to a new <code>List</code> object containing the addresses.
	 */
	public static List<Address> getAddresses(Customer customer) {
		try {
			if (customer == null)
				return null;
			return addressDao.queryForEq("idCustomer_id",
					customer.getIdCustomer());
		} catch (SQLException e) {
			System.err.println("Error fetching addresses: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Removes addresses associated with a {@link Customer}.
	 * 
	 * @param customer
	 *            the <code>Customer</code> whose addresses should be removed.
	 */
	public static void remAddresses(Customer customer) {
		try {
			if (customer == null)
				return;

			int i = customer.getIdCustomer();
			DeleteBuilder<Address, Integer> del = addressDao.deleteBuilder();
			del.where().eq("idCustomer_id", i);
			addressDao.delete(del.prepare());

		} catch (SQLException e) {
			System.err.println("Error fetching addresses: " + e.getMessage());
		}
	}

	/**
	 * Finds addresses containing the search string.
	 * 
	 * @param s
	 *            the search string.
	 * @return a reference to a new <code>List</code> object with the matching addresses.
	 */

	public static List<Address> findAddresses(String s) {
		try {
			return addressDao.query(addressDao.queryBuilder().where()
					.like("addressLine", "%" + s + "%").prepare());
		} catch (SQLException e) {
			System.err.println("Error when searching for addresses: "
					+ e.getMessage());
			return null;
		}
	}

	/**
	 * Stores a {@link Dish} to the database. If the ID matches an existing <code>Dish</code>, the
	 * match is updated. If not, a new <code>Dish</code> is added to the database.
	 * 
	 * @param dish
	 *            a reference to the <code>Dish</code> object 
	 *            containing the data to be stored.
	 */
	public static void saveDish(Dish dish) {
		try {
			dishDao.createOrUpdate(dish);
		} catch (SQLException e) {
			System.err.println("Error storing dish: " + e.getMessage());
		}
	}

	/**
	 * Fetches dish data and stores it in a {@link Dish} object.
	 * 
	 * @param id
	 *            a unique ID used to identify a <code>Dish</code> in the database (idDish).
	 * @return a reference to a new <code>Dish</code> object containing the data.
	 */
	public static Dish getDish(int id) {
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
	 * Finds dishes containing the search string.
	 * 
	 * @param s
	 *            the search string.
	 * @return a reference to a new <code>List</code> object with the matching dishes.
	 */
	public static List<Dish> findDishes(String s) {
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
	 * Finds all the dishes of the specified {@link DishType}.
	 * 
	 * @param type
	 *            the <code>DishType</code> to search for.
	 * @return a reference to a new <code>List</code> object with the matching dishes.
	 */
	public static List<Dish> findDishes(DishType type) {
		try {
			return dishDao.query(dishDao.queryBuilder().where()
					.eq("type", type.name()).prepare());
		} catch (SQLException e) {
			System.err.println("Error finding dishes of type " + type.name()
					+ ": " + e.getMessage());
			return null;
		}
	}

	/**
	 * Stores an {@link Order} to the database. If the ID matches an existing <code>Order</code>, the
	 * match is updated. If not, a new <code>Order</code> is added to the database.
	 * 
	 * @param order
	 *            a reference to the <code>Order</code> object 
	 *            containing the data to be stored.
	 */
	public static void saveOrder(Order order) {
		try {
			orderDao.createOrUpdate(order);
		} catch (SQLException e) {
			System.err.println("Error storing order: " + e.getMessage());
		}
	}

	/**
	 * Removes an existing <code>Order</code> in the database.
	 * 
	 * @param order
	 *            a reference to the <code>Order</code> object 
	 *            containing the data to be removed.
	 */
	public static void remOrder(Order order) {
		try {
			// First remove all the orderItems
			List<OrderItem> orderItems = getOrderItems(order);
			for (OrderItem item : orderItems) {
				remOrderItem(item);
			}
			// Then remove the order
			orderDao.delete(order);
		} catch (SQLException e) {
			System.err.println("Error removing order: " + e.getMessage());
		}
	}

	/**
	 * Fetches order data and stores it in a {@link Order} object.
	 * 
	 * @param id
	 *            a unique ID used to identify a <code>Dish</code> in the database (idOrder).
	 * @return a reference to a new <code>Order</code> object containing the data.
	 */
	public static Order getOrder(int id) {
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
	 * Returns a <code>List</code> containing all the <code>Orders</code>
	 * in the database ordered by the time they were added.
	 * 
	 * @return a new <code>List</code> object containing all 
	 * the <code>Orders</code> in the database.
	 */
	public static List<Order> getOrders() {
		try {
			return orderDao.query(orderDao.queryBuilder()
					.orderBy("orderTime", false).prepare());
		} catch (SQLException e) {
			System.err.println("Error fetching order: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Stores an {@link OrderItem} to the database. If the ID matches an existing <code>OrderItem</code>, 
	 * the match is updated. If not, a new <code>OrderItem</code> is added to the database.
	 * 
	 * @param orderItem
	 *            a reference to the <code>OrderItem</code> object 
	 *            containing the data to be stored.
	 */
	public static void saveOrderItem(OrderItem orderItem) {
		try {
			orderItemDao.createOrUpdate(orderItem);
		} catch (SQLException e) {
			System.err.println("Error storing orderitem: " + e.getMessage());
		}
	}

	/**
	 * Removes an {@link OrderItem} from the database.
	 * 
	 * @param orderItem
	 *            a reference to the <code>OrderItem</code> object 
	 *            containing the data to be removed.
	 */
	public static void remOrderItem(OrderItem orderItem) {
		try {
			orderItemDao.delete(orderItem);
		} catch (SQLException e) {
			System.err.println("Error removing orderitem: " + e.getMessage());
		}
	}

	/**
	 * Removes all <code>OrderItems</code> associated with 
	 * an <code>Order</code> from the database.
	 * 
	 * @param order
	 *            a reference to the <code>Order</code> object for 
	 *            which the <code>OrderItems</code> to be
	 *            removed are associated.
	 */
	public static void remOrderItems(Order order) {
		if (order == null)
			return;
		try {
			int i = order.getIdOrder();
			DeleteBuilder<OrderItem, Integer> del = orderItemDao
					.deleteBuilder();
			del.where().eq("idOrder_id", i);
			orderItemDao.delete(del.prepare());
		} catch (SQLException e) {
			System.err.println("Error removing orderitems: " + e.getMessage());
		}
	}

	/**
	 * Fetches order item data and stores it in a {@link OrderItem} object.
	 * 
	 * @param id
	 *            a unique ID used to identify an <code>OrderItem</code>
	 *            in the database (idOrderItem).
	 *            
	 * @return a reference to a new <code>OrderItem</code> object containing the data.
	 */
	public static OrderItem getOrderItem(int id) {
		try {
			if (id == 0)
				return null;
			return orderItemDao.queryForId(id);
		} catch (SQLException e) {
			System.err.println("Error fetching order item: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Fetches a list of <code>OrderItems</code> associated with an <code>Order</code>.
	 * 
	 * @param order
	 *            the <code>Order</code> the <code>OrderItems</code> should be associated with.
	 *            
	 * @return a reference to a new <code>List</code> containing the <code>OrderItems</code>.
	 */
	public static List<OrderItem> getOrderItems(Order order) {
		try {
			return orderItemDao.queryForEq("idOrder_id", order.getIdOrder());
		} catch (SQLException e) {
			System.err.println("Error fetching order items: " + e.getMessage());
			return null;
		}
	}

	// Config

	/**
	 * Stores a configuration value to the database.
	 * 
	 * @param key
	 *            a <code>String</code> containing the name of the configuration value.
	 * @param value
	 *            a <code>String</code> containing the value.
	 */
	static void setConfig(String key, String value) {
		try {
			configDao.createOrUpdate(new Config(key, value));
		} catch (SQLException e) {
			System.err.println("Error storing config value: " + e.getMessage());
		}
	}

	/**
	 * Fetches a configuration value from the database.
	 * 
	 * @param key
	 * 			a <code>String</code> containing the name of the configuration value.
	 * @return a a <code>String</code> containing the value.
	 */
	static String getConfig(String key) {
		try {
			Config config = configDao.queryForId(key);
			if (config == null) {
				return null;
			}
			return config.getValue();
		} catch (SQLException e) {
			System.err
					.println("Error fetching config value: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Returns the name of the {@link Customer} assigned to an {@link Order}, 
	 * or a a <code>String</code> describing self pickup.
	 * 
	 * @return A <code>String</code> with a customer name, or a parenthesized comment.
	 */
	public static String getCustomerName(Order order) {
		if (order == null) {
			return "(Ingen)";
		} else {
			Address a = getAddress(order);
			if (a == null) {
				return "(Ikke spesifisert)";
			} else {
				return getCustomer(a).getName();
			}
		}
	}

	/**
	 * Returns a <code>String</code> describing where (or if) the order should be delivered.
	 * 
	 * @return a <code>String</code> with an {@link Address}, or a parenthesized comment.
	 */
	public static String getDeliveryDescription(Order order) {
		if (order == null) {
			return "(Ingen)";
		}
		if (order.getSelfPickup()) {
			return "(Hentes av kunden)";
		} else if (order.getIdAddress() == null) {
			return "(Ingen addresse spesifisert)";
		} else {
			Address a = getAddress(order);
			return a.getAddressLine() + ", " + a.getPostalCode();
		}
	}
}
