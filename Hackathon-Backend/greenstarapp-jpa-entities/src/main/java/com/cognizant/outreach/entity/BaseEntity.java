package com.cognizant.outreach.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Base entity for all the JPA entities
 * 
 * @author Magesh
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, updatable = false, nullable = false)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DTM", nullable = false)
	private Date createdDtm;

	@Column(name = "CREATED_USER_ID", nullable = false, length = 90)
	private String createdUserId;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATED_DTM", nullable = false)
	private Date lastUpdatedDtm;

	@Column(name = "LAST_UPDATED_USER_ID", nullable = false, length = 90)
	private String lastUpdatedUserId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the createdDtm
	 */
	public Date getCreatedDtm() {
		return createdDtm;
	}

	/**
	 * @param createdDtm
	 *            the createdDtm to set
	 */
	public void setCreatedDtm(Date createdDtm) {
		this.createdDtm = createdDtm;
	}

	/**
	 * @return the createdUserId
	 */
	public String getCreatedUserId() {
		return createdUserId;
	}

	/**
	 * @param createdUserId
	 *            the createdUserId to set
	 */
	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	/**
	 * @return the lastUpdatedDtm
	 */
	public Date getLastUpdatedDtm() {
		return lastUpdatedDtm;
	}

	/**
	 * @param lastUpdatedDtm
	 *            the lastUpdatedDtm to set
	 */
	public void setLastUpdatedDtm(Date lastUpdatedDtm) {
		this.lastUpdatedDtm = lastUpdatedDtm;
	}

	/**
	 * @return the lastUpdatedUserId
	 */
	public String getLastUpdatedUserId() {
		return lastUpdatedUserId;
	}

	/**
	 * @param lastUpdatedUserId
	 *            the lastUpdatedUserId to set
	 */
	public void setLastUpdatedUserId(String lastUpdatedUserId) {
		this.lastUpdatedUserId = lastUpdatedUserId;
	}

}
