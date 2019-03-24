/**
 * ${BulkUploadPerfDataControllerTest}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  18/Feb/2019            Panneer        Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.perfdata.controller;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.util.modal.ApiResponse;


/**
 * Test class for performance metrics controller
 * 
 * @author Bharath
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BulkUploadPerfDataControllerTest {
	
	MockMvc mockMvc;
	
	@Mock
	private BulkUploadPerfDataController bulkUploadPerfDataController;

	@Autowired
	private TestRestTemplate template;

	/**
	 * Setup mock mvc
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(bulkUploadPerfDataController).build();
	}

	/**
	 * Test method for {@link com.cognizant.outreach.microservices.perfdata.controller.BulkUploadPerfDataController#downloadTemplate(com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData)}.
	 */
	@Test
	public void testDownloadTemplate() {
		
		SearchPerformanceData searchPerformanceDataTemp = getSearchParamValue();
		
		@SuppressWarnings("rawtypes")
		ResponseEntity<ApiResponse> response = template.postForEntity("/downloadtemplate", searchPerformanceDataTemp, ApiResponse.class);
		Assert.assertNotNull("Create performance bulk upload template:", response.getBody());
	}

	/**
	 * Test method for {@link com.cognizant.outreach.microservices.perfdata.controller.BulkUploadPerfDataController#bulkUploadPerformanceMetric(org.springframework.web.multipart.MultipartFile, java.lang.String)}.
	 * @throws IOException 
	 */
	/*
	@Test
	public void testBulkUploadPerformanceMetric() throws IOException {
		
		File file = new File("src/test/resources/test_bulk_upload_template.xlsx");
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/vnd.ms-excel", IOUtils.toByteArray(input));
		
	    @SuppressWarnings("rawtypes")
		ResponseEntity<ApiResponse> response = template.postForEntity("/uploadbulkdata", multipartFile, null, ApiResponse.class);
	    Assert.assertNotNull("Performance bulk upload response:", response.getBody());
	}
	*/
	
	private SearchPerformanceData getSearchParamValue() {
		
		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		searchPerformanceData.setSchoolId(1l);
		searchPerformanceData.setSchoolName("SSVM Matriculation School");

		searchPerformanceData.setClassId(1l);
		searchPerformanceData.setClassName("I");
		searchPerformanceData.setSectionName("A");
		searchPerformanceData.setMonth(2);
		searchPerformanceData.setWeek("18-Feb-2019~19-Feb-2019~20-Feb-2019");
		
		return searchPerformanceData;
	}
	
}

