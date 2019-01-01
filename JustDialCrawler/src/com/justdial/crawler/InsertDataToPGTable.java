package com.justdial.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InsertDataToPGTable {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public  void insertData() throws IOException, SQLException {
		
		DatabaseConnection connection = DatabaseConnection.getInstance();;
		Connection databaseConnection = connection.getDatabaseConnection();
		File file=new File("C:\\Users\\Siva\\Desktop\\JustdialData\\");
		if(file.isDirectory())
		{
			File[] listFiles = file.listFiles();
			
			for(File inputfile:listFiles)
			{
				try {
					File datafile=new File(inputfile.getAbsolutePath());
					
					System.err.println("inputfile.getAbsolutePath()"+inputfile.getAbsolutePath());
					FileInputStream fis = new FileInputStream(datafile);
						
					
					 XSSFWorkbook  workbook = new XSSFWorkbook(fis);
					   XSSFSheet datatypeSheet = workbook.getSheetAt(0);

					   Iterator<Row> rowIterator = datatypeSheet.iterator();
					   
					   int physicalNumberOfRows = datatypeSheet.getPhysicalNumberOfRows();
					   
					   
					   if(physicalNumberOfRows>1)
					   {
						   for(int i=1;i<=physicalNumberOfRows;i++)
						   {
							   try {
								Company company=new Company();
								   
								   System.out.println(datatypeSheet.getRow(i).getCell(0).toString());
								   company.setName(datatypeSheet.getRow(i).getCell(0).toString());
								   company.setAddress( datatypeSheet.getRow(i).getCell(1).toString());
								  company.setNumvotes( datatypeSheet.getRow(i).getCell(2).toString());
								  company.setRatings(  datatypeSheet.getRow(i).getCell(3).toString());
								  company.setPhonenum(datatypeSheet.getRow(i).getCell(4).toString());
								  
								  
								  connection.insertRecordIntoTable(databaseConnection, company);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							  
							  
							   
						   }
						   
						   
					   }
					   while (rowIterator.hasNext()) {
					        Row row = rowIterator.next();
					        
					        Iterator<Cell> cellIterator = row.cellIterator();
					        while (cellIterator.hasNext())
 {

							Cell cell = cellIterator.next();

							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case Cell.CELL_TYPE_NUMERIC:
								System.out.print(cell.getNumericCellValue() + "\t");
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								System.out.print(cell.getBooleanCellValue() + "\t");
								break;
							default:
							}
						} System.out.println("");

					       
					   }
					   fis.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				   
				   
				   
				   
				   
				   
				   
				   
				   
			}
		}
		
		
		

	}
	public void executeUpdateQueries() throws SQLException
	{
		
		
		
		DatabaseConnection connection = DatabaseConnection.getInstance();
		Connection databaseConnection = connection.getDatabaseConnection();
		
		String[] queries= {

"update just_dial set \"AREA\" = 'Sarjapur Road' where  \"ADDRESS\" like '%Sarjapur Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Whitefield' where  \"ADDRESS\" like '%Whitefield%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Bannerghatta Road' where  \"ADDRESS\" like '%Bannerghatta Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Electronic City' where  \"ADDRESS\" like '%Electronic City%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'HSR Layout' where  \"ADDRESS\" like '%HSR Layout%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Jayanagar' where  \"ADDRESS\" like '%Jayanagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'JP Nagar' where  \"ADDRESS\" like '%JP Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Hosur Road' where  \"ADDRESS\" like '%Hosur Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Indira Nagar' where  \"ADDRESS\" like '%Indira Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Koramangala' where  \"ADDRESS\" like '%Koramangala%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Yelahanka' where  \"ADDRESS\" like '%Yelahanka%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Hesaraghatta Main Road' where  \"ADDRESS\" like '%Hesaraghatta Main Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Devanahalli' where  \"ADDRESS\" like '%Devanahalli%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Marathahalli' where  \"ADDRESS\" like '%Marathahalli%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Hebbal' where  \"ADDRESS\" like '%Hebbal%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Kanakapura Road' where  \"ADDRESS\" like '%Kanakapura Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Anekal' where  \"ADDRESS\" like '%Anekal%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Hennur Road' where  \"ADDRESS\" like '%Hennur Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'C V Raman Nagar' where  \"ADDRESS\" like '%C V Raman Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Kalyan Nagar' where  \"ADDRESS\" like '%Kalyan Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'RT Nagar' where  \"ADDRESS\" like '%RT Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Jalahalli' where  \"ADDRESS\" like '%Jalahalli%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'HRBR Layout' where  \"ADDRESS\" like '%HRBR Layout%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'BTM Layout' where  \"ADDRESS\" like '%BTM Layout%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Ramamurthy Nagar' where  \"ADDRESS\" like '%Ramamurthy Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Brooke Field' where  \"ADDRESS\" like '%Brooke Field%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Jakkur' where  \"ADDRESS\" like '%Jakkur%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Dollars Colony' where  \"ADDRESS\" like '%Dollars Colony%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Abbigere' where  \"ADDRESS\" like '%Abbigere%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Rajanukunte' where  \"ADDRESS\" like '%Rajanukunte%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'G M Palya' where  \"ADDRESS\" like '%G M Palya%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Kundalahalli' where  \"ADDRESS\" like '%Kundalahalli%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Madiwala' where  \"ADDRESS\" like '%Madiwala%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Fraser Town' where  \"ADDRESS\" like '%Fraser Town%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Chandapur' where  \"ADDRESS\" like '%Chandapur%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Gottigere' where  \"ADDRESS\" like '%Gottigere%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Basavanagar' where  \"ADDRESS\" like '%Basavanagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Nagarbhavi' where  \"ADDRESS\" like '%Nagarbhavi%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Belandur' where  \"ADDRESS\" like '%Belandur%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Hoskote' where  \"ADDRESS\" like '%Hoskote%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Chamarajpet' where  \"ADDRESS\" like '%Chamarajpet%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Richards Town' where  \"ADDRESS\" like '%Richards Town%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Lavelle Road' where  \"ADDRESS\" like '%Lavelle Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Kodigehalli' where  \"ADDRESS\" like '%Kodigehalli%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Chikkajala' where  \"ADDRESS\" like '%Chikkajala%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Hanumanth Nagar' where  \"ADDRESS\" like '%Hanumanth Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Akshaya Nagar' where  \"ADDRESS\" like '%Akshaya Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Thanisandra' where  \"ADDRESS\" like '%Thanisandra%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Sarjapur' where  \"ADDRESS\" like '%Sarjapur%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Hegde Nagar' where  \"ADDRESS\" like '%Hegde Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Jigani Industrial Area' where  \"ADDRESS\" like '%Jigani Industrial Area%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Mathikere' where  \"ADDRESS\" like '%Mathikere%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Rest House Road' where  \"ADDRESS\" like '%Rest House Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Begur Road' where  \"ADDRESS\" like '%Begur Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Rajajinagar' where  \"ADDRESS\" like '%Rajajinagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'MG Road' where  \"ADDRESS\" like '%MG Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'HBR Layout' where  \"ADDRESS\" like '%HBR Layout%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Banaswadi' where  \"ADDRESS\" like '%Banaswadi%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Uttarahalli' where  \"ADDRESS\" like '%Uttarahalli%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Airport Road' where  \"ADDRESS\" like '%Airport Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Thippasandra' where  \"ADDRESS\" like '%Thippasandra%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Banashankari' where  \"ADDRESS\" like '%Banashankari%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Bagalur' where  \"ADDRESS\" like '%Bagalur%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Horamavu' where  \"ADDRESS\" like '%Horamavu%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'KR Puram' where  \"ADDRESS\" like '%KR Puram%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Bommanahalli' where  \"ADDRESS\" like '%Bommanahalli%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'OMBR Layout' where  \"ADDRESS\" like '%OMBR Layout%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Mysore Road' where  \"ADDRESS\" like '%Mysore Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Silkboard' where  \"ADDRESS\" like '%Silkboard%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'RMV Extension Stage' where  \"ADDRESS\" like '%RMV Extension Stage%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Old Madras Road' where  \"ADDRESS\" like '%Old Madras Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Kasturi Nagar' where  \"ADDRESS\" like '%Kasturi Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Tumkur Road' where  \"ADDRESS\" like '%Tumkur Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Richmond Road' where  \"ADDRESS\" like '%Richmond Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Vidyaranyapura' where  \"ADDRESS\" like '%Vidyaranyapura%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Mahadevapura' where  \"ADDRESS\" like '%Mahadevapura%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Rajarajeshwari Nagar' where  \"ADDRESS\" like '%Rajarajeshwari Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Malleshwaram' where  \"ADDRESS\" like '%Malleshwaram%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'AECS Layout' where  \"ADDRESS\" like '%AECS Layout%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Chikkaballapur' where  \"ADDRESS\" like '%Chikkaballapur%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Defence Colony' where  \"ADDRESS\" like '%Defence Colony%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Kanaka Nagar' where  \"ADDRESS\" like '%Kanaka Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Hulimavu' where  \"ADDRESS\" like '%Hulimavu%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Thyagaraj Nagar' where  \"ADDRESS\" like '%Thyagaraj Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Basaveshwaranagar' where  \"ADDRESS\" like '%Basaveshwaranagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Airport Area' where  \"ADDRESS\" like '%Airport Area%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Kumaraswamy Layout' where  \"ADDRESS\" like '%Kumaraswamy Layout%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Sanjay Nagar' where  \"ADDRESS\" like '%Sanjay Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Hoskote' where  \"ADDRESS\" like '%Hoskote%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Kudlu Gate' where  \"ADDRESS\" like '%Kudlu Gate%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'RBI Layout' where  \"ADDRESS\" like '%RBI Layout%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Palace Road' where  \"ADDRESS\" like '%Palace Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Hoodi Village' where  \"ADDRESS\" like '%Hoodi Village%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Millers Road' where  \"ADDRESS\" like '%Millers Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Huskur' where  \"ADDRESS\" like '%Huskur%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Vijaya Bank Layout' where  \"ADDRESS\" like '%Vijaya Bank Layout%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Shanti Nagar' where  \"ADDRESS\" like '%Shanti Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Hebbal Kempapura' where  \"ADDRESS\" like '%Hebbal Kempapura%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Tippasandra' where  \"ADDRESS\" like '%Tippasandra%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Naganathapura' where  \"ADDRESS\" like '%Naganathapura%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Yeshwantpur' where  \"ADDRESS\" like '%Yeshwantpur%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Jeevan Bima Nagar' where  \"ADDRESS\" like '%Jeevan Bima Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Cox Town' where  \"ADDRESS\" like '%Cox Town%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Ulsoor' where  \"ADDRESS\" like '%Ulsoor%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Benson Town' where  \"ADDRESS\" like '%Benson Town%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'ITPL' where  \"ADDRESS\" like '%ITPL%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Doddaballapur' where  \"ADDRESS\" like '%Doddaballapur%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Kaggadaspura' where  \"ADDRESS\" like '%Kaggadaspura%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Nagwar' where  \"ADDRESS\" like '%Nagwar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Attibele' where  \"ADDRESS\" like '%Attibele%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Padmanabhanagar' where  \"ADDRESS\" like '%Padmanabhanagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Vijayanagar' where  \"ADDRESS\" like '%Vijayanagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Kengeri' where  \"ADDRESS\" like '%Kengeri%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Peenya' where  \"ADDRESS\" like '%Peenya%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Magadi Road' where  \"ADDRESS\" like '%Magadi Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'NelaMangala' where  \"ADDRESS\" like '%NelaMangala%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Sahakar Nagar' where  \"ADDRESS\" like '%Sahakar Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Dodballapur Road' where  \"ADDRESS\" like '%Dodballapur Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Outer Ring Road' where  \"ADDRESS\" like '%Outer Ring Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Vigyan Nagar' where  \"ADDRESS\" like '%Vigyan Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Basavanagudi' where  \"ADDRESS\" like '%Basavanagudi%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Mallesh Palaya' where  \"ADDRESS\" like '%Mallesh Palaya%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Domlur' where  \"ADDRESS\" like '%Domlur%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Cookes Town' where  \"ADDRESS\" like '%Cookes Town%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Old Airport Road' where  \"ADDRESS\" like '%Old Airport Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Bellary Road' where  \"ADDRESS\" like '%Bellary Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Sadaramangala' where  \"ADDRESS\" like '%Sadaramangala%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Anjanapura' where  \"ADDRESS\" like '%Anjanapura%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Majestic' where  \"ADDRESS\" like '%Majestic%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Vasanth Nagar' where  \"ADDRESS\" like '%Vasanth Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Wilson Garden' where  \"ADDRESS\" like '%Wilson Garden%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'ISRO Layout' where  \"ADDRESS\" like '%ISRO Layout%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'HMT Layout' where  \"ADDRESS\" like '%HMT Layout%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Nagawara' where  \"ADDRESS\" like '%Nagawara%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Doddaballapur Road' where  \"ADDRESS\" like '%Doddaballapur Road%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Central Silk Board' where  \"ADDRESS\" like '%Central Silk Board%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Nandi Hills' where  \"ADDRESS\" like '%Nandi Hills%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Ganganagar' where  \"ADDRESS\" like '%Ganganagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Bommasandra' where  \"ADDRESS\" like '%Bommasandra%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Pai Layout' where  \"ADDRESS\" like '%Pai Layout%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Sadaramangala' where  \"ADDRESS\" like '%Sadaramangala%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Prashanth Nagar' where  \"ADDRESS\" like '%Prashanth Nagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Hennur' where  \"ADDRESS\" like '%Hennur%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Raj Bhavan' where  \"ADDRESS\" like '%Raj Bhavan%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Vidyanagar' where  \"ADDRESS\" like '%Vidyanagar%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Bilekahalli' where  \"ADDRESS\" like '%Bilekahalli%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Manek Chowk' where  \"ADDRESS\" like '%Manek Chowk%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Chambal River' where  \"ADDRESS\" like '%Chambal River%' AND \"CITY\" = 'Bangalore'",

"update just_dial set \"AREA\" = 'Indraprastha' where  \"ADDRESS\" like '%Indraprastha%' AND \"CITY\" = 'Bangalore'",
"update just_dial set \"AREA\" = 'Thiruvallur' where  \"ADDRESS\" like '%Thiruvallur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'OMR Road' where  \"ADDRESS\" like '%OMR Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Velacheri' where  \"ADDRESS\" like '%Velacheri%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Oragadam' where  \"ADDRESS\" like '%Oragadam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Poonamalli' where  \"ADDRESS\" like '%Poonamalli%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'E.C.R Road' where  \"ADDRESS\" like '%E.C.R Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Anna Nagar' where  \"ADDRESS\" like '%Anna Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Valasaravakam' where  \"ADDRESS\" like '%Valasaravakam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Avadi' where  \"ADDRESS\" like '%Avadi%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Madipakkam' where  \"ADDRESS\" like '%Madipakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Medavakkam' where  \"ADDRESS\" like '%Medavakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Porur' where  \"ADDRESS\" like '%Porur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Tambaram East' where  \"ADDRESS\" like '%Tambaram East%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kancheepuram' where  \"ADDRESS\" like '%Kancheepuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Gudovancherry' where  \"ADDRESS\" like '%Gudovancherry%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Pallikaranai' where  \"ADDRESS\" like '%Pallikaranai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Adyar' where  \"ADDRESS\" like '%Adyar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Urappakkam' where  \"ADDRESS\" like '%Urappakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Adambakkam' where  \"ADDRESS\" like '%Adambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Manali' where  \"ADDRESS\" like '%Manali%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Alwarthirunagar' where  \"ADDRESS\" like '%Alwarthirunagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Tharamani' where  \"ADDRESS\" like '%Tharamani%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Thirumullaivoyal' where  \"ADDRESS\" like '%Thirumullaivoyal%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mangadu' where  \"ADDRESS\" like '%Mangadu%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Anna Nagar' where  \"ADDRESS\" like '%Anna Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mylapore' where  \"ADDRESS\" like '%Mylapore%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Tiruvanmiyur' where  \"ADDRESS\" like '%Tiruvanmiyur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Arambakkam' where  \"ADDRESS\" like '%Arambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Thiruvottriyur' where  \"ADDRESS\" like '%Thiruvottriyur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ashok Nagar' where  \"ADDRESS\" like '%Ashok Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Tiruverkadu' where  \"ADDRESS\" like '%Tiruverkadu%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nanganallur' where  \"ADDRESS\" like '%Nanganallur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ayanpuram' where  \"ADDRESS\" like '%Ayanpuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Triplicane' where  \"ADDRESS\" like '%Triplicane%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ayyappa Nagar' where  \"ADDRESS\" like '%Ayyappa Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nungambakkam' where  \"ADDRESS\" like '%Nungambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Vadapalani' where  \"ADDRESS\" like '%Vadapalani%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Besant Nagar' where  \"ADDRESS\" like '%Besant Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Vandalur' where  \"ADDRESS\" like '%Vandalur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Old Pallavaram' where  \"ADDRESS\" like '%Old Pallavaram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Velachery' where  \"ADDRESS\" like '%Velachery%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Chetput' where  \"ADDRESS\" like '%Chetput%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Padappai' where  \"ADDRESS\" like '%Padappai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Vepery' where  \"ADDRESS\" like '%Vepery%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Chitlapakkam' where  \"ADDRESS\" like '%Chitlapakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Padi' where  \"ADDRESS\" like '%Padi%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Villivakkam' where  \"ADDRESS\" like '%Villivakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Choolai' where  \"ADDRESS\" like '%Choolai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Virugambakkam' where  \"ADDRESS\" like '%Virugambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Chromepet' where  \"ADDRESS\" like '%Chromepet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ethiraj Salai' where  \"ADDRESS\" like '%Ethiraj Salai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Park Town' where  \"ADDRESS\" like '%Park Town%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Washermanpet' where  \"ADDRESS\" like '%Washermanpet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mambalam' where  \"ADDRESS\" like '%Mambalam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Egmore' where  \"ADDRESS\" like '%Egmore%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Pattabiram' where  \"ADDRESS\" like '%Pattabiram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ekkaduthangal' where  \"ADDRESS\" like '%Ekkaduthangal%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Pazhavanthangal' where  \"ADDRESS\" like '%Pazhavanthangal%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Perambur' where  \"ADDRESS\" like '%Perambur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Perungudi' where  \"ADDRESS\" like '%Perungudi%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Gopalapuram' where  \"ADDRESS\" like '%Gopalapuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Polichalur' where  \"ADDRESS\" like '%Polichalur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ponneri' where  \"ADDRESS\" like '%Ponneri%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Greams Road' where  \"ADDRESS\" like '%Greams Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ponniammanmedu' where  \"ADDRESS\" like '%Ponniammanmedu%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Abhiramapuram' where  \"ADDRESS\" like '%Abhiramapuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Agaram' where  \"ADDRESS\" like '%Agaram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Alandur' where  \"ADDRESS\" like '%Alandur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Alappakkam' where  \"ADDRESS\" like '%Alappakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Alwarpet' where  \"ADDRESS\" like '%Alwarpet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ambattur' where  \"ADDRESS\" like '%Ambattur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ambattur OT' where  \"ADDRESS\" like '%Ambattur OT%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ambattur Industrial Estate' where  \"ADDRESS\" like '%Ambattur Industrial Estate%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ambattur Industrial Estate South' where  \"ADDRESS\" like '%Ambattur Industrial Estate South%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ambattur Industrial Estate South Phase' where  \"ADDRESS\" like '%Ambattur Industrial Estate South Phase%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ambattur OT' where  \"ADDRESS\" like '%Ambattur OT%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Aminjikarai' where  \"ADDRESS\" like '%Aminjikarai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Anakaputhur' where  \"ADDRESS\" like '%Anakaputhur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Anna Nagar East' where  \"ADDRESS\" like '%Anna Nagar East%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Anna Nagar West' where  \"ADDRESS\" like '%Anna Nagar West%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Anna Nagar West Extension' where  \"ADDRESS\" like '%Anna Nagar West Extension%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Anna Road' where  \"ADDRESS\" like '%Anna Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Anna Salai' where  \"ADDRESS\" like '%Anna Salai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Arcot Road' where  \"ADDRESS\" like '%Arcot Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Arumbakkam' where  \"ADDRESS\" like '%Arumbakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Attipattu' where  \"ADDRESS\" like '%Attipattu%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ayanambakkam' where  \"ADDRESS\" like '%Ayanambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ayanavaram' where  \"ADDRESS\" like '%Ayanavaram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Iyyapanthangal' where  \"ADDRESS\" like '%Iyyapanthangal%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Broadway' where  \"ADDRESS\" like '%Broadway%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Cathedral Road' where  \"ADDRESS\" like '%Cathedral Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'General Post Office' where  \"ADDRESS\" like '%General Post Office%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Chepauk' where  \"ADDRESS\" like '%Chepauk%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Chetpet' where  \"ADDRESS\" like '%Chetpet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Chintadripet' where  \"ADDRESS\" like '%Chintadripet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Chinmaya Nagar' where  \"ADDRESS\" like '%Chinmaya Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Choolaimedu' where  \"ADDRESS\" like '%Choolaimedu%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'CIT Nagar' where  \"ADDRESS\" like '%CIT Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'East Coast Road' where  \"ADDRESS\" like '%East Coast Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ennore' where  \"ADDRESS\" like '%Ennore%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ernavoor' where  \"ADDRESS\" like '%Ernavoor%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Flowers Road' where  \"ADDRESS\" like '%Flowers Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Gandhinagar' where  \"ADDRESS\" like '%Gandhinagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Gowrivakkam' where  \"ADDRESS\" like '%Gowrivakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Guduvancheri' where  \"ADDRESS\" like '%Guduvancheri%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Guindy' where  \"ADDRESS\" like '%Guindy%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Guindy' where  \"ADDRESS\" like '%Guindy%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Gummidipoondi' where  \"ADDRESS\" like '%Gummidipoondi%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Gerugambakkam' where  \"ADDRESS\" like '%Gerugambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Hasthinapuram' where  \"ADDRESS\" like '%Hasthinapuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'IIT Campus' where  \"ADDRESS\" like '%IIT Campus%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Indira Nagar' where  \"ADDRESS\" like '%Indira Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Injambakkam' where  \"ADDRESS\" like '%Injambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Jafferkhanpet' where  \"ADDRESS\" like '%Jafferkhanpet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Jawahar Nagar' where  \"ADDRESS\" like '%Jawahar Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kaladipet' where  \"ADDRESS\" like '%Kaladipet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kamaraj Nagar' where  \"ADDRESS\" like '%Kamaraj Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kanchipuram' where  \"ADDRESS\" like '%Kanchipuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kandanchavadi' where  \"ADDRESS\" like '%Kandanchavadi%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kodambakkam' where  \"ADDRESS\" like '%Kodambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Karayanchavadi' where  \"ADDRESS\" like '%Karayanchavadi%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kattupakkam' where  \"ADDRESS\" like '%Kattupakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Keelkattalai' where  \"ADDRESS\" like '%Keelkattalai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kelambakkam' where  \"ADDRESS\" like '%Kelambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kellys' where  \"ADDRESS\" like '%Kellys%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kilkattalai' where  \"ADDRESS\" like '%Kilkattalai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kilpauk' where  \"ADDRESS\" like '%Kilpauk%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'KK Nagar' where  \"ADDRESS\" like '%KK Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'KK Nagar West' where  \"ADDRESS\" like '%KK Nagar West%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kodungaiyur' where  \"ADDRESS\" like '%Kodungaiyur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kolathur' where  \"ADDRESS\" like '%Kolathur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kondithope' where  \"ADDRESS\" like '%Kondithope%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Korattur' where  \"ADDRESS\" like '%Korattur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Korukkupet' where  \"ADDRESS\" like '%Korukkupet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kottivakkam' where  \"ADDRESS\" like '%Kottivakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kotturpuram' where  \"ADDRESS\" like '%Kotturpuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kovilambakkam' where  \"ADDRESS\" like '%Kovilambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Koyambedu' where  \"ADDRESS\" like '%Koyambedu%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kundrathur' where  \"ADDRESS\" like '%Kundrathur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kosapet' where  \"ADDRESS\" like '%Kosapet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Little Mount' where  \"ADDRESS\" like '%Little Mount%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Madambakkam' where  \"ADDRESS\" like '%Madambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Madhavaram' where  \"ADDRESS\" like '%Madhavaram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Maduravoyal' where  \"ADDRESS\" like '%Maduravoyal%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mahabalipuram' where  \"ADDRESS\" like '%Mahabalipuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mahabalipuram' where  \"ADDRESS\" like '%Mahabalipuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Manapakkam' where  \"ADDRESS\" like '%Manapakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mandaveli' where  \"ADDRESS\" like '%Mandaveli%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mandavelipakkam' where  \"ADDRESS\" like '%Mandavelipakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mannady' where  \"ADDRESS\" like '%Mannady%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mannurpet' where  \"ADDRESS\" like '%Mannurpet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Maraimalai Nagar' where  \"ADDRESS\" like '%Maraimalai Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Meenambakkam' where  \"ADDRESS\" like '%Meenambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Metha Nagar' where  \"ADDRESS\" like '%Metha Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mettukuppam' where  \"ADDRESS\" like '%Mettukuppam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'MGR Nagar' where  \"ADDRESS\" like '%MGR Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Minjur' where  \"ADDRESS\" like '%Minjur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'MKB Nagar' where  \"ADDRESS\" like '%MKB Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mogappair' where  \"ADDRESS\" like '%Mogappair%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mogappair East' where  \"ADDRESS\" like '%Mogappair East%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mogappair West' where  \"ADDRESS\" like '%Mogappair West%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Moolakadai' where  \"ADDRESS\" like '%Moolakadai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Mount Road' where  \"ADDRESS\" like '%Mount Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Muttukadu' where  \"ADDRESS\" like '%Muttukadu%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nammalwarpet' where  \"ADDRESS\" like '%Nammalwarpet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nandambakkam' where  \"ADDRESS\" like '%Nandambakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nandanam' where  \"ADDRESS\" like '%Nandanam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nandanam Extension' where  \"ADDRESS\" like '%Nandanam Extension%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nazarethpettai' where  \"ADDRESS\" like '%Nazarethpettai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nehru Nagar' where  \"ADDRESS\" like '%Nehru Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nelson Manickam Road' where  \"ADDRESS\" like '%Nelson Manickam Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nerkundram' where  \"ADDRESS\" like '%Nerkundram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nesapakkam' where  \"ADDRESS\" like '%Nesapakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'New Perungalathur' where  \"ADDRESS\" like '%New Perungalathur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nilangarai' where  \"ADDRESS\" like '%Nilangarai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'North Usman Road' where  \"ADDRESS\" like '%North Usman Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Old Mahabalipuram Road' where  \"ADDRESS\" like '%Old Mahabalipuram Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Old Perungalattu' where  \"ADDRESS\" like '%Old Perungalattu%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Old Washermenpet' where  \"ADDRESS\" like '%Old Washermenpet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Otteri' where  \"ADDRESS\" like '%Otteri%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Palavakkam' where  \"ADDRESS\" like '%Palavakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Pammal' where  \"ADDRESS\" like '%Pammal%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Parrys' where  \"ADDRESS\" like '%Parrys%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Pattalam' where  \"ADDRESS\" like '%Pattalam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Perambur Barracks' where  \"ADDRESS\" like '%Perambur Barracks%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Periyamedu' where  \"ADDRESS\" like '%Periyamedu%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Periyar Nagar' where  \"ADDRESS\" like '%Periyar Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Pondy Bazaar' where  \"ADDRESS\" like '%Pondy Bazaar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Poonamallee High Road' where  \"ADDRESS\" like '%Poonamallee High Road%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Puzhal' where  \"ADDRESS\" like '%Puzhal%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Pudupet' where  \"ADDRESS\" like '%Pudupet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Pulianthope' where  \"ADDRESS\" like '%Pulianthope%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Puludivakkam' where  \"ADDRESS\" like '%Puludivakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Purasavakkam' where  \"ADDRESS\" like '%Purasavakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kotturpuram' where  \"ADDRESS\" like '%Kotturpuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Raja Annamalai Puram' where  \"ADDRESS\" like '%Raja Annamalai Puram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Rajaji Salai' where  \"ADDRESS\" like '%Rajaji Salai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Rajakilpakkam' where  \"ADDRESS\" like '%Rajakilpakkam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ramapuram' where  \"ADDRESS\" like '%Ramapuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Rangarajapuram' where  \"ADDRESS\" like '%Rangarajapuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Red Hills' where  \"ADDRESS\" like '%Red Hills%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Royapettah' where  \"ADDRESS\" like '%Royapettah%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Royapuram' where  \"ADDRESS\" like '%Royapuram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nazarethpetai' where  \"ADDRESS\" like '%Nazarethpetai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nazarethpettai' where  \"ADDRESS\" like '%Nazarethpettai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Old Pallavaram' where  \"ADDRESS\" like '%Old Pallavaram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Pazavanthangal' where  \"ADDRESS\" like '%Pazavanthangal%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Perumbedu' where  \"ADDRESS\" like '%Perumbedu%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Poonamallee' where  \"ADDRESS\" like '%Poonamallee%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Vyasarpadi' where  \"ADDRESS\" like '%Vyasarpadi%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Avadi Camp' where  \"ADDRESS\" like '%Avadi Camp%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Devampattu' where  \"ADDRESS\" like '%Devampattu%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Officers Training Academy' where  \"ADDRESS\" like '%Officers Training Academy%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Eguvarpalayam' where  \"ADDRESS\" like '%Eguvarpalayam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Jothi Nagar' where  \"ADDRESS\" like '%Jothi Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'T Nagar' where  \"ADDRESS\" like '%T Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Tambaram' where  \"ADDRESS\" like '%Tambaram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'ICF Colony' where  \"ADDRESS\" like '%ICF Colony%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Madras High Court' where  \"ADDRESS\" like '%Madras High Court%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kunnathur' where  \"ADDRESS\" like '%Kunnathur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Nandabakkamudiyiruppu' where  \"ADDRESS\" like '%Nandabakkamudiyiruppu%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Elavur' where  \"ADDRESS\" like '%Elavur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ennore Thermal Station' where  \"ADDRESS\" like '%Ennore Thermal Station%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Kaveripettai' where  \"ADDRESS\" like '%Kaveripettai%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Puzhal' where  \"ADDRESS\" like '%Puzhal%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Pulicat' where  \"ADDRESS\" like '%Pulicat%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'RA Puram' where  \"ADDRESS\" like '%RA Puram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Raj Bhavan' where  \"ADDRESS\" like '%Raj Bhavan%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'RajBhavan' where  \"ADDRESS\" like '%RajBhavan%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Shed Avadi' where  \"ADDRESS\" like '%Shed Avadi%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Saidapet' where  \"ADDRESS\" like '%Saidapet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Saligramam' where  \"ADDRESS\" like '%Saligramam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Sathyamurthi Nagar' where  \"ADDRESS\" like '%Sathyamurthi Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Selaiyur' where  \"ADDRESS\" like '%Selaiyur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Shenoy Nagar' where  \"ADDRESS\" like '%Shenoy Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Sholavaram' where  \"ADDRESS\" like '%Sholavaram%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'SIDCO Estate' where  \"ADDRESS\" like '%SIDCO Estate%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Sowcarpet' where  \"ADDRESS\" like '%Sowcarpet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Srinivasa Nagar' where  \"ADDRESS\" like '%Srinivasa Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'St. Thomas Mount' where  \"ADDRESS\" like '%St. Thomas Mount%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Tharamani' where  \"ADDRESS\" like '%Tharamani%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Teynampet' where  \"ADDRESS\" like '%Teynampet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Thiruninravur' where  \"ADDRESS\" like '%Thiruninravur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Thirupalaivanam' where  \"ADDRESS\" like '%Thirupalaivanam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'West Mambalam' where  \"ADDRESS\" like '%West Mambalam%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'West Porur' where  \"ADDRESS\" like '%West Porur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'St. George' where  \"ADDRESS\" like '%St. George%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'IIT' where  \"ADDRESS\" like '%IIT%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Tondiarpet' where  \"ADDRESS\" like '%Tondiarpet%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Thrisulam Village' where  \"ADDRESS\" like '%Thrisulam Village%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Tiruvottiyur' where  \"ADDRESS\" like '%Tiruvottiyur%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Chandan Nagar' where  \"ADDRESS\" like '%Chandan Nagar%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Palavanthangal' where  \"ADDRESS\" like '%Palavanthangal%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'Ambattur Old Town' where  \"ADDRESS\" like '%Ambattur Old Town%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'spacer' where  \"ADDRESS\" like '%spacer%' AND \"CITY\" = 'Chennai'",

"update just_dial set \"AREA\" = 'spacer' where  \"ADDRESS\" like '%spacer%' AND \"CITY\" = 'Chennai'"


		
		
		
		};
		
		
		for(int i=0;i<queries.length;i++)
		{
			
			System.out.println("execuint --"+queries[i]);
			Statement createStatement = databaseConnection.createStatement();
			 int executeUpdate = createStatement.executeUpdate(queries[i]);
			 
			 System.out.println(executeUpdate+"executeUpdate");
			
		}
		
	}
	
	
	public List<List<Object>> executeSelectQueries() throws SQLException

	{
		
		DatabaseConnection connection = DatabaseConnection.getInstance();;
		Connection databaseConnection = connection.getDatabaseConnection();
		
		
		List<List<Object>> arrayasList=new ArrayList<List<Object>>();
		
		Statement createStatement = databaseConnection.createStatement();
		
		String slectQuery="SELECT \"CITY\", count(*) FROM just_dial GROUP BY \"CITY\"";
		ResultSet rs = createStatement.executeQuery(slectQuery);
		
		 while (rs.next()) {

			 List<Object> innerList=new ArrayList<Object>();
	            String city = rs.getString(1);
	            
	            if(city==null)
	            {
	            	city="UNKNNOWN";	
	            }
	            
	            System.out.println(city);

	            String  records = rs.getString(2);
	            System.out.println(records);

	            innerList.add(city);
	            innerList.add(records);
	            arrayasList.add(innerList);
	        }
		
		return arrayasList;
	}
	
	public void executeCityUpdateQueries() throws SQLException
	{
		
		
		
		String[] queries= {
				
				"update just_dial set \"CITY\" = 'Ahmedabad' where \"ADDRESS\" like '%Ahmedabad%'",

				"update just_dial set \"CITY\" = 'Goa' where \"ADDRESS\" like '%Goa%'",

				"update just_dial set \"CITY\" = 'Banglore' where \"ADDRESS\" like '%Banglore%'",

				"update just_dial set \"CITY\" = 'Indore' where \"ADDRESS\" like '%Indore%'",

				"update just_dial set \"CITY\" = 'Hyderabad' where \"ADDRESS\" like '%Hyderabad%'",

				"update just_dial set \"CITY\" = 'Vadodara' where \"ADDRESS\" like '%Vadodara%'",

				"update just_dial set \"CITY\" = 'Jaipur' where \"ADDRESS\" like '%Jaipur%'",

				"update just_dial set \"CITY\" = 'Chennai' where \"ADDRESS\" like '%Chennai%'",
				
				"update just_dial set \"CITY\" = 'Chennai' where \"ADDRESS\" like '%chennai%'",

				"update just_dial set \"CITY\" = 'Mysore' where \"ADDRESS\" like '%Mysore%'",

				"update just_dial set \"CITY\" = 'Nashik' where \"ADDRESS\" like '%Nashik%'",

				"update just_dial set \"CITY\" = 'Pune' where \"ADDRESS\" like '%Pune%'",

				"update just_dial set \"CITY\" = 'Surat' where \"ADDRESS\" like '%Surat%'",

				"update just_dial set \"CITY\" = 'Nagpur' where \"ADDRESS\" like '%Nagpur%'",

				"update just_dial set \"CITY\" = 'Bangalore' where \"ADDRESS\" like '%Bangalore%'",

				"update just_dial set \"CITY\" = 'Coimbatore' where \"ADDRESS\" like '%Coimbatore%'",

				"update just_dial set \"CITY\" = 'Chandigarh' where \"ADDRESS\" like '%Chandigarh%'",

				"update just_dial set \"CITY\" = 'Ernakulam' where \"ADDRESS\" like '%Ernakulam%'",

				"update just_dial set \"CITY\" = 'Kolkata' where \"ADDRESS\" like '%Kolkata%'",

				"update just_dial set \"CITY\" = 'Delhi' where \"ADDRESS\" like '%Delhi%'",

				"update just_dial set \"CITY\" = 'Mumbai' where \"ADDRESS\" like '%Mumbai%'"

};
		
		DatabaseConnection connection = DatabaseConnection.getInstance();;
		Connection databaseConnection = connection.getDatabaseConnection();
		
		
		
		
		for(int i=0;i<queries.length;i++)
		{
			
			System.out.println("execuint --"+queries[i]);
			Statement createStatement = databaseConnection.createStatement();
			 int executeUpdate = createStatement.executeUpdate(queries[i]);
			 
			 System.out.println(executeUpdate+"executeUpdate");
			
		}
		
		
		
	}
}
