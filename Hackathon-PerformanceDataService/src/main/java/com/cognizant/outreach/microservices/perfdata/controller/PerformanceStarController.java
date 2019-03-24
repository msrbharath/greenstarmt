/**
 * ${PerformanceStarController}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  03/Mar/2019            371793        Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.perfdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.outreach.microservices.perfdata.service.PerformanceStarService;
import com.cognizant.outreach.microservices.perfdata.vo.star.PerformanceStarSearchDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.star.PerformanceStarVO;

/**
 * Controller for performance star
 * 
 * @author 371793
 */
@RestController
public class PerformanceStarController {
	
	@Autowired
	private PerformanceStarService performanceStarService;
	
	/**
	 * To get the list of class for the given name
	 * 
	 * @return List of class if present else null
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/perfstar/generateStar")
	public ResponseEntity<PerformanceStarVO> generatePerformanceStar(@RequestBody PerformanceStarSearchDataVO performanceStarSearchDataVO) {
		PerformanceStarVO performanceStarVO = performanceStarService.getStarData(performanceStarSearchDataVO).get();

		return ResponseEntity.status(HttpStatus.OK).body(performanceStarVO);
	}
	
}
