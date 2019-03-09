package com.cognizant.outreach.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the school_weekend_working_days database table.
 * 
 */
@Entity
@Table(name = "school_weekend_working_days")
public class SchoolWeekendWorkingDay extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// Association to School
	@ManyToOne
	@JoinColumn(name = "SCHOOL_ID", nullable = false)
	private School school;

	@Temporal(TemporalType.DATE)
	@Column(name = "WORKING_DATE", nullable = false)
	private Date workingDate;

	@Column(nullable = false, length = 100)
	private String reason;

	/**
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * @param school
	 *            the school to set
	 */
	public void setSchool(School school) {
		this.school = school;
	}

	/**
	 * @return the workingDate
	 */
	public Date getWorkingDate() {
		return workingDate;
	}

	/**
	 * @param workingDate
	 *            the workingDate to set
	 */
	public void setWorkingDate(Date workingDate) {
		this.workingDate = workingDate;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

}