/**
 * 
 */
package com.cognizant.outreach.microservices.perfdata.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;

public interface BulkUploadPerfDataService {
	
	public byte[] downloadTemplate(SearchPerformanceData searchPerformanceData) throws IOException;
	
	public String uploadTemplate(MultipartFile multipartFile) throws IOException;
}

