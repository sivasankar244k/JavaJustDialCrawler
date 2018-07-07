package com.justdial.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CrawlerApp {

	public static void main(String[] args) throws InterruptedException {

		/*
		 * 
		 * //*[@id="bcard0"]/div[1]/section/div[1]/h4/span/a here we should get
		 * the title -------- Name
		 * 
		 * 
		 * //*[@id="bcard0"]/div[1]/section/div[1]/p[1]/a/span[2]
		 * -------------Voter card
		 * 
		 * //*[@id="bcard0"]/div[1]/section/div[1]/p[2]/span/a/b
		 * -----------Phone number
		 * 
		 * 
		 * 
		 */

		System.setProperty("webdriver.chrome.driver", "C:\\CHROME-DRIVER-2\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		for (int i = 0; i < 3; i++) {

			System.out.println("I am  going to open the page number " + i);
			try {

				List<Company> listElectricians = new ArrayList<>();

				// driver.get("https://www.justdial.com/Bangalore/Electricians/nct-10184166/page-"
				// + i);
				// https://www.justdial.com/Bangalore/Plumbers/nct-10378025/page-2

				driver.get("https://www.justdial.com/Bangalore/Carpentry-Contractors/nct-10080646/page-" + i);
				driver.manage().window().maximize();

				int numCount = 0;
				int yCorodinate = 400;
				int totalNumberOfrecords;

				int oldnumRecord = driver.findElements(By.xpath("//span[@class='jcn']")).size();

				do {

					Thread.sleep(500);
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("scroll(0,'" + yCorodinate + "' );");
					yCorodinate += 400;

					totalNumberOfrecords = driver.findElements(By.xpath("//span[@class='jcn']")).size();

					if (oldnumRecord != totalNumberOfrecords) {
						System.out.println(
								"oldnumRecord     " + oldnumRecord + "totalNumberOfrecords" + totalNumberOfrecords);
						oldnumRecord = totalNumberOfrecords;

						numCount = 0;

					} else {
						numCount++;
						System.out.println("numCount    " + numCount);
					}

				} while (numCount < 30);

				/*
				 * Reading the records
				 */

				for (int j = 0; j < 10; j++) {

					System.out.println("Started reading the record" + j);
					String name = null;
					String address = null;
					String numvotes = null;
					String phonenum = null;

					List<WebElement> nameElement = driver
							.findElements(By.xpath("//*[@id='bcard0']/div[1]/section/div[1]/p[3]/span/span"));

					//// *[@id="bcard0"]/div[1]/section/div[1]/h2/span/a/span

					if (nameElement.size() > 0) {
						name = nameElement.get(0).getText();
					}
					/*
					 * List<WebElement> addressElement = driver
					 * .findElements(By.xpath("//*[@id='bcard" + j +
					 * "']/div[1]/section/div[1]/h4/span/a"));
					 */

					List<WebElement> addressElement = driver
							.findElements(By.xpath("//*[@id='bcard0']/div[1]/section/div[1]/p[3]/span/span"));

					if (addressElement.size() > 0) {
						address = addressElement.get(0).getAttribute("title");
					}

					List<WebElement> numOfvoteselement = driver
							.findElements(By.xpath("//*[@id='bcard" + j + "']/div[1]/section/div[1]/p[1]/a/span[2]"));

					if (numOfvoteselement.size() > 0) {
						numvotes = numOfvoteselement.get(0).getText();
					}

					List<WebElement> phoneNumElement = driver
							.findElements(By.xpath("//*[@id='bcard" + j + "']/div[1]/section/div[1]/p[2]/span/a/b"));

					// *[@id="bcard0"]/div[1]/section/div[1]/p[2]

					if (phoneNumElement.size() > 0) {
						phonenum = phoneNumElement.get(0).getText();

					} else {

						phoneNumElement = driver
								.findElements(By.xpath("//*[@id='bcard" + j + "']/div[1]/section/div[1]/p[2]/span/a"));

						if (phoneNumElement.size() > 0) {
							phonenum = phoneNumElement.get(0).getText();

						}
					}

					System.out.println("The record number " + j + "    " + name + " " + address + "   " + numvotes
							+ "     " + phonenum);

					if (name == null || address == null || numvotes == null | phonenum == null) {
						System.out.println("some value is null so owe are not goin got write into the xls");
					}

					if (null != name && null != phonenum) {
						System.out.println(name + "" + address + "" + numvotes + "" + phonenum);
						//Company electrician = new Company(name, address, numvotes, phonenum);
						//listElectricians.add(electrician);
					}
				}

				ExportToXLS exportToXLS = new ExportToXLS();
				try {

					System.out.println(listElectricians.size() + "are wrinting into xls ");
					exportToXLS.createXLS(listElectricians);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (Exception e) {
				System.err.println("Some thing gone wrong in fetching so I am going to export the data ");
				e.printStackTrace();
			}
		}

		// WRITE TO XLS

		// Close the driver
		driver.close();

	}

}
