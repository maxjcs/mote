package com.xuexibao.teacher.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.filter.Token;
import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.VerfiyCodeStatus;
import com.xuexibao.teacher.service.OrganizationService;
import com.xuexibao.teacher.service.PushMsgService;
import com.xuexibao.teacher.service.TeacherService;
import com.xuexibao.teacher.service.VerifyCodeService;
import com.xuexibao.teacher.util.BusinessConstant;
import com.xuexibao.teacher.util.CipherUtil;
import com.xuexibao.teacher.util.DeviceUtil;
import com.xuexibao.teacher.util.HttpUtil;
import com.xuexibao.teacher.util.JsonUtil;
import com.xuexibao.teacher.util.PropertyUtil;
import com.xuexibao.teacher.util.RedisUtil;
import com.xuexibao.teacher.util.UpYunUtil;
import com.xuexibao.teacher.util.VerifyCodeUtil;
import com.xuexibao.teacher.validator.TeacherValidator;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("user")
public class UserController extends AbstractController {
	@Resource
	private VerifyCodeService verifyCodeService;
	@Resource
	private TeacherService teacherService;
	@Resource
	private RedisUtil redisUtil;
	@Resource
	private PushMsgService pushMsgService;
	@Resource
	private OrganizationService organizationService;

	/**
	 * 发送机构邀请码
	 */
	@ResponseBody
	@RequestMapping(value = "sendOrgInvitationCode")
	public Object sendOrgInvitationCode(String code) throws Exception {
		Validator.validateBlank(code, "邀请码不能为空");

		teacherService.sendOrgInvitationCode(AppContext.getTeacherId(), code);

		teacherService.refreshTeacherToRedis(AppContext.getTeacherId());

		return dataJson(true);
	}

	/**
	 * 发送手机验证码
	 */
	@ResponseBody
	@RequestMapping(value = "sendVerifyCode")
	public Object sendVerifyCode(String phoneNumber, String cipherCode, String code) throws Exception {
		checkValidVerifyCode(cipherCode, code);
		Validator.validateBlank(phoneNumber, "手机号不能为空.");
		Validator.validateMobile(phoneNumber, "手机号非法.");

		Teacher teacher = teacherService.loadTeacherByPhoneNumber(phoneNumber);
		if (teacher == null) {
			throw new BusinessException("该手机号未注册");
		}

		String verifyCode = VerifyCodeUtil.getRandNum(BusinessConstant.VERIFY_CODE_LENGTH);

		verifyCodeService.saveVerifyCode(phoneNumber, verifyCode);

		VerifyCodeUtil.sendMessage(phoneNumber, verifyCode);

		return dataJson(true);
	}

	@ResponseBody
	@RequestMapping(value = "sendBankVerifyCode")
	public Object sendBankVerifyCode(String cipherCode, String code) throws Exception {
		checkValidVerifyCode(cipherCode, code);
		Teacher teacher = teacherService.getRequiredTeacher(AppContext.getTeacherId());

		String verifyCode = VerifyCodeUtil.getRandNum(BusinessConstant.VERIFY_CODE_LENGTH);

		verifyCodeService.saveVerifyCode(teacher.getPhoneNumber(), verifyCode);

		VerifyCodeUtil.sendMessage(teacher.getPhoneNumber(), verifyCode);

		return dataJson(true);
	}

	@ResponseBody
	@RequestMapping(value = "sendRegisterVerifyCode")
	public Object sendRegisterVerifyCode(String phoneNumber, String cipherCode, String code) throws Exception {
		checkValidVerifyCode(cipherCode, code);
		Validator.validateBlank(phoneNumber, "手机号不能为空.");
		Validator.validateMobile(phoneNumber, "手机号非法.");

		Teacher teacher = teacherService.loadTeacherByPhoneNumber(phoneNumber);
		if (teacher != null) {
			throw new BusinessException("该手机号已经注册");
		}

		String verifyCode = VerifyCodeUtil.getRandNum(BusinessConstant.VERIFY_CODE_LENGTH);

		verifyCodeService.saveVerifyCode(phoneNumber, verifyCode);

		VerifyCodeUtil.sendMessage(phoneNumber, verifyCode);

		return dataJson(true);
	}

