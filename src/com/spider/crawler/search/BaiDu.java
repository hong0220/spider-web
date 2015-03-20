package com.spider.crawler.search;

import java.net.URLEncoder;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hibernate.dao.SearchDao;
import com.hibernate.model.Search;
import com.spider.utils.RegexUtil;
import com.spider.utils.StringUtil;
import com.webcollector.crawler.DeepCrawler;
import com.webcollector.model.Links;
import com.webcollector.model.Page;

/**
 * jsoup包，使用css selector解析
 */
public class BaiDu extends DeepCrawler {

	public BaiDu(String crawlPath) {
		super(crawlPath);
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		Document doc = page.getDoc();
		// System.out.println(doc);
		Elements titles = doc.select("div[tpl=se_com_default]  h3");
		Elements contents = doc
				.select("div[tpl=se_com_default]  div.c-abstract");
		// <div class="bbs f13"> 重复标签
		Elements times = doc.select("div[tpl=se_com_default]  div[class=f13]");
		SearchDao sd = new SearchDao();
		for (int i = 0; i < titles.size(); ++i) {
			Element titleElement = titles.get(i);
			Element contentElement = contents.get(i);
			Element timeElement = times.get(i);
			// System.out.println(titleElement.select("a").first()
			// .attr("abs:href"));

			String title = titleElement.text().trim();
			String content = contentElement.text().trim();

			// <span
			// class="g">m4774411wang.iteye.com/&nbsp;2013-06-04&nbsp;</span>只能使用html()提取出时间,text()提取不出时间
			// String time = RegexUtil.parseTime(timeElement.html().trim());
			String time = RegexUtil.parseTime(timeElement.text().trim());
			// Date time = null;
			// try {
			// time = DateTimeUtil.formatStr(
			// RegexUtil.parseTime(timeElement.html().trim()),
			// "yyyy-MM-dd");
			// } catch (ParseException e) {
			// LOG.error("解析时间错误的网址:" + page.getUrl() + "\n" + "错误时间:"
			// + timeElement.html().trim());
			// e.printStackTrace();
			// }

			String http = titleElement.select("a").first().attr("abs:href")
					.trim();
			if (!StringUtil.isEmpty(title) || !StringUtil.isEmpty(content)
					|| !StringUtil.isEmpty(time) || !StringUtil.isEmpty(http)) {
				Search search = new Search(title, content, time, http, "百度");
				System.out.println(search);
				sd.save(search);
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		BaiDu crawler = new BaiDu("log/bd");
		for (int page = 1; page <= 15; page++) {
			if (page == 0) {
				crawler.addSeed("http://www.baidu.com/s?wd="
						+ URLEncoder.encode("编程入门"));
			} else {
				crawler.addSeed("http://www.baidu.com/s?wd="
						+ URLEncoder.encode("编程入门") + "&pn=" + (page * 10));
			}
		}
		// Proxys proxys = new Proxys();
		// proxys.add("202.101.96.154", 8888);
		// crawler.setProxys(proxys);
		crawler.setThreads(1);
		crawler.start(1);
	}
}
