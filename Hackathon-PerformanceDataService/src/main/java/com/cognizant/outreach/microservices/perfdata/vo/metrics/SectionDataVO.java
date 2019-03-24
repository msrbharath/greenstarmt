package com.cognizant.outreach.microservices.perfdata.vo.metrics;

/**
 * @author Bharath
 *
 */
public class SectionDataVO {

	private String section;
	private double month1percentage;
	private double month2percentage;
	private double increasePercentage;

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public double getMonth1percentage() {
		return month1percentage;
	}

	public void setMonth1percentage(double month1percentage) {
		this.month1percentage = month1percentage;
	}

	public double getMonth2percentage() {
		return month2percentage;
	}

	public void setMonth2percentage(double month2percentage) {
		this.month2percentage = month2percentage;
	}

	public double getChangeinpercentage() {
		return changeinpercentage;
	}

	public void setChangeinpercentage(double changeinpercentage) {
		this.changeinpercentage = changeinpercentage;
	}

	public double getIncreasePercentage() {
		return increasePercentage;
	}

	public void setIncreasePercentage(double increasePercentage) {
		this.increasePercentage = increasePercentage;
	}

	private double changeinpercentage;
}
