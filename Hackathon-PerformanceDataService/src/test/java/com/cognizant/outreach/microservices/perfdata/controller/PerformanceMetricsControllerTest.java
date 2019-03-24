/**
 * ${PerformanceMetricsControllerTest}
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

import com.cognizant.outreach.microservices.perfdata.vo.metrics.SearchPerformanceMetrics;
import com.cognizant.outreach.util.modal.ApiResponse;


/**
 * Test class for performance metrics controller
 * 
 * @author Bharath
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PerformanceMetricsControllerTest {
	
	MockMvc mockMvc;
	
	@Mock
	private PerformanceMetricsController performanceMetricsController;

	@Autowired
	private TestRestTemplate template;

	/**
	 * Setup mock mvc
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(performanceMetricsController).build();
	}
	
	/**
	 * Test method for {@link com.cognizant.outreach.microservices.perfdata.controller.PerformanceMetricsController#getClasswisePerformanceMetrics(com.cognizant.outreach.microservices.perfdata.vo.metrics.SearchPerformanceMetrics)}.
	 */
	@Test
	public void testGetClasswisePerformanceMetrics() {
		SearchPerformanceMetrics searchPerformanceMetrics = new SearchPerformanceMetrics();
		searchPerformanceMetrics.setSchoolId(42l);
		searchPerformanceMetrics.setClassName("I");
				
		@SuppressWarnings("rawtypes")
		ResponseEntity<ApiResponse> response = template.postForEntity("/perfmetrics/classwise", searchPerformanceMetrics, ApiResponse.class);
		Assert.assertNotNull("Classwise metrics data:", response.getBody());
	}

	/**
	 * Test method for {@link com.cognizant.outreach.microservices.perfdata.controller.PerformanceMetricsController#getTeamwisePerformanceMetrics(com.cognizant.outreach.microservices.perfdata.vo.metrics.SearchPerformanceMetrics)}.
	 */
	@Test
	public void testGetTeamwisePerformanceMetrics() {
		SearchPerformanceMetrics searchPerformanceMetrics = new SearchPerformanceMetrics();
		searchPerformanceMetrics.setSchoolId(42l);
		searchPerformanceMetrics.setClassName("I");
				
		@SuppressWarnings("rawtypes")
		ResponseEntity<ApiResponse> response = template.postForEntity("/perfmetrics/teamwise", searchPerformanceMetrics, ApiResponse.class);
		Assert.assertNotNull("Teamwise metrics data:", response.getBody());
	}

	/**
	 * Test method for {@link com.cognizant.outreach.microservices.perfdata.controller.PerformanceMetricsController#getEncouragingPerformanceMetrics(com.cognizant.outreach.microservices.perfdata.vo.metrics.SearchPerformanceMetrics)}.
	 */
	@Test
	public void testGetEncouragingPerformanceMetrics() {
		SearchPerformanceMetrics searchPerformanceMetrics = new SearchPerformanceMetrics();
		searchPerformanceMetrics.setSchoolId(42l);
		searchPerformanceMetrics.setClassName("I");
		searchPerformanceMetrics.setClassId(37l);
		searchPerformanceMetrics.setMonth1(1);
		searchPerformanceMetrics.setMonth1(2);
				
		@SuppressWarnings("rawtypes")
		ResponseEntity<ApiResponse> response = template.postForEntity("/perfmetrics/encouraging", searchPerformanceMetrics, ApiResponse.class);
		Assert.assertNotNull("Encouraging metrics data:", response.getBody());
	}

	/**
	 * Test method for {@link com.cognizant.outreach.microservices.perfdata.controller.PerformanceMetricsController#getSchoolByMonthMetrics()}.
	 */
	@Test
	public void testGetSchoolByMonthMetrics() {
		@SuppressWarnings("rawtypes")
		ResponseEntity<ApiResponse> response = template.getForEntity("/dashboard/schoolbymonth", ApiResponse.class);
		Assert.assertNotNull("Number of schools using Greenstar application per month metrics data:", response.getBody());
	}

	/**
	 * Test method for {@link com.cognizant.outreach.microservices.perfdata.controller.PerformanceMetricsController#getTotalNoOfSchools()}.
	 */
	@Test
	public void testGetTotalNoOfSchools() {
		@SuppressWarnings("rawtypes")
		ResponseEntity<ApiResponse> response = template.getForEntity("/dashboard/totalschools", ApiResponse.class);
		Assert.assertNotNull("Number of schools using Greenstar application per month metrics data:", response.getBody());
	}

	/**
	 * Test method for {@link com.cognizant.outreach.microservices.perfdata.controller.PerformanceMetricsController#getTopPerformingSchools()}.
	 */
	@Test
	public void testGetTopPerformingSchools() {
		@SuppressWarnings("rawtypes")
		ResponseEntity<ApiResponse> response = template.getForEntity("/dashboard/topschools", ApiResponse.class);
		Assert.assertNotNull("Top schools in Greenstar application:", response.getBody());
	}

	/**
	 * Test method for {@link com.cognizant.outreach.microservices.perfdata.controller.PerformanceMetricsController#getTopPerformingVolunteers()}.
	 */
	@Test
	public void testGetTopPerformingVolunteers() {
		@SuppressWarnings("rawtypes")
		ResponseEntity<ApiResponse> response = template.getForEntity("/dashboard/topvolunteers", ApiResponse.class);
		Assert.assertNotNull("Top volunteers contributing to Greenstar application:", response.getBody());
	}

	
}

