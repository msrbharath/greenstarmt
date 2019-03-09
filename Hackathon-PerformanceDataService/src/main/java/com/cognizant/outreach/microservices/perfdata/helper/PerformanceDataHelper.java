/**
 * ${performanceDataHelper}
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
package com.cognizant.outreach.microservices.perfdata.helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.MeasurableParamData;
import com.cognizant.outreach.entity.SchoolHoliday;
import com.cognizant.outreach.entity.StudentSchoolAssoc;
import com.cognizant.outreach.microservices.perfdata.repository.SchoolHolidayRepository;
import com.cognizant.outreach.microservices.perfdata.repository.SchoolWeekendWorkingDayResposiotry;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDayVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceHeaderVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceRowVO;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.util.DateUtil;

/**
 * PerformanceDataHelper class to handle reusable utility business methods.
 * 
 * @author Panneer
 */
@Component
public class PerformanceDataHelper {
	
	@Autowired
	SchoolHolidayRepository schoolHolidayRepository;
	
	@Autowired
	SchoolWeekendWorkingDayResposiotry schoolWeekendWorkingDayResposiotry;

	/**
	 * Method to build existing performance row object list.
	 * 
	 * @param datas
	 * @return performanceRows
	 */
	public List<PerformanceRowVO> buildExistingPerformanceRow(List<Object[]> datas) {

		List<PerformanceRowVO> performanceRows = new ArrayList<>();
		for (Object[] row : datas) {
			PerformanceRowVO performanceRowVO = new PerformanceRowVO();
			performanceRowVO.setRollId((String) row[0]);
			if (!performanceRows.contains(performanceRowVO)) {
				performanceRowVO.setStudentName((String) row[1]);
				performanceRowVO.setSchoolName((String) row[2]);
				performanceRowVO.setClassName((String) row[3]);
				performanceRowVO.setTeamName((String) row[4]);
				
				buildExistingPerformanceDay(performanceRowVO, row);

				performanceRows.add(performanceRowVO);
			} else {
				int index = performanceRows.indexOf(performanceRowVO);
				buildExistingPerformanceDay(performanceRows.get(index), row);
			}
		}
		return performanceRows;
	}

	/**
	 * Method to build existing performance daya object.
	 * 
	 * @param performanceRowVO
	 * @param row
	 */
	private void buildExistingPerformanceDay(PerformanceRowVO performanceRowVO, Object[] row) {

		PerformanceDayVO performanceDayVO = new PerformanceDayVO();
		performanceDayVO.setDateValue(DateUtil.getViewDate((Date) row[5]));

		if (!performanceRowVO.getPerformanceDays().contains(performanceDayVO)) {
			performanceRowVO.getPerformanceDays().add(performanceDayVO);

			buildExistingPerformanceData(performanceDayVO, row);
		} else {
			int index = performanceRowVO.getPerformanceDays().indexOf(performanceDayVO);
			buildExistingPerformanceData(performanceRowVO.getPerformanceDays().get(index), row);
		}
	}

	/**
	 * Method to build existing performance data object.
	 * 
	 * @param performanceDayVO
	 * @param row
	 */
	private void buildExistingPerformanceData(PerformanceDayVO performanceDayVO, Object[] row) {

		PerformanceDataVO performanceDataVO = new PerformanceDataVO();
		performanceDataVO.setKey((String) row[6]);

		if (!performanceDayVO.getPerformanceData().contains(performanceDataVO)) {
			performanceDataVO.setValue((((Integer) row[7]) == 1) ? true : false);
			performanceDayVO.getPerformanceData().add(performanceDataVO);
		}
	}

