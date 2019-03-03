package com.cognizant.outreach.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the student_school_assoc database table.
 * 
 */
@Entity
@Table(name="student_school_assoc")
public class StudentSchoolAssoc implements Serializable {
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

	@Column(name="ROLL_ID", nullable=false, length=45)
	private String rollId;

	@Column(nullable=false, length=45)
	private String status;

	@Column(name="TEAM_NAME", nullable=false, length=45)
	private String teamName;

	//bi-directional many-to-one association to MeasurableParamData
	@OneToMany(mappedBy="studentSchoolAssoc")
	private List<MeasurableParamData> measurableParamData;

	//bi-directional many-to-one association to ClassDetail
    @ManyToOne
	@JoinColumn(name="CLASS_ID", nullable=false)
	private ClassDetail clazz;

	//bi-directional many-to-one association to Student
    @ManyToOne
	@JoinColumn(name="STUDENT_DETAIL_ID", nullable=false)
	private Student student;

    public StudentSchoolAssoc() {
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

	public String getRollId() {
		return this.rollId;
	}

	public void setRollId(String rollId) {
		this.rollId = rollId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<MeasurableParamData> getMeasurableParamData() {
		return this.measurableParamData;
	}

	public void setMeasurableParamData(List<MeasurableParamData> measurableParamData) {
		this.measurableParamData = measurableParamData;
	}
	
	public ClassDetail getClazz() {
		return this.clazz;
	}

	public void setClazz(ClassDetail clazz) {
		this.clazz = clazz;
	}
	
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
}