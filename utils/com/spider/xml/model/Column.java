package com.spider.xml.model;

public class Column {
	private String name;
	private String Xpath;

	public Column() {

	}

	public Column(String name, String xpath) {
		this.name = name;
		Xpath = xpath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXpath() {
		return Xpath;
	}

	public void setXpath(String xpath) {
		Xpath = xpath;
	}

	@Override
	public String toString() {
		return "Column [name=" + name + ", Xpath=" + Xpath + "]";
	}
}
