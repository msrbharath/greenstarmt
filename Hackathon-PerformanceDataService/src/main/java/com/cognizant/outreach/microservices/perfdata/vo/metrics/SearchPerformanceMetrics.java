package com.cognizant.outreach.microservices.perfdata.vo.metrics;

public class SearchPerformanceMetrics {


	private Long schoolId;
	private String schoolName;
	private Long classId;
	private String className;
	private String sectionName;
	private Integer month;
	private Integer month2;
	private String week;
	private String labelDateRange;

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

	public Integer getMonth2() {
		return month2;
	}

	public void setMonth2(Integer month2) {
		this.month2 = month2;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getLabelDateRange() {
		return labelDateRange;
	}

	public void setLabelDateRange(String labelDateRange) {
		this.labelDateRange = labelDateRange;
	}



}
