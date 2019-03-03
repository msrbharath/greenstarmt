/**
 * ${APITokenRepository}
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
package com.cognizant.outreach.microservices.security.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cognizant.outreach.entity.ApiToken;

/**
 * Database repository for APItokens table
 * 
 * @author 371793
 */
@RestResource(exported = false)
public interface APITokenRepository extends CrudRepository<ApiToken, String> {

	/**
	 * To get the api token for the given token
	 * 
	 * @param token
	 * @return apitoken if valid else null
	 */
	public Optional<ApiToken> findByToken(String token);
}
