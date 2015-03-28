package com.spider.crawler.js;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.hibernate.dao.IpagentDao;
import com.hibernate.model.Ipagent;

/**
 * 抓取代理ip和port
 * 
 * js加载的数据使用HtmlUnit包解析
 */
public class KuaiDaiLiJS extends DeepCrawler {

	public KuaiDaiLiJS(String crawlPath) throws Exception {
		super(crawlPath);
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		System.out.println("开始解析");
		HtmlUnitDriver driver = page.getDriver(BrowserVersion.CHROME);
		System.out.println("解析完成");
		// List<WebElement> ips = driver.findElements(By
		// .xpath("//*[@id='ip_list']/tbody/tr/td[3]"));
		// List<WebElement> ports = driver.findElements(By
		// .xpath("//*[@id='ip_list']/tbody/tr/td[4]"));
		// for (int i = 0; i < ips.size(); ++i) {
		// WebElement ip = ips.get(i);
		// WebElement port = ports.get(i);
		// System.out.println("---------");
		// System.out.println(ip.getText());
		// System.out.println(port.getText());
		// System.out.println("---------");
		// }

		List<WebElement> ips = driver
				.findElementsByCssSelector("tr > td:nth-child(1)");
		List<WebElement> ports = driver
				.findElementsByCssSelector("tr > td:nth-child(2)");
		IpagentDao id = new IpagentDao();
		for (int i = 0; i < ips.size(); ++i) {
			WebElement ip = ips.get(i);
			WebElement port = ports.get(i);
			System.out.println("---------");
			System.out.println(ip.getText());
			System.out.println(port.getText());
			id.save(new Ipagent(ip.getText(), Integer.valueOf(port.getText()),
					""));
			System.out.println("---------");
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		KuaiDaiLiJS crawler = new KuaiDaiLiJS("log/js2");
		for (int i = 1; i <= 1; ++i) {
			crawler.addSeed("http://www.kuaidaili.com/proxylist/" + i);
		}
		// crawler.addSeed("http://www.xici.net.co/nt/");
		// crawler.addSeed("http://www.xici.net.co/wn/");
		// crawler.addSeed("http://www.xici.net.co/wt/");
		// crawler.addSeed("http://www.xici.net.co/qq/");
		// Proxys proxys = new Proxys();
		// proxys.add("119.226.246.89", 8080);
		// crawler.setProxys(proxys);
		crawler.setThreads(1);
		crawler.start(1);
	}
}
