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
 * The persistent class for the school_holidays database table.
 * 
 */
@Entity
@Table(name = "school_holidays")
public class SchoolHoliday extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// Association to School
	@ManyToOne
	@JoinColumn(name = "SCHOOL_ID", nullable = false)
	private School school;

	@Temporal(TemporalType.DATE)
	@Column(name = "FROM_DATE", nullable = false)
	private Date fromDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "TO_DATE", nullable = false)
	private Date toDate;

	@Column(nullable = false, length = 100)
	private String description;

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
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}