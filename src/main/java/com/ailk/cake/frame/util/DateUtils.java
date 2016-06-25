/* 
 * Copyright 2006 AsiaInfo Holdings, Inc
 * All right reserved.
 * Created on Aug 21, 2006
 */
package com.ailk.cake.frame.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 处理时间的工具类
 * 
 * @author plh
 */
public class DateUtils {
	/**
	 * 获取形如yyyyMMddHHmmss的当前日期字串
	 * 
	 * @return String
	 */
	public static String getDateString() {
		// 日期格式
		DateFormat dfmt = new SimpleDateFormat("yyyyMMddHHmmss");
		return dfmt.format(new Date());
	}

	public static String getYYYYMMDDHH24MISS(String strYYYY_MM_DD) {
		String tmp = "";
		try {
			Date d = getDate(strYYYY_MM_DD, "yyyy-mm-dd");
			DateFormat dfmt = new SimpleDateFormat("yyyyMMddHHmmss");
			return dfmt.format(d);
		} catch (ParseException e) {
			;
		}

		return tmp;
	}

	/**
	 * 获取指定格式的当前日期字串
	 * 
	 * @return String
	 */
	public static String getDateString(String pattern) {
		// 日期格式
		DateFormat dfmt = new SimpleDateFormat(pattern);
		return dfmt.format(new Date());
	}

	/**
	 * 获取指定格式的当前月的第一天
	 * 
	 * @return String
	 */
	public static String getDateStringFirst(String pattern) {
		// 日期格式
		DateFormat dfmt = new SimpleDateFormat(pattern);
		String dates = dfmt.format(new Date());
		return dates.substring(0, 8) + "01";
	}

	/**
	 * 日期运算
	 * 
	 * @param days
	 *            和当前日期的差值（单位:天）
	 * @param pattern
	 * @return
	 */
	public static String getDateString(int days, String pattern) {
		DateFormat dfmt = new SimpleDateFormat(pattern);
		long days2 = (long) days;
		return dfmt.format(new java.util.Date((new Date()).getTime() + 1000
				* 60 * 60 * 24 * days2));
	}


	/**
	 * 获取形如yyyyMMddHHmmss的日期字串
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String getDateString(Date date) {
		if (date == null) {
			return "";
		}
		DateFormat vdfmt1 = new SimpleDateFormat("yyyyMMddHHmmss");
		return vdfmt1.format(date);
	}

	public static String getDateString(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		DateFormat vdfmt1 = new SimpleDateFormat(pattern);
		return vdfmt1.format(date);
	}


	/**
	 * 将形如yyyyMMddHHmmss的字串转换成日期
	 * 
	 * @param strDate
	 *            String
	 * @return Date
	 * @throws ParseException
	 */
	public static Date getDate(String yyyyMMddHHmmss) throws ParseException {
		// 日期格式
		DateFormat dfmt = new SimpleDateFormat("yyyyMMddHHmmss");
		return dfmt.parse(yyyyMMddHHmmss);
	}

	/**
	 * 添加时间方法
	 * 
	 * @param 输入的时间
	 * @param 与输入时间相差的天数
	 * @return Date
	 */
	@SuppressWarnings("deprecation")
	public static Date appendDate(Date time, int n) throws ParseException {

		DateFormat mediumDateFormat = DateFormat.getDateTimeInstance();
		Date time1 = mediumDateFormat.parse(mediumDateFormat.format(time));
		time1.setDate(time1.getDate() + n);
		return time1;
	}

	/**
	 * 添加时间方法 String类型
	 * 
	 * @param 输入的时间
	 * @param 与输入时间相差的天数
	 * @param 日期的格式
	 * @return Date
	 */
	@SuppressWarnings("deprecation")
	public static String appendDate(String beginvalue, int n, String pattern)
			throws ParseException {

		DateFormat mediumDateFormat = DateFormat.getDateTimeInstance();
		Date time = getDate(beginvalue, pattern);
		Date time1 = mediumDateFormat.parse(mediumDateFormat.format(time));
		time1.setDate(time1.getDate() + n);
		return getDateString(time1, pattern);
	}

	/**
	 * 将指定格式的字串转换成日期
	 * 
	 * @param strDate
	 *            String
	 * @return Date
	 * @throws ParseException
	 */
	public static Date getDate(String strDate, String pattern)
			throws ParseException {
		// 日期格式
		DateFormat dfmt = new SimpleDateFormat(pattern);
		return dfmt.parse(strDate);
	}
	

