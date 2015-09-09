package com.xuexibao.teacher.dao;

import java.util.List;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Organization;

/**
 * 
 * @author oldlu
 * 
 */
@MybatisMapper
public interface OrganizationDao {
	Organization loadOrganization(long organizationId);

	List<Organization> getOrgListByCityId(int cityId);

	boolean isOrgTeacher(String teacherId);

	Organization loadOrgByCode(String code);
}
