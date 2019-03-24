/**
 * ${AdminController}
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
package com.cognizant.outreach.microservices.security.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.outreach.microservices.security.service.AdminService;
import com.cognizant.outreach.microservices.security.vo.UserRoleMappingVO;

@RestController
public class AdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(method = RequestMethod.GET, path = "/userrolemappings")
	public ResponseEntity<List<UserRoleMappingVO>> getListOfUserRoleMapping() {
		
		List<UserRoleMappingVO> userRoleMappings = null;
		try {
			userRoleMappings = adminService.listOfUserRolesMappings();
		} catch (Exception e) {
			LOGGER.debug("Exception occured while fetchinh user role mapping details");
			LOGGER.debug(e.getMessage());
			ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(userRoleMappings);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/saveuserrole")
	public ResponseEntity<String> saveUserRoleMapping(@RequestBody UserRoleMappingVO userRoleMappingVO) {

		String responseMessage = null;
		try {
			responseMessage = adminService.saveUserRolesMappings(userRoleMappingVO);

		} catch (Exception e) {
			LOGGER.debug("Exception occured while saving user role mapping details ==> {}", userRoleMappingVO.getUserId());
			LOGGER.debug(e.getMessage());
			ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
		LOGGER.debug("Save new user({}) and role({}) mapping status {}", userRoleMappingVO.getUserId(), userRoleMappingVO.getRoleName(), responseMessage);
		String responseString = "\""+responseMessage+"\"";
		return ResponseEntity.status(HttpStatus.OK).body(responseString);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/updateuserrole")
	public ResponseEntity<String> updateUserRoleMapping(@RequestBody UserRoleMappingVO userRoleMappingVO) {

		String responseMessage = null;
		try {
			responseMessage = adminService.updateUserRolesMappings(userRoleMappingVO);
		} catch (Exception e) {
			LOGGER.debug("Exception occured while updating user role mapping details ==> {}", userRoleMappingVO.getUserId());
			LOGGER.debug(e.getMessage());
			ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
		LOGGER.debug("Update existing user({}) and role({}) mapping status {}", userRoleMappingVO.getUserId(), userRoleMappingVO.getRoleName(), responseMessage);
		String responseString = "\""+responseMessage+"\"";
		return ResponseEntity.status(HttpStatus.OK).body(responseString);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/deleteuserrole")
	public ResponseEntity<String> deleteUserRoleMapping(@RequestBody UserRoleMappingVO userRoleMappingVO) {

		String responseMessage = null;
		try {
			responseMessage = adminService.deleteUserRolesMappings(userRoleMappingVO);
		} catch (Exception e) {
			LOGGER.debug("Exception occured while deleteing user role mapping details ==> {}", userRoleMappingVO.getUserId());
			LOGGER.debug(e.getMessage());
			ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
		LOGGER.debug("Delete existing user({}) and role({}) mapping status {}", userRoleMappingVO.getUserId(), userRoleMappingVO.getRoleName(), responseMessage);
		String responseString = "\""+responseMessage+"\"";
		return ResponseEntity.status(HttpStatus.OK).body(responseString);
	}	
	
}
