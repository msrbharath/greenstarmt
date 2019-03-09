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
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DateUtil {

	public static final DateFormat DB_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat VIEW_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
	public static final DateTimeFormatter DATE_FORMAT_DD_MMM_YYYY = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	public static final DateTimeFormatter DATE_FORMAT_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public enum MonthValue {
		JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6), JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(
				10), NOVEMBER(11), DECEMBER(12);

		private static int value;

		MonthValue(int value) {
		}

		public static int getMonthValue(String month) {
			for (MonthValue monthValue : MonthValue.values()) {
				if (month.equals(monthValue.name())) {
					value = monthValue.ordinal() + 1;
					break;
				}
			}
			return value;
		}
	}

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
			dateValue = localDate.format(DATE_FORMAT_DD_MMM_YYYY);
		}
		return dateValue;
	}

	public static LocalDate getLocalDate(Date date) {
		return new java.sql.Date(date.getTime()).toLocalDate();
	}

	public static String getMonthName(int month) {
		return LocalDate.of(2019, month, 01).getMonth().name();
	}

	public static Integer getCurrentYear() {
		return Year.now().getValue();
	}

	public static String getCurrentYearMonthFirstAndLastDate(int month) {
		YearMonth yearMonth = YearMonth.of(getCurrentYear(), month);
		return yearMonth.atDay(1) + "  to  " + yearMonth.atEndOfMonth();
	}

	public static List<String> getWeekDetails(int year, int month) {

		List<String> weekDateList = new ArrayList<>();

		LocalDate firstOfMonth = LocalDate.of(year, month, 1);
		LocalDate firstOfNextMonth = firstOfMonth.plusMonths(1);
		LocalDate firstDateInGrid = firstOfMonth.with(DayOfWeek.SUNDAY);
		if (firstDateInGrid.isAfter(firstOfMonth)) { // If getting the next start of week instead of desired week's
														// start, adjust backwards.
			firstDateInGrid = firstDateInGrid.minusWeeks(1);
		}

		LocalDate weekStart = firstDateInGrid;
		LocalDate weekStop = null;
		int weekNumber = 0;

		do {
			weekNumber = weekNumber + 1;
			weekStop = weekStart.plusDays(6);

			weekDateList.add(weekStart + "~" + weekStop);
			weekStart = weekStop.plusDays(1);
		} while (weekStop.isBefore(firstOfNextMonth));

		return weekDateList;
	}

	public static List<LocalDate> getDatesBetweenTwoLocalDates(Date startDate, Date endDate) {

		LocalDate startLocalDate = getLocalDate(startDate);
		LocalDate endLocalDate = getLocalDate(endDate);

		long numOfDaysBetween = ChronoUnit.DAYS.between(startLocalDate, endLocalDate) + 1;
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startLocalDate.plusDays(i))
				.collect(Collectors.toList());
	}

	public static List<LocalDate> getDatesBetweenTwoDates(String startDate, String endDate) {

		LocalDate startLocalDate = LocalDate.parse(startDate);
		LocalDate endLocalDate = LocalDate.parse(endDate);

		long numOfDaysBetween = ChronoUnit.DAYS.between(startLocalDate, endLocalDate) + 1;
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startLocalDate.plusDays(i))
				.collect(Collectors.toList());
	}

}
