/**
 * ${PerformanceStarServiceImpl}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  04/Mar/2019            371793        Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.perfdata.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.MeasurableParamData;
import com.cognizant.outreach.microservices.perfdata.constants.PerfDataConstants;
import com.cognizant.outreach.microservices.perfdata.constants.StarColorCodes;
import com.cognizant.outreach.microservices.perfdata.helper.PerformanceDataHelper;
import com.cognizant.outreach.microservices.perfdata.repository.MeasurableParamRepository;
import com.cognizant.outreach.microservices.perfdata.repository.PerformanceDataRepository;
import com.cognizant.outreach.microservices.perfdata.vo.star.PerformanceStarSearchDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.star.PerformanceStarVO;

/**
 * Service have implementation for generating star
 * 
 * @author 371793
 */
@Service
public class PerformanceStarServiceImpl implements PerformanceStarService {
	
	protected Logger logger = LoggerFactory.getLogger(PerformanceStarServiceImpl.class);

	@Autowired
	MeasurableParamRepository measurableParamRepository;

	@Autowired
	PerformanceDataRepository performanceDataRepository;

	@Autowired
	PerformanceDataHelper performanceDataHelper;

	private static final Long VALUE_100 = new Long(100L);

	private static final Long VALUE_75 = new Long(75L);

	@Override
	public Optional<PerformanceStarVO> getStarData(PerformanceStarSearchDataVO searchVO) {
		PerformanceStarVO performanceStarVO = new PerformanceStarVO();

		// Monthly holidays configured for the school
		List<Integer> monthHolidays = performanceDataHelper.getHolidays(searchVO.getSchoolId(), searchVO.getMonth());

		// Weekend working days configured for the school
		List<Integer> weekendWorkingdays = performanceDataHelper.getWeekendWorkingDays(searchVO.getSchoolId(),
				searchVO.getMonth());

		// Measurable params configured for the school
		Optional<List<MeasurableParam>> measurableParams = measurableParamRepository
				.findBySchoolId(searchVO.getSchoolId());

		if (measurableParams.isPresent()) {
			List<MeasurableParam> measurableParamList = measurableParams.get();
			// Populate the parameter names for star
			populateMeasurableParamNames(measurableParamList, performanceStarVO);

			// populate data for first parameter star
			if (!StringUtils.isEmpty(performanceStarVO.getParamOne())) {
				String[] colorCodes = generateStarColorCodes(searchVO, measurableParamList.get(0).getId(),
						monthHolidays, weekendWorkingdays);
				performanceStarVO.setParamOneMonthColorCodes(colorCodes);
			}

			// populate data for Second parameter star
			if (!StringUtils.isEmpty(performanceStarVO.getParamTwo())) {
				String[] colorCodes = generateStarColorCodes(searchVO, measurableParamList.get(1).getId(),
						monthHolidays, weekendWorkingdays);
				performanceStarVO.setParamTwoMonthColorCodes(colorCodes);
			}

			// populate data for third parameter star
			if (!StringUtils.isEmpty(performanceStarVO.getParamThree())) {
				String[] colorCodes = generateStarColorCodes(searchVO, measurableParamList.get(2).getId(),
						monthHolidays, weekendWorkingdays);
				performanceStarVO.setParamThreeMonthColorCodes(colorCodes);
			}
		}
		logger.debug("Completed service for generating star");
		return Optional.of(performanceStarVO);
	}

	/**
	 * To populate performance parameter names for the given school
	 * 
	 * @param measurableParams
	 * @param performanceStarVO
	 */
	private void populateMeasurableParamNames(List<MeasurableParam> measurableParams,
			PerformanceStarVO performanceStarVO) {
		if (!measurableParams.isEmpty()) {
			performanceStarVO.setParamOne(measurableParams.get(0).getParameterTitle());
			if (measurableParams.size() > 1) {
				performanceStarVO.setParamTwo(measurableParams.get(1).getParameterTitle());
			}
			if (measurableParams.size() > 2) {
				performanceStarVO.setParamThree(measurableParams.get(2).getParameterTitle());
			}
		}
	}

	/**
	 * To generate star codes for the given search criteria
	 * 
	 * @param searchVO
	 * @param measurableParamList
	 * @param monthHolidays
	 * @param weekendWorkingDays
	 * @return
	 */
	private String[] generateStarColorCodes(PerformanceStarSearchDataVO searchVO, long paramId,
			List<Integer> monthHolidays, List<Integer> weekendWorkingDays) {
		String[] starColorCodes = null;
		if (searchVO.getCalcType().equalsIgnoreCase(PerfDataConstants.INDIVIDUAL)) {
			starColorCodes = populateIndividualStarColorCodes(searchVO, paramId, monthHolidays, weekendWorkingDays);
		}else{
			starColorCodes = populateStarColorCodesForCalcType(searchVO, paramId, monthHolidays, weekendWorkingDays);
		}
		return starColorCodes;
	}

