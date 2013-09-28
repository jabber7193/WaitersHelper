import java.sql.*;

public class MoneyDB {

	MoneyDB() {
		this("test.db");
	}// end default constructor

	MoneyDB(String dbName) {// Create database with passed name if it doesn't
							// exist

		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:" + dbName);// connect
			// to
			// database
			System.out.println("Database Opened Successfully...");
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}// end constructor

	public void newTable() { // Creates AMOUNT table in DB if it doesn't exist
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement stmt = c.createStatement();
			String sql = "CREATE TABLE ACCOUNT"
					+ "(ID INTEGER PRIMARY KEY	NOT NULL,"
					+ " DATE			 STRING		NOT NULL," + " AMTDEP		 FLOAT		NOT NULL,"
					+ " AMTMADE		 FLOAT		NOT NULL)";
			stmt.executeUpdate(sql);
			System.out.println("Table created!");
			stmt.close();
			c.close();
		} catch (Exception e) {
			// System.err.println(e.getClass().getName() + ": " +
			// e.getMessage());
		}
	}// end method newTable

	public static void printDB() { // method to print out each record of DB. NEEDS
							// FORMATING
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ACCOUNT;");
			while (rs.next()) {
				int id = rs.getInt("id");
				String date = rs.getString("date");
				float amtdep = rs.getFloat("amtdep");
				float amtmade = rs.getFloat("amtmade");
				
				WaitersHelperGUI.populate(id, date, amtmade, amtdep);
				
				//System.out.println("ID = " + id); 
				//System.out.println("DATE = " + date);
				//System.out.println("AMTDEP = " + amtdep);
				//System.out.println("AMTMADE = " + amtmade);
				//System.out.println();
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}// end method printDB

	// insertDB: Method for inserting new record into database. Pass variables
	// from user input.
	public static void insertDB(double amtdep, double amtmade) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "INSERT INTO ACCOUNT (ID,DATE,AMTDEP,AMTMADE) "
					+ "VALUES (null, CURRENT_DATE, " + amtdep + ", " + amtmade
					+ ");";
			stmt.executeUpdate(sql);
			System.out.println("Data inserted.");
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			// System.exit(0);
		}
		System.out.println("Records created successfully");
	}// end method insertDB

	// updateDB: Method for editing previous entries to database.
	public static void updateDB(int x, float m, float d) {
		// TODO: create method
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			String sql = ( "UPDATE ACCOUNT set AMTMADE = " + m + " WHERE ID=" + x + ";" );
			String sql2 = ( "UPDATE ACCOUNT set AMTDEP = " + d + " WHERE ID=" + x + ";" );
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql2);
			System.out.println("Data inserted.");
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}// end method updateDB

	// sumColDB: Method to sum a given column. Returns the total. Consider
	// returning string.
	public static float sumColDB(String colName) {
		float total = 0;
		ResultSet rs;
		
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			rs = stmt.executeQuery( "SELECT SUM(" + colName + ") FROM ACCOUNT");
			total = rs.getFloat(1);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			// System.exit(0);
		}
		//System.out.println(total);
			
		return total;
	}// end method sumColDB

}// end class MoneyDB
