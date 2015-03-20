package com.webcollector.segment;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.webcollector.model.CrawlDatum;
import com.webcollector.model.Links;
import com.webcollector.util.BerkeleyDBUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 爬取过程中，写入爬取历史、网页Content、解析信息的Writer
 */
public class SegmentWriter {
	public int BUFFER_SIZE = 20;
	Database fetchDatabase = null;
	Database linkDatabase = null;
	AtomicInteger count_fetch = new AtomicInteger(0);
	AtomicInteger count_link = new AtomicInteger(0);
	Environment env;

	public SegmentWriter(Environment env) {
		this.env = env;
	}

	public void init() {
		fetchDatabase = env.openDatabase(null, "fetch",
				BerkeleyDBUtils.defaultDBConfig);
		linkDatabase = env.openDatabase(null, "link",
				BerkeleyDBUtils.defaultDBConfig);
		count_fetch = new AtomicInteger(0);
		count_link = new AtomicInteger(0);
	}

	// 写入一条爬取历史记录（爬取任务)
	public void wrtieFetch(CrawlDatum fetch) throws Exception {
		DatabaseEntry key = fetch.getKey();
		DatabaseEntry value = fetch.getValue();
		fetchDatabase.put(null, key, value);
		if (count_fetch.incrementAndGet() % BUFFER_SIZE == 0) {
			fetchDatabase.sync();
		}
	}

	public void wrtieLinks(Links links) throws Exception {
		for (String url : links) {
			CrawlDatum datum = new CrawlDatum(url,
					CrawlDatum.STATUS_DB_UNFETCHED);
			DatabaseEntry key = datum.getKey();
			DatabaseEntry value = datum.getValue();
			linkDatabase.put(null, key, value);
		}
		if (count_link.incrementAndGet() % BUFFER_SIZE == 0) {
			linkDatabase.sync();
		}
	}

	// 关闭Writer
	public void close() throws Exception {
		fetchDatabase.sync();
		linkDatabase.sync();
		fetchDatabase.close();
		linkDatabase.close();
	}
}
