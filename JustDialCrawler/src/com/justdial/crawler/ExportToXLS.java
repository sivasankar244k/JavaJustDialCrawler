package com.justdial.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportToXLS {

	private String crawlerName;
	private String CONFIG_FILE = "C:\\Users\\Siva\\workspace\\JavaJustDialCrawler\\config.properties";

	public void ExportToXLS(String crawlerName) {
		this.crawlerName = crawlerName;

	}

	public void createXLS(Set<Company> setCompany) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("Just Dial data");
		// Create row object
		XSSFRow row;
		// This data needs to be written (Object[])
		Map<String, Object[]> empinfo = createHeaderRow();

		int j = 0;
		for (Iterator<Company> it = setCompany.iterator(); it.hasNext();) {

			String rownum = String.valueOf(j + 2);
			Company company = it.next();

			empinfo.put(rownum,
					new Object[] { company.getName(), company.getAddress(),
							company.getNumvotes(), company.getRatings(),
							company.getPhonenum() });
			j++;
		}

		// Iterate over data and write to sheet
		Set<String> keyid = empinfo.keySet();
		int rowid = 0;
		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = empinfo.get(key);
			int cellid = 0;
			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}

		// Write the workbook in file system
		FileInputStream input = null;
		input = new FileInputStream(CONFIG_FILE);

		// load a properties file
		Properties prop = new Properties();
		prop.load(input);

		String outputFolder = prop.getProperty("outputFolder");
		FileOutputStream out = new FileOutputStream(new File(outputFolder
				+ System.getProperty("user.dir").substring(
						System.getProperty("user.dir").lastIndexOf("\\") + 1)
				+ System.currentTimeMillis() + "data.xlsx"));
		resizeColumnsInSheet(workbook);
		workbook.write(out);
		out.close();
		System.out.println(outputFolder
				+ System.getProperty("user.dir").substring(
						System.getProperty("user.dir").lastIndexOf("\\") + 1)
				+ System.currentTimeMillis() + "data.xlsx"
				+ " written successfully");
	}

	private Map<String, Object[]> createHeaderRow() {
		Map<String, Object[]> empinfo = new TreeMap<String, Object[]>();
		empinfo.put("1", new Object[] { "NAME", "ADDRESS", "VOTES", "RATINGS",
				"PHONENUMBER" });
		return empinfo;
	}

	private static void resizeColumnsInSheet(Workbook workbook) {

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(i);
			Row row = sheet.getRow(0);
			for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
				sheet.autoSizeColumn(colNum);
			}
		}

	}
}
