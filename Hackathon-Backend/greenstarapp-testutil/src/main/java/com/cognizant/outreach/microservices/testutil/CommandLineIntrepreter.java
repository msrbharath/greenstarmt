package com.cognizant.outreach.microservices.testutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cognizant.outreach.microservices.testutil.service.TestUtilService;

@Component
public class CommandLineIntrepreter implements CommandLineRunner {
    @Autowired
    private TestUtilService testutilService;

    @Override
    public void run(String...args) throws Exception {
    	testutilService.createTestData();
    	testutilService.createMeasurableParamData();
    	/*testutilService.deleteTestData();*/
    }
    
    
    public void createMockData() throws Exception {
    	testutilService.createTestData();
    }
    
    public void removeMockData() throws Exception {
    	testutilService.deleteTestData();
    }
}