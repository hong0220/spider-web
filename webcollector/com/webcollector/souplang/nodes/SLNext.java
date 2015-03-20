package com.webcollector.souplang.nodes;

import com.webcollector.souplang.nodes.InputTypeErrorException;
import com.webcollector.souplang.nodes.SLNext;

import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.souplang.Context;
import com.webcollector.souplang.LangNode;

public class SLNext extends LangNode {
	public static final Logger LOG = LoggerFactory.getLogger(SLNext.class);
	public int index = 0;

	public void readIndex(org.w3c.dom.Element xmlElement) {
		String indexAttr = xmlElement.getAttribute("index");
		if (!indexAttr.isEmpty()) {
			index = Integer.valueOf(indexAttr);
		}
	}

	@Override
	public Object process(Object input, Context context)
			throws InputTypeErrorException {
		Node jsoupNode = (Node) input;
		Node result = jsoupNode;
		for (int i = 0; i <= index; i++) {
			result = result.nextSibling();
		}
		LOG.debug(result.outerHtml());
		return result;
	}

	@Override
	public boolean validate(Object input) throws Exception {
		if (!(input instanceof Node)) {
			return false;
		}
		return true;
	}
}
