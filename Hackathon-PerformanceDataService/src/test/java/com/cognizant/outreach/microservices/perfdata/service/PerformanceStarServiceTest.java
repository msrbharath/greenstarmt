package com.cognizant.outreach.microservices.perfdata.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.MeasurableParamData;
import com.cognizant.outreach.microservices.perfdata.constants.PerfDataConstants;
import com.cognizant.outreach.microservices.perfdata.constants.StarColorCodes;
import com.cognizant.outreach.microservices.perfdata.helper.PerformanceDataHelper;
import com.cognizant.outreach.microservices.perfdata.repository.MeasurableParamRepository;
import com.cognizant.outreach.microservices.perfdata.repository.PerformanceDataRepository;
import com.cognizant.outreach.microservices.perfdata.vo.star.PerformanceStarSearchDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.star.PerformanceStarVO;
import com.cognizant.outreach.util.DateUtil;

public class PerformanceStarServiceTest {
	@InjectMocks
	PerformanceStarService performanceStarService = new PerformanceStarServiceImpl();
	
	@Mock
	MeasurableParamRepository measurableParamRepository;

	@Mock
	PerformanceDataRepository performanceDataRepository;
	
	@Mock
	PerformanceDataHelper performanceDataHelper;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void TestGetStarData_Individual() throws ParseException {
		when(performanceDataHelper.getHolidays(1, 10)).thenReturn(listHolidays());
		when(performanceDataHelper.getWeekendWorkingDays(1, 10)).thenReturn(workingDays());
		when(measurableParamRepository.findBySchoolId(1L)).thenReturn(getMeasurableParams());
		when(performanceDataRepository.listOfMeasurableParamDataByStudent(1L, 1L, 10)).
		thenReturn(listOfMeasurableParamDataByStudentForAttendance());
		when(performanceDataRepository.listOfMeasurableParamDataByStudent(1L, 2L, 10)).
		thenReturn(listOfMeasurableParamDataByStudentForHW());
		when(performanceDataRepository.listOfMeasurableParamDataByStudent(1L, 3L, 10)).
		thenReturn(listOfMeasurableParamDataByStudentForDiscipline());
		
		PerformanceStarSearchDataVO searchDataVO = new PerformanceStarSearchDataVO();
		searchDataVO.setCalcType(PerfDataConstants.INDIVIDUAL);
		searchDataVO.setStudentId(1L);
		searchDataVO.setSchoolId(1L);
		searchDataVO.setMonth(10);
		
		PerformanceStarVO  PerformanceStarVO =  performanceStarService.getStarData(searchDataVO).get();
		
		assertEquals(PerformanceStarVO.getParamOne(), "Attendance");
		assertEquals(PerformanceStarVO.getParamTwo(), "HomeWork");
		assertEquals(PerformanceStarVO.getParamThree(), "Discipline");
		
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[0], StarColorCodes.COMPLAINT.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[2], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[1], StarColorCodes.HOLIDAY.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[4], StarColorCodes.NO_DATA.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[8], StarColorCodes.NO_DATA.getColorCode());
		
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[0], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[2], StarColorCodes.COMPLAINT.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[1], StarColorCodes.HOLIDAY.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[8], StarColorCodes.NO_DATA.getColorCode());
		
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[0], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[2], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[1], StarColorCodes.HOLIDAY.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[8], StarColorCodes.NO_DATA.getColorCode());
	}
	
	
	@Test
	public void TestGetStarData_Team() throws ParseException {
		when(performanceDataHelper.getHolidays(1, 10)).thenReturn(listHolidays());
		when(performanceDataHelper.getWeekendWorkingDays(1, 10)).thenReturn(workingDays());
		when(measurableParamRepository.findBySchoolId(1L)).thenReturn(getMeasurableParams());
		when(performanceDataRepository.listOfMeasurableParamDataByTeam(1L, "Red", 1L, 10)).
		thenReturn(listOfMeasurableParamDataForAttendance());
		when(performanceDataRepository.listOfMeasurableParamDataByTeam(1L, "Red", 2L, 10)).
		thenReturn(listOfMeasurableParamDataForHomework());
		when(performanceDataRepository.listOfMeasurableParamDataByTeam(1L, "Red", 3L, 10)).
		thenReturn(listOfMeasurableParamDataForDiscipline());
		
		PerformanceStarSearchDataVO searchDataVO = new PerformanceStarSearchDataVO();
		searchDataVO.setCalcType(PerfDataConstants.TEAM);
		searchDataVO.setStudentId(1L);
		searchDataVO.setSchoolId(1L);
		searchDataVO.setClassId(1L);
		searchDataVO.setTeamName("Red");
		searchDataVO.setMonth(10);
		
		PerformanceStarVO  PerformanceStarVO =  performanceStarService.getStarData(searchDataVO).get();
		
		assertEquals(PerformanceStarVO.getParamOne(), "Attendance");
		assertEquals(PerformanceStarVO.getParamTwo(), "HomeWork");
		assertEquals(PerformanceStarVO.getParamThree(), "Discipline");
		
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[0], StarColorCodes.COMPLAINT.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[2], StarColorCodes.EQUAL_ABOVE_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[6], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[1], StarColorCodes.HOLIDAY.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[8], StarColorCodes.NO_DATA.getColorCode());
		
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[0], StarColorCodes.COMPLAINT.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[2], StarColorCodes.EQUAL_ABOVE_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[6], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[1], StarColorCodes.HOLIDAY.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[8], StarColorCodes.NO_DATA.getColorCode());
		
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[0], StarColorCodes.COMPLAINT.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[2], StarColorCodes.EQUAL_ABOVE_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[6], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[1], StarColorCodes.HOLIDAY.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[8], StarColorCodes.NO_DATA.getColorCode());
	}
	
	
	@Test
	public void TestGetStarData_Class() throws ParseException {
		when(performanceDataHelper.getHolidays(1, 10)).thenReturn(listHolidays());
		when(performanceDataHelper.getWeekendWorkingDays(1, 10)).thenReturn(workingDays());
		when(measurableParamRepository.findBySchoolId(1L)).thenReturn(getMeasurableParams());
		when(performanceDataRepository.listOfMeasurableParamDataByClass(1L, 1L, 10)).
		thenReturn(listOfMeasurableParamDataForAttendance());
		when(performanceDataRepository.listOfMeasurableParamDataByClass(1L,2L, 10)).
		thenReturn(listOfMeasurableParamDataForHomework());
		when(performanceDataRepository.listOfMeasurableParamDataByClass(1L,3L, 10)).
		thenReturn(listOfMeasurableParamDataForDiscipline());
		
		PerformanceStarSearchDataVO searchDataVO = new PerformanceStarSearchDataVO();
		searchDataVO.setCalcType(PerfDataConstants.CLASS);
		searchDataVO.setSchoolId(1L);
		searchDataVO.setClassId(1L);
		searchDataVO.setMonth(10);
		
		PerformanceStarVO  PerformanceStarVO =  performanceStarService.getStarData(searchDataVO).get();
		
		assertEquals(PerformanceStarVO.getParamOne(), "Attendance");
		assertEquals(PerformanceStarVO.getParamTwo(), "HomeWork");
		assertEquals(PerformanceStarVO.getParamThree(), "Discipline");
		
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[0], StarColorCodes.COMPLAINT.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[2], StarColorCodes.EQUAL_ABOVE_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[6], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[1], StarColorCodes.HOLIDAY.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[8], StarColorCodes.NO_DATA.getColorCode());
		
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[0], StarColorCodes.COMPLAINT.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[2], StarColorCodes.EQUAL_ABOVE_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[6], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[1], StarColorCodes.HOLIDAY.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[8], StarColorCodes.NO_DATA.getColorCode());
		
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[0], StarColorCodes.COMPLAINT.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[2], StarColorCodes.EQUAL_ABOVE_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[6], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[1], StarColorCodes.HOLIDAY.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[8], StarColorCodes.NO_DATA.getColorCode());
	}
	
	@Test
	public void TestGetStarData_School() throws ParseException {
		when(performanceDataHelper.getHolidays(1, 10)).thenReturn(listHolidays());
		when(performanceDataHelper.getWeekendWorkingDays(1, 10)).thenReturn(workingDays());
		when(measurableParamRepository.findBySchoolId(1L)).thenReturn(getMeasurableParams());
		when(performanceDataRepository.listOfMeasurableParamDataBySchool(1L, 1L, 10)).
		thenReturn(listOfMeasurableParamDataForAttendance());
		when(performanceDataRepository.listOfMeasurableParamDataBySchool(1L, 2L, 10)).
		thenReturn(listOfMeasurableParamDataForHomework());
		when(performanceDataRepository.listOfMeasurableParamDataBySchool(1L,3L, 10)).
		thenReturn(listOfMeasurableParamDataForDiscipline());
		
		PerformanceStarSearchDataVO searchDataVO = new PerformanceStarSearchDataVO();
		searchDataVO.setCalcType(PerfDataConstants.SCHOOL);
		searchDataVO.setSchoolId(1L);
		searchDataVO.setMonth(10);
		
		PerformanceStarVO  PerformanceStarVO =  performanceStarService.getStarData(searchDataVO).get();
		
		assertEquals(PerformanceStarVO.getParamOne(), "Attendance");
		assertEquals(PerformanceStarVO.getParamTwo(), "HomeWork");
		assertEquals(PerformanceStarVO.getParamThree(), "Discipline");
		
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[0], StarColorCodes.COMPLAINT.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[2], StarColorCodes.EQUAL_ABOVE_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[6], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[1], StarColorCodes.HOLIDAY.getColorCode());
		assertEquals(PerformanceStarVO.getParamOneMonthColorCodes()[8], StarColorCodes.NO_DATA.getColorCode());
		
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[0], StarColorCodes.COMPLAINT.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[2], StarColorCodes.EQUAL_ABOVE_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[6], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[1], StarColorCodes.HOLIDAY.getColorCode());
		assertEquals(PerformanceStarVO.getParamTwoMonthColorCodes()[8], StarColorCodes.NO_DATA.getColorCode());
		
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[0], StarColorCodes.COMPLAINT.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[2], StarColorCodes.EQUAL_ABOVE_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[6], StarColorCodes.BELOW_75.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[1], StarColorCodes.HOLIDAY.getColorCode());
		assertEquals(PerformanceStarVO.getParamThreeMonthColorCodes()[8], StarColorCodes.NO_DATA.getColorCode());
	}
	private Optional<List<MeasurableParam>> getMeasurableParams(){
		List<MeasurableParam> measurableParams = new ArrayList<MeasurableParam>();
		MeasurableParam measurableParam= new MeasurableParam();
		measurableParam.setId(1L);
		measurableParam.setParameterTitle("Attendance");
		measurableParam.setParameterDesc("Attendance");
		measurableParams.add(measurableParam);
		
		measurableParam= new MeasurableParam();
		measurableParam.setId(2L);
		measurableParam.setParameterTitle("HomeWork");
		measurableParam.setParameterDesc("HomeWork");
		measurableParams.add(measurableParam);
		
		measurableParam= new MeasurableParam();
		measurableParam.setId(3L);
		measurableParam.setParameterTitle("Discipline");
		measurableParam.setParameterDesc("Discipline");
		measurableParams.add(measurableParam);
		return Optional.of(measurableParams);
	}
	
	private List<MeasurableParamData> listOfMeasurableParamDataByStudentForAttendance() throws ParseException {
		List<MeasurableParamData> measurableParamDatas= new ArrayList<>();
		MeasurableParamData measurableParamData = new MeasurableParamData();

		measurableParamData.setMeasurableDate(DateUtil.getParseDateObject("01-OCT-2019"));
		measurableParamData.setMeasurableParamValue(1);
		measurableParamDatas.add(measurableParamData);
		
		measurableParamData = new MeasurableParamData();
		measurableParamData.setMeasurableDate(DateUtil.getParseDateObject("03-OCT-2019"));
		measurableParamData.setMeasurableParamValue(0);
		measurableParamDatas.add(measurableParamData);
		
		return measurableParamDatas;
	}
	
	
	private List<MeasurableParamData> listOfMeasurableParamDataByStudentForHW() throws ParseException {
		List<MeasurableParamData> measurableParamDatas= new ArrayList<>();
		MeasurableParamData measurableParamData = new MeasurableParamData();

		measurableParamData.setMeasurableDate(DateUtil.getParseDateObject("01-OCT-2019"));
		measurableParamData.setMeasurableParamValue(0);
		measurableParamDatas.add(measurableParamData);
		
		measurableParamData = new MeasurableParamData();
		measurableParamData.setMeasurableDate(DateUtil.getParseDateObject("03-OCT-2019"));
		measurableParamData.setMeasurableParamValue(1);
		measurableParamDatas.add(measurableParamData);
		
		return measurableParamDatas;
	}
	
	private List<MeasurableParamData> listOfMeasurableParamDataByStudentForDiscipline() throws ParseException {
		List<MeasurableParamData> measurableParamDatas= new ArrayList<>();
		MeasurableParamData measurableParamData = new MeasurableParamData();

		measurableParamData.setMeasurableDate(DateUtil.getParseDateObject("01-OCT-2019"));
		measurableParamData.setMeasurableParamValue(0);
		measurableParamDatas.add(measurableParamData);
		
		measurableParamData = new MeasurableParamData();
		measurableParamData.setMeasurableDate(DateUtil.getParseDateObject("03-OCT-2019"));
		measurableParamData.setMeasurableParamValue(0);
		measurableParamDatas.add(measurableParamData);
		
		return measurableParamDatas;
	}
	
	
	private List<Object[]> listOfMeasurableParamDataForAttendance() {
		List<Object[]> dbValues = new ArrayList<>();
		Object[] value0 = { 100L, 1 };
		dbValues.add(value0);
		Object[] value1={75L,3};
		dbValues.add(value1);
		Object[] value3={55L,7};
		dbValues.add(value3);
		return dbValues;
	}
	
	private List<Object[]> listOfMeasurableParamDataForHomework() {
		List<Object[]> dbValues = new ArrayList<>();
		Object[] value0 = { 100L, 1 };
		dbValues.add(value0);
		Object[] value1={78L,3};
		dbValues.add(value1);
		Object[] value3={55L,7};
		dbValues.add(value3);
		return dbValues;
	}
	
	private List<Object[]> listOfMeasurableParamDataForDiscipline() {
		List<Object[]> dbValues = new ArrayList<>();
		Object[] value0 = { 100L, 1 };
		dbValues.add(value0);
		Object[] value1={75L,3};
		dbValues.add(value1);
		Object[] value3={55L,7};
		dbValues.add(value3);
		return dbValues;
	}
	
	private List<Integer> listHolidays(){
		List<Integer> holidays = new ArrayList<>();
		holidays.add(2);
		holidays.add(4);
		return holidays;
	}
	
	private List<Integer> workingDays(){
		List<Integer> workingDays = new ArrayList<>();
		workingDays.add(5);
		return workingDays;
	}
}
