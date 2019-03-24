/**
 * ${SecurityController}
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

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.outreach.microservices.security.model.User;
import com.cognizant.outreach.microservices.security.service.SecurityService;


/**
 * Provides method to authorize and authenticate user along with API token validation 
 * 
 * @author 371793
 */
@RestController
public class SecurityController {

	protected Logger logger = LoggerFactory.getLogger(SecurityController.class);
	
	@Autowired
	SecurityService securityService;
	
	/**
	 * To validate the provided api token is valid or not
	 * 
	 * @param user
	 * @return  HttpStatus.ACCEPTED if valid and HttpStatus.UNAUTHORIZED for not a valid token
	 */
	@RequestMapping(method=RequestMethod.POST,path="/validatetoken")
	public ResponseEntity<String> validateAPIToken(@RequestBody User user) {
		if(!securityService.isTokenValid(user.getApiToken())) {
			logger.debug("Token invalid for user {}", user.getUserId());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("API Token is not valid or expired");
		}
		logger.debug("Token validated for user {}", user.getUserId());
		return ResponseEntity.accepted().body("API Token is valid");
	}
	
	/**
	 * To authenticate user and provide authorization information like role and menus to access
	 * 
	 * @param params
	 * @return HttpStatus.UNAUTHORIZED for not authorized user and valid user if authorized
	 */
	@RequestMapping(method=RequestMethod.POST,path="/login")
	public ResponseEntity<User> userLogin(@RequestParam("userId") String userId, @RequestParam("password") String password) {
		
		Optional<User> user = securityService.initializeUser(userId, password);

		if(!user.isPresent()) {
			logger.debug("User not authorized with the provided credentials ==> user {}", userId);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		logger.debug("User authorized ==> user {}", userId);
		return ResponseEntity.accepted().body(user.get());
	}
	
}
