package com.spider.xml.model;

import java.util.List;

public class XmlConfig {
	private String Name;
	private String ContentTable;
	private String Encoding;
	private String LinkUrl;
	private String Regex;
	private int CrawlDepth;
	private int Interval;
	private List<Column> ColumnNames;

	public XmlConfig() {
	}

	public XmlConfig(String name, String contentTable, String encoding,
			String linkUrl, String regex, int crawlDepth, int interval,
			List<Column> columnNames) {
		Name = name;
		ContentTable = contentTable;
		Encoding = encoding;
		LinkUrl = linkUrl;
		Regex = regex;
		CrawlDepth = crawlDepth;
		Interval = interval;
		ColumnNames = columnNames;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getContentTable() {
		return ContentTable;
	}

	public void setContentTable(String contentTable) {
		ContentTable = contentTable;
	}

	public String getEncoding() {
		return Encoding;
	}

	public void setEncoding(String encoding) {
		Encoding = encoding;
	}

	public String getLinkUrl() {
		return LinkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		LinkUrl = linkUrl;
	}

	public String getRegex() {
		return Regex;
	}

	public void setRegex(String regex) {
		Regex = regex;
	}

	public int getCrawlDepth() {
		return CrawlDepth;
	}

	public void setCrawlDepth(int crawlDepth) {
		CrawlDepth = crawlDepth;
	}

	public int getInterval() {
		return Interval;
	}

	public void setInterval(int interval) {
		Interval = interval;
	}

	public List<Column> getColumnNames() {
		return ColumnNames;
	}

	public void setColumnNames(List<Column> columnNames) {
		ColumnNames = columnNames;
	}

	@Override
	public String toString() {
		return "XmlConfig [Name=" + Name + ", ContentTable=" + ContentTable
				+ ", Encoding=" + Encoding + ", LinkUrl=" + LinkUrl
				+ ", Regex=" + Regex + ", CrawlDepth=" + CrawlDepth
				+ ", Interval=" + Interval + ", ColumnNames=" + ColumnNames
				+ "]";
	}
}
