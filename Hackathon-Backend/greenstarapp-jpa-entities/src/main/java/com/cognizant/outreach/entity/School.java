package com.cognizant.outreach.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the school database table.
 * 
 */
@Entity
@Table(name = "school")
public class School extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "SCHOOL_NAME", nullable = false, length = 90)
	private String schoolName;

	@Column(name = "ADDRESS", nullable = false, length = 500)
	private String address;

	@Column(name = "CITY_NAME", nullable = false, length = 45)
	private String cityName;

	@Column(name = "DISTRICT", nullable = false, length = 500)
	private String district;

	@Column(name = "STATE", nullable = false, length = 200)
	private String state;

	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}

	/**
	 * @param schoolName
	 *            the schoolName to set
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 *            the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district
	 *            the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

}