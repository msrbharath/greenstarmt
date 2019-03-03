/**
 * 
 */
package com.cognizant.outreach.microservices.security.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.outreach.entity.ApiToken;
import com.cognizant.outreach.entity.RoleMenuMapping;
import com.cognizant.outreach.entity.UserRoleMapping;
import com.cognizant.outreach.microservices.security.ApplicationProperties;
import com.cognizant.outreach.microservices.security.dao.APITokenRepository;
import com.cognizant.outreach.microservices.security.dao.UserRoleMappingRepository;
import com.cognizant.outreach.microservices.security.model.User;

@Service
public class SecurityServiceImpl implements SecurityService{

  @Autowired
  APITokenRepository apiTokenRepository;
  
  @Autowired
  UserRoleMappingRepository userRoleMappingRepository;
  
  @Autowired
  ApplicationProperties applicationProperties;
 

	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.auth.service.AuthService#isTokenValid(java.lang.String)
	 */
	@Override
	public Boolean isTokenValid(String token) {
		Optional<ApiToken> apiToken = apiTokenRepository.findById(token);
		if(!apiToken.isPresent() || new Date().getTime() > apiToken.get().getEndTime().getTime()) {
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see com.cognizant.outreach.microservices.auth.service.AuthService#initializeUser(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<User> initializeUser(String userId, String password) {
		userId = userId.toLowerCase();
		if(!authenticateUser(userId,password)) {
			return Optional.ofNullable(null);
		}
		
		User user = new User();
		Optional<UserRoleMapping> userRoleMapping = userRoleMappingRepository.findByUserId(userId);
		
		
		if(userRoleMapping.isPresent()) {
			user.setRoleName(userRoleMapping.get().getRoleDetail().getRoleName());
			// Set authorized menus
			List<RoleMenuMapping> roleMenuMappings =    userRoleMapping.get().getRoleDetail().getRoleMenuMappings();
			if(!CollectionUtils.isEmpty(roleMenuMappings)) {
				List<String> menus = new ArrayList<>();
 				for (RoleMenuMapping roleMenuMapping : roleMenuMappings) {
					menus.add(roleMenuMapping.getMainUiMenu().getMenuName());
				}
 				user.setUiMenuList(menus);
			}
		}
		
		String apiToken = generateAPIToken(userId);
		user.setApiToken(apiToken);
		
		// Save token to use later
		saveToken(apiToken,userId);

		return Optional.of(user);
	}

	private String generateAPIToken(String userId) {
		Long token = System.currentTimeMillis() + ("my secret api token").hashCode() + userId.hashCode();
		return token.toString();
	}
	
	//TODO: yet to implement ldap or sso logic
	private boolean authenticateUser(String userId, String password) {
		Map<String, String> users = applicationProperties.getUsers();
		String security = users.get(userId);
		if(!StringUtils.isEmpty(security) && security.equals(password)) {
			return true;
		}
		return false;
	}
	
	private void saveToken(String token,String userId) {
		ApiToken apiToken = new ApiToken();
		apiToken.setUserid(userId);
		apiToken.setToken(token);
		Date date = new Date();
		apiToken.setStartTime(date);
		apiToken.setEndTime(DateUtils.addMinutes(date, applicationProperties.getApitimeout()));
		apiTokenRepository.save(apiToken);
	}
	
}
