package ntnu.it1901.gruppe4.db;

import java.io.File;

/**
 * A container class for methods to reset the database
 * and create example data.
 * 
 * @see #resetDB()
 * @see #createExampleData()
 * 
 * @author David M.
 */
public class DBReset {
	/**
	 * Run this once if you want to reset the database. 
	 * <p>
	 * <b>Warning:</b> If your db schema is deprecated, 
	 * you have to manually delete the data.db file.
	 */
	public static void resetDB() {

		(new File("./data.db")).delete();
		DataAPI.open("./data.db");
		DataAPI.clearDatabase();
		DataAPI.close();

		System.out.println("[Debug] Database has been reset");
	}

	/**
	 * Populates the database with example data.
	 */
	public static void createExampleData() {
		DataAPI.open("./data.db");
		DataAPI.createExampleData();
		DataAPI.close();

		System.out.println("[Debug] Example data has been inserted");
	}
}
