package com.cognizant.outreach.microservices.perfdata.vo;

import java.util.ArrayList;
import java.util.List;

public class PerformanceDayVO {

	private String dateValue;
	private boolean isWorkingday;
	private List<PerformanceDataVO> performanceData;

	public PerformanceDayVO() {
	}

	public PerformanceDayVO(String dateValue, boolean isWorkingday, List<PerformanceDataVO> performanceData) {
		this.dateValue = dateValue;
		this.isWorkingday = isWorkingday;
		this.performanceData = performanceData;
	}

	public String getDateValue() {
		return dateValue;
	}

	public void setDateValue(String dateValue) {
		this.dateValue = dateValue;
	}

	public boolean isWorkingday() {
		return isWorkingday;
	}

	public void setWorkingday(boolean isWorkingday) {
		this.isWorkingday = isWorkingday;
	}

	public List<PerformanceDataVO> getPerformanceData() {
		if (null == this.performanceData) {
			this.performanceData = new ArrayList<>();
		}
		return performanceData;
	}

	public void setPerformanceData(List<PerformanceDataVO> performanceData) {
		this.performanceData = performanceData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateValue == null) ? 0 : dateValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerformanceDayVO other = (PerformanceDayVO) obj;
		if (dateValue == null) {
			if (other.dateValue != null)
				return false;
		} else if (!dateValue.equals(other.dateValue))
			return false;
		return true;
	}

}
