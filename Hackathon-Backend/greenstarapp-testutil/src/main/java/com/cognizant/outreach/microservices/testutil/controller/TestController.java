package com.cognizant.outreach.microservices.testutil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.outreach.microservices.testutil.service.TestUtilService;

@RestController
public class TestController {

	@Autowired
	private TestUtilService testUtilService;
	
	/**
	 * To get the list of class for the given name
	 * 
	 * @return List of class if present else null
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/test")
	public ResponseEntity<String> generatePerformanceStar() throws Exception {
		testUtilService.createTestData();
		testUtilService.createMeasurableParamData();
		/*testUtilService.deleteTestData();*/

		return ResponseEntity.status(HttpStatus.OK).body("Success");
	}
}
