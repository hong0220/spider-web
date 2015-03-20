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
public class SoSo extends DeepCrawler {
	public SoSo(String crawlPath) {
		super(crawlPath);
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		Document doc = page.getDoc();
		// System.out.println(doc);
		Elements titles = doc.select("div[id^=rb] > h3");
		Elements contents = doc.select("div[id^=rb] > div.ft");
		Elements times = doc.select("div[id^=rb] > div.fb");
		SearchDao sd = new SearchDao();
		for (int i = 0; i < titles.size(); ++i) {
			Element titleElement = titles.get(i);
			Element contentElement = contents.get(i);
			Element timeElement = times.get(i);
			// System.out.println(titleElement.select("a").first()
			// .attr("abs:href"));
			// System.out.println(titleElement.text());
			// System.out.println(contentElement.text());
			// System.out.println(timeElement.text());

			String title = titleElement.text().trim();
			String content = contentElement.text().trim();
			// Date date = null;
			// try {
			// date = DateTimeUtil.formatStr(
			// RegexUtil.parseTime(timeElement.text().trim()),
			// "yyyy-MM-dd");
			// } catch (ParseException e) {
			// LOG.error("解析时间错误的网址:" + page.getUrl() + "\n" + "错误时间:"
			// + timeElement.text().trim());
			// e.printStackTrace();
			// }

			System.out.println(timeElement.text().trim());
			String time = RegexUtil.parseTime(timeElement.text().trim());
			String http = titleElement.select("a").first().attr("abs:href")
					.trim();
			if (!StringUtil.isEmpty(title) || !StringUtil.isEmpty(content)
					|| !StringUtil.isEmpty(time) || !StringUtil.isEmpty(http)) {
				Search search = new Search(title, content, time, http, "搜搜");
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
		SoSo crawler = new SoSo("log/ss");
		for (int page = 1; page <= 20; page++) {
			crawler.addSeed("http://www.soso.com/q?query="
					+ URLEncoder.encode("编程") + "&pg=" + page);
		}
		// Proxys proxys = new Proxys();
		// proxys.add("202.101.96.154", 8888);
		// crawler.setProxys(proxys);
		crawler.setThreads(3);
		crawler.start(1);
	}
}
