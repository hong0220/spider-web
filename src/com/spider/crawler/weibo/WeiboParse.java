//package com.spider.crawler.weibo;
//
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import com.hibernate.dao.WeiboDao;
//import com.hibernate.model.Weibo;
//import com.spider.crawler.weibo.util.WeiboTimeUtil;
//
///**
// * 个人微博复杂抽取
// * 
// * 已经放弃
// */
//public class WeiboParse {
//
//	public static void getWeibo(Document doc, String userid) {
//		WeiboDao wd = new WeiboDao();
//		Elements divs = doc.select("div.c");
//
//		for (Element div : divs) {
//			// System.out.println(div.html());
//
//			// parent > child：直接为父元素后代的子元素，例如: div.content > pf查找p元素，body >
//			// * 查找body元素的直系子元素
//			Elements divs2 = div.select("div > div");
//			Weibo weibo = new Weibo();
//
//			// 错误写法Elements divs2 = div.select("div");会包括自身div
//			for (int j = 0; j < divs2.size(); ++j) {
//				Element div2 = divs2.get(j);
//				// System.out.println(divs2.size());
//				// System.out.println(divs2.html());
//
//				if (divs2.size() == 1) { // 原创微博只有文字
//					System.out.println("------------");
//					String content = div2.getElementsByClass("ctt").text()
//							.trim();
//					if (content != null && !"".equals(content)) {
//						System.out.println("内容 " + content);
//						weibo.setContent(content);
//					}
//					String time = div2.getElementsByClass("ct").text().trim();
//					if (time != null && !"".equals(time)) {
//						weibo.setUserid(userid);
//						weibo.setTime(WeiboTimeUtil.handleTime(time));
//						wd.save(weibo);
//					}
//					System.out.println("------------");
//				} else if (divs2.size() == 2) {
//					// 转发微博已经被删除
//					// 或者转发微博内容只有文字没有图片
//					// 或者原创微博有图片和文字
//					if (j == 0) {// 原创微博有图片和文字
//						System.out.println("------------");
//						String content = div2.getElementsByClass("ctt").text()
//								.trim();
//						if (content != null && !"".equals(content)) {
//							System.out.println("内容 " + content);
//							weibo.setContent(content);
//						}
//					} else if (j == 1) {// 转发微博已经被删除或者转发微博内容只有文字没有图片
//						System.out.println("------------");
//						if (weibo.getContent() != null) {// 原创微博有图片和文字
//							String time = div2.getElementsByClass("ct").text()
//									.trim();
//							if (time != null && !"".equals(time)) {
//								weibo.setUserid(userid);
//								weibo.setTime(WeiboTimeUtil.handleTime(time));
//								wd.save(weibo);
//							}
//						} else {
//							String content = div2.text().trim();
//							if (content != null && !"".equals(content)) {
//								System.out.println("内容 " + content);
//								content = content.split("赞")[0].trim();
//								weibo.setContent(content);
//							}
//							String time = div2.getElementsByClass("ct").text()
//									.trim();
//							if (time != null && !"".equals(time)) {
//								weibo.setUserid(userid);
//								weibo.setTime(WeiboTimeUtil.handleTime(time));
//								wd.save(weibo);
//							}
//						}
//					}
//					System.out.println("------------");
//				} else { // 转载微博加评论
//					// System.out.println(div2);
//					if (j == 0) { // 被转发的内容
//						String content = div2.getElementsByClass("ctt").text()
//								.trim();
//						if (content != null && !"".equals(content)) {
//							System.out.println("被转发的内容 " + content);
//						}
//					} else if (j == 1) { // 被转发的访问量
//					} else if (j == 2) { // 转发的评论
//						String content = div2.text().trim();
//						if (content != null && !"".equals(content)) {
//							System.out.println("转发的评论 " + content);
//							content = content.split("赞")[0].trim();
//							weibo.setContent(content);
//						}
//						String time = div2.getElementsByClass("ct").text()
//								.trim();
//						if (time != null && !"".equals(time)) {
//							weibo.setUserid(userid);
//							weibo.setTime(WeiboTimeUtil.handleTime(time));
//							wd.save(weibo);
//						}
//						Elements link_elements = div2.select("a[href]");
//						for (Element link : link_elements) {
//							String text = link.text();
//							String href = link.attr("abs:href");
//							if (href.startsWith("http://weibo.cn/attitude/")) {
//								System.out.println(href);
//								System.out.println("赞成 " + text);
//							} else if (href
//									.startsWith("http://weibo.cn/repost/")) {
//								System.out.println(href);
//								System.out.println("转发 " + text);
//							} else if (href
//									.startsWith("http://weibo.cn/comment/")) {
//								System.out.println(text);
//								System.out.println("评论 " + href);
//							}
//						}
//					}
//					System.out.println("------------");
//				}
//			}
//		}
//	}
// }
