/**
 * ${PerformanceStarSearchDataVO}
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
 * To hold the value of user entered search data
 * 
 * @author 371793
 *
 */
public class PerformanceStarSearchDataVO {

	public String calcType;
	public long schoolId;
	public long studentId;
	public long classId;
	public String teamName;
	public int month;

	public String getCalcType() {
		return calcType;
	}

	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}

	public long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public long getClassId() {
		return classId;
	}

	public void setClassId(long classId) {
		this.classId = classId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

}
