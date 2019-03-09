/**
 * 
 */
package com.cognizant.outreach.microservices.perfdata.service;

import java.util.List;

import com.cognizant.outreach.microservices.perfdata.vo.metrics.ClasswiseMetricsVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.EncouragingMetricsVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.SearchPerformanceMetrics;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.TeamwiseMetricsVO;

public interface PerformanceMetricsService {
	
	
	/**
	 * Method to fetch encouraging performance metrics data.
	 * 
	 * @param searchPerformanceMetrics
	 * @return List<EncouragingMetricsVO>
	 */
	public List<EncouragingMetricsVO> getEncouragingPerformanceMetrics(SearchPerformanceMetrics searchPerformanceMetrics);

	/**
	 * @param searchPerformanceMetrics
	 * @return List<ClasswiseMetricsVO>
	 */
	public ClasswiseMetricsVO getClasswisePerformanceMetrics(SearchPerformanceMetrics searchPerformanceMetrics);

	/**
	 * @param searchPerformanceMetrics
	 * @return List<TeamwiseMetricsVO>
	 */
	public TeamwiseMetricsVO getTeamwisePerformanceMetrics(SearchPerformanceMetrics searchPerformanceMetrics);
	
	}

