package com.spider.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spider.utils.StringUtil;
import com.spider.xml.model.Column;
import com.spider.xml.model.XmlConfig;
import com.webcollector.util.FileUtils;

public class JdomUtil {
	public static void main(String args[]) {
		try {
			readXML("xml/FengHuangConfig.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<XmlConfig> getXmlConfig() {
		String[] strList = null;
		List<File> fileList = new ArrayList<File>();
		List<XmlConfig> xmlConfigList = readXML(fileList);
		try {
			strList = FileUtils.readfile("xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < strList.length; i++) {
			File readfile = new File("xml" + "\\" + strList[i]);
			// System.out.println(readfile.getPath());
			fileList.add(readfile);
		}
		xmlConfigList = readXML(fileList);
		for (XmlConfig xmlConfig : xmlConfigList) {
			System.out.println(xmlConfig);
		}

		return xmlConfigList;
	}

	public static List<XmlConfig> readXML(List<File> path) {
		List<XmlConfig> list = new ArrayList<XmlConfig>();
		for (File file : path) {
			try {
				list.add(readXML(file.getPath()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static XmlConfig readXML(String path) throws Exception {
		XmlConfig xmlConfig = new XmlConfig();
		List<Column> ColumnNames = new ArrayList<Column>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(path);
		doc.normalize();

		NodeList task = doc.getElementsByTagName("Task");
		for (int i = 0; i < task.getLength(); i++) {
			Element link = (Element) task.item(i);
			if (!StringUtil.isEmpty(link.getElementsByTagName("Name").item(0)
					.getFirstChild().getNodeValue())) {
				xmlConfig.setName(link.getElementsByTagName("Name").item(0)
						.getFirstChild().getNodeValue());
			}
			if (!StringUtil.isEmpty(link.getElementsByTagName("ContentTable")
					.item(0).getFirstChild().getNodeValue())) {
				xmlConfig.setContentTable(link
						.getElementsByTagName("ContentTable").item(0)
						.getFirstChild().getNodeValue());
			}
			if (!StringUtil.isEmpty(link.getElementsByTagName("Encoding")
					.item(0).getFirstChild().getNodeValue())) {
				xmlConfig.setEncoding(link.getElementsByTagName("Encoding")
						.item(0).getFirstChild().getNodeValue());
			}
			if (!StringUtil.isEmpty(link.getElementsByTagName("LinkUrl")
					.item(0).getFirstChild().getNodeValue())) {
				xmlConfig.setLinkUrl(link.getElementsByTagName("LinkUrl")
						.item(0).getFirstChild().getNodeValue());
			}
			if (!StringUtil.isEmpty(link.getElementsByTagName("Regex").item(0)
					.getFirstChild().getNodeValue())) {
				xmlConfig.setRegex(link.getElementsByTagName("Regex").item(0)
						.getFirstChild().getNodeValue());
			}
			if (!StringUtil.isEmpty(link.getElementsByTagName("CrawlDepth")
					.item(0).getFirstChild().getNodeValue())) {
				xmlConfig.setCrawlDepth(Integer.valueOf(link
						.getElementsByTagName("CrawlDepth").item(0)
						.getFirstChild().getNodeValue()));
			}
			if (!StringUtil.isEmpty(link.getElementsByTagName("Interval")
					.item(0).getFirstChild().getNodeValue())) {
				xmlConfig.setInterval(Integer.valueOf(link
						.getElementsByTagName("Interval").item(0)
						.getFirstChild().getNodeValue()));
			}

			NodeList ColumnNamesNodeList = link
					.getElementsByTagName("ColumnNames");
			Element link2 = (Element) ColumnNamesNodeList.item(0);
			NodeList Columns = link2.getElementsByTagName("Column");
			for (int j = 0; j < Columns.getLength(); j++) {
				Column column = new Column();
				Element link3 = (Element) Columns.item(j);
				if (!StringUtil.isEmpty(link3.getElementsByTagName("Name")
						.item(0).getFirstChild().getNodeValue())) {
					column.setName(link3.getElementsByTagName("Name").item(0)
							.getFirstChild().getNodeValue());
				}
				if (!StringUtil.isEmpty(link3.getElementsByTagName("Xpath")
						.item(0).getFirstChild().getNodeValue())) {
					column.setXpath(link3.getElementsByTagName("Xpath").item(0)
							.getFirstChild().getNodeValue());
					ColumnNames.add(column);
				}
			}
			xmlConfig.setColumnNames(ColumnNames);
			System.out.println(xmlConfig);
		}
		return xmlConfig;
	}
}
