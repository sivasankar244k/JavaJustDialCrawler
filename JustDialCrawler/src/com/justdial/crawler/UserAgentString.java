package com.justdial.crawler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UserAgentString {

	/**
	 * @param args
	 * @throws IOException
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, SQLException {

		Document document = null;

		document = Jsoup
				.connect(
						"http://www.useragentstring.com/pages/useragentstring.php?name=ChromePlus")
				.userAgent(
						"Mozimlla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
				.get();

		Elements links = document.select("li a");

		ArrayList<UserAgentPojo> userAgentList = new ArrayList<UserAgentPojo>();

		UserAgentPojo agentPojo = new UserAgentPojo();

		for (Element element : links) {
			if (null != element.text() && !"".equalsIgnoreCase(element.text())) {

				System.out.println(element.text());
				
				insertUserAgentString(element.text());
				
				/*agentPojo.setUserAgent(element.text());

				userAgentList.add(agentPojo);*/
			}

		}
		
		

	}

	public static void insertUserAgentString(String userAgent )
			throws SQLException {

		DatabaseConnection dbConnection = new DatabaseConnection();

		Connection connObj = dbConnection.getDatabaseConnection();

		// NAME ADDRESS VOTES RATINGS PHONENUMBER

		String insertTableSQL = "INSERT INTO user_agent VALUES (?)";

		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = connObj.prepareStatement(insertTableSQL);

		

				try {
					preparedStatement.setString(1, userAgent);

					preparedStatement.executeUpdate();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

	


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed tp insert!");

		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}
}
