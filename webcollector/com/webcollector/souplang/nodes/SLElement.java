package com.webcollector.souplang.nodes;

import com.webcollector.souplang.nodes.InputTypeErrorException;
import com.webcollector.souplang.nodes.SLElement;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.souplang.Context;
import com.webcollector.souplang.LangNode;

public class SLElement extends LangNode {
	public static final Logger LOG = LoggerFactory.getLogger(SLElement.class);
	public String cssSelector = null;
	public int index = 0;

	public void readCSSSelector(org.w3c.dom.Element xmlElement) {
		cssSelector = xmlElement.getAttribute("selector");
		if (cssSelector.isEmpty()) {
			cssSelector = null;
		}
	}

	public void readIndex(org.w3c.dom.Element xmlElement) {
		String indexAttr = xmlElement.getAttribute("index");
		if (!indexAttr.isEmpty()) {
			index = Integer.valueOf(indexAttr);
		}
	}

	@Override
	public Object process(Object input, Context context)
			throws InputTypeErrorException {
		Element jsoupElement = null;
		Elements jsoupElements = null;
		if (input instanceof Element) {
			jsoupElement = (Element) input;
		} else {
			jsoupElements = (Elements) input;
		}
		if (cssSelector != null && !cssSelector.isEmpty()) {
			Element result;
			if (jsoupElement != null) {
				result = jsoupElement.select(cssSelector).get(index);
			} else {
				result = jsoupElements.select(cssSelector).get(index);
			}
			// System.out.println("this is element" + result);
			return result;
		} else {
			return input;
		}
	}

	@Override
	public boolean validate(Object input) throws Exception {
		if (!(input instanceof Element) && !(input instanceof Elements)) {
			return false;
		}
		return true;
	}
}
