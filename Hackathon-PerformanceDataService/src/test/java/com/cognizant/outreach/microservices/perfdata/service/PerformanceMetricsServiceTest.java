package com.cognizant.outreach.microservices.perfdata.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.microservices.perfdata.dao.PerformanceDataDAOImpl;
import com.cognizant.outreach.microservices.perfdata.repository.PerformanceDataRepository;
import com.cognizant.outreach.microservices.perfdata.repository.SchoolRepository;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.ClasswiseMetricsVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.DashboardSingleVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.EncouragingMetricsVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.SearchPerformanceMetrics;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.TeamwiseMetricsVO;

public class PerformanceMetricsServiceTest {

	@InjectMocks
	PerformanceMetricsService performanceMetricsService = new PerformanceMetricsServiceImpl();

	@Mock
	PerformanceDataRepository performanceDataRepository;

	@Mock
	SchoolRepository schoolRepository;

	@Mock
	private PerformanceDataDAOImpl performanceDataDAO;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetTotalNoOfSchool() {
		when(schoolRepository.count()).thenReturn(10L);
		assertEquals(performanceMetricsService.getTotalNoOfSchools(), Long.valueOf(10));
	}

	/**
	 * Method to test the encouraging performance metric data
	 */
	@Test
	public void TestPerformanceMetrics_GetEncouraging() {

		SearchPerformanceMetrics searchPerformanceMetrics = new SearchPerformanceMetrics();
		searchPerformanceMetrics.setSchoolId(1l);
		searchPerformanceMetrics.setClassName("I");
		searchPerformanceMetrics.setMonth1(1);
		searchPerformanceMetrics.setMonth2(2);

		when(performanceDataRepository.listOfMeasurableParamBySchoolId(Mockito.any(Long.class))).thenReturn(getMeasurableParams());
		when(performanceDataRepository.listOfMeasurableParamDataByMonth(Mockito.any(Long.class),Mockito.any(String.class), Mockito.any(Long.class), Mockito.any(Integer.class))).thenReturn(getMeasurableParamDataByMonth());
		when(performanceDataRepository.listOfMeasurableParamDataByMonth(Mockito.any(Long.class),Mockito.any(String.class), Mockito.any(Long.class), Mockito.any(Integer.class))).thenReturn(getMeasurableParamDataByMonth());

		List<EncouragingMetricsVO> encouragingMetricsVOs = performanceMetricsService.getEncouragingPerformanceMetrics(searchPerformanceMetrics);

		assertTrue("Available Encourageing Metric Data", encouragingMetricsVOs.size() > 0);
	}

	/**
	 * Method to test the class wise performance metric data
	 */
	@Test
	public void TestPerformanceMetrics_GetClasswise() {

		SearchPerformanceMetrics searchPerformanceMetrics = new SearchPerformanceMetrics();
		searchPerformanceMetrics.setSchoolId(1l);
		searchPerformanceMetrics.setClassName("I");

		when(performanceDataRepository.listOfMeasurableParamBySchoolId(Mockito.any(Long.class))).thenReturn(getMeasurableParams());
		when(performanceDataRepository.listOfMeasurableParamDataBySection(Mockito.any(Long.class),Mockito.any(String.class), Mockito.any(Long.class))).thenReturn(getMeasurableParamDataBySection());

		ClasswiseMetricsVO classwiseMetricsVO = performanceMetricsService.getClasswisePerformanceMetrics(searchPerformanceMetrics);

		assertNotNull(classwiseMetricsVO);
	}

