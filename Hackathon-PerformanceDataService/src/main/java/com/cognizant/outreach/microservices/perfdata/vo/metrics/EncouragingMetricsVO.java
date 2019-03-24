package com.cognizant.outreach.microservices.perfdata.vo.metrics;

import java.util.List;

/**
 * @author Bharath
 *
 */
public class EncouragingMetricsVO {

	private String metricsType;
	private String classId;
	private String className;
	private List<SectionDataVO> sectionData;
	private Integer month1;
	private String monthName1;
	private Integer month2;
	private String monthName2;
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

	public Integer getMonth1() {
		return month1;
	}

	public void setMonth1(Integer month1) {
		this.month1 = month1;
	}

	public String getMonthName1() {
		return monthName1;
	}

	public void setMonthName1(String monthName1) {
		this.monthName1 = monthName1;
	}

	public Integer getMonth2() {
		return month2;
	}

	public void setMonth2(Integer month2) {
		this.month2 = month2;
	}

	public String getMonthName2() {
		return monthName2;
	}

	public void setMonthName2(String monthName2) {
		this.monthName2 = monthName2;
	}

	public AverageRowVO getAverageRow() {
		return averageRow;
	}

	public void setAverageRow(AverageRowVO averageRow) {
		this.averageRow = averageRow;
	}
}