	/**
	 * Method to build create performance row list of objects.
	 * 
	 * @param studentList
	 * @param dateList
	 * @param measurableParamList
	 * @return performanceRows
	 */
	public List<PerformanceRowVO> buildCreatePerformanceRow(SearchPerformanceData searchPerformanceData, List<Object[]> studentList,
			List<MeasurableParam> measurableParamList, List<String> dateList) {
			
		List<PerformanceRowVO> performanceRows = new ArrayList<>();

		for (Object[] row : studentList) {
			PerformanceRowVO performanceRowVO = new PerformanceRowVO();
			performanceRowVO.setRollId((String) row[0]);
			if (!performanceRows.contains(performanceRowVO)) {
				performanceRowVO.setStudentName((String) row[1]);
				performanceRowVO.setSchoolName((String) row[2]);
				performanceRowVO.setClassName((String) row[3]);
				performanceRowVO.setSection((String) row[4]);
				performanceRowVO.setTeamName((String) row[5]);
				buildCreatePerformanceDay(performanceRowVO, dateList, measurableParamList);

				performanceRows.add(performanceRowVO);
			}
		}
		return performanceRows;
	}

	/**
	 * Method to build the create performance day object.
	 * 
	 * @param performanceRowVO
	 * @param dateList
	 * @param measurableParamList
	 */
	private void buildCreatePerformanceDay(PerformanceRowVO performanceRowVO, List<String> dateList,
			List<MeasurableParam> measurableParamList) {

		for (String localDate : dateList) {
			PerformanceDayVO performanceDayVO = new PerformanceDayVO();
			performanceDayVO.setDateValue(localDate);
			buildCreatePerformanceData(performanceDayVO, measurableParamList);

			performanceRowVO.getPerformanceDays().add(performanceDayVO);
		}
	}

	/**
	 * Method to build the create performance data object.
	 * 
	 * @param performanceDayVO
	 * @param measurableParamList
	 */
	private void buildCreatePerformanceData(PerformanceDayVO performanceDayVO, List<MeasurableParam> measurableParamList) {

		for (MeasurableParam measurableParam : measurableParamList) {
			PerformanceDataVO performanceDataVO = new PerformanceDataVO();
			performanceDataVO.setKey(measurableParam.getParameterTitle());
			performanceDataVO.setValue(false);

			performanceDayVO.getPerformanceData().add(performanceDataVO);
		}
	}

	/**
	 * Method to form performance main header to use in view table.
	 * 
	 * @param measurableParamList
	 * @param searchPerformanceData
	 * @return headers
	 */
	public List<PerformanceHeaderVO> getPerformanceHeaderForWeek(List<MeasurableParam> measurableParamList, SearchPerformanceData searchPerformanceData, List<String> weekDates) {
		
		// Sub headers(Measurable Parameters)
		List<PerformanceHeaderVO> subHeaders = getPerformanceSubHeader(measurableParamList);
				
		List<PerformanceHeaderVO> headers = new ArrayList<>();
		for (String weekDate : weekDates) {
			headers.add(new PerformanceHeaderVO(weekDate, weekDate, false, true, subHeaders));
		}
		return headers;
	}
	
	/**
	 * Method to form performance main header to use in view table.
	 * 
	 * @param measurableParamList
	 * @param searchPerformanceData
	 * @return headers
	 */
	public List<PerformanceHeaderVO> getPerformanceHeaderForMonth(List<MeasurableParam> measurableParamList, SearchPerformanceData searchPerformanceData, List<String> monthDates) {
		
		// Sub headers(Measurable Parameters)
		List<PerformanceHeaderVO> subHeaders = getPerformanceSubHeader(measurableParamList);
		
		List<PerformanceHeaderVO> headers = new ArrayList<>();
		for (String date : monthDates) {
			headers.add(new PerformanceHeaderVO(date, date, false, true, subHeaders));
		}
		return headers;
	}

