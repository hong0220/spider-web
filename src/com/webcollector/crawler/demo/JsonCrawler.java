package com.webcollector.crawler.demo;

import java.net.HttpURLConnection;
import java.net.ProtocolException;

import org.json.JSONObject;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;

/**
 * 爬取JSON的例子 例如我们爬取http://www.brieftools.info/proxy/test/test1.json
 * 和http://www.brieftools.info/proxy/test/test2.json
 * 
 * 很多JSON爬取必须要设置Cookie、User-Agent 有时候还需要使用POST方法
 */
public class JsonCrawler extends DeepCrawler {

	public JsonCrawler(String crawlPath) {
		super(crawlPath);
		HttpRequesterImpl myRequester = new HttpRequesterImpl() {

			// Override这个方法，可以用来修改http请求(HttpURLConnection)
			@Override
			public void configConnection(HttpURLConnection con) {
				try {
					con.setRequestMethod("POST");
				} catch (ProtocolException e) {
					e.printStackTrace();
				}
				// 添加http头
				con.addRequestProperty("xxx", "xxxxxxx");
			}
		};
		myRequester.setCookie("你的cookie");
		this.setHttpRequester(myRequester);
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		String jsonStr = page.getHtml();
		JSONObject json = new JSONObject(jsonStr);
		String ip = json.getString("ip");
		int port = json.getInt("port");
		System.out.println("原JSON:" + jsonStr.trim() + "\n" + "JSON解析信息 ip="
				+ ip + "  port=" + port);
		return null;
	}

	public static void main(String[] args) throws Exception {
		JsonCrawler crawler = new JsonCrawler("/wb");
		crawler.addSeed("http://www.brieftools.info/proxy/test/test1.json");
		crawler.addSeed("http://www.brieftools.info/proxy/test/test2.json");
		crawler.start(1);
	}
}
