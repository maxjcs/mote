/**
 * 
 */
package com.longcity.modeler.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.longcity.modeler.constant.BusinessConstant;
import com.longcity.modeler.core.AppContext;
import com.longcity.modeler.dao.UserDao;
import com.longcity.modeler.exception.BusinessException;
import com.longcity.modeler.filter.Token;
import com.longcity.modeler.model.MoteCard;
import com.longcity.modeler.model.User;
import com.longcity.modeler.service.RedisService;
import com.longcity.modeler.service.UserService;
import com.longcity.modeler.service.VerifyCodeService;
import com.longcity.modeler.util.DateUtils;
import com.longcity.modeler.util.FileUtil;
import com.longcity.modeler.util.UpYunUtil;
import com.longcity.modeler.util.VerifyCodeUtil;
import com.longcity.modeler.validator.Validator;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("user")
public class UserController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource
	UserService userService;
	
	@Resource
	private VerifyCodeService verifyCodeService;
	
	@Resource
	private RedisService redisService;
	
	/**
     * 模特注册
     */
	@ResponseBody
    @RequestMapping(value = "register")
    public Object register(HttpServletRequest request,String phoneNumber,String smsCode,String password) throws Exception{
        try{
        	//验证码是否有效
        	verifyCodeService.validateVerifyCode(phoneNumber, smsCode);
        	//注册
        	int code=userService.register(phoneNumber, smsCode, password);
        	if(code==1){
        		return errorJson("该手机号已经注册！", request);
        	}
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("用户注册失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 商家注册
     */
	@ResponseBody
    @RequestMapping(value = "register4Seller")
    public Object register4Seller(HttpServletRequest request,User user) throws Exception{
        try{
        	//验证码是否有效
        	verifyCodeService.validateVerifyCode(user.getPhoneNumber(), user.getSmsCode());
        	//注册
        	int code=userService.register4Seller(user);
        	if(code==1){
        		return errorJson("该手机号已经注册！", request);
        	}
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("用户注册失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 手机是否注册
     */
	@ResponseBody
    @RequestMapping(value = "hasRegistered")
    public Object hasRegistered(HttpServletRequest request,String phoneNumber) throws Exception{
        try{
        	Boolean hasRegistered=userService.hasRegistered(phoneNumber);
            return dataJson(hasRegistered, request);
        }catch(Exception e){
            logger.error("检测手机是否注册失败失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "login")
	public Object login(HttpServletRequest request,HttpServletResponse response,User userparam) {
		Validator.validateBlank(userparam.getPhoneNumber(), "手机号不能为空.");
		Validator.validateBlank(userparam.getPassword(), "密码不能为空.");
		Validator.validateMobile(userparam.getPhoneNumber(), "手机号非法.");
		Validator.validateBlank(userparam.getLoginType(), "登陆类型非法.");

//		String lastDeviceId = DeviceUtil.decodeDeviceId(deviceId);
		User user = userService.login(userparam);
		
		String token=Token.gen(user);
		if(StringUtils.equals(userparam.getLoginType(), "1")){//手机登陆
		}else if(StringUtils.equals(userparam.getLoginType(), "2")){//pc端登陆
			Cookie c1 = new Cookie("token", token);
			c1.setMaxAge(24*60*60);
			response.addCookie(c1);
		}
		//设置登陆状态
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("token", token);
		redisService.redisSetLoginStatus(user.getId());
		return dataJson(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "logout")
	public Object logout() {
		userService.logout(AppContext.getUserId());
		//清除登陆状态
		redisService.redisClearLoginStatus(AppContext.getUserId());
		return dataJson(true);
	}
	
	/**
	 * 发送手机验证码
	 */
	@ResponseBody
	@RequestMapping(value = "sendVerifyCode")
	public Object sendVerifyCode(String phoneNumber, String cipherCode, String code) throws Exception {
		//checkValidVerifyCode(cipherCode, code);
		Validator.validateBlank(phoneNumber, "手机号不能为空.");
		Validator.validateMobile(phoneNumber, "手机号非法.");

		User user = userService.selectByPhoneNumber(phoneNumber);
		if (user == null) {
			throw new BusinessException("该手机号未注册");
		}

		String verifyCode = VerifyCodeUtil.getRandNum(BusinessConstant.VERIFY_CODE_LENGTH);

		verifyCodeService.saveVerifyCode(phoneNumber, verifyCode);

		VerifyCodeUtil.sendMessage(phoneNumber, verifyCode);

		return dataJson(true);
	}
	
	/**
	 * 更新模特个人信息
	 */
	@ResponseBody
	@RequestMapping(value = "updateMoteAvart")
	public Object updateMoteAvart(MultipartFile image) {
		try{
			Integer userId=AppContext.getUserId();
			Validator.validateImageFile(image);
			if (!FileUtil.isEmpty(image)) {
				String url = UpYunUtil.upload(image);
				userService.updateMoteAvart(userId,url);
			}
		    return dataJson(true);
		}catch (Exception e) {
			logger.error("更新个人信息失败",e);
		}
		return errorJson("服务器异常，请重试.");
	}
	
	/**
	 * 更新模特个人信息
	 */
	@ResponseBody
	@RequestMapping(value = "updateMote")
	public Object updateMote(User user) {
		try{
			Integer userId=AppContext.getUserId();
			user.setId(userId);
			if(StringUtils.isNotBlank(user.getBirdthdayStr())){
				user.setBirdthday(DateUtils.stringToDate(user.getBirdthdayStr()));
			}
			userService.updateMote(user);
		    return dataJson(true);
		}catch (Exception e) {
			logger.error("更新个人信息失败",e);
		}
		return errorJson("服务器异常，请重试.");
	}
	
	/**
	 * 更新商家信息
	 */
	@ResponseBody
	@RequestMapping(value = "updateSeller")
	public Object updateSeller(String nickname,String shopName,String email,String weixin,String address,String referee) {
		try{
			Integer userId=AppContext.getUserId();
			User user=new User();
			user.setId(userId);
			user.setNickname(nickname);
			user.setShopName(shopName);
			user.setEmail(email);
			user.setWeixin(weixin);
			user.setAddress(address);
			user.setReferee(referee);
			userService.updateSeller(user);
		    return dataJson(true);
		}catch (Exception e) {
			logger.error("更新个人信息失败",e);
		}
		return errorJson("服务器异常，请重试.");
	}
	
	/**
	 * 获取用户详情
	 */
	@ResponseBody
	@RequestMapping(value = "getUserInfo")
	public Object getUserInfo() { 
		try {
			Integer userId=AppContext.getUserId();
			return dataJson(userService.getUserInfo(userId));
		} catch (Exception e) {
			logger.error("获取用户详情失败",e);
		}
		return errorJson("服务器异常，请重试.");
	}
	
	/**
	 * 密码重置
	 */
	@ResponseBody
	@RequestMapping(value = "changePasswordByVerifyCode")
	public Object changePasswordByVerifyCode(User user,String smsCode) {
//		TeacherValidator.validateRegister(teacher);

		userService.changePasswordByVerifyCode(user.getPhoneNumber(), user.getPassword(),
				smsCode);

		return dataJson(true);
	}
	
	
	/**
	 * 模特上传模卡图片
	 * @param image1
	 * @param image2
	 * @param image3
	 * @param image4
	 * @param image5
	 * @param image6
	 */
	@ResponseBody
    @RequestMapping(value = "addMoteCard")
	public Object addMoteCard(HttpServletRequest request,MultipartFile image1, MultipartFile image2, MultipartFile image3,
			MultipartFile image4,MultipartFile image5,MultipartFile image6)throws Exception {
		Validator.validateImageFile(image1);
		Validator.validateImageFile(image2);
		Validator.validateImageFile(image3);
		Validator.validateImageFile(image4);
		Validator.validateImageFile(image5);
		Validator.validateImageFile(image6);
		try{
			Integer userId=AppContext.getUserId();
			if (!FileUtil.isEmpty(image1)) {
				String url = UpYunUtil.upload(image1);
				userService.addMoteCard(userId,url);
			}
			if (!FileUtil.isEmpty(image2)) {
				String url = UpYunUtil.upload(image2);
				userService.addMoteCard(userId,url);
			}
			if (!FileUtil.isEmpty(image3)) {
				String url = UpYunUtil.upload(image3);
				userService.addMoteCard(userId,url);
			}
			if (!FileUtil.isEmpty(image4)) {
				String url = UpYunUtil.upload(image4);
				userService.addMoteCard(userId,url);
			}
			if (!FileUtil.isEmpty(image5)) {
				String url = UpYunUtil.upload(image5);
				userService.addMoteCard(userId,url);
			}
			if (!FileUtil.isEmpty(image6)) {
				String url = UpYunUtil.upload(image6);
				userService.addMoteCard(userId,url);
			}
			 return dataJson(true,request);
		}
		catch (Exception e) {
			logger.error("上传模卡照片失败.", e);
            return errorJson("服务器异常，请重试.", request);
		}
	}
	
	/**
	 * 获取模卡图片
	 */
	@ResponseBody
	@RequestMapping(value = "getMoteCardList")
	public Object getMoteCardList(Integer userId) {
		if(userId==null){
			userId=AppContext.getUserId();
		}
		List<MoteCard> cardList=userService.getMoteCardList(userId);
		return dataJson(cardList);
	}
	
	/**
	 * 删除模卡图片
	 */
	@ResponseBody
	@RequestMapping(value = "deleteMoteCard")
	public Object deleteMoteCard(Integer id) {
		userService.deleteMoteCard(id);
		return dataJson(true);
	}
	
	/**
	 * 审核用户
	 */
	@ResponseBody
	@RequestMapping(value = "approve")
	public void approve(HttpServletResponse response,Integer id,Integer status,Integer type) {
		userService.approve(id,status);
		try{
			if(type==1){//商家
				 response.sendRedirect("../back/seller/sellerDetail?sellerId="+id);
			}else{
				response.sendRedirect("../back/mote/moteDetail?moteId="+id);
			}  
			return;
		}catch (Exception e) {
			
		}
		
	}
	
	/**
	 * 我是模特面板
	 */
	@ResponseBody
	@RequestMapping(value = "myMoteInfo")
	public Object myMoteInfo() {
		Integer userId=AppContext.getUserId();
		Map map=userService.myMoteInfo(userId);
		return dataJson(map);
	}
	
	
	
//	private void checkValidVerifyCode(String cipherCode, String code) {
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		if (StringUtils.isNotBlank(cipherCode)) {
//			paramMap.put("cipherCode", cipherCode);
//		}
//		if (StringUtils.isNotBlank(code)) {
//			paramMap.put("code", code);
//		}
//		paramMap.put("expireTime", 60000);
//		String url = PropertyUtil.getProperty("check_varlid_verify_code_url");
//		VerfiyCodeStatus status = HttpUtil.httpPost(url, paramMap, VerfiyCodeStatus.class);
//		if (status == null) {
//			throw new BusinessException("图片服务验证异常");
//		}
//		if (status.getStatus() != 0) {
//			throw new BusinessException(status.getMessage(), status.getStatus());
//		}
//	}

}
