package com.cognizant.outreach.microservices.perfdata.vo;

public class TemplateError {

	private Integer cellNo;
	private String value;
	private String message;

	public TemplateError() {
	}

	public TemplateError(Integer cellNo, String value, String message) {
		this.cellNo = cellNo;
		this.value = value;
		this.message = message;
	}

	public Integer getCellNo() {
		return cellNo;
	}

	public void setCellNo(Integer cellNo) {
		this.cellNo = cellNo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
