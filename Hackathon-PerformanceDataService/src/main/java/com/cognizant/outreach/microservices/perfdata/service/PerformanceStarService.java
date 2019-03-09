/**
 * ${PerformanceStarService}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  04/Mar/2019            371793        Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.perfdata.service;

import java.util.Optional;

import com.cognizant.outreach.microservices.perfdata.vo.star.PerformanceStarSearchDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.star.PerformanceStarVO;


public interface PerformanceStarService {

	/**
	 * To the data to populate the star
	 * 
	 * @return PerformanceStarVO
	 */
	public Optional<PerformanceStarVO> getStarData(PerformanceStarSearchDataVO performanceStarSearchDataVO);
		
}
