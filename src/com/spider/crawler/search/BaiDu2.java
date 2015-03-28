package com.spider.crawler.search;

import java.net.URLEncoder;

import org.htmlcleaner.TagNode;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

import com.hibernate.dao.SearchDao;
import com.hibernate.model.Search;
import com.spider.parse.HttpCleanerUtil;
import com.spider.utils.RegexUtil;
import com.spider.utils.StringUtil;

/**
 * HttpCleaner包,基于xpath解析,支持性不好
 */
public class BaiDu2 extends DeepCrawler {
	public BaiDu2(String crawlPath) {
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
		Object[] titles = HttpCleanerUtil.crawlByXPath(html,
				"//*[@tpl='se_com_default']/h3[@class='t']/a");
		Object[] times = HttpCleanerUtil.crawlByXPath(html,
				"//*[@class='f13']//span");
		Object[] contents = HttpCleanerUtil.crawlByXPath(html,
				"//*[@class='c-abstract']");
		String title = null;
		String time = null;
		String content = null;
		SearchDao sd = new SearchDao();
		for (Object tagNode : titles) {
			title = ((TagNode) tagNode).getText().toString().trim();
			System.out.println(title);
			break;
		}
		for (Object tagNode : times) {
			time = ((TagNode) tagNode).getText().toString().trim();
			System.out.println(time);
			break;
		}
		for (Object tagNode : contents) {
			content = ((TagNode) tagNode).getText().toString().trim();
			System.out.println(content);
			break;
		}
		// Date date = null;
		// try {
		// date = DateTimeUtil.formatStr(RegexUtil.parseTime(time),
		// "yyyy-MM-dd");
		// } catch (ParseException e) {
		// LOG.error("解析时间错误的网址:" + page.getUrl() + "\n" + "错误时间:" + time);
		// e.printStackTrace();
		// }

		time = RegexUtil.parseTime(time);
		String http = page.getUrl();
		if (!StringUtil.isEmpty(title) || !StringUtil.isEmpty(content)
				|| !StringUtil.isEmpty(time) || !StringUtil.isEmpty(http)) {
			Search search = new Search(title, content, time, http, "百度");
			System.out.println(search);
			sd.save(search);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		BaiDu2 crawler = new BaiDu2("log/bd2");
		for (int page = 1; page <= 22; page++) {
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
