/**
 * ${DateUtil}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  27/Feb/2019            Panneer        	Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DateUtil {

	public static final DateFormat DB_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat VIEW_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
	public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

	public static String getViewDate(Date date) {

		String dateValue = null;
		if (null != date) {
			dateValue = VIEW_FORMAT.format(date);
		}

		return dateValue;
	}

	public static Date getParseDateObject(String dateValue) throws ParseException {

		Date dateObject = null;
		if (null != dateValue) {
			dateObject = VIEW_FORMAT.parse(dateValue);
		}

		return dateObject;
	}

	public static Date getDatabaseDate(String dateValue) throws ParseException {

		Date dateObject = null;
		if (null != dateValue) {
			dateObject = DB_FORMAT.parse(dateValue);
		}

		return dateObject;
	}

	public static String getViewLocalDate(LocalDate localDate) {

		String dateValue = null;
		if (null != localDate) {
			dateValue = localDate.format(dateTimeFormatter);
		}

		return dateValue;
	}

	public static Map<String, String> getWeekDetails(int year, int month) {

		Map<String, String> weekDateMap = new LinkedHashMap<>();

		LocalDate firstOfMonth = LocalDate.of(year, month, 1);
		LocalDate firstOfNextMonth = firstOfMonth.plusMonths(1);
		LocalDate firstDateInGrid = firstOfMonth.with(DayOfWeek.MONDAY);
		if (firstDateInGrid.isAfter(firstOfMonth)) { // If getting the next start of week instead of desired week's
														// start, adjust backwards.
			firstDateInGrid = firstDateInGrid.minusWeeks(1);
		}

		LocalDate weekStart = firstDateInGrid;
		LocalDate weekStop = null;
		int weekNumber = 0;

		do {
			weekNumber = weekNumber + 1;
			weekStop = weekStart.plusDays(4);

			System.out.println(weekNumber + " week: " + weekStart + " --- " + weekStop);
			weekDateMap.put(weekStart + "~" + weekStop, weekNumber + "-Week(" + weekStart + "," + weekStop + ")");
			weekStart = weekStop.plusDays(3);
		} while (weekStop.isBefore(firstOfNextMonth));

		return weekDateMap;
	}

	public static List<LocalDate> getDatesBetweenTwoDates(String startDate, String endDate) {

		LocalDate startLocalDate = LocalDate.parse(startDate);
		LocalDate endLocalDate = LocalDate.parse(endDate);

		long numOfDaysBetween = ChronoUnit.DAYS.between(startLocalDate, endLocalDate) + 1;
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startLocalDate.plusDays(i))
				.collect(Collectors.toList());
	}

	public static void main(String args[]) {

		getWeekDetails(2019, 3);

		List<LocalDate> list = getDatesBetweenTwoDates("2019-01-28", "2019-02-03");

		System.out.println(list);
	}

}
