package com.spider.crawler.weibo.comment;

import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hibernate.dao.WeiboCommentDao;
import com.hibernate.model.WeiboComment;
import com.spider.crawler.weibo.util.WeiboTimeUtil;
import com.spider.utils.RegexUtil;

/**
 * 微博评论解析
 */
public class WeiboCommentParse {

	// public static void main(String[] args) {
	// getComments(
	// "2929571482",
	// "http://weibo.cn/comment/Bn8t846oN?uid=2929571482&rl=1&vt=4#cmtfrm",
	// 3);
	// }
	//
	// public static void getComments(String weiboid, String url, int pageSize)
	// {
	// // http://weibo.cn/comment/BnwHZfmrS?rl=1&vt=4&page=
	// // http://weibo.cn/comment/Bn8t846oN?uid=2929571482&rl=1&vt=4#cmtfrm
	// // http://weibo.cn/comment/Bn8t846oN?uid=2929571482&rl=1&vt=4&page=2
	//
	// url = url.replace("cmtfrm", "page=");
	//
	// for (int i = 1; i <= pageSize; ++i) {
	// getComments(weiboid, url + i);
	// }
	// }
	//
	// public static void getComments(String weiboid, String url) {
	// WeiboCommentDao wcd = new WeiboCommentDao();
	// WeiboComment weiboComment = new WeiboComment();
	//
	// String html = null;
	// try {
	// html = HttpClientUtil.getHtmlByPost(url);
	// } catch (Exception e) {
	// System.out.println("网络连接错误:" + url);
	// e.printStackTrace();
	// }
	// Document doc = Jsoup.parse(html, url);
	// try {
	// Thread.sleep(100);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// Elements divs = doc.select("div.c");
	//
	// for (int i = 2; i < divs.size(); ++i) {
	// System.out.println("------------");
	// Element div = divs.get(i);
	// // System.out.println(div);
	// Element link = div.select("a[href]").first();
	// if (link != null) {
	// // System.out.println(link.attr("abs:href"));
	// System.out.println("名称" + div.select("a[href]").text());
	// }
	// String content = div.getElementsByClass("ctt").text().trim();
	// if (content != null && !"".equals(content)) {
	// content = content.trim();
	// // content =
	// // content.split("赞")[0].trim().split("//@")[0].trim();
	// // System.out.println("内容 " + content);
	// weiboComment.setContent(content);
	// }
	// String time = div.getElementsByClass("ct").text().trim();
	// if (time != null && !"".equals(time)) {
	// Date date = WeiboTimeUtil.handleTime(time);
	// weiboComment.setWeiboid(weiboid);
	// weiboComment.setTime(date);
	// wcd.save(weiboComment);
	// }
	// System.out.println("------------");
	// }
	//
	// try {
	// Thread.sleep(2000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	//
	// // 下一页
	// // Elements divs2 = doc.select("div.pa");
	// // // System.out.println(divs2);
	// // Elements link_elements = divs2.select("a[href]");
	// // // System.out.println(link_elements);
	// // for (Element link : link_elements) {
	// // // System.out.println(link);
	// // System.out.println(link.attr("abs:href"));
	// // // String nextPage = link.attr("href");
	// // // nextPage = "http://weibo.cn" + nextPage;
	// // // System.out.println(nextPage);
	// // }
	// }

	public static void getComments(Document doc, String url) {
		WeiboCommentDao wcd = new WeiboCommentDao();
		WeiboComment weiboComment = new WeiboComment();
		Elements divs = doc.select("div.c");
		// 1或者2
		for (int i = 2; i < divs.size(); ++i) {
			System.out.println("------------");
			Element div = divs.get(i);
			System.out.println(div);
			Element link = div.select("a[href]").first();
			if (link != null) {
				// System.out.println(link.attr("abs:href"));
				System.out.println("名称:" + link.text());
				weiboComment.setUserId(link.text());
			}
			Elements elements = div.select("a[href]");
			for (Element element : elements) {
				if ("举报".equals(element.text())) {
					String spam = element.attr("abs:href");
					// http: //
					// weibo.cn/spam/?cid=3787032956942296&fuid=2669195451&type=2&rl=2&vt=4
					spam = spam.substring(
							spam.indexOf("cid=") + "cid=".length(),
							spam.indexOf("&"));
					weiboComment.setCid(spam);
				}
			}
			String content = div.getElementsByClass("ctt").text().trim();

			// String content = div.select("span.ctt").first().text();
			System.out.println(content);
			if (content != null && !"".equals(content)) {
				content = content.trim();
				// content = content.split("//@")[0].trim();
				if (content.startsWith("回复@")) {
					content = RegexUtil.getData("^回复@[\\S]*:(.*)", content);
				} else if (content.contains("//@")) {
					// content = RegexUtil.getData("^//@[\\S]*:(.*?)//@",
					// content);
					content = RegexUtil.getData("^(.*?)//@", content);
				}

				System.out.println("内容 " + content);
				weiboComment.setContent(RegexUtil.strFilter(content));
			}
			String time = div.getElementsByClass("ct").text().trim();
			if (time != null && !"".equals(time)) {
				Date date = WeiboTimeUtil.handleTime(time);
				weiboComment.setTime(date);

				weiboComment.setWeiboid(url.substring(url.indexOf("comment/")
						+ "comment/".length(), url.indexOf("?")));

				String source = time.substring(time.indexOf("来自")).trim();
				System.out.println(source);
				weiboComment.setSource(RegexUtil.strFilter(source));

				weiboComment.setHttp(url);
				// if (!wcd.isExits(weiboComment)) {
				wcd.save(weiboComment);
				// }
			}
			System.out.println("------------");
		}
	}
}
