package com.wenge.datagroup.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 *  时间工具类 java8
 * @author 张璐
 * @date 2020/8/19
 */
public class DateTimeUtil {
	private static final String YEAR = "year";
	private static final String MONTH = "month";
	private static final String WEEK = "week";
	private static final String DAY = "day";
	private static final String HOUR = "hour";
	private static final String MINUTE = "minute";
	private static final String SECOND = "second";

	// 星期元素
	private static final String MONDAY = "MONDAY";// 星期一
	private static final String TUESDAY = "TUESDAY";// 星期二
	private static final String WEDNESDAY = "WEDNESDAY";// 星期三
	private static final String THURSDAY = "THURSDAY";// 星期四
	private static final String FRIDAY = "FRIDAY";// 星期五
	private static final String SATURDAY = "SATURDAY";// 星期六
	private static final String SUNDAY = "SUNDAY";// 星期日

	// 根据指定格式显示日期和时间
	/** yyyy-MM-dd */
	private static final DateTimeFormatter yyyyMMdd_EN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	/** yyyy-MM-dd HH */
	private static final DateTimeFormatter yyyyMMddHH_EN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
	/** yyyy-MM-dd HH:mm */
	private static final DateTimeFormatter yyyyMMddHHmm_EN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	/** yyyy-MM-dd HH:mm:ss */
	private static final DateTimeFormatter yyyyMMddHHmmss_EN = DateTimeFormatter.ofPattern(DateTimeUtil.DEFAULT_PATTERN);
	/** HH:mm:ss */
	private static final DateTimeFormatter HHmmss_EN = DateTimeFormatter.ofPattern("HH:mm:ss");
	/** yyyy年MM月dd日 */
	private static final DateTimeFormatter yyyyMMdd_CN = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
	/** yyyy年MM月dd日HH时 */
	private static final DateTimeFormatter yyyyMMddHH_CN = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时");
	/** yyyy年MM月dd日HH时mm分 */
	private static final DateTimeFormatter yyyyMMddHHmm_CN = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分");
	/** yyyy年MM月dd日HH时mm分ss秒 */
	private static final DateTimeFormatter yyyyMMddHHmmss_CN = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分ss秒");

	/** HH时mm分ss秒 */
	private static final DateTimeFormatter HHmmss_CN = DateTimeFormatter.ofPattern("HH时mm分ss秒");

	// 本地时间显示格式：区分中文和外文显示
	private static final DateTimeFormatter shotDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
	private static final DateTimeFormatter fullDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
	private static final DateTimeFormatter longDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
	private static final DateTimeFormatter mediumDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
	/**
	 * 获取当前日期
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getNowDate_EN() {
		return String.valueOf(LocalDate.now());
	}

	/**
	 * 获取当前日期
	 *
	 * @return 字符串yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowTime_EN() {
		return LocalDateTime.now().format(yyyyMMddHHmmss_EN);
	}
	public static String getNowTime_EN_yMd() {
		return LocalDateTime.now().format(yyyyMMdd_EN);
	}

	/** 获取当前时间（yyyy-MM-dd HH） */
	public static String getNowTime_EN_yMdH() {
		return LocalDateTime.now().format(yyyyMMddHH_EN);
	}

	/** 获取当前时间（yyyy年MM月dd日） */
	public static String getNowTime_CN_yMdH() {
		return LocalDateTime.now().format(yyyyMMddHH_CN);
	}

	/** 获取当前时间（yyyy-MM-dd HH:mm） */
	public static String getNowTime_EN_yMdHm() {
		return LocalDateTime.now().format(yyyyMMddHHmm_EN);
	}

	/** 获取当前时间（yyyy年MM月dd日HH时mm分） */
	public static String getNowTime_CN_yMdHm() {
		return LocalDateTime.now().format(yyyyMMddHHmm_CN);
	}

	/** 获取当前时间（HH时mm分ss秒） */
	public static String getNowTime_CN_HHmmss() {
		return LocalDateTime.now().format(HHmmss_CN);
	}
	/**
	 * 默认的时间日期样式
	 */
	public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 系统默认的时区
	 */
	// private static final ZoneId ZONE_ID= ZoneId.systemDefault();


	/**
	 * 中国时区
	 */
	private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");


