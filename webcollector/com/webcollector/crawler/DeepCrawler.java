package com.webcollector.crawler;

import com.webcollector.crawler.Crawler;
import com.webcollector.crawler.DeepCrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.visitor.Visitor;

public abstract class DeepCrawler extends Crawler implements Visitor {
	public static final Logger LOG = LoggerFactory.getLogger(DeepCrawler.class);

	public DeepCrawler(String crawlPath) {
		super(crawlPath);
	}

	@Override
	public Visitor createVisitor(String url, String contentType) {
		// ---------
		return this;
	}

	public static void main(String[] args) throws Exception {
		// DeepCrawler crawler = new DeepCrawler("/bdbcrawl");
		// crawler.addSeed("http://www.xinhuanet.com/");
		// crawler.addSeed("http://www.sina.com");
		// crawler.start(3);
	}
}
