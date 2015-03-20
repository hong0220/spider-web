package com.spider.crawler.search;

import java.net.URLEncoder;
import java.util.List;

import us.codecraft.xsoup.Xsoup;

import com.hibernate.dao.SearchDao;
import com.hibernate.model.Search;
import com.spider.utils.RegexUtil;
import com.spider.utils.StringUtil;
import com.webcollector.crawler.DeepCrawler;
import com.webcollector.model.Links;
import com.webcollector.model.Page;

/**
 * Xsoup包解析
 */
public class BaiDu3 extends DeepCrawler {
	public BaiDu3(String crawlPath) {
		super(crawlPath);
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		String html = page.getHtml();
		// System.out.println(html);
		if (StringUtil.isEmpty(html)) {
			System.out.println("没有得到网页内容");
			return null;
		}
		System.out.println("有网页内容");

		List<String> titles = Xsoup.select(page.getDoc(),
				"//*[@tpl='se_com_default']/h3[@class='t']/a/text()").list();
		List<String> links = Xsoup.select(page.getDoc(),
				"//*[@tpl='se_com_default']/h3[@class='t']/a/@href").list();
		List<String> times = Xsoup
				.select(page.getDoc(),
						"//*[@tpl='se_com_default']/div[@class='f13']/span[@class='g']/text() | //*[@tpl='se_com_default']/*/*/div[@class='f13']/span[@class='g']/text()")
				.list();
		List<String> contents = Xsoup
				.select(page.getDoc(),
						"//*[@tpl='se_com_default']/div[@class='c-abstract']/text() | //*[@tpl='se_com_default']/*/*/div[@class='c-abstract']/text()")
				.list();

		System.out.println(titles.size());
		System.out.println(times.size());
		System.out.println(contents.size());
		SearchDao sd = new SearchDao();
		for (int i = 0; i < titles.size(); ++i) {
			// Date date = null;
			// try {
			// date = DateTimeUtil.formatStr(
			// RegexUtil.parseTime(times.get(i)), "yyyy-MM-dd");
			// } catch (ParseException e) {
			// e.printStackTrace();
			// }
			String title = titles.get(i);
			String content = contents.get(i);
			String time = RegexUtil.parseTime(times.get(i));
			String http = page.getUrl();
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
		BaiDu3 crawler = new BaiDu3("log/bd3");
		for (int page = 0; page <= 10; page++) {
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
		crawler.setThreads(3);
		crawler.start(1);
	}
}
