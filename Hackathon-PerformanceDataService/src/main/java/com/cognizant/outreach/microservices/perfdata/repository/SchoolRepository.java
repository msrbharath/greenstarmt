package com.cognizant.outreach.microservices.perfdata.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cognizant.outreach.entity.School;

@RestResource(exported = false)
public interface SchoolRepository extends CrudRepository<School, Long> {
	
}
