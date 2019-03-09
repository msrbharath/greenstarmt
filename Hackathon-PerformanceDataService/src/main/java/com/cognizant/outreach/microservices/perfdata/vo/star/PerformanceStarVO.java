/**
 * ${PerformanceStarVO}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  02/Mar/2019            371793        Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.perfdata.vo.star;

/**
 * To hold the value used to generate performance star data
 * 
 * @author 371793
 *
 */
public class PerformanceStarVO {
	
	public String paramOne;
	public String paramTwo;
	public String paramThree;
	public String[] paramOneMonthColorCodes;
	public String[] paramTwoMonthColorCodes;
	public String[] paramThreeMonthColorCodes;

	public String getParamOne() {
		return paramOne;
	}

	public void setParamOne(String paramOne) {
		this.paramOne = paramOne;
	}

	public String getParamTwo() {
		return paramTwo;
	}

	public void setParamTwo(String paramTwo) {
		this.paramTwo = paramTwo;
	}

	public String getParamThree() {
		return paramThree;
	}

	public void setParamThree(String paramThree) {
		this.paramThree = paramThree;
	}

	public String[] getParamOneMonthColorCodes() {
		return paramOneMonthColorCodes;
	}

	public void setParamOneMonthColorCodes(String[] paramOneMonthColorCodes) {
		this.paramOneMonthColorCodes = paramOneMonthColorCodes;
	}

	public String[] getParamTwoMonthColorCodes() {
		return paramTwoMonthColorCodes;
	}

	public void setParamTwoMonthColorCodes(String[] paramTwoMonthColorCodes) {
		this.paramTwoMonthColorCodes = paramTwoMonthColorCodes;
	}

	public String[] getParamThreeMonthColorCodes() {
		return paramThreeMonthColorCodes;
	}

	public void setParamThreeMonthColorCodes(String[] paramThreeMonthColorCodes) {
		this.paramThreeMonthColorCodes = paramThreeMonthColorCodes;
	}

}
