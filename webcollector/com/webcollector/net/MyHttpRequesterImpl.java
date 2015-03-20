package com.webcollector.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

import org.apache.log4j.Logger;

import com.webcollector.proxy.MyProxys;
import com.webcollector.proxy.Proxys;
import com.webcollector.util.Config;

/**
 *
 */
public class MyHttpRequesterImpl implements HttpRequester {
	private Logger logger = Logger.getLogger(MyHttpRequesterImpl.class);
	protected Proxys proxys = null;
	protected String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:27.0) Gecko/20100101 Firefox/27.0";
	protected String cookie = null;

	public void configConnection(HttpURLConnection con) {
	}

	@Override
	public HttpResponse getResponse(String url) throws Exception {
		HttpResponse response = new HttpResponse(url);
		HttpURLConnection con;
		URL _URL;
		Proxy proxy = null;
		try {
			_URL = new URL(url);
			if (proxys == null) {
				con = (HttpURLConnection) _URL.openConnection();
			} else {
				proxy = proxys.nextProxy();
				if (proxy == null) {
					con = (HttpURLConnection) _URL.openConnection();
				} else {
					logger.info("代理IP:" + proxy.address());
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

			con.setConnectTimeout(3000);
			con.setReadTimeout(10000);

			configConnection(con);
			InputStream is;
			response.setCode(con.getResponseCode());
			is = con.getInputStream();
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
		} catch (IOException e) {
			logger.info("链接超时");
			if (proxys != null) {
				proxys.remove(proxy);
				if (proxys.size() < 5) {
					((MyProxys) proxys).getProxy();
				}
			}
			throw e;
		} catch (IllegalArgumentException e) {
			logger.info("链接超时");
			if (proxys != null) {
				proxys.remove(proxy);
				if (proxys.size() < 5) {
					((MyProxys) proxys).getProxy();
				}
			}
			throw e;
		}
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

	public static void main(String[] args) throws Exception {
		MyHttpRequesterImpl requester = new MyHttpRequesterImpl();
		HttpResponse response = requester.getResponse("http://www.baidu.com");
		System.out.println(response.getCode());
	}
}
