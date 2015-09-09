package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xuexibao.teacher.dao.TeacherDao;
import com.xuexibao.teacher.dao.TeacherMessageDao;
import com.xuexibao.teacher.enums.TeacherIdentify;
import com.xuexibao.teacher.model.Rating;
import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class HomeService {
	@Resource
	private PointOfflineService pointOfflineService;
	@Resource
	private TeacherDao teacherDao;
	@Resource
	private EvaluationService evaluationService;
	@Resource
	private RatingService ratingService;
	@Resource
	private StudentApiService studentApiService;
	@Resource
	private CommonFeudService commonFeudService;
	@Resource
	private TeacherService teacherService;
	@Resource
	private LearnTalkMessageService learnTalkMessageService;
	@Resource
	private FeudPointFeeConfService reudPointFeeConfService;
	@Resource
	private TeacherFunctionService teacherFunctionService;
	@Resource
	private AudioSetService audioSetService;
	@Resource
	private RattingApplyService rattingApplyService;
	@Resource
	private TeacherMessageDao teacherMessageDao;
	
	public List<Object> getPointRuleList(Integer teacherIdentify) {
		List<Object> result = new ArrayList<Object>();

		Map<String, Object> rule1 = new HashMap<String, Object>();
		String name = "录题任务\n\n请求讲解";
		if (teacherIdentify == TeacherIdentify.teacher.getValue()) {
			name += "\n\n拍题";
		}
		rule1.put("name", name);
		rule1.put("items", evaluationService.listEvaluationByIdentify(teacherIdentify));
		result.add(rule1);

		Map<String, Object> rule2 = new HashMap<String, Object>();
		rule2.put("name", "抢答");
		rule2.put("items", reudPointFeeConfService.getPointByTeacherIdentify(teacherIdentify));
		result.add(rule2);

		return result;
	}

	public Object getPoint(String teacherId) {
		StarPoint starPoint = teacherService.getStarPointByTeacherId(teacherId);

		Map<String, Object> result = new HashMap<String, Object>();

		if (starPoint != null) {
			int curStar = starPoint.getStar();
			Rating nextRating = ratingService.nextRating(curStar);

			result.put("point", starPoint.getPoint());
			result.put("curStar", curStar);
			if (nextRating != null) {
				result.put("nextStar", nextRating.getStar());
				result.put("leavePoint", nextRating.getPoints() - starPoint.getPoint());
			}
		}

		return result;
	}

	public Map<String, Object> getInfo(String teacherId) {
		// teacherService.refreshTeacherToRedis(teacherId);
		Teacher teacher = teacherDao.getRatingAndUndoTaskDaysByTeacherId(teacherId);
		StarPoint starPoint = teacherService.getStarPointByTeacherId(teacherId);

		Map<String, Object> result = teacherMessageDao.summaryTeacherMessage(teacherId);

		teacherService.appendTeacherInfo(result, teacherId);

		if (teacher != null) {
			result.put("days", teacher.getUndoTaskDays());
			result.put("star", starPoint.getStar());

			result.put("income", studentApiService.getTeacherBalance(teacherId, false));
			result.put("quests", commonFeudService.getFeudQuestCountForPageHome(teacherId));
			result.put("directQuests", commonFeudService.getDirectFeudWithTeacher(teacherId));

			result.put("countAudioSet", audioSetService.countAudioSet(teacherId));

			Teacher redisTeacher = teacherService.getRequiredTeacher(teacherId);
			result.put("nickname", redisTeacher.getWrapperNickname());
			result.put("avatarUrl", redisTeacher.getAvatarUrl());

			result.put("students", learnTalkMessageService.studentNumber(teacherId));

			result.put("isJoinCapacityTest", !redisTeacher.isJoinCapacityTest());

			result.put("functions", teacherFunctionService.selectByTeacherId(redisTeacher.getId()));
			result.put("teacherId", redisTeacher.getId());
			result.put("completeNewUserTask", redisTeacher.getCompleteNewUserTask());
			result.put("hasPmsFeud", starPoint.getStar() >= 3);
			result.put("hasRattingApply", rattingApplyService.getLastRattingApply(teacherId) != null);
			result.put("audioPrice", 1);

			result.put("isBindBank", redisTeacher.isBindBank());
			result.put("teacherIdentify", redisTeacher.getTeacherIdentify());
			// TODO 升星成功标记
			result.put("rattingFlag", redisTeacher.getTeacherIdentify());
		}

		return result;
	}
}
