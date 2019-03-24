/**
 * ${PerformanceStarControllerTest}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  18/Feb/2019            Magesh        Developed base code structure
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cognizant.outreach.microservices.perfdata.constants.PerfDataConstants;
import com.cognizant.outreach.microservices.perfdata.vo.star.PerformanceStarSearchDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.star.PerformanceStarVO;

/**
 * Test class for performance star controller
 * 
 * @author Magesh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PerformanceStarControllerTest {

	MockMvc mockMvc;

	@Mock
	private PerformanceStarController performanceStarController;

	@Autowired
	private TestRestTemplate template;

	/**
	 * Setup mock mvc
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(performanceStarController).build();
	}

	/**
	 * To check if the passed invalid token
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInvalidToken() throws Exception {
		PerformanceStarSearchDataVO performanceStarSearchDataVO = new PerformanceStarSearchDataVO();

		performanceStarSearchDataVO.setCalcType(PerfDataConstants.INDIVIDUAL);
		performanceStarSearchDataVO.setSchoolId(1L);
		performanceStarSearchDataVO.setClassId(1L);
		performanceStarSearchDataVO.setStudentId(1L);
		performanceStarSearchDataVO.setMonth(10);
		ResponseEntity<PerformanceStarVO> response = template.postForEntity("/perfstar/generateStar",
				performanceStarSearchDataVO, PerformanceStarVO.class);

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}


}
