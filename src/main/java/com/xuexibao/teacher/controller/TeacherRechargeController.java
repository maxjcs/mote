package com.xuexibao.teacher.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.exception.ValidateException;
import com.xuexibao.teacher.model.CommonConfig;
import com.xuexibao.teacher.model.TeacherRecharge;
import com.xuexibao.teacher.service.TeacherRechargeService;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("teacherrecharge")
public class TeacherRechargeController extends AbstractController {
	@Resource
	private TeacherRechargeService teacherRechargeService;

	@ResponseBody
	@RequestMapping(value = "addRecharge")
	public Object addRecharge(TeacherRecharge charge) {
		Validator.validateBlank(charge.getStudentPhoneNumber(), "学生手机号不能为空");
		charge.setTeacherId(AppContext.getTeacherId());

		teacherRechargeService.addTeacherRecharge(charge);

		return dataJson(charge);
	}

	@ResponseBody
	@RequestMapping(value = "getChargetNotice")
	public Object getChargetNotice() {
		return dataJson(teacherRechargeService.getChargetNotice());
	}

	@ResponseBody
	@RequestMapping(value = "getTeacherRewardOfMemberCharge")
	public Object getTeacherRewardOfMemberCharge(String phoneNumber, Integer type,Integer money) {
		if (type == null || (type != CommonConfig.TYPE_CHONGZHI_BAONIAN && type != CommonConfig.TYPE_CHONGZHI_BAOYUE)) {
			throw new ValidateException("type值有误[1,2]");
		}
		Validator.validateBlank(phoneNumber, "phoneNumber不能为空");
		Validator.validateBlank(money, "money不能为空");
		
		return dataStudentJson(teacherRechargeService.getTeacherRewardOfMemberCharge(phoneNumber,type,money));
	}

	@ResponseBody
	@RequestMapping(value = "updateStudentRecharge")
	public Object updateStudentRecharge(String teacherId, String studentId, Integer money) {
		Validator.validateBlank(studentId, "studentId不能为空");
		Validator.validateBlank(teacherId, "teacherId不能为空");
		Validator.validateBlank(money, "money不能为空");

		return dataStudentJson(teacherRechargeService.updateStudentRecharge(teacherId, studentId, money));
	}

	@ResponseBody
	@RequestMapping(value = "listRecharge")
	public Object listRecharge(Long lastId) {
		if (lastId == null || lastId == 0) {
			lastId = Long.MAX_VALUE;
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastId", lastId);
		paramMap.put("teacherId", AppContext.getTeacherId());
		paramMap.put("pageSize", PageConstant.PAGE_SIZE_10);

		return dataJson(teacherRechargeService.listTeacherRecharge(paramMap));
	}

}
