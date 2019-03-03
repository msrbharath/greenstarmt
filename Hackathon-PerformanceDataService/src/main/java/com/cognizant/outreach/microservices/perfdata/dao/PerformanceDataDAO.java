/**
 * ${PerformanceDataDAO}
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
 *  ---------------------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.perfdata.dao;

import java.util.List;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.MeasurableParamData;
import com.cognizant.outreach.entity.StudentSchoolAssoc;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataTableVO;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;

public interface PerformanceDataDAO {

	/**
	 * Method to save / update the measurable parameter data
	 * 
	 * @param measurableParamData
	 */
	public void saveOrUpdateMeasurableParamData(MeasurableParamData measurableParamData);

	/**
	 * Method to search performance metric data based on search parameter.
	 * 
	 * @param searchPerformanceData
	 * @return list
	 */
	public List<Object[]> listOfPerformanceMetricDataBySearchParam(SearchPerformanceData searchPerformanceData);

	/**
	 * @param performanceDataTableVO
	 * @return list
	 */
	public List<MeasurableParamData> listOfPerformanceMetricObjectBySearchParam(PerformanceDataTableVO performanceDataTableVO);
	
	/**
	 * Method to check measurable parameter is already exist by given search condition.
	 * @param searchPerformanceData
	 * @return boolean
	 */
	public boolean isExistOfMeasurableParamObjectBySearchParam(SearchPerformanceData searchPerformanceData);
	
	/**
	 * Method to fetch measurable parameter list based on school id.
	 * 
	 * @param schoolId
	 * @return list
	 */
	public List<MeasurableParam> listOfMeasurableParamBySchoolId(Long schoolId);

	/**
	 * Method to fetch student details based on search parameter.
	 * 
	 * @param searchPerformanceData
	 * @return list
	 */
	public List<Object[]> listOfStudentDetailBySearchParam(SearchPerformanceData searchPerformanceData);

	/**
	 * Method to fetch student details object list based on search parameter.
	 * 
	 * @param performanceDataTableVO
	 * @return list
	 */
	public List<StudentSchoolAssoc> listOfStudentSchoolAssocBySearchParam(PerformanceDataTableVO performanceDataTableVO);
}
