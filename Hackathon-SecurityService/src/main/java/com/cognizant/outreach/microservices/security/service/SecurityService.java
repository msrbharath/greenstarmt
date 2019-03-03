/**
 * 
 */
package com.cognizant.outreach.microservices.security.service;

import java.util.Optional;

import com.cognizant.outreach.microservices.security.model.User;


public interface SecurityService {
  

  /**
   * To check passed api token is valid
   * 
 * @param token
 * @return
 */
public Boolean isTokenValid(String token);
  
  /**
   * This method is to authenticate and authorize user
   * 
 * @param userId
 * @param password
 * @return User user with authorization details and api token
 */
public Optional<User> initializeUser(String userId,String password);
  
}

