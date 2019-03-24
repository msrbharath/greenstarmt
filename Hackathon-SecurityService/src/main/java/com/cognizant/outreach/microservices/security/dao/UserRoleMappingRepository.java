/**
 * 
 */
package com.cognizant.outreach.microservices.security.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cognizant.outreach.entity.RoleDetail;
import com.cognizant.outreach.entity.UserRoleMapping;

@RestResource(exported = false)
public interface UserRoleMappingRepository extends CrudRepository<UserRoleMapping, Integer> {

	public Optional<UserRoleMapping> findById(Long id);
	
	public Optional<UserRoleMapping> findByUserId(String userId);
	
	@Query("SELECT ur.id, ur.userId, ur.roleDetail.roleName FROM UserRoleMapping ur")
	public Optional<List<Object[]>> listUserRolesMappings();
	
	@Query("SELECT rd FROM RoleDetail rd WHERE rd.roleName=:ROLE_NAME")
	public Optional<RoleDetail> findRoleDetailsByRoleName(@Param("ROLE_NAME") String roleName);
}
