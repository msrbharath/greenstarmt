package com.cognizant.outreach.microservices.perfdata.vo;

import java.util.ArrayList;
import java.util.List;

public class PerformanceHeaderVO {

	private String title;
	private String alais;
	private boolean checkValue;
	private boolean isWorkingDay;
	private List<PerformanceHeaderVO> subTitleList;

	public PerformanceHeaderVO() {
	}

	public PerformanceHeaderVO(String title, String alais, boolean checkValue, boolean isWorkingDay,
			List<PerformanceHeaderVO> subTitleList) {
		this.title = title;
		this.alais = alais;
		this.checkValue = checkValue;
		this.subTitleList = subTitleList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlais() {
		return alais;
	}

	public void setAlais(String alais) {
		this.alais = alais;
	}

	public boolean isCheckValue() {
		return checkValue;
	}

	public void setCheckValue(boolean checkValue) {
		this.checkValue = checkValue;
	}

	public boolean isWorkingDay() {
		return isWorkingDay;
	}

	public void setWorkingDay(boolean isWorkingDay) {
		this.isWorkingDay = isWorkingDay;
	}

	public List<PerformanceHeaderVO> getSubTitleList() {

		if (null == this.subTitleList) {
			this.subTitleList = new ArrayList<>();
		}
		return subTitleList;
	}

	public void setSubTitleList(List<PerformanceHeaderVO> subTitleList) {
		this.subTitleList = subTitleList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		PerformanceHeaderVO other = (PerformanceHeaderVO) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
