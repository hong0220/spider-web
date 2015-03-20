package com.webcollector.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;

import com.webcollector.souplang.Context;
import com.webcollector.souplang.SoupLang;

public class DemoSoupLang {
	public static void main(String[] args) throws IOException,
			ParserConfigurationException, SAXException {
		Document doc = Jsoup
				.connect("http://www.youdao.com/search?q=webcollector")
				.userAgent(
						"Mozilla/5.0 (X11; Linux i686; rv:34.0) Gecko/20100101 Firefox/34.0")
				.get();
		SoupLang soupLang = new SoupLang(
				ClassLoader.getSystemResourceAsStream("example/DemoRule2.xml"));
		Context context = soupLang.extract(doc);
		System.out.println(context);
	}
}
