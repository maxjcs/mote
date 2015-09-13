/**
 * 
 */
package com.longcity.modeler.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.longcity.modeler.constant.BusinessConstant;
import com.longcity.modeler.dao.MoteCardDao;
import com.longcity.modeler.dao.UserDao;
import com.longcity.modeler.enums.UserStatus;
import com.longcity.modeler.exception.BusinessException;
import com.longcity.modeler.model.MoteCard;
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
	MoteCardDao moteCardDao;
	
	@Resource
	private VerifyCodeService verifyCodeService;
	
	/**
	 * 根据主键获取Id
	 * @param id
	 * @return
	 */
	public User getUserById(Integer id){
		return userDao.selectByPrimaryKey(id);
	}
	
	
	/**
	 * 冻结金额
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void freezeFee(Integer userId,Integer fee){
		Map paramMap=new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("fee", fee);
		userDao.freezeFee(paramMap);
	}
	
	
	
	
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
	 * 更新模特资料
	 * @param userParam
	 */
	public void updateMote(User userParam) {
		//更新个人资料
		userDao.updateMote(userParam);
	}
	
	/**
	 * 更新商家资料
	 * @param userParam
	 */
	public void updateSeller(User userParam) {
		//更新个人资料
		userDao.updateSeller(userParam);
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
	
	/**
	 * 插入模卡图片
	 * @param phoneNumber
	 * @return
	 */
	public void addMoteCard(Integer userId,String imgUrl){
		MoteCard record=new MoteCard();
		record.setUserId(userId);
		record.setImgUrl(imgUrl);
		record.setSortNo(0);
	    moteCardDao.insert(record);
	}
	
	/**
	 * 获取模卡图片
	 * @param phoneNumber
	 * @return
	 */
	public List<MoteCard> getMoteCardList(Integer userId){
	   return moteCardDao.queryByUserId(userId);
	}
	
	/**
	 * 删除模卡图片
	 * @param phoneNumber
	 * @return
	 */
	public void deleteMoteCard(Integer id){
	   moteCardDao.delete(id);
	}
	
	

}
