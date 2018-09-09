package db.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class GenerateConnection {
	private static final String DATABASE_INFO_FILE_NAME = "db_info.properties";
	private static final DB_Info info = new DB_Info(DATABASE_INFO_FILE_NAME); 
	
	
	static {
		DriverClassLoader.load(info.readProperties("DATABASE_DRIVER"));
	}
	
	public static Connection getConnection() {
		String url = info.readProperties("DATABASE_URL");
		String user = info.readProperties("DATABASE_ID");
		String password = info.readProperties("DATABASE_PASSWORD");
		
		
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
