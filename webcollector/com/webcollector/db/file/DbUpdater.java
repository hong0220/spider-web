package com.webcollector.db.file;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.CursorConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.webcollector.model.CrawlDatum;
import com.webcollector.segment.SegmentWriter;
import com.webcollector.util.BerkeleyDBUtils;

import java.io.IOException;

/**
 * 用于更新爬取任务列表的类
 */
public class DbUpdater {
	private Environment env;
	private SegmentWriter segmentWriter;
	private Database lockDatabase;

	public DbUpdater(Environment env) {
		this.env = env;
		segmentWriter = new SegmentWriter(env);
	}

	public void lock() throws Exception {
		lockDatabase = env.openDatabase(null, "lock",
				BerkeleyDBUtils.defaultDBConfig);
		DatabaseEntry key = new DatabaseEntry("lock".getBytes("utf-8"));
		DatabaseEntry value = new DatabaseEntry("locked".getBytes("utf-8"));
		lockDatabase.put(null, key, value);
		lockDatabase.sync();
		lockDatabase.close();
	}

	public boolean isLocked() throws Exception {
		boolean isLocked = false;
		lockDatabase = env.openDatabase(null, "lock",
				BerkeleyDBUtils.defaultDBConfig);
		DatabaseEntry key = new DatabaseEntry("lock".getBytes("utf-8"));
		DatabaseEntry value = new DatabaseEntry();
		if (lockDatabase.get(null, key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
			String lockInfo = new String(value.getData(), "utf-8");
			if (lockInfo.equals("locked")) {
				isLocked = true;
			}
		}
		lockDatabase.close();
		return isLocked;
	}

	public void unlock() throws Exception {
		lockDatabase = env.openDatabase(null, "lock",
				BerkeleyDBUtils.defaultDBConfig);
		DatabaseEntry key = new DatabaseEntry("lock".getBytes("utf-8"));
		DatabaseEntry value = new DatabaseEntry("unlocked".getBytes("utf-8"));
		lockDatabase.put(null, key, value);
		lockDatabase.sync();
		lockDatabase.close();
	}

	public void close() throws Exception {
		segmentWriter.close();
	}

	public void merge() throws Exception {
		Database crawldbDatabase = env.openDatabase(null, "crawldb",
				BerkeleyDBUtils.defaultDBConfig);
		Database fetchDatabase = env.openDatabase(null, "fetch",
				BerkeleyDBUtils.defaultDBConfig);
		Cursor fetchCursor = fetchDatabase.openCursor(null, null);
		DatabaseEntry key = new DatabaseEntry();
		DatabaseEntry value = new DatabaseEntry();
		while (fetchCursor.getNext(key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
			crawldbDatabase.put(null, key, value);
		}
		fetchCursor.close();
		fetchDatabase.close();
		Database linkDatabase = env.openDatabase(null, "link",
				BerkeleyDBUtils.defaultDBConfig);
		Cursor linkCursor = linkDatabase.openCursor(null, null);
		while (linkCursor.getNext(key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
			if (!(crawldbDatabase.get(null, key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS)) {
				crawldbDatabase.put(null, key, value);
			}
		}
		linkCursor.close();
		linkDatabase.close();
		crawldbDatabase.sync();
		crawldbDatabase.close();
		env.removeDatabase(null, "fetch");
		env.removeDatabase(null, "link");
	}

	public SegmentWriter getSegmentWriter() {
		return segmentWriter;
	}
}
