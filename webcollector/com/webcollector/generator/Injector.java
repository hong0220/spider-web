package com.webcollector.generator;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.webcollector.model.CrawlDatum;
import com.webcollector.util.BerkeleyDBUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Injector {
	private Environment env;

	public Injector(Environment env) {
		this.env = env;
	}

	public void inject(String seed) throws UnsupportedEncodingException {
		Database database = env.openDatabase(null, "crawldb",
				BerkeleyDBUtils.defaultDBConfig);
		CrawlDatum datum = new CrawlDatum(seed, CrawlDatum.STATUS_DB_INJECTED);
		database.put(null, datum.getKey(), datum.getValue());
		database.sync();
		database.close();
	}

	public void inject(ArrayList<String> seeds)
			throws UnsupportedEncodingException {
		DatabaseConfig databaseConfig = new DatabaseConfig();
		databaseConfig.setAllowCreate(true);
		databaseConfig.setDeferredWrite(true);
		Database database = env.openDatabase(null, "crawldb", databaseConfig);
		for (String seed : seeds) {
			// 创建存储爬取任务的类
			CrawlDatum datum = new CrawlDatum(seed,
					CrawlDatum.STATUS_DB_INJECTED);
			// 写入存储爬取任务的类
			database.put(null, datum.getKey(), datum.getValue());
		}
		database.sync();
		database.close();
	}
}
