package com.cognizant.outreach.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the user_role_mapping database table.
 * 
 */
@Entity
@Table(name = "user_role_mapping")
public class UserRoleMapping extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "USER_ID", nullable = false, length = 90)
	private String userId;

	// Association to RoleDetail
	@ManyToOne
	@JoinColumn(name = "ROLE_ID", nullable = false)
	private RoleDetail roleDetail;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the roleDetail
	 */
	public RoleDetail getRoleDetail() {
		return roleDetail;
	}

	/**
	 * @param roleDetail
	 *            the roleDetail to set
	 */
	public void setRoleDetail(RoleDetail roleDetail) {
		this.roleDetail = roleDetail;
	}

}