	/**
	 * Method to form performance sub headers.
	 * 
	 * @param measurableParamList
	 * @return subHeaders
	 */
	private List<PerformanceHeaderVO> getPerformanceSubHeader(List<MeasurableParam> measurableParamList) {

		List<PerformanceHeaderVO> subHeaders = new ArrayList<>();
		for (MeasurableParam measurableParam : measurableParamList) {
			subHeaders.add(new PerformanceHeaderVO(measurableParam.getParameterTitle(), 
					measurableParam.getParameterTitle(), false, true, null));
		}
		return subHeaders;
	}
	
	/**
	 * Method to form map object for measurable parameter.
	 * 
	 * @param measurableParamList
	 * @return measurableParamMap
	 */
	public Map<String, MeasurableParam> getPerformanceParamMap(List<MeasurableParam> measurableParamList) {
		
		Map<String, MeasurableParam> measurableParamMap = new HashMap<String, MeasurableParam>();
		
		measurableParamList.forEach(measurableParam -> {
			measurableParamMap.put(measurableParam.getParameterTitle(), measurableParam);
		});		
		return measurableParamMap;		
	}
	
	/**
	 * Method to form student and school details to map object use in service layer.
	 * 
	 * @param studentSchoolAssocList
	 * @return studentSchoolAssocMap
	 */
	public Map<String, StudentSchoolAssoc> getStudentSchoolAssocMap(List<StudentSchoolAssoc> studentSchoolAssocList) {
		
		Map<String, StudentSchoolAssoc> studentSchoolAssocMap = new HashMap<String, StudentSchoolAssoc>();
		
		studentSchoolAssocList.forEach(studentSchoolAssoc -> {
			studentSchoolAssocMap.put(studentSchoolAssoc.getRollId(), studentSchoolAssoc);
		});		
		return studentSchoolAssocMap;		
	}
	
	/**
	 * Method to form the measurable parameter data object map for easy to use in service layer.
	 *  
	 * @param measurableParamDataList
	 * @return measurableParamDataMap
	 */
	public Map<String, MeasurableParamData> getMeasurableParamDataMap(List<MeasurableParamData> measurableParamDataList) {
		
		Map<String, MeasurableParamData> measurableParamDataMap = new HashMap<String, MeasurableParamData>();
		
		measurableParamDataList.forEach(measurableParamData -> {
			measurableParamDataMap.put(measurableParamData.getStudentSchoolAssoc().getRollId()+DateUtil.getViewDate(measurableParamData.getMeasurableDate())+measurableParamData.getMeasurableParam().getParameterTitle(), measurableParamData);
		});
		return measurableParamDataMap;
	}
	
	/**
	 * Method to get list of roll id.
	 * @param studentList
	 * @return list
	 */
	public List<String> getStudentRollIds(List<Object[]> studentList) {
		
		List<String> rollIds = new ArrayList<>();		
		studentList.forEach(arrayObj -> {
			rollIds.add((String)arrayObj[0]);
		});		
		return rollIds;
	}
	
	/**
	 * Method to populate holidays day on month wise
	 * 
	 * @param schoolHolidays
	 * @return Map<Integer, Map<Integer,String>> list of holidays month wise.example <10,<2,TUE>> for oct Gandhi jeyanthi
	 */
	public Map<Integer, Map<Integer,String>> populateHolidays(List<SchoolHoliday> schoolHolidays){
		
		 Map<Integer, Map<Integer,String>>  holidayMapMonthwise = new HashMap<Integer, Map<Integer,String>>();
		for (SchoolHoliday schoolHoliday : schoolHolidays) {
			org.joda.time.LocalDate fromDate = new org.joda.time.LocalDate(schoolHoliday.getFromDate());
			org.joda.time.LocalDate toDate = new org.joda.time.LocalDate(schoolHoliday.getToDate());
			for (org.joda.time.LocalDate date = fromDate; date.isBefore(toDate.plusDays(1)); date = date.plusDays(1)) {
				Map<Integer,String> holidaysOfMonth = holidayMapMonthwise.get(date.monthOfYear().get());
				if(null == holidaysOfMonth) {
					holidaysOfMonth = new HashMap<Integer,String>();
					holidayMapMonthwise.put(date.monthOfYear().get(), holidaysOfMonth);
				}
				holidaysOfMonth.put(date.dayOfMonth().get(), date.dayOfMonth().getAsShortText());
			}
		}
		return holidayMapMonthwise;
	}
	
