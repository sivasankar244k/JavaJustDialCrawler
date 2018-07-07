package com.justdial.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class VoterListGrabber {

	public static void main(String[] args) throws IOException {
		Document document;
		document = Jsoup.connect("https://www.justdial.com/Chennai/House-Painters/nct-11017366page-1")
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
				.get();
	}

}
