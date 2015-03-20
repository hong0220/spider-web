package com.spider.crawler.news;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.xsoup.Xsoup;

import com.hibernate.dao.NewsDao;
import com.hibernate.model.News;
import com.spider.model.SpiderConfig;
import com.spider.utils.RegexUtil;
import com.spider.utils.StringUtil;
import com.webcollector.crawler.DeepCrawler;
import com.webcollector.model.Links;
import com.webcollector.model.Page;
import com.webcollector.util.RegexRule;

/**
 * 新闻抽取模板
 * 
 * Xsoup包解析网页,基于xpath语法解析
 * 
 * https://github.com/code4craft/xsoup
 */
public class NewsCrawler extends DeepCrawler {
	public static final Logger LOG = LoggerFactory.getLogger(NewsCrawler.class);
	private SpiderConfig spiderConfig;

	public NewsCrawler(String crawlPath) {
		super(crawlPath);
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		// System.out.println(page.getHtml());
		String http = page.getUrl().trim();
		String title = null;
		String time = null;
		String source = null;
		String content = null;

		if (spiderConfig.getTitleRuler() != null) {
			title = Xsoup.select(page.getDoc(), spiderConfig.getTitleRuler())
					.get();
		}
		if (spiderConfig.getTimeRuler() != null) {
			time = Xsoup.select(page.getDoc(), spiderConfig.getTimeRuler())
					.get();
		}
		if (spiderConfig.getSourceRuler() != null) {
			source = Xsoup.select(page.getDoc(), spiderConfig.getSourceRuler())
					.get();
		}
		if (spiderConfig.getContentRuler() != null) {
			content = Xsoup.select(page.getDoc(),
					spiderConfig.getContentRuler()).get();
		}

		System.out.println(title);
		System.out.println(time);
		System.out.println(source);
		System.out.println(content);

		if (!StringUtil.isEmpty(title) || !StringUtil.isEmpty(content)
				|| !StringUtil.isEmpty(time) || !StringUtil.isEmpty(http)) {
			// 增加一条数据
			title = StringUtil.filter(title);
			source = StringUtil.filter(source);
			content = StringUtil.filter(content);
			time = RegexUtil.parseTime(time);

			// Date date = DateTimeUtil.formatStr(
			// SiteTimeUtil.getSinaDate(time), "yyyy-MM-dd");
			// } catch (ParseException e) {
			// LOG.error("解析时间错误的网址:" + http + "\n" + "错误时间:" + time);
			// e.printStackTrace();
			// }

			try {
				News news = new News(title, time, source, content, http, 0);
				System.out.println(news);
				NewsDao nd = new NewsDao();
				// if (!nd.getExist(news)) {
				nd.save(news);
				// }
			} catch (Exception e) {
				LOG.error("网址:" + http + "标题:" + title);
			}
		}
		System.out.println("-----------------------------------------");
		Links nextLinks = new Links();
		RegexRule rr = new RegexRule();
		rr.addRule(".*");
		nextLinks.addAllFromDocument(page.getDoc(), rr);
		System.out.println("抓到多少链接" + nextLinks.size());
		System.out.println("-----------------------------------------");
		return nextLinks;
	}

	public SpiderConfig getSpiderConfig() {
		return spiderConfig;
	}

	public void setSpiderConfig(SpiderConfig spiderConfig) {
		this.spiderConfig = spiderConfig;
	}

	public static void main(String[] args) throws Exception {
		// allText
		SpiderConfig spiderConfig = new SpiderConfig();
		// spiderConfig
		// .setHttp("http://jwc.fjnu.edu.cn/s/10/t/781/55/e2/info87522.htm");
		// spiderConfig
		// .setHttp("http://jwc.fjnu.edu.cn/s/10/t/781/55/e3/info87523.htm");
		spiderConfig
				.setHttp("http://jwc.fjnu.edu.cn/s/10/t/781/4e/35/info85557.htm");
		spiderConfig.setTitleRuler("//*[@class=ArticleTitle]/allText()");
		spiderConfig
				.setTimeRuler("//*[@id=Body]/div[2]/table/tbody/tr[5]/allText()");
		spiderConfig
				.setContentRuler("//*[@id=Body]/div[2]/table/tbody/tr[8]/allText()");

		NewsCrawler nc = new NewsCrawler("log/nc");
		nc.addSeed(spiderConfig.getHttp());
		nc.setSpiderConfig(spiderConfig);
		nc.start(1);
	}
}