	/**
	 * To get weekend working days for school and it's month
	 * 
	 * @param schoolId
	 * @param monthNumber
	 * @return List<Integer> list of working days for the month
	 */
	public List<Integer> getWeekendWorkingDays(long schoolId, int monthNumber) {
		List<Integer> weekendWorkingDayList = new ArrayList<>();
		Optional<List<Integer>> weekendWorkingDays = schoolWeekendWorkingDayResposiotry.listWorkingDaysBySchoolIdAndDate(schoolId, monthNumber);
		if (weekendWorkingDays.isPresent()) {
			weekendWorkingDayList = weekendWorkingDays.get();
		}
		return weekendWorkingDayList;
	}

	/**
	 * To get weekend holidays for school and it's month
	 * 
	 * @param schoolId
	 * @param monthNumber
	 * @return List<Integer> list of day number those are configures as holidays
	 */
	public List<Integer> getHolidays(long schoolId, int monthNumber) {
		Optional<List<SchoolHoliday>> schoolHolidays = schoolHolidayRepository.findBySchoolId(schoolId);
		List<Integer> holidays = new ArrayList<Integer>();
		Map<Integer, Map<Integer, String>> holidayMapMonthwise = null;
		if (schoolHolidays.isPresent()) {
			holidayMapMonthwise = this.populateHolidays(schoolHolidays.get());
			 Map<Integer, String> holidayMap = holidayMapMonthwise.get(monthNumber);
			 if(null != holidayMap) {
				 holidays.addAll(holidayMap.keySet());
			 }
		}
		return holidays;
	}
	
	/**
	 * Method to working week dates based on school id and month number.
	 * 
	 * @param schoolId
	 * @param monthNumber
	 * @return weekendWorkingDayList
	 */
	public List<LocalDate> getWeekendWorkingDates(long schoolId, int monthNumber) {
		
		List<LocalDate> weekendWorkingDayList = new ArrayList<>();
		Optional<List<Date>> weekendWorkingDates = schoolWeekendWorkingDayResposiotry.listWorkingDatesBySchoolIdAndDate(schoolId, monthNumber);
		
		if (weekendWorkingDates.isPresent()) {
			weekendWorkingDates.get().forEach(date -> {
				weekendWorkingDayList.add(DateUtil.getLocalDate(date));
			});
		}
		return weekendWorkingDayList;		
	}
	
	/**
	 * Method fetch holidays list based on school id.
	 * 
	 * @param schoolId
	 * @param monthNumber
	 * @return holidayList
	 */
	public List<LocalDate> getHolidayDates(long schoolId, int monthNumber) {
		
		List<LocalDate> holidayList = new ArrayList<>();
		Optional<List<SchoolHoliday>> schoolHolidays = schoolHolidayRepository.findBySchoolId(schoolId);
		
		if (schoolHolidays.isPresent()) {
			schoolHolidays.get().forEach(schoolHoliday -> {
				holidayList.addAll(DateUtil.getDatesBetweenTwoLocalDates(schoolHoliday.getFromDate(), schoolHoliday.getToDate()));
			});
		}
		return holidayList;
	}
	
