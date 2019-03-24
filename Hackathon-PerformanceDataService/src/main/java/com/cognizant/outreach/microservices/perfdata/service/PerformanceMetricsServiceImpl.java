/**
 * 
 */
package com.cognizant.outreach.microservices.perfdata.service;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.microservices.perfdata.repository.PerformanceDataRepository;
import com.cognizant.outreach.microservices.perfdata.repository.SchoolRepository;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.AverageRowVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.ClassTeamwiseSectionData;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.ClasswiseMetricsVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.DashboardSingleVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.EncouragingMetricsVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.SearchPerformanceMetrics;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.SectionDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.TeamwiseMetricsVO;

@Service
public class PerformanceMetricsServiceImpl implements PerformanceMetricsService {

	protected Logger logger = LoggerFactory.getLogger(PerformanceMetricsServiceImpl.class);

	@Autowired
	PerformanceDataRepository performanceDataRepository;

	@Autowired
	SchoolRepository schoolRepository;

	@Override
	public List<EncouragingMetricsVO> getEncouragingPerformanceMetrics(
			SearchPerformanceMetrics searchPerformanceMetrics) {
		List<EncouragingMetricsVO> encouragingMetricsVOs = new ArrayList<EncouragingMetricsVO>();
		EncouragingMetricsVO encouragingMetricsVO = null;
		SectionDataVO sectionData = null;
		List<SectionDataVO> sectionDatas = null;
		Map<String, SectionDataVO> sectionwiseMap = null;
		sectionwiseMap = new HashMap<String, SectionDataVO>();
		AverageRowVO averageRowVO = new AverageRowVO();
		List<MeasurableParam> measurableParam = performanceDataRepository
				.listOfMeasurableParamBySchoolId(searchPerformanceMetrics.getSchoolId());
		for (MeasurableParam param : measurableParam) {
			encouragingMetricsVO = new EncouragingMetricsVO();
			sectionDatas = new ArrayList<SectionDataVO>();
			averageRowVO = new AverageRowVO();
			generateParamDataEncouragingMap(
					performanceDataRepository.listOfMeasurableParamDataByMonth(searchPerformanceMetrics.getSchoolId(),
							searchPerformanceMetrics.getClassName(), param.getId(),
							searchPerformanceMetrics.getMonth1()),
					param, sectionData, sectionwiseMap, "month1", encouragingMetricsVO,
					searchPerformanceMetrics.getMonth1());
			generateParamDataEncouragingMap(
					performanceDataRepository.listOfMeasurableParamDataByMonth(searchPerformanceMetrics.getSchoolId(),
							searchPerformanceMetrics.getClassName(), param.getId(),
							searchPerformanceMetrics.getMonth2()),
					param, sectionData, sectionwiseMap, "month2", encouragingMetricsVO,
					searchPerformanceMetrics.getMonth2());
			for (Entry<String, SectionDataVO> classwiseSectionData : sectionwiseMap.entrySet()) {
				sectionDatas.add(classwiseSectionData.getValue());
				averageRowVO.setMonth1Average(
						averageRowVO.getMonth1Average() + classwiseSectionData.getValue().getMonth1percentage());
				averageRowVO.setMonth2Average(
						averageRowVO.getMonth2Average() + classwiseSectionData.getValue().getMonth2percentage());
			}
			averageRowVO.setMonth1Average(averageRowVO.getMonth1Average() / sectionwiseMap.size());
			averageRowVO.setMonth2Average(averageRowVO.getMonth2Average() / sectionwiseMap.size());
			averageRowVO.setChangeinAverage(averageRowVO.getMonth1Average() - averageRowVO.getMonth2Average());
			encouragingMetricsVO.setAverageRow(averageRowVO);
			encouragingMetricsVO.setClassName(searchPerformanceMetrics.getClassName());
			encouragingMetricsVO.setSectionData(sectionDatas);
			encouragingMetricsVOs.add(encouragingMetricsVO);
		}
		return encouragingMetricsVOs;
	}

