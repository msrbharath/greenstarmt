/**
 * 
 */
package com.cognizant.outreach.microservices.testutil.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cognizant.outreach.entity.ClassDetail;

/**
 *
 */
@RestResource(exported = false)
public interface ClassRepository extends CrudRepository<ClassDetail, Long> {
}
