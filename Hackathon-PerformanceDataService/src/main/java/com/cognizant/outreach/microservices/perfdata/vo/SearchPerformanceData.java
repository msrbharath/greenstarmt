package com.cognizant.outreach.microservices.perfdata.vo;

public class SearchPerformanceData {

	private Long schoolId;
	private String schoolName;
	private Long classId;
	private String className;
	private String sectionName;
	private Integer month;
	private String week;
	private String monthRange;
	private String labelDateRange;
	private String startDate;
	private String endDate;

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getMonthRange() {
		return monthRange;
	}

	public void setMonthRange(String monthRange) {
		this.monthRange = monthRange;
	}

	public String getLabelDateRange() {
		return labelDateRange;
	}

	public void setLabelDateRange(String labelDateRange) {
		this.labelDateRange = labelDateRange;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
