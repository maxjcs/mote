package com.xuexibao.teacher.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.dao.TeacherMessageDao;
import com.xuexibao.teacher.model.TeacherMessage;
import com.xuexibao.teacher.service.TeacherMessageService;
import com.xuexibao.teacher.validator.Validator;
import com.xuexibao.webapi.student.client.T_UserService;
import com.xuexibao.webapi.student.model.User;

@Controller
@RequestMapping("teacherMessage")
public class TeacherMessageController extends AbstractController {
	private static Logger logger = LoggerFactory.getLogger(TeacherMessageController.class);

	@Resource
	private TeacherMessageService teacherMessageService;
	@Resource
	private TeacherMessageDao teacherMessageDao;
	@Resource
	private T_UserService t_UserService;

	@ResponseBody
	@RequestMapping(value = "addTeacherMessageByTeacher")
	public Object addTeacherMessageByTeacher(String content, Long replyId) throws Exception {
		Validator.validateBlank(content, "content不能为空");
		if (content.length() > 256) {
			return errorJson("输入字符长度不能超过256.");
		}

		String teacherId = AppContext.getTeacherId();
		TeacherMessage result = teacherMessageService.addTeacherMessage(teacherId, null, replyId, content,
				TeacherMessage.TEACHER_SEND);
		// redis中留言数+1
		teacherMessageService.addMessageNumRedis(teacherId);
		return dataJson(result);
	}