	/**
	 * To populate star for calculation type other than individual
	 * 
	 * @param searchVO
	 * @param measurableParamList
	 * @param monthHolidays
	 * @param weekendWorkingDays
	 * @return
	 */
	private String[] populateStarColorCodesForCalcType(PerformanceStarSearchDataVO searchVO, long paramId,
			List<Integer> monthHolidays, List<Integer> weekendWorkingDays) {
		List<String> monthColorCodes = new ArrayList<String>();

		// Get the parameter value for the month
		List<Object[]> measurableParamDatas = null;
		measurableParamDatas = getDBParamValues(searchVO,paramId);
		// Map with days as key and parameter value from DB as value
		Map<Integer, Long> dbDayParamValueMap = generateDayValueMap(measurableParamDatas);
		// Map with days as key and day week name as value
		Map<Integer, Integer> monthMapWithWeekDay = getMonthMap(searchVO.getMonth());

		// Iterate through each month day and set color code based on db value and
		// holidays
		for (Map.Entry<Integer, Integer> entry : monthMapWithWeekDay.entrySet()) {
			int monthDay = entry.getKey();
			int weekDay = entry.getValue();
			String colorCode = null;
			// Check if data present in db for the particular day
			if (dbDayParamValueMap.get(monthDay) == null) {
				colorCode = getColorCodeForNoData(weekDay, monthDay, monthHolidays, weekendWorkingDays);
			} else {
				if (dbDayParamValueMap.get(monthDay).equals(VALUE_100)) {
					colorCode = StarColorCodes.COMPLAINT.getColorCode();
				} else {
					int compareResult = dbDayParamValueMap.get(monthDay).compareTo(VALUE_75);
					if (compareResult == 0 || compareResult == 1) {
						colorCode = StarColorCodes.EQUAL_ABOVE_75.getColorCode();
					} else {
						colorCode = StarColorCodes.BELOW_75.getColorCode();
					}
				}
			}
			monthColorCodes.add(colorCode);
		}
		// Convert list to array
		String[] starColorCodes = monthColorCodes.stream().toArray(String[]::new);
		return starColorCodes;
	}

	/**
	 * To populate star for individual calculation type
	 * 
	 * @param searchVO
	 * @param measurableParamList
	 * @param monthHolidays
	 * @param weekendWorkingDays
	 * @return
	 */
	private String[] populateIndividualStarColorCodes(PerformanceStarSearchDataVO searchVO, long paramId,
			List<Integer> monthHolidays, List<Integer> weekendWorkingDays) {
		List<String> monthColorCodes = new ArrayList<String>();

		// Get the parameter value for the month
		List<MeasurableParamData> measurableParamDatas = null;
		measurableParamDatas = performanceDataRepository.listOfMeasurableParamDataByStudent(searchVO.getStudentId(),
				paramId, searchVO.getMonth());
		// Map with days as key and parameter value from DB as value
		Map<Integer, Integer> dbDayParamValueMap = populateDayValueMap(measurableParamDatas);
		// Map with days as key and day week name as value
		Map<Integer, Integer> monthMapWithWeekDay = getMonthMap(searchVO.getMonth());

		// Iterate through each month day and set color code based on db value and
		// holidays
		for (Map.Entry<Integer, Integer> entry : monthMapWithWeekDay.entrySet()) {
			int monthDay = entry.getKey();
			int weekDay = entry.getValue();
			String colorCode = null;
			// Check if data present in db for the particular day
			if (dbDayParamValueMap.get(monthDay) == null) {
				colorCode = getColorCodeForNoData(weekDay, monthDay, monthHolidays, weekendWorkingDays);
			} else {
				if (dbDayParamValueMap.get(monthDay) == 1) {
					// Set green color code.
					colorCode = StarColorCodes.COMPLAINT.getColorCode();
				} else {
					// Set red color code for individual there is no color code for above 75. It's
					// either 1 or 0
					colorCode = StarColorCodes.BELOW_75.getColorCode();
				}
			}
			monthColorCodes.add(colorCode);
		}
		// Convert list to array
		String[] starColorCodes = monthColorCodes.stream().toArray(String[]::new);
		return starColorCodes;
	}

