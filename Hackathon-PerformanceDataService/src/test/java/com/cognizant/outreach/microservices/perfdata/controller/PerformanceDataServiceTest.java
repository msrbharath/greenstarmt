package com.cognizant.outreach.microservices.perfdata.controller;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.outreach.microservices.perfdata.Application;
import com.cognizant.outreach.microservices.perfdata.helper.ExcelTemplateWriteHelper;
import com.cognizant.outreach.microservices.perfdata.service.BulkUploadPerfDataService;
import com.cognizant.outreach.microservices.perfdata.service.PerformanceDataService;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataTableVO;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(Application.class)
public class PerformanceDataServiceTest {

	@Autowired
	private PerformanceDataService performanceDataService;
	
	@Autowired
	private ExcelTemplateWriteHelper excelTemplateHelper;
	
	@Autowired
	private BulkUploadPerfDataService bulkUploadPerfDataService;

	@Test
	public void populatePerformanceDataExistTest() {

		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		searchPerformanceData.setSchoolId(1l);
		searchPerformanceData.setClassName("First");
		searchPerformanceData.setSectionName("A");
		searchPerformanceData.setMonth(2);
		searchPerformanceData.setWeek("2019-02-18~2019-02-22");
		
		PerformanceDataTableVO performanceDataTableVO = performanceDataService.getExistingPerformanceData(searchPerformanceData);

		System.out.println("Tested");
	}
	
	@Test
	public void populatePerformanceDataNotExistTest() {

		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		searchPerformanceData.setSchoolId(1l);
		searchPerformanceData.setClassName("First");
		searchPerformanceData.setSectionName("A");
		searchPerformanceData.setMonth(2);
		searchPerformanceData.setWeek("2019-02-18~2019-02-22");
		
		PerformanceDataTableVO performanceDataTableVO = performanceDataService.getCreatePerformanceData(searchPerformanceData);

		System.out.println("Tested");
	}
	
	@Test
	public void createAndSavePerformanceDataTest() {
		
		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		searchPerformanceData.setSchoolId(1l);
		searchPerformanceData.setClassName("First");
		searchPerformanceData.setSectionName("A");
		searchPerformanceData.setMonth(2);
		searchPerformanceData.setWeek("2019-02-18~2019-02-22");
		
		PerformanceDataTableVO performanceDataTableVO = performanceDataService.getCreatePerformanceData(searchPerformanceData);
		
		String response = performanceDataService.savePerformanceData(performanceDataTableVO);
		
		System.out.println("Create - Save");
	}
	
	@Test
	public void editAndUpdatePerformanceDataTest() {
		
		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		searchPerformanceData.setSchoolId(1l);
		searchPerformanceData.setClassName("First");
		searchPerformanceData.setSectionName("A");
		searchPerformanceData.setMonth(2);
		searchPerformanceData.setWeek("2019-02-18~2019-02-22");
		
		PerformanceDataTableVO performanceDataTableVO = performanceDataService.getExistingPerformanceData(searchPerformanceData);
		
		String response = performanceDataService.updatePerformanceData(performanceDataTableVO);
		
		System.out.println("Create - Save");
	}
	
	@Test
	public void populatePerformanceTemplateTest() throws IOException {

		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		searchPerformanceData.setSchoolId(1l);
		searchPerformanceData.setClassName("First");
		searchPerformanceData.setSectionName("A");
		searchPerformanceData.setMonth(2);
		searchPerformanceData.setWeek("2019-02-18~2019-02-22");
		
		PerformanceDataTableVO performanceDataTableVO = performanceDataService.getCreatePerformanceData(searchPerformanceData);
		
		excelTemplateHelper.getExcelTemplateFile(performanceDataTableVO);
		
		System.out.println("Tested");
	}
	
	@Test
	public void uploadPerformanceDataTemplateTest() throws IOException {

		bulkUploadPerfDataService.uploadTemplate(null);
		
		
		System.out.println("Tested");
	}
	
}
