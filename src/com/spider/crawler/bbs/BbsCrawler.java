package com.spider.crawler.bbs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.xsoup.Xsoup;

import com.hibernate.dao.BbsDao;
import com.hibernate.model.Bbs;
import com.spider.model.SpiderConfig;
import com.spider.utils.StringUtil;
import com.webcollector.crawler.DeepCrawler;
import com.webcollector.model.Links;
import com.webcollector.model.Page;
import com.webcollector.util.RegexRule;

/**
 * 论坛抽取模板
 * 
 * Xsoup包解析网页,基于xpath语法解析
 * 
 * https://github.com/code4craft/xsoup
 */
public class BbsCrawler extends DeepCrawler {
	public static final Logger LOG = LoggerFactory.getLogger(BbsCrawler.class);
	private SpiderConfig spiderConfig;

	public BbsCrawler(String crawlPath) {
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
			// 特殊的时间格式：昨天 23:24
			// time = RegexUtil.parseTime(time);

			// Date date = DateTimeUtil.formatStr(
			// SiteTimeUtil.getSinaDate(time), "yyyy-MM-dd");
			// } catch (ParseException e) {
			// LOG.error("解析时间错误的网址:" + http + "\n" + "错误时间:" + time);
			// e.printStackTrace();
			// }

			try {
				Bbs bbs = new Bbs(title, time, source, content, http, 0);
				System.out.println(bbs);
				BbsDao nd = new BbsDao();
				// if (!nd.getExist(news)) {
				nd.save(bbs);
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
		// spiderConfig.setHttp("http://www.discuz.net/thread-3646749-1-1.html");
		// spiderConfig.setHttp("http://www.discuz.net/thread-3645974-1-1.html");
		spiderConfig.setHttp("http://www.discuz.net/thread-3646665-1-1.html");
		spiderConfig.setTitleRuler("//*[@id=thread_subject]/allText()");
		spiderConfig.setSourceRuler("//*[@id*=favatar]/div[1]/div/allText()");
		spiderConfig.setTimeRuler("//*[@id*=authorposton]/span/allText()");
		spiderConfig.setContentRuler("//*[@id*=postmessage]/allText()");

		BbsCrawler bc = new BbsCrawler("log/bc");
		bc.addSeed(spiderConfig.getHttp());
		bc.setSpiderConfig(spiderConfig);
		bc.start(1);
	}
}
