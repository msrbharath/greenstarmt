/**
 * ${SchoolService}
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
package com.cognizant.outreach.microservices.school.service;

import java.util.List;
import java.util.Optional;

import com.cognizant.outreach.microservices.school.vo.ClassVO;
import com.cognizant.outreach.microservices.school.vo.SchoolVO;

public interface SchoolService {

	/**
	 * To get the list of school
	 * 
	 * @return list of school
	 */
	public Optional<List<SchoolVO>> getSchools();
	
	/**
	 * To get the list of class details
	 * 
	 * @return list of class
	 */
	public Optional<List<ClassVO>> getClassBySchoolId(long schoolId);
	
	/**
	 * To get the list of student and team detail of the class
	 * 
	 * @return list of class
	 */
	public Optional<ClassVO> getStudentAndTeamDetailsByClassId(long classId);

}
