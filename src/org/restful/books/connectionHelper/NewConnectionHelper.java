package org.restful.books.connectionHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewConnectionHelper
{
	private String url;
	private static NewConnectionHelper instance;

	private NewConnectionHelper()
	{
    	String driver = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			url = "jdbc:mysql://localhost:3306/library";
            /*ResourceBundle bundle = ResourceBundle.getBundle("cellar");
            driver = bundle.getString("jdbc.driver");
            Class.forName(driver);
            url=bundle.getString("jdbc.url");*/
			
			driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			url = "jdbc:mysql://localhost:3306/library";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			instance = new NewConnectionHelper();
		}
		try {
			return DriverManager.getConnection(instance.url,"root","global123");
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public static void close(Connection connection)
	{
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}