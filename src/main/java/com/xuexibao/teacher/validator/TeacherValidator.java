package com.xuexibao.teacher.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

import com.xuexibao.teacher.exception.ValidateException;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.util.ValidateUtil;

public class TeacherValidator {
	public static void validatePassword(String password) {
		Validator.validateBlank(password, "密码不能为空.");
		if (password.length() < 6) {
			throw new ValidateException("密码长度不足6位.");
		}

		if (password.length() > 16) {
			throw new ValidateException("密码长度超过16位.");
		}
	}

	public static void validateRegister(Teacher teacher) {
		String phoneNumber = teacher.getPhoneNumber();
		validatePassword(teacher.getPassword());
		Validator.validateBlank(teacher.getVerifyCode(), "验证码不能为空.");

		Validator.validateMobile(phoneNumber, "手机号非法.");
	}

	public void validateTeacher(Teacher teacher, Errors errors) {
		String name = teacher.getName();
		if (StringUtils.isBlank(name)) {
			errors.rejectValue("name", null, "姓名不能为空.");
			return;
		}

		String idNumber = teacher.getIdNumber();
		if (StringUtils.isBlank(idNumber)) {
			errors.rejectValue("idNumber", null, "身份证号不能为空.");
			return;
		}

		String qq = teacher.getQq();
		if (StringUtils.isBlank(qq)) {
			errors.rejectValue("qq", null, "QQ号不能为空.");
			return;
		}

		long schoolId = teacher.getSchoolId();
		if (schoolId == 0L) {
			errors.rejectValue("schoolId", null, "学校不能为空.");
			return;
		}

		if (name.length() < 2) {
			errors.rejectValue("name", null, "姓名长度不足2位.");
			return;
		}

		if (name.length() > 20) {
			errors.rejectValue("name", null, "姓名长度超过20位.");
			return;
		}

		if (!ValidateUtil.isChinese(name)) {
			errors.rejectValue("name", null, "姓名必须全部是中文.");
			return;
		}

		if (qq.length() > 15) {
			errors.rejectValue("qq", null, "QQ长度超过15位.");
			return;
		}

		if (!ValidateUtil.isIDCard(idNumber)) {
			errors.rejectValue("idNumber", null, "身份证号非法.");
			return;
		}
	}
}
