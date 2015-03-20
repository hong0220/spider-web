package com.spider.utils;

public class StringUtil {
	public static Boolean isEmpty(String str) {
		if (str == null || "".equals(str) || " ".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static String filter(String html) {
		if (html == null || "".equals(html.trim())) {
			return "";
		}
		// &apos; (IE不支持)
		html = html.replace("&apos;", "'");
		html = html.replaceAll("&amp;", "&");
		html = html.replace("&quot;", "\"");
		html = html.replace("&nbsp;", "");
		html = html.replace("&lt;", "<");
		html = html.replaceAll("&gt;", ">");
		return html;
	}

	public static String filter2(String html) {
		if (html == null || "".equals(html.trim())) {
			return "";
		}
		// &apos; (IE不支持)
		// html = html.replace( "'", "&apos;");
		html = html.replaceAll("&", "&amp;");
		html = html.replace("\"", "&quot;");
		html = html.replace("\t", "&nbsp;&nbsp;");// 替换跳格
		html = html.replace(" ", "&nbsp;");// 替换空格
		html = html.replace("<", "&lt;");
		html = html.replaceAll(">", "&gt;");
		return html;
	}
}
