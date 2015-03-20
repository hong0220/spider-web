package com.webcollector.souplang;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import com.webcollector.souplang.Context;
import com.webcollector.souplang.LangNode;

public abstract class LangNode {
	public static final Logger LOG = LoggerFactory.getLogger(LangNode.class);
	public String name = null;

	public void readName(Element xmlElement) {
		name = xmlElement.getAttribute("name");
		if (name.isEmpty()) {
			name = null;
		}
	}

	public abstract Object process(Object input, Context context)
			throws Exception;

	public abstract boolean validate(Object input) throws Exception;

	public void save(Object output, Context context) {
		if (context != null && name != null) {
			context.output.put(name, output);
		}
	}

	public void processAll(Object input, Context context) throws Exception {
		if (!validate(input)) {
			return;
		}
		Object output = null;
		try {
			output = process(input, context);
		} catch (Exception e) {
			LOG.info("Exception", e);
			return;
		}
		save(output, context);
		/*
		 * if (output == null) { return; } for (LanguageNode child : children) {
		 * child.context=this.context; child.processAll(output); }
		 */
	}

	public ArrayList<LangNode> children = new ArrayList<LangNode>();

	public void addChild(LangNode child) {
		this.children.add(child);
	}

	public void printTree(String prefix) {
		System.out.println(prefix + this.getClass());
		for (LangNode child : children) {
			child.printTree("   " + prefix);
		}
	}
}
