/**
 * ${BulkUploadPerfDataService}
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

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.util.modal.ApiResponse;

/**
 * BulkUploadPerfDataService class to handle the intermediate between controller and DAO layer and it handle business logic. 
 * 
 * @author Panneer
 */
public interface BulkUploadPerfDataService {
	
	/**
	 * Method populate the excel template based on search parameters.
	 * 
	 * @param searchPerformanceData
	 * @return
	 * @throws IOException
	 */
	public byte[] downloadTemplate(SearchPerformanceData searchPerformanceData) throws IOException;
	
	/**
	 * Method to process the uploaded excel template.
	 * 
	 * @param multipartFile
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	public ApiResponse<Object> uploadTemplate(MultipartFile multipartFile, String userId) throws IOException;
}
