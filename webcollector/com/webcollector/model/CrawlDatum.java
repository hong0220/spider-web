package com.webcollector.model;

import com.webcollector.model.CrawlDatum;

import com.sleepycat.je.DatabaseEntry;

import java.io.UnsupportedEncodingException;

/**
 * 存储爬取任务的类，是WebCollector的核心类，记录了一个url的爬取信息，同样也可以作为一个爬取任务
 */
public class CrawlDatum {
	// 爬取状态常量-注入
	public static final byte STATUS_DB_INJECTED = 0x01;
	// 爬取状态常量-未爬取
	public static final byte STATUS_DB_UNFETCHED = 0x04;
	// 爬取状态常量-已爬取
	public static final byte STATUS_DB_FETCHED = 0x05;
	private String url;
	private byte status = CrawlDatum.STATUS_DB_INJECTED;
	private int retry = 0;

	public CrawlDatum(String url, byte status) {
		this.url = url;
		this.status = status;
		this.retry = 0;
	}

	public CrawlDatum(String url, byte status, int retry) {
		this.url = url;
		this.status = status;
		this.retry = retry;
	}

	public CrawlDatum(DatabaseEntry key, DatabaseEntry value)
			throws UnsupportedEncodingException {
		this.url = new String(key.getData(), "utf-8");
		byte[] valueData = value.getData();
		this.status = valueData[0];
		this.retry = valueData[1];
	}

	public DatabaseEntry getKey() throws UnsupportedEncodingException {
		return new DatabaseEntry(url.getBytes("utf-8"));
	}

	public DatabaseEntry getValue() {
		byte[] value = new byte[2];
		value[0] = status;
		value[1] = (byte) retry;
		return new DatabaseEntry(value);
	}

	// 获取爬取任务的url
	public String getUrl() {
		return url;
	}

	// 设置爬取任务的url
	public void setUrl(String url) {
		this.url = url;
	}

	// 获取爬取任务的状态
	public byte getStatus() {
		return status;
	}

	// 设置爬取任务的状态
	public void setStatus(byte status) {
		this.status = status;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}
}
