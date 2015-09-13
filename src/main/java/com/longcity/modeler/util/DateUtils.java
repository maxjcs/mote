package com.longcity.modeler.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author oldlu
 */
public class DateUtils {
	public static final long ONE_MINUTE = 60 * 1000;
	public static final long ONE_HOUR = ONE_MINUTE * 60;
	public static final long EIGHT_ONE_HOUR = ONE_MINUTE * 60 * 8;
	public static final long ONE_DAY = ONE_HOUR * 24;
	public static final long ONE_MONTH = ONE_DAY * 30;
	public static final long ONE_YEAR = ONE_DAY * 365;
	public static final long SUNDAY_OF_WEEK = 1;
	public static String[] WEEK_DAY = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
	
	static SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
	
	public static String getWeekOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return WEEK_DAY[w];
	}

	public static long differDay(Date date1, Date date2) {
		return (date2.getTime() + EIGHT_ONE_HOUR) / ONE_DAY - (date1.getTime() + EIGHT_ONE_HOUR) / ONE_DAY;
	}

	private static boolean isSameWeek(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		return cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 对话页面日期格式化
	 * 
	 * @param date1
	 * @return
	 * 
	 */
	public static String formatDate1(Date date1) {
		if (date1 == null) {
			return "";
		}
		Date date2 = new Date();

		long day = differDay(date1, date2);

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		if (day == 0) {
			return cal1.get(Calendar.HOUR_OF_DAY) + ":" + cal1.get(Calendar.MINUTE);
		}
		if (day == 1) {
			return "昨天 " + cal1.get(Calendar.HOUR_OF_DAY) + ":" + cal1.get(Calendar.MINUTE);
		}
		if (day < 7) {
			if (isSameWeek(date1, date2) && cal1.get(Calendar.DAY_OF_WEEK) != SUNDAY_OF_WEEK) {
				return getWeekOfDate(date1) + " " + cal1.get(Calendar.HOUR_OF_DAY) + ":" + cal1.get(Calendar.MINUTE);
			}
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return format.format(date1);
	}

	/**
	 * 对话页面日期格式化
	 * 
	 * @param date1
	 * @return
	 * 
	 */
	public static String formatDate2(Date date1) {
		if (date1 == null) {
			return "";
		}
		Date date2 = new Date();

		long day = differDay(date1, date2);

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		if (day == 0) {
			return cal1.get(Calendar.HOUR_OF_DAY) + ":" + cal1.get(Calendar.MINUTE);
		}
		if (day == 1) {
			return "昨天";
		}
		if (day < 7) {
			if (isSameWeek(date1, date2) && cal1.get(Calendar.DAY_OF_WEEK) != SUNDAY_OF_WEEK) {
				return getWeekOfDate(date1);
			}
		}

		SimpleDateFormat format = new SimpleDateFormat("HH/mm/ss(yy/MM/dd)");
		return format.format(date1);
	}

	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public static String formatList(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		long day = differDay(date, new Date());
		if (day == 0) {
			SimpleDateFormat format = new SimpleDateFormat("H:mm");
			return format.format(date);
		}
		if (day == 1) {
			return "昨天 ";
		}

		if (calendar.get(Calendar.YEAR) == getYear()) {
			SimpleDateFormat format = new SimpleDateFormat("M月d日");
			return format.format(date);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d");
		return format.format(date);
	}
	
	/**
	 *  字符串转日期
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String date) {
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			
		}
		return null;
	}

	public static String formatDetail(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		Date now = new Date();
		long ago = now.getTime() - date.getTime();
		if (ago < ONE_MINUTE) {
			return "刚刚";
		}

		if (ago < ONE_HOUR) {
			return ago / ONE_MINUTE + "分钟前";
		}

		long day = differDay(date, now);
		if (day == 0) {
			return ago / ONE_HOUR + "小时前";
		}
		if (day == 1) {
			SimpleDateFormat format = new SimpleDateFormat("H:mm");

			return "昨天 " + format.format(date);
		}

		if (calendar.get(Calendar.YEAR) == getYear()) {
			SimpleDateFormat format = new SimpleDateFormat("M月d日 H:mm");
			return format.format(date);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d HH:mm");
		return format.format(date);
	}

	public static void main(String[] args) throws ParseException {
		for (int i = 0; i < 1000; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(new Date().getTime() - i * 2 * ONE_HOUR);
			Date d = calendar.getTime();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(formatList(d) + "                  " + simple.format(d));
		}
	}

	public static String formatList(long createTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(createTime);

		return formatList(calendar.getTime());
	}
}