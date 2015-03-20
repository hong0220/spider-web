//package com.webcollector.visitor;
//
//import java.util.LinkedHashMap;
//import java.util.regex.Pattern;
//
//import com.webcollector.visitor.Visitor;
//import com.webcollector.visitor.VisitorFactory;
//
//public class MapVisitorFactory implements VisitorFactory {
//	public LinkedHashMap<String, Visitor> visitorMap = new LinkedHashMap<String, Visitor>();
//
//	public void addVisitor(String urlPattern, Visitor visitor) {
//		visitorMap.put(urlPattern, visitor);
//	}
//
//	@Override
//	public Visitor createVisitor(String url, String contentType) {
//		for (String urlPattern : visitorMap.keySet()) {
//			if (Pattern.matches(urlPattern, url)) {
//				return visitorMap.get(urlPattern);
//			}
//		}
//		return null;
//	}
//
//	public LinkedHashMap<String, Visitor> getVisitorMap() {
//		return visitorMap;
//	}
//
//	public void setVisitorMap(LinkedHashMap<String, Visitor> visitorMap) {
//		this.visitorMap = visitorMap;
//	}
// }
