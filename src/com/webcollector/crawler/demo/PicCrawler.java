package com.webcollector.crawler.demo;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.FileUtils;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

/**
 * 用WebCollector爬虫爬取整站图片
 */
public class PicCrawler extends DeepCrawler {
	AtomicInteger id = new AtomicInteger(0);

	public PicCrawler(String crawlPath) {
		super(crawlPath);
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		System.out.println(page.getHtml());
		if (Pattern.matches(".*jpg$", page.getUrl())
				|| Pattern.matches(".*png$", page.getUrl())
				|| Pattern.matches(".*gif$", page.getUrl())) {
			try {
				FileUtils.writeFileWithParent(
						"download/" + id.incrementAndGet() + ".jpg",
						page.getContent());
				System.out.println("download:" + page.getUrl());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		MyLinks nextLinks = new MyLinks();
		RegexRule rr = new RegexRule();
		rr.addRule(".*meishij.*");
		nextLinks.addAllFromDocument(page.getDoc(), rr);
		nextLinks.filterImgUrl(page.getDoc(), rr);

		System.out.println(nextLinks.size());
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return nextLinks;
	}

	public static void main(String[] args) throws Exception {
		PicCrawler crawler = new PicCrawler("log/meishij");
		crawler.addSeed("http://www.meishij.net/");
		crawler.setThreads(15);
		crawler.start(10);
	}
}