	/**
	 * Method to test the team wise performance metric data
	 */
	@Test
	public void TestPerformanceMetrics_GetTeamwise() {

		SearchPerformanceMetrics searchPerformanceMetrics = new SearchPerformanceMetrics();
		searchPerformanceMetrics.setSchoolId(1l);
		searchPerformanceMetrics.setClassId(1L);
		searchPerformanceMetrics.setClassName("I");

		when(performanceDataRepository.listOfMeasurableParamBySchoolId(Mockito.any(Long.class))).thenReturn(getMeasurableParams());
		when(performanceDataRepository.listOfMeasurableParamDataByTeam(Mockito.any(Long.class),Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(getMeasurableParamDataByTeam());

		TeamwiseMetricsVO teamwiseMetricsVO = performanceMetricsService.getTeamwisePerformanceMetrics(searchPerformanceMetrics);

		assertNotNull(teamwiseMetricsVO);
	}

	/**
	 * Test method for
	 * {@link com.cognizant.outreach.microservices.perfdata.service.PerformanceMetricsService#getSchoolByMonthMetrics()}.
	 */
	@Test
	public void testGetSchoolByMonthMetrics() {
		when(performanceDataRepository.listOfMeasurableParamDataNumSchoolsByMonth()).thenReturn(getSchoolsByMonth());
		List<DashboardSingleVO> schoolsByMonth = performanceMetricsService.getSchoolByMonthMetrics();
		assertEquals(schoolsByMonth.get(0).getName(), "January");
		assertEquals(schoolsByMonth.get(0).getValue(), Integer.valueOf(38));
		assertEquals(schoolsByMonth.get(1).getName(), "February");
		assertEquals(schoolsByMonth.get(1).getValue(), Integer.valueOf(42));
		assertEquals(schoolsByMonth.get(2).getName(), "March");
		assertEquals(schoolsByMonth.get(2).getValue(), Integer.valueOf(56));
		assertEquals(schoolsByMonth.get(3).getName(), "April");
		assertEquals(schoolsByMonth.get(3).getValue(), Integer.valueOf(40));
		assertEquals(schoolsByMonth.get(4).getName(), "May");
		assertEquals(schoolsByMonth.get(4).getValue(), Integer.valueOf(60));
		assertEquals(schoolsByMonth.get(5).getName(), "June");
		assertEquals(schoolsByMonth.get(5).getValue(), Integer.valueOf(70));
		assertEquals(schoolsByMonth.get(6).getName(), "July");
		assertEquals(schoolsByMonth.get(6).getValue(), Integer.valueOf(30));
		assertEquals(schoolsByMonth.get(7).getName(), "August");
		assertEquals(schoolsByMonth.get(7).getValue(), Integer.valueOf(72));
		assertEquals(schoolsByMonth.get(8).getName(), "September");
		assertEquals(schoolsByMonth.get(8).getValue(), Integer.valueOf(69));
		assertEquals(schoolsByMonth.get(9).getName(), "October");
		assertEquals(schoolsByMonth.get(9).getValue(), Integer.valueOf(77));
		assertEquals(schoolsByMonth.get(10).getName(), "November");
		assertEquals(schoolsByMonth.get(10).getValue(), Integer.valueOf(78));
		assertEquals(schoolsByMonth.get(11).getName(), "December");
		assertEquals(schoolsByMonth.get(11).getValue(), Integer.valueOf(79));
	}

	/**
	 * Test method for
	 * {@link com.cognizant.outreach.microservices.perfdata.service.PerformanceMetricsService#getTotalNoOfSchools()}.
	 */
	@Test
	public void testGetTotalNoOfSchools() {
		when(schoolRepository.count()).thenReturn(10L);
		assertEquals(performanceMetricsService.getTotalNoOfSchools(), Long.valueOf(10));
	}

	/**
	 * Test method for
	 * {@link com.cognizant.outreach.microservices.perfdata.service.PerformanceMetricsService#getTopPerformingSchools()}.
	 */
	@Test
	public void testGetTopPerformingSchools() {
		when(performanceDataRepository.listOfMeasurableParamDataOfTopSchool()).thenReturn(topPerformingSchools());
		List<DashboardSingleVO> dashboardTopSchoolVOs = performanceMetricsService.getTopPerformingSchools();
		assertEquals(dashboardTopSchoolVOs.get(0).getName(), "KVM School");
		assertEquals(dashboardTopSchoolVOs.get(0).getValue(), Integer.valueOf(12300));
		assertEquals(dashboardTopSchoolVOs.get(1).getName(), "TIPS School");
		assertEquals(dashboardTopSchoolVOs.get(1).getValue(), Integer.valueOf(11200));
		assertEquals(dashboardTopSchoolVOs.get(2).getName(), "AMALA School");
		assertEquals(dashboardTopSchoolVOs.get(2).getValue(), Integer.valueOf(10100));
		assertEquals(dashboardTopSchoolVOs.get(3).getName(), "DJHSS School");
		assertEquals(dashboardTopSchoolVOs.get(3).getValue(), Integer.valueOf(8300));
		assertEquals(dashboardTopSchoolVOs.get(4).getName(), "SVMHSS School");
		assertEquals(dashboardTopSchoolVOs.get(4).getValue(), Integer.valueOf(4000));
	}

	/**
	 * Test method for
	 * {@link com.cognizant.outreach.microservices.perfdata.service.PerformanceMetricsService#getTopPerformingVolunteers()}.
	 */
	@Test
	public void testGetTopPerformingVolunteers() {
		when(performanceDataRepository.listOfMeasurableParamDataOfTopPerformer()).thenReturn(topPerformingVolunteers());
		List<DashboardSingleVO> dashboardTopVolunteersVOs = performanceMetricsService.getTopPerformingVolunteers();
		assertEquals(dashboardTopVolunteersVOs.get(0).getName(), "Bharath");
		assertEquals(dashboardTopVolunteersVOs.get(0).getValue(), Integer.valueOf(100));
		assertEquals(dashboardTopVolunteersVOs.get(1).getName(), "Magesh");
		assertEquals(dashboardTopVolunteersVOs.get(1).getValue(), Integer.valueOf(78));
		assertEquals(dashboardTopVolunteersVOs.get(2).getName(), "Panneer");
		assertEquals(dashboardTopVolunteersVOs.get(2).getValue(), Integer.valueOf(55));
	}
	
	private List<Object[]> getSchoolsByMonth() {
		List<Object[]> dbValues = new ArrayList<>();
		Object[] value0 = { 38, "1" };
		dbValues.add(value0);
		Object[] value1 = { 42, "2" };
		dbValues.add(value1);
		Object[] value2 = { 56, "3" };
		dbValues.add(value2);
		Object[] value3 = { 40, "4" };
		dbValues.add(value3);
		Object[] value4 = { 60, "5" };
		dbValues.add(value4);
		Object[] value5 = { 70, "6" };
		dbValues.add(value5);
		Object[] value6 = { 30, "7" };
		dbValues.add(value6);
		Object[] value7 = { 72, "8" };
		dbValues.add(value7);
		Object[] value8 = { 69, "9" };
		dbValues.add(value8);
		Object[] value9 = { 77, "10" };
		dbValues.add(value9);
		Object[] value10 = { 78, "11" };
		dbValues.add(value10);
		Object[] value11 = { 79, "12" };
		dbValues.add(value11);
		return dbValues;
	}
	
	private List<Object[]> topPerformingVolunteers() {
		List<Object[]> dbValues = new ArrayList<>();
		Object[] value0 = { 100, "Bharath" };
		dbValues.add(value0);
		Object[] value1 = { 78, "Magesh" };
		dbValues.add(value1);
		Object[] value3 = { 55, "Panneer" };
		dbValues.add(value3);
		return dbValues;
	}

	private List<Object[]> topPerformingSchools() {
		List<Object[]> dbValues = new ArrayList<>();
		Object[] value0 = { 12300, "KVM School" };
		dbValues.add(value0);
		Object[] value1 = { 11200, "TIPS School" };
		dbValues.add(value1);
		Object[] value2 = { 10100, "AMALA School" };
		dbValues.add(value2);
		Object[] value3 = { 8300, "DJHSS School" };
		dbValues.add(value3);
		Object[] value4 = { 4000, "SVMHSS School" };
		dbValues.add(value4);
		return dbValues;
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

	private List<Object[]> getMeasurableParamDataBySection() {
		List<Object[]> measurableParamDataList = new ArrayList<>();

		Object[] data1 = new Object[] { 50l, "I", "A" };
		Object[] data2 = new Object[] { 100l, "I", "B" };
		Object[] data3 = new Object[] { 150l, "II", "A" };

		measurableParamDataList.add(data1);
		measurableParamDataList.add(data2);
		measurableParamDataList.add(data3);

		return measurableParamDataList;
	}

	private List<Object[]> getMeasurableParamDataByTeam() {
		List<Object[]> measurableParamDataList = new ArrayList<>();

		Object[] data1 = new Object[] { 50l, "Green" };
		Object[] data2 = new Object[] { 100l, "Red" };
		Object[] data3 = new Object[] { 150l, "Yellow" };

		measurableParamDataList.add(data1);
		measurableParamDataList.add(data2);
		measurableParamDataList.add(data3);

		return measurableParamDataList;
	}

	private List<Object[]> getMeasurableParamDataByMonth() {
		List<Object[]> measurableParamDataList = new ArrayList<>();

		Object[] data1 = new Object[] { 50l, "I", "A" };
		Object[] data2 = new Object[] { 100l, "I", "B" };
		Object[] data3 = new Object[] { 150l, "II", "A" };

		measurableParamDataList.add(data1);
		measurableParamDataList.add(data2);
		measurableParamDataList.add(data3);

		return measurableParamDataList;
	}

}
