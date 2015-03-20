package com.webcollector.souplang.nodes;

import com.webcollector.souplang.nodes.InputTypeErrorException;
import com.webcollector.souplang.nodes.SLDocuments;
import com.webcollector.souplang.nodes.SLElement;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.souplang.Context;

public class SLDocuments extends SLElement {
	public static final Logger LOG = LoggerFactory.getLogger(SLDocuments.class);
	public String urlRegex = null;

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
		return input;
	}
}
