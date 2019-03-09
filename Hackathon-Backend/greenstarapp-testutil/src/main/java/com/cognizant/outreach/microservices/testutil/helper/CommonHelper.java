package com.cognizant.outreach.microservices.testutil.helper;

import java.util.Date;

import com.cognizant.outreach.entity.BaseEntity;

public class CommonHelper {
	
	public static void setAuditTrailInfo(BaseEntity dbEntity){
		Date date = new Date();
		dbEntity.setCreatedDtm(date);
		dbEntity.setLastUpdatedDtm(date);
		dbEntity.setCreatedUserId("Tester");
		dbEntity.setLastUpdatedUserId("Tester");
	}

}
