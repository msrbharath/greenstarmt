/**
 * ${BulkUploadPerfDataServiceTest}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  27/Feb/2019            Panneer        	Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.perfdata.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
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
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceHeaderVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceRowVO;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.microservices.perfdata.vo.TemplateError;
import com.cognizant.outreach.util.DateUtil;
import com.cognizant.outreach.util.modal.ApiResponse;

public class BulkUploadPerfDataServiceTest {
	
	@InjectMocks
	private ExcelTemplateReadHelper templateReadHelper = new ExcelTemplateReadHelper();
	
	@InjectMocks
	private TemplateValidator excelValidator = new TemplateValidator();
	
	@InjectMocks
	private PerformanceDataHelper performanceHelper = new PerformanceDataHelper();
	
	@InjectMocks
	private BulkUploadPerfDataService bulkUploadPerfDataService = new BulkUploadPerfDataServiceImpl();

	@Mock
	private PerformanceDataHelper performanceDataHelper;

	@Mock
	private PerformanceDataDAOImpl performanceDataDAO;
	
	@Mock
	private ExcelTemplateWriteHelper excelTemplateWriteHelper;
	
	@Mock
	private ExcelTemplateReadHelper excelTemplateReadHelper;
	
	@Mock
	private TemplateValidator templateValidator;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestPerformanceData_DownloadTemplate() throws ParseException, IOException {

		SearchPerformanceData searchPerformanceData = getSearchParamValue();
		
		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
		performanceDataTableVO.setSchoolId(searchPerformanceData.getSchoolId());
		performanceDataTableVO.setSchoolName(searchPerformanceData.getSchoolName());
		performanceDataTableVO.setClassId(searchPerformanceData.getClassId());
		performanceDataTableVO.setClassName(searchPerformanceData.getClassName());
		performanceDataTableVO.setMonth(searchPerformanceData.getMonth());
		performanceDataTableVO.setMonthName(DateUtil.getMonthName(searchPerformanceData.getMonth()));
		performanceDataTableVO.setWeek(searchPerformanceData.getWeek());
		performanceDataTableVO.setTotalSubTitle(3);
		
		when(performanceDataDAO.listOfStudentDetailBySearchParam(Mockito.any(SearchPerformanceData.class))).thenReturn(getStudentData());
		when(performanceDataDAO.listOfMeasurableParamBySchoolId(1L)).thenReturn(getMeasurableParams());
		when(performanceDataHelper.getMonthLevelWorkingDays(1l, DateUtil.getCurrentYear(), 2)).thenReturn(getAllDatesForMonth());
		when(performanceDataHelper.getPerformanceHeaderForMonth(Mockito.any(List.class), Mockito.any(SearchPerformanceData.class), Mockito.any(List.class))).thenReturn(getPerformanceHeaderForMonth());
		when(performanceDataHelper.buildCreatePerformanceRow(Mockito.any(SearchPerformanceData.class), Mockito.any(List.class), Mockito.any(List.class), Mockito.any(List.class))).thenReturn(buildCreatePerformanceRow());
		when(excelTemplateWriteHelper.getExcelTemplateFile(Mockito.any(PerformanceDataTableVO.class))).thenReturn(getExcelByteArray());
		
		byte[] byteArray = bulkUploadPerfDataService.downloadTemplate(searchPerformanceData);
		
		assertNotNull("Performance bulk upload template created successfully", byteArray);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestPerformanceDataBulkUpload_Success() throws Exception {

		SearchPerformanceData searchPerformanceDataTemp = getSearchParamValue();
		
		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
		performanceDataTableVO.setSchoolId(searchPerformanceDataTemp.getSchoolId());
		performanceDataTableVO.setSchoolName(searchPerformanceDataTemp.getSchoolName());
		performanceDataTableVO.setClassId(searchPerformanceDataTemp.getClassId());
		performanceDataTableVO.setClassName(searchPerformanceDataTemp.getClassName());
		performanceDataTableVO.setMonth(searchPerformanceDataTemp.getMonth());
		performanceDataTableVO.setMonthName(DateUtil.getMonthName(searchPerformanceDataTemp.getMonth()));
		performanceDataTableVO.setWeek(searchPerformanceDataTemp.getWeek());
		performanceDataTableVO.setTotalSubTitle(3);
		
		File file = new File("src/test/resources/test_bulk_upload_template.xlsx");
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/vnd.ms-excel", IOUtils.toByteArray(input));
	    Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
	    		
		when(templateValidator.validateTemplateSrchParam(Mockito.any(Workbook.class))).thenReturn(validationForSrchParmTemplate(workbook));
		when(excelTemplateReadHelper.getSearchParamFromTemplate(Mockito.any(Workbook.class))).thenReturn(templateReadHelper.getSearchParamFromTemplate(workbook));
		when(performanceDataHelper.getMonthLevelWorkingDays(Mockito.any(Long.class), Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(getAllDatesForMonth());
		when(performanceDataDAO.isExistOfMeasurableParamObjectBySearchParam(Mockito.any(SearchPerformanceData.class))).thenReturn(isMeasurableAlreadyExist());
		when(performanceDataDAO.listOfStudentDetailBySearchParam(Mockito.any(SearchPerformanceData.class))).thenReturn(getStudentData());
		when(performanceDataHelper.getStudentRollIds(Mockito.any(List.class))).thenReturn(getStudentRollIds());
		when(performanceDataDAO.listOfMeasurableParamBySchoolId(Mockito.any(Long.class))).thenReturn(getMeasurableParams());
		when(performanceDataHelper.getPerformanceParamMap(Mockito.any(List.class))).thenReturn(getPerformanceParamMap());
		when(templateValidator.validateTemplateFile(Mockito.any(Workbook.class), Mockito.any(List.class), Mockito.any(Map.class), Mockito.any(List.class))).thenReturn(validationTemplateFile(workbook));
		when(performanceDataHelper.buildCreatePerformanceRow(Mockito.any(SearchPerformanceData.class), Mockito.any(List.class), Mockito.any(List.class), Mockito.any(List.class))).thenReturn(buildCreatePerformanceRow());
		when(excelTemplateReadHelper.getExcelTemplateData(Mockito.any(Workbook.class))).thenReturn(templateReadHelper.getExcelTemplateData(workbook));
		
		when(performanceDataDAO.listOfStudentSchoolAssocBySearchParam(Mockito.any(PerformanceDataTableVO.class))).thenReturn(getStudentSchoolAssocList());
		when(performanceDataHelper.getStudentSchoolAssocMap(Mockito.any(List.class))).thenReturn(getStudentSchoolAssocMap());
		when(performanceDataDAO.saveOrUpdateMeasurableParamData(Mockito.any(MeasurableParamData.class))).thenReturn(getMeasurableParamData());
		
		ApiResponse<Object> response = bulkUploadPerfDataService.uploadTemplate(multipartFile, "534556");
		
		assertNotNull("Performance bulk upload successfuly completed", response);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestPerformanceDataBulkUpload_SearchParmError() throws Exception {

		SearchPerformanceData searchPerformanceDataTemp = getSearchParamValue();
		
		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
		performanceDataTableVO.setSchoolId(searchPerformanceDataTemp.getSchoolId());
		performanceDataTableVO.setSchoolName(searchPerformanceDataTemp.getSchoolName());
		performanceDataTableVO.setClassId(searchPerformanceDataTemp.getClassId());
		performanceDataTableVO.setClassName(searchPerformanceDataTemp.getClassName());
		performanceDataTableVO.setMonth(searchPerformanceDataTemp.getMonth());
		performanceDataTableVO.setMonthName(DateUtil.getMonthName(searchPerformanceDataTemp.getMonth()));
		performanceDataTableVO.setWeek(searchPerformanceDataTemp.getWeek());
		performanceDataTableVO.setTotalSubTitle(3);
		
		File file = new File("src/test/resources/test_bulk_upload_template.xlsx");
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/vnd.ms-excel", IOUtils.toByteArray(input));
	    Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
	    
		when(templateValidator.validateTemplateSrchParam(Mockito.any(Workbook.class))).thenReturn(validationForSrchParmTemplateWithError());
		when(excelTemplateReadHelper.getSearchParamFromTemplate(Mockito.any(Workbook.class))).thenReturn(getSearchParamValue());
		when(performanceDataHelper.getMonthLevelWorkingDays(1l, DateUtil.getCurrentYear(), 2)).thenReturn(getAllDatesForMonth());
		when(performanceDataDAO.isExistOfMeasurableParamObjectBySearchParam(Mockito.any(SearchPerformanceData.class))).thenReturn(isMeasurableAlreadyExist());
		when(performanceDataDAO.listOfStudentDetailBySearchParam(Mockito.any(SearchPerformanceData.class))).thenReturn(getStudentData());
		when(performanceDataHelper.getStudentRollIds(Mockito.any(List.class))).thenReturn(getStudentRollIds());
		when(performanceDataDAO.listOfMeasurableParamBySchoolId(1L)).thenReturn(getMeasurableParams());
		when(performanceDataHelper.getPerformanceParamMap(Mockito.any(List.class))).thenReturn(getPerformanceParamMap());
		when(templateValidator.validateTemplateFile(Mockito.any(Workbook.class), Mockito.any(List.class), Mockito.any(Map.class), Mockito.any(List.class))).thenReturn(validationForSrchParmTemplate(workbook));
		when(performanceDataHelper.buildCreatePerformanceRow(Mockito.any(SearchPerformanceData.class), Mockito.any(List.class), Mockito.any(List.class), Mockito.any(List.class))).thenReturn(buildCreatePerformanceRow());
		when(excelTemplateReadHelper.getExcelTemplateData(Mockito.any(Workbook.class))).thenReturn(getExcelTemplateData());
		
		when(performanceDataDAO.listOfStudentSchoolAssocBySearchParam(Mockito.any(PerformanceDataTableVO.class))).thenReturn(getStudentSchoolAssocList());
		when(performanceDataHelper.getStudentSchoolAssocMap(Mockito.any(List.class))).thenReturn(getStudentSchoolAssocMap());
		when(performanceDataDAO.saveOrUpdateMeasurableParamData(Mockito.any(MeasurableParamData.class))).thenReturn(getMeasurableParamData());
		
		ApiResponse<Object> response = bulkUploadPerfDataService.uploadTemplate(multipartFile, "534556");
		
		assertNotNull("Performance Measurable Data - Search Parameter Error", response);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestPerformanceDataBulkUpload_ExistError() throws Exception {

		SearchPerformanceData searchPerformanceDataTemp = getSearchParamValue();
		
		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
		performanceDataTableVO.setSchoolId(searchPerformanceDataTemp.getSchoolId());
		performanceDataTableVO.setSchoolName(searchPerformanceDataTemp.getSchoolName());
		performanceDataTableVO.setClassId(searchPerformanceDataTemp.getClassId());
		performanceDataTableVO.setClassName(searchPerformanceDataTemp.getClassName());
		performanceDataTableVO.setMonth(searchPerformanceDataTemp.getMonth());
		performanceDataTableVO.setMonthName(DateUtil.getMonthName(searchPerformanceDataTemp.getMonth()));
		performanceDataTableVO.setWeek(searchPerformanceDataTemp.getWeek());
		performanceDataTableVO.setTotalSubTitle(3);
		
		File file = new File("src/test/resources/test_bulk_upload_template.xlsx");
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/vnd.ms-excel", IOUtils.toByteArray(input));
	    Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
	    
		when(templateValidator.validateTemplateSrchParam(Mockito.any(Workbook.class))).thenReturn(validationForSrchParmTemplate(workbook));
		when(excelTemplateReadHelper.getSearchParamFromTemplate(Mockito.any(Workbook.class))).thenReturn(getSearchParamValue());
		when(performanceDataHelper.getMonthLevelWorkingDays(1l, DateUtil.getCurrentYear(), 2)).thenReturn(getAllDatesForMonth());
		when(performanceDataDAO.isExistOfMeasurableParamObjectBySearchParam(Mockito.any(SearchPerformanceData.class))).thenReturn(isMeasurableAlreadyExistWithError());
		when(performanceDataDAO.listOfStudentDetailBySearchParam(Mockito.any(SearchPerformanceData.class))).thenReturn(getStudentData());
		when(performanceDataHelper.getStudentRollIds(Mockito.any(List.class))).thenReturn(getStudentRollIds());
		when(performanceDataDAO.listOfMeasurableParamBySchoolId(1L)).thenReturn(getMeasurableParams());
		when(performanceDataHelper.getPerformanceParamMap(Mockito.any(List.class))).thenReturn(getPerformanceParamMap());
		when(templateValidator.validateTemplateFile(Mockito.any(Workbook.class), Mockito.any(List.class), Mockito.any(Map.class), Mockito.any(List.class))).thenReturn(validationForSrchParmTemplate(workbook));
		when(performanceDataHelper.buildCreatePerformanceRow(Mockito.any(SearchPerformanceData.class), Mockito.any(List.class), Mockito.any(List.class), Mockito.any(List.class))).thenReturn(buildCreatePerformanceRow());
		when(excelTemplateReadHelper.getExcelTemplateData(Mockito.any(Workbook.class))).thenReturn(getExcelTemplateData());
		
		when(performanceDataDAO.listOfStudentSchoolAssocBySearchParam(Mockito.any(PerformanceDataTableVO.class))).thenReturn(getStudentSchoolAssocList());
		when(performanceDataHelper.getStudentSchoolAssocMap(Mockito.any(List.class))).thenReturn(getStudentSchoolAssocMap());
		when(performanceDataDAO.saveOrUpdateMeasurableParamData(Mockito.any(MeasurableParamData.class))).thenReturn(getMeasurableParamData());
		
		
		ApiResponse<Object> response = bulkUploadPerfDataService.uploadTemplate(multipartFile, "534556");
		
		assertNotNull("Performance bulk upload - already exist for specific month", response);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestPerformanceDataBulkUpload_DataError() throws Exception {

		SearchPerformanceData searchPerformanceDataTemp = getSearchParamValue();
		
		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
		performanceDataTableVO.setSchoolId(searchPerformanceDataTemp.getSchoolId());
		performanceDataTableVO.setSchoolName(searchPerformanceDataTemp.getSchoolName());
		performanceDataTableVO.setClassId(searchPerformanceDataTemp.getClassId());
		performanceDataTableVO.setClassName(searchPerformanceDataTemp.getClassName());
		performanceDataTableVO.setMonth(searchPerformanceDataTemp.getMonth());
		performanceDataTableVO.setMonthName(DateUtil.getMonthName(searchPerformanceDataTemp.getMonth()));
		performanceDataTableVO.setWeek(searchPerformanceDataTemp.getWeek());
		performanceDataTableVO.setTotalSubTitle(3);
		
		File file = new File("src/test/resources/test_bulk_upload_template.xlsx");
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/vnd.ms-excel", IOUtils.toByteArray(input));
	    Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
	    
		when(templateValidator.validateTemplateSrchParam(Mockito.any(Workbook.class))).thenReturn(validationForSrchParmTemplate(workbook));
		when(excelTemplateReadHelper.getSearchParamFromTemplate(Mockito.any(Workbook.class))).thenReturn(getSearchParamValue());
		when(performanceDataHelper.getMonthLevelWorkingDays(1l, DateUtil.getCurrentYear(), 2)).thenReturn(getAllDatesForMonth());
		when(performanceDataDAO.isExistOfMeasurableParamObjectBySearchParam(Mockito.any(SearchPerformanceData.class))).thenReturn(isMeasurableAlreadyExist());
		when(performanceDataDAO.listOfStudentDetailBySearchParam(Mockito.any(SearchPerformanceData.class))).thenReturn(getStudentData());
		when(performanceDataHelper.getStudentRollIds(Mockito.any(List.class))).thenReturn(getStudentRollIds());
		when(performanceDataDAO.listOfMeasurableParamBySchoolId(1L)).thenReturn(getMeasurableParams());
		when(performanceDataHelper.getPerformanceParamMap(Mockito.any(List.class))).thenReturn(getPerformanceParamMap());
		when(templateValidator.validateTemplateFile(Mockito.any(Workbook.class), Mockito.any(List.class), Mockito.any(Map.class), Mockito.any(List.class))).thenReturn(validationForDataTemplateWithError());
		when(performanceDataHelper.buildCreatePerformanceRow(Mockito.any(SearchPerformanceData.class), Mockito.any(List.class), Mockito.any(List.class), Mockito.any(List.class))).thenReturn(buildCreatePerformanceRow());
		when(excelTemplateReadHelper.getExcelTemplateData(Mockito.any(Workbook.class))).thenReturn(getExcelTemplateData());
		
		when(performanceDataDAO.listOfStudentSchoolAssocBySearchParam(Mockito.any(PerformanceDataTableVO.class))).thenReturn(getStudentSchoolAssocList());
		when(performanceDataHelper.getStudentSchoolAssocMap(Mockito.any(List.class))).thenReturn(getStudentSchoolAssocMap());
		when(performanceDataDAO.saveOrUpdateMeasurableParamData(Mockito.any(MeasurableParamData.class))).thenReturn(getMeasurableParamData());
		
		ApiResponse<Object> response = bulkUploadPerfDataService.uploadTemplate(multipartFile, "534556");
		
		assertNotNull("Performance bulk upload template - Data sheet error.", response);
	}
	
	private SearchPerformanceData getSearchParamValue() {
		
		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		searchPerformanceData.setSchoolId(1l);
		searchPerformanceData.setSchoolName("SSVM Matriculation School");

		searchPerformanceData.setClassId(1l);
		searchPerformanceData.setClassName("I");
		searchPerformanceData.setSectionName("A");
		searchPerformanceData.setMonth(2);
		searchPerformanceData.setWeek("18-Feb-2019~19-Feb-2019~20-Feb-2019");
		
		return searchPerformanceData;
	}
		
	private List<MeasurableParam> getMeasurableParams() {

		List<MeasurableParam> measurableParams = new ArrayList<MeasurableParam>();

		MeasurableParam measurableParam = new MeasurableParam();
		measurableParam.setId(1L);
		measurableParam.setParameterTitle("Attendance");
		measurableParam.setParameterDesc("Attendance");
		measurableParams.add(measurableParam);

		measurableParam = new MeasurableParam();
		measurableParam.setId(2L);
		measurableParam.setParameterTitle("HomeWork");
		measurableParam.setParameterDesc("HomeWork");
		measurableParams.add(measurableParam);

		measurableParam = new MeasurableParam();
		measurableParam.setId(3L);
		measurableParam.setParameterTitle("Discipline");
		measurableParam.setParameterDesc("Discipline");
		measurableParams.add(measurableParam);
		return measurableParams;
	}
	
	private List<Object[]> getStudentData() throws ParseException {

		List<Object[]> studentList = new ArrayList<>();
		
		Object[] data1 = new Object[] { "LKG001", "Jishnu", "SSVM Matriculation School", "I", "A", "Red"};
		Object[] data2 = new Object[] { "LKG002", "Akil", "SSVM Matriculation School", "I", "A", "Red"};
		Object[] data3 = new Object[] { "LKG003", "Anu", "SSVM Matriculation School", "I", "A", "Red"};
		
		studentList.add(data1);
		studentList.add(data2);
		studentList.add(data3);
		
		return studentList;
	}
	
	private MeasurableParamData getMeasurableParamData() throws ParseException {
		
		MeasurableParamData measurableParamData = new MeasurableParamData();
		
		measurableParamData.setMeasurableDate(DateUtil.getParseDateObject("18-Feb-2019"));
		// Measurable parameter object refer from map
		measurableParamData.setMeasurableParam(new MeasurableParam());
		measurableParamData.setMeasurableParamValue(1);
		
		// Student object object refer from map
		measurableParamData.setStudentSchoolAssoc(new StudentSchoolAssoc());
		measurableParamData.setCreatedDtm(new Date());
		measurableParamData.setCreatedUserId("534556");
		measurableParamData.setLastUpdatedDtm(new Date());
		measurableParamData.setLastUpdatedUserId("534556");
		
		return measurableParamData;
	}
		
	public List<String> getAllDatesForMonth() {
		
		List<String> dateList = new ArrayList<>();
		
		List<LocalDate> monthDates = DateUtil.getDatesBetweenTwoDates("2019-02-01", "2019-02-28");
		for (LocalDate localDate : monthDates) {			
			dateList.add(DateUtil.getViewLocalDate(localDate));
		}
		
		return dateList;
	}
	
	/**
	 * Method to form performance main header to use in view table.
	 * 
	 * @param measurableParamList
	 * @param searchPerformanceData
	 * @return headers
	 */
	public List<PerformanceHeaderVO> getPerformanceHeaderForMonth() {
		
		List<LocalDate> monthDates = DateUtil.getDatesBetweenTwoDates("2019-02-01", "2019-02-28");
		
		List<MeasurableParam> measurableParamList = getMeasurableParams();
		
		// Sub headers(Measurable Parameters)
		List<PerformanceHeaderVO> subHeaders = getPerformanceSubHeader(measurableParamList);
		
		List<PerformanceHeaderVO> headers = new ArrayList<>();
		for (LocalDate localDate : monthDates) {			
			headers.add(new PerformanceHeaderVO(DateUtil.getViewLocalDate(localDate), DateUtil.getViewLocalDate(localDate), false, true, subHeaders));
		}
		return headers;
	}

	/**
	 * Method to form performance sub headers.
	 * 
	 * @param measurableParamList
	 * @return subHeaders
	 */
	private List<PerformanceHeaderVO> getPerformanceSubHeader(List<MeasurableParam> measurableParamList) {

		List<PerformanceHeaderVO> subHeaders = new ArrayList<>();
		for (MeasurableParam measurableParam : measurableParamList) {
			subHeaders.add(new PerformanceHeaderVO(measurableParam.getParameterTitle(), 
					measurableParam.getParameterTitle(), false, true, null));
		}
		return subHeaders;
	}
	
	/**
	 * Method to build create performance row list of objects.
	 * 
	 * @param studentList
	 * @param dateList
	 * @param measurableParamList
	 * @return performanceRows
	 * @throws ParseException 
	 */
	public List<PerformanceRowVO> buildCreatePerformanceRow() throws ParseException {
		 
		List<Object[]> studentList = getStudentData();
		List<MeasurableParam> measurableParamList = getMeasurableParams();
		List<String> dateList = getAllDatesForMonth();
		
		List<PerformanceRowVO> performanceRows = new ArrayList<>();

		for (Object[] row : studentList) {
			PerformanceRowVO performanceRowVO = new PerformanceRowVO();
			performanceRowVO.setRollId((String) row[0]);
			if (!performanceRows.contains(performanceRowVO)) {
				performanceRowVO.setStudentName((String) row[1]);
				performanceRowVO.setSchoolName((String) row[2]);
				performanceRowVO.setClassName((String) row[3]);
				performanceRowVO.setSection((String) row[4]);
				performanceRowVO.setTeamName((String) row[5]);
				buildCreatePerformanceDay(performanceRowVO, dateList, measurableParamList);

				performanceRows.add(performanceRowVO);
			}
		}
		return performanceRows;
	}

	/**
	 * Method to build the create performance day object.
	 * 
	 * @param performanceRowVO
	 * @param dateList
	 * @param measurableParamList
	 */
	private void buildCreatePerformanceDay(PerformanceRowVO performanceRowVO, List<String> dateList,
			List<MeasurableParam> measurableParamList) {

		for (String localDate : dateList) {
			PerformanceDayVO performanceDayVO = new PerformanceDayVO();
			performanceDayVO.setDateValue(localDate);
			buildCreatePerformanceData(performanceDayVO, measurableParamList);

			performanceRowVO.getPerformanceDays().add(performanceDayVO);
		}
	}

	/**
	 * Method to build the create performance data object.
	 * 
	 * @param performanceDayVO
	 * @param measurableParamList
	 */
	private void buildCreatePerformanceData(PerformanceDayVO performanceDayVO, List<MeasurableParam> measurableParamList) {

		for (MeasurableParam measurableParam : measurableParamList) {
			PerformanceDataVO performanceDataVO = new PerformanceDataVO();
			performanceDataVO.setKey(measurableParam.getParameterTitle());
			performanceDataVO.setValue(false);

			performanceDayVO.getPerformanceData().add(performanceDataVO);
		}
	}
	
	/**
	 * Method to form map object for measurable parameter.
	 * 
	 * @param measurableParamList
	 * @return measurableParamMap
	 */
	public Map<String, MeasurableParam> getPerformanceParamMap() {
		
		List<MeasurableParam> measurableParamList = getMeasurableParams();
		
		Map<String, MeasurableParam> measurableParamMap = new HashMap<String, MeasurableParam>();
		
		measurableParamList.forEach(measurableParam -> {
			measurableParamMap.put(measurableParam.getParameterTitle(), measurableParam);
		});		
		return measurableParamMap;		
	}
	
	public List<StudentSchoolAssoc> getStudentSchoolAssocList() {
		
		List<StudentSchoolAssoc> studentSchoolAssocList = new ArrayList<>();
		
		StudentSchoolAssoc student = new StudentSchoolAssoc();
		student.setRollId("LKG001");
		studentSchoolAssocList.add(student);
		
		student = new StudentSchoolAssoc();
		student.setRollId("LKG002");
		studentSchoolAssocList.add(student);
		
		student = new StudentSchoolAssoc();
		student.setRollId("LKG003");
		studentSchoolAssocList.add(student);
		
		return studentSchoolAssocList;		
	}
	
	/**
	 * Method to form student and school details to map object use in service layer.
	 * 
	 * @param studentSchoolAssocList
	 * @return studentSchoolAssocMap
	 */
	public Map<String, StudentSchoolAssoc> getStudentSchoolAssocMap() {
		
		List<StudentSchoolAssoc> studentSchoolAssocList = getStudentSchoolAssocList();
		
		Map<String, StudentSchoolAssoc> studentSchoolAssocMap = new HashMap<String, StudentSchoolAssoc>();		
		studentSchoolAssocList.forEach(studentSchoolAssoc -> {
			studentSchoolAssocMap.put(studentSchoolAssoc.getRollId(), studentSchoolAssoc);
		});		
		return studentSchoolAssocMap;		
	}
		
	/**
	 * Method to get list of roll id.
	 * @param studentList
	 * @return list
	 * @throws ParseException 
	 */
	public List<String> getStudentRollIds() throws ParseException {		
		List<Object[]> studentList = getStudentData();
		return performanceHelper.getStudentRollIds(studentList);
	}
	
	public List<TemplateError> validationForSrchParmTemplate(Workbook workbook) {		
		return excelValidator.validateTemplateSrchParam(workbook);		
	}
	
	public List<TemplateError> validationTemplateFile(Workbook workbook) throws ParseException, Exception {		
		return excelValidator.validateTemplateFile(workbook, getStudentRollIds(), getPerformanceParamMap(), getAllDatesForMonth());	
	}
	
	public List<TemplateError> validationForSrchParmTemplateWithError() {		
		List<TemplateError> errorMessages = new ArrayList<>();	
		errorMessages.add(new TemplateError(1, "Sheet-0", "School should not be empty"));
		return errorMessages;		
	}
	
	public List<TemplateError> validationForDataTemplateWithError() {		
		List<TemplateError> errorMessages = new ArrayList<>();
		errorMessages.add(new TemplateError(1, "Date in header", "Date in header should not be empty"));
		return errorMessages;		
	}
	
	private boolean isMeasurableAlreadyExist() {		
		return false;
	}
	
	private boolean isMeasurableAlreadyExistWithError() {
		
		return true;
	}
	
	public Map<String, Map<String, String>> getExcelTemplateData() throws ParseException {
		
		Map<String, Map<String, String>> excelDataMap = new HashMap<>();
		
		List<Object[]> studentList = getStudentData();
		List<String> monthDateList = getAllDatesForMonth();
		List<MeasurableParam> measurableParamList = getMeasurableParams();
		
		for(Object[] student: studentList) {			
			Map<String, String> excelMap = new HashMap<>();
			for(String date: monthDateList) {				
				for(MeasurableParam measurableParam : measurableParamList) {					
					excelMap.put(date+measurableParam.getParameterTitle(), "1");
				}
			}
			excelDataMap.put((String)student[0], excelMap);
		}		
		return excelDataMap;
	}

	public byte[] getExcelByteArray() throws IOException, ParseException {
		
		SearchPerformanceData searchPerformanceData = getSearchParamValue();
		
		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
		performanceDataTableVO.setSchoolId(searchPerformanceData.getSchoolId());
		performanceDataTableVO.setSchoolName(searchPerformanceData.getSchoolName());
		performanceDataTableVO.setClassId(searchPerformanceData.getClassId());
		performanceDataTableVO.setClassName(searchPerformanceData.getClassName());
		performanceDataTableVO.setMonth(searchPerformanceData.getMonth());
		performanceDataTableVO.setMonthName(DateUtil.getMonthName(searchPerformanceData.getMonth()));
		performanceDataTableVO.setWeek(searchPerformanceData.getWeek());
		performanceDataTableVO.setTotalSubTitle(3);
		
		performanceDataTableVO.setHeaders(getPerformanceHeaderForMonth());
		performanceDataTableVO.setPerformanceRows(buildCreatePerformanceRow());
		
		byte[] byteArray = new ExcelTemplateWriteHelper().getExcelTemplateFile(performanceDataTableVO);
		
		return byteArray;		
	}
	
}
