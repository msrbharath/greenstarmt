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

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.outreach.microservices.perfdata.vo.star.PerformanceStarSearchDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.star.PerformanceStarVO;

/**
 * Controller for performance star
 * 
 * @author 371793
 */
@RestController
@CrossOrigin
public class PerformanceStarController {
	
	/**
	 * To get the list of class for the given name
	 * 
	 * @return List of class if present else null
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/perfstar/generateStar")
	public ResponseEntity<PerformanceStarVO> generatePerformanceStar(@RequestBody PerformanceStarSearchDataVO performanceStarSearchDataVO) {
		/*List<ClassVO> classVOs = schoolService.getClassBySchoolId(schoolVO.getId()).get();
		logger.debug("Retrieved class count ==> {} for schoolId {}", null == classVOs ? null : classVOs.size(),
				schoolVO.getId());*/
		
		PerformanceStarVO performanceStarVO = new PerformanceStarVO();
		String[] perfStarMonthDataParamThree = {"#7CFC00", "#7CFC00", "#7CFC00", "#7CFC00", "#7CFC00", "#7beded", "#7beded", "#7CFC00", "#7CFC00", "#7CFC00", "#FFFF00", "#7CFC00", "#7beded", "#7beded", "#7CFC00", "#FF0000", "#7CFC00", "#7CFC00", "#7CFC00", "#7beded", "#7beded", "#7CFC00",
		        "#7CFC00", "#7CFC00", "#FFFF00", "#7CFC00", "#7beded", "#7beded", "#7CFC00", "#FFFFFF", "#FFFFFF"};
		String[] dummy = {};
		performanceStarVO.setParamOneMonthColorCodes(perfStarMonthDataParamThree);
		performanceStarVO.setParamTwoMonthColorCodes(perfStarMonthDataParamThree);
		performanceStarVO.setParamThreeMonthColorCodes(perfStarMonthDataParamThree);
		performanceStarVO.setParamOne("HomeWork");
		performanceStarVO.setParamTwo("Discipline");
		performanceStarVO.setParamThree("Attendance");

		return ResponseEntity.status(HttpStatus.OK).body(performanceStarVO);
	}
	
}
