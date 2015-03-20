package com.webcollector.souplang.nodes;

import com.webcollector.souplang.nodes.InputTypeErrorException;
import com.webcollector.souplang.nodes.SLStr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.souplang.Context;
import com.webcollector.souplang.LangNode;

public class SLStr extends LangNode {
	public static final Logger LOG = LoggerFactory.getLogger(SLStr.class);
	public String value = null;

	public void readValue(org.w3c.dom.Element xmlElement) {
		value = xmlElement.getAttribute("value");
		if (value.isEmpty()) {
			value = null;
		}
	}

	@Override
	public Object process(Object input, Context context)
			throws InputTypeErrorException {
		if (value == null || name == null) {
			return null;
		}
		return value;
	}

	@Override
	public boolean validate(Object input) throws Exception {
		return true;
	}
}
