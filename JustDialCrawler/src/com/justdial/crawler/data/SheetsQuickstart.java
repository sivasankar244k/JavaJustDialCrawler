package com.justdial.crawler.data;
import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.security.GeneralSecurityException;

import java.util.Arrays;

import java.util.Collections;

import java.util.List;



import com.google.api.client.auth.oauth2.Credential;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.client.http.javanet.NetHttpTransport;

import com.google.api.client.json.JsonFactory;

import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.sheets.v4.Sheets;

import com.google.api.services.sheets.v4.SheetsScopes;

import com.google.api.services.sheets.v4.model.UpdateValuesResponse;



public class SheetsQuickstart {

	private static final String APPLICATION_NAME = "dataAnalytics";

	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private static final String CREDENTIALS_FOLDER = "credentials"; // Directory

																	// to store

																	// user

																	// credentials.



	/**

	 * Global instance of the scopes required by this quickstart. If modifying

	 * these scopes, delete your previously saved credentials/ folder.

	 */

	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

	





	private static final String CLIENT_SECRET_DIR = "C:\\Users\\Siva\\git\\JDCrawler\\JustDialCrawler\\credentials.json";

	private List<List<String>> asList;



	/**

	 * Creates an authorized Credential object.

	 * 

	 * @param HTTP_TRANSPORT

	 *            The network HTTP Transport.

	 * @return An authorized Credential object.

	 * @throws IOException

	 *             If there is no client_secret.

	 */

	private  Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

		// Load client secrets.

		InputStream in = new FileInputStream(

				new File(CLIENT_SECRET_DIR));

		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));



		// Build flow and trigger user authorization request.

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,

				clientSecrets, SCOPES)

						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))

						.setAccessType("offline").build();

		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

	}



	/**

	 * Prints the names and majors of students in a sample spreadsheet:

	 * https://docs.google.com/spreadsheets/d/

	 * 1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit

	 */

	public  void updateGoogleSpreadSheet(List<List<Object>> data) throws IOException, GeneralSecurityException {

		// Build a new authorized API client service.

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

		final String spreadsheetId = "1LupAl3lDrZm98te_SGglHpid-vQwoKUBAtqlr8x0y3M";

		final String range = "Sheet1!A1:D";



		Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))

				.setApplicationName(APPLICATION_NAME).build();

		

		



		com.google.api.services.sheets.v4.model.ValueRange response = service.spreadsheets().values()

				.get(spreadsheetId, range).execute();



		List<List<Object>> values = response.getValues();


		

		

		

		

		

		

		/*    Here we have to write the data */

		
		/*asList = Arrays.asList(Arrays.asList("Expenses","January"), Arrays.asList("Rangeela", "30"),

				Arrays.asList("Ramajogayya", "10"), Arrays.asList("Naga February"), Arrays.asList("Maneela", "20"),

				Arrays.asList("Heroosiemaa", "5"));*/

		com.google.api.services.sheets.v4.model.ValueRange body = new com.google.api.services.sheets.v4.model.ValueRange()

				.setValues(data);



		UpdateValuesResponse result = service.spreadsheets().values().update(spreadsheetId, "A1", body)

				.setValueInputOption("RAW").execute();

		

		

		System.out.println(result);

		

		

		

		

		

		

		

	}

}