/**
 * ${PerformanceDataServiceImpl}
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
package com.cognizant.outreach.microservices.perfdata.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.MeasurableParamData;
import com.cognizant.outreach.entity.StudentSchoolAssoc;
import com.cognizant.outreach.microservices.perfdata.dao.PerformanceDataDAOImpl;
import com.cognizant.outreach.microservices.perfdata.helper.PerformanceDataHelper;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataTableVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDayVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceRowVO;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.util.DateUtil;

/**
 * PerformanceDataServiceImpl class to handle the intermediate between controller and DAO layer and it handle business logic. 
 * 
 * @author Panneer
 */
@Service
public class PerformanceDataServiceImpl implements PerformanceDataService {
	
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	
	public static final DateFormat DB_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private PerformanceDataDAOImpl performanceDataDAO;

	@Autowired
	private PerformanceDataHelper performanceDataHelper;

	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.service.PerformanceDataService#getExistingPerformanceData(com.cognizant.outreach.microservices.perfdata.view.SearchPerformanceData)
	 */
	@Override
	public PerformanceDataTableVO getExistingPerformanceData(SearchPerformanceData searchPerformanceData) {
		
		// Fetch measurable parameters by school id.
		List<MeasurableParam> measurableParamList = performanceDataDAO.listOfMeasurableParamBySchoolId(searchPerformanceData.getSchoolId());
		
		// Fetch existing measurable parameter and value
		List<Object[]> objectArrayList = performanceDataDAO.listOfPerformanceMetricDataBySearchParam(searchPerformanceData);

		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
		performanceDataTableVO.setSchoolId(searchPerformanceData.getSchoolId());
		performanceDataTableVO.setClassId(searchPerformanceData.getClassId());
		performanceDataTableVO.setClassName(searchPerformanceData.getClassName());
		performanceDataTableVO.setMonth(searchPerformanceData.getMonth());
		performanceDataTableVO.setWeek(searchPerformanceData.getWeek());
		performanceDataTableVO.setTotalSubTitle(measurableParamList.size());
		
		// Individual dates for given week.
		List<String> dateList = performanceDataHelper.convertTildeSeprationToList(searchPerformanceData.getWeek());
		
		// Performance data table header row formation
		performanceDataTableVO.setHeaders(performanceDataHelper.getPerformanceHeaderForWeek(measurableParamList, searchPerformanceData, dateList));
		
		// Performance data table content formation (existing metrics data)
		performanceDataTableVO.setPerformanceRows(performanceDataHelper.buildExistingPerformanceRow(objectArrayList));

		return performanceDataTableVO;
	}

	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.service.PerformanceDataService#getCreatePerformanceData(com.cognizant.outreach.microservices.perfdata.view.SearchPerformanceData)
	 */
	@Override
	public PerformanceDataTableVO getCreatePerformanceData(SearchPerformanceData searchPerformanceData) {
		
		// Fetch student details based on school, class and section.
		List<Object[]> studentList = performanceDataDAO.listOfStudentDetailBySearchParam(searchPerformanceData);
		
		// Fetch measurable parameters
		List<MeasurableParam> measurableParamList = performanceDataDAO.listOfMeasurableParamBySchoolId(searchPerformanceData.getSchoolId());
		
		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
		performanceDataTableVO.setSchoolId(searchPerformanceData.getSchoolId());
		performanceDataTableVO.setClassId(searchPerformanceData.getClassId());
		performanceDataTableVO.setClassName(searchPerformanceData.getClassName());
		performanceDataTableVO.setMonth(searchPerformanceData.getMonth());
		performanceDataTableVO.setWeek(searchPerformanceData.getWeek());
		performanceDataTableVO.setTotalSubTitle(measurableParamList.size());
		
		// Individual dates for given week.
		List<String> dateList = performanceDataHelper.convertTildeSeprationToList(searchPerformanceData.getWeek());
		
		// Performance data table header row formation
		performanceDataTableVO.setHeaders(performanceDataHelper.getPerformanceHeaderForWeek(measurableParamList, searchPerformanceData, dateList));
		
		// Performance data table content formation (create empty metric data)
		performanceDataTableVO.setPerformanceRows(performanceDataHelper.buildCreatePerformanceRow(searchPerformanceData, studentList,  measurableParamList, dateList));
		
		return performanceDataTableVO;
	}

	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.service.PerformanceDataService#savePerformanceData(com.cognizant.outreach.microservices.perfdata.view.PerformanceDataTableVO)
	 */
	@Override
	public String savePerformanceData(PerformanceDataTableVO performanceDataTableVO) {
		
		Date currentDate = new Date();
		
		// Fetch measurable parameters and converted to map(to refer the before saving)
		List<MeasurableParam> measurableParamList = performanceDataDAO.listOfMeasurableParamBySchoolId(performanceDataTableVO.getSchoolId());
		Map<String, MeasurableParam> measurableParamMap = performanceDataHelper.getPerformanceParamMap(measurableParamList);
		
		// Fetch student details and converted to map(to refer the before saving)
		List<StudentSchoolAssoc> studentSchoolAssocList = performanceDataDAO.listOfStudentSchoolAssocBySearchParam(performanceDataTableVO);
		Map<String, StudentSchoolAssoc> studentSchoolAssocMap = performanceDataHelper.getStudentSchoolAssocMap(studentSchoolAssocList);
		
		try {
			for(PerformanceRowVO performanceRowVO : performanceDataTableVO.getPerformanceRows()) {
				for(PerformanceDayVO performanceDayVO : performanceRowVO.getPerformanceDays()) {
					for(PerformanceDataVO performanceDataVO : performanceDayVO.getPerformanceData()) {
						MeasurableParamData measurableParamData = new MeasurableParamData();
						
						measurableParamData.setMeasurableDate(DateUtil.getParseDateObject(performanceDayVO.getDateValue()));
						// Measurable parameter object refer from map
						measurableParamData.setMeasurableParam(measurableParamMap.get(performanceDataVO.getKey()));
						measurableParamData.setMeasurableParamValue(performanceDataVO.isValue() == true ? 1 : 0);
						
						// Student object object refer from map
						measurableParamData.setStudentSchoolAssoc(studentSchoolAssocMap.get(performanceRowVO.getRollId()));
						measurableParamData.setCreatedDtm(currentDate);
						measurableParamData.setCreatedUserId(performanceDataTableVO.getUserId());
						measurableParamData.setLastUpdatedDtm(currentDate);
						measurableParamData.setLastUpdatedUserId(performanceDataTableVO.getUserId());
						
						performanceDataDAO.saveOrUpdateMeasurableParamData(measurableParamData);
					}				
				}
			}
		} catch(Exception e) {
			return ERROR;
		}		
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.service.PerformanceDataService#updatePerformanceData(com.cognizant.outreach.microservices.perfdata.view.PerformanceDataTableVO)
	 */
	@Override
	public String updatePerformanceData(PerformanceDataTableVO performanceDataTableVO) {
		
		Date currentDate = new Date();
		
		// Fetch measurable parameter data and converted to map(to refer the before updating)
		List<MeasurableParamData> measurableParamList = performanceDataDAO.listOfPerformanceMetricObjectBySearchParam(performanceDataTableVO);
		Map<String, MeasurableParamData> measurableParamMap = performanceDataHelper.getMeasurableParamDataMap(measurableParamList);
		
		try {
			for(PerformanceRowVO performanceRowVO : performanceDataTableVO.getPerformanceRows()) {
				for(PerformanceDayVO performanceDayVO : performanceRowVO.getPerformanceDays()) {
					for(PerformanceDataVO performanceDataVO : performanceDayVO.getPerformanceData()) {
						
						// Existing measurable parameter data object refer from map
						MeasurableParamData measurableParamData = measurableParamMap.get(performanceRowVO.getRollId()+performanceDayVO.getDateValue()+performanceDataVO.getKey());
						
						measurableParamData.setMeasurableParamValue(performanceDataVO.isValue() == true ? 1 : 0);
						measurableParamData.setLastUpdatedDtm(currentDate);
						measurableParamData.setLastUpdatedUserId(performanceDataTableVO.getUserId());
						
						performanceDataDAO.saveOrUpdateMeasurableParamData(measurableParamData);
					}				
				}
			}
		} catch(Exception e) {
			return ERROR;
		}		
		return SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.service.PerformanceDataService#getWeekWorkingDaysByMonth(com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData)
	 */
	@Override
	public Map<String, String> getWeekWorkingDaysByMonth(SearchPerformanceData searchPerformanceData) {
		return performanceDataHelper.getMonthLevelWorkingWeekDays(searchPerformanceData.getSchoolId(), DateUtil.getCurrentYear(), searchPerformanceData.getMonth());
	}

}
