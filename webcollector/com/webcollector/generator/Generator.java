package com.webcollector.generator;

import com.webcollector.model.CrawlDatum;

/**
 * 抓取任务生成器
 */
public interface Generator {

	/**
	 * 获取下一个抓取任务
	 * 
	 * @return 下一个抓取任务，如果没有任务，返回null
	 */
	public CrawlDatum next();
}
