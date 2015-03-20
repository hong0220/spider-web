package com.spider.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

/**
 * 测试网络通信
 */
public class NetUtil {

	// 登陆得到数据
	public static void getDataByLogin() {
		try {
			String loginUrl = "http://10.192.5.217:8080/General/control!control_login.do";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ip", InetAddress.getLocalHost()
					.getHostAddress()));
			String json = HttpClientUtil.requestPost(loginUrl, params);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void success() {
		try {
			String loginUrl = "http://10.192.5.217:8080/General/control!control_success.do";
			HttpClientUtil.requestPost(loginUrl, null);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fail() {

	}

	public static void main(String[] args) {
		success();
	}
}
