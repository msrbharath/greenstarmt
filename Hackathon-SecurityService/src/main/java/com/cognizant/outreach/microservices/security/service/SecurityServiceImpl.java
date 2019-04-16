/**
 * 
 *//*
package com.cognizant.outreach.microservices.security.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cognizant.outreach.entity.ApiToken;
import com.cognizant.outreach.entity.RoleMenuMapping;
import com.cognizant.outreach.entity.UserRoleMapping;
import com.cognizant.outreach.microservices.security.ApplicationProperties;
import com.cognizant.outreach.microservices.security.dao.APITokenRepository;
import com.cognizant.outreach.microservices.security.dao.UserRoleMappingRepository;
import com.cognizant.outreach.microservices.security.model.User;
import com.cognizant.outreach.util.DateUtil;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
// TODO: Entire service implementation need to re write once the Authentication method(SSO/LDAP) is confirmed
//The current application did't ** use ** any of these implementation authentication and authorization handled in JwtUsernamePasswordAuthenticationFilter
// and JwtTokenAuthenticationFilter(gateway)
public class SecurityServiceImpl implements SecurityService {

	private static final long SESSION_EXPIRE_MS = 1200000;
	private static final String TOKEN_ISSUER = "system";
	private static final String TOKEN_SUBJECT = "greenstar";
	private static final String SECURITY_KEY = "Cognizant Technology Soulation-HackFSE-Greenstar-web-Application";

	@Autowired
	private APITokenRepository apiTokenRepository;

	@Autowired
	private UserRoleMappingRepository userRoleMappingRepository;

	@Autowired
	private ApplicationProperties applicationProperties;

	
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cognizant.outreach.microservices.auth.service.AuthService#isTokenValid(
	 * java.lang.String)
	 
	@Override
	public Boolean isTokenValid(String token) {
		
		Boolean isTokenValid = false;
		Optional<ApiToken> apiToken = apiTokenRepository.findById(token);
		
		if(apiToken.isPresent()) {
			long defaultApiTimeout = (long) applicationProperties.getApitimeout();
			long currentDifference = DateUtil.getMinutesDifferenceBetweenTwoDates(apiToken.get().getLast_updated_dt(), new Date());
			if(defaultApiTimeout > currentDifference) {
				isTokenValid = true;
				// Update last updated date and time for token.
				updateToken(apiToken.get());
			}			
		}
		
		return isTokenValid;
	}

	
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cognizant.outreach.microservices.auth.service.AuthService#initializeUser(
	 * java.lang.String, java.lang.String)
	 
	@Override
	public Optional<User> initializeUser(String userId, String password) {
		userId = userId.toLowerCase();
		if (!authenticateUser(userId, password)) {
			return Optional.ofNullable(null);
		}

		User user = new User();
		Optional<UserRoleMapping> userRoleMapping = userRoleMappingRepository.findByUserId(userId);

		if (userRoleMapping.isPresent()) {
			user.setRoleName(userRoleMapping.get().getRoleDetail().getRoleName());
			
			// Set authorized menus
			List<RoleMenuMapping> roleMenuMappings = userRoleMapping.get().getRoleDetail().getRoleMenuMapping();
			if (!CollectionUtils.isEmpty(roleMenuMappings)) {
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
		saveToken(apiToken, userId);

		return Optional.of(user);
	}

	public String generateAPIToken(String userId) {
		// Long token = System.currentTimeMillis() + ("my secret api token").hashCode()
		// + userId.hashCode();
		// return token.toString();

		// JWT token generator
		String tokenValue = createJWTToken("1");
		return tokenValue;
	}

	private String createJWTToken(String userId) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECURITY_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder()
					.setId(userId)
					.setIssuedAt(now)
					.setSubject(TOKEN_SUBJECT)
					.setIssuer(TOKEN_ISSUER)
					.signWith(signatureAlgorithm, signingKey);

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}
	
	
	private void parseJWT(String jwt) {

		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECURITY_KEY))
				.parseClaimsJws(jwt).getBody();
		
		System.out.println("ID: " + claims.getId());
	    System.out.println("Subject: " + claims.getSubject());
	    System.out.println("Issuer: " + claims.getIssuer());	    
	}
	

	// TODO: yet to implement ldap or sso logic
	private boolean authenticateUser(String userId, String password) {
		Map<String, String> users = applicationProperties.getUsers();
		String security = users.get(userId);
		if (!StringUtils.isEmpty(security) && security.equals(password)) {
			return true;
		}
		return false;
	}

	private void saveToken(String token, String userId) {
		
		ApiToken apiToken = new ApiToken();
		apiToken.setUserid(userId);
		apiToken.setToken(token);
		Date date = new Date();
		apiToken.setStartTime(date);
		// apiToken.setEndTime(DateUtils.addMinutes(date,
		// applicationProperties.getApitimeout()));
		apiToken.setEndTime(date);
		apiToken.setLast_updated_dt(date);
		apiTokenRepository.save(apiToken);
	}
	
	private void updateToken(ApiToken apiToken) {
		
		apiToken.setLast_updated_dt(new Date());
		apiTokenRepository.save(apiToken);
	}

}
*/