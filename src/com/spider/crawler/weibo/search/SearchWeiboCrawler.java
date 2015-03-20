package com.spider.crawler.weibo.search;

import com.spider.crawler.weibo.util.WeiboCN;
import com.webcollector.crawler.DeepCrawler;
import com.webcollector.model.Links;
import com.webcollector.model.Page;
import com.webcollector.net.HttpRequesterImpl;

/**
 * 关键字搜索微博
 */
public class SearchWeiboCrawler extends DeepCrawler {
	private String keyword;

	public SearchWeiboCrawler(String crawlPath) throws Exception {
		super(crawlPath);
		// 获取新浪微博的cookie，账号密码以明文形式传输，请使用小号
		String cookie = WeiboCN.getSinaCookie("微博用户名", "微博密码");

		HttpRequesterImpl myRequester = (HttpRequesterImpl) this
				.getHttpRequester();
		myRequester.setCookie(cookie);
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		// 抽取微博
		SearchWeiboParse3.getSearchWeibo(page.getDoc(), keyword);
		// 如果要爬取评论，这里可以抽取评论页面的URL，返回
		return null;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public static void main(String[] args) throws Exception {
		SearchWeiboCrawler swc = new SearchWeiboCrawler("log/swc");
		swc.setThreads(1);
		String keyword = "百度";
		swc.setKeyword(keyword);
		swc.setThreads(1);
		for (int i = 1; i < 5; i++) {
			swc.addSeed("http://weibo.cn/search/mblog/?keyword=" + keyword
					+ "&vt=4&page=" + i);
		}
		swc.start(1);
	}
}
