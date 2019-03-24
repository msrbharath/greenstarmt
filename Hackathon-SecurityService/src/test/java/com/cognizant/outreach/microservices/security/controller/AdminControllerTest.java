/**
 * ${AdminControllerTest}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  18/Feb/2019            371793        Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.security.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;

import com.cognizant.outreach.microservices.security.service.AdminService;
import com.cognizant.outreach.microservices.security.vo.UserRoleMappingVO;

/**
 * Test class for security service
 * 
 * @author 371793
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {

	@Mock
	private AdminService adminService;

	@InjectMocks
	private AdminController adminController;


	@Test
	public void TestGetRoles() {
		when(adminService.listOfUserRolesMappings()).thenReturn(getRoles());
		assertEquals(adminController.getListOfUserRoleMapping().getStatusCode(),HttpStatus.OK);
	}

	
	@Test
	public void TestDeleteUserRoleMapping() {
		when(adminService.deleteUserRolesMappings(Mockito.any(UserRoleMappingVO.class))).thenReturn("Success");
		assertEquals(adminController.deleteUserRoleMapping(new UserRoleMappingVO()).getStatusCode(),HttpStatus.OK);
	}

	@Test
	public void TestUpdateUserRoleMapping() {
		when(adminService.updateUserRolesMappings(Mockito.any(UserRoleMappingVO.class))).thenReturn("Success");
		assertEquals(adminController.updateUserRoleMapping(new UserRoleMappingVO()).getStatusCode(),HttpStatus.OK);
	}
	
	private List<UserRoleMappingVO> getRoles() {
		List<UserRoleMappingVO> roles = new ArrayList<>();
		return roles;
	}
	
	

}
