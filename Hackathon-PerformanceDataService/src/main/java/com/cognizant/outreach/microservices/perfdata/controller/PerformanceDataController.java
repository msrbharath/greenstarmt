/**
 * ${PerformanceDataController}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  18/Feb/2019            Panneer        	Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.perfdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.outreach.microservices.perfdata.service.PerformanceDataService;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataTableVO;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.util.modal.ApiResponse;

/**
 * PerformanceDataController class to handle all request and response for performance measurable data.
 * 
 * @author Panneer
 */
@RestController
@CrossOrigin
@RequestMapping("/perfdata")
public class PerformanceDataController {

	@Autowired
	private PerformanceDataService performanceDataService;
	
	/**
	 * Method to populate the existing performance transaction measurable data for manual edit.
	 * 
	 * @param searchPerformanceData
	 * @return ApiResponse
	 */
	@PostMapping("/existingmetricdatas")
	public ApiResponse<Object> populateExistingPerformanceMetricsData(@RequestBody SearchPerformanceData searchPerformanceData) {
		try {
			return new ApiResponse<>(HttpStatus.OK.value(), null, performanceDataService.getExistingPerformanceData(searchPerformanceData));
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.EXPECTATION_FAILED.value(), "Error", e.getMessage());
		}
	}

	/**
	 * Method to populate the new performance data template with student details for user manual entry.
	 * 
	 * @param searchPerformanceData
	 * @return ApiResponse
	 */
	@PostMapping("/createmetricdatas")
	public ApiResponse<Object> populateCreatePerformanceMetricsData(@RequestBody SearchPerformanceData searchPerformanceData) {
		try {
			return new ApiResponse<>(HttpStatus.OK.value(), null, performanceDataService.getCreatePerformanceData(searchPerformanceData));
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.EXPECTATION_FAILED.value(), "Error", e.getMessage());
		}		
	}

	/**
	 * Method to save the new performance measurable transaction data.
	 * 
	 * @param performanceDataTableVO
	 * @return ApiResponse
	 */
	@PostMapping("/savemetricdatas")
	public ApiResponse<Object> savePerformanceMetricsData(@RequestBody PerformanceDataTableVO performanceDataTableVO) {
		try {
			return new ApiResponse<>(HttpStatus.OK.value(), performanceDataService.savePerformanceData(performanceDataTableVO), null);
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.EXPECTATION_FAILED.value(), "Error", e.getMessage());
		}		
	}
	
	/**
	 * Method to update existing performance measurable transaction data.
	 * 
	 * @param performanceDataTableVO
	 * @return ApiResponse
	 */
	@PostMapping("/updatemetricdatas")
	public ApiResponse<Object> updatePerformanceMetricsData(@RequestBody PerformanceDataTableVO performanceDataTableVO) {
		try {
			return new ApiResponse<>(HttpStatus.OK.value(), performanceDataService.updatePerformanceData(performanceDataTableVO), null);
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.EXPECTATION_FAILED.value(), "Error", e.getMessage());
		}
	}
	
	/**
	 * Method to populate the week wise working dates based on current year and selected month. 
	 * @param searchPerformanceData
	 * @return ApiResponse
	 */
	@PostMapping("/weekdayes")
	public ApiResponse<Object> getWeekWorkingDaysByMonth(@RequestBody SearchPerformanceData searchPerformanceData) {
		return new ApiResponse<>(HttpStatus.OK.value(), null, performanceDataService.getWeekWorkingDaysByMonth(searchPerformanceData));
	}

}
