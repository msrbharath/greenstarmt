package com.cognizant.outreach.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the main_ui_menu database table.
 * 
 */
@Entity
@Table(name = "main_ui_menu")
public class MainUiMenu extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "MENU_NAME", nullable = false, length = 50)
	private String menuName;

	@Column(nullable = false, length = 200)
	private String description;

	// BI-directional many-to-one association to RoleMenuMapping
	@OneToMany(mappedBy = "mainUiMenu")
	private List<RoleMenuMapping> roleMenuMappings;

	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param menuName
	 *            the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
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

}