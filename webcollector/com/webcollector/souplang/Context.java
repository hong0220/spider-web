package com.webcollector.souplang;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.souplang.Context;

public class Context {
	public static final Logger LOG = LoggerFactory.getLogger(Context.class);
	public HashMap<String, Object> output = new HashMap<String, Object>();

	public Object get(String name) {
		return output.get(name);
	}

	public String getString(String name) {
		Object result = output.get(name);
		if (result == null) {
			return null;
		} else {
			return result.toString();
		}
	}

	public ArrayList<String> getList(String name) throws Exception {
		Object value = output.get(name);
		if (value == null) {
			return null;
		} else if (value instanceof ArrayList) {
			ArrayList<String> result = (ArrayList<String>) value;
			return result;
		} else {
			throw new Exception("not a list");
		}
	}

	@Override
	public String toString() {
		// To change body of generated methods, choose Tools | Templates.
		return output.toString();
	}
}
