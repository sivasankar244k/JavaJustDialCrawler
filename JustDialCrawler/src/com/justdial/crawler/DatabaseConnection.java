package com.justdial.crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseConnection {
	
	
	 
	 
	 
	 
	 
	//Step 1  

     // create a JDBCSingleton class.  

    //static member holds only one instance of the JDBCSingleton class.  

            

        private static DatabaseConnection jdbc;  

          

    //JDBCSingleton prevents the instantiation from any other class.  

      private DatabaseConnection() {  }  

       

   //Now we are providing gloabal point of access.  

        public static DatabaseConnection getInstance() {    

                                    if (jdbc==null)  

                                  {  

                                           jdbc=new  DatabaseConnection();  

                                  }  

                        return jdbc;  

            }  

	public Connection getDatabaseConnection() throws SQLException {

		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/postgres", "postgres",
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

		return connection;
	}

	public void insertRecordIntoTable(Connection dbConnection, Company compObj)
			throws SQLException {

		PreparedStatement preparedStatement = null;

		// NAME ADDRESS VOTES RATINGS PHONENUMBER

		String insertTableSQL = "INSERT INTO just_dial VALUES (?,?,?,?,?)";

		try {

			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setString(1, compObj.getName());
			preparedStatement.setString(2, compObj.getAddress());
			preparedStatement.setString(3, compObj.getNumvotes());
			preparedStatement.setString(4, compObj.getRatings());
			preparedStatement.setString(5, compObj.getPhonenum());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {/*
					 * 
					 * if (preparedStatement != null) {
					 * preparedStatement.close(); }
					 * 
					 * if (dbConnection != null) { dbConnection.close(); }
					 */
		}

	}

	public ArrayList<IpAddressPojo> getIPAddresses(Connection dbConnection)
			throws SQLException {

		ArrayList<IpAddressPojo> ipList = new ArrayList<IpAddressPojo>();

		String selectTableSQL = "SELECT ipaddress,portnum,countrycode from ip_rotation";
		Statement statement = dbConnection.createStatement();
		ResultSet rs = statement.executeQuery(selectTableSQL);
		while (rs.next()) {
			String ipaddress = rs.getString("ipaddress");
			int portnumber = rs.getInt("portnum");
			String country = rs.getString("countrycode");

			if (null != ipaddress && null != country) {
				IpAddressPojo ip1 = new IpAddressPojo();
				ip1.setIpAddress(ipaddress);
				ip1.setPortNum(String.valueOf(portnumber));
				ip1.setCountryCode(country);
				ipList.add(ip1);
			}
		}

		return ipList;
	}
	
	
	public void removeIPAddresses(Connection dbConnection, String ipaddress)
			throws SQLException {

		String deleteSQL = "DELETE from ip_rotation WHERE ipaddress = ?";
		PreparedStatement preparedStatement = dbConnection
				.prepareStatement(deleteSQL);
		preparedStatement.setString(1, ipaddress);
		// execute delete SQL stetement
		preparedStatement.executeUpdate();

	}

	public  ArrayList<UserAgentPojo> getUserAgents (Connection dbConnection)
			throws SQLException {

		ArrayList<UserAgentPojo> userAgentList = new ArrayList<UserAgentPojo>();

		String selectTableSQL = "SELECT useragent from user_agent";
		Statement statement = dbConnection.createStatement();
		ResultSet rs = statement.executeQuery(selectTableSQL);
		while (rs.next()) {
			String useragent = rs.getString("useragent");

			if (null != useragent) {
				UserAgentPojo userag1 = new UserAgentPojo();

				userag1.setUserAgent(useragent);

				userAgentList.add(userag1);
			}
		}

		return userAgentList;
	}

}