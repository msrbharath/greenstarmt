package com.cognizant.outreach.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the student database table.
 * 
 */
@Entity
@Table(name="student")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(nullable=false, length=45)
	private String address;

	@Column(name="CREATED_DTM", nullable=false)
	private Timestamp createdDtm;

	@Column(name="CREATED_USER_ID", nullable=false, length=90)
	private String createdUserId;

    @Temporal( TemporalType.DATE)
	private Date dob;

	@Column(name="LAST_UPDATED_DTM", nullable=false)
	private Timestamp lastUpdatedDtm;

	@Column(name="LAST_UPDATED_USER_ID", nullable=false, length=90)
	private String lastUpdatedUserId;

	@Column(name="STUDENT_NAME", nullable=false, length=90)
	private String studentName;

	//bi-directional many-to-one association to StudentSchoolAssoc
	@OneToMany(mappedBy="student")
	private List<StudentSchoolAssoc> studentSchoolAssocs;

    public Student() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public List<StudentSchoolAssoc> getStudentSchoolAssocs() {
		return this.studentSchoolAssocs;
	}

	public void setStudentSchoolAssocs(List<StudentSchoolAssoc> studentSchoolAssocs) {
		this.studentSchoolAssocs = studentSchoolAssocs;
	}
	
}