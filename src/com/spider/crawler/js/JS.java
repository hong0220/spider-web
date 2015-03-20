//package com.spider.crawler.js;
//
//import java.util.List;
//
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
//
//import com.gargoylesoftware.htmlunit.BrowserVersion;
//import com.webcollector.crawler.DeepCrawler;
//import com.webcollector.model.Links;
//import com.webcollector.model.Page;
//
///**
// * 代码有问题
// */
//public class JS extends DeepCrawler {
//
//	public JS(String crawlPath) throws Exception {
//		super(crawlPath);
//	}
//
//	@Override
//	public Links visitAndGetNextLinks(Page page) {
//		System.out.println("开始解析");
//		HtmlUnitDriver driver = page.getDriver(BrowserVersion.CHROME,
//				"119.226.246.89", 8080);
//		System.out.println("解析完成");
//		// List<WebElement> ips = driver.findElements(By
//		// .xpath("//*[@id='ip_list']/tbody/tr/td[3]"));
//		// List<WebElement> ports = driver.findElements(By
//		// .xpath("//*[@id='ip_list']/tbody/tr/td[4]"));
//		// for (int i = 0; i < ips.size(); ++i) {
//		// WebElement ip = ips.get(i);
//		// WebElement port = ports.get(i);
//		// System.out.println("---------");
//		// System.out.println(ip.getText());
//		// System.out.println(port.getText());
//		// System.out.println("---------");
//		// }
//
//		List<WebElement> ips = driver
//				.findElementsByCssSelector("tr > td:nth-child(3)");
//		List<WebElement> ports = driver
//				.findElementsByCssSelector("tr > td:nth-child(4)");
//		for (int i = 0; i < ips.size(); ++i) {
//			WebElement ip = ips.get(i);
//			WebElement port = ports.get(i);
//			System.out.println("---------");
//			System.out.println(ip.getText());
//			System.out.println(port.getText());
//			System.out.println("---------");
//		}
//		return null;
//	}
//
//	public static void main(String[] args) throws Exception {
//		JS crawler = new JS("log/js");
//		for (int i = 1; i <= 1; ++i) {
//			crawler.addSeed("http://www.xici.net.co/nn");
//		}
//		// crawler.addSeed("http://www.xici.net.co/nt/");
//		// crawler.addSeed("http://www.xici.net.co/wn/");
//		// crawler.addSeed("http://www.xici.net.co/wt/");
//		// crawler.addSeed("http://www.xici.net.co/qq/");
//		// Proxys proxys = new Proxys();
//		// proxys.add("119.226.246.89", 8080);
//		// crawler.setProxys(proxys);
//		crawler.start(1);
//	}
// }
