/**
 * ${PerformanceDataDAOImpl}
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
package com.cognizant.outreach.microservices.perfdata.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.MeasurableParamData;
import com.cognizant.outreach.entity.StudentSchoolAssoc;
import com.cognizant.outreach.microservices.perfdata.repository.PerformanceDataRepository;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataTableVO;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.util.DateUtil;

/**
 * PerformanceDataDAOImpl class to handle all database related activities
 * 
 * @author Panneer
 */
@Repository
public class PerformanceDataDAOImpl implements PerformanceDataDAO {
	
	@Autowired
	private PerformanceDataRepository performanceDataRepository;
	
	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.dao.PerformanceDataDAO#saveOrUpdateMeasurableParamData(com.cognizant.outreach.entity.MeasurableParamData)
	 */
	@Override
	public MeasurableParamData saveOrUpdateMeasurableParamData(MeasurableParamData measurableParamData) {
		return performanceDataRepository.save(measurableParamData);
	}
	
	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.dao.PerformanceDataDAO#listOfPerformanceMetricDataBySearchParam(com.cognizant.outreach.microservices.perfdata.view.SearchPerformanceData)
	 */
	@Override
	public List<Object[]> listOfPerformanceMetricDataBySearchParam(SearchPerformanceData searchPerformanceData) {
		
		List<Object[]> performanceDataList = null;
		Date fromDate = null;
		Date toDate = null;
		try {
			String[] weekDates = searchPerformanceData.getWeek().split("~");
			if(null != weekDates) {
				if(weekDates.length == 1) {
					fromDate = DateUtil.getParseDateObject(weekDates[0]);
					toDate = DateUtil.getParseDateObject(weekDates[0]);
				} else {
					fromDate = DateUtil.getParseDateObject(weekDates[0]);
					toDate = DateUtil.getParseDateObject(weekDates[weekDates.length-1]);
				}
			}
			performanceDataList = performanceDataRepository.listOfMeasurableParamDataBySearchParam(searchPerformanceData.getSchoolId(), searchPerformanceData.getClassId(), fromDate, toDate);
		} catch(Exception e) {
			
		}		
		return performanceDataList;
	}
	
	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.dao.PerformanceDataDAO#listOfPerformanceMetricObjectBySearchParam(com.cognizant.outreach.microservices.perfdata.view.PerformanceDataTableVO)
	 */
	@Override
	public List<MeasurableParamData> listOfPerformanceMetricObjectBySearchParam(PerformanceDataTableVO performanceDataTableVO) {
		
		List<MeasurableParamData> performanceDataList = null;
		Date fromDate = null;
		Date toDate = null;
		try {
			String[] weekDates = performanceDataTableVO.getWeek().split("~");
			if(null != weekDates) {
				if(weekDates.length == 1) {
					fromDate = DateUtil.getParseDateObject(weekDates[0]);
					toDate = DateUtil.getParseDateObject(weekDates[0]);
				} else {
					fromDate = DateUtil.getParseDateObject(weekDates[0]);
					toDate = DateUtil.getParseDateObject(weekDates[weekDates.length-1]);
				}
			}
			performanceDataList = performanceDataRepository.listOfMeasurableParamObjectBySearchParam(performanceDataTableVO.getSchoolId(), performanceDataTableVO.getClassId(), fromDate, toDate);
		} catch(Exception e) {
			
		}		
		return performanceDataList;
	}
	
	@Override
	public boolean isExistOfMeasurableParamObjectBySearchParam(SearchPerformanceData searchPerformanceData) {
		
		boolean isExist = false;
		try {			
			Date fromDate = DateUtil.getDatabaseDate(searchPerformanceData.getStartDate());
			Date toDate = DateUtil.getDatabaseDate(searchPerformanceData.getEndDate());
			isExist = performanceDataRepository.isExistOfMeasurableParamObjectBySearchParam(searchPerformanceData.getSchoolId(), searchPerformanceData.getClassId(), fromDate, toDate);
		} catch(Exception e) {
			
		}
		
		return isExist;
	}
	
	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.dao.PerformanceDataDAO#listOfMeasurableParamBySchoolId(java.lang.Long)
	 */
	@Override
	public List<MeasurableParam> listOfMeasurableParamBySchoolId(Long schoolId) {
				
		return performanceDataRepository.listOfMeasurableParamBySchoolId(schoolId);
	}
	
	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.dao.PerformanceDataDAO#listOfStudentDetailBySearchParam(com.cognizant.outreach.microservices.perfdata.view.SearchPerformanceData)
	 */
	@Override
	public List<Object[]> listOfStudentDetailBySearchParam(SearchPerformanceData searchPerformanceData) {

		return performanceDataRepository.listOfStudentDetailBySearchParam(searchPerformanceData.getSchoolId(), searchPerformanceData.getClassId());
	}
	
	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.dao.PerformanceDataDAO#listOfStudentSchoolAssocBySearchParam(com.cognizant.outreach.microservices.perfdata.view.PerformanceDataTableVO)
	 */
	@Override
	public List<StudentSchoolAssoc> listOfStudentSchoolAssocBySearchParam(PerformanceDataTableVO performanceDataTableVO) {

		return performanceDataRepository.listOfStudentSchoolAssocBySearchParam(performanceDataTableVO.getSchoolId(), performanceDataTableVO.getClassId());
	}

	@Override
	public String findSchoolNameBySchoolId(long studentId) {
		return performanceDataRepository.findSchoolNameBySchoolId(studentId);
	}

	@Override
	public String findClassNameByClassId(long classId) {
		return performanceDataRepository.findClassNameByClassId(classId);
	}

}
