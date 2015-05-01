package com.webcollector.crawler.demo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

public class MyLinks extends Links {
	public void filterImgUrl(Document doc, RegexRule regexRule) {
		Elements as = doc.select("img[src]");
		for (Element a : as) {
			String href = a.attr("abs:src");
			if (regexRule.satisfy(href))
				add(href);
		}
	}
}
