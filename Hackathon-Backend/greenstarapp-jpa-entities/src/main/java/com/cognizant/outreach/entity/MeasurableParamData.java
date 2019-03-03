package com.cognizant.outreach.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the measurable_param_data database table.
 * 
 */
@Entity
@Table(name = "measurable_param_data")
public class MeasurableParamData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "ID", unique = true, updatable = false, nullable = false)
	private Long id;

	@Column(name = "CREATED_DTM", nullable = false)
	private Date createdDtm;

	@Column(name = "CREATED_USER_ID", nullable = false, length = 90)
	private String createdUserId;

	@Column(name = "LAST_UPDATED_DTM", nullable = false)
	private Date lastUpdatedDtm;

	@Column(name = "LAST_UPDATED_USER_ID", nullable = false, length = 90)
	private String lastUpdatedUserId;

	@Temporal(TemporalType.DATE)
	@Column(name = "MEASURABLE_DATE", nullable = false)
	private Date measurableDate;

	@Column(name = "MEASURABLE_PARAM_VALUE", nullable = false, length = 1)
	private String measurableParamValue;

	// bi-directional many-to-one association to MeasurableParam
	@ManyToOne
	@JoinColumn(name = "MEASURABLE_PARAM_ID", nullable = false)
	private MeasurableParam measurableParam;

	// bi-directional many-to-one association to StudentSchoolAssoc
	@ManyToOne
	@JoinColumn(name = "STUD_SCHL_ASSOC_ID", nullable = false)
	private StudentSchoolAssoc studentSchoolAssoc;

	public MeasurableParamData() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDtm() {
		return createdDtm;
	}

	public void setCreatedDtm(Date createdDtm) {
		this.createdDtm = createdDtm;
	}

	public String getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Date getLastUpdatedDtm() {
		return lastUpdatedDtm;
	}

	public void setLastUpdatedDtm(Date lastUpdatedDtm) {
		this.lastUpdatedDtm = lastUpdatedDtm;
	}

	public String getLastUpdatedUserId() {
		return this.lastUpdatedUserId;
	}

	public void setLastUpdatedUserId(String lastUpdatedUserId) {
		this.lastUpdatedUserId = lastUpdatedUserId;
	}

	public Date getMeasurableDate() {
		return this.measurableDate;
	}

	public void setMeasurableDate(Date measurableDate) {
		this.measurableDate = measurableDate;
	}

	public String getMeasurableParamValue() {
		return this.measurableParamValue;
	}

	public void setMeasurableParamValue(String measurableParamValue) {
		this.measurableParamValue = measurableParamValue;
	}

	public MeasurableParam getMeasurableParam() {
		return this.measurableParam;
	}

	public void setMeasurableParam(MeasurableParam measurableParam) {
		this.measurableParam = measurableParam;
	}

	public StudentSchoolAssoc getStudentSchoolAssoc() {
		return this.studentSchoolAssoc;
	}

	public void setStudentSchoolAssoc(StudentSchoolAssoc studentSchoolAssoc) {
		this.studentSchoolAssoc = studentSchoolAssoc;
	}

}