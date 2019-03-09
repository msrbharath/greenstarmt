/**
 * 
 */
package com.cognizant.outreach.microservices.testutil.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cognizant.outreach.entity.MeasurableParam;

/**
 *
 */
@RestResource(exported = false)
public interface MeasurableParamRepository extends CrudRepository<MeasurableParam, Long> {
	

}
