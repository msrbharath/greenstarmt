package com.cognizant.outreach.microservices.perfdata.vo.metrics;

import java.util.List;

public class TeamwiseMetricsVO {

	private String teamName;
	private List<ClassTeamwiseSectionData> sectionData;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<ClassTeamwiseSectionData> getSectionData() {
		return sectionData;
	}

	public void setSectionData(List<ClassTeamwiseSectionData> sectionData) {
		this.sectionData = sectionData;
	}
}
