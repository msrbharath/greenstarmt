package com.cognizant.outreach.microservices.testutil.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestUtilServiceTest {

	MockMvc mockMvc;
	
	@Mock
	private TestUtilService testUtilService;
	
	/**
	 * Setup mock mvc
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(testUtilService).build();
	}
	
	/**
	 * To if the data created
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSchoolInsert() throws Exception {
		testUtilService.createTestData();
		testUtilService.deleteTestData();

		Assert.assertEquals(testUtilService.schoolHelper, null);
	}
}
