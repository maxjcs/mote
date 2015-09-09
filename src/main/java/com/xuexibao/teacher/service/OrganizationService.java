package com.xuexibao.teacher.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.core.GenericRedisTemplate;
import com.xuexibao.teacher.dao.OrganizationDao;
import com.xuexibao.teacher.model.Organization;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class OrganizationService {
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private GenericRedisTemplate<Organization> genericRedisTemplate;
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	public Organization loadOrganization(long orgId) {
		if (orgId <= 0) {
			return null;
		}
		Organization org = getOrgRedis(orgId);
		if (org == null) {
			org = organizationDao.loadOrganization(orgId);

			setOrg2Redis(org);
		}
		return org;
	}

	public List<Organization> getOrgListByCityId(int cityId) {
		return organizationDao.getOrgListByCityId(cityId);
	}

	private void setOrg2Redis(Organization org) {
		if (org != null) {
			genericRedisTemplate.set(RedisContstant.TEACHER_ORG_CACHE_KEY + org.getId(), org);
		}
	}

	private Organization getOrgRedis(long orgId) {
		return genericRedisTemplate.get(RedisContstant.TEACHER_ORG_CACHE_KEY + orgId, Organization.class);
	}

	public boolean isOrgTeacher(String teacherId) {
		String key = RedisContstant.TEACHER_ORG_TEACHER_CACHE_KEY + teacherId;
		String value = stringRedisTemplate.opsForValue().get(key);

		// if (value != null) {
		// return StringUtils.equals(value, "true");
		// }

		boolean isOrgTeacher = organizationDao.isOrgTeacher(teacherId);

		stringRedisTemplate.opsForValue().set(key, String.valueOf(isOrgTeacher));

		return isOrgTeacher;
	}

	public String getTeacherIdByOrgId(long orgId) {
		if (orgId <= 0) {
			return "";
		}
		Organization org = loadOrganization(orgId);
		if (org != null) {
			return org.getTeacherId();
		}
		return "";
	}

	/**
	 * @param code
	 * @return
	 */
	public Organization loadOrgByCode(String code) {
		return organizationDao.loadOrgByCode(code);
	}

	public Boolean isPlanATeacher(String phoneNumber) {
		Organization org = organizationDao.loadOrgByCode(phoneNumber);
		if (org == null) {
			return false;
		}
		return Boolean.valueOf(StringUtils.equals(org.getPlanType(), "A"));
	}

}
