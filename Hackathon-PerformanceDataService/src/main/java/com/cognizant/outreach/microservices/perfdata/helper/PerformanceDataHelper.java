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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.MeasurableParamData;
import com.cognizant.outreach.entity.StudentSchoolAssoc;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDayVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceHeaderVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceRowVO;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.util.DateUtil;

/**
 * PerformanceDataUtil class to handle reusable utility business methods.
 * 
 * @author Panneer
 */
@Component
public class PerformanceDataHelper {

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
				performanceRowVO.setSection((String) row[4]);
				performanceRowVO.setTeamName((String) row[5]);
				
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
		performanceDayVO.setDateValue(DateUtil.getViewDate((Date) row[6]));

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
		performanceDataVO.setKey((String) row[7]);

		if (!performanceDayVO.getPerformanceData().contains(performanceDataVO)) {
			performanceDataVO.setValue((((String) row[8]).equals("1") ? true : false));
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
			List<MeasurableParam> measurableParamList) {
		
		String[] weekDates = searchPerformanceData.getWeek().split("~");
		List<LocalDate> dateList = DateUtil.getDatesBetweenTwoDates(weekDates[0], weekDates[1]);
		
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
	private void buildCreatePerformanceDay(PerformanceRowVO performanceRowVO, List<LocalDate> dateList,
			List<MeasurableParam> measurableParamList) {

		for (LocalDate localDate : dateList) {
			PerformanceDayVO performanceDayVO = new PerformanceDayVO();
			performanceDayVO.setDateValue(DateUtil.getViewLocalDate(localDate));
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
	public List<PerformanceHeaderVO> getPerformanceHeader(List<MeasurableParam> measurableParamList, SearchPerformanceData searchPerformanceData) {

		List<PerformanceHeaderVO> subHeaders = getPerformanceSubHeader(measurableParamList);
		
		String[] weekDates = searchPerformanceData.getWeek().split("~");
		
		List<LocalDate> monthDates = DateUtil.getDatesBetweenTwoDates(weekDates[0], weekDates[1]);
		List<PerformanceHeaderVO> headers = new ArrayList<>();

		for (LocalDate localDate : monthDates) {
			headers.add(new PerformanceHeaderVO(DateUtil.getViewLocalDate(localDate),
					DateUtil.getViewLocalDate(localDate), false, true, subHeaders));
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

}
