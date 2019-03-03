package com.cognizant.outreach.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the role_menu_mapping database table.
 * 
 */
@Entity
@Table(name="role_menu_mapping")
public class RoleMenuMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private String id;

	@Column(name="CREATED_DTM", nullable=false)
	private Timestamp createdDtm;

	@Column(name="CREATED_USER_ID", nullable=false, length=90)
	private String createdUserId;

	@Column(name="LAST_UPDATED_DTM", nullable=false)
	private Timestamp lastUpdatedDtm;

	@Column(name="LAST_UPDATED_USER_ID", nullable=false, length=90)
	private String lastUpdatedUserId;

	//bi-directional many-to-one association to MainUiMenu
    @ManyToOne
	@JoinColumn(name="MENU_ID", nullable=false)
	private MainUiMenu mainUiMenu;

	//bi-directional many-to-one association to RoleDetail
    @ManyToOne
	@JoinColumn(name="ROLE_ID", nullable=false)
	private RoleDetail roleDetail;

    public RoleMenuMapping() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreatedDtm() {
		return this.createdDtm;
	}

	public void setCreatedDtm(Timestamp createdDtm) {
		this.createdDtm = createdDtm;
	}

	public String getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Timestamp getLastUpdatedDtm() {
		return this.lastUpdatedDtm;
	}

	public void setLastUpdatedDtm(Timestamp lastUpdatedDtm) {
		this.lastUpdatedDtm = lastUpdatedDtm;
	}

	public String getLastUpdatedUserId() {
		return this.lastUpdatedUserId;
	}

	public void setLastUpdatedUserId(String lastUpdatedUserId) {
		this.lastUpdatedUserId = lastUpdatedUserId;
	}

	public MainUiMenu getMainUiMenu() {
		return this.mainUiMenu;
	}

	public void setMainUiMenu(MainUiMenu mainUiMenu) {
		this.mainUiMenu = mainUiMenu;
	}
	
	public RoleDetail getRoleDetail() {
		return this.roleDetail;
	}

	public void setRoleDetail(RoleDetail roleDetail) {
		this.roleDetail = roleDetail;
	}
	
}