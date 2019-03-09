package com.cognizant.outreach.microservices.testutil.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cognizant.outreach.entity.ClassDetail;
import com.cognizant.outreach.entity.School;
import com.cognizant.outreach.microservices.testutil.dao.ClassRepository;
import com.cognizant.outreach.microservices.testutil.dao.MeasurableParamRepository;

@Component
public class MeasurableParamHelper {
	
	@Autowired
	ClassRepository classRepository;
	
	@Autowired
	MeasurableParamRepository measurableParamRepository;
	
	public List<ClassDetail> createClasses(School school) {
		List<ClassDetail> classDetails = new ArrayList<ClassDetail>();
		ClassDetail classDetail = new ClassDetail();
		classDetail.setSchool(school);
		classDetail.setClassName("I");
		classDetail.setSection("A");
		CommonHelper.setAuditTrailInfo(classDetail);
		classRepository.save(classDetail);
		classDetails.add(classDetail);

		classDetail = new ClassDetail();
		classDetail.setSchool(school);
		classDetail.setClassName("I");
		classDetail.setSection("B");
		CommonHelper.setAuditTrailInfo(classDetail);
		classRepository.save(classDetail);
		classDetails.add(classDetail);

		 return classDetails;
	}
	
	public void deleteClasses(List<ClassDetail> classDetails) {
		classRepository.deleteAll(classDetails);
	}
}
