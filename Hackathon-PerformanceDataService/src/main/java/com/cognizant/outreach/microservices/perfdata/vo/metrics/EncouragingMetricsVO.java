package com.cognizant.outreach.microservices.perfdata.vo.metrics;

import java.util.List;

public class EncouragingMetricsVO {

	private String metricsType;
	private String classId;
	private String className;
	private List<SectionDataVO> sectionData;
	private Integer month;
	private String monthName;
	private AverageRowVO averageRow;

	public String getMetricsType() {
		return metricsType;
	}

	public void setMetricsType(String metricsType) {
		this.metricsType = metricsType;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<SectionDataVO> getSectionData() {
		return sectionData;
	}

	public void setSectionData(List<SectionDataVO> sectionData) {
		this.sectionData = sectionData;
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

	public AverageRowVO getAverageRow() {
		return averageRow;
	}

	public void setAverageRow(AverageRowVO averageRow) {
		this.averageRow = averageRow;
	}
}
