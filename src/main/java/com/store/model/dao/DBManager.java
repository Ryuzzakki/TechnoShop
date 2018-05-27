package com.store.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class DBManager {

	final String DB_IP = "127.0.0.1";
	final String DB_PORT = "3306";
	final String DB_DBNAME = "pizza_store";
	final String DB_USER = "root";
	final String DB_PASS = "1234";

	private Connection con;

	private DBManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found or failed to load. Check your libraries");
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_DBNAME, DB_USER,
					DB_PASS);
		} catch (SQLException e) {
			System.out.println("Check database connection.");
		}

	}

	public Connection getConnection() {
		return con;
	}

	public void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Check database connection.");
			}
		}
	}
}
