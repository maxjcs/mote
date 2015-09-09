package com.xuexibao.teacher.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.enums.Wb2videoProcessStatus;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.FeudAnswerDetail;
import com.xuexibao.teacher.model.FeudDetailWb;
import com.xuexibao.teacher.model.LearnMessage;
import com.xuexibao.teacher.model.LearnTalk;
import com.xuexibao.teacher.model.LearnTalkAllCount;
import com.xuexibao.teacher.model.LearnTalkCount;
import com.xuexibao.teacher.model.LearnTalkTeacher;
import com.xuexibao.teacher.model.Question;
import com.xuexibao.teacher.model.Wb2videoProcess;
import com.xuexibao.teacher.model.vo.AudioVO;
import com.xuexibao.teacher.model.vo.LearnMessageVO;
import com.xuexibao.teacher.service.FeudDetailWbService;
import com.xuexibao.teacher.service.LearnTalkMessageService;
import com.xuexibao.teacher.service.Wb2videoProcessService;
import com.xuexibao.teacher.util.FileUtil;
import com.xuexibao.teacher.validator.LearnTalkValidator;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("learnTalk")
public class LearnTalkController extends AbstractController {
	@Resource
	private LearnTalkMessageService learnTalkMessageService;
	@Resource
	private FeudDetailWbService feudDetailWbService;
	@Resource
	private Wb2videoProcessService wb2videoProcessService;

	private static Logger logger = LoggerFactory.getLogger(LearnTalkController.class);

	/**
	 * 获取教师与学生对某个资源互动的未读消息数
	 */
	@ResponseBody
	@RequestMapping(value = "learnTalkCount")
	public Object countTeacherUnReadMessage(String studentId, String teacherOfferId) {
		Validator.validateBlank(studentId, "studentId不能为空");
		Validator.validateBlank(teacherOfferId, "teacherOfferId不能为空");

		String teacherId = AppContext.getTeacherId();

		LearnTalkCount count = learnTalkMessageService.countTeacherUnReadMessage(studentId, teacherId, teacherOfferId);

		return dataJson(count);
	}

	/**
	 * 获取教师与学生对多个资源互动的未读消息数
	 */
	@ResponseBody
	@RequestMapping(value = "learnTalkAllCount")
	public Object countAllTeacherUnReadMessage(String studentId, String teacherOfferId) throws Exception {
		Validator.validateBlank(studentId, "studentId不能为空");
		Validator.validateBlank(teacherOfferId, "teacherOfferId不能为空");

		String teacherId = AppContext.getTeacherId();

		List<LearnTalkAllCount> result = new ArrayList<LearnTalkAllCount>();

		String[] teacher_offer_ids = teacherOfferId.split(",");
		for (String offerId : teacher_offer_ids) {
			LearnTalkCount count = learnTalkMessageService.countTeacherUnReadMessage(studentId, teacherId, offerId);

			LearnTalkAllCount item = new LearnTalkAllCount();
			item.setNumber(count.getNumber());
			item.setTeacherOfferId(offerId);

			result.add(item);
		}

		return dataJson(result);
	}

	/**
	 * 返回与该教师互动的最近10个学生的记录
	 */
	@ResponseBody
	@RequestMapping(value = "learnTalkList")
	public Object learnTalkList(Integer pageNo) throws Exception {
		Validator.validateBlank(pageNo, " pageNo不能为空");
		String teacherId = AppContext.getTeacherId();

		List<LearnTalk> learnTalkList = learnTalkMessageService.learnTalkList(teacherId, pageNo);

		return dataJson(learnTalkList);
	}

	/**
	 * 学生端互动学习消息列表
	 */
	@ResponseBody
	@RequestMapping(value = "learnTalkStudentList")
	public Object learnTalkStudentList(String studentId, Integer pageNo) throws Exception {
		Validator.validateBlank(studentId, "studentId 不能为空");
		Validator.validateBlank(pageNo, " pageNo不能为空");

		// Validator.validateContainsEnum(LearnMessage.DIALOG_TYPES, dialogType,
		// "dialogType类型不对");

		List<LearnTalkTeacher> learnTalkList = learnTalkMessageService.learnTalkStudentList(studentId, pageNo);

		return dataStudentJson(learnTalkList);
	}