	private void generateParamDataEncouragingMap(List<Object[]> classwiseMetrics, MeasurableParam param,
			SectionDataVO sectionData, Map<String, SectionDataVO> sectionwiseMap, String month,
			EncouragingMetricsVO encouragingMetricsVO, Integer monthId) {
		for (Object[] sectionRow : classwiseMetrics) {
			if (sectionwiseMap.get((String) sectionRow[2]) != null) {
				sectionData = sectionwiseMap.get((String) sectionRow[2]);
			} else {
				sectionData = new SectionDataVO();
			}
			switch (month) {
			case "month1":
				sectionData.setMonth1percentage((Long) sectionRow[0]);
				encouragingMetricsVO.setMonth1(monthId);
				break;
			case "month2":
				sectionData.setMonth2percentage((Long) sectionRow[0]);
				encouragingMetricsVO.setMonth2(monthId);
				sectionData
						.setIncreasePercentage(sectionData.getMonth1percentage() - sectionData.getMonth2percentage());
				break;

			default:
				break;
			}
			sectionData.setSection((String) sectionRow[2]);
			encouragingMetricsVO.setClassName((String) sectionRow[1]);
			encouragingMetricsVO.setMetricsType(param.getParameterTitle());
			sectionwiseMap.put(sectionData.getSection(), sectionData);
		}

	}

	@Override
	public ClasswiseMetricsVO getClasswisePerformanceMetrics(SearchPerformanceMetrics searchPerformanceMetrics) {
		ClasswiseMetricsVO classwiseMetricsVO = new ClasswiseMetricsVO();
		ClassTeamwiseSectionData sectionData = null;
		List<ClassTeamwiseSectionData> classwiseSectionDatas = new ArrayList<ClassTeamwiseSectionData>();
		Map<String, ClassTeamwiseSectionData> classwiseMap = null;
		int i = 0;
		classwiseMap = new HashMap<String, ClassTeamwiseSectionData>();
		List<MeasurableParam> measurableParam = performanceDataRepository
				.listOfMeasurableParamBySchoolId(searchPerformanceMetrics.getSchoolId());
		for (MeasurableParam param : measurableParam) {
			generateParamDataClassMap(
					performanceDataRepository.listOfMeasurableParamDataBySection(searchPerformanceMetrics.getSchoolId(),
							searchPerformanceMetrics.getClassName(), param.getId()),
					param, sectionData, classwiseMap, ++i, classwiseMetricsVO);
		}
		classwiseMetricsVO.setClassName(searchPerformanceMetrics.getClassName());
		for (Entry<String, ClassTeamwiseSectionData> classwiseSectionData : classwiseMap.entrySet()) {
			classwiseSectionData.getValue()
					.setTotal(classwiseSectionData.getValue().getParam1Total()
							+ classwiseSectionData.getValue().getParam3Total()
							+ classwiseSectionData.getValue().getParam3Total());
			classwiseSectionDatas.add(classwiseSectionData.getValue());
		}
		classwiseMetricsVO.setSectionData(classwiseSectionDatas);
		return classwiseMetricsVO;
	}

