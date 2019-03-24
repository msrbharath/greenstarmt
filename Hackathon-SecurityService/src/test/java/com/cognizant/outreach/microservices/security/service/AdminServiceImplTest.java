package com.cognizant.outreach.microservices.security.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cognizant.outreach.entity.RoleDetail;
import com.cognizant.outreach.entity.UserRoleMapping;
import com.cognizant.outreach.microservices.security.dao.UserRoleMappingRepository;
import com.cognizant.outreach.microservices.security.vo.UserRoleMappingVO;

public class AdminServiceImplTest {

	@InjectMocks
	private AdminServiceImpl adminServiceImpl = new AdminServiceImpl();

	@Mock
	private UserRoleMappingRepository userRoleMappingRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void TestGetUserRoleMappings() {

		when(userRoleMappingRepository.listUserRolesMappings()).thenReturn(getUserRoleMappings());
		List<UserRoleMappingVO> userRoleList = adminServiceImpl.listOfUserRolesMappings();
		assertTrue(userRoleList.size() >0 );
	}

	@Test
	public void TestSaveUserRoleMapping_Exist() {

		when(userRoleMappingRepository.findByUserId(Mockito.any(String.class))).thenReturn(getUserRoleMappingNotExist());
		when(userRoleMappingRepository.findRoleDetailsByRoleName(Mockito.any(String.class))).thenReturn(getRoleDetailOptional());
		when(userRoleMappingRepository.save(Mockito.any(UserRoleMapping.class))).thenReturn(getNewUserRoleMapping());
		
		String resp = adminServiceImpl.saveUserRolesMappings(createUserRoleMappingVO());

		assertTrue(resp == "EXIST");
	}

	@Test
	public void TestSaveUserRoleMapping_NotExist() {

		when(userRoleMappingRepository.findByUserId(Mockito.any(String.class))).thenReturn(getUserRoleMapping_empty());
		when(userRoleMappingRepository.findRoleDetailsByRoleName(Mockito.any(String.class))).thenReturn(getRoleDetailOptional());
		when(userRoleMappingRepository.save(Mockito.any(UserRoleMapping.class))).thenReturn(getNewUserRoleMapping());
		
		String resp = adminServiceImpl.saveUserRolesMappings(createUserRoleMappingVO());

		assertTrue(resp == "SUCCESS");
	}
	
	@Test
	public void TestUpdateUserRoleMapping() {

		when(userRoleMappingRepository.findById(Mockito.any(Long.class))).thenReturn(getUserRoleMapping());
		when(userRoleMappingRepository.findByUserId(Mockito.any(String.class))).thenReturn(getUserRoleMapping());
		when(userRoleMappingRepository.findRoleDetailsByRoleName(Mockito.any(String.class))).thenReturn(getRoleDetailOptional());
		when(userRoleMappingRepository.save(Mockito.any(UserRoleMapping.class))).thenReturn(getNewUserRoleMapping());
		
		String resp = adminServiceImpl.updateUserRolesMappings(updateUserRoleMappingVO());

		assertTrue(resp == "SUCCESS");
	}

	
	@Test
	public void TestUpdateUserRoleMapping_Empty() {

		when(userRoleMappingRepository.findById(Mockito.any(Long.class))).thenReturn(getUserRoleMapping());
		when(userRoleMappingRepository.findByUserId(Mockito.any(String.class))).thenReturn(getUserRoleMapping_empty());
		when(userRoleMappingRepository.findRoleDetailsByRoleName(Mockito.any(String.class))).thenReturn(getRoleDetailOptional());
		when(userRoleMappingRepository.save(Mockito.any(UserRoleMapping.class))).thenReturn(getNewUserRoleMapping());
		
		String resp = adminServiceImpl.updateUserRolesMappings(updateUserRoleMappingVO());

		assertTrue(resp == "SUCCESS");
	}

	
	@Test
	public void TestDeleteUserRoleMapping() {
		
		when(userRoleMappingRepository.findByUserId(Mockito.any(String.class))).thenReturn(getUserRoleMapping());
		
		String resp = adminServiceImpl.deleteUserRolesMappings(deleteUserRoleMappingVO());

		assertTrue(resp == "SUCCESS");
	}