	/**
	 * 互动学习消息详情
	 */
	@ResponseBody
	@RequestMapping(value = "learnTalkDetail")
	public Object learnTalkDetail(String studentId, String teacherOfferId, Integer teacherOfferType, Integer dialogType,
			Integer type, Long timestamp, Integer pageZize) {
//		Validator.validateContainsEnum(LearnMessage.DIALOG_TYPES, dialogType, "dialogType类型不对");

		String validateStr = LearnTalkValidator.validateLearnTalkDetail(studentId, teacherOfferId, teacherOfferType,
				type);

		if (validateStr != null) {
			return errorJson(validateStr);
		}

		if (timestamp == null) {
			timestamp = System.currentTimeMillis();
		}

		String teacher_id = AppContext.getTeacherId();

		Map<String, Object> learnTalkList = null;

		if (type == 1) {
			learnTalkList = learnTalkMessageService.learnTalkStudentRecentMessage(teacher_id, studentId, teacherOfferId,
					teacherOfferType, dialogType, timestamp);
		} else if (type == 2) {
			learnTalkList = learnTalkMessageService.learnTalkDetail(teacher_id, studentId, teacherOfferId,
					teacherOfferType, dialogType, timestamp, pageZize);
		}

		return dataJson(learnTalkList);
	}

