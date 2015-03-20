//package com.spider.net.main;
//
//import java.util.List;
//
//import com.spider.crawler.news.NewsCrawler;
//import com.spider.net.model.vo.SpiderConfig;
//import com.spider.net.util.NetUtil;
//import com.webcollector.crawler.Crawler;
//
//public class SystemControl implements Runnable {
//	private Crawler crawler;
//	private int depth;
//
//	public SystemControl(Crawler crawler, int depth) {
//		this.crawler = crawler;
//		this.depth = depth;
//	}
//
//	@Override
//	public void run() {
//		try {
//			crawler.start(depth);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//		Control con = NetUtil.getDataByLogin();
//
//		List<SpiderConfig> newsConfigs = con.getSpiderConfig();
//		System.out.println(newsConfigs.size());
//		for (SpiderConfig newsConfig : newsConfigs) {
//			System.out.println(newsConfig.getHttp().replace(".", "")
//					.replace("/", "").replace(":", "").replace("\\", "")
//					.replace("?", ""));
//			NewsCrawler nc = new NewsCrawler("log/nc");
//			nc.addSeed(newsConfig.getHttp());
//			nc.setNewsConfig(newsConfig);
//			// 设置代理服务器
//			// Proxys proxys = new Proxys();
//			// proxys.add("202.101.96.154", 8888);
//			// nc.setProxys(proxys);
//			Thread thread = new Thread(new SystemControl(nc,
//					newsConfig.getDepth()));
//			thread.start();
//		}
//
//		// List<BbsConfig> bbsConfigs = con.getBbsConfigs();
//		// System.out.println(bbsConfigs.size());
//		// for (BbsConfig bbsConfig : bbsConfigs) {
//		// if (bbsConfig.getStatus() == 1) {
//		// System.out.println(bbsConfig);
//		// BBSCrawler bc = new BBSCrawler("log/" + bbsConfig.getSiteFile());
//		// // 添加种子
//		// bc.addSeed(bbsConfig.getHttp());
//		// // 设置配置文件参数
//		// bc.setBbsConfig(bbsConfig);
//		// // 设置线程数
//		// // bc.setThreads(bbsConfig.getThread());
//		// // 设置线程睡眠时间
//		// bc.setSleep(1000);
//		// // 设置代理服务器
//		// Proxys proxys = new Proxys();
//		// proxys.add("202.101.96.154", 8888);
//		// bc.setProxys(proxys);
//		//
//		// // 开启线程
//		// Thread bbsThread = new Thread(new SystemControl(bc,
//		// bbsConfig.getDepth()));
//		// bbsThread.start();
//		// }
//		// }
//	}
//}
//// public static void readConfig() {
//// // 数据少
//// // 百度百家站点
//// // BaiJiaCrawler bjc = new BaiJiaCrawler();
//// // bjc.addSeed("http://baijia.baidu.com/");
//// // bjc.addRegex("http://baijia.baidu.com/.*");
//// // Thread bjcThread = new Thread(new SystemControl(bjc, 5));
//// // bjcThread.start();
////
//// // 腾讯网站点
//// // TencentCrawler tc = new TencentCrawler();
//// // tc.addSeed("http://news.qq.com/");
//// // tc.addRegex("http://news.qq.com/.*");
//// // tc.setResumable(true);
//// // // tc.setThreads(5);
//// // Thread tcThread = new Thread(new SystemControl(tc, 5));
//// // tcThread.start();
////
//// // // 凤凰网站点
//// // FengHuangCrawler fhc = new FengHuangCrawler();
//// // fhc.addSeed("http://news.ifeng.com/");
//// // fhc.addRegex("http://news.ifeng.com.*");
//// // // fhc.addRegex("-.*#.*");
//// // // fhc.setThreads(1);
//// // fhc.setIsContentStored(false);
//// // Thread fhcThread = new Thread(new SystemControl(fhc, 5));
//// // fhcThread.start();
//// //
//// // // 新浪网站点
//// // SinaCrawler sc = new SinaCrawler();
//// // sc.addSeed("http://news.sina.com.cn/");
//// // sc.addRegex("http://news.sina.com.cn/.*");
//// // sc.setResumable(true);
//// // sc.setThreads(1);
//// // Thread scThread = new Thread(new SystemControl(sc, 5));
//// // scThread.start();
//// //
//// // // 搜狐网站点
//// // SoHuCrawler shc = new SoHuCrawler();
//// // shc.addSeed("http://news.sohu.com/");
//// // shc.addRegex("http://news.sohu.com/.*");
//// // shc.setResumable(true);
//// // // shc.setThreads(5);
//// // Thread shcThread = new Thread(new SystemControl(shc, 5));
//// // shcThread.start();
////
//// // 网易网站点
//// // WangYiCrawler wyc = new WangYiCrawler();
//// // wyc.addSeed("http://news.sohu.com/");
//// // wyc.addRegex("http://news.sohu.com/.*");
//// // wyc.setResumable(true);
//// // // xhc.setThreads(5);
//// // Thread wycThread = new Thread(new SystemControl(wyc, 3));
//// // wycThread.start();
////
//// // // 新华网站点
//// // XinHuaCrawler xhc = new XinHuaCrawler();
//// // xhc.addSeed("http://www.xinhuanet.com/");
//// // xhc.addRegex("http://.*xinhuanet.com/.*");
//// // xhc.setResumable(true);
//// // // xhc.setThreads(1);
//// // Thread xhcThread = new Thread(new SystemControl(xhc, 5));
//// // xhcThread.start();
//// //
//// // // 人民网站点
//// // PeopleCrawler pc = new PeopleCrawler();
//// // pc.addSeed("http://www.people.com.cn/");
//// // pc.addRegex("http://.*people.com.cn/.*");
//// // pc.setResumable(true);
//// // pc.setThreads(5);
//// // Thread pcThread = new Thread(new SystemControl(pc, 3));
//// // pcThread.start();
//// List<XmlConfig> list = JdomUtil.getXmlConfig();
//// for (XmlConfig xmlConfig : list) {
//// NewsCrawler nc = new NewsCrawler("nc");
//// nc.addSeed(xmlConfig.getLinkUrl());
//// nc.setXmlConfig(xmlConfig);
//// // 设置代理服务器
//// Proxys proxys = new Proxys();
//// proxys.add("202.101.96.154", 8888);
//// nc.setProxys(proxys);
////
//// Thread gcThread = new Thread(new SystemControl(nc,
//// xmlConfig.getCrawlDepth()));
//// gcThread.start();
//// }
// // }
