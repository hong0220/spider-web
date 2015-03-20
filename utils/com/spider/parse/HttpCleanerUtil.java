package com.spider.parse;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class HttpCleanerUtil {

	public static Object[] crawlByXPath(String html, String xpathExp) {
		HtmlCleaner hc = new HtmlCleaner();
		TagNode tn = hc.clean(html);
		Object[] objs = null;
		try {
			objs = tn.evaluateXPath(xpathExp);
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return objs;
	}
}
