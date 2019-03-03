/**
 * ${BulkUploadPerfDataController}
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

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.outreach.microservices.perfdata.service.BulkUploadPerfDataService;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.util.modal.ApiResponse;

/**
 * BulkUploadPerfDataController class to handle all request and response for
 * bulk upload for performance metric and download template data
 * 
 * @author Panneer
 */
@RestController
@CrossOrigin
@RequestMapping("/perfdata")
public class BulkUploadPerfDataController {

	@Autowired
	private BulkUploadPerfDataService bulkUploadPerfDataService;

	/**
	 * Method to download the bulk upload template excel file.
	 * 
	 * @param searchPerformanceData
	 * @return ResponseEntity
	 * @throws IOException
	 */
	@PostMapping("/downloadtemplate")
	public byte[] downloadTemplate(@RequestBody SearchPerformanceData searchPerformanceData) throws IOException {
		return bulkUploadPerfDataService.downloadTemplate(searchPerformanceData);
	}

	@PostMapping("/uploadbulkdata")
	public ApiResponse<Object> bulkUploadPerformanceMetric(@RequestParam("file") MultipartFile file) {
		try {
			return new ApiResponse<>(HttpStatus.OK.value(), bulkUploadPerfDataService.uploadTemplate(file), null);
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.EXPECTATION_FAILED.value(), "Error", e.getMessage());
		}
	}

}
