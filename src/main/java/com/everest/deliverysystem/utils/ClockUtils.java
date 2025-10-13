package com.everest.deliverysystem.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClockUtils {
	
	public static final String GENERAL_DATE_FORMAT = "yyyy-MM-dd HH:mm";
	
	public static String getCurrentTimeString() {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(GENERAL_DATE_FORMAT);
		return formatter.format(now.getTime());
	}
	
	public static String getCurrentTimeOffsetString(int minutesTOAdd) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutesTOAdd);
		
		SimpleDateFormat formatter = new SimpleDateFormat(GENERAL_DATE_FORMAT);
		return formatter.format(now.getTime());
	}
	
	public static java.sql.Timestamp getCurrentTime() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		return castToSqlDate(now.getTime());
	}
	
	public static java.sql.Timestamp getTimeOffSet(Calendar now, int minutesTOAdd) {
		now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutesTOAdd);
		return castToSqlDate(now.getTime());
	}
	
	public static java.sql.Timestamp getTimeOffSet(java.sql.Timestamp now, int minutesTOAdd) {
		Calendar cnow = Calendar.getInstance();
		cnow.setTimeInMillis(now.getTime());
		cnow.add(Calendar.MINUTE, minutesTOAdd);
		return castToSqlDate(cnow.getTime());
	}
	
	public static java.sql.Timestamp getCurrentTimeOffset(int minutesTOAdd) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutesTOAdd);
		return castToSqlDate(now.getTime());
	}
	
	public static String getFormattedTime(Date date, String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(date);
	}
	
	private static java.sql.Timestamp castToSqlDate(Date date) {
		return new java.sql.Timestamp(date.getTime());
	}
}