	/**
	 * Method to get the DB param values and day of the month other than individual types
	 * 
	 * @param searchVO
	 * @param paramId
	 * @return
	 */
	private List<Object[]> getDBParamValues(PerformanceStarSearchDataVO searchVO, long paramId) {
		List<Object[]> measurableParamDatas = null;
		if(searchVO.getCalcType().equalsIgnoreCase(PerfDataConstants.TEAM)) {
			measurableParamDatas = performanceDataRepository.listOfMeasurableParamDataByTeam(searchVO.getClassId(),
					searchVO.getTeamName(), paramId, searchVO.getMonth());
		}else if(searchVO.getCalcType().equalsIgnoreCase(PerfDataConstants.CLASS)) {
			measurableParamDatas = performanceDataRepository.listOfMeasurableParamDataByClass(searchVO.getClassId(),
					paramId, searchVO.getMonth());
		}else if(searchVO.getCalcType().equalsIgnoreCase(PerfDataConstants.SCHOOL)) {
			measurableParamDatas = performanceDataRepository.listOfMeasurableParamDataBySchool(searchVO.getSchoolId(),
					paramId, searchVO.getMonth());
		}
		
		return measurableParamDatas;
	}

	/**
	 * To populate color code for no data scenario
	 * 
	 * @param weekDay
	 *            Day on which the month day falls on the week
	 * @param monthDay
	 *            Day for which the code need to be calculated
	 * @param monthHolidays
	 *            Holidays configured for the school for a particular month
	 * @param weekendWorkingDays
	 *            Weekend working days configured for the school for the particular
	 *            month
	 * @return color code for the day either no data or holiday color code
	 */
	private String getColorCodeForNoData(int weekDay, int monthDay, List<Integer> monthHolidays,
			List<Integer> weekendWorkingDays) {
		String colorCode = null;

		// check the day is not weekend or not configured holiday. Means it is working
		// day and no data entered
		if (!(weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY) && !(monthHolidays.contains(monthDay))) {
			// Set no data color code. Means data is not entered by user
			colorCode = StarColorCodes.NO_DATA.getColorCode();
		} else if ((weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY)
				&& (weekendWorkingDays.contains(monthDay))) {
			// Check if the day is weekend and it is configured as working day
			// Set no data color code. Means data is not entered by user
			colorCode = StarColorCodes.NO_DATA.getColorCode();
		} else {
			// Set holiday color code.
			colorCode = StarColorCodes.HOLIDAY.getColorCode();
		}
		return colorCode;
	}

	/**
	 * Method to convert the param data to key value map
	 * 
	 * @param measurableParamDatas
	 * @return Map<Integer, Integer> - Key is the day of the month and value is the
	 *         param value for the day
	 */
	private Map<Integer, Integer> populateDayValueMap(List<MeasurableParamData> measurableParamDatas) {
		Map<Integer, Integer> dayValueMap = new HashMap<Integer, Integer>();
		for (MeasurableParamData measurableParamData : measurableParamDatas) {
			org.joda.time.LocalDate measurableDate = new LocalDate(measurableParamData.getMeasurableDate());
			dayValueMap.put(measurableDate.dayOfMonth().get(), measurableParamData.getMeasurableParamValue());
		}
		return dayValueMap;
	}

	/**
	 * Method to convert the param data to key value map
	 * 
	 * @param measurableParamDatas
	 * @return Map<Integer, Long> - Key is the day of the month and value is the
	 *         param value for the day
	 */
	private Map<Integer, Long> generateDayValueMap(List<Object[]> measurableParamDatas) {
		Map<Integer, Long> dayValueMap = new HashMap<Integer, Long>();
		for (Object[] dbRow : measurableParamDatas) {
			dayValueMap.put((Integer) dbRow[1], (Long) dbRow[0]);
		}
		return dayValueMap;
	}

	/**
	 * Method to get a month map for the given month with value as weekday name
	 * 
	 * @param monthNumber
	 * @return Map<Integer, String> Default month map with key as days of the given
	 *         month and
	 */
	private Map<Integer, Integer> getMonthMap(int monthNumber) {
		org.joda.time.LocalDate firstDayOfMonth = new org.joda.time.LocalDate(2019, monthNumber, 1);
		int lastDayOfmonth = firstDayOfMonth.plusMonths(1).minusDays(1).getDayOfMonth();
		Map<Integer, Integer> monthMap = new LinkedHashMap<Integer, Integer>();
		org.joda.time.LocalDate currentDay = firstDayOfMonth;
		for (int i = 1; i <= lastDayOfmonth; i++) {
			monthMap.put(i, currentDay.dayOfWeek().get());
			currentDay = currentDay.plusDays(1);
		}
		return monthMap;
	}
}
