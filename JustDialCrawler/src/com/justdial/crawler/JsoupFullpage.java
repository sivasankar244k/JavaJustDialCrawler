package com.justdial.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupFullpage {

	public static void main(String args[])
	{
		;
		
		try {
			Document doc = Jsoup.connect("https://www.justdial.com/photos/j-p-enterprises-vidyaranyapura-bangalore-carpenters-8q9z6-pc-73906100-sco-152ea6eyuqu").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").get();
	
		System.out.println(doc);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
