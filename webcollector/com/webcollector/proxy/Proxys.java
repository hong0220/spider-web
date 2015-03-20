package com.webcollector.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcollector.proxy.Proxys;

public class Proxys extends ArrayList<Proxy> {
	public static final Logger LOG = LoggerFactory.getLogger(Proxys.class);
	public static Random random = new Random();

	public void add(String ip, int port) {
		Proxy proxy = new Proxy(Proxy.Type.HTTP,
				new InetSocketAddress(ip, port));
		this.add(proxy);
	}

	public void add(String proxyStr) throws Exception {
		try {
			String[] infos = proxyStr.split(":");
			String ip = infos[0];
			int port = Integer.valueOf(infos[1]);

			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip,
					port));
			this.add(proxy);
		} catch (Exception e) {
			LOG.info("Exception", e);
		}
	}

	public Proxy nextProxy() {
		return getRandomProxy();
	}

	public Proxy getRandomProxy() {
		if (this.isEmpty()) {
			return null;
		}
		try {
			int r = random.nextInt(this.size());
			return this.get(r);
		} catch (Exception e) {
			return null;
		}
	}

	public void addAllFromFile(File file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = null;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.startsWith("#") || line.isEmpty()) {
				continue;
			} else {
				this.add(line);
			}
		}
	}
}
