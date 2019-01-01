package com.justdial.crawler.data;

import java.sql.Connection;
import java.sql.SQLException;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;

import com.justdial.crawler.DatabaseConnection;

public class Dataanalytics {

	public static void main(String[] args) throws FindFailed, SQLException, InterruptedException {

		do {

			Region region = new Region(1260, 240, 80, 50);

			region.click();
			Thread.sleep(30000);
		

		} while (isDBConnected());

		// so fianlly data base connection is made you can now find out the statiscs

		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
		Connection conn = databaseConnection.getDatabaseConnection();

	}

	

	private static boolean isDBConnected() throws SQLException {
		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
		Connection conn = databaseConnection.getDatabaseConnection();
		if (conn != null) {
			return false;
		} else {
			return true;
		}
	}
}
