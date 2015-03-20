package com.spider.crawler.weibo.util;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WeiboCN {
	// 使用代理获取cookie
	public static String getSinaCookie(String username, String password,
			String ip, int port) throws Exception {
		StringBuilder sb = new StringBuilder();

		Proxy proxy = new Proxy();
		// 设置代理服务器地址
		proxy.setHttpProxy(ip + ":" + port);
		DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
		capabilities.setCapability(CapabilityType.PROXY, proxy);
		HtmlUnitDriver driver = new HtmlUnitDriver(capabilities);
		driver.setJavascriptEnabled(true);
		driver.get("http://login.weibo.cn/login/");

		WebElement mobile = driver
				.findElementByCssSelector("input[name=mobile]");
		mobile.sendKeys(new CharSequence[] { username });
		WebElement pass = driver
				.findElementByCssSelector("input[name^=password]");
		pass.sendKeys(new CharSequence[] { password });
		WebElement rem = driver
				.findElementByCssSelector("input[name=remember]");
		rem.click();
		WebElement submit = driver
				.findElementByCssSelector("input[name=submit]");
		submit.click();

		Set<Cookie> cookieSet = driver.manage().getCookies();
		driver.close();
		for (Cookie cookie : cookieSet) {
			sb.append(new StringBuilder().append(cookie.getName()).append("=")
					.append(cookie.getValue()).append(";").toString());
		}
		String result = sb.toString();
		if (result.contains("gsid_CTandWM")) {
			return result;
		}
		throw new Exception("weibo login failed");
	}

	// 不使用代理获取cookie
	public static String getSinaCookie(String username, String password)
			throws Exception {
		StringBuilder sb = new StringBuilder();

		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.setJavascriptEnabled(true);
		driver.get("http://login.weibo.cn/login/");

		WebElement mobile = driver
				.findElementByCssSelector("input[name=mobile]");
		mobile.sendKeys(new CharSequence[] { username });
		WebElement pass = driver
				.findElementByCssSelector("input[name^=password]");
		pass.sendKeys(new CharSequence[] { password });
		WebElement rem = driver
				.findElementByCssSelector("input[name=remember]");
		rem.click();
		WebElement submit = driver
				.findElementByCssSelector("input[name=submit]");
		submit.click();

		Set<Cookie> cookieSet = driver.manage().getCookies();
		driver.close();
		for (Cookie cookie : cookieSet) {
			sb.append(new StringBuilder().append(cookie.getName()).append("=")
					.append(cookie.getValue()).append(";").toString());
		}
		String result = sb.toString();
		if (result.contains("gsid_CTandWM")) {
			return result;
		}
		throw new Exception("weibo login failed");
	}

	public static void main(String[] args) {
		String cookie = null;
		try {
			// 获取新浪微博的cookie，账号密码以明文形式传输，请使用小号
			cookie = WeiboCN.getSinaCookie("微博用户名", "微博密码");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(cookie);
	}
}
