/**
 * ${BulkUploadPerfDataServiceImpl}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  18/Feb/2019            Panneer        	Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.perfdata.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.MeasurableParamData;
import com.cognizant.outreach.entity.StudentSchoolAssoc;
import com.cognizant.outreach.microservices.perfdata.dao.PerformanceDataDAOImpl;
import com.cognizant.outreach.microservices.perfdata.helper.ExcelTemplateReadHelper;
import com.cognizant.outreach.microservices.perfdata.helper.ExcelTemplateWriteHelper;
import com.cognizant.outreach.microservices.perfdata.helper.PerformanceDataHelper;
import com.cognizant.outreach.microservices.perfdata.helper.TemplateValidator;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataTableVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDayVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceRowVO;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.microservices.perfdata.vo.TemplateError;
import com.cognizant.outreach.util.DateUtil;
import com.cognizant.outreach.util.modal.ApiResponse;

/**
 * BulkUploadPerfDataServiceImpl class to handle the intermediate between controller and DAO layer and it handle business logic. 
 * 
 * @author Panneer
 */
@Service
public class BulkUploadPerfDataServiceImpl implements BulkUploadPerfDataService {
	
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	
	@Autowired
	private PerformanceDataDAOImpl performanceDataDAO;

	@Autowired
	private PerformanceDataHelper performanceDataHelper;

	@Autowired
	private ExcelTemplateWriteHelper excelTemplateWriteHelper;

	@Autowired
	private ExcelTemplateReadHelper excelTemplateReadHelper;
	
