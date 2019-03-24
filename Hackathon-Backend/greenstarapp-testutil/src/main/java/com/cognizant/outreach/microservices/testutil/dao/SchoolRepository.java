/**
 * 
 */
package com.cognizant.outreach.microservices.testutil.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cognizant.outreach.entity.School;

/**
 *
 */
@RestResource(exported = false)
public interface SchoolRepository extends CrudRepository<School, Long> {
	public School findBySchoolName(String schoolName);
}
