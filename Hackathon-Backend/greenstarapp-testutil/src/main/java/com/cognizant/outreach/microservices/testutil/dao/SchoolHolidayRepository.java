/**
 * 
 */
package com.cognizant.outreach.microservices.testutil.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cognizant.outreach.entity.SchoolHoliday;

/**
 *
 */
@RestResource(exported = false)
public interface SchoolHolidayRepository extends CrudRepository<SchoolHoliday, Long> {
}
