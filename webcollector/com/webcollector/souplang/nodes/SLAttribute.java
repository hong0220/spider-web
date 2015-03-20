package com.webcollector.souplang.nodes;

import java.util.ArrayList;

import com.webcollector.souplang.nodes.InputTypeErrorException;
import com.webcollector.souplang.nodes.SLAttribute;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.souplang.Context;
import com.webcollector.souplang.LangNode;

public class SLAttribute extends LangNode {
	public static final Logger LOG = LoggerFactory.getLogger(SLAttribute.class);
	public String attributeName = null;

	public void readAttribute(org.w3c.dom.Element xmlElement) {
		attributeName = xmlElement.getAttribute("attr");
		if (attributeName.isEmpty()) {
			attributeName = null;
		}
	}

	@Override
	public Object process(Object input, Context context)
			throws InputTypeErrorException {
		if (attributeName == null) {
			return null;
		}
		Element jsoupElement = null;
		Elements jsoupElements = null;
		if (input instanceof Element) {
			jsoupElement = (Element) input;
			if (!jsoupElement.hasAttr(attributeName)) {
				return null;
			}
			String result = jsoupElement.attr(attributeName);
			return result;
		} else {
			jsoupElements = (Elements) input;
			ArrayList<String> result = new ArrayList<String>();
			for (Element ele : jsoupElements) {
				if (ele.hasAttr(attributeName)) {
					System.out.println("attr=" + ele.attr(attributeName));
					result.add(ele.attr(attributeName));
				}
			}
			return result;
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
