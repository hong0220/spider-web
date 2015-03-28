package com.spider.crawler.news;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 * 主要用来测试css selector语法
 */
public class TestCrawler extends DeepCrawler {
	public static final Logger LOG = LoggerFactory.getLogger(TestCrawler.class);

	public TestCrawler(String crawlPath) {
		super(crawlPath);
	}

	public Links visitAndGetNextLinks(Page page) {
		Document doc = page.getDoc();
		String str = doc.select("#page > h1").first().text();
		System.out.println(str);
		return null;
	}

	public static void main(String[] args) throws Exception {
		TestCrawler t = new TestCrawler("log/t");
		// t.addForcedSeed("http://news.sina.com.cn/c/2015-03-14/185031607056.shtml");
		t.addForcedSeed("http://majihua.baijia.baidu.com/article/49517");
		t.setThreads(1);
		t.start(1);
	}
}
