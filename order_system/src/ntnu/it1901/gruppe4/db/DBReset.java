package ntnu.it1901.gruppe4.db;

/**
 * Run this once if you want to reset the database. (PS: If your db schema is
 * deprecated, you still have to delete data.db
 * 
 * @author David M.
 */

public class DBReset {
	public static void main(String args[]) {
		DataAPI.open("./data.db");
		DataAPI.clearDatabase();
		DataAPI.createExampleData();

		DataAPI.close();

		System.out.println("Database has been reset.");
	}
}
