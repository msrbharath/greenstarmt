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
 * The persistent class for the measurable_param_data database table.
 * 
 */
@Entity
@Table(name = "measurable_param_data")
public class MeasurableParamData extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// bi-directional many-to-one association to StudentSchoolAssoc
	@ManyToOne
	@JoinColumn(name = "STUD_SCHL_ASSOC_ID", nullable = false)
	private StudentSchoolAssoc studentSchoolAssoc;

	// bi-directional many-to-one association to MeasurableParam
	@ManyToOne
	@JoinColumn(name = "MEASURABLE_PARAM_ID", nullable = false)
	private MeasurableParam measurableParam;

	@Temporal(TemporalType.DATE)
	@Column(name = "MEASURABLE_DATE", nullable = false)
	private Date measurableDate;

	@Column(name = "MEASURABLE_PARAM_VALUE", nullable = false)
	private int measurableParamValue;

	/**
	 * @return the studentSchoolAssoc
	 */
	public StudentSchoolAssoc getStudentSchoolAssoc() {
		return studentSchoolAssoc;
	}

	/**
	 * @param studentSchoolAssoc
	 *            the studentSchoolAssoc to set
	 */
	public void setStudentSchoolAssoc(StudentSchoolAssoc studentSchoolAssoc) {
		this.studentSchoolAssoc = studentSchoolAssoc;
	}

	/**
	 * @return the measurableParam
	 */
	public MeasurableParam getMeasurableParam() {
		return measurableParam;
	}

	/**
	 * @param measurableParam
	 *            the measurableParam to set
	 */
	public void setMeasurableParam(MeasurableParam measurableParam) {
		this.measurableParam = measurableParam;
	}

	/**
	 * @return the measurableDate
	 */
	public Date getMeasurableDate() {
		return measurableDate;
	}

	/**
	 * @param measurableDate
	 *            the measurableDate to set
	 */
	public void setMeasurableDate(Date measurableDate) {
		this.measurableDate = measurableDate;
	}

	/**
	 * @return the measurableParamValue
	 */
	public int getMeasurableParamValue() {
		return measurableParamValue;
	}

	/**
	 * @param measurableParamValue
	 *            the measurableParamValue to set
	 */
	public void setMeasurableParamValue(int measurableParamValue) {
		this.measurableParamValue = measurableParamValue;
	}

}