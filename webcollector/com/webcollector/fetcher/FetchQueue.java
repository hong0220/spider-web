package com.webcollector.fetcher;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetchQueue {
	public static final Logger LOG = LoggerFactory.getLogger(FetchQueue.class);
	public AtomicInteger totalSize = new AtomicInteger(0);
	public List<FetchItem> queue = Collections
			.synchronizedList(new LinkedList<FetchItem>());

	public synchronized void clear() {
		queue.clear();
	}

	public int getSize() {
		return queue.size();
	}

	public void addFetchItem(FetchItem item) {
		if (item == null) {
			return;
		}
		queue.add(item);
		totalSize.incrementAndGet();
	}

	public synchronized FetchItem getFetchItem() {
		if (queue.isEmpty()) {
			return null;
		}
		return queue.remove(0);
	}

	public synchronized void dump() {
		for (int i = 0; i < queue.size(); i++) {
			FetchItem it = queue.get(i);
			LOG.info("  " + i + ". " + it.datum.getUrl());
		}
	}
}