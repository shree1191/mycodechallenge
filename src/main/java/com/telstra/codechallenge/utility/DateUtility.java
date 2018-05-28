package com.telstra.codechallenge.utility;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
 * 
 * This is the utility class 
 * which is use for date format and date manipulation
 * */

public final class DateUtility {
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	// return last 7 days records
	public static Date getPreviousSevenDaysdate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		Date lastWeekDate =(cal.getTime());
		return lastWeekDate;
	}

	// return created At date
	public static Date getFormattedDate(String createdAt) {
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(createdAt);
		} catch (ParseException e) {
			LOGGER.error("Date format parsing error : "+ e.getMessage());
		}
		return date1;
	}

}
