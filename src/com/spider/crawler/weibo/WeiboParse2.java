package com.spider.crawler.weibo;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.hfut.dmic.webcollector.model.Page;

import com.hibernate.dao.WeiboDao;
import com.hibernate.model.Weibo;
import com.spider.crawler.weibo.util.WeiboTimeUtil;
import com.spider.utils.RegexUtil;

public class WeiboParse2 {
	public static final Logger LOG = LoggerFactory.getLogger(WeiboParse2.class);

	public static void getWeibo(Page page, String userid) {
		WeiboDao wd = new WeiboDao();
		try {
			Elements weibos = page.getDoc().select("div.c");
			for (int i = 0; i < weibos.size(); ++i) {
				Weibo w = new Weibo();
				String content = null;
				String source = null;
				String time = null;
				Date date = null;
				Element weibo = weibos.get(i);
				// System.out.println("----------------------");
				String text = weibo.text();
				System.out.println(text);
				try {
					if (!text.contains("赞")) {
						continue;
					}

					// 修改lastIndexOf
					//  赞 编码问题
					// 定位更准确
					if (text.contains("转发理由:")) {
						content = text.substring(
								text.lastIndexOf("转发理由:") + "转发理由:".length(),
								text.lastIndexOf(" 赞")).trim();
					} else {
						content = text.substring(0, text.lastIndexOf(" 赞"))
								.trim();
					}

					source = text.substring(text.lastIndexOf("来自")).trim();
					time = text.substring(
							text.lastIndexOf("收藏 ") + "收藏 ".length(),
							text.lastIndexOf(" 来自")).trim();
					date = WeiboTimeUtil.handleTime(time);
					System.out.println(text);
					System.out.println(content);
					System.out.println(source);
					System.out.println(time);
					System.out.println(date);
				} catch (Exception e) {
					LOG.error("解析数据错误,网址:" + page.getUrl());
					LOG.error(text);
					LOG.error(content);
					LOG.error(source);
					LOG.error(time);
					LOG.error(date.toString());
				}
				w.setContent(RegexUtil.strFilter(content));
				w.setCreateTime(date);
				w.setUserId(userid);
				w.setSource(source);
				w.setPage(Integer.valueOf(page.getUrl().substring(
						page.getUrl().lastIndexOf("=") + "=".length())));
				// 月日
				// if (!wd.isExits(w)) {
				System.out.println(w);
				wd.save(w);
				// }
			}
		} catch (Exception e) {
			LOG.error("网址错误:" + page.getUrl());
		}
	}
}
