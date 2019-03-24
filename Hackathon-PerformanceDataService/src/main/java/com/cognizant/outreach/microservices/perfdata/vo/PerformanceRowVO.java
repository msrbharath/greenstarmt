package com.cognizant.outreach.microservices.perfdata.vo;

import java.util.ArrayList;
import java.util.List;

public class PerformanceRowVO {

	private String rollId;
	private String studentName;
	private String schoolName;
	private String className;
	private String section;
	private String teamName;
	private List<PerformanceDayVO> performanceDays;

	public PerformanceRowVO() {
	}

	public String getRollId() {
		return rollId;
	}

	public void setRollId(String rollId) {
		this.rollId = rollId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<PerformanceDayVO> getPerformanceDays() {

		if (null == this.performanceDays) {
			this.performanceDays = new ArrayList<>();
		}

		return performanceDays;
	}

	public void setPerformanceDays(List<PerformanceDayVO> performanceDays) {
		this.performanceDays = performanceDays;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rollId == null) ? 0 : rollId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerformanceRowVO other = (PerformanceRowVO) obj;
		if (rollId == null) {
			if (other.rollId != null)
				return false;
		} else if (!rollId.equals(other.rollId))
			return false;
		return true;
	}

}
