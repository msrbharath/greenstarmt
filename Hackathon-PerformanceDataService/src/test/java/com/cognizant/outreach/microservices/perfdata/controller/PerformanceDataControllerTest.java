/**
 * ${PerformanceDataControllerTest}
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
 * Test class for performance data controller in performance data service.
 * 
 * @author Panneer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PerformanceDataControllerTest {
	
	MockMvc mockMvc;
	
	@Mock
	private PerformanceDataController performanceDataController;

	@Autowired
	private TestRestTemplate template;

	/**
	 * Setup mock mvc
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(performanceDataController).build();
	}
	
	/**
	 * To check test case to populate the existing performance data.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetExistingPerformanceData() throws Exception {
		
		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		searchPerformanceData.setSchoolId(1l);
		searchPerformanceData.setSchoolName("SSVM Matriculation");
		searchPerformanceData.setClassId(1l);
		searchPerformanceData.setClassName("First");
		searchPerformanceData.setMonth(1);
		searchPerformanceData.setWeek("01-Jan-2019~02-Jan-2019~03-Jan-2019~04-Jan-2019");
				
		ResponseEntity<ApiResponse> response = template.postForEntity("/existingmetricdatas", searchPerformanceData, ApiResponse.class);

		Assert.assertNotNull("To populate existing performance data", response.getBody());
	}
	
	/**
	 * To check add new performance data for student.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateNewPerformanceData() throws Exception {
		
		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		searchPerformanceData.setSchoolId(1l);
		searchPerformanceData.setSchoolName("SSVM Matriculation School");
		searchPerformanceData.setClassId(1l);
		searchPerformanceData.setClassName("First");
		searchPerformanceData.setMonth(1);
		searchPerformanceData.setWeek("01-Feb-2019~02-Feb-2019");
				
		ResponseEntity<ApiResponse> response = template.postForEntity("/createmetricdatas", searchPerformanceData, ApiResponse.class);

		Assert.assertNotNull("To populate create new performance data", response.getBody());
	}
	
	@Test
	public void testSavePerformanceMetricsData() {		
		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		searchPerformanceData.setSchoolId(1l);
		searchPerformanceData.setSchoolName("SSVM Matriculation School");
		searchPerformanceData.setClassId(1l);
		searchPerformanceData.setClassName("First");
		searchPerformanceData.setMonth(1);
		searchPerformanceData.setWeek("01-Feb-2019~02-Feb-2019");
		
		ResponseEntity<ApiResponse> response = template.postForEntity("/savemetricdatas", searchPerformanceData, ApiResponse.class);
		
		Assert.assertNotNull("Performance data saved successfully", response.getBody());
	}
	
	@Test
	public void testUpdatePerformanceMetricsData() {		
		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		searchPerformanceData.setSchoolId(1l);
		searchPerformanceData.setSchoolName("SSVM Matriculation School");
		searchPerformanceData.setClassId(1l);
		searchPerformanceData.setClassName("First");
		searchPerformanceData.setMonth(1);
		searchPerformanceData.setWeek("01-Feb-2019~02-Feb-2019");
		
		ResponseEntity<ApiResponse> response = template.postForEntity("/updatemetricdatas", searchPerformanceData, ApiResponse.class);
		
		Assert.assertNotNull("Performance data updated successfully", response.getBody());
	}
	
	@Test
	public void testGetWeekWorkingDayes() {		
		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		searchPerformanceData.setSchoolId(1l);
		searchPerformanceData.setSchoolName("SSVM Matriculation School");
		searchPerformanceData.setClassId(1l);
		searchPerformanceData.setClassName("First");
		searchPerformanceData.setMonth(1);
		searchPerformanceData.setWeek("01-Feb-2019~02-Feb-2019");
		
		ResponseEntity<ApiResponse> response = template.postForEntity("/weekdayes", searchPerformanceData, ApiResponse.class);
		
		Assert.assertNotNull("Get performance data week working days", response.getBody());
	}
		
}

