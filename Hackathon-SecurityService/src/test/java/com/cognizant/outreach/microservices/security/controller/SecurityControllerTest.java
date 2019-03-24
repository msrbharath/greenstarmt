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
 */
package com.cognizant.outreach.microservices.security.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cognizant.outreach.microservices.security.controller.SecurityController;
import com.cognizant.outreach.microservices.security.model.User;


/**
 * Test class for security service
 * 
 * @author 371793
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SecurityControllerTest {
	
	MockMvc mockMvc;
	
	@Mock
	private SecurityController securityController;

	@Autowired
	private TestRestTemplate template;

	/**
	 * Setup mock mvc
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(securityController).build();
	}

	
	/**
	 * To check if the passed invalid token
	 * 
	 * @throws Exception
	 */
	@Test
	public void testValidLoginAndToken() throws Exception {
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("userId", "magesh");
		map.add("password", "magesh");

		HttpEntity<MultiValueMap<String, String>> requestParam = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<User> response = template.postForEntity("/login", requestParam, User.class);
		
		User user = new User();
		user.setApiToken(response.getBody().getApiToken());
		user.setUserId("magesh");

		ResponseEntity<String> response1 = template.postForEntity("/validatetoken", user, String.class);

		Assert.assertEquals(HttpStatus.ACCEPTED, response1.getStatusCode());
	}
	

	
	
	/**
	 * To check if the passed invalid token
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInValidToken() throws Exception {

		User user = new User();
		user.setApiToken("12334");
		user.setUserId("magesh");

		ResponseEntity<String> response1 = template.postForEntity("/validatetoken", user, String.class);

		Assert.assertEquals(HttpStatus.UNAUTHORIZED, response1.getStatusCode());
	}
	
	
	/**
	 * To check if the passed invalid login
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInValidLogin() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("userId", "magesh");
		map.add("password", "magesh123");

		HttpEntity<MultiValueMap<String, String>> requestParam = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<User> response = template.postForEntity("/login", requestParam, User.class);

		Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	
	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}
	
}

