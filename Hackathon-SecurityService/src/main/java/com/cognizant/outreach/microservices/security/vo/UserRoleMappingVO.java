/**
 * ${UserRoleMappingVO}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  09/Mar/2019            Panneer        	Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.security.vo;

public class UserRoleMappingVO {

	private long id;
	private String userId;
	private String roleName;

	private String loggedUserId;

	public UserRoleMappingVO() {
	}

	public UserRoleMappingVO(long id, String userId, String roleName) {
		this.id = id;
		this.userId = userId;
		this.roleName = roleName;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

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
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the loggedUserId
	 */
	public String getLoggedUserId() {
		return loggedUserId;
	}

	/**
	 * @param loggedUserId
	 *            the loggedUserId to set
	 */
	public void setLoggedUserId(String loggedUserId) {
		this.loggedUserId = loggedUserId;
	}

}
