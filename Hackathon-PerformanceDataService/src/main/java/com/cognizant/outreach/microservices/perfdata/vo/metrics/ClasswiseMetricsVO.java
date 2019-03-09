package com.cognizant.outreach.microservices.perfdata.vo.metrics;

import java.util.List;

public class ClasswiseMetricsVO {

	private String className;
	private List<ClassTeamwiseSectionData> sectionData;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<ClassTeamwiseSectionData> getSectionData() {
		return sectionData;
	}

	public void setSectionData(List<ClassTeamwiseSectionData> sectionData) {
		this.sectionData = sectionData;
	}

}
