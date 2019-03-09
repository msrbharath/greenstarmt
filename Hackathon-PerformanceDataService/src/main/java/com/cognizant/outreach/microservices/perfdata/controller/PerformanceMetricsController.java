package com.cognizant.outreach.microservices.perfdata.controller;

/**
 * ${PerformanceMetricsController}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  18/Feb/2019            Bharath        Developed base code structure
 *  ---------------------------------------------------------------------------
 */
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cognizant.outreach.microservices.perfdata.service.PerformanceMetricsService;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.SearchPerformanceMetrics;
import com.cognizant.outreach.util.modal.ApiResponse;

/**
 * Provides method to authorize and authenticate user along with API token
 * validation
 * 
 * @author Bharath
 */
@RestController
@CrossOrigin
@RequestMapping("/perfmetrics")
public class PerformanceMetricsController {

	@Autowired
	private PerformanceMetricsService performanceMetricsService;

	/**
	 * Method for testing purpose later we will delete this method
	 * 
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
	 * Method to populate the classwise performance data
	 * 
	 * @param searchPerformanceMetrics
	 * @return ResponseEntity
	 */
	@PostMapping("/classwise")
	public ApiResponse<Object> getClasswisePerformanceMetrics(
			@RequestBody SearchPerformanceMetrics searchPerformanceMetrics) {
		try {
			return new ApiResponse<>(HttpStatus.OK.value(), null,
					performanceMetricsService.getClasswisePerformanceMetrics(searchPerformanceMetrics));
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.EXPECTATION_FAILED.value(), "Error", e.getMessage());
		}
	}

	/**
	 * Method to populate the teamwise performance data
	 * 
	 * @param searchPerformanceMetrics
	 * @return ResponseEntity
	 */
	@PostMapping("/teamwise")
	public ApiResponse<Object> getTeamwisePerformanceMetrics(
			@RequestBody SearchPerformanceMetrics searchPerformanceMetrics) {
		try {
			return new ApiResponse<>(HttpStatus.OK.value(), null,
					performanceMetricsService.getTeamwisePerformanceMetrics(searchPerformanceMetrics));
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.EXPECTATION_FAILED.value(), "Error", e.getMessage());
		}
	}

	/**
	 * Method to populate the encouraging metrics
	 * 
	 * @param searchPerformanceMetrics
	 * @return ResponseEntity
	 */
	@PostMapping("/encouraging")
	public ApiResponse<Object> getEncouragingPerformanceMetrics(
			@RequestBody SearchPerformanceMetrics searchPerformanceMetrics) {
		try {
			return new ApiResponse<>(HttpStatus.OK.value(), null,
					performanceMetricsService.getEncouragingPerformanceMetrics(searchPerformanceMetrics));
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.EXPECTATION_FAILED.value(), "Error", e.getMessage());
		}
	}

}
