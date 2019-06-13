/**
 * ${AdminServiceImpl}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  09/Mar/2019            Panneer        	Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cognizant.outreach.entity.RoleDetail;
import com.cognizant.outreach.entity.School;
import com.cognizant.outreach.entity.UserRoleMapping;
import com.cognizant.outreach.microservices.security.dao.SchoolRepository;
import com.cognizant.outreach.microservices.security.dao.UserRoleMappingRepository;
import com.cognizant.outreach.microservices.security.vo.UserRoleMappingVO;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	private static final String SUCCESS = "SUCCESS";
	private static final String EXIST = "EXIST";

	@Autowired
	private UserRoleMappingRepository userRoleMappingRepository;
	
	@Autowired
	private SchoolRepository schoolRepository;

	@Override
	public List<UserRoleMappingVO> listOfUserRolesMappings() {

		List<UserRoleMappingVO> userRolesMappingsList = new ArrayList<>();

		Optional<List<Object[]>> userRoleObj = userRoleMappingRepository.listUserRolesMappings();
		if (userRoleObj.isPresent()) {
			for (Object[] userRoleArray : userRoleObj.get()) {
				String schoolIds = (String) userRoleArray[3];
				String schoolNmstr="";
				if(!StringUtils.isEmpty(schoolIds)) {
					String[] ids = schoolIds.split(",");
					List<Long> longIds = Arrays.stream(ids).map(Long::valueOf).collect(Collectors.toList());
					List<School> schoolNames = schoolRepository.findByIdIn(longIds);
					List<String> schoolNms= new ArrayList<>();
					for (School school : schoolNames) {
						schoolNms.add(school.getSchoolName());
					}
					schoolNmstr = org.apache.commons.lang.StringUtils.join(schoolNms, ",");
				}
				userRolesMappingsList.add(new UserRoleMappingVO((long) userRoleArray[0], (String) userRoleArray[1],
						(String) userRoleArray[2],schoolIds,schoolNmstr));
			}
		}
		LOGGER.info("Total user role mappings {}", userRolesMappingsList.size());		
		return userRolesMappingsList;
	}

	@Override
	public String saveUserRolesMappings(UserRoleMappingVO userRoleMappingVO) {

		Optional<UserRoleMapping> userRole = userRoleMappingRepository.findByUserId(userRoleMappingVO.getUserId());
		if (!userRole.isPresent()) {
			Date currentDate = new Date();
			Optional<RoleDetail> roleDetailOpt = userRoleMappingRepository.findRoleDetailsByRoleName(userRoleMappingVO.getRoleName());
			UserRoleMapping userRoleMapping = new UserRoleMapping();
			userRoleMapping.setUserId(userRoleMappingVO.getUserId());
			userRoleMapping.setRoleDetail(roleDetailOpt.get());
			userRoleMapping.setSchools(userRoleMappingVO.getSchoolIds());
			userRoleMapping.setCreatedUserId(userRoleMappingVO.getLoggedUserId());
			userRoleMapping.setCreatedDtm(currentDate);
			userRoleMapping.setLastUpdatedUserId(userRoleMappingVO.getLoggedUserId());
			userRoleMapping.setLastUpdatedDtm(currentDate);
			
			userRoleMappingRepository.save(userRoleMapping);
			LOGGER.info("User Role Mapping Saved Successfully");
			return SUCCESS;
		}
		return EXIST;
	}

	@Override
	public String updateUserRolesMappings(UserRoleMappingVO userRoleMappingVO) {

		Optional<UserRoleMapping> userRole = userRoleMappingRepository.findByUserId(userRoleMappingVO.getUserId());
		if (userRole.isPresent()) {
			if(userRoleMappingVO.getId() == userRole.get().getId()) {
				Date currentDate = new Date();
				Optional<RoleDetail> roleDetailOpt = userRoleMappingRepository.findRoleDetailsByRoleName(userRoleMappingVO.getRoleName());
				userRole.get().setUserId(userRoleMappingVO.getUserId());
				userRole.get().setRoleDetail(roleDetailOpt.get());
				
				userRole.get().setLastUpdatedUserId(userRoleMappingVO.getLoggedUserId());
				userRole.get().setLastUpdatedDtm(currentDate);
				
				userRoleMappingRepository.save(userRole.get());
				
				LOGGER.info("User Role Mapping Updated Successfully");
				return SUCCESS;
			} else {
				return EXIST;
			}
		} else { 
			Optional<UserRoleMapping> userRoleObjById = userRoleMappingRepository.findById(userRoleMappingVO.getId());
			if (userRoleObjById.isPresent()) {
				Date currentDate = new Date();
				Optional<RoleDetail> roleDetailOpt = userRoleMappingRepository.findRoleDetailsByRoleName(userRoleMappingVO.getRoleName());
				userRoleObjById.get().setUserId(userRoleMappingVO.getUserId());
				userRoleObjById.get().setRoleDetail(roleDetailOpt.get());
				
				userRoleObjById.get().setLastUpdatedUserId(userRoleMappingVO.getLoggedUserId());
				userRoleObjById.get().setLastUpdatedDtm(currentDate);
				userRoleMappingRepository.save(userRoleObjById.get());
				
				LOGGER.info("User Role Mapping Updated Successfully");
				return SUCCESS;
			}
			
			return SUCCESS;
		}
	}

	@Override
	public String deleteUserRolesMappings(UserRoleMappingVO userRoleMappingVO) {

		Optional<UserRoleMapping> userRole = userRoleMappingRepository.findByUserId(userRoleMappingVO.getUserId());
		if (userRole.isPresent()) {
			userRoleMappingRepository.delete(userRole.get());
		}
		
		LOGGER.info("User Role Mapping Deleted Successfully");
		return SUCCESS;
	}

	@Override
	public String getAssignedSchools(String userId) {
		Optional<UserRoleMapping> userRoleMapping = userRoleMappingRepository.findByUserId(userId);
		if(userRoleMapping.isPresent()) {
			return userRoleMapping.get().getSchools();
		}
		return null;
	}
}
