package com.justdial.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

import com.justdial.crawler.data.SheetsQuickstart;
import com.justdial.crawler.utility.Util;
import com.steadystate.css.parser.CSSOMParser;

public class JsoupHTMLParser implements Phonenumbers {

	static List<UrlDetailer> urlList;

	static String CONFIG_FILE = "C:\\Users\\Siva\\git\\JDCrawler\\JustDialCrawler\\config.properties";
	static String SAMPLE_XLSX_FILE_PATH;

	static String GOOGLE_SPREADSHET_URL_PATH;

	public static void main(String args[]) throws SQLException, InterruptedException, IOException,
			NumberFormatException, FindFailed, GeneralSecurityException {

		/*
		 * 
		 * //2. we have to start our friends crawlers for crawling
		 * 
		 * for(int i=1;i<=10;i++) {
		 * 
		 * try {
		 * 
		 * File dir = new File("C:\\Users\\Siva\\Desktop\\JARS\\crawler"+i);
		 * 
		 * ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "Start","run.bat");
		 * 
		 * pb.directory(dir);
		 * 
		 * Process p = pb.start();
		 * 
		 * 
		 * 
		 * String[] command = { "cmd.exe", "/C", "Start",
		 * "C:\\Users\\Siva\\Desktop\\JARS\\crawler1\\run.bat" };
		 * 
		 * Process p = Runtime.getRuntime().exec(command);
		 * 
		 * } catch (IOException ex) { ex.printStackTrace(); }
		 * 
		 * }
		 */

		do {
			// get data from google spreaad sheett

			String prjName = System.getProperty("user.dir")
					.substring(System.getProperty("user.dir").lastIndexOf("\\") + 1);
			FileInputStream input = null;
			input = new FileInputStream(CONFIG_FILE);

			// load a properties file
			Properties prop = new Properties();
			prop.load(input);

			SAMPLE_XLSX_FILE_PATH = prop.getProperty(prjName);
			GOOGLE_SPREADSHET_URL_PATH = prop.getProperty("Google_Spread_Sheet_path");

			// Creating a Workbook from an Excel file (.xls or .xlsx)

			Workbook workBook;
			urlList = Util.readXLSInput(SAMPLE_XLSX_FILE_PATH);

			try {
				doExtract();

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Change ip address and try again");
			}
		} while (true);
	}

	private static void doExtract() throws IOException, SQLException, InterruptedException, NumberFormatException,
			FindFailed, GeneralSecurityException {

		DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
		Connection conn = databaseConnection.getDatabaseConnection();
		TreeMap phonemunbers = Util.getPhoneNumberMap();

		continousCrawler(databaseConnection, conn, phonemunbers);

	}

