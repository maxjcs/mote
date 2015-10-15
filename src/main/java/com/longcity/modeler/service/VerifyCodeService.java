package com.longcity.modeler.service;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.longcity.modeler.dao.VerifyCodeDao;
import com.longcity.modeler.exception.BusinessException;

@Service
public class VerifyCodeService {
	@Resource
	private VerifyCodeDao verifyCodeDao;

	/**
	 * 保存验证码
	 */
	public void saveVerifyCode(String phoneNumber, String verifyCode) {
		verifyCodeDao.save(phoneNumber, verifyCode);
	}

	/**
	 * 校验验证码是否过期
	 */
	public void validateVerifyCode(String phoneNumber, String verifyCode, int expiredTime) {
		boolean isValid = verifyCodeDao.validateVerifyCode1(phoneNumber, verifyCode);

		if (!isValid) {
			throw new BusinessException("验证码输入有误");
		}
		
		isValid = verifyCodeDao.validateVerifyCode2(phoneNumber, verifyCode, expiredTime);
		if (!isValid) {
			throw new BusinessException("验证码已失效");
		}
	}

	/**
	 * 校验验证码
	 */
	public void validateVerifyCode(String phoneNumber, String verifyCode) {
//		if(StringUtils.equals(verifyCode, "000000")){
//			return;
//		}
		boolean isValid = verifyCodeDao.validateVerifyCode1(phoneNumber, verifyCode);

		if (!isValid) {
			throw new BusinessException("验证码输入有误");
		}
	}
}
