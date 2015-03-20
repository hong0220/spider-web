package com.webcollector.crawler.test;

import com.webcollector.crawler.DeepCrawler;
import com.webcollector.model.Links;
import com.webcollector.model.Page;
import com.webcollector.net.HttpRequesterImpl;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestWeiboCrawler extends DeepCrawler {

	public TestWeiboCrawler(String crawlPath) throws Exception {
		super(crawlPath);
		// 获取新浪微博的cookie，账号密码以明文形式传输，请使用小号
		// String cookie = WeiboCN.getSinaCookie("微博用户名", "微博密码");
		String cookie = "_T_WM=2a2b18b4968dc35ab20941232693b9c8;SUB=_2A255xZKzDeTxGeNK6VIW8SfJwz6IHXVbST77rDV6PUJbrdANLUzmkW1jVoB006MSp5feU-TN-PQyl6AzKQ..;gsid_CTandWM=4uPB50b11aNEDh1lvVDkfmKaX9o;M_WEIBOCN_PARAMS=rl%3D1;";
		HttpRequesterImpl myRequester = (HttpRequesterImpl) this
				.getHttpRequester();
		myRequester.setCookie(cookie);
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		// 抽取微博
		System.out.println("----------------------");
		Elements weibos = page.getDoc().select("div.c");
		for (Element weibo : weibos) {
			System.out.println(weibo.text());
		}
		System.out.println("----------------------");
		// 如果要爬取评论，这里可以抽取评论页面的URL，返回
		return null;
	}

	public static void main(String[] args) throws Exception {
		TestWeiboCrawler crawler = new TestWeiboCrawler("/home/hu/data/weibo");
		crawler.setThreads(1);
		// 对某人微博前5页进行爬取
		for (int i = 0; i < 1; i++) {
			crawler.addSeed("http://weibo.cn/zhouhongyi?vt=4&page=" + i);
		}
		crawler.start(1);
	}
}