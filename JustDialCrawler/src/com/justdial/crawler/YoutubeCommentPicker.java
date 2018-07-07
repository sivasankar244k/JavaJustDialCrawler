package com.justdial.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class YoutubeCommentPicker {
	public static void main(String args[]) throws IOException {

		Document document;
		String uri;
		// Get Document object after parsing the html from given url.

		document = Jsoup.connect("https://www.youtube.com/watch?v=kYc0x4gFCGg")
				.header("Accept-Encoding", "gzip, deflate")
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
				.maxBodySize(0).timeout(600000).get();
		
		System.out.println(document);

		Elements comments = document.getElementsByClass("style-scope ytd-comment-renderer");
		for (Element comment : comments) {
			System.out.println(comment);
		}

	}

}
