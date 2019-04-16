package com.cognizant.outreach.microservices.security.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cognizant.outreach.entity.School;

@RestResource(exported = false)
public interface SchoolRepository extends CrudRepository<School, Long> {
	
	public List<School> findByIdIn(List<Long> ids);
}
