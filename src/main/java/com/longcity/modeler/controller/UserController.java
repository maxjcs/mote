/**
 * 
 */
package com.longcity.modeler.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longcity.modeler.core.AppContext;
import com.longcity.modeler.exception.BusinessException;
import com.longcity.modeler.filter.Token;
import com.longcity.modeler.model.User;
import com.longcity.modeler.service.UserService;
import com.longcity.modeler.service.VerifyCodeService;
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
	
	/**
     * 注册
     */
	@ResponseBody
    @RequestMapping(value = "register")
    public Object register(HttpServletRequest request,String phoneNumber,String smsCode,String password,Integer type) throws Exception{
        try{
        	int code=userService.register(phoneNumber, smsCode, password, type);
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
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "login")
	public Object login(User userparam, String deviceId, String deviceType, String version) {
		Validator.validateBlank(userparam.getPhoneNumber(), "手机号不能为空.");
		Validator.validateBlank(userparam.getPassword(), "密码不能为空.");
		Validator.validateMobile(userparam.getPhoneNumber(), "手机号非法.");

//		String lastDeviceId = DeviceUtil.decodeDeviceId(deviceId);
		User user = userService.login(userparam);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("token", Token.gen(user));

		return dataJson(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "logout")
	public Object logout() {
		userService.logout(AppContext.getUserId());

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

		String verifyCode = "000000";//VerifyCodeUtil.getRandNum(BusinessConstant.VERIFY_CODE_LENGTH);

		verifyCodeService.saveVerifyCode(phoneNumber, verifyCode);

		//VerifyCodeUtil.sendMessage(phoneNumber, verifyCode);

		return dataJson(true);
	}
	
	/**
	 * 更新个人信息
	 */
	@ResponseBody
	@RequestMapping(value = "update")
	public Object update(User user) {
		try{
			userService.updateUser(user);
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
	@RequestMapping(value = "getUserById")
	public Object getUserById(Integer userId) { 
		try {
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
