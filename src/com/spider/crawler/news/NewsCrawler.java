package com.spider.crawler.news;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.xsoup.Xsoup;
import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

import com.hibernate.dao.NewsDao;
import com.hibernate.model.News;
import com.spider.model.SpiderConfig;
import com.spider.utils.RegexUtil;
import com.spider.utils.StringUtil;

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
			time = Xsoup.select(page.getDoc(),
					spiderConfig.getTimeRuler().split("#=>#")[0]).get();
		}
		if (spiderConfig.getSourceRuler() != null) {
			source = Xsoup.select(page.getDoc(),
					spiderConfig.getSourceRuler().split("#=>#")[0]).get();
		}
		if (spiderConfig.getContentRuler() != null) {
			content = Xsoup.select(page.getDoc(),
					spiderConfig.getContentRuler()).get();
		}

		System.out.println(title);
		System.out.println(time);
		System.out.println(source);
		System.out.println(content);

		if (!StringUtil.isEmpty(title) && !StringUtil.isEmpty(content)
				&& !StringUtil.isEmpty(time) && !StringUtil.isEmpty(source)
				&& !StringUtil.isEmpty(http)) {
			// 增加一条数据
			if (spiderConfig.getTimeRuler().contains("#split_small#")) {
				String regex = spiderConfig.getTimeRuler().split("#=>#")[1]
						.replaceFirst("#split_small#", "(");
				regex = regex.replaceFirst("#split_small#", ")");
				System.out.println(regex);
				time = RegexUtil.getData(regex, time);
			} else {
				time = RegexUtil.parseTime(time);
			}
			if (spiderConfig.getSourceRuler().contains("#split_small#")) {
				String regex = spiderConfig.getSourceRuler().split("#=>#")[1]
						.replaceFirst("#split_small#", "(");
				regex = regex.replaceFirst("#split_small#", ")");
				System.out.println(regex);
				source = RegexUtil.getData(regex, source);
			} else {
				source = StringUtil.filter(source);
			}
			title = StringUtil.filter(title);
			content = StringUtil.filter(content);

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
		rr.addRule(spiderConfig.getRuler());

		nextLinks.addAllFromDocument(page.getDoc(), rr);
		System.out.println("抓到多少链接" + nextLinks.size());
		System.out.println("-----------------------------------------");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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

		// 新浪新闻
		SpiderConfig spiderConfig = new SpiderConfig();
		spiderConfig.setRuler("http://news.sina.com.cn/c/.*shtml");
		spiderConfig.setHttp("http://news.sina.com.cn/china/");
		spiderConfig.setTitleRuler("//*[@id=artibodyTitle]/allText()");
		spiderConfig
				.setSourceRuler("//*[@id=wrapOuter]/div/div[5]/span/span/allText()#=>#");
		spiderConfig
				.setTimeRuler("//*[@id=wrapOuter]/div/div[5]/span/allText()#=>#");
		spiderConfig.setContentRuler("//*[@id=artibody]/allText()");

		NewsCrawler nc = new NewsCrawler("log/nc");
		nc.addSeed(spiderConfig.getHttp());
		nc.setThreads(2);
		nc.setSpiderConfig(spiderConfig);
		nc.start(2);

		System.out.println("------网易新闻--------");
		// 网易新闻
		SpiderConfig spiderConfig2 = new SpiderConfig();
		spiderConfig2.setRuler("http://news.163.com/.*html");
		spiderConfig2.setHttp("http://news.163.com/");
		spiderConfig2.setTitleRuler("//*[@id=h1title]/allText()");
		spiderConfig2.setSourceRuler("//*[@class=ep-time-soure]/allText()#=>#");
		spiderConfig2
				.setTimeRuler("//*[@class=ep-time-soure]/allText()#=>##split_small#.*#split_small#来源");
		spiderConfig.setContentRuler("//*[@id=endText]/allText()");

		NewsCrawler nc2 = new NewsCrawler("log/nc");
		nc2.addSeed(spiderConfig.getHttp());
		nc2.setThreads(2);
		nc2.setSpiderConfig(spiderConfig);
		nc2.start(2);

		System.out.println("------搜狐新闻--------");
		// 搜狐新闻
		SpiderConfig spiderConfig3 = new SpiderConfig();
		spiderConfig3.setRuler("http://news.sohu.com/.*shtml");
		spiderConfig3.setHttp("http://news.sohu.com/");
		spiderConfig3.setTitleRuler("//h1/allText()");
		spiderConfig3
				.setSourceRuler("//*[@id=source_baidu]/allText()#=>#来源：#split_small#.*#split_small#");
		spiderConfig3.setTimeRuler("//*[@id=pubtime_baidu]/allText()#=>#");
		spiderConfig3.setContentRuler("//*[@id=contentText]/div[1]/allText()");

		NewsCrawler nc3 = new NewsCrawler("log/nc");
		nc3.addSeed(spiderConfig.getHttp());
		nc3.setThreads(2);
		nc3.setSpiderConfig(spiderConfig);
		nc3.start(2);
	}
}
