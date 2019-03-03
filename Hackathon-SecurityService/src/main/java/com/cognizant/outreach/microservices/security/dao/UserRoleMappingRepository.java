/**
 * 
 */
package com.cognizant.outreach.microservices.security.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cognizant.outreach.entity.UserRoleMapping;


@RestResource(exported = false)
public interface UserRoleMappingRepository extends CrudRepository<UserRoleMapping, Integer> {
	
	public Optional<UserRoleMapping> findByUserId(String userId);
}
