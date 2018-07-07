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
import org.sikuli.script.Screen;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.parser.CSSOMParser;

public class JsoupHTMLParser {

	static List<UrlDetailer> urlList ;
	
	
	
	
	
	static String CONFIG_FILE = "C:\\Users\\Siva\\JDC-1\\JustDialCrawler\\config.properties";
	static String SAMPLE_XLSX_FILE_PATH;
	
	static String  GOOGLE_SPREADSHET_URL_PATH;

	public static void main(String args[]) throws SQLException,
			InterruptedException, IOException, NumberFormatException, FindFailed {
		
		
/*		 
		
		//2. we have to start our friends crawlers for crawling 
	
	for(int i=1;i<=10;i++)
	{
		
		try {
			
			File dir = new File("C:\\Users\\Siva\\Desktop\\JARS\\crawler"+i);

	        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "Start","run.bat");

	        pb.directory(dir);

	        Process p = pb.start();
	        
	        

			String[] command = { "cmd.exe", "/C", "Start", "C:\\Users\\Siva\\Desktop\\JARS\\crawler1\\run.bat" };

			Process p = Runtime.getRuntime().exec(command);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}*/
		
		
		


do {
//get data from google spreaad sheett
		

	
		
		
		

		String prjName=System.getProperty("user.dir").substring(System.getProperty("user.dir").lastIndexOf("\\")+1);
		FileInputStream input = null;
		input = new FileInputStream(CONFIG_FILE);

		// load a properties file
		Properties prop = new Properties();
		prop.load(input);
		
		SAMPLE_XLSX_FILE_PATH = prop.getProperty(prjName);
		GOOGLE_SPREADSHET_URL_PATH=prop.getProperty("Google_Spread_Sheet_path");
			
		
		
		
			
		// Creating a Workbook from an Excel file (.xls or .xlsx)

		Workbook workBook;
		urlList=readXLSInput(SAMPLE_XLSX_FILE_PATH);

		try {
			doExtract();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Change ip address and try again");
		}
	}while(true);
	}

	private static void getDatafromGoogleSpreadSheet() throws IOException {
		try {

			File excel = new File(SAMPLE_XLSX_FILE_PATH);

			FileInputStream fis = new FileInputStream(excel);

			HSSFWorkbook book = new HSSFWorkbook(fis);

			HSSFSheet sheet = book.getSheetAt(0);

			ArrayList<String> url = new ArrayList<String>();

			Document document = Jsoup

					.connect(

							GOOGLE_SPREADSHET_URL_PATH)

					.userAgent(

							"Mozimlla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")

					.get();

			// System.out.println(document);

			Element table = document.select("table").get(0); // select the first

			// table.

			Elements rows = table.select("tr");

			for (int k = 0; k <= sheet.getLastRowNum(); k++) {

				String urlValue = sheet.getRow(k).getCell(0).getStringCellValue();

				url.add(urlValue);

			}

			for (int i = 2; i < rows.size(); i++) { // first row is the col

				// names so skip it.

				Element row = rows.get(i);

				Elements cols = row.select("td");

				int lastRowNum = sheet.getLastRowNum();

				if (!url.contains(cols.get(0).text())) {

					System.out.println(cols.get(0).text() + "----------" + cols.get(1).text() + "-------------"

							+ cols.get(2).text() + "-------------" + cols.get(3).text());

					HSSFRow lastrow = sheet.createRow(lastRowNum + 1);

					lastrow.createCell(0).setCellValue(cols.get(0).text());

					lastrow.createCell(1).setCellValue(cols.get(1).text());

					lastrow.createCell(2).setCellValue(cols.get(2).text());

					lastrow.createCell(3).setCellValue(cols.get(3).text());

				}

			}

			// open an OutputStream to save written data into Excel file

			FileOutputStream os = new FileOutputStream(excel);

			book.write(os);

			

			// Close workbook, OutputStream and Excel file to prevent leak

			os.close();

			fis.close();

		} catch (FileNotFoundException fe) {

			fe.printStackTrace();

		} catch (IOException ie) {

			ie.printStackTrace();

		}

	}

