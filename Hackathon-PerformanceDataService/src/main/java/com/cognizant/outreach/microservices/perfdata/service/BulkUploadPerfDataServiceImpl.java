/**
 * 
 */
package com.cognizant.outreach.microservices.perfdata.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.MeasurableParamData;
import com.cognizant.outreach.entity.StudentSchoolAssoc;
import com.cognizant.outreach.microservices.perfdata.dao.PerformanceDataDAOImpl;
import com.cognizant.outreach.microservices.perfdata.helper.ExcelTemplateReadHelper;
import com.cognizant.outreach.microservices.perfdata.helper.ExcelTemplateWriteHelper;
import com.cognizant.outreach.microservices.perfdata.helper.PerformanceDataHelper;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataTableVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDayVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceRowVO;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.util.DateUtil;

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

	@Override
	public byte[] downloadTemplate(SearchPerformanceData searchPerformanceData) throws IOException {

		List<Object[]> studentList = performanceDataDAO.listOfStudentDetailBySearchParam(searchPerformanceData);
		List<MeasurableParam> measurableParamList = performanceDataDAO.listOfMeasurableParamBySchoolId(searchPerformanceData.getSchoolId());
	
		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
		performanceDataTableVO.setSchoolId(searchPerformanceData.getSchoolId());
		performanceDataTableVO.setClassName(searchPerformanceData.getClassName());
		performanceDataTableVO.setSection(searchPerformanceData.getSectionName());
		performanceDataTableVO.setMonth(searchPerformanceData.getMonth());
		performanceDataTableVO.setWeek(searchPerformanceData.getWeek());
		performanceDataTableVO.setHeaders(performanceDataHelper.getPerformanceHeader(measurableParamList, searchPerformanceData));
		performanceDataTableVO.setPerformanceRows(performanceDataHelper.buildCreatePerformanceRow(searchPerformanceData, studentList, measurableParamList));

		return excelTemplateWriteHelper.getExcelTemplateFile(performanceDataTableVO);
	}

	@Override
	public String uploadTemplate(MultipartFile multipartFile) throws IOException {
		
		Date currentDate = new Date();
		
		// Read constant search parameter from uploaded bulk upload file.
		SearchPerformanceData searchPerformanceData = excelTemplateReadHelper.getSearchParamFromTemplate(multipartFile);
		
		boolean isExist = performanceDataDAO.isExistOfMeasurableParamObjectBySearchParam(searchPerformanceData);
		
		// Fetch student list to build PerformanceDataTableVO object
		List<Object[]> studentList = performanceDataDAO.listOfStudentDetailBySearchParam(searchPerformanceData);
		
		// Fetch measurable parameter list based on school id.
		List<MeasurableParam> measurableParamList = performanceDataDAO.listOfMeasurableParamBySchoolId(searchPerformanceData.getSchoolId());
		
		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
		performanceDataTableVO.setSchoolId(searchPerformanceData.getSchoolId());
		performanceDataTableVO.setClassName(searchPerformanceData.getClassName());
		performanceDataTableVO.setSection(searchPerformanceData.getSectionName());
		performanceDataTableVO.setMonth(searchPerformanceData.getMonth());
		performanceDataTableVO.setWeek(searchPerformanceData.getWeek());
		
		// performanceDataTableVO.setHeaders(performanceDataHelper.getPerformanceHeader(measurableParamList, searchPerformanceData));
		performanceDataTableVO.setPerformanceRows(performanceDataHelper.buildCreatePerformanceRow(searchPerformanceData, studentList, measurableParamList));
		
		// Read data from bulk uploaded template file
		Map<String, Map<String, String>> templateDataMap = excelTemplateReadHelper.getExcelTemplateData(multipartFile);
		
		// Form the map object hold key pair value for measurable parameter title and object.
		Map<String, MeasurableParam> measurableParamMap = performanceDataHelper.getPerformanceParamMap(measurableParamList);
		
		// Fetch student school association object list based on search parameter(school id, class name, section) 
		List<StudentSchoolAssoc> studentSchoolAssocList = performanceDataDAO.listOfStudentSchoolAssocBySearchParam(performanceDataTableVO);
		Map<String, StudentSchoolAssoc> studentSchoolAssocMap = performanceDataHelper.getStudentSchoolAssocMap(studentSchoolAssocList);
		
		// Block to update the measurable parameter value to target object.
		try {
			for(PerformanceRowVO performanceRowVO : performanceDataTableVO.getPerformanceRows()) {
				
				Map<String, String> rowDataMap = templateDataMap.get(performanceRowVO.getRollId());
				
				for(PerformanceDayVO performanceDayVO : performanceRowVO.getPerformanceDays()) {
					for(PerformanceDataVO performanceDataVO : performanceDayVO.getPerformanceData()) {
						MeasurableParamData measurableParamData = new MeasurableParamData();
						
						measurableParamData.setMeasurableDate(DateUtil.getParseDateObject(performanceDayVO.getDateValue()));
						measurableParamData.setMeasurableParam(measurableParamMap.get(performanceDataVO.getKey()));
						
						// Update the excel measurable parameter value.
						measurableParamData.setMeasurableParamValue(rowDataMap.get(performanceDayVO.getDateValue()+performanceDataVO.getKey()));
						
						measurableParamData.setStudentSchoolAssoc(studentSchoolAssocMap.get(performanceRowVO.getRollId()));
						measurableParamData.setCreatedDtm(currentDate);
						measurableParamData.setCreatedUserId(performanceDataTableVO.getUserId());
						measurableParamData.setLastUpdatedDtm(currentDate);
						measurableParamData.setLastUpdatedUserId(performanceDataTableVO.getUserId());
						
						//performanceDataDAO.saveOrUpdateMeasurableParamData(measurableParamData);
					}				
				}
			}
		} catch(Exception e) {
			return ERROR;
		}		
		return SUCCESS;
	}
	
}
