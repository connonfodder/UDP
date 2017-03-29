package com.aadhk.product.util;

import org.acra.ACRA;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarUtil {

	private static final String TAG = "CalendarUtil";

	public static String[] getStartEndDate(int periodType, int amount) {
		return getStartEndDate(null, periodType, amount);
	}

	public static String[] getStartEndDate(String startDate, int periodType, int amount) {
		String result[] = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		Calendar calendar = Calendar.getInstance();

		if (startDate != null) {
			try {
				Date date = sdf.parse(startDate);
				calendar.setTime(date);
			} catch (Exception e) {
				ACRA.getErrorReporter().handleException(e);
				e.printStackTrace();
			}
		}

		if (BaseConstant.PEROID_YEAR == periodType) {
			calendar.add(Calendar.YEAR, amount);
			result[0] = calendar.get(Calendar.YEAR) + "-01-01";
			result[1] = calendar.get(Calendar.YEAR) + "-12-31";
		} else if (BaseConstant.PEROID_MONTH == periodType) {
			calendar.add(Calendar.MONTH, amount);
			int monthInt = (calendar.get(Calendar.MONTH) + 1);
			String month = padZero(monthInt);
			result[0] = calendar.get(Calendar.YEAR) + "-" + month + "-01";
			result[1] = calendar.get(Calendar.YEAR) + "-" + month + "-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		} else if (BaseConstant.PEROID_DAY == periodType) {
			result[0] = result[1] = getDate(amount);
		}

		return result;
	}

	public static String[] getWeekStartEndDate(int amount, int firstDayOfWeek) {
		return getWeekStartEndDate(null, amount, firstDayOfWeek);
	}

	public static String[] getWeekStartEndDate(String startDate, int amount, int firstDayOfWeek) {
		String result[] = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		if (startDate != null) {
			try {
				Date date = sdf.parse(startDate);
				calendar.setTime(date);
			} catch (Exception e) {
				ACRA.getErrorReporter().handleException(e);
				e.printStackTrace();
			}
		}

		calendar.add(Calendar.DATE, amount * 7);

		// start date
		while (firstDayOfWeek != calendar.get(Calendar.DAY_OF_WEEK)) {
			calendar.add(Calendar.DATE, -1);
		}
		int startDayInt = calendar.get(Calendar.DAY_OF_MONTH);
		String startDay = padZero(startDayInt);
		int startMonthInt = (calendar.get(Calendar.MONTH) + 1);
		String startMonth = padZero(startMonthInt);

		result[0] = calendar.get(Calendar.YEAR) + "-" + startMonth + "-" + startDay;

		// end date
		calendar.add(Calendar.DATE, 6);
		int endDayInt = calendar.get(Calendar.DAY_OF_MONTH);
		String endDay = padZero(endDayInt);
		int endMonthInt = (calendar.get(Calendar.MONTH) + 1);
		String endMonth = padZero(endMonthInt);

		result[1] = calendar.get(Calendar.YEAR) + "-" + endMonth + "-" + endDay;

		return result;
	}

	public static String[] getCustomDate(String fromDateValue, String toDateValue, int amount) {
		String result[] = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		//Log.i(TAG, "===fromDateValue:"+fromDateValue+", toDateValue:"+toDateValue+", amount:"+amount);

		int days = 0;
		if (fromDateValue != null) {
			try {
				calendar.setTime(sdf.parse(fromDateValue));
				days = (int) ((sdf.parse(toDateValue).getTime() - sdf.parse(fromDateValue).getTime()) / (1000 * 60 * 60 * 24));
				calendar.add(Calendar.DATE, amount * (days + 1));
			} catch (Exception e) {
				ACRA.getErrorReporter().handleException(e);
				e.printStackTrace();
			}
		}

		//Log.i(TAG, "fromDateValue:"+fromDateValue+", days:"+days+", amount:"+amount);

		// start date
		int startDayInt = calendar.get(Calendar.DAY_OF_MONTH);
		String startDay = padZero(startDayInt);
		int startMonthInt = (calendar.get(Calendar.MONTH) + 1);
		String startMonth = padZero(startMonthInt);

		result[0] = calendar.get(Calendar.YEAR) + "-" + startMonth + "-" + startDay;

		// end date
		calendar.add(Calendar.DATE, days);

		int endDayInt = calendar.get(Calendar.DAY_OF_MONTH);
		String endDay = padZero(endDayInt);
		int endMonthInt = (calendar.get(Calendar.MONTH) + 1);
		String endMonth = padZero(endMonthInt);

		result[1] = calendar.get(Calendar.YEAR) + "-" + endMonth + "-" + endDay;

		return result;
	}

	public static String getCurrentDateForFileName() {
		Calendar calendar = Calendar.getInstance();
		int monthInt = (calendar.get(Calendar.MONTH) + 1);
		String month = padZero(monthInt);
		return calendar.get(Calendar.YEAR) + "_" + month + "_" + calendar.get(Calendar.DATE);

	}

	public static String getCurrentTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME_SECOND, Locale.US);
		return dateFormat.format(new Date());
	}


	public static String getCurrentTime(String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		return dateFormat.format(new Date());
	}

	public static int getYear(int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, amount);
		return calendar.get(Calendar.YEAR);
	}

	public static String getMonth(String value) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		Calendar cal = Calendar.getInstance();
		Date date = sdf.parse(value);
		cal.setTime(date);
		int startMonthInt = (cal.get(Calendar.MONTH) + 1);
		return padZero(startMonthInt);
	}

	public static int getMonthInt(String value) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		Calendar cal = Calendar.getInstance();
		try {
			Date date = sdf.parse(value);
			cal.setTime(date);
		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return cal.get(Calendar.MONTH);
	}

	public static String getDate(int dayAmount) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, dayAmount);
		SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		return dateFormat.format(calendar.getTime());
	}

	public static String getNextDay(String value) {
		DateFormat format = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			Date dd = format.parse(value);
			calendar.setTime(dd);
			calendar.add(Calendar.DATE, 1);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return format.format(calendar.getTime());
	}

	public static String getPrevDay(String value) {
		DateFormat format = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			Date dd = format.parse(value);
			calendar.setTime(dd);
			calendar.add(Calendar.DATE, -1);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return format.format(calendar.getTime());
	}

	public static String getDateByMonth(int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -amount);
		int monthInt = (calendar.get(Calendar.MONTH) + 1);
		String month = padZero(monthInt);
		int dayInt = (calendar.get(Calendar.DATE));
		String day = padZero(dayInt);
		return calendar.get(Calendar.YEAR) + "-" + month + "-" + day + " 00:00";
	}

	public static String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
		return dateFormat.format(new Date());
	}

	public static int getDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		return dateFormat.format(new Date());
	}

	public static int getHour(String value) {
		String result = value.substring(0, value.indexOf(':'));
		return Integer.parseInt(result);
	}

	public static String getTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstant.TIME_FORMAT_24, Locale.US);
		return dateFormat.format(new Date());
	}

	public static String getTimeSecond() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstant.TIME_FORMAT_24_SECOND, Locale.US);
		return dateFormat.format(new Date());
	}

	public static String displayTime(String time, String timeFormat) {
		String result = time;
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.TIME_FORMAT_24, Locale.US);
		try {
			Date date = sdf.parse(time);
			sdf = new SimpleDateFormat(timeFormat, LocaleInstance.getLocale());
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	public static String displayTime(String dateTime) {
		String result = dateTime;
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
		try {
			Date date = sdf.parse(dateTime);
			sdf = new SimpleDateFormat(BaseConstant.TIME_FORMAT_24, LocaleInstance.getLocale());
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	public static String displayDate(String dateTime) {
		String result = dateTime;
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
		try {
			Date date = sdf.parse(dateTime);
			sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, LocaleInstance.getLocale());
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	public static String displayTimeSecond(String time, String timeFormat) {
		String result = time;
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.TIME_FORMAT_24_SECOND, Locale.US);
		try {
			Date date = sdf.parse(time);
//			Log.d("jack", " locale = " + LocaleInstance.getLocale());
			sdf = new SimpleDateFormat(timeFormat, LocaleInstance.getLocale());
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	public static String displayDay(String dateValue) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		String result = dateValue;
		try {
			Date date = sdf.parse(dateValue);
			sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_MONTH_DAY_YEAR);
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	public static String displayMonth(String dateValue) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		String result = dateValue;
		try {
			Date date = sdf.parse(dateValue);
			sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_MONTH_YEAR);
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	public static String displayYear(String dateValue) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		String result = dateValue;
		try {
			Date date = sdf.parse(dateValue);
			sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_YEAR);
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	public static String displayDateTime(String dateTimeValue) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
		String result = dateTimeValue;
		try {
			Date date = sdf.parse(dateTimeValue);
			sdf = new SimpleDateFormat(BaseConstant.TIME_FORMAT_NO_YEAR);
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	public static String displayDateTime(String dateTimeValue, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
		String result = dateTimeValue;
		try {
			Date date = sdf.parse(dateTimeValue);
			sdf = new SimpleDateFormat(dateFormat + " " + BaseConstant.TIME_FORMAT_24);
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	public static String displayDateTime24(String dateTimeValue, String dateTimeFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
		String result = dateTimeValue;
		try {
			Date date = sdf.parse(dateTimeValue);
			sdf = new SimpleDateFormat(dateTimeFormat );
			result = sdf.format(date);
		} catch (ParseException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return result;
	}

	public static String displayDate(String dateValue, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
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

	public static Calendar getCalendarByDay(String value) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME_SECOND, Locale.US);
		Calendar cal = Calendar.getInstance();
		Date date = sdf.parse(value + " 00:00:00");
		cal.setTime(date);
		return cal;
	}

	public static Calendar getCalendarByTime(String value) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
		Calendar cal = Calendar.getInstance();
		Date date = sdf.parse("2011-09-07 " + value);
		cal.setTime(date);
		return cal;
	}

	public static int[] getDays(String startDate, String startTime, String endDate, String endTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
		Date fromDate = sdf.parse(startDate + " " + startTime);
		Date toDate = sdf.parse(endDate + " " + endTime);
		int days = (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
		int hr = (int) ((toDate.getTime() - fromDate.getTime()) % (1000 * 60 * 60 * 24) / (1000 * 60 * 60));
		return new int[] { days, hr };
	}

	public static int getDays(String startDate, String endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
		Date fromDate = sdf.parse(startDate);
		Date toDate = sdf.parse(endDate);
		int days = (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
		return days;
	}

	private static String padZero(int value) {
		String result = "";

		if (value < 10 && value > 0) {
			result = "0" + value;
		} else {
			result = value + "";
		}
		return result;
	}

	public static boolean isAfterTime(String startTime, String endTime) {
		boolean result = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.TIME_FORMAT_24, Locale.US);
			Date fromDate = sdf.parse(startTime);
			Date toDate = sdf.parse(endTime);
			result = fromDate.compareTo(toDate) >= 0;
		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}

		return result;
	}

	public static boolean isBeforeToday(String sorucedate) {
		boolean result = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
			Date today = new Date();
			Date date = sdf.parse(sorucedate);
			result = today.compareTo(date) <= 0;
		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}

		return result;
	}

	//include equal
	public static boolean isBetweenCurrent(String beforeDate, String endDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
			Date current = new Date();
			Date min = sdf.parse(beforeDate);
			Date max = sdf.parse(endDate);
			return current.after(min) && current.before(max) || current.equals(min) || current.equals(max);
		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return false;
	}

	//include equal
	public static boolean isBetweenCurrentTime(String beforeTime, String endTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			Date current = sdf.parse(sdf.format(new Date()));
			Date min = sdf.parse(beforeTime);
			Date max = sdf.parse(endTime);
			return current.after(min) && current.before(max) || current.equals(min) || current.equals(max);

		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isBeforeCurrentTime(String beforeDate, String beforeTime) {
		boolean result = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
			Date currentTime = new Date();
			Date date = sdf.parse(beforeDate + " " + beforeTime);
			result = date.compareTo(currentTime) < 0;
		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}

		return result;
	}

	public static boolean isAfterCurrentTime(String beforeDate, String beforeTime) {
		boolean result = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
			Date today = new Date();
			Date date = sdf.parse(beforeDate + " " + beforeTime);
			result = date.compareTo(today) > 0;
		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}

		return result;
	}

	public static boolean isBefore(String beforeDate, String afterDate) {
		boolean result = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE, Locale.US);
			Date fromDate = sdf.parse(beforeDate);
			Date toDate = sdf.parse(afterDate);

			result = fromDate.compareTo(toDate) <= 0;
		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}

		return result;
	}

	public static boolean isBefore(String beforeDate, String beforeTime, String afterDate, String afterTime) {
		boolean result = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
			Date fromDate = sdf.parse(beforeDate + " " + beforeTime);
			Date toDate = sdf.parse(afterDate + " " + afterTime);

			result = fromDate.compareTo(toDate) <= 0;
		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}

		return result;
	}

	public static long getMillis(String date, String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		try {
			Date dateTime = sdf.parse(date + " " + time);
			cal.setTime(dateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cal.getTimeInMillis();
	}

	public static double getDiffHours(String startDate, String startTime, String endDate, String endTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
		Date fromDate = sdf.parse(startDate + " " + startTime);
		Date toDate = sdf.parse(endDate + " " + endTime);
		return (toDate.getTime() - fromDate.getTime()) / (1000.0 * 60 * 60);
	}

	public static double getDiffHours(String startDateTime, String endDateTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
		Date fromDate = sdf.parse(startDateTime);
		Date toDate = sdf.parse(endDateTime);
		return (double)(toDate.getTime() - fromDate.getTime()) / (1000.0 * 60 * 60);
	}

	public static int getDiffMinutes(String startDateTime, String endDateTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_TIME, Locale.US);
		Date fromDate = sdf.parse(startDateTime);
		Date toDate = sdf.parse(endDateTime);
		return (int) (toDate.getTime() - fromDate.getTime()) / (1000 * 60);
	}

	public static String getDateTimeWithoutFormat() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(BaseConstant.PREF_DEF_DATE_YYYYMMDD, Locale.US);
		return dateFormat.format(new Date());
	}

	public static synchronized long uniqueId() {
		return System.currentTimeMillis();
	}

}
