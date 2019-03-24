/**
 * ${TestUtilService}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  06/Mar/2019            371793        Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.testutil.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.outreach.entity.ClassDetail;
import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.School;
import com.cognizant.outreach.entity.SchoolHoliday;
import com.cognizant.outreach.entity.SchoolWeekendWorkingDay;
import com.cognizant.outreach.entity.Student;
import com.cognizant.outreach.entity.StudentSchoolAssoc;
import com.cognizant.outreach.microservices.testutil.dao.MeasurableParamDataRepository;
import com.cognizant.outreach.microservices.testutil.dao.SchoolRepository;
import com.cognizant.outreach.microservices.testutil.helper.ClassHelper;
import com.cognizant.outreach.microservices.testutil.helper.MeasurableParamDataHelper;
import com.cognizant.outreach.microservices.testutil.helper.MeasurableParamHelper;
import com.cognizant.outreach.microservices.testutil.helper.SchoolHelper;
import com.cognizant.outreach.microservices.testutil.helper.StudentHelper;

/**
 * Service to insert mock test data with below scenarios 1. create 8 schools(4
 * schools for one sate and 4 school for other state. Out of 4 school 2 school
 * on two different district) 2. Each school will have two classes 3. Each class
 * will have 10 students. 5 members on a team 4. All students will have
 * parameter entries for academic month june1- april30 (This will be different
 * to capture major scenario's) 5. Each school will have parameters as
 * Homework,Attendance,Discipline 6. Each school will have holidays configured
 * 7. Each school will have weekend working days configured These mock data can
 * be updated on the test cases based on the unit testing scenario
 * 
 * @author 371793
 */
@Service
public class TestUtilService {

	protected Logger logger = LoggerFactory.getLogger(TestUtilService.class);

	@Autowired
	SchoolHelper schoolHelper;

	@Autowired
	ClassHelper classHelper;

	@Autowired
	StudentHelper studentHelper;

	@Autowired
	MeasurableParamHelper measurableParamHelper;

	@Autowired
	MeasurableParamDataRepository measurableParamDataRepository;

	@Autowired
	MeasurableParamDataHelper measurableParamDataHelper;

	public List<School> schools = null;
	
	@Autowired
	SchoolRepository schoolRepository;

	// Key school Id value list of holidays
	public Map<Long, List<SchoolHoliday>> schoolHolidayMap;

	// Key school Id value list of weekendworkingdays
	public Map<Long, List<SchoolWeekendWorkingDay>> schoolWeekendWorkingDayMap;

	// Key school Id value list of parameters
	public Map<Long, List<MeasurableParam>> measurableParamMap;

	// Key school Id value list of class
	public Map<Long, List<ClassDetail>> classDetailMap;

	// Key school id value list of students
	public Map<Long, List<Student>> schoolStudentMap;

	// Key school Id value (key class id value list of students association)
	public Map<Long, Map<Long, List<StudentSchoolAssoc>>> schoolClassStudentAssocMap;

	// List of paramDataIds
	List<Long> paramDataIds;

	boolean dataAvailable;

