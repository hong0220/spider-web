package com.webcollector.crawler;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.webcollector.db.file.DbUpdater;
import com.webcollector.fetcher.Fetcher;
import com.webcollector.generator.Generator;
import com.webcollector.generator.Injector;
import com.webcollector.generator.StandardGenerator;
import com.webcollector.net.HttpRequester;
import com.webcollector.net.HttpRequesterImpl;
import com.webcollector.proxy.Proxys;
import com.webcollector.util.FileUtils;
import com.webcollector.visitor.VisitorFactory;

public abstract class Crawler implements VisitorFactory {
	private static final Logger LOG = LoggerFactory.getLogger(Crawler.class);
	public final static int RUNNING = 1;
	public final static int STOPED = 2;
	protected int status;
	protected boolean resumable = false;
	protected int threads = 50;
	protected ArrayList<String> seeds = new ArrayList<String>();
	protected ArrayList<String> forcedSeeds = new ArrayList<String>();
	protected Fetcher fetcher;
	protected VisitorFactory visitorFactory = this;
	// 默认
	protected HttpRequester httpRequester = new HttpRequesterImpl();
	protected String crawlPath;
	protected Environment env;

	public Crawler(String crawlPath) {
		this.crawlPath = crawlPath;
	}

	public void start(int depth) throws Exception {
		File dir = new File(crawlPath);
		boolean needInject = true;

		// 种子重复，忽略
		if (resumable && dir.exists()) {
			// 在断点续爬的时候，不需要通过Injector向CrawlDB注入种子，因为CrawlDB中已有爬取任务。
			needInject = false;
		}
		if (resumable && !dir.exists()) {
			dir.mkdirs();
		}
		if (!resumable) {
			if (dir.exists()) {
				FileUtils.deleteDir(dir);
			}
			dir.mkdirs();
			if (seeds.isEmpty() && forcedSeeds.isEmpty()) {
				LOG.info("error:Please add at least one seed");
				return;
			}
		}
		EnvironmentConfig environmentConfig = new EnvironmentConfig();
		environmentConfig.setAllowCreate(true);
		// 指定文件存放路径和配置
		env = new Environment(dir, environmentConfig);

		// 在断点续爬的时候，不需要通过Injector向CrawlDB注入种子，因为CrawlDB中已有爬取任务。
		if (needInject) {
			inject();
		}

		if (!forcedSeeds.isEmpty()) {
			injectForcedSeeds();
		}

		status = RUNNING;
		for (int i = 0; i < depth; i++) {
			if (status == STOPED) {
				break;
			}
			LOG.info("starting depth " + (i + 1));
			Generator generator = new StandardGenerator(env);

			fetcher = new Fetcher();
			fetcher.setHttpRequester(httpRequester);
			fetcher.setDbUpdater(new DbUpdater(env));
			fetcher.setVisitorFactory(visitorFactory);
			fetcher.setThreads(threads);

			fetcher.fetchAll(generator);
		}
		env.close();
	}

	// 添加一个种子url(如果断点爬取，种子只会在第一次爬取时注入)
	public void addSeed(String seed) {
		seeds.add(seed);
	}

	// 添加一个种子url(如果断点爬取，种子会在每次启动爬虫时注入， 如果爬取历史中有相同url,则覆盖)
	public void addForcedSeed(String seed) {
		forcedSeeds.add(seed);
	}

	public Generator createGenerator() {
		return new StandardGenerator(env);
	}

	public void inject() throws Exception {
		Injector injector = new Injector(env);
		injector.inject(seeds);
	}

	public void injectForcedSeeds() throws Exception {
		Injector injector = new Injector(env);
		injector.inject(forcedSeeds);
	}

	public VisitorFactory getVisitorFactory() {
		return visitorFactory;
	}

	public void setVisitorFactory(VisitorFactory visitorFactory) {
		this.visitorFactory = visitorFactory;
	}

	public HttpRequester getHttpRequester() {
		return httpRequester;
	}

	public void setHttpRequester(HttpRequester httpRequester) {
		this.httpRequester = httpRequester;
	}

	public ArrayList<String> getSeeds() {
		return seeds;
	}

	public void setSeeds(ArrayList<String> seeds) {
		this.seeds = seeds;
	}

	public ArrayList<String> getForcedSeeds() {
		return forcedSeeds;
	}

	public void setForcedSeeds(ArrayList<String> forcedSeeds) {
		this.forcedSeeds = forcedSeeds;
	}

	public boolean isResumable() {
		return resumable;
	}

	public void setResumable(boolean resumable) {
		this.resumable = resumable;
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public Proxys getProxys() {
		return httpRequester.getProxys();
	}

	public void setProxys(Proxys proxys) {
		httpRequester.setProxys(proxys);
	}
}
