package com.aadhk.kds.util;

import com.aadhk.product.util.BaseConstant;
import com.aadhk.product.util.CalendarUtil;

import org.acra.ACRA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class POSCalendarUtil extends CalendarUtil {

	private static final String TAG = "CalendarUtil";

	public static String displayDateTime(String dateTimeValue, String dateFormat, String timeFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME);
		String result = dateTimeValue;
		if (result != null) {
			try {
				Date date = sdf.parse(dateTimeValue);
				sdf = new SimpleDateFormat(dateFormat + " " + timeFormat);
				result = sdf.format(date);
			} catch (ParseException e) {
				ACRA.getErrorReporter().handleException(e);
				e.printStackTrace();
			}
		} else {
			result = "";
		}
		return result;
	}

	public static String displayTimeByDate(String time, String timeFormat) {
		String result = time;
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME);
		try {
			Date date = sdf.parse(time);
			sdf = new SimpleDateFormat(timeFormat);
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	public static String displayTime(String time, String timeFormat) {
		String result = time;
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.TIME_FORMAT_24_SECOND);
		try {
			Date date = sdf.parse(time);
			sdf = new SimpleDateFormat(timeFormat);
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	public static String displayDate(String dateValue, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE);
		String result = dateValue;
		try {
			Date date = sdf.parse(dateValue);
			sdf = new SimpleDateFormat(dateFormat);
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取分钟
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static long getMins(String startTime, String endTime) {
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long min = 0;
		// 获得两个时间的毫秒时间差异
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			diff = sdf.parse(endTime).getTime() - sdf.parse(startTime).getTime();
			min = diff / nm;// 计算差多少天
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return min;
	}
}
