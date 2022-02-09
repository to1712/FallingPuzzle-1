package fallingpuzzle.controller.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SettingsDAO {
	
	//Ogni "tabella" del db dovrebbe avere un DAO ( Data Access Object )
	//ed il rispettivo DTO ( Data Transfer Object )
	//Es: SettingsDAO - Setting
	//Il DAO contiene i methodi CRUDS per le query ad db
	//Il DTO è la rappresentazione sotto forma di oggetto
	//di una riga della tabella corrispondente nel db
	//Es: nel mio db ho una tabella che si chiama "Settings"
	//La classe SettingsDAO mi permette di fare le query su questa tabella
	//La classe Setting corrisponde a una riga della tabella
	
	//SettingsDAO creation methods
	private SettingsDAO() {}
	private static SettingsDAO instance;
	private static SettingsDAO initialize() {
		if( instance == null ) {
			instance = new SettingsDAO();
			createTable();
		}
		return instance;
	}
	
	private static void createTable() {
		try {
			Statement statement = DatabaseManager.getConnection().createStatement();
			String sql = "create table if not exists"
				  + " Settings("
				  + " name varchar(255) primary key,"
				  + " value varchar(255) ); ";		
			statement.executeUpdate( sql );	
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insert( Setting setting ) {
		initialize();
		try {
			String sql = "insert or replace into Settings values( ?, ? ); ";
			PreparedStatement statement = DatabaseManager.getConnection().prepareStatement( sql );
			statement.setString( 1, setting.getName() );
			statement.setString( 2, setting.getValue() );
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Setting getById( String name ) {
		initialize();
		Setting setting = null;
		try {
			String sql = "select * from Settings where name = ?; ";
			PreparedStatement statement = DatabaseManager.getConnection().prepareStatement( sql );
			statement.setString( 1, name );
			ResultSet rs = statement.executeQuery();
			if( rs.next() )
				setting = new Setting( rs.getString( 1 ), rs.getString( 2 ) );	
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return setting;
	}
	
	public static List<Setting> getAll() {
		initialize();
		List<Setting> settings = new ArrayList<Setting>();
		try {
			String sql = "select * from Settings";
			Statement statement = DatabaseManager.getConnection().createStatement();
			ResultSet rs = statement.executeQuery( sql );
			while( rs.next() ) {
				Setting setting = new Setting( rs.getString(0), rs.getString( 1 ) );
				settings.add( setting );
			}					
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return settings;
	}
}
