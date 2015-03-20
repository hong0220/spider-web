package com.webcollector.net;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.net.HttpRequester;
import com.webcollector.net.HttpRequesterImpl;
import com.webcollector.net.HttpResponse;
import com.webcollector.proxy.Proxys;
import com.webcollector.util.Config;

public class HttpRequesterImpl implements HttpRequester {
	public static final Logger LOG = LoggerFactory
			.getLogger(HttpRequesterImpl.class);
	protected Proxys proxys = null;
	protected String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:27.0) Gecko/20100101 Firefox/27.0";
	protected String cookie = null;

	public void configConnection(HttpURLConnection con) {
	}

	@Override
	public HttpResponse getResponse(String url) throws Exception {
		HttpResponse response = new HttpResponse(url);
		HttpURLConnection con;
		URL _URL = new URL(url);
		if (proxys == null) {
			con = (HttpURLConnection) _URL.openConnection();
		} else {
			Proxy proxy = proxys.nextProxy();
			if (proxy == null) {
				con = (HttpURLConnection) _URL.openConnection();
			} else {
				con = (HttpURLConnection) _URL.openConnection(proxy);
			}
		}
		if (userAgent != null) {
			con.setRequestProperty("User-Agent", userAgent);
		}
		if (cookie != null) {
			con.setRequestProperty("Cookie", cookie);
		}
		con.setDoInput(true);
		con.setDoOutput(true);
		// con.setConnectTimeout(10000);
		// con.setReadTimeout(10000);
		configConnection(con);
		response.setCode(con.getResponseCode());
		InputStream is = con.getInputStream();
		byte[] buf = new byte[2048];
		int read;
		int sum = 0;
		int maxsize = Config.MAX_RECEIVE_SIZE;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((read = is.read(buf)) != -1) {
			if (maxsize > 0) {
				sum = sum + read;
				if (sum > maxsize) {
					read = maxsize - (sum - read);
					bos.write(buf, 0, read);
					break;
				}
			}
			bos.write(buf, 0, read);
		}
		is.close();
		response.setContent(bos.toByteArray());
		response.setHeaders(con.getHeaderFields());
		bos.close();
		return response;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public Proxys getProxys() {
		return proxys;
	}

	public void setProxys(Proxys proxys) {
		this.proxys = proxys;
	}

	public static void main(String[] args) {
		HttpRequesterImpl requester = new HttpRequesterImpl();
		HttpResponse response = null;
		Proxys proxys = new Proxys();
		proxys.add("124.127.204.249", 8080);
		requester.setProxys(proxys);
		String url = "http://wwww.baidu.com";
		try {
			response = requester.getResponse(url);
			System.out.println(response.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("连接异常" + url);
		}
	}
}
