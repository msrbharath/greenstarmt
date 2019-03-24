package com.cognizant.outreach.microservices.perfdata.vo.metrics;

/**
 * @author Bharath
 *
 */
public class DashboardSingleVO {

	private String name;
	private Integer value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer integer) {
		this.value = integer;
	}

}
