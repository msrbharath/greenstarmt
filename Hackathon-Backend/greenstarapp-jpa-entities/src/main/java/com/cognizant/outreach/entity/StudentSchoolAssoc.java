package com.cognizant.outreach.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the student_school_assoc database table.
 * 
 */
@Entity
@Table(name = "student_school_assoc")
public class StudentSchoolAssoc extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// Association to Student
	@ManyToOne(cascade = { CascadeType.MERGE})
	@JoinColumn(name = "STUDENT_DETAIL_ID", nullable = false)
	private Student student;

	@Column(name = "ROLL_ID", nullable = false, length = 45)
	private String rollId;

	// Association to ClassDetail
	@ManyToOne(cascade = { CascadeType.MERGE})
	@JoinColumn(name = "CLASS_ID", nullable = false)
	private ClassDetail clazz;

	@Column(name = "TEAM_NAME", nullable = false, length = 45)
	private String teamName;

	@Column(nullable = false, length = 45)
	private String status;

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return the rollId
	 */
	public String getRollId() {
		return rollId;
	}

	/**
	 * @param rollId
	 *            the rollId to set
	 */
	public void setRollId(String rollId) {
		this.rollId = rollId;
	}

	/**
	 * @return the clazz
	 */
	public ClassDetail getClazz() {
		return clazz;
	}

	/**
	 * @param clazz
	 *            the clazz to set
	 */
	public void setClazz(ClassDetail clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName
	 *            the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}