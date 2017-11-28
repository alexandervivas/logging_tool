package com.ef.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ef.enums.Duration;

public class DateTimeUtils {
	
	public static DateTimeFormatter getDateTimeFormatter() {
		return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
	}
	
	public static DateTime getEndDate(DateTime startDate, Duration duration) {
		
		switch(duration) {
			case DAILY: return startDate.plusDays(1);
			case HOURLY: return startDate.plusHours(1);
		}
		
		return startDate;
	}
	
	public static DateTime getDate(String dateStr) {
		DateTime tmp = getDateTimeFormatter().parseDateTime(dateStr);
		Date date = tmp.toDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int offset = calendar.getTimeZone().getOffset(calendar.getTimeInMillis());
		calendar.add(Calendar.MILLISECOND, offset);
		calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		return new DateTime(calendar);
	}

}