	@Override
	public TeamwiseMetricsVO getTeamwisePerformanceMetrics(SearchPerformanceMetrics searchPerformanceMetrics) {

		TeamwiseMetricsVO teamwiseMetricsVO = new TeamwiseMetricsVO();
		ClassTeamwiseSectionData sectionData = null;
		List<ClassTeamwiseSectionData> classwiseSectionDatas = new ArrayList<ClassTeamwiseSectionData>();
		Map<String, ClassTeamwiseSectionData> classwiseMap = null;
		int i = 0;
		classwiseMap = new HashMap<String, ClassTeamwiseSectionData>();
		List<MeasurableParam> measurableParam = performanceDataRepository
				.listOfMeasurableParamBySchoolId(searchPerformanceMetrics.getSchoolId());
		for (MeasurableParam param : measurableParam) {
			generateParamDataTeamMap(
					performanceDataRepository.listOfMeasurableParamDataByTeam(searchPerformanceMetrics.getSchoolId(),
							searchPerformanceMetrics.getClassName(), param.getId()),
					param, sectionData, classwiseMap, ++i, teamwiseMetricsVO);
		}
		teamwiseMetricsVO.setClassName(searchPerformanceMetrics.getClassName());
		for (Entry<String, ClassTeamwiseSectionData> classwiseSectionData : classwiseMap.entrySet()) {
			classwiseSectionData.getValue()
					.setTotal(classwiseSectionData.getValue().getParam1Total()
							+ classwiseSectionData.getValue().getParam3Total()
							+ classwiseSectionData.getValue().getParam3Total());
			classwiseSectionDatas.add(classwiseSectionData.getValue());
		}
		teamwiseMetricsVO.setSectionData(classwiseSectionDatas);
		return teamwiseMetricsVO;

	}

	/**
	 * Method to convert the param data to key value map
	 * 
	 * @param sectionwiseMap
	 * @param classwiseMetrics
	 * @param classwiseSectionDatas
	 * @param sectionData
	 * @param classwiseMap
	 * @param paramTitle
	 * 
	 * @return Map<Integer, Long> - Key is the day of the month and value is the
	 *         param value for the day
	 */
	private void generateParamDataClassMap(List<Object[]> classwiseMetrics, MeasurableParam param,
			ClassTeamwiseSectionData sectionData, Map<String, ClassTeamwiseSectionData> classwiseMap, int key,
			ClasswiseMetricsVO metricsVO) {
		for (Object[] sectionRow : classwiseMetrics) {
			if (classwiseMap.get((String) sectionRow[1] + "-" + (String) sectionRow[2]) != null) {
				sectionData = classwiseMap.get((String) sectionRow[1] + "-" + (String) sectionRow[2]);
			} else {
				sectionData = new ClassTeamwiseSectionData();
			}
			switch (key) {
			case 1:
				sectionData.setParam1Title(param.getParameterTitle());
				metricsVO.setParamName1(param.getParameterTitle());
				sectionData.setParam1Total((Long) sectionRow[0]);
				break;
			case 2:
				metricsVO.setParamName2(param.getParameterTitle());
				sectionData.setParam2Title(param.getParameterTitle());
				sectionData.setParam2Total((Long) sectionRow[0]);
				break;
			case 3:
				metricsVO.setParamName3(param.getParameterTitle());
				sectionData.setParam3Title(param.getParameterTitle());
				sectionData.setParam3Total((Long) sectionRow[0]);
				break;

			default:
				break;
			}
			metricsVO.setTotalTitle("Total");
			if (sectionData.getTotal() == null) {
				sectionData.setTotal(0L);
			}
			sectionData.setTotal(sectionData.getTotal() + (Long) sectionRow[0]);
			sectionData.setTeamName((String) sectionRow[1]);
			sectionData.setSection((String) sectionRow[1] + "-" + (String) sectionRow[2]);
			classwiseMap.put(sectionData.getSection(), sectionData);
		}

	}

