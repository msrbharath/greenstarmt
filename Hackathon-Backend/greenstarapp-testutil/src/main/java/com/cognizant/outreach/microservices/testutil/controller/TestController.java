package com.cognizant.outreach.microservices.testutil.controller;

import java.io.IOException;
import java.text.ParseException;

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
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/test")
	public ResponseEntity<String> generatePerformanceStar() throws ParseException, IOException {
		testUtilService.createTestData();
		testUtilService.createMeasurableParamData();
		testUtilService.deleteTestData();

		return ResponseEntity.status(HttpStatus.OK).body("Success");
	}
}
