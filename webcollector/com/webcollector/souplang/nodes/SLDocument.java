package com.webcollector.souplang.nodes;

import java.util.regex.Pattern;

import com.webcollector.souplang.nodes.InputTypeErrorException;
import com.webcollector.souplang.nodes.SLDocument;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.souplang.Context;
import com.webcollector.souplang.LangNode;

public class SLDocument extends LangNode {
	public static final Logger LOG = LoggerFactory.getLogger(SLDocument.class);
	public String urlRegex = null;

	public void readUrlRegex(org.w3c.dom.Element xmlElement) {
		urlRegex = xmlElement.getAttribute("url");
		if (urlRegex.isEmpty()) {
			urlRegex = null;
		}
	}

	@Override
	public boolean validate(Object input) throws Exception {
		if (!(input instanceof Document)) {
			return false;
		}
		return true;
	}

	@Override
	public Object process(Object input, Context context)
			throws InputTypeErrorException {
		if (!(input instanceof Document)) {
			throw new InputTypeErrorException();
		}
		Document jsoupDoc = (Document) input;
		LOG.debug("baseuri=" + jsoupDoc.baseUri());
		if (jsoupDoc.baseUri() != null && urlRegex != null) {
			if (!Pattern.matches(urlRegex, jsoupDoc.baseUri())) {
				return null;
			}
		}
		return input;
	}
}
