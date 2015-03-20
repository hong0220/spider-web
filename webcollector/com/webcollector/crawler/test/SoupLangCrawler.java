package com.webcollector.crawler.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.webcollector.crawler.DeepCrawler;
import com.webcollector.crawler.test.SoupLangCrawler;
import com.webcollector.model.Links;
import com.webcollector.model.Page;
import com.webcollector.souplang.Context;
import com.webcollector.souplang.SoupLang;
import com.webcollector.util.JDBCHelper;
import com.webcollector.util.RegexRule;

/**
 * SoupLang是WebCollector 2.x中的一种爬虫脚本，以Jsoup内置的CSS SELECTOR为基础
 * 程序会将SoupLang的脚本(xml)转换成语义树，所以不用担心配置文件会影响网页抽取的速度。
 * SoupLang除了有Jsoup选择元素、元素属性的功能外，还可以做正则匹配、写数据库等操作 使用SoupLang，可以将多种异构页面的抽取业务统一管理
 * 
 * 本例子使用SoupLang，同时对知乎网站的用户信息、提问信息进行抽取，并同时提交到两张数据表 (用户信息表、提问信息表)。
 */
public class SoupLangCrawler extends DeepCrawler {

	public SoupLang soupLang;
	RegexRule regexRule = new RegexRule();

	public SoupLangCrawler(String crawlPath)
			throws ParserConfigurationException, SAXException, IOException {
		super(crawlPath);
		addSeed("http://www.zhihu.com/");
		regexRule.addRule("http://www.zhihu.com/question/[0-9]+");
		regexRule.addRule("http://www.zhihu.com/people/.+");
		regexRule.addRule("-http://www.zhihu.com/people/.+/.*");
		regexRule.addRule("-.*(jpg|png|gif|#|\\?).*");

		// soupLang可以从文件、InputStream中读取SoupLang写的抽取脚本 如果从外部文件读取，soupLang=new
		// SoupLang("文件路径")
		soupLang = new SoupLang(
				ClassLoader.getSystemResourceAsStream("example/DemoRule1.xml"));

	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		// soupLang.extract的返回值是一个Context类型的对象,对象中存储了SoupLang所有包含name属性的元素，可以通过Context.get()或者Context.getString()等方法获取

		Context context = soupLang.extract(page.getDoc());
		System.out.println(context);

		// 返回链接，递归爬取
		Links nextLinks = new Links();
		// 满足正则
		nextLinks.addAllFromDocument(page.getDoc(), regexRule);
		return nextLinks;
	}

	public static void main(String[] args) throws Exception {
		try {
			// 用JDBCHelper在JDBCTemplate池中建立一个名为temp1的JDBCTemplate
			JDBCHelper
					.createMysqlTemplate(
							"temp1",
							"jdbc:mysql://localhost/testdb?useUnicode=true&characterEncoding=utf8",
							"root", "mysql", 5, 30);

			JDBCHelper.getJdbcTemplate("temp1").execute(
					"CREATE TABLE IF NOT EXISTS tb_zhihu_question ("
							+ "id int(11) NOT NULL AUTO_INCREMENT,"
							+ "title text,content longtext,"
							+ "PRIMARY KEY (id)"
							+ ") ENGINE=MyISAM DEFAULT CHARSET=utf8;");
			System.out.println("成功创建数据表 tb_zhihu_question");

			JDBCHelper.getJdbcTemplate("temp1").execute(
					"CREATE TABLE IF NOT EXISTS tb_zhihu_user ("
							+ "id int(11) NOT NULL AUTO_INCREMENT,"
							+ "user varchar(30),url text," + "PRIMARY KEY (id)"
							+ ") ENGINE=MyISAM DEFAULT CHARSET=utf8;");
			System.out.println("成功创建数据表 tb_zhihu_question");
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!");
			return;
		}

		SoupLangCrawler crawler = new SoupLangCrawler("data/souplang");
		crawler.start(5);
	}
}
