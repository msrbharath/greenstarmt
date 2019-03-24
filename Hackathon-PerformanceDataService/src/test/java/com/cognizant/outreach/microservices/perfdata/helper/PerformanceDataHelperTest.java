package com.cognizant.outreach.microservices.perfdata.helper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cognizant.outreach.entity.SchoolHoliday;
import com.cognizant.outreach.microservices.perfdata.repository.SchoolHolidayRepository;
import com.cognizant.outreach.microservices.perfdata.repository.SchoolWeekendWorkingDayResposiotry;
import com.cognizant.outreach.util.DateUtil;

public class PerformanceDataHelperTest {
	
	@InjectMocks
	PerformanceDataHelper performanceDataHelper;
	
	@Mock
	SchoolWeekendWorkingDayResposiotry schoolWeekendWorkingDayResposiotry;

	@Mock
	SchoolHolidayRepository schoolHolidayRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void TestGetHolidays() throws ParseException {
		when(schoolHolidayRepository.findBySchoolId(1L)).thenReturn(getHolidays());
		List<Integer>  holidays = performanceDataHelper.getHolidays(1L, 10);
		assertEquals(holidays.size(), 1);
	}
	
	private Optional<List<SchoolHoliday>> getHolidays() throws ParseException {
		List<SchoolHoliday> schoolHolidays = new ArrayList<>();
		SchoolHoliday schoolHoliday = new SchoolHoliday();
		schoolHoliday.setFromDate(DateUtil.getParseDateObject("02-OCT-2019"));
		schoolHoliday.setToDate(DateUtil.getParseDateObject("02-OCT-2019"));
		schoolHolidays.add(schoolHoliday);
		return Optional.of(schoolHolidays);
	}
	
	@Test
	public void TestGetWeekendWorkingDays() throws ParseException {
		when(schoolWeekendWorkingDayResposiotry.listWorkingDaysBySchoolIdAndDate(1L,10)).thenReturn(getWeekendWorkingDays());
		List<Integer>  workingDays = performanceDataHelper.getWeekendWorkingDays(1L, 10);
		assertEquals(workingDays.size(), 2);
	}
	
	private Optional<List<Integer>> getWeekendWorkingDays() throws ParseException {
		List<Integer> weekendWorkingDays = new ArrayList<>();
		weekendWorkingDays.add(new Integer(12));
		weekendWorkingDays.add(new Integer(19));
		return Optional.of(weekendWorkingDays);
	}
	
}