	private static List<UrlDetailer> readXLSInput(String SAMPLE_XLSX_FILE_PATH) {
		Workbook workBook;
		urlList= new ArrayList<UrlDetailer>();
		try {
			
			DataFormatter dataFormatter = new DataFormatter();
			workBook = WorkbookFactory.create(new FileInputStream(
					SAMPLE_XLSX_FILE_PATH));
		

			Sheet sheet = workBook.getSheetAt(0);

			for (int rownum = 1; rownum < sheet.getPhysicalNumberOfRows(); rownum++) {

				UrlDetailer urlDetailer = new UrlDetailer();

				Row row = sheet.getRow(rownum);

				urlDetailer.setBaseUrlString(dataFormatter.formatCellValue(row
						.getCell(0)));
				urlDetailer.setFromPageNum(dataFormatter.formatCellValue(row
						.getCell(1)));
				urlDetailer.setToPage(dataFormatter.formatCellValue(row
						.getCell(2)));
				urlDetailer.setStatus(dataFormatter.formatCellValue(row
						.getCell(3)));

				urlList.add(urlDetailer);

			}
			

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return urlList;
	}

	private static void doExtract() throws IOException, SQLException,
			InterruptedException, NumberFormatException, FindFailed {

		boolean isIpchangeRequired;
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection conn = databaseConnection.getDatabaseConnection();
		TreeMap phonemunbers = new TreeMap();

		phonemunbers.put("9d001", "0");
		phonemunbers.put("9d002", "1");
		phonemunbers.put("9d003", "2");
		phonemunbers.put("9d004", "3");
		phonemunbers.put("9d005", "4");
		phonemunbers.put("9d006", "5");
		phonemunbers.put("9d007", "6");
		phonemunbers.put("9d008", "7");
		phonemunbers.put("9d009", "8");
		phonemunbers.put("9d010", "9");
		phonemunbers.put("9d011", "+");
		phonemunbers.put("9d012", "-");
		phonemunbers.put("9d013", ")");
		phonemunbers.put("9d014", "(");

		
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
	 */
	private static void continousCrawler(DatabaseConnection databaseConnection,
			Connection conn, TreeMap phonemunbers)
			throws NumberFormatException, FileNotFoundException, IOException,
			InterruptedException, FindFailed, SQLException {
		// Create a DataFormatter to format and get each cell's value as String


		//continous update input sheet 
		getDatafromGoogleSpreadSheet();
		
		
		InsertdataBeforeFetching();
		



		
		for (int numOffect = 0; numOffect < urlList.size(); numOffect++) {
			Set<Company> setCompany = new HashSet<Company>();

			
			
			
			 urlList = readXLSInput(SAMPLE_XLSX_FILE_PATH);
			Properties prop = new Properties();
			UrlDetailer urlDetailer = urlList.get(numOffect);

			String baseURL = urlDetailer.getBaseUrlString().toString();
			int fromPageNum = Integer.parseInt(urlDetailer.getFromPageNum());

			int toPageNum = Integer.parseInt(urlDetailer.getToPage());

			if(!urlDetailer.getStatus().equalsIgnoreCase("DONE"))
			{
			for (int pageno = fromPageNum; pageno < toPageNum; pageno++) {

				//placed the scrapping insdie the do while to keep cotinue scrapping;
				
			
					System.out.println("in do while loop baseurl withpage "+baseURL+pageno);
				doScrapping(databaseConnection, conn, phonemunbers, setCompany,
						prop, baseURL, pageno);
				if(pageno==(toPageNum-1))
				{
					updateXLSheet(baseURL,setCompany.size());
					
					
				}
			}
		}

			// here you have to send that to cvs file

			ExportToXLS exportToXLS = new ExportToXLS();
			try {
				
				if(setCompany.size()>0)
				{

				System.out
						.println(setCompany.size() + "are wrinting into xls ");

				/*
				 * TreeSet<Company> sortedCompanies = new TreeSet<Company>(new
				 * CompanyRatingsComparator());
				 * sortedCompanies.addAll(setCompany);
				 */
				exportToXLS.createXLS(setCompany);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				if(setCompany.size()>0)
				{
				exportToXLS.createXLS(setCompany);
				}
				e.printStackTrace();
			}
		}
	}

	private static void InsertdataBeforeFetching() throws IOException, SQLException, FindFailed, InterruptedException {
		
		Screen offWindscribe=new Screen();
		offWindscribe.click("C:\\Users\\Siva\\Documents\\Sikoli-images\\onButtonWindscribe.PNG");


		Thread.sleep(60000);
		
		
		String crawlerOutput = "C:\\Users\\Siva\\Desktop\\CrawlerOutput";	
		String justDialdata = "C:\\Users\\Siva\\Desktop\\JustdialData";

		String justDialOldData = "C:\\Users\\Siva\\Desktop\\JustdialOldData";
		
		
		File folder = new File(crawlerOutput);

		File[] listOfFiles = folder.listFiles();
		if(listOfFiles.length>0)
		{
		
		moveFetecedData(justDialdata,justDialOldData);
		moveFetecedData(crawlerOutput,justDialdata);
		InsertDataToPGTable insertDataToPGTable=new InsertDataToPGTable();
		insertDataToPGTable.insertData();
		insertDataToPGTable.executeQueries();
		
		}
		
	}

	private static void moveFetecedData(String inputDirectory,String outputDirectory ) {
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

					source = inputDirectory + "//"+listOfFiles[i].getName();

					destination = outputDirectory +"//"+ listOfFiles[i].getName();

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

	private static void updateXLSheet(String url,int numRecords) {
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
					
					System.out.println("url IS GETTING UPDATED AS done "+url);
					row.getCell(3).setCellValue("DONE");
					//row.getCell(4).setCellValue(String.valueOf(numRecords));
					
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
	private static void doScrapping(DatabaseConnection databaseConnection,
			Connection conn, TreeMap phonemunbers, Set<Company> setCompany,
			Properties prop, String baseURL, int pageno)
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
			 * System.setProperty("https.proxyHost",
			 * prop.getProperty("ipaddress"));
			 * System.out.println(System.getProperties());
			 * System.setProperty("https.proxyPort",
			 * prop.getProperty("portnum"));
			 */
			// System.out.println("Ip addres "+System.getProperty("https.proxyHosst"));

			try {

				document = Jsoup
						.connect(baseURL + pageno)
						.userAgent(
								"Mozimlla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
						.get();
			} catch (Exception e) {
				
				
				if (e instanceof UnknownHostException) {
					System.err.println("internet connection is gone "
							+ ((UnknownHostException) e).getMessage());
					
					System.out.println("waiting for internet connection");

					do{
						
						
					}while(!netIsAvailable());
					
					System.out.println("intert connection came");
				}
				
				if (null == document) {
					document = Jsoup
							.connect(baseURL + pageno)
							.userAgent(
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
					
					System.out.println("uri-------"+uri);
					innerDoc = Jsoup
							.connect(uri.trim())
							.userAgent(
									"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
							.get();

					// comp-contact

					Elements innerlinks = innerDoc
							.getElementsByClass("company-details");
					Elements selectStles = innerDoc.select("style");

					Map phSwapArray = getPhSwapArray(selectStles);

					for (Element innerLink : innerlinks) {

						Company comp = new Company();

						String companyName = innerLink
								.getElementsByClass("fn").text();
						String companyRatings = innerLink
								.getElementsByClass("rating").text();
						String companyVotes = innerLink
								.getElementsByClass("votes").text();
						String companyaddress = null;
						StringBuilder companyPhonenumber = new StringBuilder();

						// System.out.println(innerLink.getElementsByTag("Span"));

						// company contact details

						Elements companyContactLinks = innerDoc
								.getElementsByClass("comp-contact");

						for (Element companyConactLink : companyContactLinks) {

							companyaddress = companyConactLink
									.getElementsByClass("lng_add")
									.text();

							Elements phonenumberLinks = companyConactLink
									.getElementsByClass("mobilesv")
									.addClass("mobilesv");
							int i = 1;
							for (Element phonenumberLink : phonenumberLinks) {

								if (i == 14) {
									System.out.print(",");
								}
								companyPhonenumber
										.append(phonemunbers.get(phSwapArray
												.get(phonenumberLink
														.toString()
														.replace(
																"<span class=\"mobilesv ",
																"")
														.replace(
																"\"></span>",
																""))));

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
							System.out.println(comp.toString());
							databaseConnection.insertRecordIntoTable(
									conn, comp);
						}
						System.out.println(comp.toString());
						setCompany.add(comp);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ExportToXLS exportToXLS = new ExportToXLS();

					System.out.println(setCompany.size()
							+ "are wrinting into xls ");

					/*
					 * TreeSet<Company> sortedCompanies = new
					 * TreeSet<Company>(new CompanyRatingsComparator());
					 * sortedCompanies.addAll(setCompany);
					 */
					exportToXLS.createXLS(setCompany);
					// waitForIpChnage(prop,uri.trim());

				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

			if (e instanceof UnknownHostException) {
				System.err.println("internet connection is gone "
						+ ((UnknownHostException) e).getMessage());

				System.out.println("waiting for internet connection");
				do{
					
				
				}while(!netIsAvailable());
				
				System.out.println("intert connection came");
			}

			if (e instanceof HttpStatusException) {
				int statusCode = ((HttpStatusException) e)
						.getStatusCode();

				if (statusCode == 403) {
					System.err.println(((HttpStatusException) e)
							.getStatusCode()
							+ "  "
							+ ((HttpStatusException) e).getUrl());
					waitForIpChnage(prop,
							((HttpStatusException) e).getUrl());
				}

			} else {
				e.printStackTrace();
			}
		} finally {

		}
	}

	private
	static void waitForIpChnage(Properties prop, String url)
			throws FileNotFoundException, IOException, InterruptedException, FindFailed {
		
		do {
			
			System.out.println("ip change required");
			//here we have to work with Sikuli
			Sikuli ipchanger=new Sikuli();
		
			ipchanger.doIPChnage(url);
			
		}while (isIPchanged(url));
		
		System.out.println("ip  has changed suceessfully");
	}

	private static Map getPhSwapArray(Elements selectStles) {
		String styleString = selectStles.get(1).toString();
		String repaledString = styleString.replace("\"", "");
		String replaceHash = repaledString.replace("\\", "");

		Map phoneMap = new HashMap();
		InputSource styleTextStream = new InputSource(new StringReader(
				replaceHash));

		CSSOMParser parser = new CSSOMParser();

		// parse and create a stylesheet composition

		CSSStyleSheet parsedStyleSheet = null;
		try {
			parsedStyleSheet = parser.parseStyleSheet(styleTextStream, null,
					null);
			CSSRuleList ruleList = parsedStyleSheet.getCssRules();

			for (int i = 0; i < ruleList.getLength(); i++) {
				CSSRule rule = ruleList.item(i);
				if (rule instanceof CSSStyleRule) {
					CSSStyleRule styleRule = (CSSStyleRule) rule;

					CSSStyleDeclaration styleDeclaration = styleRule.getStyle();

					for (int j = 0; j < styleDeclaration.getLength(); j++) {
						String property = styleDeclaration.item(j);
						if (property.equalsIgnoreCase("content")
								&& styleDeclaration
										.getPropertyCSSValue(property)
										.getCssText().contains("9d0")) {
							phoneMap.put(
									styleRule.getSelectorText()
											.replace(":before", "")
											.replace(".", ""), styleDeclaration
											.getPropertyCSSValue(property)
											.getCssText().replace(",", ""));
						}

					}

				} // end of StyleRule instance test
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		// System.out.println(parsedStyleSheet);

		return phoneMap;
	}

	private static boolean netIsAvailable() {

		try {

			final URL url = new URL("http://www.google.com");

			final URLConnection conn = url.openConnection();

			conn.connect();

			conn.getInputStream().close();

			return true;

		} catch (MalformedURLException e) {

			throw new RuntimeException(e);

		} catch (IOException e) {

			return false;

		}

	}
	
	private static boolean isIPchanged(String url) throws InterruptedException {
/*
		FileInputStream input = null;
		input = new FileInputStream(CONFIG_FILE);

		// load a properties file
		prop.load(input);*/

		long yourmilliseconds = System.currentTimeMillis();

		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

		Date resultdate = new Date(yourmilliseconds);

	

		System.out.println("ip change required-------------"
				+ sdf.format(resultdate) + "" + url);
		Thread.sleep(10000);

		try {
			Document document = Jsoup
					.connect(url)
					.userAgent(
							"Mozimlla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
					.get();
			
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}

	}
}
