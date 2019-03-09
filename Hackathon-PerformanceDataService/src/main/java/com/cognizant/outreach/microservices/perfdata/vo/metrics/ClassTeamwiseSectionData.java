package com.cognizant.outreach.microservices.perfdata.vo.metrics;

public class ClassTeamwiseSectionData {

	private String section;
	private String teamName;
	private Long attendanceTotal;
	private Long homeworkTotal;
	private Long disciplineTotal;

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

	public Long getAttendanceTotal() {
		return attendanceTotal;
	}

	public void setAttendanceTotal(Long attendanceTotal) {
		this.attendanceTotal = attendanceTotal;
	}

	public Long getHomeworkTotal() {
		return homeworkTotal;
	}

	public void setHomeworkTotal(Long homeworkTotal) {
		this.homeworkTotal = homeworkTotal;
	}

	public Long getDisciplineTotal() {
		return disciplineTotal;
	}

	public void setDisciplineTotal(Long disciplineTotal) {
		this.disciplineTotal = disciplineTotal;
	}

}
