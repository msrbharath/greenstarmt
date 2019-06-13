/**
 * ${AdminService}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  09/Mar/2019            Panneer        	Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.security.service;

import java.util.List;

import com.cognizant.outreach.microservices.security.vo.UserRoleMappingVO;

public interface AdminService {

	public List<UserRoleMappingVO> listOfUserRolesMappings();

	public String saveUserRolesMappings(UserRoleMappingVO userRoleMappingVO);

	public String updateUserRolesMappings(UserRoleMappingVO userRoleMappingVO);

	public String deleteUserRolesMappings(UserRoleMappingVO userRoleMappingVO);
	
	public String getAssignedSchools(String userId);
}
