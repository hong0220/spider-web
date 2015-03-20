package com.spider.utils;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	@SuppressWarnings("deprecation")
	public static String getHtmlByPost(String url) {
		HttpPost httpPost = new HttpPost(url);
		// 自己填写cookie
		httpPost.setHeader("Cookie", "");
		httpPost.setHeader("User-Agent",
				"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)");
		DefaultHttpClient httpclient = getHttpClient();
		String html = null;
		try {
			html = httpclient.execute(httpPost, responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return html;
	}

	// 设置代理
	@SuppressWarnings("deprecation")
	public static DefaultHttpClient getHttpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// HttpHost proxy = new HttpHost("223.64.35.179", 8123, null);
		// httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY,
		// proxy);
		return httpClient;
	}

	// 使用ResponseHandler接口处理响应
	// HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
	private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		// 自定义响应处理
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return new String(EntityUtils.toByteArray(entity), "utf-8");
			} else {
				return null;
			}
		}
	};

	public static String requestGet(String urlWithParams) throws Exception {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		// HttpGet httpget = new HttpGet("http://www.baidu.com/");
		HttpGet httpGet = new HttpGet(urlWithParams);
		// 配置请求的超时设置
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(50).setConnectTimeout(50)
				.setSocketTimeout(50).build();
		// 自己填写cookie
		httpGet.setHeader("Cookie", "");
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		System.out.println("StatusCode -> "
				+ response.getStatusLine().getStatusCode());
		HttpEntity entity = response.getEntity();
		String jsonStr = EntityUtils.toString(entity);// , "utf-8");
		System.out.println(jsonStr);
		httpGet.releaseConnection();
		return jsonStr;
	}

	public static String requestPost(String url, List<NameValuePair> params)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httppost = new HttpPost(url);
		if (params != null) {
			httppost.setEntity(new UrlEncodedFormEntity(params));
		}
		CloseableHttpResponse response = httpclient.execute(httppost);
		System.out.println(response.toString());
		HttpEntity entity = response.getEntity();
		String jsonStr = EntityUtils.toString(entity, "utf-8");
		System.out.println(jsonStr);
		httppost.releaseConnection();
		return jsonStr;
	}
}
