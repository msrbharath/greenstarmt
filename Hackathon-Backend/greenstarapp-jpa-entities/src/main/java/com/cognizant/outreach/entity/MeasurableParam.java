package com.cognizant.outreach.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the measurable_param database table.
 * 
 */
@Entity
@Table(name="measurable_param")
public class MeasurableParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private String id;

	@Column(name="CREATED_DTM", nullable=false)
	private Timestamp createdDtm;

	@Column(name="CREATED_USER_ID", nullable=false, length=90)
	private String createdUserId;

	@Column(name="LAST_UPDATED_DTM", nullable=false)
	private Timestamp lastUpdatedDtm;

	@Column(name="LAST_UPDATED_USER_ID", nullable=false, length=90)
	private String lastUpdatedUserId;

	@Column(name="PARAMETER_DESC", nullable=false, length=255)
	private String parameterDesc;

	@Column(name="PARAMETER_TITLE", nullable=false, length=90)
	private String parameterTitle;

	//bi-directional many-to-one association to School
    @ManyToOne
	@JoinColumn(name="SCHOOL_ID", nullable=false)
	private School school;

	//bi-directional many-to-one association to MeasurableParamData
	@OneToMany(mappedBy="measurableParam")
	private List<MeasurableParamData> measurableParamData;

    public MeasurableParam() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreatedDtm() {
		return this.createdDtm;
	}

	public void setCreatedDtm(Timestamp createdDtm) {
		this.createdDtm = createdDtm;
	}

	public String getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Timestamp getLastUpdatedDtm() {
		return this.lastUpdatedDtm;
	}

	public void setLastUpdatedDtm(Timestamp lastUpdatedDtm) {
		this.lastUpdatedDtm = lastUpdatedDtm;
	}

	public String getLastUpdatedUserId() {
		return this.lastUpdatedUserId;
	}

	public void setLastUpdatedUserId(String lastUpdatedUserId) {
		this.lastUpdatedUserId = lastUpdatedUserId;
	}

	public String getParameterDesc() {
		return this.parameterDesc;
	}

	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}

	public String getParameterTitle() {
		return this.parameterTitle;
	}

	public void setParameterTitle(String parameterTitle) {
		this.parameterTitle = parameterTitle;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
	
	public List<MeasurableParamData> getMeasurableParamData() {
		return this.measurableParamData;
	}

	public void setMeasurableParamData(List<MeasurableParamData> measurableParamData) {
		this.measurableParamData = measurableParamData;
	}
	
}