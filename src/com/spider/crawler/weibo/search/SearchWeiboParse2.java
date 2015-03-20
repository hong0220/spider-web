//package com.spider.crawler.weibo.search;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import com.hibernate.dao.WeiboSearchDao;
//import com.hibernate.model.WeiboSearch;
//import com.spider.utils.DateTimeUtil;
//import com.spider.utils.HttpClientUtil;
//import com.webcollector.net.HttpResponse;
//import com.webcollector.util.CharsetDetector;
//
///**
// * 关键字搜索微博复杂抽取
// * 
// * 已经放弃
// */
//public class SearchWeiboParse2 {
//	public static void main(String[] args) {
//		// getWeibo("2929571482", 10);
//		String keyWord = "王思聪丑";
//		getSearchWeibo(keyWord, 1);
//	}
//
//	public static void getSearchWeibo(String keyWord, int pageSize) {
//		for (int i = 1; i <= pageSize; ++i) {
//			getSearchWeibo("http://weibo.cn/search/mblog/?keyword=" + keyWord
//					+ "&vt=4&page=" + i);
//		}
//	}
//
//	public static void getSearchWeibo(String url) {
//		WeiboSearchDao sw = new WeiboSearchDao();
//		WeiboSearch searchWeibo = new WeiboSearch();
//
//		// String url = "http://weibo.cn/search/mblog/?keyword=" + keyWord
//		// + "&vt=4";
//		System.out.println(url);
//
//		String html = HttpClientUtil.getHtmlByPost(url);
//
//		// HttpRequesterImpl requester = new HttpRequesterImpl();
//		// HttpResponse response = null;
//		// try {
//		// response = requester.getResponse(url);
//		// Proxys proxys = new Proxys();
//		// proxys.add("223.64.35.179", 8123);
//		// requester.setProxys(proxys);
//		// } catch (Exception e1) {
//		// e1.printStackTrace();
//		// }
//		// String html = getHtml(response);
//
//		Document doc = Jsoup.parse(html,
//				"http://weibo.cn/search/?tf=5_012&vt=4");
//
//		Elements divs = doc.select("div.c");
//
//		for (Element div : divs) {
//			// System.out.println(div.html());
//
//			// parent > child：直接为父元素后代的子元素，例如: div.content > pf查找p元素，body >
//			// * 查找body元素的直系子元素
//			Elements divs2 = div.select("div > div");
//
//			// 错误写法Elements divs2 = div.select("div");会包括自身div
//			for (int j = 0; j < divs2.size(); ++j) {
//				Element div2 = divs2.get(j);
//				// System.out.println(divs2.size());
//				// System.out.println(divs2.html());
//
//				if (divs2.size() == 1 || divs2.size() == 2) { // 原创微博
//					System.out.println("------------");
//					String content = div2.getElementsByClass("ctt").text()
//							.trim();
//					if (content != null && !"".equals(content)) {
//						System.out.println("内容 " + content);
//						content = RexUtil.StringFilter(content);
//						searchWeibo.setContent(content);
//					}
//					String time = div2.getElementsByClass("ct").text().trim();
//					if (time != null && !"".equals(time)) {
//						if (time.contains("今天")) {
//							// System.out.println(time);
//							// System.out.println(time.split(" ")[1].substring(0,
//							// 5));
//							time = DateTimeUtil.formatDate(new Date(),
//									"yyyy-MM-dd")
//									+ " "
//									+ time.split(" ")[1].trim().substring(0, 5)
//									+ ":00";
//						} else if (time.contains("分钟前")) {
//							int min = Integer.valueOf(time.substring(0,
//									time.indexOf("分钟前")));
//							time = DateTimeUtil.formatDate(new Date(),
//									"yyyy-MM-dd")
//									+ " "
//									+ DateTimeUtil.getPreTimeStr(min, 2,
//											new Date());
//						} else if (time.contains("月")) {
//							time = DateTimeUtil.getYear()
//									+ "-"
//									+ DateTimeUtil
//											.formatDate(DateTimeUtil.formatStr(
//													time, "MM月dd日"), "MM-dd")
//									+ " 00:00:00";
//						} else {
//							time = time.split(" ")[0].trim() + " "
//									+ time.split(" ")[1].trim().substring(0, 5);
//						}
//						System.out.println(time);
//						// searchWeibo.setUserid(userid);
//						searchWeibo.setTime(time);
//						sw.save(searchWeibo);
//					}
//					System.out.println("------------");
//				} else { // 转载微博加评论
//							// System.out.println(div2);
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
//							content = RexUtil.StringFilter(content);
//							searchWeibo.setContent(content);
//						}
//						String time = div2.getElementsByClass("ct").text()
//								.trim();
//						if (time != null && !"".equals(time)) {
//							if (time.contains("今天")) {
//								// System.out.println(time);
//								// System.out.println(time.split(" ")[1].substring(0,
//								// 5));
//								time = DateTimeUtil.formatDate(new Date(),
//										"yyyy-MM-dd")
//										+ " "
//										+ time.split(" ")[1].trim().substring(
//												0, 5) + ":00";
//							} else if (time.contains("分钟前")) {
//								int min = Integer.valueOf(time.substring(0,
//										time.indexOf("分钟前")));
//								time = DateTimeUtil.formatDate(new Date(),
//										"yyyy-MM-dd")
//										+ " "
//										+ DateTimeUtil.getPreTimeStr(min, 2,
//												new Date());
//							} else if (time.contains("月")) {
//								time = DateTimeUtil.getYear()
//										+ "-"
//										+ DateTimeUtil.formatDate(DateTimeUtil
//												.formatStr(time, "MM月dd日"),
//												"MM-dd") + " 00:00:00";
//							} else {
//								time = time.split(" ")[0].trim()
//										+ " "
//										+ time.split(" ")[1].trim().substring(
//												0, 5);
//							}
//							System.out.println(time);
//							// weibo.setUserid(userid);
//							searchWeibo.setTime(time);
//							sw.save(searchWeibo);
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
//				}
//			}
//		}
//
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//
//	// 返回网页的源码字符串
//	public static String getHtml(HttpResponse response) {
//		String html = null;
//		if (getContent(response) == null) {
//			return html;
//		}
//		String charset = CharsetDetector.guessEncoding(getContent(response));
//		try {
//			html = new String(getContent(response), charset);
//			return html;
//		} catch (UnsupportedEncodingException e) {
//			return html;
//		}
//	}
//
//	// 返回网页/文件的内容
//	public static byte[] getContent(HttpResponse response) {
//		if (response == null)
//			return null;
//		return response.getContent();
//	}
// }
