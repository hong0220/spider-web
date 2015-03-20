package com.webcollector.model;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.webcollector.util.RegexRule;

public class Links extends ArrayList<String> {

	public void addAllFromDocument(Document doc) {
		Elements as = doc.select("a[href]");
		for (Element a : as) {
			String href = a.attr("abs:href");
			this.add(href);
		}
	}

	public void addAllFromDocument(Document doc, String cssSelector) {
		Elements as = doc.select(cssSelector).select("a[href]");
		for (Element a : as) {
			String href = a.attr("abs:href");
			this.add(href);
		}
	}

	public void addAllFromDocument(Document doc, RegexRule regexRule) {
		Elements as = doc.select("a[href]");
		for (Element a : as) {
			String href = a.attr("abs:href");
			if (regexRule.satisfy(href)) {
				this.add(href);
			}
		}
	}

	public void addAllFromDocument(Document doc, String cssSelector,
			RegexRule regexRule) {
		Elements as = doc.select(cssSelector).select("a[href]");
		for (Element a : as) {
			String href = a.attr("abs:href");
			if (regexRule.satisfy(href)) {
				this.add(href);
			}
		}
	}
}
