package com.cognizant.outreach.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the measurable_param database table.
 * 
 */
@Entity
@Table(name = "measurable_param")
public class MeasurableParam extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "PARAMETER_TITLE", nullable = false, length = 90)
	private String parameterTitle;

	@Column(name = "PARAMETER_DESC", nullable = false, length = 255)
	private String parameterDesc;

	// BI-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name = "SCHOOL_ID", nullable = false)
	private School school;

	/**
	 * @return the parameterTitle
	 */
	public String getParameterTitle() {
		return parameterTitle;
	}

	/**
	 * @param parameterTitle
	 *            the parameterTitle to set
	 */
	public void setParameterTitle(String parameterTitle) {
		this.parameterTitle = parameterTitle;
	}

	/**
	 * @return the parameterDesc
	 */
	public String getParameterDesc() {
		return parameterDesc;
	}

	/**
	 * @param parameterDesc
	 *            the parameterDesc to set
	 */
	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}

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

}