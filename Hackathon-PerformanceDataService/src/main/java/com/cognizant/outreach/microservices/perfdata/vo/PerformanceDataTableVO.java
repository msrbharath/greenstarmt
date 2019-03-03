package com.cognizant.outreach.microservices.perfdata.vo;

import java.util.List;

public class PerformanceDataTableVO {

	private Long schoolId;
	private String className;
	private String section;
	private Integer month;
	private String week;
	private String userId;
	private List<PerformanceHeaderVO> headers;
	private List<PerformanceRowVO> performanceRows;

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
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

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
