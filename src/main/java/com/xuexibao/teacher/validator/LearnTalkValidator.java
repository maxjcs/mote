package com.xuexibao.teacher.validator;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.xuexibao.teacher.exception.ValidateException;
import com.xuexibao.teacher.model.LearnMessage;

public class LearnTalkValidator {

	public static Set<Integer> teacherOfferTypes = new HashSet<Integer>();

	static {
		teacherOfferTypes.add(new Integer(1));
		teacherOfferTypes.add(new Integer(2));
		teacherOfferTypes.add(new Integer(3));
	}

	public static Set<Integer> returnTypes = new HashSet<Integer>();

	static {
		returnTypes.add(new Integer(1));
		returnTypes.add(new Integer(2));
	}

	public static String validateLearnTalkUpdate(String studentId, String teacherOfferId, Integer teacherOfferType) {
		String msg = null;
		if (StringUtils.isBlank(studentId)) {
			msg = "studentId 不能为空";
			return msg;
		}

		if (!teacherOfferTypes.contains(teacherOfferType)) {
			msg = "teacherOfferType 取值范围不正确";
			return msg;
		}

		if (StringUtils.isBlank(teacherOfferId)) {
			msg = "teacherOfferId 不能为空";
			return msg;
		}

		return null;
	}

	public static String validateLearnTalkDetail(String studentId, String teacherOfferId, Integer teacherOfferType,
			Integer type) {
		String msg = null;
		if (StringUtils.isBlank(studentId)) {
			msg = "studentId 不能为空";
			return msg;
		}
		if (StringUtils.isBlank(teacherOfferId)) {
			msg = "teacherOfferId 不能为空";
			return msg;
		}

		if (!teacherOfferTypes.contains(teacherOfferType)) {
			msg = "teacherOfferType 取值范围不正确";
			return msg;
		}

		if (null == type) {
			msg = "type 参数取值不能为空";
			return msg;
		}

		if (!returnTypes.contains(type)) {
			msg = "type 取值范围不正确";
			return msg;
		}

		return null;
	}

	public static String validateLearnTalkRecentMessage(String studentId, String teacherOfferId,
			Integer teacherOfferType, Long timestamp) {
		String msg = null;
		if (StringUtils.isBlank(studentId)) {
			msg = "studentId 不能为空";
			return msg;
		}
		if (StringUtils.isBlank(teacherOfferId)) {
			msg = "teacherOfferId 不能为空";
			return msg;
		}

		if (!teacherOfferTypes.contains(teacherOfferType)) {
			msg = "teacherOfferType 取值范围不正确";
			return msg;
		}

		if (null == timestamp) {
			msg = "timestamp 不能为空";
			return msg;
		}

		return null;
	}

	public static String validateTeacherSendMsg(String teacherOfferId, String sendToStudentId, Integer teacherOfferType,
			String sendMsg, String img) {
		String msg = null;
		if (StringUtils.isBlank(teacherOfferId)) {
			msg = "teacherOfferId 不能为空";
			return msg;
		} else if (teacherOfferId.length() > 50) {
			msg = "teacherOfferId 过长，请不要超过50个字符";
			return msg;
		}

		// if (StringUtils.isBlank(teacherOfferDesc)) {
		// msg = "teacherOfferDesc 不能为空";
		// return msg;
		// }else if(teacherOfferDesc.length() > 255){
		// msg = "teacherOfferDesc过长，请不要超过255个字符";
		// return msg;
		// }

		if (StringUtils.isBlank(sendToStudentId)) {
			msg = "sendToStudentId 不能为空";
			return msg;
		} else if (sendToStudentId.length() > 50) {
			msg = "sendToStudentId 过长，请不要超过50个字符";
			return msg;
		}

		if (!teacherOfferTypes.contains(teacherOfferType)) {
			msg = "teacherOfferType 取值范围不正确";
			return msg;
		}

		if (StringUtils.isBlank(sendMsg) && StringUtils.isBlank(img)) {
			msg = "msg和img 其中之一不能为空";
			return msg;
		}

		return null;
	}

	public static String validateStudentSendMsg(String studentId, String teacherOfferId, String teacherOfferDesc,
			Integer teacherOfferType, String sendMsg, String img) {

		String msg = null;
		if (StringUtils.isBlank(studentId)) {
			msg = "studentId 不能为空";
			return msg;
		}

		if (StringUtils.isBlank(teacherOfferId)) {
			msg = "teacherOfferId 不能为空";
			return msg;
		}

		if (StringUtils.isBlank(teacherOfferDesc)) {
			msg = "teacherOfferDesc 不能为空";
			return msg;
		}

		if (!teacherOfferTypes.contains(teacherOfferType)) {
			msg = "teacherOfferType 取值范围不正确";
			return msg;
		}

		if (StringUtils.isBlank(sendMsg) && StringUtils.isBlank(img)) {
			msg = "msg和img 其中之一不能为空";
			return msg;
		}

		return null;
	}

	public static void validateStudentSendMsgWithoutToken(LearnMessage message) {
		Validator.validateBlank(message.getTeacherId(), "teacherId 不能为空");
		Validator.validateBlank(message.getStudentId(), "studentId 不能为空");
		Validator.validateBlank(message.getTeacherOfferId(), "teacherOfferId 不能为空");
		Validator.validateBlank(message.getImageId(), " imageId 不能为空");

		if (!teacherOfferTypes.contains(message.getTeacherOfferType())) {
			throw new ValidateException("teacher_offer_type 取值范围不正确");
		}
		if (!LearnMessage.DIALOG_TYPES.contains(message.getDialogType())) {
			message.setDialogType(LearnMessage.DIALOG_TYPE_PAITI);
		}

		if (StringUtils.isBlank(message.getMsg()) && StringUtils.isBlank(message.getImg())) {
			throw new ValidateException("msg和img 其中之一不能为空");
		}
	}

	public static String validateLearnTalkStudentUpdate(String teacherId, String studentId, String teacherOfferId,
			Integer teacherOfferType) {
		String msg = null;
		if (StringUtils.isBlank(teacherId)) {
			msg = "teacher_id 不能为空";
			return msg;
		}

		if (StringUtils.isBlank(studentId)) {
			msg = "studentId 不能为空";
			return msg;
		}

		if (StringUtils.isBlank(teacherOfferId)) {
			msg = "teacherOfferId 不能为空";
			return msg;
		}

		if (!teacherOfferTypes.contains(teacherOfferType)) {
			msg = "teacherOfferType 取值范围不正确";
			return msg;
		}

		return null;
	}

}
