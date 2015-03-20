package com.webcollector.visitor;

import com.webcollector.model.Links;
import com.webcollector.model.Page;

/*
 * Visitor是新API中，用户自定义的对每个页面访问的操作
 */
public interface Visitor {

	// 访问页面page,并从页面中抽取页面中发现的需要爬取的URL,返回 如果不需要从给定页面中发现新的链接，返回null
	public abstract Links visitAndGetNextLinks(Page page);
}
