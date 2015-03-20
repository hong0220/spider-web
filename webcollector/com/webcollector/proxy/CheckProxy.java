package com.webcollector.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.lang.model.element.UnknownElementException;

/**
 * 验证代理
 */
public class CheckProxy {
	@SuppressWarnings({ "finally", "unused" })
	private static boolean judgeProxy(String proxy) {
		String ip = proxy.split(":")[0].trim();
		int port = Integer.valueOf(proxy.split(":")[1].trim());
		Socket server = new Socket();
		InetSocketAddress address = new InetSocketAddress(ip, port);
		System.out.println("检测代理地址" + ip + ":" + port);
		Boolean flag = false;
		try {
			server.connect(address, 300);
			flag = true;
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (UnknownElementException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return flag;
		}
	}
}
