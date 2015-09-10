/**
 * 
 */
package com.longcity.modeler.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.longcity.modeler.constant.BusinessConstant;
import com.longcity.modeler.dao.UserDao;
import com.longcity.modeler.enums.UserStatus;
import com.longcity.modeler.exception.BusinessException;
import com.longcity.modeler.model.User;
import com.longcity.modeler.util.CipherUtil;


/**
 * @author maxjcs
 *
 */
@Service
public class UserService {
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	
	@Resource
	UserDao userDao;
	
	@Resource
	private VerifyCodeService verifyCodeService;
	
	
	/**
	 * 注册用户
	 * @param phoneNumber
	 * @param smsCode
	 * @param password
	 * @param type
	 * @return
	 */
	public int register(String phoneNumber,String smsCode,String password,Integer type){
		
		User existUser=userDao.selectByPhoneNumber(phoneNumber);
		if(existUser!=null){//存在
			return 1;
		}
		User newUser=new User();
		newUser.setPhoneNumber(phoneNumber);
		newUser.setType(type);
		newUser.setPassword(CipherUtil.MD5(password));
		newUser.setStatus(UserStatus.normal.getValue());
		newUser.setRemindFee(0);
		userDao.insert(newUser);
		
		return 0;//success
	}
	
	@Transactional
	public User login(User userParam) {
		User user = selectByPhoneNumber(userParam.getPhoneNumber());
		if (user == null) {
			throw new BusinessException("手机号尚未注册.");
		}

		if (UserStatus.abnormal.getValue() == user.getStatus()) {
			throw new BusinessException("您的账号因为录题规范问题已被停用.");
		}

		if (!StringUtils.equalsIgnoreCase(CipherUtil.MD5(userParam.getPassword()), user.getPassword())) {
			throw new BusinessException("密码输入有误.");
		}

		return user;
	}
	
	/**
	 * 更新个人资料
	 * @param userParam
	 */
	public void updateUser(User userParam) {
		//更新个人资料
		userDao.updateByPrimaryKey(userParam);
	}
	
	/**
	 * 获取个人资料详情
	 * @param userParam
	 */
	public User getUserInfo(Integer userId) {
		return  userDao.selectByPrimaryKey(userId);
	}
	
	
	/**
	 * 下线状态
	 * @param userId
	 */
	public void logout(Integer userId) {
		//userDao.setOfflineTeacher(userId);
	}
	
	public void changePasswordByVerifyCode(String phoneNumber, String password, String verifyCode) {
		User user = selectByPhoneNumber(phoneNumber);
		if (user == null) {
			throw new BusinessException("用户不存在或已停用.");
		}

		verifyCodeService.validateVerifyCode(phoneNumber, verifyCode, BusinessConstant.VERIFY_CODE_EXPIRATION_TIME);

		user.setPassword(password);
		updatePassword(user);
	}
	
	/**
	 * 更新密码
	 */
	@Transactional
	public void updatePassword(User user) {
		user.setPassword(CipherUtil.MD5(user.getPassword()));
		userDao.updatePassword(user);
	}
	
	
	
	
	
	
	/**
	 * 根据手机号查询用户
	 * @param phoneNumber
	 * @return
	 */
	public User selectByPhoneNumber(String phoneNumber){
		return userDao.selectByPhoneNumber(phoneNumber);
	}

}
