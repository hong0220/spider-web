package com.webcollector.fetcher.thread;

import com.webcollector.fetcher.FetchItem;
import com.webcollector.fetcher.FetchQueue;
import com.webcollector.generator.Generator;
import com.webcollector.model.CrawlDatum;

public class QueueFeeder extends Thread {
	public FetchQueue queue;
	public Generator generator;
	public int size;

	public QueueFeeder(FetchQueue queue, Generator generator, int size) {
		this.queue = queue;
		this.generator = generator;
		this.size = size;
	}

	@Override
	public void run() {
		boolean hasMore = true;
		while (hasMore) {
			int feed = size - queue.getSize();
			if (feed <= 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			while (feed > 0 && hasMore) {
				CrawlDatum datum = generator.next();
				hasMore = (datum != null);
				if (hasMore) {
					queue.addFetchItem(new FetchItem(datum));
					feed--;
				}
			}
		}
	}
}