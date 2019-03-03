package com.cognizant.outreach.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the india_state_district database table.
 * 
 */
@Entity
@Table(name="india_state_district")
public class IndiaStateDistrict implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=200)
	private String district;

	@Column(nullable=false, length=200)
	private String state;

    public IndiaStateDistrict() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}