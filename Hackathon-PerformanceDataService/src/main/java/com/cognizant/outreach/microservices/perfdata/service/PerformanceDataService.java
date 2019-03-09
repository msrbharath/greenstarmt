/**
 * ${PerformanceDataService}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  2/Feb/2019            Panneer        	Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.perfdata.service;

import java.util.Map;

import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataTableVO;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;

public interface PerformanceDataService {

	/**
	 * Method to fetch existing performance measurable transaction data.
	 * 
	 * @param searchPerformanceData
	 * @return PerformanceDataTableVO
	 */
	public PerformanceDataTableVO getExistingPerformanceData(SearchPerformanceData searchPerformanceData);

	/**
	 * Method create new performance measurable template data.
	 * 
	 * @param searchPerformanceData
	 * @return PerformanceDataTableVO
	 */
	public PerformanceDataTableVO getCreatePerformanceData(SearchPerformanceData searchPerformanceData);

	/**
	 * Method to save the performance data(New entry)
	 * 
	 * @param performanceDataTableVO
	 * @return String
	 */
	public String savePerformanceData(PerformanceDataTableVO performanceDataTableVO);

	/**
	 * Method to update the existing performance data with update.
	 * 
	 * @param performanceDataTableVO
	 * @return String
	 */
	public String updatePerformanceData(PerformanceDataTableVO performanceDataTableVO);
	
	/**
	 * Method to populate the week wise working dates based on current year and selected month.
	 * @param searchPerformanceData
	 * @return map
	 */
	public Map<String, String> getWeekWorkingDaysByMonth(SearchPerformanceData searchPerformanceData);

}
