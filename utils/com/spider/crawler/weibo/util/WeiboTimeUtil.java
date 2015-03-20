package com.spider.crawler.weibo.util;

import java.text.ParseException;
import java.util.Date;

import com.spider.utils.DateTimeUtil;

public class WeiboTimeUtil {
	public static Date handleTime(String time) {
		if (time.contains("今天")) {
			// System.out.println(time);
			// System.out.println(time.split(" ")[1].substring(0,
			// 5));
			time = DateTimeUtil.formatDate(new Date(), "yyyy-MM-dd") + " "
					+ time.split(" ")[1].trim().substring(0, 5) + ":00";
		} else if (time.contains("分钟前")) {
			int min = Integer.valueOf(time.substring(0, time.indexOf("分钟前")));
			time = DateTimeUtil.formatDate(new Date(), "yyyy-MM-dd") + " "
					+ DateTimeUtil.getPreTimeStr(min, 2, new Date());
		} else if (time.contains("月")) {
			try {
				time = DateTimeUtil.getYear()
						+ "-"
						+ DateTimeUtil
								.formatDate(
										DateTimeUtil.formatStr(time, "MM月dd日"),
										"MM-dd") + " 00:00:00";
			} catch (ParseException e) {
				System.out.println("时间解析错误：" + time);
				e.printStackTrace();
			}
		} else {
			time = time.split(" ")[0].trim() + " "
					+ time.split(" ")[1].trim().substring(0, 8).trim();
		}
		System.out.println(time);
		try {
			return DateTimeUtil.formatStr(time, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