	private void checkValidVerifyCode(String cipherCode, String code) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(cipherCode)) {
			paramMap.put("cipherCode", cipherCode);
		}
		if (StringUtils.isNotBlank(code)) {
			paramMap.put("code", code);
		}
		paramMap.put("expireTime", 60000);
		String url = PropertyUtil.getProperty("check_varlid_verify_code_url");
		VerfiyCodeStatus status = HttpUtil.httpPost(url, paramMap, VerfiyCodeStatus.class);
		if (status == null) {
			throw new BusinessException("图片服务验证异常");
		}
		if (status.getStatus() != 0) {
			throw new BusinessException(status.getMessage(), status.getStatus());
		}
	}

	@ResponseBody
	@RequestMapping(value = "bindBank")
	public Object bindBank(String bankCard, String idNumber, String bank, String name, String verifyCode)
			throws Exception {
		Validator.validateBlank(bankCard, "银行卡名称不能为空.");
		Validator.validateBlank(name, "账户名不能空.");
		Validator.validateBlank(verifyCode, "验证码不能空.");
		Validator.validateBlank(idNumber, "身份证号码不能为空");

		Teacher teacher = teacherService.getRequiredTeacher(AppContext.getTeacherId());

		verifyCodeService.validateVerifyCode(teacher.getPhoneNumber(), verifyCode,
				BusinessConstant.VERIFY_CODE_EXPIRATION_TIME);

		teacher.setBank(bank);
		teacher.setBankCard(bankCard);
		teacher.setBankUserName(name);
		teacher.setIdNumber(idNumber);

		teacherService.bindBank(teacher);
		teacherService.refreshTeacherToRedis(teacher.getId());

		return dataJson(true);
	}

	@ResponseBody
	@RequestMapping(value = "unbindBank")
	public Object unbindBank(String verifyCode) throws Exception {
		Validator.validateBlank(verifyCode, "验证码不能空.");

		Teacher teacher = teacherService.getRequiredTeacher(AppContext.getTeacherId());

		verifyCodeService.validateVerifyCode(teacher.getPhoneNumber(), verifyCode,
				BusinessConstant.VERIFY_CODE_EXPIRATION_TIME);

		teacherService.unbindBank(teacher);
		teacherService.refreshTeacherToRedis(teacher.getId());

		return dataJson(new HashMap<String, Object>());
	}

	/**
	 * 注册
	 */
	@ResponseBody
	@RequestMapping(value = "register")
	public Object register(Teacher teacher, String deviceId, String deviceType, HttpServletResponse response) {
		Validator.validateBlank(deviceId, "设备编号不能为空.");
		Validator.validateBlank(deviceType, "设备类型不能为空.");
		String lastDeviceId = DeviceUtil.decodeDeviceId(deviceId);
		teacher.setLastDeviceId(lastDeviceId);
		teacher.setLastDeviceType(deviceType);

		TeacherValidator.validateRegister(teacher);

		teacherService.register(teacher);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("token", Token.gen(teacher));
		result.put("deviceId", lastDeviceId);

		return dataJson(result);
	}

	/**
	 * 验证码
	 */
	@ResponseBody
	@RequestMapping(value = "verifyCode")
	public Object verifyCode(String phoneNumber, String verifyCode) {
		verifyCodeService.validateVerifyCode(phoneNumber, verifyCode, BusinessConstant.VERIFY_CODE_EXPIRATION_TIME);

		return dataJson(true);
	}

	@ResponseBody
	@RequestMapping(value = "bindIosToken")
	public Object bindIosToken(String iosToken) {
		Validator.validateBlank(iosToken, "IOS Token不能为空");

		teacherService.bindIosToken(AppContext.getTeacherId(), iosToken);

		return dataJson(true);
	}

	@ResponseBody
	@RequestMapping(value = "logout")
	public Object logout() {
		teacherService.logout(AppContext.getTeacherId());

		return dataJson(true);
	}

	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "login")
	public Object login(Teacher teacherParam, String deviceId, String deviceType, String version) {
		Validator.validateBlank(teacherParam.getPhoneNumber(), "手机号不能为空.");
		Validator.validateBlank(teacherParam.getPassword(), "密码不能为空.");
		Validator.validateMobile(teacherParam.getPhoneNumber(), "手机号非法.");
		Validator.validateBlank(deviceId, "设备编号不能为空.");
		Validator.validateBlank(deviceType, "设备类型不能为空.");

		String lastDeviceId = DeviceUtil.decodeDeviceId(deviceId);

		teacherParam.setLastDeviceId(lastDeviceId);
		teacherParam.setLastDeviceType(deviceType);

		Teacher teacher = teacherService.login(teacherParam);

		pushMsgService.reSendPushMsg(teacher.getId());

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("token", Token.gen(teacher));
		result.put("isPerfectInfo", teacher.isPerfectInfo(version));
		result.put("deviceId", lastDeviceId);
		result.put("teacherId", teacher.getId());

		return dataJson(result);
	}

	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "loginReact")
	public Object loginReact(Teacher teacherParam, String deviceId, String deviceType, String version) {
		deviceId = "RAgP1i7r+jLQxFd+VmL/5NBNznPtmW/6YxoTQ4QOZvNptzTbxbAz1zE5jUemdXKRYD9SK7tFlmwV45EdVbeYlD8Lt85Xr74b220z6klJFm2RjjZTr121xJeoUrUDV9FRhcTfhabbMndrBCIEo7NYTJ7K/9MoiimxbMgIUTHOVEqygNDKH7QxAW5BNCSYj8Utcilgd2Qeulx/sGMSfLcrdqdlAg2Y19f3h/QPRVNu5pGitagstapOnn6S+NCPV17WTGIfU1UKhL+n+60zSs/SxVNHrj/wbw+kO2HnN5RKGuJTHkPCGmN9aqX1i1EVm0KobQw4zJSo4w8VbPOyQhcCPQ==";

		Validator.validateBlank(teacherParam.getPhoneNumber(), "手机号不能为空.");
		Validator.validateBlank(teacherParam.getPassword(), "密码不能为空.");
		Validator.validateMobile(teacherParam.getPhoneNumber(), "手机号非法.");
		Validator.validateBlank(deviceId, "设备编号不能为空.");
		Validator.validateBlank(deviceType, "设备类型不能为空.");

		String lastDeviceId = DeviceUtil.decodeDeviceId(deviceId) + teacherParam.getPhoneNumber();

		teacherParam.setLastDeviceId(lastDeviceId);
		teacherParam.setLastDeviceType(deviceType);

		Teacher teacher = teacherService.login(teacherParam);

		// pushMsgService.reSendPushMsg(teacher.getId());

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("token", Token.gen(teacher));
		result.put("isPerfectInfo", teacher.isPerfectInfo(version));
		result.put("deviceId", lastDeviceId);
		result.put("teacherId", teacher.getId());

		return dataJson(result);
	}

	/**
	 * 密码重置
	 */
	@ResponseBody
	@RequestMapping(value = "changePasswordByVerifyCode")
	public Object changePasswordByVerifyCode(Teacher teacher) {
		TeacherValidator.validateRegister(teacher);

		teacherService.changePasswordByVerifyCode(teacher.getPhoneNumber(), teacher.getPassword(),
				teacher.getVerifyCode());

		return dataJson(true);
	}

	/**
	 * 更新个人信息
	 */
	@ResponseBody
	@RequestMapping(value = "update")
	public Object update(Teacher teacher) {
		teacher.setId(AppContext.getTeacherId());

		Validator.validateImageFile(teacher.getAvatarFile());
		Validator.validateImageFile(teacher.getStudentCardFile());

		if (teacher.getAvatarFile() != null && !teacher.getAvatarFile().isEmpty()) {
			MultipartFile file = teacher.getAvatarFile();
			String url = UpYunUtil.upload(file);
			teacher.setAvatarUrl(url);
		}
		if (teacher.getStudentCardFile() != null && !teacher.getStudentCardFile().isEmpty()) {
			MultipartFile file = teacher.getStudentCardFile();
			String url = UpYunUtil.upload(file);
			teacher.setStudentCardImageUrl(url);
		}

		teacherService.updateTeacher(teacher);

		return dataJson(true);
	}

	/**
	 * 更新个人简介
	 */
	@ResponseBody
	@RequestMapping(value = "updateSelfDescription")
	public Object updateSelfDescription(Teacher teacher) {
		Validator.validateBlank(teacher.getSelfDescription(), "个人描述不能为空");
		teacher.setId(AppContext.getTeacherId());
		teacherService.updateSelfDescription(teacher);

		return dataJson(true);
	}

	/**
	 * 不参加任务
	 */
	@ResponseBody
	@RequestMapping(value = "unjoinCapacityTest")
	public Object unjoinCapacityTest() {
		teacherService.unjoinCapacityTest(AppContext.getTeacherId());

		return dataJson(true);
	}

	/**
	 * 获取教师详情
	 */
	@ResponseBody
	@RequestMapping(value = "getTeacher")
	public Object getTeacher() {
		Teacher teacher = teacherService.getTeacher(AppContext.getTeacherId());
		StarPoint starPoint = teacherService.getStarPointByTeacherId(teacher.getId());
		Map<String, Object> result = JsonUtil.obj2Map(teacher);
		result.put("selfDescription", teacher.getEmojiSelfDescription());
		result.put("gradeStr", redisUtil.getGradeStringByIds(teacher.getGradeIds()));
		result.put("subjectStr", redisUtil.getSubjectStringByIds(teacher.getSubjectIds()));
		result.put("schoolStr", redisUtil.getSchoolNameById(teacher.getSchoolId()));
		result.put("cityStr", redisUtil.getCityNameById(teacher.getCityId()));
		result.put("teacherId", teacher.getId());
		result.put("star", starPoint.getStar());
		result.put("point", starPoint.getPoint());
		teacherService.appendTeacherInfo(result, teacher.getId());

		return dataJson(result);
	}

	@ResponseBody
	@RequestMapping(value = "getBindBankList")
	public Object getBindBankList() {
		Teacher teacher = teacherService.getRequiredTeacher(AppContext.getTeacherId());
		List<Object> result = new ArrayList<Object>();
		if (teacher.isBindBank()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bank", teacher.getBank());
			map.put("bankCard", teacher.getBankCard());
			map.put("bankUserName", teacher.getBankUserName());
			map.put("idNumber", teacher.getIdNumber());
			result.add(map);
		}

		return dataJson(result);
	}

	/**
	 * 获取教师详情
	 */
	@ResponseBody
	@RequestMapping(value = "getTeacherById")
	public Object getTeacherById(String teacherId) {
		return dataJson(teacherService.getTeacherInfo(teacherId));
	}

	/**
	 * 密码重置
	 */
	@ResponseBody
	@RequestMapping(value = "changePassword")
	public Object changePassword(String oldPassword, String newPassword) {
		Validator.validateBlank(oldPassword, "原密码不能为空");

		Teacher teacher = teacherService.getRequiredTeacher(AppContext.getTeacherId());

		if (!StringUtils.equalsIgnoreCase(CipherUtil.MD5(oldPassword), teacher.getPassword())) {
			throw new BusinessException("旧密码错误");
		}

		if (StringUtils.equals(oldPassword, newPassword)) {
			throw new BusinessException("新旧密码不能相同");
		}

		TeacherValidator.validatePassword(newPassword);

		teacher.setId(AppContext.getTeacherId());
		teacher.setPassword(newPassword);
		teacherService.updatePassword(teacher);

		return dataJson(Token.gen(teacherService.getTeacher(AppContext.getTeacherId())));
	}

	@ResponseBody
	@RequestMapping(value = "getOrgListByCityId")
	public Object getOrgListByCityId(Integer cityId) throws Exception {
		Validator.validateLtZero(cityId, "cityId不能为空");
		return dataJson(organizationService.getOrgListByCityId(cityId));
	}

	@ResponseBody
	@RequestMapping(value = "getMyStudentList")
	public Object getMyStudentList(Integer sortType, Integer gradeId, Integer pageNo, Integer pageSize)
			throws Exception {
		// 默认排序：关注时间降序
		if (sortType == null) {
			sortType = 1;
		}
		if (gradeId == null) {
			gradeId = -1;// 全部
		}
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		String teacherId = AppContext.getTeacherId();

		return dataJson(teacherService.getMyStudentList(teacherId, sortType, gradeId, pageNo, pageSize));
	}

}
