package com.cognizant.outreach.microservices.testutil.helper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.School;
import com.cognizant.outreach.entity.SchoolHoliday;
import com.cognizant.outreach.entity.SchoolWeekendWorkingDay;
import com.cognizant.outreach.microservices.testutil.dao.MeasurableParamRepository;
import com.cognizant.outreach.microservices.testutil.dao.SchoolHolidayRepository;
import com.cognizant.outreach.microservices.testutil.dao.SchoolRepository;
import com.cognizant.outreach.microservices.testutil.dao.SchoolWeekendWorkingDayRepository;
import com.cognizant.outreach.util.DateUtil;

@Component
public class SchoolHelper {

	@Autowired
	SchoolRepository schoolRepository;

	@Autowired
	SchoolHolidayRepository schoolHolidayRepository;

	@Autowired
	SchoolWeekendWorkingDayRepository weekendWorkingDayRepository;

	@Autowired
	MeasurableParamRepository measurableParamRepository;

	public List<School> createSchools() {
		List<School> schools = new ArrayList<School>();
		School school1 = createSchool("Coimbatore Government Hr Sec School", "TAMIL NADU", "COIMBATORE", "COIMBATORE",
				"COIMBATORE");
		School school2 = createSchool("Annur Government Hr Sec School", "TAMIL NADU", "COIMBATORE", "ANNUR", "ANNUR");
		School school3 = createSchool("Anthiyur Government Hr Sec School", "TAMIL NADU", "ERODE", "ANTHIYUR",
				"ANTHIYUR");
		School school4 = createSchool("Bhavani Government Hr Sec School", "TAMIL NADU", "ERODE", "BHAVANI", "BHAVANI");
		School school5 = createSchool("Alwar Government Senior Sec School", "RAJASTHAN", "ALWAR", "ALWAR", "ALWAR");
		School school6 = createSchool("Bhiwadi Government Hr Sec School", "RAJASTHAN", "ALWAR", "Bhiwadi", "Bhiwadi");
		School school7 = createSchool("Baran Government Senior Secondary School", "RAJASTHAN", "BARAN", "BARAN",
				"BARAN");
		School school8 = createSchool("Samraniya Government Hr Sec School", "RAJASTHAN", "BARAN", "Samraniya",
				"Samraniya");
		schools.add(school1);
		schools.add(school2);
		schools.add(school3);
		schools.add(school4);
		schools.add(school5);
		schools.add(school6);
		schools.add(school7);
		schools.add(school8);
		return schools;
	}

	public List<MeasurableParam> createMeasurableParams(School school) {
		List<MeasurableParam> measurableParams = new ArrayList<MeasurableParam>();

		measurableParams.add(createMeasurableParam("Attendance", "Attendance", school));
		measurableParams.add(createMeasurableParam("HomeWork", "Home Work", school));
		measurableParams.add(createMeasurableParam("Discipline", "Discipline", school));

		return measurableParams;
	}

	public List<SchoolHoliday> createHolidays(School school) throws ParseException {
		List<SchoolHoliday> schoolHolidays = new ArrayList<SchoolHoliday>();

		// Public Holidays
		schoolHolidays.add(createSchoolHoliday("01-JAN-2019", "01-JAN-2019", "New Year’s Day", school));
		schoolHolidays.add(createSchoolHoliday("04-MAR-2019", "04-MAR-2019", "Maha Shivaratri", school));
		schoolHolidays.add(createSchoolHoliday("21-MAR-2019", "21-MAR-2019", "Holi", school));
		schoolHolidays.add(createSchoolHoliday("05-JUN-2019", "05-JUN-2019", "Idul Fitr", school));
		schoolHolidays.add(createSchoolHoliday("15-AUG-2019", "15-AUG-2019", "Independence Day", school));
		schoolHolidays.add(createSchoolHoliday("10-SEP-2019", "10-SEP-2019", "Muharram", school));
		schoolHolidays.add(createSchoolHoliday("02-OCT-2019", "02-OCT-2019", "Gandhi Jayanti", school));
		schoolHolidays.add(createSchoolHoliday("08-OCT-2019", "08-OCT-2019", "Durga Puja and Dussehra", school));
		schoolHolidays.add(createSchoolHoliday("28-OCT-2019", "28-OCT-2019", "Diwali Holiday", school));
		schoolHolidays.add(createSchoolHoliday("12-NOV-2019", "12-NOV-2019", "Guru Nanak Jayanti", school));
		schoolHolidays.add(createSchoolHoliday("25-DEC-2019", "25-DEC-2019", "Christmas Day", school));

		// Non public holidays
		schoolHolidays.add(createSchoolHoliday("01-APR-2019", "31-MAY-2019", "Summer Holidays", school));
		schoolHolidays
				.add(createSchoolHoliday("06-FEB-2019", "07-FEB-2019", "State level inter school tournament", school));

		return schoolHolidays;
	}