	public Optional<List<Object[]>> getUserRoleMappings() {

		List<Object[]> userRoleMappingList = new ArrayList<>();

		Object[] data1 = new Object[] { 1l, "panneer", "Admin" };
		Object[] data2 = new Object[] { 2l, "magesh", "Admin" };
		Object[] data3 = new Object[] { 3l, "bharath", "POM" };

		userRoleMappingList.add(data1);
		userRoleMappingList.add(data2);
		userRoleMappingList.add(data3);

		return Optional.of(userRoleMappingList);
	}

	public Optional<UserRoleMapping> getUserRoleMapping() {

		UserRoleMapping userRoleMapping = new UserRoleMapping();
		userRoleMapping.setId(1l);
		userRoleMapping.setUserId("panneer");
		userRoleMapping.setRoleDetail(getRoleDetail());
		userRoleMapping.setCreatedUserId("create-user-id");
		userRoleMapping.setCreatedDtm(new Date());
		userRoleMapping.setLastUpdatedUserId("last-updated-user-id");
		userRoleMapping.setLastUpdatedDtm(new Date());

		return Optional.of(userRoleMapping);
	}
	
	public Optional<UserRoleMapping> getUserRoleMapping_empty() {

		return Optional.empty();
	}
	
	public Optional<UserRoleMapping> getUserRoleMappingNotExist() {
		return Optional.of(new UserRoleMapping());
	}
	
	public UserRoleMapping getNewUserRoleMapping() {

		UserRoleMapping userRoleMapping = new UserRoleMapping();
		userRoleMapping.setUserId("panneer");
		userRoleMapping.setRoleDetail(getRoleDetail());
		userRoleMapping.setCreatedUserId("create-user-id");
		userRoleMapping.setCreatedDtm(new Date());
		userRoleMapping.setLastUpdatedUserId("last-updated-user-id");
		userRoleMapping.setLastUpdatedDtm(new Date());
		
		return userRoleMapping;
	}

	public RoleDetail getRoleDetail() {

		RoleDetail roleDetail = new RoleDetail();
		roleDetail.setId(1l);
		roleDetail.setDescription("Admin");
		roleDetail.setRoleName("Admin");

		return roleDetail;
	}

	public Optional<RoleDetail> getRoleDetailOptional() {

		RoleDetail roleDetail = new RoleDetail();
		roleDetail.setId(1l);
		roleDetail.setDescription("Admin");
		roleDetail.setRoleName("Admin");

		return Optional.of(roleDetail);
	}

	private UserRoleMappingVO createUserRoleMappingVO() {

		UserRoleMappingVO userRoleMappingVO = new UserRoleMappingVO();
		userRoleMappingVO.setUserId("panneer");
		userRoleMappingVO.setRoleName("Admin");

		userRoleMappingVO.setLoggedUserId("selvam");

		return userRoleMappingVO;
	}
	
	private UserRoleMappingVO updateUserRoleMappingVO() {

		UserRoleMappingVO userRoleMappingVO = new UserRoleMappingVO();
		userRoleMappingVO.setId(1l);
		userRoleMappingVO.setUserId("panneer");
		userRoleMappingVO.setRoleName("Admin");

		userRoleMappingVO.setLoggedUserId("selvam");

		return userRoleMappingVO;
	}
	
	private UserRoleMappingVO deleteUserRoleMappingVO() {

		UserRoleMappingVO userRoleMappingVO = new UserRoleMappingVO();
		userRoleMappingVO.setId(2l);
		userRoleMappingVO.setUserId("bharath");
		userRoleMappingVO.setRoleName("Admin");

		userRoleMappingVO.setLoggedUserId("selvam");

		return userRoleMappingVO;
	}

}
