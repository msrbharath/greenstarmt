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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	 * Method for testing purpose later we will delete this method
	 * @return ResponseEntity
	 */
	@GetMapping("/test")
	public ResponseEntity<?> testMessage() {
		try {
			return ResponseEntity.ok().body("SUCCESS");
		} catch (Exception e) {
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	/**
	 * Method to populate the existing performance transaction measurable data for manual edit.
	 * 
	 * @param searchPerformanceData
	 * @return ResponseEntity
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
	 * @return ResponseEntity
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
	 * @return ResponseEntity
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
	 * @return ResponseEntity
	 */
	@PostMapping("/updatemetricdatas")
	public ApiResponse<Object> updatePerformanceMetricsData(@RequestBody PerformanceDataTableVO performanceDataTableVO) {
		try {
			return new ApiResponse<>(HttpStatus.OK.value(), performanceDataService.updatePerformanceData(performanceDataTableVO), null);
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.EXPECTATION_FAILED.value(), "Error", e.getMessage());
		}		
	}

}
