package com.spider.crawler.search;

import java.net.URLEncoder;
import java.util.List;

import us.codecraft.xsoup.Xsoup;
import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

import com.hibernate.dao.SearchDao;
import com.hibernate.model.Search;
import com.spider.utils.RegexUtil;
import com.spider.utils.StringUtil;

/**
 * Xsoup包解析
 */
public class SoGou2 extends DeepCrawler {
	public SoGou2(String crawlPath) {
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

		List<String> titles = Xsoup.select(page.getDoc(),
				"//*[@id*=rb]/h3/text()/a/text()").list();
		List<String> links = Xsoup.select(page.getDoc(),
				"//*[@id*=rb]/h3/text()/a/@href").list();
		List<String> times = Xsoup.select(page.getDoc(),
				"//*[@id*=cacheresult_info_]/text()").list();
		List<String> contents = Xsoup.select(page.getDoc(),
				"//*[@id*=rb]/div[@class='ft']/text()").list();

		// System.out.println(titles.size());
		// System.out.println(times.size());
		// System.out.println(contents.size());
		SearchDao sd = new SearchDao();
		for (int i = 0; i < titles.size(); ++i) {
			String title = titles.get(i);
			// Date date = null;
			// try {
			// date = DateTimeUtil.formatStr(
			// RegexUtil.parseTime(times.get(i)), "yyyy-MM-dd");
			// } catch (ParseException e) {
			// e.printStackTrace();
			// }
			String content = contents.get(i);
			System.out.println(times.get(i));
			String time = RegexUtil.parseTime(times.get(i));
			String http = page.getUrl();
			if (!StringUtil.isEmpty(title) || !StringUtil.isEmpty(content)
					|| !StringUtil.isEmpty(time) || !StringUtil.isEmpty(http)) {
				Search search = new Search(title, content, time, http, "搜狗");
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
		SoGou2 crawler = new SoGou2("log/sg2");
		for (int page = 2; page <= 10; page++) {
			crawler.addSeed("http://www.sogou.com/web?query="
					+ URLEncoder.encode("编程") + "&page=" + page);
		}
		// 设置代理
		// Proxys proxys = new Proxys();
		// proxys.add("202.101.96.154", 8888);
		// crawler.setProxys(proxys);
		// 遍历中第一层爬取搜狗的搜索结果页面， 第二层爬取搜索结果对应的页面, 所以这里要将层数设置为2
		crawler.start(1);
	}
}