	/**
	 * Method to finalize the working week dates based on inputs given month, weekend working days, holidays and week dates.
	 * @param month
	 * @param weekLimitList
	 * @param weekendWorkingDayList
	 * @param holidayList
	 * @return weekDayMap
	 */
	private Map<Integer, List<String>> getFinalWokingWeekWiseDates(int month, List<String> weekLimitList, List<LocalDate> weekendWorkingDayList, List<LocalDate> holidayList) {
				
		int indexKey = 1;
		Map<Integer, List<String>> weekDayMap = new HashMap<>();
		for (String weekLimit : weekLimitList) {
			String[] weekLimitArray = weekLimit.split("\\~");
			List<LocalDate> weekDates = DateUtil.getDatesBetweenTwoDates(weekLimitArray[0], weekLimitArray[1]);

			List<String> dateList = new ArrayList<>();

			for (LocalDate localDate : weekDates) {
				if (month == localDate.getMonthValue()) {					
					if(DayOfWeek.SUNDAY.name().equals(localDate.getDayOfWeek().name()) 
							|| DayOfWeek.SATURDAY.name().equals(localDate.getDayOfWeek().name())) {						
						if(weekendWorkingDayList.contains(localDate)) {
							dateList.add(DateUtil.getViewLocalDate(localDate));
						}						
					} else {
						if(!holidayList.contains(localDate)) {
							dateList.add(DateUtil.getViewLocalDate(localDate));
						}						
					}					
				}				
			}
			weekDayMap.put(indexKey, dateList);
			indexKey++;
		}
		
		return weekDayMap;
	}
	
	/**
	 * Method to format to map from finalized working week dates.
	 * 
	 * Method to form the UI level 
	 * @param schoolId
	 * @param year
	 * @param month
	 * @return map
	 */
	public Map<String, String> getMonthLevelWorkingWeekDays(long schoolId, int year, int month) {
		
		// Week wise all dates by given month and year
		List<String> weekLimitList = DateUtil.getWeekDetails(year, month);
		
		// Weekend Working dates
		List<LocalDate> weekendWorkingDayList = getWeekendWorkingDates(schoolId, month);
		
		// Holiday dates
		List<LocalDate> holidayList = getHolidayDates(schoolId, month);
		
		// Finalized week dates
		Map<Integer, List<String>> weekDayMap = getFinalWokingWeekWiseDates(month, weekLimitList, weekendWorkingDayList, holidayList);
		
		// Form data for view dropdown selection component
		Integer weekIndex = 1;
		Map<String, String> weekAllDates = new LinkedHashMap<>();
		for(Map.Entry<Integer, List<String>> weekDates : weekDayMap.entrySet()) {
			if(!CollectionUtils.isEmpty(weekDates.getValue())) {
				weekAllDates.put("Week-"+weekIndex, String.join("~", weekDates.getValue()));
				weekIndex++;
			}
		}		
		return weekAllDates;		
	}
	
	/**
	 * Method to format to map from finalized working month dates.
	 * 
	 * @param schoolId
	 * @param year
	 * @param month
	 * @return
	 */
	public List<String> getMonthLevelWorkingDays(long schoolId, int year, int month) {
		
		// Week wise all dates by given month and year
		List<String> weekLimitList = DateUtil.getWeekDetails(year, month);
		
		// Weekend Working dates
		List<LocalDate> weekendWorkingDayList = getWeekendWorkingDates(schoolId, month);
		
		// Holiday dates
		List<LocalDate> holidayList = getHolidayDates(schoolId, month);
		
		// Finalized week dates
		Map<Integer, List<String>> weekDayMap = getFinalWokingWeekWiseDates(month, weekLimitList, weekendWorkingDayList, holidayList);
		
		List<String> dateList = new ArrayList<>();
		for(Map.Entry<Integer, List<String>> weekDates : weekDayMap.entrySet()) {
			weekDates.getValue().forEach(day -> {
				dateList.add(day);
			});
		}		
		return dateList;
	}
	
	/**
	 * Method to convert tiled string value to list of string.
	 * 
	 * @param value
	 * @return list
	 */
	public List<String> convertTildeSeprationToList(String value) {		
		List<String> dateList = new ArrayList<>();
		if(!StringUtils.isEmpty(value)) {
			String[] dateArray = value.split("\\~");
			Collections.addAll(dateList, dateArray);
		}		
		return dateList;		
	}

}