	/**
	 * Method to convert the param data to key value map
	 * 
	 * @param sectionwiseMap
	 * @param classwiseMetrics
	 * @param sectionData
	 * @param classwiseMap
	 * @param paramTitle
	 * 
	 * @return Map<Integer, Long> - Key is the day of the month and value is the
	 *         param value for the day
	 */
	private void generateParamDataTeamMap(List<Object[]> classwiseMetrics, MeasurableParam param,
			ClassTeamwiseSectionData sectionData, Map<String, ClassTeamwiseSectionData> classwiseMap, int key,
			TeamwiseMetricsVO metricsVO) {
		for (Object[] sectionRow : classwiseMetrics) {
			if (classwiseMap.get((String) sectionRow[1]) != null) {
				sectionData = classwiseMap.get((String) sectionRow[1]);
			} else {
				sectionData = new ClassTeamwiseSectionData();
			}
			switch (key) {
			case 1:
				sectionData.setParam1Title(param.getParameterTitle());
				metricsVO.setParamName1(param.getParameterTitle());
				sectionData.setParam1Total((Long) sectionRow[0]);
				break;
			case 2:
				metricsVO.setParamName2(param.getParameterTitle());
				sectionData.setParam2Title(param.getParameterTitle());
				sectionData.setParam2Total((Long) sectionRow[0]);
				break;
			case 3:
				metricsVO.setParamName3(param.getParameterTitle());
				sectionData.setParam3Title(param.getParameterTitle());
				sectionData.setParam3Total((Long) sectionRow[0]);
				break;

			default:
				break;
			}
			metricsVO.setTotalTitle("Total");
			if (sectionData.getTotal() == null) {
				sectionData.setTotal(0L);
			}
			metricsVO.setTeamName((String) sectionRow[1]);
			sectionData.setTotal(sectionData.getTotal() + (Long) sectionRow[0]);
			sectionData.setTeamName((String) sectionRow[1]);
			sectionData.setSection((String) sectionRow[1]);
			classwiseMap.put(sectionData.getSection(), sectionData);
		}

	}

	@Override
	public List<DashboardSingleVO> getSchoolByMonthMetrics() {
		List<Object[]> schoolByMonthDashboard = null;
		DashboardSingleVO dashboardSingleVO;
		List<DashboardSingleVO> dashboardSingleVOs = new ArrayList<DashboardSingleVO>();
		schoolByMonthDashboard = performanceDataRepository.listOfMeasurableParamDataNumSchoolsByMonth();
		for (Object[] schoolByMonth : schoolByMonthDashboard) {
			dashboardSingleVO = new DashboardSingleVO();
			dashboardSingleVO.setName(Month.of(Integer.valueOf(String.valueOf(schoolByMonth[1])))
					.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
			dashboardSingleVO.setValue(Integer.valueOf(String.valueOf(schoolByMonth[0])));
			dashboardSingleVOs.add(dashboardSingleVO);
		}
		return dashboardSingleVOs;
	}

	@Override
	public Long getTotalNoOfSchools() {
		Long totalNoOfSchools = schoolRepository.count();
		return totalNoOfSchools;
	}

	@Override
	public List<DashboardSingleVO> getTopPerformingSchools() {
		List<Object[]> topPerformingSchools = null;
		List<DashboardSingleVO> dashboardTopSchoolsVOs = new ArrayList<DashboardSingleVO>();
		DashboardSingleVO dashboardSingleVO = null;
		topPerformingSchools = performanceDataRepository.listOfMeasurableParamDataOfTopSchool();
		for (Object[] topSchoolsByMonth : topPerformingSchools) {
			dashboardSingleVO = new DashboardSingleVO();
			dashboardSingleVO.setName(String.valueOf(topSchoolsByMonth[1]));
			dashboardSingleVO.setValue(Integer.valueOf(String.valueOf(topSchoolsByMonth[0])));
			dashboardTopSchoolsVOs.add(dashboardSingleVO);
		}
		return dashboardTopSchoolsVOs;
	}

	@Override
	public List<DashboardSingleVO> getTopPerformingVolunteers() {
		List<Object[]> topPerformingVolunteers = null;
		List<DashboardSingleVO> dashboardTopVolunteersVOs = new ArrayList<DashboardSingleVO>();
		DashboardSingleVO dashboardSingleVO = null;
		topPerformingVolunteers = performanceDataRepository.listOfMeasurableParamDataOfTopPerformer();
		for (Object[] topVolunteers : topPerformingVolunteers) {
			dashboardSingleVO = new DashboardSingleVO();
			dashboardSingleVO.setName(String.valueOf(topVolunteers[1]));
			dashboardSingleVO.setValue(Integer.valueOf(String.valueOf(topVolunteers[0])));
			dashboardTopVolunteersVOs.add(dashboardSingleVO);
		}
		return dashboardTopVolunteersVOs;
	}
}
