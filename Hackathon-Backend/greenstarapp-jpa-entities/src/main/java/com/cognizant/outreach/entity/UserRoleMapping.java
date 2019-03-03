package com.cognizant.outreach.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user_role_mapping database table.
 * 
 */
@Entity
@Table(name="user_role_mapping")
public class UserRoleMapping implements Serializable {
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

	@Column(name="USER_ID", nullable=false, length=90)
	private String userId;

	//bi-directional many-to-one association to RoleDetail
    @ManyToOne
	@JoinColumn(name="ROLE_ID", nullable=false)
	private RoleDetail roleDetail;

    public UserRoleMapping() {
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public RoleDetail getRoleDetail() {
		return this.roleDetail;
	}

	public void setRoleDetail(RoleDetail roleDetail) {
		this.roleDetail = roleDetail;
	}
	
}