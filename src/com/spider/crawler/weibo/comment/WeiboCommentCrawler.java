package com.spider.crawler.weibo.comment;

import com.webcollector.crawler.DeepCrawler;
import com.webcollector.model.Links;
import com.webcollector.model.Page;
import com.webcollector.net.HttpRequesterImpl;
import com.webcollector.proxy.Proxys;

/**
 * 微博评论解析
 */
public class WeiboCommentCrawler extends DeepCrawler {
	private String userid;

	public WeiboCommentCrawler(String crawlPath) throws Exception {
		super(crawlPath);
		// 获取新浪微博的cookie，账号密码以明文形式传输，请使用小号
		// String cookie = WeiboCN.getSinaCookie("微博用户名", "微博密码");
		String cookie = "_T_WM=404c75f89f22dbc8280ab834d15ad092;SUB=_2A254CSDmDeTxGeVH41QR9S7JyjyIHXVb8kCurDV6PUJbrdANLXDlkW112o8--wOCwti8bq-_tJEXhTzCJA..;gsid_CTandWM=4uAt50b11Axn27qss1qN4gIX88e;";
		HttpRequesterImpl myRequester = (HttpRequesterImpl) this
				.getHttpRequester();
		myRequester.setCookie(cookie);
	}

	public WeiboCommentCrawler(String ip, int port, String crawlPath)
			throws Exception {
		super(crawlPath);
		// 获取新浪微博的cookie，账号密码以明文形式传输，请使用小号
		// String cookie = WeiboCN.getSinaCookie("微博用户名", "微博密码");
		String cookie = "_T_WM=404c75f89f22dbc8280ab834d15ad092;SUB=_2A254CSDmDeTxGeVH41QR9S7JyjyIHXVb8kCurDV6PUJbrdANLXDlkW112o8--wOCwti8bq-_tJEXhTzCJA..;gsid_CTandWM=4uAt50b11Axn27qss1qN4gIX88e;";

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
		WeiboCommentParse.getComments(page.getDoc(), page.getUrl());
		// 休息两秒
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
		WeiboCommentCrawler wcc = new WeiboCommentCrawler("log/wcc");
		wcc.setUserid("1640016932");
		wcc.setResumable(true);
		for (int i = 1; i <= 100; i++) {
			String url = "http://weibo.cn/comment/C64DjjIIC?uid="
					+ wcc.getUserid() + "&rl=0&page=" + i;
			wcc.addSeed(url);
		}
		wcc.setThreads(2);
		wcc.start(1);
		// String spam =
		// "http://weibo.cn/spam/?cid=3787032956942296&fuid=2669195451&type=2&rl=2&vt=4";
		// spam = spam.substring(spam.indexOf("cid=") + "cid=".length(),
		// spam.indexOf("&"));
		// System.out.println(spam);
	}
}
