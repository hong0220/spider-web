package com.webcollector.generator;

import com.webcollector.generator.Generator;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.CursorConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.webcollector.model.CrawlDatum;
import com.webcollector.util.BerkeleyDBUtils;

import java.io.UnsupportedEncodingException;

public class StandardGenerator implements Generator {
	private Database database = null;
	private Cursor cursor = null;
	private DatabaseEntry key = new DatabaseEntry();
	private DatabaseEntry value = new DatabaseEntry();

	public StandardGenerator(Environment env) {
		this.database = env.openDatabase(null, "crawldb",
				BerkeleyDBUtils.defaultDBConfig);
	}

	@Override
	public CrawlDatum next() {
		if (cursor == null) {
			cursor = database.openCursor(null, CursorConfig.DEFAULT);
		}
		while (true) {
			if (cursor.getNext(key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				try {
					CrawlDatum datum = new CrawlDatum(key, value);
					if (datum.getStatus() == CrawlDatum.STATUS_DB_FETCHED) {
						continue;
					} else {
						if (datum.getRetry() >= 10) {
							continue;
						}
						return datum;
					}
				} catch (UnsupportedEncodingException e) {
					continue;
				}
			} else {
				cursor.close();
				database.close();
				return null;
			}
		}
	}

	public static void main(String[] args) {

	}
}
