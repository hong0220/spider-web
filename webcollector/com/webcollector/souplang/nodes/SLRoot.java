package com.webcollector.souplang.nodes;

import com.webcollector.souplang.nodes.SLRoot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.souplang.Context;
import com.webcollector.souplang.LangNode;

public class SLRoot extends LangNode {
	public static final Logger LOG = LoggerFactory.getLogger(SLRoot.class);

	@Override
	public Object process(Object input, Context context) throws Exception {
		return input;
	}

	@Override
	public boolean validate(Object input) throws Exception {
		return true;
	}
}
