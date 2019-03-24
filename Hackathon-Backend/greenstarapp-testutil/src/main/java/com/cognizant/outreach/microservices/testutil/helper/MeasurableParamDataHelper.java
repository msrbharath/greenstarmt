package com.cognizant.outreach.microservices.testutil.helper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.cognizant.outreach.entity.ClassDetail;
import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.MeasurableParamData;
import com.cognizant.outreach.entity.StudentSchoolAssoc;
import com.cognizant.outreach.microservices.testutil.dao.MeasurableParamDataRepository;
import com.cognizant.outreach.util.DateUtil;

@Component
public class MeasurableParamDataHelper {

	protected Logger logger = LoggerFactory.getLogger(MeasurableParamDataHelper.class);
	
	@Autowired
	MeasurableParamDataRepository measurableParamDataRepository;
	
	@Autowired
	ExcelDataLoadHelper dataLoadHelper;

	public List<Long> createParamData(Map<Long, Map<Long, List<StudentSchoolAssoc>>> schoolClassStudentAssocMap,
			Map<Long, List<ClassDetail>> classDetailMap, Map<Long, List<MeasurableParam>> measurableParamMap)
			throws IOException, ParseException {
		// School number is used to create the name of the excel
		int shoolNumber = 1;
		List<Long> allParamDataIds = new ArrayList<>();
		for (Map.Entry<Long, Map<Long, List<StudentSchoolAssoc>>> schoolEntry : schoolClassStudentAssocMap.entrySet()) {
			long schoolId = schoolEntry.getKey();
			logger.debug("School Id: {}",schoolId);
			for (Map.Entry<Long, List<StudentSchoolAssoc>> associationEntry : schoolEntry.getValue().entrySet()) {
				logger.debug("Class Id: {}", associationEntry.getKey());
				long classId = associationEntry.getKey();
				
				ClassDetail classDetail = getClassDetail(classId, schoolId, classDetailMap);
				String sectionName = classDetail.getSection();

				List<StudentSchoolAssoc> studentSchoolAssocs = associationEntry.getValue();

				// Get the month wise data from excel
				String fileName = shoolNumber + "-" + sectionName+"-TestData.xlsx";
				File file = ResourceUtils.getFile("classpath:testdata/"+fileName);
				
				Map<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, Integer>>>> monthWiseValues = dataLoadHelper
						.extractParamValueDataFromExcel(file);

				allParamDataIds.addAll(insertParamData(studentSchoolAssocs, "JAN", monthWiseValues.get("JAN"),
						measurableParamMap.get(schoolId)));
				allParamDataIds.addAll(insertParamData(studentSchoolAssocs, "FEB", monthWiseValues.get("FEB"),
						measurableParamMap.get(schoolId)));
				allParamDataIds.addAll(insertParamData(studentSchoolAssocs, "MAR", monthWiseValues.get("MAR"),
						measurableParamMap.get(schoolId)));
				allParamDataIds.addAll(insertParamData(studentSchoolAssocs, "JUN", monthWiseValues.get("JUN"),
						measurableParamMap.get(schoolId)));
				allParamDataIds.addAll(insertParamData(studentSchoolAssocs, "JUL", monthWiseValues.get("JUL"),
						measurableParamMap.get(schoolId)));
				allParamDataIds.addAll(insertParamData(studentSchoolAssocs, "AUG", monthWiseValues.get("AUG"),
						measurableParamMap.get(schoolId)));
				allParamDataIds.addAll(insertParamData(studentSchoolAssocs, "SEP", monthWiseValues.get("SEP"),
						measurableParamMap.get(schoolId)));
				allParamDataIds.addAll(insertParamData(studentSchoolAssocs, "OCT", monthWiseValues.get("OCT"),
						measurableParamMap.get(schoolId)));
				allParamDataIds.addAll(insertParamData(studentSchoolAssocs, "NOV", monthWiseValues.get("NOV"),
						measurableParamMap.get(schoolId)));
				allParamDataIds.addAll(insertParamData(studentSchoolAssocs, "DEC", monthWiseValues.get("DEC"),
						measurableParamMap.get(schoolId)));
			}
			shoolNumber++;
		}
		return allParamDataIds;
	}

	private ClassDetail getClassDetail(Long classId, Long schoolId, Map<Long, List<ClassDetail>> classDetailMap) {
		for (ClassDetail classDetail : classDetailMap.get(schoolId)) {
			if (classDetail.getId().equals(classId)) {
				return classDetail;
			}
		}
		return null;
	}

	private List<Long> insertParamData(List<StudentSchoolAssoc> associations, String month,
			LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, Integer>>> monthParamData,
			List<MeasurableParam> measurableParams) throws ParseException {
		List<Long> paramDataIds = new ArrayList<>();
		// paramEntry structure <ParamTitle<StudentName,<Day,value>>>
		// Iterate through each param title
		for (Map.Entry<String, LinkedHashMap<String, LinkedHashMap<Integer, Integer>>> paramEntry : monthParamData
				.entrySet()) {
			MeasurableParam measurableParam = getMeasurableParam(measurableParams, paramEntry.getKey().trim());
			// Iterate through each student
			for (Map.Entry<String, LinkedHashMap<Integer, Integer>> studentEntry : paramEntry.getValue().entrySet()) {
				String studentName = studentEntry.getKey();
				StudentSchoolAssoc studentSchoolAssoc = getStudentSchoolAssoc(associations, studentName);
				// Iterate through each day and insert the param into DB
				for (Map.Entry<Integer, Integer> dayEntry : studentEntry.getValue().entrySet()) {
					Long paramDataId = saveMeasurableParam(dayEntry.getKey(), month, dayEntry.getValue(),
							studentSchoolAssoc, measurableParam);
					paramDataIds.add(paramDataId);
				}

			}
		}
		return paramDataIds;
	}

	private MeasurableParam getMeasurableParam(List<MeasurableParam> measurableParams, String paramTitle) {
		for (MeasurableParam measurableParam : measurableParams) {
			if (measurableParam.getParameterTitle().equalsIgnoreCase(paramTitle)) {
				return measurableParam;
			}
		}
		return null;
	}

	private StudentSchoolAssoc getStudentSchoolAssoc(List<StudentSchoolAssoc> associations, String studentName) {
		for (StudentSchoolAssoc studentSchoolAssoc : associations) {
			if (studentSchoolAssoc.getStudent().getStudentName().equalsIgnoreCase(studentName)) {
				return studentSchoolAssoc;
			}
		}
		return null;
	}

	private Long saveMeasurableParam(int day, String month, int paramValue, StudentSchoolAssoc studentSchoolAssoc,
			MeasurableParam measurableParam) throws ParseException {
		String dayStr = (day < 10) ? "0" + day : day + "";
		MeasurableParamData measurableParamData = new MeasurableParamData();
		measurableParamData.setMeasurableDate(DateUtil.getParseDateObject(dayStr + "-" + month + "-2019"));
		measurableParamData.setMeasurableParamValue(paramValue);
		measurableParamData.setStudentSchoolAssoc(studentSchoolAssoc);
		measurableParamData.setMeasurableParam(measurableParam);
		CommonHelper.setAuditTrailInfo(measurableParamData);
		measurableParamDataRepository.save(measurableParamData);
		return measurableParamData.getId();
	}
}
