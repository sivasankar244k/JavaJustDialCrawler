package com.justdial.crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class Sikuli {

	public void doIPChnage(String url) throws FindFailed, InterruptedException {
		Map<String, ArrayList<Pattern>> patternMap = getPatternMap();

		Screen screen = new Screen();

		App.open("C:\\Program Files (x86)\\Windscribe\\WindscribeLauncher.exe");

		List usefulIpList = new ArrayList();
		// usefulIpList.add(0);
		//usefulIpList.add(2);
		usefulIpList.add(5);
		usefulIpList.add(6);
		usefulIpList.add(20);
		usefulIpList.add(23);
		usefulIpList.add(24);
		usefulIpList.add(26);
		usefulIpList.add(28);
		usefulIpList.add(32);
		usefulIpList.add(37);
		usefulIpList.add(46);
		usefulIpList.add(49);
		usefulIpList.add(51);
		usefulIpList.add(68);
		usefulIpList.add(70);
		usefulIpList.add(97);
		usefulIpList.add(100);
		usefulIpList.add(108);
		usefulIpList.add(119);
		usefulIpList.add(123);
		usefulIpList.add(124);
		usefulIpList.add(164);

		

				playDownUp(patternMap, screen, usefulIpList,url);

				/*
				 * screen.click(patternMap.get("bestLocation").get(0));
				 * 
				 * Thread.sleep(60000);
				 * 
				 * screen.click(patternMap.get("mainExpand").get(0));
				 * 
				 * Thread.sleep(3000); screen.click(patternMap.get("usCentral").get(0));
				 * 
				 * Thread.sleep(60000);
				 * 
				 * screen.click(patternMap.get("usCentral").get(1));
				 * 
				 * Thread.sleep(60000); screen.click(patternMap.get("mainExpand").get(0));
				 * Thread.sleep(5000);
				 * 
				 * screen.click(patternMap.get("usCentral").get(2)); Thread.sleep(60000);
				 * screen.click(patternMap.get("mainExpand").get(0)); Thread.sleep(5000);
				 * 
				 * screen.click(patternMap.get("usCentral").get(3)); Thread.sleep(60000);
				 */
				/*
				 * screen.click(patternMap.get("mainExpand").get(0)); Thread.sleep(5000);
				 */



	}

	
	private static void playDownUp(Map<String, ArrayList<Pattern>> patternMap, Screen screen, List usefulIpList,String url)
			throws FindFailed, InterruptedException {
		screen.click(patternMap.get("mainExpand").get(0));
		Thread.sleep(5000);

		// get back to first position then we can start after that

		for (int i = 1; i <= 164; i++) {
			screen.type(Key.UP);
			System.out.println("i is " + i);

		}

		Thread.sleep(5000);

		// going down to click on ip addres
		for (int i = 1; i <= 164; i++) {
			
			Thread.sleep(600);
			screen.type(Key.DOWN);
			System.out.println("i is " + i);
			
				if (usefulIpList.contains(i)) {

					
					System.out.println("i is " + i);
					Thread.sleep(600);

					screen.type(Key.ENTER);
					Thread.sleep(60000);
					
					try
					{
						
						Document document = Jsoup
								.connect(url)
								.userAgent(
										"Mozimlla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
								.get();

						return ;
					
						
					}catch(Exception e) {
						System.out.println("i is  have to expnad the tab " + i);
						e.printStackTrace();
						screen.click(patternMap.get("mainExpand").get(0));
					}
					Thread.sleep(6000);
				}
			
		}

		Thread.sleep(5000);
		screen.click(patternMap.get("mainExpand").get(0));

	}

	private static Map<String, ArrayList<Pattern>> getPatternMap() {

		
		Map<String, ArrayList<Pattern>> patternMap = new HashMap<String, ArrayList<Pattern>>();

		Pattern mainExpand = new Pattern("C:\\Users\\Siva\\Documents\\Sikoli-images\\Expand.PNG");

		ArrayList<Pattern> mainExpandList = new ArrayList<Pattern>();
		mainExpandList.add(mainExpand);

		patternMap.put("mainExpand", mainExpandList);

		Pattern internalExpand = new Pattern("C:\\Users\\Siva\\Documents\\Sikoli-images\\internalExpand.PNG");

		ArrayList<Pattern> internalExpandList = new ArrayList<Pattern>();
		internalExpandList.add(internalExpand);

		patternMap.put("internalExpand", internalExpandList);

		Pattern bestLocation = new Pattern("C:\\Users\\Siva\\Documents\\Sikoli-images\\01-BestLocation.PNG"); // 1

		ArrayList<Pattern> bestLocationList = new ArrayList<Pattern>();
		bestLocationList.add(bestLocation);

		patternMap.put("bestLocation", bestLocationList);

		// US Central
		Pattern usCentral = new Pattern("C:\\Users\\Siva\\Documents\\Sikoli-images\\02-USCentral.PNG");

		Pattern usCentral_Atlanta_Mountain = new Pattern(
				"C:\\Users\\Siv" + "a\\Documents\\Sikoli-images\\usCentral-Atlanta-Mountain.PNG"); // 2
		Pattern usCentral_Dallas_BBQ = new Pattern(
				"C:\\Users\\Siva\\Documents\\Sikoli-images\\usCentral-Dallas-BBQ.PNG"); // 3
		Pattern usCentral_Dalls_Ranch = new Pattern(
				"C:\\Users\\Siva\\Documents\\Sikoli-images\\usCentral-Dalls-Ranch.PNG"); // 4

		ArrayList<Pattern> usCentralList = new ArrayList<Pattern>();
		usCentralList.add(usCentral);

		usCentralList.add(usCentral_Atlanta_Mountain);
		usCentralList.add(usCentral_Dallas_BBQ);
		usCentralList.add(usCentral_Dalls_Ranch);

		patternMap.put("usCentral", usCentralList);

		// US East
		Pattern usEast = new Pattern("C:\\Users\\Siva\\Documents\\Sikoli-images\\03-USEast.PNG");

		Pattern usEast_Chicago_Cub = new Pattern("C:\\Users\\Siva\\Documents\\Sikoli-images\\usEast-Chicago-Cub.PNG"); // 5
		Pattern usEast_Miami_Vice = new Pattern("C:\\Users\\Siva\\Documents\\Sikoli-images\\usEast-Miami-Vice.PNG"); // 6
		Pattern usEast_Newyork_Empire = new Pattern(
				"C:\\Users\\Siva\\Documents\\Sikoli-images\\usEast-Newyork-Empire.PNG"); // 7

		ArrayList<Pattern> usEastList = new ArrayList<Pattern>();
		usEastList.add(usEast);

		usEastList.add(usEast_Chicago_Cub);
		usEastList.add(usEast_Miami_Vice);
		usEastList.add(usEast_Newyork_Empire);

		patternMap.put("usEast", usEastList);

		// US West
		Pattern usWest = new Pattern("C:\\Users\\Siva\\Documents\\Sikoli-images\\04-USWest.PNG");

		Pattern usWest_LosAngeles_Dogg = new Pattern(
				"C:\\Users\\Siva\\Documents\\Sikoli-images\\usWest-LosAngeles-Dogg.PNG"); // 8
		Pattern usWest_Sanfransico_Bay = new Pattern(
				"C:\\Users\\Siva\\Documents\\Sikoli-images\\usWest-Sanfransico-Bay.PNG"); // 9

		ArrayList<Pattern> usWestList = new ArrayList<Pattern>();
		usWestList.add(usWest);
		usWestList.add(usWest_LosAngeles_Dogg);
		usWestList.add(usWest_Sanfransico_Bay);

		patternMap.put("usWest", usWestList);

		Pattern canadaEast = new Pattern("C:\\Users\\Siva\\Documents\\Sikoli-images\\05-CanadaEast.PNG"); // 10

		ArrayList<Pattern> canadaEastList = new ArrayList<Pattern>();
		canadaEastList.add(canadaEast);
		patternMap.put("canadaEast", canadaEastList);

		Pattern canadaWest = new Pattern("C:\\Users\\Siva\\Documents\\Sikoli-images\\06-CanadaWest.PNG"); // 11

		ArrayList<Pattern> canadaWestList = new ArrayList<Pattern>();
		canadaWestList.add(canadaWest);
		patternMap.put("canadaWest", canadaWestList);

		return patternMap;
	}

}
