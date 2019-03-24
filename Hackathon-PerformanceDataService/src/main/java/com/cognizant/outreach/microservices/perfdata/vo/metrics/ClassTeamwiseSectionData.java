package com.cognizant.outreach.microservices.perfdata.vo.metrics;

/**
 * @author Bharath
 *
 */
public class ClassTeamwiseSectionData {

	private String section;
	private String teamName;
	private String param1Title;
	private Long param1Total;
	private String param2Title;
	private Long param2Total;
	private String param3Title;
	private Long param3Total;
	private String totalTitle;
	private Long total;

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getParam1Title() {
		return param1Title;
	}

	public void setParam1Title(String param1Title) {
		this.param1Title = param1Title;
	}

	public Long getParam1Total() {
		return param1Total;
	}

	public void setParam1Total(Long param1Total) {
		this.param1Total = param1Total;
	}

	public String getParam2Title() {
		return param2Title;
	}

	public void setParam2Title(String param2Title) {
		this.param2Title = param2Title;
	}

	public Long getParam2Total() {
		return param2Total;
	}

	public void setParam2Total(Long param2Total) {
		this.param2Total = param2Total;
	}

	public String getParam3Title() {
		return param3Title;
	}

	public void setParam3Title(String param3Title) {
		this.param3Title = param3Title;
	}

	public Long getParam3Total() {
		return param3Total;
	}

	public void setParam3Total(Long param3Total) {
		this.param3Total = param3Total;
	}

	public String getTotalTitle() {
		return totalTitle;
	}

	public void setTotalTitle(String totalTitle) {
		this.totalTitle = totalTitle;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
