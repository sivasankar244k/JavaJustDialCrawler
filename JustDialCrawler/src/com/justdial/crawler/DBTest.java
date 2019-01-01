package com.justdial.crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBTest {

	public static void main(String args[]) {

		System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
					"TeamKuppala!544");

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();

		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");

		}

	}
}