	/**
	 * 按照默认的模板将时间戳转换为时间日期的字符串形式
	 * 
	 * @param epochSecond
	 *            时间戳 1525767228
	 * @return 返回时间日期的字符串形式 2018-05-08 16:13:48
	 */
	public static String DefaultFormatEpochSecond(long epochSecond) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSecond),
				ZoneId.systemDefault());
		return localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
	}


	/**
	 * 按照给定的时间日期模版，将时间戳转换成字符串形式
	 * 
	 * @param pattern
	 *            模版，例如"yyyy-MM-dd HH:mm:ss"
	 * @param epochSecond
	 *            时间戳 1525767228
	 * @return 转换后的字符串 2018-05-08 16:13:48
	 */


	public static String formatEpochSecond(String pattern, long epochSecond) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSecond),
				ZoneId.systemDefault());
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
	}


	/**
	 * 按照给定的时间日期模版，将时间戳转换成字符串形式
	 * 
	 * @param dateTimeFormatter
	 *            模版
	 * @param epochSecond
	 *            时间戳
	 * @return 转换后的字符串
	 */
	public static String formatEpochSecond(DateTimeFormatter dateTimeFormatter, long epochSecond) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSecond),
				ZoneId.systemDefault());
		return localDateTime.format(dateTimeFormatter);
	}


	/**
	 * 将一种时间日期字符串转换成另外一种形式
	 * 
	 * @param oldPattern
	 *            旧的时间日期字符串样式 "yyyy-MM-dd HH:mm:ss"
	 * @param oldDateTime
	 *            旧的时间日期字符串 2018-05-08 16:13:48
	 * @param newPattern
	 *            新的时间日期字符串样式 "MM-dd"
	 * @return 转换后的字符串 05-08
	 */
	public static String parseStrToNewStr(String oldPattern, String oldDateTime, String newPattern) {
		LocalDateTime localDateTime = LocalDateTime.parse(oldDateTime, DateTimeFormatter.ofPattern(oldPattern));
		return localDateTime.format(DateTimeFormatter.ofPattern(newPattern));
	}


	/**
	 * 将一种时间日期字符串转换成另外一种形式
	 * 
	 * @param oldDateTimeFormatter
	 *            旧的时间日期字符串样式
	 * @param oldDateTime
	 *            旧的时间日期字符串
	 * @param newDateTimeFormatter
	 *            新的时间日期字符串样
	 * @return 转换后的字符串
	 */
	public static String parseStrToNewStr(DateTimeFormatter oldDateTimeFormatter, String oldDateTime,
			DateTimeFormatter newDateTimeFormatter) {
		LocalDateTime localDateTime = LocalDateTime.parse(oldDateTime, oldDateTimeFormatter);
		return localDateTime.format(newDateTimeFormatter);
	}


	/**
	 * 将给定的时间日期字符串按照指定的模版解析成时间戳
	 * 
	 * @param pattern
	 *            模版，例如"yyyy-MM-dd HH:mm:ss"
	 * @param dateTime
	 *            时间日期字符串 2018-05-08 16:13:48
	 * @return 时间戳 1525767228000
	 */
	public static long parseDateTime(String pattern, String dateTime) {
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
		Instant instant = localDateTime.atZone(ZONE_ID).toInstant();
		return instant.toEpochMilli();
	}


	/**
	 * 将给定的时间日期字符串按照指定的模版解析成时间戳
	 * 
	 * @param dateTimeFormatter
	 *            模版
	 * @param dateTime
	 *            时间日期字符串
	 * @return 时间戳
	 */
	public static long parseDateTime(DateTimeFormatter dateTimeFormatter, String dateTime) {
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
		Instant instant = localDateTime.atZone(ZONE_ID).toInstant();
		return instant.toEpochMilli();
	}


	/**
	 * 将给定的时间日期字符串按照默认的模版解析成时间戳 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param dateTime
	 *            2018-05-08 16:13:48
	 * @return 时间戳 1525767228000
	 */
	public static long DefaultParseDateTime(String dateTime) {
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
		Instant instant = localDateTime.atZone(ZONE_ID).toInstant();
		return instant.toEpochMilli();
	}


	/**
	 * 按照给定的格式获取昨天这个时候的时间日期字符串
	 * 
	 * @param dateTimeFormatter
	 *            时间日期格式
	 * @return
	 */
	public static String yesterdayStr(DateTimeFormatter dateTimeFormatter) {
		LocalDateTime localDateTime = LocalDateTime.now().plusDays(-1);
		return localDateTime.format(dateTimeFormatter);
	}


	/**
	 * 按照给定的格式获取昨天这个时候的时间日期字符串
	 * 
	 * @param pattern
	 *            时间日期格式
	 * @return
	 */
	public static String yesterdayStr(String pattern) {
		LocalDateTime localDateTime = LocalDateTime.now().plusDays(-1);
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
	}


	/**
	 * 按照给定的格式获取当前时间日期字符串
	 * 
	 * @param pattern
	 *            时间日期格式
	 * @return
	 */
	public static String todayStr(String pattern) {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
	}


	/**
	 * 按照给定的格式获取当前时间日期字符串
	 * 
	 * @param dateTimeFormatter
	 *            时间日期格式
	 * @return
	 */
	public static String todayStr(DateTimeFormatter dateTimeFormatter) {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.format(dateTimeFormatter);
	}


	/**
	 * 获取昨天这个时间的时间戳
	 * 
	 * @return 时间戳
	 */
	public static long yesterday() {
		LocalDateTime localDateTime = LocalDateTime.now().plusDays(-1);
		return localDateTime.atZone(ZONE_ID).toInstant().toEpochMilli();
	}

	/**
	 * 获取下个月第一天的时间戳
	 *
	 * @return 时间戳
	 */
	public static long nextMonths() {
		LocalDateTime of = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDate.now().getMonth(), 1, 0, 0).plusMonths(1);
		return of.atZone(ZONE_ID).toInstant().toEpochMilli();
	}

	public static long months(String dateString) {
		LocalDateTime of = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDate.now().getMonth(), 1, 0, 0).plusMonths(1);
		return of.atZone(ZONE_ID).toInstant().toEpochMilli();
	}

	public static LocalDateTime nextMonthsLocalDate(String dateString) {
		LocalDateTime local = LocalDateTime.parse(dateString, yyyyMMddHHmmss_EN);
		LocalDateTime of = LocalDateTime.of(local.getYear(), local.getMonth(), 1, 0, 0).plusMonths(1);
		return of;
	}

	public static LocalDateTime zeroLocalDate(String dateString) {
		LocalDateTime local = LocalDateTime.parse(dateString, yyyyMMddHHmmss_EN);
		LocalDateTime of = LocalDateTime.of(local.getYear(),local.getMonth(), local.getDayOfMonth(), 0, 0);
		return of;
	}

	public static void main(String[] args) {
		String format = LocalDateTime.now().format(yyyyMMddHHmmss_EN);
		LocalDateTime thisMonth = zeroLocalDate(format);
		LocalDateTime nextMoth = nextMonthsLocalDate(format);
		while (thisMonth.compareTo(nextMoth) < 0) {
			System.out.println(thisMonth.format(yyyyMMddHHmmss_EN));
			thisMonth = thisMonth.plusDays(1);
		}
	}
}