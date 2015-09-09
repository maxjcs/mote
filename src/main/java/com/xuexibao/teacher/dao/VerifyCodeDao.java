package com.xuexibao.teacher.dao;

import com.xuexibao.teacher.core.MybatisMapper;

@MybatisMapper
public interface VerifyCodeDao {

	void save(String phoneNumber, String verifyCode);

	boolean validateVerifyCode2(String phoneNumber, String verifyCode, int expiredTime);

	boolean validateVerifyCode1(String phoneNumber, String verifyCode);

}
