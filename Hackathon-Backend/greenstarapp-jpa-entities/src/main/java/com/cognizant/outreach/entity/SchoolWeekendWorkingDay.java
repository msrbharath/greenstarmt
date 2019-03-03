package com.cognizant.outreach.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the school_weekend_working_days database table.
 * 
 */
@Entity
@Table(name="school_weekend_working_days")
public class SchoolWeekendWorkingDay implements Serializable {
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

	@Column(nullable=false, length=100)
	private String reason;

    @Temporal( TemporalType.DATE)
	@Column(name="WORKING_DATE", nullable=false)
	private Date workingDate;

	//bi-directional many-to-one association to School
    @ManyToOne
	@JoinColumn(name="SCHOOL_ID", nullable=false)
	private School school;

    public SchoolWeekendWorkingDay() {
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

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getWorkingDate() {
		return this.workingDate;
	}

	public void setWorkingDate(Date workingDate) {
		this.workingDate = workingDate;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
	
}