	@Autowired
	private TemplateValidator templateValidator;

	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.service.BulkUploadPerfDataService#downloadTemplate(com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData)
	 */
	@Override
	public byte[] downloadTemplate(SearchPerformanceData searchPerformanceData) throws IOException {
		
		// Fetch student details based on school, class and section.
		List<Object[]> studentList = performanceDataDAO.listOfStudentDetailBySearchParam(searchPerformanceData);
		
		// Fetch measurable parameters
		List<MeasurableParam> measurableParamList = performanceDataDAO.listOfMeasurableParamBySchoolId(searchPerformanceData.getSchoolId());
		
		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
		performanceDataTableVO.setSchoolId(searchPerformanceData.getSchoolId());
		
		// Fetch school Name by school id (to show in excel sheet)
		performanceDataTableVO.setSchoolName(performanceDataDAO.findSchoolNameBySchoolId(searchPerformanceData.getSchoolId()));
		performanceDataTableVO.setClassId(searchPerformanceData.getClassId());
		
		// Fetch class Name by class id (to show in excel sheet)
		performanceDataTableVO.setClassName(performanceDataDAO.findClassNameByClassId(searchPerformanceData.getClassId()));
		performanceDataTableVO.setMonth(searchPerformanceData.getMonth());
		performanceDataTableVO.setMonthName(DateUtil.getMonthName(searchPerformanceData.getMonth()));
		performanceDataTableVO.setWeek(searchPerformanceData.getWeek());
		performanceDataTableVO.setTotalSubTitle(measurableParamList.size());
		
		// Individual dates for given school id, month and current year.
		List<String> monthDates = performanceDataHelper.getMonthLevelWorkingDays(searchPerformanceData.getSchoolId(), DateUtil.getCurrentYear(), searchPerformanceData.getMonth());
		
		// Performance data table header row formation
		performanceDataTableVO.setHeaders(performanceDataHelper.getPerformanceHeaderForMonth(measurableParamList, searchPerformanceData, monthDates));
		
		// Performance data table content formation (create empty metric data)
		performanceDataTableVO.setPerformanceRows(performanceDataHelper.buildCreatePerformanceRow(searchPerformanceData, studentList, measurableParamList, monthDates));
		
		// Generate the excel template based on data
		return excelTemplateWriteHelper.getExcelTemplateFile(performanceDataTableVO);
	}

	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.perfdata.service.BulkUploadPerfDataService#uploadTemplate(org.springframework.web.multipart.MultipartFile, java.lang.String)
	 */
	@Override
	public ApiResponse<Object> uploadTemplate(MultipartFile multipartFile, String userId) throws IOException {
		
		ApiResponse<Object> aprResponse = null;
		List<TemplateError> errorMessages = new ArrayList<>();
		
		try (Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream())) {
			
			// Excel file validation for sheet(1 sheet) search parameters
			errorMessages = templateValidator.validateTemplateSrchParam(workbook);
			
			if(errorMessages.size() <= 0) {
				// Read constant search parameter from uploaded bulk upload file.
				SearchPerformanceData searchPerformanceData = excelTemplateReadHelper.getSearchParamFromTemplate(workbook);
				
				// Populate the performance measurement date for validation purpose.
				List<String> monthDates = performanceDataHelper.getMonthLevelWorkingDays(searchPerformanceData.getSchoolId(), DateUtil.getCurrentYear(), searchPerformanceData.getMonth());
				
				searchPerformanceData.setStartDate(monthDates.get(0));
				searchPerformanceData.setEndDate(monthDates.get(monthDates.size()-1));
				
				// To check performance data already exist / not for specific week.
				boolean isExist = performanceDataDAO.isExistOfMeasurableParamObjectBySearchParam(searchPerformanceData);
				if(!isExist) {
					// Fetch student list to build PerformanceDataTableVO object
					List<Object[]> studentList = performanceDataDAO.listOfStudentDetailBySearchParam(searchPerformanceData);
					List<String> rollNoList = performanceDataHelper.getStudentRollIds(studentList);
					
					// Fetch measurable parameter list based on school id.
					List<MeasurableParam> measurableParamList = performanceDataDAO.listOfMeasurableParamBySchoolId(searchPerformanceData.getSchoolId());
					
					// Form the map object hold key pair value for measurable parameter title and object.
					Map<String, MeasurableParam> measurableParamMap = performanceDataHelper.getPerformanceParamMap(measurableParamList);
					
					// Excel file validation for sheet(2 sheet) header and data rows.
					errorMessages = templateValidator.validateTemplateFile(workbook, rollNoList, measurableParamMap, monthDates);
					
					if(errorMessages.size() <= 0) {
						PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
						performanceDataTableVO.setSchoolId(searchPerformanceData.getSchoolId());						
						performanceDataTableVO.setClassId(searchPerformanceData.getClassId());
						performanceDataTableVO.setMonth(searchPerformanceData.getMonth());
						performanceDataTableVO.setWeek(searchPerformanceData.getWeek());
						performanceDataTableVO.setTotalSubTitle(measurableParamList.size());
						performanceDataTableVO.setUserId(userId);
						performanceDataTableVO.setPerformanceRows(performanceDataHelper.buildCreatePerformanceRow(searchPerformanceData, studentList, measurableParamList, monthDates));
						
						// Read data from bulk uploaded template file
						Map<String, Map<String, String>> templateDataMap = excelTemplateReadHelper.getExcelTemplateData(workbook);
						
						String message = saveMeasurableParameter(performanceDataTableVO, measurableParamMap, templateDataMap);
						aprResponse = new ApiResponse<>(HttpStatus.OK.value(), message, null);
					} else {
						aprResponse = new ApiResponse<>(HttpStatus.OK.value(), null, errorMessages);
					}
				} else {
					errorMessages.add(new TemplateError(null, "", "Performance metric data already exist for expected date range"));
					aprResponse = new ApiResponse<>(HttpStatus.OK.value(), null, errorMessages);
				}
			} else {
				aprResponse = new ApiResponse<>(HttpStatus.OK.value(), null, errorMessages);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aprResponse;
	}
	
	/**
	 * Method to process and save the uploaded excel data(measurable parameters and values) 
	 * @param performanceDataTableVO
	 * @param measurableParamMap
	 * @param templateDataMap
	 * @return String
	 */
	private String saveMeasurableParameter(PerformanceDataTableVO performanceDataTableVO, Map<String, MeasurableParam> measurableParamMap, Map<String, Map<String, String>> templateDataMap) {
		
		Date currentDate = new Date();
		
		// Fetch student school association object list based on search parameter(school id, class name) 
		List<StudentSchoolAssoc> studentSchoolAssocList = performanceDataDAO.listOfStudentSchoolAssocBySearchParam(performanceDataTableVO);
		Map<String, StudentSchoolAssoc> studentSchoolAssocMap = performanceDataHelper.getStudentSchoolAssocMap(studentSchoolAssocList);
		
		try {
			for(PerformanceRowVO performanceRowVO : performanceDataTableVO.getPerformanceRows()) {
				
				Map<String, String> rowDataMap = templateDataMap.get(performanceRowVO.getRollId());
				
				for(PerformanceDayVO performanceDayVO : performanceRowVO.getPerformanceDays()) {
					for(PerformanceDataVO performanceDataVO : performanceDayVO.getPerformanceData()) {
						MeasurableParamData measurableParamData = new MeasurableParamData();
						
						measurableParamData.setMeasurableDate(DateUtil.getParseDateObject(performanceDayVO.getDateValue()));
						measurableParamData.setMeasurableParam(measurableParamMap.get(performanceDataVO.getKey()));
						
						// Update the excel measurable parameter value.
						measurableParamData.setMeasurableParamValue(Integer.parseInt(rowDataMap.get(performanceDayVO.getDateValue()+performanceDataVO.getKey())));
						
						measurableParamData.setStudentSchoolAssoc(studentSchoolAssocMap.get(performanceRowVO.getRollId()));
						measurableParamData.setCreatedDtm(currentDate);
						measurableParamData.setCreatedUserId(performanceDataTableVO.getUserId());
						measurableParamData.setLastUpdatedDtm(currentDate);
						measurableParamData.setLastUpdatedUserId(performanceDataTableVO.getUserId());
						
						performanceDataDAO.saveOrUpdateMeasurableParamData(measurableParamData);
					}
				}
			}
		} catch(Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}
	
}
