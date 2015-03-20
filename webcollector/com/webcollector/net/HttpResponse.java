package com.webcollector.net;

import java.util.List;
import java.util.Map;

/**
 * Response的一种实现，WebCollector默认使用HttpResponse作为http响应
 */
public class HttpResponse {
	private String url;
	private int code;
	private Map<String, List<String>> headers = null;
	private byte[] content = null;

	public String getContentType() {
		try {
			String contentType;
			List<String> contentTypeList = getHeader("Content-Type");
			if (contentTypeList == null) {
				contentType = null;
			} else {
				contentType = contentTypeList.get(0);
			}
			return contentType;
		} catch (Exception e) {
			return null;
		}
	}

	public HttpResponse(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCode() {
		return code;
	}

	public List<String> getHeader(String name) {
		return headers.get(name);
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}
}
