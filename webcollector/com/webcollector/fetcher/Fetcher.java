package com.webcollector.fetcher;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.db.file.DbUpdater;
import com.webcollector.fetcher.thread.FetcherThread;
import com.webcollector.fetcher.thread.QueueFeeder;
import com.webcollector.generator.Generator;
import com.webcollector.net.HttpRequester;
import com.webcollector.util.Config;
import com.webcollector.visitor.VisitorFactory;

/**
 * deep web抓取器
 */
public class Fetcher {
	public static final Logger LOG = LoggerFactory.getLogger(Fetcher.class);
	public DbUpdater dbUpdater = null;
	public HttpRequester httpRequester = null;
	public VisitorFactory visitorFactory = null;
	public static final int FETCH_SUCCESS = 1;
	public static final int FETCH_FAILED = 2;
	private int retry = 3;
	private AtomicInteger activeThreads;
	private AtomicInteger spinWaiting;
	private AtomicLong lastRequestStart;
	private QueueFeeder feeder;
	private FetchQueue fetchQueue;
	private int threads = 50;
	private boolean isContentStored = false;
	private boolean running;

	private void before() throws Exception {
		// DbUpdater recoverDbUpdater = createRecoverDbUpdater();
		try {
			if (dbUpdater.isLocked()) {
				dbUpdater.merge();
				dbUpdater.unlock();
			}
		} catch (Exception e) {
			LOG.info("Exception", e);
		}
		dbUpdater.lock();
		dbUpdater.getSegmentWriter().init();
		running = true;
	}

	/**
	 * 抓取当前所有任务，会阻塞到爬取完成
	 * 
	 * @param generator
	 *            给抓取提供任务的Generator(抓取任务生成器)
	 */
	public void fetchAll(Generator generator) throws Exception {
		if (visitorFactory == null) {
			LOG.info("Please specify a VisitorFactory!");
			return;
		}
		before();
		lastRequestStart = new AtomicLong(System.currentTimeMillis());
		activeThreads = new AtomicInteger(0);
		spinWaiting = new AtomicInteger(0);

		// 就一个生产者
		fetchQueue = new FetchQueue();
		feeder = new QueueFeeder(fetchQueue, generator, 1000);
		feeder.start();

		// 多个消费者
		FetcherThread[] fetcherThreads = new FetcherThread[threads];
		for (int i = 0; i < threads; i++) {
			fetcherThreads[i] = new FetcherThread(Fetcher.this);
			fetcherThreads[i].start();
		}

		do {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			LOG.info("-activeThreads=" + activeThreads.get() + ", spinWaiting="
					+ spinWaiting.get() + ", fetchQueue.size="
					+ fetchQueue.getSize());

			if (!feeder.isAlive() && fetchQueue.getSize() < 5) {
				// 打印出来
				fetchQueue.dump();
			}

			if ((System.currentTimeMillis() - lastRequestStart.get()) > Config.requestMaxInterval) {
				LOG.info("Aborting with " + activeThreads + " hung threads.");
				break;
			}

		} while (activeThreads.get() > 0 && running);

		for (int i = 0; i < threads; i++) {
			if (fetcherThreads[i].isAlive()) {
				fetcherThreads[i].stop();
			}
		}
		feeder.stop();
		fetchQueue.clear();
		after();
	}

	private void after() throws Exception {
		dbUpdater.close();
		dbUpdater.merge();
		dbUpdater.unlock();
	}

	// 停止爬取
	public void stop() {
		running = false;
	}

	// 返回爬虫的线程数
	public int getThreads() {
		return threads;
	}

	// 设置爬虫的线程数
	public void setThreads(int threads) {
		this.threads = threads;
	}

	// 返回http请求失败后重试的次数
	public int getRetry() {
		return retry;
	}

	// 设置http请求失败后重试的次数
	public void setRetry(int retry) {
		this.retry = retry;
	}

	// 返回是否存储网页/文件的内容
	public boolean getIsContentStored() {
		return isContentStored;
	}

	// 设置是否存储网页／文件的内容
	public void setContentStored(boolean isContentStored) {
		this.isContentStored = isContentStored;
	}

	// 返回CrawlDB更新器
	public DbUpdater getDbUpdater() {
		return dbUpdater;
	}

	// 设置CrawlDB更新器
	public void setDbUpdater(DbUpdater dbUpdater) {
		this.dbUpdater = dbUpdater;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public HttpRequester getHttpRequester() {
		return httpRequester;
	}

	public void setHttpRequester(HttpRequester httpRequester) {
		this.httpRequester = httpRequester;
	}

	public VisitorFactory getVisitorFactory() {
		return visitorFactory;
	}

	public void setVisitorFactory(VisitorFactory visitorFactory) {
		this.visitorFactory = visitorFactory;
	}

	public AtomicInteger getActiveThreads() {
		return activeThreads;
	}

	public void setActiveThreads(AtomicInteger activeThreads) {
		this.activeThreads = activeThreads;
	}

	public AtomicLong getLastRequestStart() {
		return lastRequestStart;
	}

	public void setLastRequestStart(AtomicLong lastRequestStart) {
		this.lastRequestStart = lastRequestStart;
	}

	public QueueFeeder getFeeder() {
		return feeder;
	}

	public void setFeeder(QueueFeeder feeder) {
		this.feeder = feeder;
	}

	public FetchQueue getFetchQueue() {
		return fetchQueue;
	}

	public void setFetchQueue(FetchQueue fetchQueue) {
		this.fetchQueue = fetchQueue;
	}

	public AtomicInteger getSpinWaiting() {
		return spinWaiting;
	}

	public void setSpinWaiting(AtomicInteger spinWaiting) {
		this.spinWaiting = spinWaiting;
	}
}
