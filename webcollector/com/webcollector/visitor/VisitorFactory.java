package com.webcollector.visitor;

import com.webcollector.visitor.Visitor;

public interface VisitorFactory {
	public Visitor createVisitor(String url, String contentType);
}
