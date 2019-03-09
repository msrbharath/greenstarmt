/**
 * 
 */
package com.cognizant.outreach.microservices.testutil.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cognizant.outreach.entity.StudentSchoolAssoc;

/**
 *
 */
@RestResource(exported = false)
public interface StudentSchoolAssocRepository extends CrudRepository<StudentSchoolAssoc, Long> {
}