	/**
	 * 互动学习消息详情
	 */
	@ResponseBody
	@RequestMapping(value = "learnTalkStudentDetail")
	public Object learnTalkStudentDetail(String teacherId, String studentId, String teacherOfferId,
			Integer teacherOfferType, Integer type, Long timestamp, Integer pageSize, Integer dialogType)
					throws Exception {
		if (!LearnMessage.DIALOG_TYPES.contains(dialogType)) {
			dialogType=LearnMessage.DIALOG_TYPE_PAITI;
		}
		
		String validateStr = LearnTalkValidator.validateLearnTalkDetail(studentId, teacherOfferId, teacherOfferType,
				type);
		if (validateStr != null) {
			return errorStudentJson(validateStr);
		}

		try {
			if (null == timestamp) {
				timestamp = System.currentTimeMillis();
			}
			Map<String, Object> learnTalkList = null;
			if (type == 1) {
				learnTalkList = learnTalkMessageService.learnTalkStudentRecentMessage(teacherId, studentId,
						teacherOfferId, teacherOfferType, dialogType, timestamp);
			} else if (type == 2) {
				learnTalkList = learnTalkMessageService.learnTalkDetail(teacherId, studentId, teacherOfferId,
						teacherOfferType, dialogType, timestamp, pageSize);
			}

			return dataStudentJson(learnTalkList);
		} catch (Exception e) {
			logger.error("获取互动学习消息详情失败.", e);
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	/**
	 * 更新互动学习消息数量
	 */
	@ResponseBody
	@RequestMapping(value = "learnTalkUpdate")
	public Object learnTalkUpdate(String studentId, String teacherOfferId, Integer teacherOfferType) throws Exception {
		String validateStr = LearnTalkValidator.validateLearnTalkUpdate(studentId, teacherOfferId, teacherOfferType);

		if (validateStr != null) {
			return errorJson(validateStr);
		}
		String teacherId = AppContext.getTeacherId();
		int count = learnTalkMessageService.learnTalkUpdate(studentId, teacherId, teacherOfferId, teacherOfferType);

		return dataJson(count);
	}

	/**
	 * 更新互动学习消息数量
	 */
	@ResponseBody
	@RequestMapping(value = "learnTalkStudentUpdate")
	public Object learnTalkStudentUpdate(String teacherId, String studentId, String teacherOfferId,
			Integer teacherOfferType) throws Exception {
		String validateStr = LearnTalkValidator.validateLearnTalkStudentUpdate(teacherId, studentId, teacherOfferId,
				teacherOfferType);

		if (validateStr != null) {
			return errorStudentJson(validateStr);
		}

		try {
			int count = learnTalkMessageService.learnTalkStudentUpdate(studentId, teacherId, teacherOfferId,
					teacherOfferType);

			return dataStudentJson(count);
		} catch (Exception e) {
			logger.error("获取互动学习消息列表出错.", e);
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	/**
	 * 互动学习老师发送消息
	 * 
	 * @param request
	 * @param sendToTeacherId
	 *            老师ID
	 * @param studentId
	 *            学生ID
	 * @param teacherOfferId
	 * @param teacherOfferType
	 * @param msg
	 * @param img
	 * @return
	 * @throws Exception
	 */
	// @ResponseBody
	// @RequestMapping(value = "studentSendMsg")
	// public Object studentSendMsg(String studentId, String teacherOfferId,
	// Integer teacherOfferType, String teacherOfferDesc, String msg,
	// String img, String imageId,String dialogType) throws Exception {
	// String validateStr = LearnTalkValidator.validateStudentSendMsg(studentId,
	// teacherOfferId, teacherOfferDesc, teacherOfferType, msg,
	// img);
	// if (validateStr != null) {
	// return errorJson(validateStr);
	// }
	// String teacherId = AppContext.getTeacherId();
	//
	// LearnMessageVO learnMessage =
	// learnTalkMessageService.insertStudentSendMsg(teacherId, studentId,
	// teacherOfferId, teacherOfferType,
	// teacherOfferDesc, msg, img, imageId);
	//
	// learnTalkMessageService.updateStudentQuestionHuDongCache(studentId,
	// teacherOfferId);
	//
	// return dataJson(learnMessage);
	// }

	@ResponseBody
	@RequestMapping(value = "studentSendMsgWithoutToken")
	public Object studentSendMsgWithoutToken(LearnMessage message) throws Exception {
		LearnTalkValidator.validateStudentSendMsgWithoutToken(message);

		LearnMessageVO learnMessage = learnTalkMessageService.insertStudentSendMsg(message);

		learnTalkMessageService.updateStudentQuestionHuDongCache(message.getStudentId(), message.getTeacherOfferId());

		return dataStudentJson(learnMessage);
	}

	@ResponseBody
	@RequestMapping(value = "learnTalkRecentMessage")
	public Object learnTalkRecentMessage(String studentId, String teacherOfferId, Integer teacherOfferType,
			Integer dialogType, Long timestamp) throws Exception {
		if (!LearnMessage.DIALOG_TYPES.contains(dialogType)) {
			dialogType=LearnMessage.DIALOG_TYPE_PAITI;
		}

		String validateStr = LearnTalkValidator.validateLearnTalkRecentMessage(studentId, teacherOfferId,
				teacherOfferType, timestamp);
		if (validateStr != null) {
			return errorJson(validateStr);
		}

		String teacher_id = AppContext.getTeacherId();
		Map<String, Object> learnTalkList = learnTalkMessageService.learnTalkStudentRecentMessage(teacher_id, studentId,
				teacherOfferId, teacherOfferType, dialogType, timestamp);

		return dataJson(learnTalkList);
	}

	@ResponseBody
	@RequestMapping(value = "learnTalkStudentRecentMessage")
	public Object learnTalkStudentRecentMessage(String teacherId, String studentId, String teacherOfferId,
			Integer teacherOfferType, Integer dialogType, Long timestamp) throws Exception {
		if (!LearnMessage.DIALOG_TYPES.contains(dialogType)) {
			dialogType=LearnMessage.DIALOG_TYPE_PAITI;
		}
		
		String validateStr = LearnTalkValidator.validateLearnTalkRecentMessage(studentId, teacherOfferId,
				teacherOfferType, timestamp);
		if (validateStr != null) {
			return errorStudentJson(validateStr);
		}

		try {
			Map<String, Object> learnTalkList = learnTalkMessageService.learnTalkStudentRecentMessage(teacherId,
					studentId, teacherOfferId, teacherOfferType, dialogType, timestamp);

			return dataStudentJson(learnTalkList);
		} catch (Exception e) {
			logger.error("获取互动学习最新消息失败.", e);
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	/**
	 * 
	 * 互动学习老师发送消息
	 * 
	 * @param request
	 * @param teacherOfferId
	 * @param teacherOfferType
	 * @param sendToStudentId
	 * @param msg
	 * @param img
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "teacherSendMsg")
	public Object teacherSendMsg(String teacherOfferId, Integer teacherOfferType, String teacherOfferDesc,
			String sendToStudentId,Integer dialogType, String msg, String img) throws Exception {
		String validateStr = LearnTalkValidator.validateTeacherSendMsg(teacherOfferId, sendToStudentId,
				teacherOfferType, msg, img);
		if(dialogType==null){
			dialogType=LearnMessage.DIALOG_TYPE_PAITI;
		}
		if (validateStr != null) {
			return errorJson(validateStr);
		}

		String teacherId = AppContext.getTeacherId();
		LearnMessageVO learnMessage = learnTalkMessageService.insertTeacherSendMsg(teacherId, teacherOfferId,
				teacherOfferType, teacherOfferDesc,dialogType, sendToStudentId, msg, img);

		return dataJson(learnMessage);
	}

	@ResponseBody
	@RequestMapping(value = "upload")
	public Object upload(MultipartFile file) throws Exception {
		Validator.validateFile(file, "文件不能为空.");
		try {
			String uri = FileUtil.upload(file, file.getOriginalFilename());

			return dataJson(uri);
		} catch (Exception e) {
			logger.error("互动学习学生发送消息失败.", e);
			return errorJson("服务器异常，请重试.");
		}
	}

	@ResponseBody
	@RequestMapping(value = "getAudioContent")
	public Object getAudioContent(HttpServletRequest request, String audioId) throws Exception {
		Validator.validateBlank(audioId, " audioId不能为空");
		try {
			Audio audio = learnTalkMessageService.getAudio(audioId);
			if (null == audio) {
				return errorJson("你指定的音频不存在!", request);
			}

			Question question = learnTalkMessageService.queryByRealId(audio.getQuestionId());

			return dataJson(question, request);
		} catch (Exception e) {
			logger.error("互动学习学生发送消息失败.", e);
			return errorJson("服务器异常，请重试.", request);
		}
	}

	@ResponseBody
	@RequestMapping(value = "getQuestionId")
	public Object getQuestionId(String audioId) throws Exception {
		Validator.validateBlank(audioId, " audioId不能为空");
		try {
			AudioVO audioVO = learnTalkMessageService.getQuestionId(audioId);
			if (null == audioVO) {
				return errorJson("你指定的音频不存在!");
			}

			return dataJson(audioVO);
		} catch (Exception e) {
			logger.error("互动学习查询音频失败.", e);
			return errorJson("服务器异常，请重试.");
		}
	}

	@ResponseBody
	@RequestMapping(value = "getQuestionIdForStudent")
	public Object getQuestionIdForStuednt(String audioId) throws Exception {
		Validator.validateBlank(audioId, " audioId不能为空");
		try {
			AudioVO audioVO = learnTalkMessageService.getQuestionId(audioId);

			if (null == audioVO) {
				return errorStudentJson("你指定的音频不存在!");
			}

			return dataStudentJson(audioVO);
		} catch (Exception e) {
			logger.error("互动学习查询音频失败.", e);
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	@ResponseBody
	@RequestMapping(value = "queryWbDownloadUrls")
	public Object queryWbDownloadUrls(String wbId) throws Exception {
		Validator.validateBlank(wbId, " wbId不能为空");
		try {

			List<FeudDetailWb> fdwbs = feudDetailWbService.queryWbDownloadUrls(wbId);

			if (CollectionUtils.isEmpty(fdwbs)) {
				return errorJson("你指定的白板不存在!");
			}
			FeudAnswerDetail feudAnswerDetail = learnTalkMessageService.getFeudAnswerDetailByWhiteBoardId(wbId);
			if (null != feudAnswerDetail) {
				for (FeudDetailWb fdwb : fdwbs) {
					fdwb.setQuestRealId(feudAnswerDetail.getQuestRealId());
				}
			} else {
				logger.error("查询白板ID对应的问题ID为空，请排查数据库。");
			}
			return dataJson(fdwbs);

		} catch (Exception e) {
			logger.error("互动学习查询白板下载地址失败.", e);
			return errorJson("服务器异常，请重试.");
		}
	}

	@ResponseBody
	@RequestMapping(value = "queryVideoDownloadUrl")
	public Object queryVideoDownloadUrl(String wbId) throws Exception {
		Validator.validateBlank(wbId, " wbId不能为空");

		Wb2videoProcess fdwbs = wb2videoProcessService.selectByWbId(wbId);

		if (fdwbs != null && fdwbs.getStatus() == Wb2videoProcessStatus.processOk.getValue()) {

			return dataStudentJson(fdwbs);
		}

		return dataStudentJson(null);
	}

	@ResponseBody
	@RequestMapping(value = "queryWbDownloadUrlsForStudent")
	public Object queryWbDownloadUrlsForStudent(String wbId) throws Exception {
		Validator.validateBlank(wbId, " wbId不能为空");
		try {

			List<FeudDetailWb> fdwbs = feudDetailWbService.queryWbDownloadUrls(wbId);

			if (CollectionUtils.isEmpty(fdwbs)) {
				return errorStudentJson("你指定的白板不存在!");
			}
			FeudAnswerDetail feudAnswerDetail = learnTalkMessageService.getFeudAnswerDetailByWhiteBoardId(wbId);
			if (null != feudAnswerDetail) {
				for (FeudDetailWb fdwb : fdwbs) {
					fdwb.setQuestRealId(feudAnswerDetail.getQuestRealId());
				}
			} else {
				logger.error("查询白板ID对应的问题ID为空，请排查数据库。");
			}
			return dataStudentJson(fdwbs);

		} catch (Exception e) {
			logger.error("互动学习查询白板下载地址失败.", e);
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	@ResponseBody
	@RequestMapping(value = "queryIsHudong")
	public Object queryIsHudong(String userId, String questionIds) throws Exception {
		Validator.validateBlank(questionIds, " questionIds不能为空");
		Validator.validateBlank(userId, " userId不能为空");

		try {
			Map<Long, Integer> resultMap = learnTalkMessageService.queryIsHudong(userId, questionIds);

			return dataStudentJson(resultMap);
		} catch (Exception e) {
			logger.error("根据题目ID[" + questionIds + "]查询互动消息失败.", e);
			return errorStudentJson("服务器异常，请重试.");
		}
	}
}