package ntnu.it1901.gruppe4.db;

import java.io.File;

/**
 * Run this once if you want to reset the database. (PS: If your db schema is
 * deprecated, you still have to delete data.db
 * 
 * @author David M.
 */

public class DBReset {
	/**
	 * Delete database file and recreate tables
	 */
	public static void resetDB() {

		(new File("./data.db")).delete();
		DataAPI.open("./data.db");
		DataAPI.clearDatabase();
		DataAPI.close();

		System.out.println("[Debug] Database has been reset");
	}

	/**
	 * Populate database with example data
	 */
	public static void createExampleData() {
		DataAPI.open("./data.db");
		DataAPI.createExampleData();
		DataAPI.close();

		System.out.println("[Debug] Example data has been inserted");
	}
}
