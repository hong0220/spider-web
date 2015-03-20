package com.spider.utils.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spider.utils.RegexUtil;

public class TestRexUtil {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStringFilter() {
		// String str =
		// "*adCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*.中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？";
		String str = "[]";
		System.out.println(str);
		System.out.println(RegexUtil.strFilter(str));
	}

	@Test
	public void testParseTime() {
		String str1 = "zhidao.baidu.com/link?... 2013年1月10日   - 百度快照 - 80%好评";
		String str2 = "zhidao.baidu.com/link?... 2013年11月1日   - 百度快照 - 80%好评";
		String str3 = "zhidao.baidu.com/link?... 2013年11月11日   - 百度快照 - 80%好评";
		String str4 = "zhidao.baidu.com/link?... 2013-11-1   - 百度快照 - 80%好评";
		String str5 = "zhidao.baidu.com/link?... 2013-1-10   - 百度快照 - 80%好评";
		String str6 = "zhidao.baidu.com/link?... 2013-11-10   - 百度快照 - 80%好评";
		String str7 = "zhidao.baidu.com/link?... 2013-11-10 11:11:11   - 百度快照 - 80%好评";
		String str8 = "zhidao.baidu.com/link?... 2013-11-10 11:11   - 百度快照 - 80%好评";
		System.out.println(RegexUtil.parseTime(str1));
		System.out.println(RegexUtil.parseTime(str2));
		System.out.println(RegexUtil.parseTime(str3));
		System.out.println(RegexUtil.parseTime(str4));
		System.out.println(RegexUtil.parseTime(str5));
		System.out.println(RegexUtil.parseTime(str6));
		System.out.println(RegexUtil.parseTime(str7));
		System.out.println(RegexUtil.parseTime(str8));
	}

	@Test
	public void testGetNumber() {
		System.out
				.println(RegexUtil
						.getNumber("正则如何取这白牛的id啊~正则不会啊。。itemId=36191701467&soldTotalNum"));
	}

	@Test
	public void testGetTimeNumber() {
		System.out.println(RegexUtil.getTimeNumber(
				"编程中国 - www.bccn.net/ - 21小时前  - 快照  - 预览", "小时前"));
		System.out.println(RegexUtil.getTimeNumber(
				"编程中国 - www.bccn.net/ - 1小时前  - 快照  - 预览", "小时前"));
		System.out.println(RegexUtil.getTimeNumber(
				"编程中国 - www.bccn.net/ - 1天前  - 快照  - 预览", "天前"));
		System.out.println(RegexUtil.getTimeNumber(
				"编程中国 - www.bccn.net/ - 21天前  - 快照  - 预览", "天前"));
		System.out.println(RegexUtil.getTimeNumber(
				"编程中国 - www.bccn.net/ - 1分钟前  - 快照  - 预览", "分钟前"));
		System.out.println(RegexUtil.getTimeNumber(
				"编程中国 - www.bccn.net/ - 11分钟前  - 快照  - 预览", "分钟前"));
	}
}
