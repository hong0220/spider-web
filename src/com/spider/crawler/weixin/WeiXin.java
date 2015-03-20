package com.spider.crawler.weixin;

import java.net.URLEncoder;
import java.text.ParseException;
import java.util.regex.Pattern;

import org.htmlcleaner.TagNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hibernate.dao.WeixinDao;
import com.hibernate.model.Weixin;
import com.spider.parse.HttpCleanerUtil;
import com.spider.utils.DateTimeUtil;
import com.spider.utils.StringUtil;
import com.webcollector.crawler.DeepCrawler;
import com.webcollector.model.Links;
import com.webcollector.model.Page;

/**
 * 使用htmlcleaner-2.10.jar,基于xpath语法解析
 */
public class WeiXin extends DeepCrawler {
	public WeiXin(String crawlPath) {
		super(crawlPath);
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		if (Pattern.matches("http://weixin.sogou.com/weixin\\?type=2&query=.*",
				page.getUrl())) {
			Document doc = page.getDoc();
			System.out.println(doc);
			Elements titles = doc.select("div[class=wx-rb wx-rb3]  h4");
			Links nextLinks = new Links();
			for (int i = 0; i < titles.size(); ++i) {
				Element title = titles.get(i);
				nextLinks.add(title.select("a").first().attr("abs:href"));
				System.out.println(title.select("a").first().attr("abs:href"));
			}
			return nextLinks;
		} else {
			String html = page.getHtml();
			if (StringUtil.isEmpty(html)) {
				System.out.println("没有得到网页内容");
				return null;
			}
			System.out.println("有网页内容");
			Object[] titles = HttpCleanerUtil.crawlByXPath(html,
					"//*[@id='activity-name']");
			Object[] times = HttpCleanerUtil.crawlByXPath(html,
					"//*[@id='post-date']");
			Object[] sources = HttpCleanerUtil.crawlByXPath(html,
					"//*[@id='post-user']");
			Object[] contents = HttpCleanerUtil.crawlByXPath(html,
					"//*[@id='js_content']");
			if (titles.length == 0 || times.length == 0 || sources.length == 0
					|| contents.length == 0) {
				return null;
			}
			String title = null;
			String time = null;
			String source = null;
			String content = null;
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
			for (Object tagNode : sources) {
				source = ((TagNode) tagNode).getText().toString().trim();
				System.out.println(source);
				break;
			}
			for (Object tagNode : contents) {
				content = ((TagNode) tagNode).getText().toString().trim();
				System.out.println(content);
				break;
			}
			WeixinDao wd = new WeixinDao();
			Weixin weixin = null;
			try {
				weixin = new Weixin(title, DateTimeUtil.formatStr(time,
						"yyyy-MM-dd"), source, content, page.getUrl());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			wd.save(weixin);
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		String keyWord = "关键";
		WeiXin crawler = new WeiXin("log/wx");
		for (int page = 5; page <= 5; page++) {
			crawler.addSeed("http://weixin.sogou.com/weixin?type=2&query="
					+ URLEncoder.encode(keyWord) + "&page=" + page);
		}
		// 注意深度
		crawler.start(2);
	}
}
