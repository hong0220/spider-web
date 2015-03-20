package com.webcollector.fetcher.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.fetcher.FetchItem;
import com.webcollector.fetcher.Fetcher;
import com.webcollector.model.CrawlDatum;
import com.webcollector.model.Links;
import com.webcollector.model.Page;
import com.webcollector.net.HttpResponse;
import com.webcollector.visitor.Visitor;

public class FetcherThread extends Thread {
	public static final Logger LOG = LoggerFactory
			.getLogger(FetcherThread.class);
	private Fetcher fetcher;

	public FetcherThread(Fetcher tetcher) {
		this.fetcher = tetcher;
	}

	@Override
	public void run() {
		fetcher.getActiveThreads().incrementAndGet();
		FetchItem item = null;
		try {
			while (true) {
				try {
					// 得到一个任务
					item = fetcher.getFetchQueue().getFetchItem();
					if (item == null) {
						// 生产者还活着或者生产者死了，但还有产品
						if (fetcher.getFeeder().isAlive()
								|| fetcher.getFetchQueue().getSize() > 0) {
							fetcher.getSpinWaiting().incrementAndGet();
							try {
								Thread.sleep(500);
							} catch (Exception e) {
							}
							fetcher.getSpinWaiting().decrementAndGet();
							continue;
						} else {
							return;
						}
					}

					fetcher.getLastRequestStart().set(
							System.currentTimeMillis());

					String url = item.datum.getUrl();

					HttpResponse response = null;
					int retryCount = 0;
					for (; retryCount <= fetcher.getRetry(); retryCount++) {
						if (retryCount > 0) {
							LOG.info("retry " + retryCount + "th " + url);
						}
						try {
							response = fetcher.getHttpRequester().getResponse(
									url);
							break;
						} catch (Exception e) {
							e.printStackTrace();
							LOG.error("连接异常" + url);
						}
					}
					CrawlDatum crawlDatum = null;

					// 爬取成功
					if (response != null) {
						LOG.info("fetch " + url);
						crawlDatum = new CrawlDatum(url,
								CrawlDatum.STATUS_DB_FETCHED,
								item.datum.getRetry() + retryCount);
					} else {// 爬取失败
						LOG.info("failed " + url);
						crawlDatum = new CrawlDatum(url,
								CrawlDatum.STATUS_DB_UNFETCHED,
								item.datum.getRetry() + retryCount);
					}

					try {
						// 写入fetch信息
						fetcher.getDbUpdater().getSegmentWriter()
								.wrtieFetch(crawlDatum);

						if (response == null) {
							continue;
						}

						String contentType = response.getContentType();
						Visitor visitor = fetcher.getVisitorFactory()
								.createVisitor(url, contentType);

						Page page = new Page();
						page.setUrl(url);
						page.setResponse(response);
						if (visitor != null) {
							Links nextLinks = null;
							try {
								// 用户自定义visitor处理页面,并获取链接
								nextLinks = visitor.visitAndGetNextLinks(page);
							} catch (Exception e) {
								LOG.info("Exception", e);
							}

							// 写入解析出的链接
							if (nextLinks != null && !nextLinks.isEmpty()) {
								fetcher.getDbUpdater().getSegmentWriter()
										.wrtieLinks(nextLinks);
							}
						}
					} catch (Exception e) {
						LOG.info("Exception", e);
					}
				} catch (Exception e) {
					LOG.info("Exception", e);
				}
			}
		} catch (Exception e) {
			LOG.info("Exception", e);
		} finally {
			fetcher.getActiveThreads().decrementAndGet();
		}
	}
}