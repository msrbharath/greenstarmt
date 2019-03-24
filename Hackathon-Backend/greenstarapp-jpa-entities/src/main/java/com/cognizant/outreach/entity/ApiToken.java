package com.cognizant.outreach.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the api_tokens database table.
 * 
 */
@Entity
@Table(name = "api_tokens")
public class ApiToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false, length = 500)
	private String token;

	@Column(nullable = false, length = 50)
	private String userid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", nullable = true)
	private Date endTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", nullable = false)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_dt", nullable = false)
	private Date last_updated_dt;

	public ApiToken() {
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the last_updated_dt
	 */
	public Date getLast_updated_dt() {
		return last_updated_dt;
	}

	/**
	 * @param last_updated_dt
	 *            the last_updated_dt to set
	 */
	public void setLast_updated_dt(Date last_updated_dt) {
		this.last_updated_dt = last_updated_dt;
	}

}