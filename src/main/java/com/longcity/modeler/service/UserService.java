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

import com.longcity.modeler.dao.MessageDao;
import com.longcity.modeler.dao.MoteCardDao;
import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.dao.UserDao;
import com.longcity.modeler.enums.UserStatus;
import com.longcity.modeler.enums.UserType;
import com.longcity.modeler.exception.BusinessException;
import com.longcity.modeler.model.MoteCard;
import com.longcity.modeler.model.User;
import com.longcity.modeler.util.CipherUtil;
import com.longcity.modeler.util.MoneyUtil;


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
	
	@Resource
	RedisService redisService;
	
	@Resource
	MoteTaskDao moteTaskDao;
	
	@Resource
	MessageDao messageDao;
	
	
	/**
	 * 更新用户头像
	 * @param userId
	 * @param avartUrl
	 */
	public void updateMoteAvart(Integer userId,String avartUrl){
		User user=new User();
		user.setId(userId);
		user.setAvartUrl(avartUrl);
		userDao.updateMote(user);
	}
	
	
	/**
	 * 我是模特
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map myMoteInfo(Integer userId){
		Integer followNum=moteTaskDao.countByMoteId(userId);
		Integer performNum=moteTaskDao.getPerformMoteTaskNum(userId);//进行中
		Integer finishNum=moteTaskDao.getMoteTaskNumByMoteId(userId);//已经完成
		Integer applyKefuNum=moteTaskDao.getKefuMoteTaskNum(userId);//申请客服
		Integer messageNum = messageDao.countMessageByType("1,2");
		
		Map resultMap=new HashMap();
		resultMap.put("followNum", followNum);
		resultMap.put("performNum", performNum);
		resultMap.put("finishNum", finishNum);
		resultMap.put("applyKefuNum", applyKefuNum);
		resultMap.put("messageNum", messageNum);
		
		User user = getUserById(userId);
		Double remindFee = user.getRemindFee();
		if(remindFee!=null){
			resultMap.put("remindFee", MoneyUtil.fen2Yuan(remindFee));
		}else{
			resultMap.put("remindFee", 0);
		}
		
		resultMap.put("avartUrl", user.getAvartUrl());
		
		return resultMap;
	}
	
	/**
	 * 根据主键获取Id
	 * @param id
	 * @return
	 */
	public User getUserById(Integer id){
		return userDao.selectByPrimaryKey(id);
	}
	
	/**
	 * 审核用户
	 * @param id
	 * @return
	 */
	public void approve(Integer id,Integer status){
		 userDao.approve(id,status);
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
	public int register(String phoneNumber,String smsCode,String password){
		
		User existUser=userDao.selectByPhoneNumber(phoneNumber);
		if(existUser!=null){//存在
			return 1;
		}
		User newUser=new User();
		newUser.setPhoneNumber(phoneNumber);
		newUser.setType(UserType.mote.getValue());
		newUser.setPassword(CipherUtil.MD5(password));
		newUser.setStatus(UserStatus.normal.getValue());
		newUser.setRemindFee(0.0);
		userDao.insert(newUser);
		//缓存中增加用户数
		redisService.registerUser(newUser);
		
		return 0;//success
	}
	
	/**
	 * 注册用户
	 * @param phoneNumber
	 * @param smsCode
	 * @param password
	 * @param type
	 * @return
	 */
	public int register4Seller(User user){
		
		User existUser=userDao.selectByPhoneNumber(user.getPhoneNumber());
		if(existUser!=null){//存在
			return 1;
		}
		User newUser=new User();
		newUser.setPhoneNumber(user.getPhoneNumber());
		newUser.setType(UserType.seller.getValue());
		newUser.setPassword(CipherUtil.MD5(user.getPassword()));
		newUser.setShopName(user.getShopName());
		newUser.setWeixin(user.getWeixin());
		newUser.setEmail(user.getEmail());
		newUser.setAddress(user.getAddress());
		newUser.setReferee(user.getReferee());
		newUser.setStatus(UserStatus.normal.getValue());
		newUser.setRemindFee(0.0);
		userDao.insert(newUser);
		//缓存中增加用户数
		redisService.registerUser(newUser);
		
		return 0;//success
	}
	
	/**
	 * 注册用户
	 * @param phoneNumber
	 * @return
	 */
	public Boolean hasRegistered(String phoneNumber){
		
		User existUser=userDao.selectByPhoneNumber(phoneNumber);
		if(existUser!=null){//存在
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	@Transactional
	public User login(User userParam) {
		User user = selectByPhoneNumber(userParam.getPhoneNumber());
		if (user == null) {
			throw new BusinessException("手机号尚未注册.");
		}

		if (UserStatus.abnormal.getValue() == user.getStatus()) {
			throw new BusinessException("您的账号已被停用.");
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
		//计算体型  体型(BMI算法)
		if(userParam.getHeight()!=null&&userParam.getHeight()!=0&&userParam.getWeight()!=null&&userParam.getWeight()!=0){
			double value=userParam.getWeight()*1.0/userParam.getHeight()*userParam.getHeight();
			if(value<18.5){
				userParam.setShape(1);//偏瘦
			}else if(value>=18.5&&value<24){
				userParam.setShape(2);//中等
			}else{
				userParam.setShape(3);//偏胖
			}
		}
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
		User user =  userDao.selectByPrimaryKey(userId);
		user.setRemindFee(MoneyUtil.fen2Yuan(user.getRemindFee()));
		return user;
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

		verifyCodeService.validateVerifyCode(phoneNumber, verifyCode);

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
	
	public void updateRemindFee(Integer userId,Integer money){
		userDao.updateRemindFee(userId, money);
	}
	
	

}