	public List<SchoolWeekendWorkingDay> createSchoolWeekendWorkingDays(School school) throws ParseException {
		List<SchoolWeekendWorkingDay> workingDays = new ArrayList<SchoolWeekendWorkingDay>();
		workingDays.add(createSchoolWeekendWorkingDay("09-FEB-2019", "Compoff for inter school meet", school));
		workingDays.add(createSchoolWeekendWorkingDay("16-FEB-2019", "Compoff for inter school meet", school));
		return workingDays;
	}

	private SchoolWeekendWorkingDay createSchoolWeekendWorkingDay(String workingDate, String reason, School school)
			throws ParseException {
		SchoolWeekendWorkingDay schoolWeekendWorkingDay = new SchoolWeekendWorkingDay();
		schoolWeekendWorkingDay.setSchool(school);
		schoolWeekendWorkingDay.setWorkingDate(DateUtil.getParseDateObject(workingDate));
		schoolWeekendWorkingDay.setReason(reason);
		CommonHelper.setAuditTrailInfo(schoolWeekendWorkingDay);
		weekendWorkingDayRepository.save(schoolWeekendWorkingDay);
		return schoolWeekendWorkingDay;
	}

	private School createSchool(String schoolName, String state, String district, String cityName, String address) {
		School school = new School();
		school.setSchoolName(schoolName);
		school.setAddress(address);
		school.setCityName(cityName);
		school.setDistrict(district);
		school.setState(state);
		CommonHelper.setAuditTrailInfo(school);
		schoolRepository.save(school);
		return school;
	}

	public MeasurableParam createMeasurableParam(String title, String Description, School school) {
		MeasurableParam measurableParam = new MeasurableParam();
		measurableParam.setParameterTitle(title);
		measurableParam.setParameterDesc(Description);
		measurableParam.setSchool(school);
		CommonHelper.setAuditTrailInfo(measurableParam);
		measurableParamRepository.save(measurableParam);
		return measurableParam;
	}

	private SchoolHoliday createSchoolHoliday(String fromDate, String toDate, String description, School school)
			throws ParseException {
		SchoolHoliday schoolHoliday = new SchoolHoliday();
		schoolHoliday.setFromDate(DateUtil.getParseDateObject(fromDate));
		schoolHoliday.setToDate(DateUtil.getParseDateObject(toDate));
		schoolHoliday.setDescription(description);
		schoolHoliday.setSchool(school);
		CommonHelper.setAuditTrailInfo(schoolHoliday);
		schoolHolidayRepository.save(schoolHoliday);
		return schoolHoliday;
	}

	public void deleteMeasurableParams(List<MeasurableParam> measurableParams) {
		measurableParamRepository.deleteAll(measurableParams);
	}

	public void deleteSchoolWeekendWorkingDays(List<SchoolWeekendWorkingDay> schoolWeekendWorkingDays) {
		weekendWorkingDayRepository.deleteAll(schoolWeekendWorkingDays);
	}

	public void deleteHolidays(List<SchoolHoliday> schoolHolidays) {
		schoolHolidayRepository.deleteAll(schoolHolidays);
	}

	public void dropSchools(List<School> schools) {
		schoolRepository.deleteAll(schools);
	}
}
