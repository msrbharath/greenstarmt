/**
 * ${SchoolServiceImpl}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  02/Mar/2019            371793        Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.school.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.outreach.entity.ClassDetail;
import com.cognizant.outreach.entity.StudentSchoolAssoc;
import com.cognizant.outreach.microservices.school.dao.ClassRepository;
import com.cognizant.outreach.microservices.school.dao.SchoolRepository;
import com.cognizant.outreach.microservices.school.dao.StudentSchoolAssocRepository;
import com.cognizant.outreach.microservices.school.vo.ClassVO;
import com.cognizant.outreach.microservices.school.vo.SchoolVO;
import com.cognizant.outreach.microservices.school.vo.StudentVO;

/**
 * Service to do crud operation on school details
 * 
 * @author 371793
 */
@Service
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	SchoolRepository schoolRespository;

	@Autowired
	ClassRepository classRepository;
	
	@Autowired
	StudentSchoolAssocRepository studentSchoolAssocRepository;

	@Override
	public Optional<List<SchoolVO>> getSchools() {
		Optional<List<Object[]>> schools = schoolRespository.getSchools();
		List<SchoolVO> schoolList = null;
		if (schools.isPresent()) {
			schoolList = new ArrayList<SchoolVO>();
			for (Object[] row : schools.get()) {
				SchoolVO schoolDetail = new SchoolVO();
				schoolDetail.setId((long) row[0]);
				schoolDetail.setSchoolName((String) row[1]);
				schoolList.add(schoolDetail);
			}
		}
		return Optional.of(schoolList);
	}

	@Override
	public Optional<List<ClassVO>> getClassBySchoolId(long schoolId) {
		Optional<List<ClassDetail>> classDetails = classRepository.findClassesBySchoolId(schoolId);
		List<ClassVO> classVOList = null;
		if (classDetails.isPresent()) {
			classVOList = new ArrayList<ClassVO>();
			for (ClassDetail classDetail : classDetails.get()) {
				ClassVO classVO = new ClassVO();
				classVO.setId(classDetail.getId());
				classVO.setSectionName(classDetail.getSection());
				classVO.setClassName(classDetail.getClassName());
				classVO.setClassAndSectionName(classDetail.getClassName() + "-" + classDetail.getSection());
				classVOList.add(classVO);
			}
		}
		return Optional.of(classVOList);
	}

	@Override
	public Optional<ClassVO> getStudentAndTeamDetailsByClassId(long classId) {
		Optional<List<StudentSchoolAssoc>> studentSchoolAssociations = studentSchoolAssocRepository.findClassDetailByClassId(classId);
		ClassVO classVO = null;
		
		if (studentSchoolAssociations.isPresent()) {
			classVO = new ClassVO();
			List<String> teamList = new ArrayList<String>();
			List<StudentVO> studentVOs = new ArrayList<StudentVO>();
			for (StudentSchoolAssoc schoolAssoc : studentSchoolAssociations.get()) {
				//Don't add the duplicate values for team name list
				if(!teamList.contains(schoolAssoc.getTeamName())) {
					teamList.add(schoolAssoc.getTeamName());
				}
				StudentVO studentVO = new StudentVO();
				studentVO.setId(schoolAssoc.getStudent().getId());
				studentVO.setStudentName(schoolAssoc.getStudent().getStudentName());
				studentVOs.add(studentVO);
			}
			classVO.setTeamList(teamList);
			classVO.setStudentList(studentVOs);
		}
		return Optional.of(classVO);
	}
}
