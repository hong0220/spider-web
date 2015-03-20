package com.webcollector.souplang;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.webcollector.souplang.LangNode;
import com.webcollector.souplang.Parser;

import com.webcollector.souplang.nodes.SLAttribute;
import com.webcollector.souplang.nodes.SLDocument;
import com.webcollector.souplang.nodes.SLDocuments;
import com.webcollector.souplang.nodes.SLElement;
import com.webcollector.souplang.nodes.SLElements;
import com.webcollector.souplang.nodes.SLList;
import com.webcollector.souplang.nodes.SLNext;
import com.webcollector.souplang.nodes.SLNextElement;
import com.webcollector.souplang.nodes.SLRoot;
import com.webcollector.souplang.nodes.SLSQL;
import com.webcollector.souplang.nodes.SLStr;
import com.webcollector.souplang.nodes.SLText;

public class Parser {
	public static final Logger LOG = LoggerFactory.getLogger(Parser.class);
	public LangNode root = new SLRoot();

	public Parser(Element xmlRoot) throws ParserConfigurationException,
			SAXException, IOException {
		parse(xmlRoot, root);
	}

	public Parser(InputStream is) throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = f.newDocumentBuilder();
		Document xmlDoc = builder.parse(is);
		Element xmlRoot = xmlDoc.getDocumentElement();
		parse(xmlRoot, root);
	}

	public void parse(Node xmlNode, LangNode lsNode)
			throws ParserConfigurationException, SAXException, IOException {
		if (xmlNode instanceof Element) {
			Element xmlElement = (Element) xmlNode;
			String tagName = xmlElement.getTagName().toLowerCase();
			LangNode childLSNode = null;
			childLSNode = createNode(xmlNode);
			if (childLSNode != null) {
				lsNode.addChild(childLSNode);
				NodeList xmlChildNodeList = xmlNode.getChildNodes();
				for (int i = 0; i < xmlChildNodeList.getLength(); i++) {
					parse(xmlChildNodeList.item(i), childLSNode);
				}
			}
		}
	}

	public static LangNode createNode(Node node) {
		if (node instanceof Element) {
			Element element = (Element) node;
			String tagName = element.getTagName().toLowerCase();
			if (tagName.equals("list")) {
				SLList slList = new SLList();
				slList.readName(element);
				slList.readCSSSelector(element);
				return slList;
			}
			if (tagName.equals("element") || tagName.equals("el")) {
				SLElement slElement = new SLElement();
				slElement.readName(element);
				slElement.readCSSSelector(element);
				slElement.readIndex(element);
				return slElement;
			}
			if (tagName.equals("elements") || tagName.equals("els")) {
				SLElements slelements = new SLElements();
				slelements.readName(element);
				slelements.readCSSSelector(element);
				return slelements;
			}
			if (tagName.equals("attribute") || tagName.equals("attr")) {
				SLAttribute slAttribute = new SLAttribute();
				slAttribute.readName(element);
				slAttribute.readAttribute(element);
				return slAttribute;
			}
			if (tagName.equals("sql")) {
				SLSQL slSQL = new SLSQL();
				slSQL.readName(element);
				slSQL.readParams(element);
				slSQL.readTemplate(element);
				slSQL.readSql(element);
				return slSQL;
			}
			if (tagName.equals("docs")) {
				SLDocuments sLDocuments = new SLDocuments();
				return sLDocuments;
			}
			if (tagName.equals("str")) {
				SLStr slStr = new SLStr();
				slStr.readName(element);
				slStr.readValue(element);
				return slStr;
			}
			if (tagName.equals("next")) {
				SLNext slNext = new SLNext();
				slNext.readName(element);
				slNext.readIndex(element);
				return slNext;
			}
			if (tagName.equals("nextelement") || tagName.equals("nexte")) {
				SLNextElement slNextElement = new SLNextElement();
				slNextElement.readName(element);
				slNextElement.readIndex(element);
				return slNextElement;
			}
			if (tagName.equals("text")) {
				SLText slText = new SLText();
				slText.readName(element);
				slText.readRegex(element);
				slText.readGroup(element);
				return slText;
			}
			if (tagName.equals("doc")) {
				SLDocument sLDocument = new SLDocument();
				sLDocument.readName(element);
				sLDocument.readUrlRegex(element);
				return sLDocument;
			}
			if (tagName.equals("root")) {
				return new SLRoot();
			}
			return null;
		} else {
			return null;
		}
	}
}
