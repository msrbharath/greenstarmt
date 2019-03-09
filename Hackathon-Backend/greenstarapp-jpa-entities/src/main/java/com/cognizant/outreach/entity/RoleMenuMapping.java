package com.cognizant.outreach.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the role_menu_mapping database table.
 * 
 */
@Entity
@Table(name = "role_menu_mapping")
public class RoleMenuMapping extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// BI-directional many-to-one association to RoleDetail
	@ManyToOne
	@JoinColumn(name = "ROLE_ID", nullable = false)
	private RoleDetail roleDetail;

	// BI-directional many-to-one association to MainUiMenu
	@ManyToOne
	@JoinColumn(name = "MENU_ID", nullable = false)
	private MainUiMenu mainUiMenu;

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

	/**
	 * @return the mainUiMenu
	 */
	public MainUiMenu getMainUiMenu() {
		return mainUiMenu;
	}

	/**
	 * @param mainUiMenu
	 *            the mainUiMenu to set
	 */
	public void setMainUiMenu(MainUiMenu mainUiMenu) {
		this.mainUiMenu = mainUiMenu;
	}

}