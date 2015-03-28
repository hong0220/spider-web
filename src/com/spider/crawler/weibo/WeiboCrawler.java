package com.spider.crawler.weibo;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;
import cn.edu.hfut.dmic.webcollector.net.Proxys;

import com.spider.crawler.weibo.util.WeiboCN;

public class WeiboCrawler extends DeepCrawler {
	private String userid;

	public WeiboCrawler(String crawlPath) throws Exception {
		super(crawlPath);
		// 获取新浪微博的cookie，账号密码以明文形式传输，请使用小号
		String cookie = WeiboCN.getSinaCookie("微博用户名", "微博密码");

		HttpRequesterImpl myRequester = (HttpRequesterImpl) this
				.getHttpRequester();
		myRequester.setCookie(cookie);
	}

	public WeiboCrawler(String ip, int port, String crawlPath) throws Exception {
		super(crawlPath);
		// 获取新浪微博的cookie，账号密码以明文形式传输，请使用小号
		String cookie = WeiboCN.getSinaCookie("微博用户名", "微博密码");

		HttpRequesterImpl myRequester = (HttpRequesterImpl) this
				.getHttpRequester();
		System.out.println(cookie);
		myRequester.setCookie(cookie);

		// 设置代理
		Proxys proxys = new Proxys();
		proxys.add(ip, port);
		setProxys(proxys);
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		WeiboParse2.getWeibo(page, getUserid());
		// 如果要爬取评论，这里可以抽取评论页面的URL，返回
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public static void main(String[] args) throws Exception {
		WeiboCrawler wc = new WeiboCrawler("log/wc");
		// wc.setResumable(true);
		wc.setUserid("2199178482");

		for (int i = 1; i <= 11; i++) {
			String url = "http://weibo.cn/" + wc.getUserid() + "?vt=4&page="
					+ i;
			System.out.println(url);
			wc.addSeed(url);
		}
		wc.setThreads(1);
		wc.start(1);
	}
}
