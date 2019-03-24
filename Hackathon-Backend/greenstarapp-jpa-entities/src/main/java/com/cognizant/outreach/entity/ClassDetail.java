package com.cognizant.outreach.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the class database table.
 * 
 */
@Entity
@Table(name = "class")
public class ClassDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "CLASS_NAME", nullable = false, length = 45)
	private String className;

	@Column(nullable = false, length = 45)
	private String section;

	// Association to School
	@ManyToOne
	@JoinColumn(name = "SCHOOL_ID", nullable = false)
	private School school;

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @param section
	 *            the section to set
	 */
	public void setSection(String section) {
		this.section = section;
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
	
	public String getClassAndSection() {
		return this.className + "-" + this.section;
	}

}