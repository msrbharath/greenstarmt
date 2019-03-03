package com.cognizant.outreach.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the class database table.
 * 
 */
@Entity
@Table(name="class")
public class ClassDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="CLASS_NAME", nullable=false, length=45)
	private String className;

	@Column(name="CREATED_DTM", nullable=false)
	private Timestamp createdDtm;

	@Column(name="CREATED_USER_ID", nullable=false, length=90)
	private String createdUserId;

	@Column(name="LAST_UPDATED_DTM", nullable=false)
	private Timestamp lastUpdatedDtm;

	@Column(name="LAST_UPDATED_USER_ID", nullable=false, length=90)
	private String lastUpdatedUserId;

	@Column(nullable=false, length=45)
	private String section;

	//bi-directional many-to-one association to School
    @ManyToOne
	@JoinColumn(name="SCHOOL_ID", nullable=false)
	private School school;

	//bi-directional many-to-one association to StudentSchoolAssoc
	@OneToMany(mappedBy="clazz")
	private List<StudentSchoolAssoc> studentSchoolAssocs;

    public ClassDetail() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
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

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
	
	public List<StudentSchoolAssoc> getStudentSchoolAssocs() {
		return this.studentSchoolAssocs;
	}

	public void setStudentSchoolAssocs(List<StudentSchoolAssoc> studentSchoolAssocs) {
		this.studentSchoolAssocs = studentSchoolAssocs;
	}
	
}