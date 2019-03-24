package com.cognizant.outreach.microservices.perfdata.vo.metrics;

import java.util.List;

/**
 * @author Bharath
 *
 */
public class TeamwiseMetricsVO {

	private String teamName;
	private String className;
	private String paramName1;
	private String paramName2;
	private String paramName3;
	private String totalTitle;
	private List<ClassTeamwiseSectionData> sectionData;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getParamName1() {
		return paramName1;
	}

	public void setParamName1(String paramName1) {
		this.paramName1 = paramName1;
	}

	public String getParamName2() {
		return paramName2;
	}

	public void setParamName2(String paramName2) {
		this.paramName2 = paramName2;
	}

	public String getParamName3() {
		return paramName3;
	}

	public void setParamName3(String paramName3) {
		this.paramName3 = paramName3;
	}

	public String getTotalTitle() {
		return totalTitle;
	}

	public void setTotalTitle(String totalTitle) {
		this.totalTitle = totalTitle;
	}

	public List<ClassTeamwiseSectionData> getSectionData() {
		return sectionData;
	}

	public void setSectionData(List<ClassTeamwiseSectionData> sectionData) {
		this.sectionData = sectionData;
	}

}
