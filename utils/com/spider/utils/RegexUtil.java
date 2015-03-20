package com.spider.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexUtil {
	// windows文件夹命名
	public static String fileNameFilter(String str) {
		String regEx = "[\\\\/:*?\"<>|]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	// 过滤特殊字符
	public static String strFilter(String str) {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";

		// 清除掉所有特殊字符
		// String regEx =
		// "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		// String regEx = "[💞]";

		// String regEx = "^[^a-zA-Z0-9_\u4e00-\u9fa5]*$";

		String regEx = "[^a-zA-Z0-9_\u4e00-\u9fa5`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static void main(String[] args) {
		String content = "北师大蒋挺 转发了 周鸿祎 的微博:【转发好搜厕所秘笈，送出3台360智能摄像机】内急的厉害，却找不到厕所怎么办？用好搜啊！怎么搜？看秘笈：http://t.cn/RZj1VuW 转发此微博，并@ 1位你认为经常找厕所的好友，就有机会获得价值149元的360智能摄像机！ [组图共2张]  原图 赞[275] 原文转发[1173] 原文评论[608] 转发理由:感觉这么多互联网公司周鸿祎是最实在的，论人品没道理干不过马化腾啊！//@周鸿祎: 恭喜@首都小城管, @张哲瀚melvin, @程阿喵喵喵喵喵 三位小伙伴获得360智能摄像机，公证地址： http://t.cn/RZHZWHh  赞[0] 转发[0] 评论[0] 收藏 6分钟前 来自微博 weibo.com";
		content = content.substring(
				content.indexOf("转发理由:") + "转发理由:".length(),
				content.lastIndexOf("赞["));
		System.out.println(content);

	}

	// 过滤出日期或者时间
	public static String parseTime(String str) {
		if (str == null || "".equals(str.trim())) {
			return "";
		}
		if (str.contains("分钟前")) {
			String min = getTimeNumber(str, "分钟前");
			return DateTimeUtil.formatDate(new Date(), "yyyy-MM-dd")
					+ " "
					+ DateTimeUtil.getPreTimeStr(Integer.valueOf(min),
							DateTimeUtil.MINUTE, new Date());
		} else if (str.contains("小时前")) {
			String hour = getTimeNumber(str, "小时前");
			return DateTimeUtil.formatDate(new Date(), "yyyy-MM-dd")
					+ " "
					+ DateTimeUtil.getPreTimeStr(Integer.valueOf(hour),
							DateTimeUtil.HOUR, new Date());
		} else if (str.contains("天前")) {
			String day = getTimeNumber(str, "天前");
			return DateTimeUtil.formatDate(new Date(), "yyyy-MM-dd")
					+ " "
					+ DateTimeUtil.getPreTimeStr(Integer.valueOf(day),
							DateTimeUtil.DAY, new Date());
		}

		// "(\\d{1,4}[-|\\/]\\d{1,2}[-|\\/]\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2})"
		List matches = null;
		Pattern p = Pattern
				.compile(
						"(\\d{1,4}[-|\\/年]\\d{1,2}[-|\\/月]\\d{1,2}[日]*[\\s]*[[\\d{1,2}:\\d{1,2}:\\d{1,2}][\\d{1,2}:\\d{1,2}]]*)",
						Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher matcher = p.matcher(str);
		if (matcher.find() && matcher.groupCount() >= 1) {
			matches = new ArrayList();
			for (int i = 1; i <= matcher.groupCount(); i++) {
				String temp = matcher.group(i);
				matches.add(temp);
			}
		} else {
			matches = Collections.EMPTY_LIST;
		}

		if (matches.size() > 0) {
			String time = ((String) matches.get(0)).trim();
			return time;
		} else {
			return "";
		}
		// if (matches.size() > 0) {
		// String time = ((String) matches.get(0)).trim();
		// // 格式正确不需要转化
		// if (!time.contains("年") && time.length() == 10) {
		// return time;
		// }
		// // 格式不正确要转换
		// if (time.contains("年")) {
		// String year = time.substring(0, time.indexOf("年"));
		// String month = time.substring(time.indexOf("年") + 1,
		// time.indexOf("月"));
		// String day = time.substring(time.indexOf("月") + 1,
		// time.indexOf("日"));
		// if (month.length() != 2) {
		// month = "0" + month;
		// }
		// if (day.length() != 2) {
		// day = "0" + day;
		// }
		// time = year + "-" + month + "-" + day;
		// } else if (time.contains("-")) {
		// String year = time.substring(0, time.indexOf("-"));
		// time = time.substring(time.indexOf("-") + 1);
		// String month = time.substring(0, time.indexOf("-"));
		// time = time.substring(time.indexOf("-"));
		// String day = time.substring(time.indexOf("-") + 1);
		// if (month.length() != 2) {
		// month = "0" + month;
		// }
		// if (day.length() != 2) {
		// day = "0" + day;
		// }
		// time = year + "-" + month + "-" + day;
		// }
		// return time;
		// } else {
		// return "";
		// }
	}

	public static String getTimeNumber(String str, String flag) {
		List matches = null;
		Pattern p = Pattern.compile("(\\d+(?=(" + flag + ")))",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher matcher = p.matcher(str);
		if (matcher.find() && matcher.groupCount() >= 1) {
			matches = new ArrayList();
			for (int i = 1; i <= matcher.groupCount(); i++) {
				String temp = matcher.group(i);
				matches.add(temp);
			}
		} else {
			matches = Collections.EMPTY_LIST;
		}
		if (matches.size() > 0) {
			return ((String) matches.get(0)).trim();
		} else {
			return "";
		}
	}

	@Test
	public void testMULTILINE() {
		String candidate = "This is a sentence.\nSo is this..";
		String regex = "^.";
		// 表明要输入多行，他们有自己的终止字符
		Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
		Matcher m = p.matcher(candidate);
		String val = null;
		System.out.println("INPUT: " + candidate);
		System.out.println("REGEX: " + regex + "\r\n");
		while (m.find()) {
			val = m.group();
			System.out.println("MATCH: " + val);
		}
		if (val == null) {
			System.out.println("NO MATCHES: ");
		}
	}

	// 逆序肯定环视，表示所在位置左侧能够匹配Expression
	public static String getNumber(String str) {
		// "(\\d{1,4}[-|\\/]\\d{1,2}[-|\\/]\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2})"
		List matches = null;
		Pattern p = Pattern.compile("((?<=(itemId=))\\d+)",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher matcher = p.matcher(str);
		if (matcher.find() && matcher.groupCount() >= 1) {
			matches = new ArrayList();
			for (int i = 1; i <= matcher.groupCount(); i++) {
				String temp = matcher.group(i);
				matches.add(temp);
			}
		} else {
			matches = Collections.EMPTY_LIST;
		}
		if (matches.size() > 0) {
			return ((String) matches.get(0)).trim();
		} else {
			return "";
		}
	}

	@Test
	public void test() {
		// String str = "<div class=\'enum\'>"
		// + "<span style=\'width:120px;display:inline-block;\'>.text</span>"
		// + "<span style=\'width:120px;display:inline-block;\'>4096</span>"
		// + "<span style=\'width:100px;display:inline-block;\'>23130</span>"
		// + "<span style=\'width:100px;display:inline-block;\'>23552</span>"
		// +
		// "<span style=\'width:80px;display:inline-block;\'>asdfasdfasdf asdfasdfasdf</span>"
		// + "<span>asdfasdfasdf	" + "0bc2ffd32265a08d72b795b18265828d"
		// + "</span>" + "</div>";
		// String reg = "(?<=<span>)[^<]+(?=</span>)";
		// Matcher m = Pattern.compile(reg).matcher(str);
		// while (m.find()) {
		// System.out.println("hello");
		// System.out.println(m.group());
		// }
	}

	@Test
	public void test1() {
		// 验证输入由字母、数字和下划线组成，下划线不能出现在开始或结束位置
		// ^(?!_)[a-zA-Z0-9_]+(?<!_)$
		// ^[a-zA-Z0-9]([a-zA-Z0-9_]*[a-zA-Z0-9])?$
	}

	@Test
	public void test2() {
		// 取得div标签的内容，不包括div标签本身
		String test = "<div>a test</div>";
		String reg = "(?<=<div>)[^<]+(?=</div>)";
		Matcher m = Pattern.compile(reg).matcher(test);
		while (m.find()) {
			System.out.println(m.group());
		}
	}

	@Test
	public void test3() {
		String test = "cba";
		String reg = "(?<=(c?b))a";
		Matcher m = Pattern.compile(reg).matcher(test);
		while (m.find()) {
			System.out.println(m.group());
			System.out.println(m.group(1));
		}
	}

	@Test
	public void test4() {
		String test = "dcba";
		String reg = "(?<=(dc?b))a";
		Matcher m = Pattern.compile(reg).matcher(test);
		while (m.find()) {
			System.out.println(m.group());
			System.out.println(m.group(1));
		}
	}
}
