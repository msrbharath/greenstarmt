package com.cognizant.outreach.microservices.perfdata.vo;

import java.util.List;

public class PerformanceDataTableVO {

	private Long schoolId;
	private String schoolName;
	private Long classId;
	private String className;
	private String section;
	private Integer month;
	private String monthName;
	private String week;
	private String labelDateRange;
	private String userId;
	private int totalSubTitle;
	private List<PerformanceHeaderVO> headers;
	private List<PerformanceRowVO> performanceRows;

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

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getTotalSubTitle() {
		return totalSubTitle;
	}

	public void setTotalSubTitle(int totalSubTitle) {
		this.totalSubTitle = totalSubTitle;
	}

	public List<PerformanceHeaderVO> getHeaders() {
		return headers;
	}

	public void setHeaders(List<PerformanceHeaderVO> headers) {
		this.headers = headers;
	}

	public List<PerformanceRowVO> getPerformanceRows() {
		return performanceRows;
	}

	public void setPerformanceRows(List<PerformanceRowVO> performanceRows) {
		this.performanceRows = performanceRows;
	}

}
