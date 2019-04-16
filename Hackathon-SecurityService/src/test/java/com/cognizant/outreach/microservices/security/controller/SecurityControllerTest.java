/**
 * ${SecurityControllerTest}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  18/Feb/2019            371793        Developed base code structure
 *  ---------------------------------------------------------------------------
 *//*
package com.cognizant.outreach.microservices.security.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


*//**
 * Test class for security service
 * TODO: This method can be revisited on SSO/Ldap integration if required or not
 * @author 371793
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SecurityControllerTest {
	
	MockMvc mockMvc;
	
	@Mock
	private SecurityController securityController;

	@Autowired
	private TestRestTemplate template;

	*//**
	 * Setup mock mvc
	 * 
	 * @throws Exception
	 *//*
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(securityController).build();
	}
	
	*//**
	 * To check if the passed invalid token
	 * TODO: This method can be revisited on SSO/Ldap integration if required or not
	 * @throws Exception
	 *//*
	
	@Test
	public void testValidLoginAndToken() throws Exception {
				
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		User requestUser = new User();
		requestUser.setUserName("magesh");
		requestUser.setPassword("magesh");
		
		ResponseEntity<User> response = template.postForEntity("/login", requestUser, User.class);
		
		User user = new User();
		user.setApiToken(response.getBody().getApiToken());
		user.setUserId("magesh");

		ResponseEntity<String> response1 = template.postForEntity("/validatetoken", user, String.class);

		Assert.assertEquals(HttpStatus.ACCEPTED, response1.getStatusCode());
	}	
	
	
	*//**
	 * To check if the passed invalid token
	 * TODO: This method can be revisited on SSO/Ldap integration if required or not
	 * @throws Exception
	 *//*
	
	@Test
	public void testInValidToken() throws Exception {

		User user = new User();
		user.setApiToken("12334");
		user.setUserId("magesh");

		ResponseEntity<String> response1 = template.postForEntity("/validatetoken", user, String.class);

		Assert.assertEquals(HttpStatus.UNAUTHORIZED, response1.getStatusCode());
	}
	
	
	*//**
	 * To check if the passed invalid login
	 * TODO: This method can be revisited on SSO/Ldap integration if required or not
	 * @throws Exception
	 *//*
	
	@Test
	public void testInValidLogin() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		User requestUser = new User();
		requestUser.setUserName("magesh");
		requestUser.setPassword("magesh123");
		
		ResponseEntity<User> response = template.postForEntity("/login", requestUser, User.class);

		Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	
		
}

*/