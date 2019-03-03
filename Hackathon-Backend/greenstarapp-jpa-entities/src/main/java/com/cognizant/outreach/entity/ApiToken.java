package com.cognizant.outreach.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the api_tokens database table.
 * 
 */
@Entity
@Table(name="api_tokens")
public class ApiToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=500)
	private String token;

	@Column(nullable=false, length=50)
	private String userid;
	
    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="end_time", nullable=false)
	private Date endTime;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="start_time", nullable=false)
	private Date startTime;

    public ApiToken() {
    }

  
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}