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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;




/**
 * Provides method to authorize and authenticate user along with API token validation 
 * 
 * @author 371793
 */
@RestController
public class SecurityController {

	protected Logger logger = LoggerFactory.getLogger(SecurityController.class);
	
	/*@Autowired
	SecurityService securityService;*/
	
	/**
	 * To validate the provided api token is valid or not
	 * TODO: This method can be revisited on SSO/Ldap integration if required or not
	 * @param user
	 * @return  HttpStatus.ACCEPTED if valid and HttpStatus.UNAUTHORIZED for not a valid token
	 */
	/*
	@RequestMapping(method=RequestMethod.POST,path="/validatetoken")
	public ResponseEntity<String> validateAPIToken(@RequestBody User user) {
		if(!securityService.isTokenValid(user.getApiToken())) {
			logger.debug("Token invalid for user {}", user.getUserId());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("API Token is not valid or expired");
		}
		logger.debug("Token validated for user {}", user.getUserId());
		return ResponseEntity.accepted().body("API Token is valid");
	}
	*/
	
	/**
	 * To authenticate user and provide authorization information like role and menus to access
	 * TODO: This method can be revisited on SSO/Ldap integration if required or not
	 * @param params
	 * @return HttpStatus.UNAUTHORIZED for not authorized user and valid user if authorized
	 */
	/*
	@RequestMapping(method=RequestMethod.POST,path="/login")
	public ResponseEntity<User> userLogin(@RequestBody User requestUser) {
		
		Optional<User> user = securityService.initializeUser(requestUser.getUserName(), requestUser.getPassword());

		if(!user.isPresent()) {
			logger.debug("User not authorized with the provided credentials ==> user {}", requestUser.getUserName());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		logger.debug("User authorized ==> user {}", requestUser.getUserName());
		return ResponseEntity.accepted().body(user.get());
	}
	*/
	
}
