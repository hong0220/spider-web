package com.webcollector.crawler.demo;

import java.net.URLEncoder;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

import com.gargoylesoftware.htmlunit.BrowserVersion;

/**
 * 如果爬虫需要抽取Javascript生成的数据，可以使用HtmlUnitDriver HtmlUnitDriver可以用page.getDriver来生成
 */
public class JSCrawler extends DeepCrawler {

	public JSCrawler(String crawlPath) {
		super(crawlPath);
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		// HtmlUnitDriver可以抽取JS生成的数据
		HtmlUnitDriver driver = page.getDriver(BrowserVersion.CHROME);
		// HtmlUnitDriver也可以像Jsoup一样用CSS SELECTOR抽取数据.
		// 关于HtmlUnitDriver的文档请查阅selenium相关文档
		List<WebElement> divInfos = driver
				.findElementsByCssSelector("h3>a[id^=uigs]");
		for (WebElement divInfo : divInfos) {
			System.out.println(divInfo.getText());
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		JSCrawler crawler = new JSCrawler("js");
		for (int page = 1; page <= 5; page++)
			crawler.addSeed("http://www.sogou.com/web?query="
					+ URLEncoder.encode("编程") + "&page=" + page);
		crawler.start(1);
	}
}