	@ResponseBody
	@RequestMapping(value = "addTeacherMessageForStudent")
	public Object addTeacherMessageForStudent(String teacherId, String studentId, String content, Long replyId)
			throws Exception {
		try {
			Validator.validateBlank(studentId, "studentId不能为空");
			Validator.validateBlank(teacherId, "teacherId不能为空");
			Validator.validateBlank(content, "content不能为空");
			if (content.length() > 256) {
				return errorStudentJson("输入字符长度不能超过256.");
			}
			User user=t_UserService.getUserById(studentId);
			if (null == user) {
				return errorStudentJson("输入学生ID不存在.");
			}

			TeacherMessage result = teacherMessageService.addTeacherMessage(teacherId, studentId, replyId, content,
					TeacherMessage.STUDENT_SEND);

			// redis中留言数+1
			teacherMessageService.addMessageNumRedis(teacherId);

			return dataStudentJson(result);
		} catch (Exception e) {
			logger.error("插入学生留言出错.", e);
			return errorStudentJson(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "summaryTeacherMessage")
	public Object summaryTeacherMessage() throws Exception {
		String teacherId = AppContext.getTeacherId();

		Map<String, Object> result = teacherMessageService.summaryTeacherMessage(teacherId, null);

		return dataJson(result);
	}

	@ResponseBody
	@RequestMapping(value = "summaryTeacherMessageForH5")
	public Object summaryTeacherMessageForH5(String teacherId) throws Exception {
		Validator.validateBlank(teacherId, "教师ID不能为空");

		Map<String, Object> result = teacherMessageService.summaryTeacherMessage(teacherId, null);

		return dataJson(result);
	}

	@ResponseBody
	@RequestMapping(value = "summaryTeacherMessageForStudent")
	public Object summaryTeacherMessageForStudent(String teacherId, String studentId) throws Exception {
		try {
			Validator.validateBlank(teacherId, "teacherId不能为空");
			Validator.validateBlank(studentId, "studentId不能为空");

			Map<String, Object> result = teacherMessageService.summaryTeacherMessage(teacherId, studentId);

			return dataStudentJson(result);
		} catch (Exception e) {
			return errorStudentJson(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "firstPageMessageForTeacher")
	public Object firstPageMessageForTeacher() throws Exception {
		String teacherId = AppContext.getTeacherId();

		Map<String, Object> result = teacherMessageService.firstPageMessageForTeacher(teacherId, "");

		return dataJson(result);
	}

	@ResponseBody
	@RequestMapping(value = "firstPageTeacherMessageForStudent")
	public Object firstPageTeacherMessageForStudent(String teacherId, String studentId) throws Exception {
		try {
			Validator.validateBlank(teacherId, "teacherId不能为空");
			Validator.validateBlank(studentId, "studentId不能为空");

			Map<String, Object> result = teacherMessageService.firstPageMessageForTeacher(teacherId, studentId);

			return dataStudentJson(result);
		} catch (Exception e) {
			return errorStudentJson(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "nextPageReplyMessageForTeacher")
	public Object nextPageReplyMessageForTeacher(String studentId, Long replyId, Long firstIdOfLastPage)
			throws Exception {
		String teacherId = AppContext.getTeacherId();
		Validator.validateBlank(replyId, "replyId不能为空");
		Validator.validateBlank(firstIdOfLastPage, "上一页第一条ID不能为空");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("firstIdOfLastPage", firstIdOfLastPage);
		paramMap.put("replyId", replyId);
		paramMap.put("teacherId", teacherId);
		paramMap.put("limit", PageConstant.PAGE_SIZE_10);

		List<TeacherMessage> result = teacherMessageService.nextPageReplyMessage(paramMap, teacherId, studentId);

		return dataJson(result);
	}

	@ResponseBody
	@RequestMapping(value = "nextPageReplyMessageForStudent")
	public Object nextPageReplyMessageForStudent(String teacherId, String studentId, Long replyId,
			Long firstIdOfLastPage) throws Exception {
		try {
			Validator.validateBlank(teacherId, "teacherId不能为空");
			Validator.validateBlank(replyId, "replyId不能为空");
			Validator.validateBlank(firstIdOfLastPage, "上一页第一条ID不能为空");

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("firstIdOfLastPage", firstIdOfLastPage);
			paramMap.put("replyId", replyId);
			paramMap.put("teacherId", teacherId);
			paramMap.put("limit", PageConstant.PAGE_SIZE_10);

			List<TeacherMessage> result = teacherMessageService.nextPageReplyMessage(paramMap, teacherId, studentId);

			return dataStudentJson(result);
		} catch (Exception e) {
			return dataStudentJson(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "firstPageReplyMessageForTeacher")
	public Object firstPageReplyMessageForTeacher(Long replyId) throws Exception {
		Validator.validateBlank(replyId, "replyId不能为空");
		String teacherId = AppContext.getTeacherId();

		Map<String, Object> result = teacherMessageService.firstPageReplyMessageForTeacher(teacherId, "", replyId);

		return dataJson(result);
	}

	@ResponseBody
	@RequestMapping(value = "firstPageReplyMessageForStudent")
	public Object firstPageReplyMessageForStudent(String teacherId, String studentId, Long replyId) throws Exception {
		try {
			Validator.validateBlank(replyId, "replyId不能为空");
			Validator.validateBlank(studentId, "studentId不能为空");

			Map<String, Object> result = teacherMessageService.firstPageReplyMessageForTeacher(teacherId, studentId,
					replyId);

			return dataStudentJson(result);
		} catch (Exception e) {
			return dataStudentJson(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "nextPageMessageForTeacher")
	public Object nextPageMessageForTeacher(Long firstIdOfLastPage, String studentId) throws Exception {
		Validator.validateBlank(firstIdOfLastPage, "上一页第一条ID不能为空");
		String teacherId = AppContext.getTeacherId();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("firstIdOfLastPage", firstIdOfLastPage);
		paramMap.put("teacherId", teacherId);
		paramMap.put("limit", PageConstant.PAGE_SIZE_10);

		List<TeacherMessage> result = teacherMessageService.nextPageTeacherMessage(paramMap, teacherId, studentId);

		return dataJson(result);
	}

	@ResponseBody
	@RequestMapping(value = "nextPageTeacherMessageForStudent")
	public Object nextPageTeacherMessageForStudent(String teacherId, String studentId, Long firstIdOfLastPage)
			throws Exception {
		try {
			Validator.validateBlank(teacherId, "teacherId不能为空");
			Validator.validateBlank(studentId, "studentId不能为空");
			Validator.validateBlank(firstIdOfLastPage, "上一页第一条ID不能为空");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("firstIdOfLastPage", firstIdOfLastPage);
			paramMap.put("teacherId", teacherId);
			paramMap.put("studentId", studentId);
			paramMap.put("limit", PageConstant.PAGE_SIZE_10);

			List<TeacherMessage> result = teacherMessageService.nextPageTeacherMessage(paramMap, teacherId, studentId);

			return dataStudentJson(result);
		} catch (Exception e) {
			return errorStudentJson(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "removeTeacherMessage")
	public Object removeTeacherMessage(Long messageId) throws Exception {
		Validator.validateBlank(messageId, "messgseId不能为空");

		teacherMessageService.removeTeacherMessage(AppContext.getTeacherId(), messageId);
		// redis中留言数-1
		teacherMessageService.removeMessageNumRedis(AppContext.getTeacherId());

		return dataJson(true);
	}

	@ResponseBody
	@RequestMapping(value = "removeStudentMessage")
	public Object removeTeacherMessageForStudent(Long messageId, String studentId) throws Exception {
		try {
			Validator.validateBlank(messageId, "messageId不能为空");
			Validator.validateBlank(studentId, "studentId不能为空");

			teacherMessageService.removeStudentMessage(studentId, messageId);
			// redis中留言数-1
			TeacherMessage teacherMessage=teacherMessageDao.getMessageById(messageId);
			teacherMessageService.removeMessageNumRedis(teacherMessage.getTeacherId());
			 
			return dataStudentJson(true);
		} catch (Exception e) {
			logger.error("删除学生留言出错.", e);
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	/**
	 * 插入学生留言的接口
	 */
	@ResponseBody
	@RequestMapping(value = "getTeacherMessage")
	public Object getTeacherMessage(Integer pageNo) throws Exception {
		Validator.validateBlank(pageNo, "pageNo不能为空");

		String teacherId = AppContext.getTeacherId();

		Map<String, Object> resultMap = teacherMessageService.getTeacherMessage(teacherId, pageNo);

		return dataJson(resultMap);
	}

	/**
	 * 插入学生留言的接口
	 */
	@ResponseBody
	@RequestMapping(value = "getTeacherMessageForH5")
	public Object getTeacherMessageForH5(String teacherId, Integer pageNo) throws Exception {
		Validator.validateBlank(pageNo, "pageNo不能为空");
		Validator.validateBlank(teacherId, "teacherId不能为空");

		Map<String, Object> resultMap = teacherMessageService.getTeacherMessage(teacherId, pageNo);

		return dataJson(resultMap);
	}

	@ResponseBody
	@RequestMapping(value = "getTeacherMessageForStudent")
	public Object getTeacherMessageForStudent(HttpServletRequest request, String teacherId, Integer pageNo)
			throws Exception {
		try {
			Validator.validateBlank(pageNo, "pageNo不能为空");
			Validator.validateBlank(teacherId, "teacherId不能为空");

			Map<String, Object> resultMap = teacherMessageService.getTeacherMessage(teacherId, pageNo);

			return dataStudentJson(resultMap);
		} catch (Exception e) {
			logger.error("获取学生留言出错.", e);
			return errorStudentJson("服务器异常，请重试.");
		}
	}

}
