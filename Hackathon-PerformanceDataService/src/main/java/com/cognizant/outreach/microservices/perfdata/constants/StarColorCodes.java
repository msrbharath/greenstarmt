/**
 * ${StarColorCodes}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  04/Mar/2019            371793        	Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.perfdata.constants;

/**
 * Holds the color codes used in performance star 
 */
public enum StarColorCodes {
	 COMPLAINT("#7CFC00"), BELOW_75("#FF0000"), EQUAL_ABOVE_75("#FFFF00"), HOLIDAY("#7beded"),
	   NO_DATA("#FFFFFF");
	 private String colorCode;
	 
	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	StarColorCodes(String colorCode) {
		this.colorCode = colorCode;
  }
}