	/**
	 * @param databaseConnection
	 * @param conn
	 * @param phonemunbers
	 * @throws NumberFormatException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws FindFailed
	 * @throws SQLException
	 * @throws GeneralSecurityException
	 */
	private static void continousCrawler(DatabaseConnection databaseConnection, Connection conn, TreeMap phonemunbers)
			throws NumberFormatException, FileNotFoundException, IOException, InterruptedException, FindFailed,
			SQLException, GeneralSecurityException {
		// Create a DataFormatter to format and get each cell's value as String

		// continous update input sheet
		Util.getDatafromGoogleSpreadSheet();

		for (int numOffect = 0; numOffect < urlList.size(); numOffect++) {
			Set<Company> setCompany = new HashSet<Company>();

			urlList = Util.readXLSInput(SAMPLE_XLSX_FILE_PATH);
			Properties prop = new Properties();

			UrlDetailer urlDetailer = urlList.get(numOffect);

			String baseURL = urlDetailer.getBaseUrlString().toString();
			int fromPageNum = Integer.parseInt(urlDetailer.getFromPageNum());

			int toPageNum = Integer.parseInt(urlDetailer.getToPage());

			if (!urlDetailer.getStatus().equalsIgnoreCase("DONE")) {

				if (Util.isDBNotConnected()) {
					System.out.println("entering into the data updation mode");
					Region region = new Region(1260, 240, 80, 50);

					region.click();
					Thread.sleep(20000);
					System.out.println("INSIDE THE CLICK");

					InsertdataBeforeFetching();
					
					region.click();
					Thread.sleep(60000);

				}
				

					for (int pageno = fromPageNum; pageno < toPageNum; pageno++) {

						// placed the scrapping insdie the do while to keep cotinue scrapping;
						
						
						

						System.out.println("in do while loop baseurl withpage " + baseURL + pageno);
						doScrapping(databaseConnection, conn, phonemunbers, setCompany, prop, baseURL, pageno);
						if (pageno == (toPageNum - 1)) {
							updateXLSheet(baseURL, setCompany.size());

						}
					}
				
			}

			// here you have to send that to cvs file

			ExportToXLS exportToXLS = new ExportToXLS();
			try {

				if (setCompany.size() > 0) {

					System.out.println(setCompany.size() + "are wrinting into xls ");

					/*
					 * TreeSet<Company> sortedCompanies = new TreeSet<Company>(new
					 * CompanyRatingsComparator()); sortedCompanies.addAll(setCompany);
					 */
					exportToXLS.createXLS(setCompany);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				if (setCompany.size() > 0) {
					exportToXLS.createXLS(setCompany);
				}
				e.printStackTrace();
			}
		}
	}

	private static void InsertdataBeforeFetching()
			throws IOException, SQLException, FindFailed, InterruptedException, GeneralSecurityException {

		String crawlerOutput = "C:\\Users\\Siva\\Desktop\\CrawlerOutput";
		String justDialdata = "C:\\Users\\Siva\\Desktop\\JustdialData";

		String justDialOldData = "C:\\Users\\Siva\\Desktop\\JustdialOldData";

		File folder = new File(crawlerOutput);

		File[] listOfFiles = folder.listFiles();
		if (listOfFiles.length > 0) {

			moveFetecedData(justDialdata, justDialOldData);
			moveFetecedData(crawlerOutput, justDialdata);
			InsertDataToPGTable insertDataToPGTable = new InsertDataToPGTable();
			insertDataToPGTable.insertData();
			insertDataToPGTable.executeCityUpdateQueries();
			List<List<Object>> fectdData = insertDataToPGTable.executeSelectQueries();

			// now we neeed to update the spread sheet

			SheetsQuickstart quickstart = new SheetsQuickstart();
			quickstart.updateGoogleSpreadSheet(fectdData);
		}

	}

	private static void moveFetecedData(String inputDirectory, String outputDirectory) {
		try {

			// STEP1 : we have to MOve justdial folder data to Justdial Old data

			InputStream inStream;

			OutputStream outStream;

			String source = "";

			String destination = "";

			File folder = new File(inputDirectory);

			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {

				if (listOfFiles[i].isFile()) {

					source = inputDirectory + "//" + listOfFiles[i].getName();

					destination = outputDirectory + "//" + listOfFiles[i].getName();

					File afile = new File(source);

					File bfile = new File(destination);

					inStream = new FileInputStream(afile);

					outStream = new FileOutputStream(bfile);

					byte[] buffer = new byte[1024];

					int length;

					// copy the file content in bytes

					while ((length = inStream.read(buffer)) > 0) {

						outStream.write(buffer, 0, length);

					}

					inStream.close();

					outStream.close();

					// delete the original file

					afile.delete();

					System.out.println("File is copied successful!");

				}

			}

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	private static void updateXLSheet(String url, int numRecords) {
		// this is the righ time to update the xls sheet as DONE

		try {

			File excel = new File(SAMPLE_XLSX_FILE_PATH);

			FileInputStream fis = new FileInputStream(excel);

			HSSFWorkbook book = new HSSFWorkbook(fis);

			HSSFSheet sheet = book.getSheetAt(0);

			Iterator<Row> itr = sheet.iterator();

			// Iterating over Excel file in Java

			while (itr.hasNext()) {

				Row row = itr.next();

				if (row.getCell(0).getStringCellValue().equalsIgnoreCase(url)) {

					System.out.println("url IS GETTING UPDATED AS done " + url);
					row.getCell(3).setCellValue("DONE");
					row.getCell(4).setCellValue(String.valueOf(numRecords));

				}

			}
			FileOutputStream os = new FileOutputStream(excel);

			book.write(os);

			System.out.println("Writing on Excel file Finished ...");

			// Close workbook, OutputStream and Excel file to prevent leak

			os.close();

			fis.close();

		} catch (FileNotFoundException fe) {

			fe.printStackTrace();

		} catch (IOException ie) {

			ie.printStackTrace();

		}
	}

	/**
	 * @param databaseConnection
	 * @param conn
	 * @param phonemunbers
	 * @param setCompany
	 * @param prop
	 * @param baseURL
	 * @param pageno
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws FindFailed
	 */
	private static void doScrapping(DatabaseConnection databaseConnection, Connection conn, TreeMap phonemunbers,
			Set<Company> setCompany, Properties prop, String baseURL, int pageno)
			throws FileNotFoundException, IOException, InterruptedException, FindFailed {
		try {

			Document document = null;
			String uri = null;
			// Get Document object after parsing the html from given
			// url.

			FileInputStream input = null;
			input = new FileInputStream(CONFIG_FILE);

			// load a properties file
			prop.load(input);

			/*
			 * System.out.println(prop.getProperty("ipaddress"));
			 * System.out.println(prop.getProperty("portnum"));
			 * 
			 * System.out.println(baseURL + pageno);
			 * System.setProperty("java.net.useSystemProxies", "true");
			 * System.setProperty("https.proxyHost", prop.getProperty("ipaddress"));
			 * System.out.println(System.getProperties());
			 * System.setProperty("https.proxyPort", prop.getProperty("portnum"));
			 */
			// System.out.println("Ip addres "+System.getProperty("https.proxyHosst"));

			try {

				document = Jsoup.connect(baseURL + pageno).userAgent(
						"Mozimlla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
						.get();
			} catch (Exception e) {

				if (e instanceof UnknownHostException) {
					System.err.println("internet connection is gone " + ((UnknownHostException) e).getMessage());

					System.out.println("waiting for internet connection");

					do {

					} while (!Util.netIsAvailable());

					System.out.println("intert connection came");
				}

				if (null == document) {
					document = Jsoup.connect(baseURL + pageno).userAgent(
							"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
							.get();
				}
			}

			Elements links = document.getElementsByClass("cntanr");

			// Iterate links and print link attributes.
			for (Element link : links) {
				try {
					Document innerDoc;
					uri = link.attr("data-href").toString();

					System.out.println("uri-------" + uri);
					innerDoc = Jsoup.connect(uri.trim()).userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
							.get();

					// comp-contact

					Elements innerlinks = innerDoc.getElementsByClass("company-details");
					Elements selectStles = innerDoc.select("style");

					Map phSwapArray = Util.getPhSwapArray(selectStles);

					for (Element innerLink : innerlinks) {

						Runnable t=new Runnable() {
							
							@Override
							public void run() {
								extractInnerLinks(conn, phonemunbers, setCompany, innerDoc, phSwapArray, innerLink);
								
							}
						};
						new Thread(t).start();
						
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ExportToXLS exportToXLS = new ExportToXLS();

					System.out.println(setCompany.size() + "are wrinting into xls ");

					/*
					 * TreeSet<Company> sortedCompanies = new TreeSet<Company>(new
					 * CompanyRatingsComparator()); sortedCompanies.addAll(setCompany);
					 */
					exportToXLS.createXLS(setCompany);
					// waitForIpChnage(prop,uri.trim());

				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

			if (e instanceof UnknownHostException) {
				System.err.println("internet connection is gone " + ((UnknownHostException) e).getMessage());

				System.out.println("waiting for internet connection");
				do {

				} while (!Util.netIsAvailable());

				System.out.println("intert connection came");
			}

			if (e instanceof HttpStatusException) {
				int statusCode = ((HttpStatusException) e).getStatusCode();

				if (statusCode == 403) {
					System.err.println(
							((HttpStatusException) e).getStatusCode() + "  " + ((HttpStatusException) e).getUrl());
					Util.waitForIpChnage(prop, ((HttpStatusException) e).getUrl());
				}

			} else {
				e.printStackTrace();
			}
		} finally {

		}
	}

	private static void extractInnerLinks(Connection conn, TreeMap phonemunbers, Set<Company> setCompany,
			Document innerDoc, Map phSwapArray, Element innerLink) {
		Company comp = new Company();

		String companyName = innerLink.getElementsByClass("fn").text();
		String companyRatings = innerLink.getElementsByClass("rating").text();
		String companyVotes = innerLink.getElementsByClass("votes").text();
		String companyaddress = null;
		StringBuilder companyPhonenumber = new StringBuilder();

		// System.out.println(innerLink.getElementsByTag("Span"));

		// company contact details

		Elements companyContactLinks = innerDoc.getElementsByClass("comp-contact");

		for (Element companyConactLink : companyContactLinks) {

			companyaddress = companyConactLink.getElementsByClass("lng_add").text();

			Elements phonenumberLinks = companyConactLink.getElementsByClass("mobilesv")
					.addClass("mobilesv");
			int i = 1;
			for (Element phonenumberLink : phonenumberLinks) {

				if (i == 14) {
					System.out.print(",");
				}
				companyPhonenumber.append(phonemunbers.get(phSwapArray.get(phonenumberLink.toString()
						.replace("<span class=\"mobilesv ", "").replace("\"></span>", ""))));

				i++;

			}

		}

		comp.setName(companyName);
		comp.setNumvotes(companyVotes);
		comp.setAddress(companyaddress);
		comp.setPhonenum(companyPhonenumber.toString());
		comp.setRatings(companyRatings);

		// here it self insert into database

		if (null != conn && null != comp) {
			//System.out.println(comp.toString());
			//databaseConnection.insertRecordIntoTable(conn, comp);
		}
		System.out.println(comp.toString());
		setCompany.add(comp);
	}

}