	@Transactional
	public void createTestData() throws Exception {
		initMaps();
		
		if (null != schoolRepository.findBySchoolName("Coimbatore Government Hr Sec School")) {
			logger.debug("School already available delete the db and rerun!");
			dataAvailable = true;
			throw new Exception("Data already available delete the db and rerun!");
		}

		logger.debug("Creating school..");
		schools = schoolHelper.createSchools();
		logger.debug("Schools created!");



		logger.debug("Master data loading for all schools...");
		// Create holidays,weekend working days, parameter, classes,students &
		// paramvalue for 10 months for each school
		for (School school : schools) {
			List<SchoolHoliday> schoolHolidays = schoolHelper.createHolidays(school);
			schoolHolidayMap.put(school.getId(), schoolHolidays);

			List<SchoolWeekendWorkingDay> schoolWeekendWorkingDays = schoolHelper
					.createSchoolWeekendWorkingDays(school);
			schoolWeekendWorkingDayMap.put(school.getId(), schoolWeekendWorkingDays);

			List<MeasurableParam> measurableParams = schoolHelper.createMeasurableParams(school);
			measurableParamMap.put(school.getId(), measurableParams);

			List<ClassDetail> classDetails = classHelper.createClasses(school);
			classDetailMap.put(school.getId(), classDetails);

			// Create 20 students
			List<Student> students = studentHelper.createStudents();
			schoolStudentMap.put(school.getId(), students);

			// Iterate through each class to create students class associations
			Map<Long, List<StudentSchoolAssoc>> studentClassAssocMap = new LinkedHashMap<>();

			// Create student class associations for section A
			// consider 1st 10 students for section A
			ClassDetail classDetail = classDetails.get(0);
			List<StudentSchoolAssoc> studentSchoolAssocs = studentHelper.createStudentAssociaton(classDetail,
					students.subList(0, 10));
			studentClassAssocMap.put(classDetail.getId(), studentSchoolAssocs);

			// Create student class associations for section B
			// consider 2nd 10 students for section B
			classDetail = classDetails.get(1);
			studentSchoolAssocs = studentHelper.createStudentAssociaton(classDetail, students.subList(10, 20));
			studentClassAssocMap.put(classDetail.getId(), studentSchoolAssocs);

			schoolClassStudentAssocMap.put(school.getId(), studentClassAssocMap);
		}
		logger.debug("Master data loaded for all the schools!");
	}

	@Transactional
	public void createMeasurableParamData() throws ParseException, IOException {
			logger.debug("MeasurableParamData Loading..");
			paramDataIds = measurableParamDataHelper.createParamData(schoolClassStudentAssocMap, classDetailMap,
					measurableParamMap);
			logger.debug("MeasurableParamData Loaded for all the schools & students for 10 months!");
	}

	private void initMaps() {
		schoolHolidayMap = new LinkedHashMap<Long, List<SchoolHoliday>>();
		schoolWeekendWorkingDayMap = new LinkedHashMap<>();
		measurableParamMap = new LinkedHashMap<>();
		schoolStudentMap = new LinkedHashMap<>();
		classDetailMap = new LinkedHashMap<>();
		schoolClassStudentAssocMap = new LinkedHashMap<>();
	}

	@Transactional
	public void deleteTestData() {
		logger.debug("Deleting all test mock data....");
		// Delete school association
		for (Map.Entry<Long, List<ClassDetail>> entry : classDetailMap.entrySet()) {
			classHelper.deleteClasses(entry.getValue());
			for (ClassDetail classDetail : entry.getValue()) {
				measurableParamDataRepository.deleteByStudentSchoolAssocClazzId(classDetail.getId());
			}
		}

		// Delete school association
		for (Map.Entry<Long, Map<Long, List<StudentSchoolAssoc>>> entry : schoolClassStudentAssocMap.entrySet()) {
			for (Map.Entry<Long, List<StudentSchoolAssoc>> entry1 : entry.getValue().entrySet()) {
				studentHelper.deleteStudentAssociations(entry1.getValue());
			}
		}
		// Delete Students
		for (Map.Entry<Long, List<Student>> entry : schoolStudentMap.entrySet()) {
			studentHelper.deleteStudents(entry.getValue());

		}
		// Delete measurable param
		for (Map.Entry<Long, List<MeasurableParam>> entry : measurableParamMap.entrySet()) {
			schoolHelper.deleteMeasurableParams(entry.getValue());
		}

		// Delete holidays
		for (Map.Entry<Long, List<SchoolHoliday>> entry : schoolHolidayMap.entrySet()) {
			schoolHelper.deleteHolidays(entry.getValue());
		}

		// Delete weekend working days
		for (Map.Entry<Long, List<SchoolWeekendWorkingDay>> entry : schoolWeekendWorkingDayMap.entrySet()) {
			schoolHelper.deleteSchoolWeekendWorkingDays(entry.getValue());
		}

		// Delete classes
		for (Map.Entry<Long, List<ClassDetail>> entry : classDetailMap.entrySet()) {
			classHelper.deleteClasses(entry.getValue());
		}

		// Delete schools
		schoolHelper.dropSchools(schools);
		logger.debug("Deleting all test mock data Completed!");
	}

}
