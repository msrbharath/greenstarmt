package com.cognizant.outreach.microservices.perfdata.vo.metrics;

public class AverageRowVO {
	
	private double month1Average;
	private double month2Average;
	private double changeinAverage;
	public double getMonth1Average() {
		return month1Average;
	}
	public void setMonth1Average(double month1Average) {
		this.month1Average = month1Average;
	}
	public double getMonth2Average() {
		return month2Average;
	}
	public void setMonth2Average(double month2Average) {
		this.month2Average = month2Average;
	}
	public double getChangeinAverage() {
		return changeinAverage;
	}
	public void setChangeinAverage(double changeinAverage) {
		this.changeinAverage = changeinAverage;
	}

}
