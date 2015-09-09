package com.xuexibao.teacher.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.constant.AppVersion;
import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.Grade;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.vo.RecordedVO;
import com.xuexibao.teacher.service.TaskService;
import com.xuexibao.teacher.service.TeacherService;
import com.xuexibao.teacher.util.BusinessConstant;
import com.xuexibao.teacher.util.RedisUtil;
import com.xuexibao.teacher.validator.Validator;

/**
 * 
 * @author oldlu
 * 
 */
@Controller
@RequestMapping("task")
public class TaskController extends AbstractController {
	@Resource
	private TaskService taskService;
	@Resource
	private TeacherService teacherService;
	@Resource
	private RedisUtil redisUtil;

	/**
	 * 获取任务列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTaskList")
	public Object getTaskList(String version) {
		String teacherId = AppContext.getTeacherId();

		List<RecordedVO> list = taskService.getTaskList(teacherId);

		if (CollectionUtils.isEmpty(list) || list.size() < BusinessConstant.AUDIO_BATCH_LIMIT) {
			return filterNewTaskList(Grade.FILTER_ALL, Grade.FILTER_ALL);
		}

		Teacher teacher = teacherService.getRequiredTeacher(teacherId);

		Map<String, Object> result = taskService.getTaskSummary(teacherId);
		result.put("list", list);
		result.put("planType", teacher.getPlanType());
		result.put("count", taskService.countTaskAudio(teacherId));
		result.put("isCompleteNewTask", teacher.getCompleteNewUserTask());

		return dataJson(result);
	}

	/**
	 * 刷新新题
	 * 
	 * @param gradeId
	 * @param subjectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "filterNewTaskList")
	public Object filterNewTaskList(Integer gradeId, Integer subjectId) {
		String teacherId = AppContext.getTeacherId();
		List<String> gradeList = null;
		List<Integer> subjectIdList = null;

		Teacher teacher = teacherService.getRequiredTeacher(teacherId);
		if (gradeId == null) {
			gradeList = redisUtil.getGradeNameListByIds(teacher.getGradeIds());
		} else {
			gradeList = redisUtil.getGradeNameListByIds(Arrays.asList(gradeId));
		}
		if (gradeId == Grade.FILTER_ALL || CollectionUtils.isEmpty(gradeList)) {
			gradeList = Grade.LIST_GRADE_ALL;
		}

		if (subjectId == null) {
			subjectIdList = teacher.getSubjectIds();
		} else {
			subjectIdList = Arrays.asList(subjectId);
		}
		if (subjectId == Grade.FILTER_ALL || CollectionUtils.isEmpty(subjectIdList)) {
			subjectIdList = Grade.LIST_SUBJECT_ALL;
		}

		taskService.filterNewTaskList(teacherId, gradeList, subjectIdList);

		List<RecordedVO> list = taskService.getTaskList(teacherId);

		Map<String, Object> result = taskService.getTaskSummary(teacherId);
		result.put("list", list);
		result.put("isCompleteNewTask", teacher.getCompleteNewUserTask());
		result.put("count", taskService.countTaskAudio(teacherId));
		result.put("planType", teacher.getPlanType());
		
		return dataJson(result);
	}

	/**
	 * 提交音频
	 */
	@ResponseBody
	@RequestMapping(value = "submitAudio")
	public Object submitAudio(Audio audio) {
		Validator.validateLtZero(audio.getQuestionId(), "题目编号不能为空.");
		Validator.validateLtZero(audio.getDuration(), "录音长度错误.");
		Validator.validateFile(audio.getFile(), "录音文件不能为空.");

		taskService.submitAudio(audio, AppContext.getTeacherId());

		return dataJson(true);
	}

	/**
	 * 获取已录制
	 * 
	 * @param pageNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getRecordedList")
	public Object getRecordedList(Integer pageNo, String version) {
		if (pageNo == null) {
			pageNo = 0;
		}
		String teacherId = AppContext.getTeacherId();

		List<RecordedVO> list = taskService.getRecordedList(teacherId, pageNo);
		if (AppVersion.isVersion13(version)) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("list", list);
			result.put("count", taskService.countTaskAudio(teacherId));

			return dataJson(result);
		}
		return dataJson(list);
	}

	/**
	 * 获取录制
	 */
	@ResponseBody
	@RequestMapping(value = "getQuestDetail")
	public Object getQuestDetail(Long questionId) {
		Validator.validateBlank(questionId, "问题ID不能为空.");
		String teacherId = AppContext.getTeacherId();

		return dataJson(taskService.getQuestDetail(teacherId, questionId));
	}
}
