/**
 * 
 */
package com.cognizant.outreach.microservices.perfdata.service;

import java.util.List;

import com.cognizant.outreach.microservices.perfdata.vo.metrics.ClasswiseMetricsVO;
import com.cognizant.outreach.microservices.perfdata.vo.metrics.DashboardSingleVO;
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
	public List<EncouragingMetricsVO> getEncouragingPerformanceMetrics(
			SearchPerformanceMetrics searchPerformanceMetrics);

	/**
	 * Method to fetch Classwise performance metrics data.
	 * 
	 * @param searchPerformanceMetrics
	 * @return List<ClasswiseMetricsVO>
	 */
	public ClasswiseMetricsVO getClasswisePerformanceMetrics(SearchPerformanceMetrics searchPerformanceMetrics);

	/**
	 * Method to fetch Teamwise performance metrics data.
	 * 
	 * @param searchPerformanceMetrics
	 * @return List<TeamwiseMetricsVO>
	 */
	public TeamwiseMetricsVO getTeamwisePerformanceMetrics(SearchPerformanceMetrics searchPerformanceMetrics);

	/**
	 * Method to fetch Number of schools that entered performance metrics data per
	 * month.
	 * 
	 * @return List<DashboardSingleVO>
	 */
	public List<DashboardSingleVO> getSchoolByMonthMetrics();

	/**
	 * Method to fetch Total number of schools enrolled in Greenstar Application.
	 * 
	 * @return totalNoOfSchools
	 */
	public Long getTotalNoOfSchools();

	/**
	 * Method to fetch Top performing schools in Greenstar application.
	 * 
	 * @return List<DashboardSingleVO>
	 */
	public List<DashboardSingleVO> getTopPerformingSchools();

	/**
	 * Method to fetch Top performing volunteers in the Greenstar application.
	 * 
	 * @return List<DashboardSingleVO>
	 */
	public List<DashboardSingleVO> getTopPerformingVolunteers();

}
