package com.justdial.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelMerger {

	public static void main(String[] args) throws IOException {

		String directoryPath = "C:\\Users\\insik3\\Desktop\\JD\\PERFECT";

		List<Company> listelectricians = new ArrayList<>();

		File file = new File(directoryPath);

		// Reading directory contents
		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i]);

			FileInputStream inputStream = new FileInputStream(new File(files[i].toString()));
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();

			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				Company electrician = new Company();

				int count = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if (count == 0) {

							System.out.print("NAME		" + cell.getStringCellValue());
							electrician.setName(cell.getStringCellValue());

						}
						if (count == 1) {
							System.out.print("ADDRESS		" + cell.getStringCellValue());
							electrician.setAddress(cell.getStringCellValue());
						}
						if (count == 2) {
							System.out.print("VOTES			" + cell.getStringCellValue());
							electrician.setNumvotes(cell.getStringCellValue());
						}
						if (count == 3) {
							System.out.print("PHONE			" + cell.getStringCellValue());
							electrician.setPhonenum(cell.getStringCellValue());
						}

						count++;
						break;

					}

				}
				System.out.println();

				listelectricians.add(electrician);
			}

			inputStream.close();
		}
		
		ExportToXLS exportToXLS=new ExportToXLS();
		exportToXLS.createXLS(listelectricians);

	}

}
