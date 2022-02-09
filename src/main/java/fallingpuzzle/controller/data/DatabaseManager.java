package fallingpuzzle.controller.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Driver
public class DatabaseManager {
	
	private static String url = "jdbc:sqlite:" + "src/main/resources/data/data.db";
	private static DatabaseManager instance;
	private static Connection connection;
	
	//DatabaseManager creation methods
	private DatabaseManager() {}
	private static DatabaseManager initialize() {
		if( instance == null )
			instance = new DatabaseManager();
		return instance;
	}
	
	//gets connection to db ( initializes DatabaseManager if it hasn't already been done )
	public static Connection getConnection() {
		initialize();
		try {
			 if( connection == null || connection.isClosed() )
				 connection = DriverManager.getConnection( url );
		} catch (SQLException e) {
			System.err.println("Unable to connect to db");
			e.printStackTrace();
		}
		return connection;
	}
	
		
}