	/**
	 * 讲时间转换为(2007-08-30 00：00：00)的方法
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static Date conversionTime(Date d) throws ParseException {
		DateFormat mediumDateFormat = DateFormat.getDateTimeInstance();
		Date d1 = mediumDateFormat.parse(mediumDateFormat.format(d));
		return d1;
	}

	/**
	 * 将指定格式的字串(20061111163558)转换成指定格式的字串(2006-11-11 16:35:58)
	 * 
	 * @param strDate
	 *            String
	 * @return Date
	 * @throws ParseException
	 */
	public static String getStringDate(String stringdate) {
		if (stringdate == null)
			return null;

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat formatter2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String dateString = "";
		try {
			Date date = formatter1.parse(stringdate);
			dateString = formatter2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString;
	}

	public static String getStringDate(String stringdate, String fpattern,
			String tpattern) {
		if (stringdate == null)
			return null;

		SimpleDateFormat formatter1 = new SimpleDateFormat(fpattern);
		SimpleDateFormat formatter2 = new SimpleDateFormat(tpattern);
		String dateString = "";
		try {
			Date date = formatter1.parse(stringdate.trim());
			dateString = formatter2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString;
	}

	/**
	 * 得到当前月的后month_num个月的帐期 例如当前为2005-09，返回上个月的帐期，则设置month_num为-1,返回为200508
	 * 例如当前为2005-09，返回下个月的帐期，则设置month_num为1,返回为200510
	 * 例如当前为2006-1，返回上个月的帐期，则设置month_num为1,返回为200512
	 */
	public static String getChdate(int month_num) {
		Calendar c1 = Calendar.getInstance();
		String result = "";
		c1.add(2, month_num);
		result = String.valueOf(c1.get(1));
		if ((c1.get(2) + 1) >= 10) {
			result = result + String.valueOf(c1.get(2) + 1);
		} else {
			result = result + "0" + String.valueOf(c1.get(2) + 1);
		}
		return result;
	}

	public static int getSysYear() {
		Calendar calendar = new GregorianCalendar();
		int iyear = calendar.get(Calendar.YEAR);
		return iyear;
	}

	public static String getMothsString(int moths, String pattern) {

		Calendar cal = Calendar.getInstance(); // 取得今天的时间
		cal.add(Calendar.MONTH, moths); // 算出moths个月以前的
		cal.get(Calendar.YEAR); // moths个月以前的年
		cal.get(Calendar.MONTH); // moths个月以前的月
		cal.get(Calendar.DATE);

		DateFormat dfmt = new SimpleDateFormat(pattern);

		Date dateaftersixmonth = cal.getTime();

		return dfmt.format(dateaftersixmonth);

	}
	
	/**
	 * 与输入的年月日相差moths的年月日
	 * @param moths
	 * @param pattern
	 * @param dataStr 2012-12-03
	 * @return
	 * @throws ParseException 
	 */
	public static String getMothsString(int moths, String dataStr,String pattern) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(dataStr,pattern));
		cal.add(Calendar.MONTH,moths); // moths个月以前的月

		DateFormat dfmt = new SimpleDateFormat(pattern);

		Date dateaftersixmonth = cal.getTime();
		return dfmt.format(dateaftersixmonth);
	}

	public static int getSysMonth() {
		Calendar calendar = new GregorianCalendar();
		int imonth = calendar.get(Calendar.MONTH) + 1;
		return imonth;
	}

	public static int getSysDay() {
		Calendar calendar = new GregorianCalendar();
		int idate = calendar.get(Calendar.DAY_OF_MONTH);
		return idate;
	}

	public static String getDateString2() {
		String tmp = "";
		tmp = getSysYear() + "    " + getSysMonth() + "    " + getSysDay()
				+ "    ";
		return tmp;
	}

	public static int getTwoMonthNum(String startDate, String endDate) {
		int year1 = Integer.parseInt(startDate.substring(0, 4));
		int year2 = Integer.parseInt(endDate.substring(0, 4));
		int month1 = Integer.parseInt(startDate.substring(4, 6));
		int month2 = Integer.parseInt(endDate.substring(4, 6));
		return Math.abs(year1 - year2) * 12 - (month1 - month2) + 1;
	}

	public static int isBetweenDays(String startDay, String endDay) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String today = formatter.format(date);
		startDay = today.substring(0, 6) + startDay;
		endDay = today.substring(0, 6) + endDay;
		if (today.compareTo(startDay) >= 0 && today.compareTo(endDay) <= 0) {
			return 0;
		} else {
			return 1;
		}
	}


	public static boolean isDateStr(String strDate, String pattern) {
		boolean tmp = true;

		try {
			getDate(strDate, pattern);
		} catch (ParseException e) {
			tmp = false;
		}

		return tmp;
	}

	public static String getLastMonth() {
		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		System.out.println(year);
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month == 1) {
			year = year - 1;
			month = month + 11;
		}
		month = month - 1;

		return year + "/" + month;

	}

	public static String getLastMonthByPattern() {
		Calendar calendar = Calendar.getInstance();
		String lastMonth = "";
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month == 1) {
			year = year - 1;
			month = month + 11;
		}
		month = month - 1;
		if (month < 10) {
			lastMonth = year + "0" + month;

		} else {
			lastMonth = year + "" + month;
		}
		return lastMonth;

	}

	/**
	 * 获取指定格式的当前月的最后一天
	 * 
	 * @return String 日期格式
	 * @author qinfj
	 */
	public static String getLastDayOfMonth(String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		DateFormat dfmt = new SimpleDateFormat(pattern);
		return dfmt.format(calendar.getTime());
	}

	/**
	 * 获取指定格式的当前月的第一天
	 * 
	 * @return String
	 * @author qinfj
	 */
	public static String getFirstDayOfMonth(String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		DateFormat dfmt = new SimpleDateFormat(pattern);
		return dfmt.format(calendar.getTime());
	}

	public static void main(String[] args) throws Exception {
	     System.out.println(DateUtils.getDateString("yyyy-MM-dd  HH:mm:ss"));
	}

}
