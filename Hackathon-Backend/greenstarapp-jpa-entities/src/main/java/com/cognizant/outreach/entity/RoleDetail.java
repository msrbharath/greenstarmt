package com.cognizant.outreach.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the role_detail database table.
 * 
 */
@Entity
@Table(name = "role_detail")
public class RoleDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "ROLE_NAME", nullable = false, length = 50)
	private String roleName;

	@Column(nullable = false, length = 200)
	private String description;

	// BI - directional many-to-one association to RoleMenuMapping
	@OneToMany(mappedBy = "roleDetail")
	private List<RoleMenuMapping> roleMenuMappings;

	// BI - directional many-to-one association to UserRoleMapping
	@OneToMany(mappedBy = "roleDetail")
	private List<UserRoleMapping> userRoleMappings;

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the roleMenuMappings
	 */
	public List<RoleMenuMapping> getRoleMenuMappings() {
		return roleMenuMappings;
	}

	/**
	 * @param roleMenuMappings
	 *            the roleMenuMappings to set
	 */
	public void setRoleMenuMappings(List<RoleMenuMapping> roleMenuMappings) {
		this.roleMenuMappings = roleMenuMappings;
	}

	/**
	 * @return the userRoleMappings
	 */
	public List<UserRoleMapping> getUserRoleMappings() {
		return userRoleMappings;
	}

	/**
	 * @param userRoleMappings
	 *            the userRoleMappings to set
	 */
	public void setUserRoleMappings(List<UserRoleMapping> userRoleMappings) {
		this.userRoleMappings = userRoleMappings;
	}

}