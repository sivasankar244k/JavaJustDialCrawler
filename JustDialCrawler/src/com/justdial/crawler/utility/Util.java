package com.justdial.crawler.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sikuli.script.FindFailed;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

import com.justdial.crawler.DatabaseConnection;
import com.justdial.crawler.Sikuli;
import com.justdial.crawler.UrlDetailer;
import com.steadystate.css.parser.CSSOMParser;

public class Util {

	static String SAMPLE_XLSX_FILE_PATH;

	static String GOOGLE_SPREADSHET_URL_PATH;
	
	static String CONFIG_FILE = "C:\\Users\\Siva\\git\\JDCrawler\\JustDialCrawler\\config.properties";
	
	/**
	 * @return  jsut doial phone number image mapping 
	 */
	public static TreeMap getPhoneNumberMap() {
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
		return phonemunbers;
	}
	
	
	

	/**
	 * @return
	 * @throws SQLException  to chcek whetehre db i sconneced or not 
	 */
	public static boolean isDBNotConnected() throws SQLException {
		DatabaseConnection databaseConnection =  DatabaseConnection.getInstance();
		Connection conn = databaseConnection.getDatabaseConnection();
		if (conn != null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	
	/**
	 * @param SAMPLE_XLSX_FILE_PATH
	 * @return  to read excel input to fetch the urls data 
	 */
	public  static List<UrlDetailer> readXLSInput(String SAMPLE_XLSX_FILE_PATH) {
		Workbook workBook;
		List urlList= new ArrayList<UrlDetailer>();
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
	
	/**
	 * @param selectStles
	 * @return get phone Swap array
	 */
	public static Map getPhSwapArray(Elements selectStles) {
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
	
	
	

	public static boolean netIsAvailable() {

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
	public static void waitForIpChnage(Properties prop, String url)
			throws FileNotFoundException, IOException, InterruptedException, FindFailed {
		
		do {
			
			System.out.println("ip change required");
			//here we have to work with Sikuli
			Sikuli ipchanger=new Sikuli();
		
			ipchanger.doIPChnage(url);
			
		}while (isIPchanged(url));
		
		System.out.println("ip  has changed suceessfully");
	}
	
	
	public static boolean isIPchanged(String url) throws InterruptedException {
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
	
	public static void getDatafromGoogleSpreadSheet() throws IOException {
		try {

			
			String prjName = System.getProperty("user.dir")
					.substring(System.getProperty("user.dir").lastIndexOf("\\") + 1);
			FileInputStream input = null;
			input = new FileInputStream(CONFIG_FILE);
			Properties prop = new Properties();
			prop.load(input);

			SAMPLE_XLSX_FILE_PATH = prop.getProperty(prjName);
			GOOGLE_SPREADSHET_URL_PATH = prop.getProperty("Google_Spread_Sheet_path");
			
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
					lastrow.createCell(4).setCellValue(0);

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
}
