package com.spider.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间转化工具类
 */
public class DateTimeUtil {
	private static String TIME_PATTERN = "HH:mm:ss";// 定义标准时间格式
	private static String DATE_PATTERN_1 = "yyyy/MM/dd";// 定义标准日期格式1
	private static String DATE_PATTERN_2 = "yyyy-MM-dd";// 定义标准日期格式2
	private static String DATE_PATTERN_3 = "yyyy/MM/dd HH:mm:ss";// 定义标准日期格式3，带有时间
	private static String DATE_PATTERN_4 = "yyyy/MM/dd HH:mm:ss E";// 定义标准日期格式4，带有时间和星期
	private static String DATE_PATTERN_5 = "yyyy年MM月dd日 HH:mm:ss E";// 定义标准日期格式5，带有时间和星期
	// 定义时间类型常量
	public final static int SECOND = 1;
	public final static int MINUTE = 2;
	public final static int HOUR = 3;
	public final static int DAY = 4;

	// 把一个日期对象，按照某种格式模型,格式化输出,返回字符串类型
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	// 将时间字符串,按照某种格式,转换为Date类型
	public static Date formatStr(String str, String pattern)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date time = null;
		// 需要捕获ParseException异常，如不要捕获，可以直接抛出异常，或者抛出到上层
		time = sdf.parse(str);
		return time;
	}

	// 得到一个日期函数的格式化时间
	public static String getTimeByDate(Date date) {
		return formatDate(date, TIME_PATTERN);
	}

	// 获取当前的时间的格式化时间
	public static String getNowTime() {
		return formatDate(new Date(), TIME_PATTERN);
	}

	// 只输出一个时间中的月份
	public static int getMonth() {
		// 获得一个Calendar实例相当于Date date=new Date()
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}

	// 只输出一个时间中的年份
	public static int getYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 将一个表示时间段的数转换为毫秒数
	 * 
	 * @param num
	 *            时间差数值,支持小数
	 * @param type
	 *            时间类型：1->秒,2->分钟,3->小时,4->天
	 * @return long类型时间差毫秒数，当为-1时表示参数有错
	 */
	public static long formatToTimeMillis(double num, int type) {
		if (num <= 0)
			return 0;
		switch (type) {
		case SECOND:
			return (long) (num * 1000);
		case MINUTE:
			return (long) (num * 60 * 1000);
		case HOUR:
			return (long) (num * 60 * 60 * 1000);
		case DAY:
			return (long) (num * 24 * 60 * 60 * 1000);
		default:
			return -1;
		}
	}

	/**
	 * 获取某一指定时间的前一段时间
	 * 
	 * @param num
	 *            时间差数值
	 * @param type
	 *            时间差类型：1->秒,2->分钟,3->小时,4->天
	 * @param date
	 *            参考时间
	 * @return 返回格式化时间字符串
	 */
	public static String getPreTimeStr(double num, int type, Date date) {
		long nowLong = date.getTime();// 将参考日期转换为毫秒时间
		Date time = new Date(nowLong - formatToTimeMillis(num, type));// 减去时间差毫秒数
		return getTimeByDate(time);
	}

	/**
	 * 获取某一指定时间的前一段时间
	 * 
	 * @param num
	 *            时间差数值
	 * @param type
	 *            时间差类型：1->秒,2->分钟,3->小时,4->天
	 * @param date
	 *            参考时间
	 * @return 返回Date对象
	 */
	public static Date getPreTime(double num, int type, Date date) {
		long nowLong = date.getTime();// 将参考日期转换为毫秒时间
		Date time = new Date(nowLong - formatToTimeMillis(num, type));// 减去时间差毫秒数
		return time;
	}

	/**
	 * 获取某一指定时间的后一段时间
	 * 
	 * @param num
	 *            时间差数值
	 * @param type
	 *            时间差类型：1->秒,2->分钟,3->小时,4->天
	 * @param date
	 *            参考时间
	 * @return 返回格式化时间字符串
	 */
	public static String getNextTimeStr(double num, int type, Date date) {
		long nowLong = date.getTime();// 将参考日期转换为毫秒时间
		Date time = new Date(nowLong + formatToTimeMillis(num, type));// 加上时间差毫秒数
		return getTimeByDate(time);
	}

	/**
	 * 获取某一指定时间的后一段时间
	 * 
	 * @param num
	 *            时间差数值
	 * @param type
	 *            时间差类型：1->秒,2->分钟,3->小时,4->天
	 * @param date
	 *            参考时间
	 * @return 返回Date对象
	 */
	public static Date getNextTime(double num, int type, Date date) {
		long nowLong = date.getTime();// 将参考日期转换为毫秒时间
		Date time = new Date(nowLong + formatToTimeMillis(num, type));// 加上时间差毫秒数
		return time;
	}

	// 得到前几个月的现在 利用GregorianCalendar类的set方法来实现
	public static Date getPreMonthTime(int num, Date date) {
		GregorianCalendar gregorianCal = new GregorianCalendar();
		gregorianCal
				.set(Calendar.MONTH, gregorianCal.get(Calendar.MONTH) - num);
		return gregorianCal.getTime();
	}

	// 得到后几个月的现在时间 利用GregorianCalendar类的set方法来实现
	public static Date getNextMonthTime(int num, Date date) {
		GregorianCalendar gregorianCal = new GregorianCalendar();
		gregorianCal
				.set(Calendar.MONTH, gregorianCal.get(Calendar.MONTH) + num);
		return gregorianCal.getTime();
	}

	public static void main(String[] args) throws ParseException {
		// "35分钟前";
		System.out.println(getPreTimeStr(35, MINUTE, new Date()));

		// "2小时前";
		System.out.println(getPreTimeStr(2, HOUR, new Date()));

		// 今天 15:48
		String str = "今天 15:48";
		System.out.println(formatDate(new Date(), DATE_PATTERN_2) + " "
				+ str.split(" ")[1].trim() + ":00");

		// 12月12日
		System.out.println((getYear() + "-" + formatDate(
				formatStr("12月12日", "MM月dd日"), "MM-dd")));

		// 2012年3月18日
		System.out.println(formatDate(formatStr("2012年3月18日", "yyyy年MM月dd日"),
				DATE_PATTERN_2));

		System.out.println(formatDate(getPreMonthTime(2, new Date()),
				DATE_PATTERN_2));

		System.out.println(formatDate(getNextMonthTime(2, new Date()),
				DATE_PATTERN_2));
	}
}
