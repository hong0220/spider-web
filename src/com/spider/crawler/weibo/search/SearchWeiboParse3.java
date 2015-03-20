package com.spider.crawler.weibo.search;

import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hibernate.dao.WeiboSearchDao;
import com.hibernate.model.WeiboSearch;
import com.spider.crawler.weibo.util.WeiboTimeUtil;

/**
 * 采用简单方法抽取关键字搜索微博内容
 */
public class SearchWeiboParse3 {
	public static void getSearchWeibo(Document doc, String keyword) {
		Elements weibos = doc.select("div.c");
		for (int i = 0; i < weibos.size(); ++i) {
			Element weibo = weibos.get(i);
			System.out.println("----------------------");
			System.out.println(weibo.text());
			String text = weibo.text();
			String content = null;
			String name = null;

			WeiboSearchDao wsd = new WeiboSearchDao();
			WeiboSearch ws = new WeiboSearch();

			if (!text.contains("赞")) {
				continue;
			}

			name = text.substring(0, text.indexOf(":"));
			if (text.contains("转发理由:")) {
				content = text.substring(
						text.indexOf("转发理由:") + "转发理由:".length(),
						text.lastIndexOf(" 赞")).trim();
			} else {
				content = text.substring(0, text.lastIndexOf(" 赞")).trim();
			}
			String source = text.substring(text.lastIndexOf("来自")).trim();
			String time = text.substring(
					text.lastIndexOf("收藏") + "收藏 ".length(),
					text.lastIndexOf(" 来自")).trim();
			Date date = WeiboTimeUtil.handleTime(time);
			System.out.println(content);
			System.out.println(name);
			System.out.println(source);
			System.out.println(date);
			ws.setContent(content);
			ws.setSource(source);
			ws.setTime(date);
			ws.setKeyWord(keyword);
			wsd.save(ws);
		}
	}
}
