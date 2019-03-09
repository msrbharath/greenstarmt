/**
 * 
 */
package com.cognizant.outreach.microservices.perfdata.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.outreach.microservices.perfdata.repository.PerformanceDataRepository;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.ClasswiseMetricsVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.ClassTeamwiseSectionData;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.EncouragingMetricsVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.SearchPerformanceMetrics;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.TeamwiseMetricsVO;

@Service
public class PerformanceMetricsServiceImpl implements PerformanceMetricsService {

	protected Logger logger = LoggerFactory.getLogger(PerformanceMetricsServiceImpl.class);

	@Autowired
	PerformanceDataRepository performanceDataRepository;

	@Override
	public List<EncouragingMetricsVO> getEncouragingPerformanceMetrics(
			SearchPerformanceMetrics searchPerformanceMetrics) {
		List<Object[]> measurableParamDatasHomework = null;
		List<Object[]> measurableParamDatasAttendance = null;
		List<Object[]> measurableParamDatasDiscipline = null;
		measurableParamDatasHomework = performanceDataRepository.listOfMeasurableParamDataByClass(
				searchPerformanceMetrics.getClassId(), 1, searchPerformanceMetrics.getMonth());
		measurableParamDatasAttendance = performanceDataRepository.listOfMeasurableParamDataByClass(
				searchPerformanceMetrics.getClassId(), 2, searchPerformanceMetrics.getMonth());
		measurableParamDatasDiscipline = performanceDataRepository.listOfMeasurableParamDataByClass(
				searchPerformanceMetrics.getClassId(), 3, searchPerformanceMetrics.getMonth());
		// return measurableParamDatas;
		return null;
	}

	@Override
	public ClasswiseMetricsVO getClasswisePerformanceMetrics(SearchPerformanceMetrics searchPerformanceMetrics) {
		ClasswiseMetricsVO classwiseMetricsVO = new ClasswiseMetricsVO();
		ClassTeamwiseSectionData sectionData = new ClassTeamwiseSectionData();
		List<ClassTeamwiseSectionData> classwiseSectionDatas = new ArrayList<ClassTeamwiseSectionData>();
		Map<String, Map<String, Long>> sectionwiseMap = new HashMap<String,Map<String,Long>>();
		generateHomeworkMap(performanceDataRepository.listOfMeasurableParamDataBySection(searchPerformanceMetrics.getSchoolId(), searchPerformanceMetrics.getClassName(),1), sectionwiseMap);
		generateAttendanceMap(performanceDataRepository.listOfMeasurableParamDataBySection(searchPerformanceMetrics.getSchoolId(), searchPerformanceMetrics.getClassName(),2), sectionwiseMap);
		generateDisciplineMap(performanceDataRepository.listOfMeasurableParamDataBySection(searchPerformanceMetrics.getSchoolId(), searchPerformanceMetrics.getClassName(),3), sectionwiseMap);
		classwiseMetricsVO.setClassName(searchPerformanceMetrics.getClassName());
		for (Entry<String, Map<String, Long>> sectionEntries : sectionwiseMap.entrySet()) {
			sectionData.setSection(sectionEntries.getKey());
			sectionData.setHomeworkTotal(sectionEntries.getValue().get("homeworkTotal"));
			sectionData.setAttendanceTotal(sectionEntries.getValue().get("attendanceTotal"));
			sectionData.setDisciplineTotal(sectionEntries.getValue().get("disciplineTotal"));
			classwiseSectionDatas.add(sectionData);
		}
		classwiseMetricsVO.setSectionData(classwiseSectionDatas);
		return classwiseMetricsVO;
	}

	@Override
	public TeamwiseMetricsVO getTeamwisePerformanceMetrics(SearchPerformanceMetrics searchPerformanceMetrics) {
		TeamwiseMetricsVO teamwiseMetricsVO = new TeamwiseMetricsVO();
		ClassTeamwiseSectionData sectionData = new ClassTeamwiseSectionData();
		List<ClassTeamwiseSectionData> classwiseSectionDatas = new ArrayList<ClassTeamwiseSectionData>();
		Map<String, Map<String, Long>> sectionwiseMap = new HashMap<String,Map<String,Long>>();
		generateHomeworkMap(performanceDataRepository.listOfMeasurableParamDataBySection(searchPerformanceMetrics.getSchoolId(), searchPerformanceMetrics.getClassName(),1), sectionwiseMap);
		generateAttendanceMap(performanceDataRepository.listOfMeasurableParamDataBySection(searchPerformanceMetrics.getSchoolId(), searchPerformanceMetrics.getClassName(),2), sectionwiseMap);
		generateDisciplineMap(performanceDataRepository.listOfMeasurableParamDataBySection(searchPerformanceMetrics.getSchoolId(), searchPerformanceMetrics.getClassName(),3), sectionwiseMap);
		for (Entry<String, Map<String, Long>> sectionEntries : sectionwiseMap.entrySet()) {
			sectionData.setSection(sectionEntries.getKey());
			sectionData.setHomeworkTotal(sectionEntries.getValue().get("homeworkTotal"));
			sectionData.setAttendanceTotal(sectionEntries.getValue().get("attendanceTotal"));
			sectionData.setDisciplineTotal(sectionEntries.getValue().get("disciplineTotal"));
			classwiseSectionDatas.add(sectionData);
		}
		teamwiseMetricsVO.setSectionData(classwiseSectionDatas);
		return teamwiseMetricsVO;
	}
	
	/**
	 * Method to convert the param data to key value map
	 * @param sectionwiseMap 
	 * @param classwiseMetrics 
	 * 
	 * @return Map<Integer, Long> - Key is the day of the month and value is the
	 *         param value for the day
	 */
	private void generateHomeworkMap(List<Object[]> classwiseMetrics, Map<String, Map<String, Long>> sectionwiseMap) {
		Map <String, Long> metricsTotalMap = new HashMap<String, Long>(); 
		for (Object[] sectionRow : classwiseMetrics) {
			metricsTotalMap.put("homeworkTotal",(Long) sectionRow[0]);
			sectionwiseMap.put((String) sectionRow[2], metricsTotalMap);
		}
	}
	
	private void generateAttendanceMap(List<Object[]> classwiseMetrics, Map<String, Map<String, Long>> sectionwiseMap) {
		Map <String, Long> metricsTotalMap = new HashMap<String, Long>(); 
		for (Object[] sectionRow : classwiseMetrics) {
			metricsTotalMap.put("attendanceTotal",(Long) sectionRow[0]);
			sectionwiseMap.put((String) sectionRow[2], metricsTotalMap);
		}
	}
	
	private void generateDisciplineMap(List<Object[]> classwiseMetrics, Map<String, Map<String, Long>> sectionwiseMap) {
		Map <String, Long> metricsTotalMap = new HashMap<String, Long>(); 
		for (Object[] sectionRow : classwiseMetrics) {
			metricsTotalMap.put("disciplineTotal",(Long) sectionRow[0]);
			sectionwiseMap.put((String) sectionRow[2], metricsTotalMap);
		}
	}
}
