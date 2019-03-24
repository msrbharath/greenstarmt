/**
 * ${PerformanceDataServiceTest}
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.MeasurableParamData;
import com.cognizant.outreach.entity.StudentSchoolAssoc;
import com.cognizant.outreach.microservices.perfdata.dao.PerformanceDataDAOImpl;
import com.cognizant.outreach.microservices.perfdata.helper.PerformanceDataHelper;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataTableVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceHeaderVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceRowVO;
import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.util.DateUtil;

public class PerformanceDataServiceTest {
	
	@InjectMocks
	private PerformanceDataHelper performanceHelper = new PerformanceDataHelper();
	
	@InjectMocks
	private PerformanceDataService performanceDataService = new PerformanceDataServiceImpl();

	@Mock
	private PerformanceDataHelper performanceDataHelper;

	@Mock
	private PerformanceDataDAOImpl performanceDataDAO;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void TestPopulatePerformanceData_ExistRecordsTest() throws ParseException {

		SearchPerformanceData searchPerformanceData = getSearchParamValue();

		when(performanceDataDAO.listOfMeasurableParamBySchoolId(1L)).thenReturn(getMeasurableParams());
		when(performanceDataDAO.listOfPerformanceMetricDataBySearchParam(Mockito.any(SearchPerformanceData.class))).thenReturn(getPerformanceMetricData());
		when(performanceDataHelper.convertTildeSeprationToList(Mockito.any(String.class))).thenReturn(convertTildeSeprationToList());
		when(performanceDataHelper.getPerformanceHeaderForWeek(Mockito.any(List.class), Mockito.any(SearchPerformanceData.class), Mockito.any(List.class))).thenReturn(getPerformanceHeaderForWeek());
		when(performanceDataHelper.buildExistingPerformanceRow(Mockito.any(List.class))).thenReturn(buildExistingPerformanceRow());
		
		PerformanceDataTableVO performanceDataTableVO = performanceDataService.getExistingPerformanceData(searchPerformanceData);

		assertNotNull("Performance data already exist", performanceDataTableVO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestPopulatePerformanceData_CreateNewRecordsTest() throws ParseException {

		SearchPerformanceData searchPerformanceData = getSearchParamValue();
		
		when(performanceDataDAO.listOfStudentDetailBySearchParam(Mockito.any(SearchPerformanceData.class))).thenReturn(getStudentData());
		when(performanceDataDAO.listOfMeasurableParamBySchoolId(1L)).thenReturn(getMeasurableParams());
		when(performanceDataHelper.convertTildeSeprationToList(Mockito.any(String.class))).thenReturn(convertTildeSeprationToList());
		when(performanceDataHelper.getPerformanceHeaderForWeek(Mockito.any(List.class), Mockito.any(SearchPerformanceData.class), Mockito.any(List.class))).thenReturn(getPerformanceHeaderForWeek());
		when(performanceDataHelper.buildCreatePerformanceRow(Mockito.any(SearchPerformanceData.class), Mockito.any(List.class), Mockito.any(List.class), Mockito.any(List.class))).thenReturn(buildCreatePerformanceRow());
		
		PerformanceDataTableVO performanceDataTableVO = performanceDataService.getCreatePerformanceData(searchPerformanceData);

		assertNotNull("Performance data created or add new performance details", performanceDataTableVO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestSavePerformanceData() throws ParseException {

		PerformanceDataTableVO performanceDataTableVO = getPerformanceDataValue();
		performanceDataTableVO.setPerformanceRows(buildExistingPerformanceRow());
		
		when(performanceDataDAO.listOfMeasurableParamBySchoolId(1L)).thenReturn(getMeasurableParams());
		when(performanceDataHelper.getPerformanceParamMap(Mockito.any(List.class))).thenReturn(getPerformanceParamMap());
		when(performanceDataDAO.listOfStudentSchoolAssocBySearchParam(Mockito.any(PerformanceDataTableVO.class))).thenReturn(getStudentSchoolAssocData());
		when(performanceDataHelper.getStudentSchoolAssocMap(Mockito.any(List.class))).thenReturn(getStudentSchoolAssocMap());
		when(performanceDataDAO.saveOrUpdateMeasurableParamData(Mockito.any(MeasurableParamData.class))).thenReturn(getMeasurableParamData());		
		
		String response = performanceDataService.savePerformanceData(performanceDataTableVO);

		assertNotNull("Performance Data Saved Successfully", response);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestUpdatePerformanceData() throws ParseException {

		PerformanceDataTableVO performanceDataTableVO = getPerformanceDataValue();
		performanceDataTableVO.setPerformanceRows(buildCreatePerformanceRow());
		
		when(performanceDataDAO.listOfPerformanceMetricObjectBySearchParam(Mockito.any(PerformanceDataTableVO.class))).thenReturn(getMeasurableParamDataList());
		when(performanceDataHelper.getMeasurableParamDataMap(Mockito.any(List.class))).thenReturn(getMeasurableParamDataMap());
		when(performanceDataDAO.saveOrUpdateMeasurableParamData(Mockito.any(MeasurableParamData.class))).thenReturn(getMeasurableParamData());
		
		String response = performanceDataService.updatePerformanceData(performanceDataTableVO);

		assertNotNull("Performance Data Saved Successfully", response);
	}
	
	@Test
	public void TestWorkingWeekDaysByMonthPerformanceData() throws ParseException {
		
		SearchPerformanceData searchPerformanceData = getSearchParamValue();
		
		when(performanceDataHelper.getMonthLevelWorkingWeekDays(Mockito.any(Long.class), Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(getMonthLevelWorkingWeekDays());
		
		Map<String, String> response = performanceDataService.getWeekWorkingDaysByMonth(searchPerformanceData);

		assertNotNull("Performance Data Saved Successfully", response);
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
	
	private PerformanceDataTableVO getPerformanceDataValue() {
		
		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();
		performanceDataTableVO.setSchoolId(1l);
		performanceDataTableVO.setSchoolName("SSVM Matriculation School");

		performanceDataTableVO.setClassId(1l);
		performanceDataTableVO.setClassName("I");
		performanceDataTableVO.setMonth(2);
		performanceDataTableVO.setWeek("18-Feb-2019~19-Feb-2019~20-Feb-2019");
		
		return performanceDataTableVO;
	}
	
	private List<MeasurableParam> getMeasurableParams() {
		List<MeasurableParam> measurableParams = new ArrayList<MeasurableParam>();
		
		measurableParams.add(getMeasurableParameter(1l, "Attendance"));
		measurableParams.add(getMeasurableParameter(2l, "HomeWork"));
		measurableParams.add(getMeasurableParameter(3l, "Discipline"));

		return measurableParams;
	}
	
	private MeasurableParam getMeasurableParameter(long id, String paramTitle) {
		
		MeasurableParam measurableParam = new MeasurableParam();
		measurableParam.setId(id);
		measurableParam.setParameterTitle(paramTitle);
		measurableParam.setParameterDesc(paramTitle);
		
		return measurableParam;
	}
	
	private List<Object[]> getPerformanceMetricData() throws ParseException {

		List<Object[]> metricDataList = new ArrayList<>();
		
		Object[] data1 = new Object[] { "LKG001", "Jishnu", "SSVM Matriculation School", "I", "Red", DateUtil.getDatabaseDate("2019-02-18"), "Attendance", 1};
		Object[] data2 = new Object[] { "LKG001", "Jishnu", "SSVM Matriculation School", "I", "Red", DateUtil.getDatabaseDate("2019-02-18"), "HomeWork", 1};
		Object[] data3 = new Object[] { "LKG001", "Jishnu", "SSVM Matriculation School", "I", "Red", DateUtil.getDatabaseDate("2019-02-18"), "Discipline", 1};
		
		metricDataList.add(data1);
		metricDataList.add(data2);
		metricDataList.add(data3);
		
		return metricDataList;
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
	
	private List<StudentSchoolAssoc> getStudentSchoolAssocData() throws ParseException {

		List<StudentSchoolAssoc> studentList = new ArrayList<>();
		
		StudentSchoolAssoc studentSchoolAssoc = new StudentSchoolAssoc();
		studentSchoolAssoc.setRollId("LKG001");
		studentSchoolAssoc.setTeamName("Rose");
		studentSchoolAssoc.setStatus("Active");		
		studentList.add(studentSchoolAssoc);
		
		studentSchoolAssoc = new StudentSchoolAssoc();
		studentSchoolAssoc.setRollId("LKG002");
		studentSchoolAssoc.setTeamName("Rose");
		studentSchoolAssoc.setStatus("Active");		
		studentList.add(studentSchoolAssoc);
		
		studentSchoolAssoc = new StudentSchoolAssoc();
		studentSchoolAssoc.setRollId("LKG003");
		studentSchoolAssoc.setTeamName("Rose");
		studentSchoolAssoc.setStatus("Active");		
		studentList.add(studentSchoolAssoc);
		
		return studentList;
	}
	
	private MeasurableParamData getMeasurableParamData(long paramId, String paramName, String rollNo, String measurableDate) throws ParseException {
		
		MeasurableParamData measurableParamData = new MeasurableParamData();
		
		measurableParamData.setMeasurableDate(DateUtil.getParseDateObject(measurableDate));
		// Measurable parameter object refer from map
		measurableParamData.setMeasurableParam(getMeasurableParameter(paramId, paramName));
		measurableParamData.setMeasurableParamValue(1);
		
		// Student object object refer from map
		StudentSchoolAssoc studentSchoolAssoc = new StudentSchoolAssoc();
		studentSchoolAssoc.setRollId(rollNo);
		measurableParamData.setStudentSchoolAssoc(studentSchoolAssoc);
		
		measurableParamData.setCreatedDtm(new Date());
		measurableParamData.setCreatedUserId("534556");
		measurableParamData.setLastUpdatedDtm(new Date());
		measurableParamData.setLastUpdatedUserId("534556");
		
		return measurableParamData;
	}
	
	private MeasurableParamData getMeasurableParamData() throws ParseException {
		
		MeasurableParamData measurableParamData = new MeasurableParamData();
		
		measurableParamData.setMeasurableDate(DateUtil.getParseDateObject("18-Feb-2019"));
		// Measurable parameter object refer from map
		measurableParamData.setMeasurableParam(getMeasurableParameter(1l, "Attendance"));
		measurableParamData.setMeasurableParamValue(1);
		
		// Student object object refer from map
		StudentSchoolAssoc studentSchoolAssoc = new StudentSchoolAssoc();
		studentSchoolAssoc.setRollId("LKG001");
		measurableParamData.setStudentSchoolAssoc(studentSchoolAssoc);
		
		measurableParamData.setCreatedDtm(new Date());
		measurableParamData.setCreatedUserId("534556");
		measurableParamData.setLastUpdatedDtm(new Date());
		measurableParamData.setLastUpdatedUserId("534556");
		
		return measurableParamData;
	}
	
	private List<MeasurableParamData> getMeasurableParamDataList() throws ParseException {
		
		List<MeasurableParamData> paramDataList = new ArrayList<>();
		
		// LKG001
		paramDataList.add(getMeasurableParamData(1l, "Attendance", "LKG001", "18-Feb-2019"));
		paramDataList.add(getMeasurableParamData(2l, "HomeWork", "LKG001", "18-Feb-2019"));
		paramDataList.add(getMeasurableParamData(3l, "Discipline", "LKG001", "18-Feb-2019"));
		
		paramDataList.add(getMeasurableParamData(1l, "Attendance", "LKG001", "19-Feb-2019"));
		paramDataList.add(getMeasurableParamData(2l, "HomeWork", "LKG001", "19-Feb-2019"));
		paramDataList.add(getMeasurableParamData(3l, "Discipline", "LKG001", "19-Feb-2019"));
		
		paramDataList.add(getMeasurableParamData(1l, "Attendance", "LKG001", "20-Feb-2019"));
		paramDataList.add(getMeasurableParamData(2l, "HomeWork", "LKG001", "20-Feb-2019"));
		paramDataList.add(getMeasurableParamData(3l, "Discipline", "LKG001", "20-Feb-2019"));
		
		// LKG002
		paramDataList.add(getMeasurableParamData(1l, "Attendance", "LKG002", "18-Feb-2019"));
		paramDataList.add(getMeasurableParamData(2l, "HomeWork", "LKG002", "18-Feb-2019"));
		paramDataList.add(getMeasurableParamData(3l, "Discipline", "LKG002", "18-Feb-2019"));
		
		paramDataList.add(getMeasurableParamData(1l, "Attendance", "LKG002", "19-Feb-2019"));
		paramDataList.add(getMeasurableParamData(2l, "HomeWork", "LKG002", "19-Feb-2019"));
		paramDataList.add(getMeasurableParamData(3l, "Discipline", "LKG002", "19-Feb-2019"));
		
		paramDataList.add(getMeasurableParamData(1l, "Attendance", "LKG002", "20-Feb-2019"));
		paramDataList.add(getMeasurableParamData(2l, "HomeWork", "LKG002", "20-Feb-2019"));
		paramDataList.add(getMeasurableParamData(3l, "Discipline", "LKG002", "20-Feb-2019"));
		
		// LKG003
		paramDataList.add(getMeasurableParamData(1l, "Attendance", "LKG003", "18-Feb-2019"));
		paramDataList.add(getMeasurableParamData(2l, "HomeWork", "LKG003", "18-Feb-2019"));
		paramDataList.add(getMeasurableParamData(3l, "Discipline", "LKG003", "18-Feb-2019"));
		
		paramDataList.add(getMeasurableParamData(1l, "Attendance", "LKG003", "19-Feb-2019"));
		paramDataList.add(getMeasurableParamData(2l, "HomeWork", "LKG003", "19-Feb-2019"));
		paramDataList.add(getMeasurableParamData(3l, "Discipline", "LKG003", "19-Feb-2019"));
		
		paramDataList.add(getMeasurableParamData(1l, "Attendance", "LKG003", "20-Feb-2019"));
		paramDataList.add(getMeasurableParamData(2l, "HomeWork", "LKG003", "20-Feb-2019"));
		paramDataList.add(getMeasurableParamData(3l, "Discipline", "LKG003", "20-Feb-2019"));
		
		return paramDataList;
	}
	
	private List<String> convertTildeSeprationToList() {
		return performanceHelper.convertTildeSeprationToList("18-Feb-2019~19-Feb-2019~20-Feb-2019");		
	}
	
	/**
	 * Method to form performance main header to use in view table.
	 * 
	 * @param measurableParamList
	 * @param searchPerformanceData
	 * @return headers
	 */
	public List<PerformanceHeaderVO> getPerformanceHeaderForWeek() {
		
		return performanceHelper.getPerformanceHeaderForWeek(getMeasurableParams(), getSearchParamValue(), convertTildeSeprationToList());
	}
		
	/**
	 * Method to build existing performance row object list.
	 * 
	 * @param datas
	 * @return performanceRows
	 * @throws ParseException 
	 */
	public List<PerformanceRowVO> buildExistingPerformanceRow() throws ParseException {
		
		return performanceHelper.buildExistingPerformanceRow(getPerformanceMetricData());
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

		return performanceHelper.buildCreatePerformanceRow(null, getStudentData(), getMeasurableParams(), convertTildeSeprationToList());
	}

	/**
	 * Method to form map object for measurable parameter.
	 * 
	 * @param measurableParamList
	 * @return measurableParamMap
	 */
	public Map<String, MeasurableParam> getPerformanceParamMap() {
				
		return performanceHelper.getPerformanceParamMap(getMeasurableParams());	
	}
	
	/**
	 * Method to form student and school details to map object use in service layer.
	 * 
	 * @param studentSchoolAssocList
	 * @return studentSchoolAssocMap
	 * @throws ParseException 
	 */
	public Map<String, StudentSchoolAssoc> getStudentSchoolAssocMap() throws ParseException {
		
		return performanceHelper.getStudentSchoolAssocMap(getStudentSchoolAssocData());		
	}
	
	/**
	 * Method to form the measurable parameter data object map for easy to use in service layer.
	 *  
	 * @param measurableParamDataList
	 * @return measurableParamDataMap
	 * @throws ParseException 
	 */
	public Map<String, MeasurableParamData> getMeasurableParamDataMap() throws ParseException {
		
		return performanceHelper.getMeasurableParamDataMap(getMeasurableParamDataList());
	}
	
	public Map<String, String> getMonthLevelWorkingWeekDays() {
		
		Map<String, String> weekMap = new LinkedHashMap<>();
		weekMap.put("Week-1", "01-Feb-2019~02-Feb-2019");
		weekMap.put("Week-2", "04-Feb-2019~05-Feb-2019~06-Feb-2019~07-Feb-20199~08-Feb-2019");
		weekMap.put("Week-3", "11-Feb-2019~13-Feb-2019~06-F13-2019~07-Feb-20199~08-Feb-2019");
		weekMap.put("Week-4", "04-Feb-2019~05-Feb-2019~06-Feb-2019~07-Feb-20199~08-Feb-2019");
		weekMap.put("Week-5", "04-Feb-2019~05-Feb-2019~06-Feb-2019~07-Feb-20199~08-Feb-2019");
		
		return weekMap;		
	}
	
}
