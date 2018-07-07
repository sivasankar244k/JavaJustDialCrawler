package com.justdial.crawler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IpRotationContainer {

	/**
	 * @param args
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException, SQLException {

		try {
			Document document = null;

			document = Jsoup
					.connect("https://www.sslproxies.org/#")
					.userAgent(
							"Mozimlla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
					.get();

			ArrayList<IpAddressPojo> proxyServerList = new ArrayList<IpAddressPojo>();

			Element table = document.select("table").get(0); // select the first
																// table.
			Elements rows = table.select("tr");

			String ip = "";
			String port = "";
			String cntryCode = "";

			for (int i = 1; i < 80; i++) { try {
				// first row is the col names so
					System.out.println("this is the  row number vasing probelm "
							+ i); // skip it.
					Element row = rows.get(i);
					Elements cols = row.select("td");
					IpAddressPojo ipAddressPojo = new IpAddressPojo();

					ip = cols.get(0).text();
					port = cols.get(1).text();
					cntryCode = cols.get(2).text();
					if (null != ip && null != port && null != cntryCode) {

						System.out.println("ip address :" + ip
								+ "         portnum:" + port + "    country code:"
								+ cntryCode);

						ipAddressPojo.setIpAddress(ip);
						ipAddressPojo.setPortNum(port);
						ipAddressPojo.setCountryCode(cntryCode);

						
						//here we can write a method that is that ip address is working fine with our just dial url fetching 
						
						System.setProperty("java.net.useSystemProxies", "true");
						System.setProperty("https.proxyHost",ip);
						System.setProperty("https.proxyPort",port);
						//by this we have set our proxy ip address now we will call a url from just dial and lets check wether it is hidden or not ??
						
						URL url = new URL("https://www.justdial.com/Goa/Arts-Colleges/nct-10891805/page-1");
						//URL url = new URL("https://www.youtube.com/");

						HttpURLConnection connection = (HttpURLConnection) url
								.openConnection();

						connection.setRequestMethod("GET");

						connection.connect();

						int code = connection.getResponseCode();
						System.err.println(code);
						
						
						
						if(code==200)
						{
							//if code is 302  means that ip is working fine 
							//System.out.println("code is "+code+"     ip address is "+ip);
						proxyServerList.add(ipAddressPojo);
						}else if(code==403)
						{
							System.out.println(" ipAddressPojo  403  "+ipAddressPojo);
						}
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println("    ip address is "+ip);
				e.printStackTrace();
			}
			}

			// here we call insert ip addesss method

			insertipAddress(proxyServerList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void insertipAddress(List<IpAddressPojo> pojoList)
			throws SQLException {

		DatabaseConnection dbConnection = new DatabaseConnection();

		Connection connObj = dbConnection.getDatabaseConnection();

		// NAME ADDRESS VOTES RATINGS PHONENUMBER

		String insertTableSQL = "INSERT INTO ip_rotation VALUES (?,?,?)";

		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = connObj.prepareStatement(insertTableSQL);

			for (IpAddressPojo ip : pojoList) {

				try {
					preparedStatement.setString(1, ip.getIpAddress());
					preparedStatement.setInt(2, Integer.parseInt(ip.getPortNum()));
					preparedStatement.setString(3, ip.getCountryCode());

					preparedStatement.executeUpdate();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	

				
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
