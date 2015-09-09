package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.xuexibao.teacher.common.CommonService;
import com.xuexibao.teacher.constant.PushConstant;
import com.xuexibao.teacher.dao.AudioApproveDao;
import com.xuexibao.teacher.dao.AudioDao;
import com.xuexibao.teacher.dao.ErrorCorrectionDao;
import com.xuexibao.teacher.dao.TaskDao;
import com.xuexibao.teacher.dao.TeacherDao;
import com.xuexibao.teacher.enums.AudioSource;
import com.xuexibao.teacher.enums.AudioStatus;
import com.xuexibao.teacher.enums.AudioType;
import com.xuexibao.teacher.enums.PlanFactory;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioApprove;
import com.xuexibao.teacher.model.AudioDetail;
import com.xuexibao.teacher.model.AudioEvalApprove;
import com.xuexibao.teacher.model.ErrorCorrection;
import com.xuexibao.teacher.model.Evaluation;
import com.xuexibao.teacher.model.Grade;
import com.xuexibao.teacher.model.OrgQuest;
import com.xuexibao.teacher.model.Organization;
import com.xuexibao.teacher.model.Question;
import com.xuexibao.teacher.model.QuestionAllot;
import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.vo.PushMsgParamVO;
import com.xuexibao.teacher.model.vo.RecordedVO;
import com.xuexibao.teacher.service.evaluprocessor.EvaluProcessorManager;
import com.xuexibao.teacher.util.BusinessConstant;
import com.xuexibao.teacher.util.FileUtil;
import com.xuexibao.teacher.util.RedisUtil;

/**
 * 
 * @author oldlu
 * 
 */
@Service
@Transactional
public class TaskService {
	@Resource
	private AudioDao audioDao;
	@Resource
	private TaskDao taskDao;
	@Resource
	private QuestionService questionService;
	@Resource
	private TeacherService teacherService;
	@Resource
	private TeacherDao teacherDao;
	@Resource
	private EvaluProcessorManager evaluProcessorManager;
	@Resource
	private AudioApproveDao audioApproveDao;
	@Resource
	private EvaluationService evaluationService;
	@Resource
	private StudentApiService studentApiService;
	@Resource
	private PushMsgService pushMsgService;
	@Resource
	private CommonConfigService commonConfigService;
	@Resource
	private RedisUtil redisUtil;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private ErrorCorrectionDao errorCorrectionDao;
	@Resource
	private CommonService commonService;
	@Resource
	private AudioStudentEvaluationService audioStudentEvaluationService;
	@Resource
	private AudioEvalApproveService audioEvalApproveService;

	public List<RecordedVO> getTaskList(String teacherId) {
		Teacher teacher = teacherService.getRequiredTeacher(teacherId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);
		paramMap.put("planType", teacher.getPlanType());
		paramMap.put("orgId", teacher.getCurOrgId());

		return taskDao.getTaskList(paramMap);
	}

	public Map<String, Object> getTaskSummary(String teacherId) {
		Map<String, Object> result = taskDao.getTaskSummary(teacherId);
		if (result == null) {
			return new HashMap<String, Object>();
		}
		// result.put("undocount", BusinessConstant.AUDIO_BATCH_LIMIT);
		return result;
	}

	private long deleteOldTaskList(Teacher teacher) {
		String teacherId = teacher.getId();
		List<QuestionAllot> allots = taskDao.listAllotQuest(teacherId);

		if (!CollectionUtils.isEmpty(allots)) {
			long orgId = 0;
			List<Long> orgQuestids = new ArrayList<Long>();
			List<Long> questids = new ArrayList<Long>();
			long maxQuestId = 0;
			for (QuestionAllot allot : allots) {
				if (allot.getQuestionId() > maxQuestId) {
					maxQuestId = allot.getQuestionId();
				}
				if (allot.getOrgId() > 0) {
					orgId = allot.getOrgId();
					orgQuestids.add(allot.getQuestionId());
				} else {
					questids.add(allot.getQuestionId());
				}
			}

			if (!CollectionUtils.isEmpty(orgQuestids)) {// 恢复机构题库状态
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("questids", orgQuestids);
				paramMap.put("orgId", orgId);
				paramMap.put("status", OrgQuest.STATUS_FREE);

				taskDao.updateOrgQuestAllot(paramMap);
			}

			if (!CollectionUtils.isEmpty(questids)) {// 恢复普通题库状态
				taskDao.releaseQuestionAllotStatus(questids);
			}

			taskDao.deleteOldTaskLsit(teacherId);// 删除教师锁定题目

			return maxQuestId;
		}
		return 0;
	}

	private int addOrgNewTaskList(Map<String, Object> paramMap, String teacherId) {
		List<QuestionAllot> questAllotList = taskDao.fetchOrgNewTaskList(paramMap);
		if (!CollectionUtils.isEmpty(questAllotList)) {
			taskDao.addNewTaskList(questAllotList);
			List<Long> questids = new ArrayList<Long>();
			for (QuestionAllot allot : questAllotList) {
				questids.add(allot.getQuestionId());
			}

			paramMap.put("questids", questids);
			paramMap.put("status", OrgQuest.STATUS_LOCK);
			taskDao.updateOrgQuestAllot(paramMap);
			return questAllotList.size();
		}
		return 0;
	}

	private int addNewTaskList(Map<String, Object> paramMap, String teacherId) {
		List<QuestionAllot> questAllotList = taskDao.fetchNewTaskList(paramMap);
		if (CollectionUtils.isEmpty(questAllotList)) {
			paramMap.put("maxQuestId", 0);
			questAllotList = taskDao.fetchNewTaskList(paramMap);
		}
		if (!CollectionUtils.isEmpty(questAllotList)) {
			taskDao.addNewTaskList(questAllotList);
			List<Long> questids = new ArrayList<Long>();
			for (QuestionAllot allot : questAllotList) {
				questids.add(allot.getQuestionId());
			}
			taskDao.lockQuestionAllotStatus(questids);
			return questAllotList.size();
		}
		return 0;
	}

	@Transactional
	// (propagation=Propagation.NEVER)
	public void filterNewTaskList(String teacherId, List<String> gradeList, List<Integer> subjectIdList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);
		paramMap.put("gradeList", gradeList);
		paramMap.put("subjectIdList", subjectIdList);
		paramMap.put("limit", BusinessConstant.AUDIO_BATCH_LIMIT);

		Teacher teacher = teacherService.getRequiredTeacher(teacherId);

		long maxQuestId = deleteOldTaskList(teacher);

		paramMap.put("maxQuestId", maxQuestId);

		if (teacher.isOrgTeacher()) {// 机构教师优先获取机构的题目
			paramMap.put("orgId", teacher.getCurOrgId());

			int countTask = addOrgNewTaskList(paramMap, teacherId);

			if (countTask < BusinessConstant.AUDIO_BATCH_LIMIT) {// 题目不足，从大题库补充
				paramMap.put("limit", BusinessConstant.AUDIO_BATCH_LIMIT - countTask);
				addNewTaskList(paramMap, teacherId);
			}
		} else {
			addNewTaskList(paramMap, teacherId);
		}
	}

	public List<RecordedVO> getRecordedList(String teacherId, int pageNo) {
		int limit = BusinessConstant.AUDIO_BATCH_LIMIT;
		int start = pageNo * limit;
		Teacher teacher = teacherService.getRequiredTeacher(teacherId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limit", limit);
		paramMap.put("teacherId", teacherId);
		paramMap.put("start", start);
		paramMap.put("planType", teacher.getPlanType());
		paramMap.put("orgId", teacher.getCurOrgId());
		boolean isOrg = organizationService.isOrgTeacher(teacherId);
		paramMap.put("isOrg", isOrg);

		List<RecordedVO> list = audioDao.getRecordedList(paramMap);

		commonService.wrapperAudioBuyCount(list);
		commonService.wrapperAudioPoint(list);
		commonService.wrapperAudioMoney(list, teacherId);

		return list;
	}

	public int countTaskAudio(String teacherId) {
		Teacher teacher = teacherService.getRequiredTeacher(teacherId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);
		paramMap.put("planType", teacher.getPlanType());
		paramMap.put("orgId", teacher.getCurOrgId());

		return audioDao.countTaskAudio(paramMap);
	}

	@Transactional
	public void submitAudio(Audio audio, String teacherId) {
		// 保存音频
		saveAudio(audio, teacherId);
		// 处理评价积分和录题费用
		dealEvaluationAndAudioFee(audio, teacherId);
		// 更新新手任务状态
		updateCompleteNewTaskStatus(teacherId);
		// 更新完成任务最后时间
		teacherDao.updateLastTaskTime(teacherId);
	}

	private void updateCompleteNewTaskStatus(String teacherId) {
		teacherService.refreshTeacherToRedis(teacherId);
		Teacher teacher = teacherService.getRequiredTeacher(teacherId);

		Date lastCompleteTaskTime = teacher.getCapacityTestCompleteTime();
		if (teacher.getCompleteNewUserTask() == Teacher.NOT_COMPLETE_NEW_USER_TASK) {
			int count = taskDao.countTaskAudioOfTeacher(teacherId, lastCompleteTaskTime);
			if (count >= BusinessConstant.NEW_TASK_BATCH_LIMIT) {
				teacherDao.completeNewTask(teacherId);

				teacher.setCompleteNewUserTask(Teacher.COMPLETE_NEW_USER_TASK_DOWN);
				teacher.setCapacityTestCompleteTime(new Date());
				teacherService.setTeacher2Redis(teacher);

				String content = "录题能力测试已完成，正在审核你的音频";
				PushMsgParamVO msg = PushMsgParamVO.createMessage(teacher, PushConstant.TYPE_DAILY_TASK, content);
				pushMsgService.pushMsg(msg);
				return;
			}
		}
	}

	/**
	 * @param audio
	 * @param teacherId
	 */
	private void saveAudio(Audio audio, String teacherId) {
		Teacher teacher = teacherService.getRequiredTeacher(teacherId);
		StarPoint starPoint = teacherService.getStarPointByTeacherId(teacherId);

		Question question = questionService.getQuestion(audio.getQuestionId());
		if (question == null) {
			throw new BusinessException("题目不存在.");
		}

		Audio oldAudio = audioDao.queryAudioByTeacherIdQuestId(teacherId, question.getRealId());
		if (oldAudio != null && AudioStatus.notPassCheck.getValue() != oldAudio.getStatus()) {
			throw new BusinessException("只有审核不通过的录音才允许重新提交录制.", Audio.BUSI_CODE_ALREADY_SUBMIT);
		}

		MultipartFile file = audio.getFile();
		String firstPartName = generateAudioName(question, teacherId);
		String fullName = FileUtil.generateFileName(file, firstPartName);
		String url = FileUtil.upload(file, fullName);
		audio.setName(fullName);
		audio.setUrl(url);
		audio.setTeacherId(teacherId);
		audio.setStatus(AudioStatus.needCheck.getValue());
		audio.setSubject(question.getRealSubject());
		audio.setPlanType(teacher.getPlanType());
		audio.setTeacherStar(starPoint.getStar());

		if (audio.getOrgId() > 0) {// 更新题库状态
			audio.setOrgId(audio.getOrgId());
			audio.setOrgTeacherId(organizationService.getTeacherIdByOrgId(audio.getOrgId()));

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("questids", Arrays.asList(audio.getQuestionId()));
			paramMap.put("status", OrgQuest.STATUS_UPLOAD);
			paramMap.put("orgId", audio.getOrgId());
			taskDao.updateOrgQuestAllot(paramMap);
		}

		// 更新题库状态未待审核
		questionService.updateQuestionStatus(audio.getQuestionId(), AudioStatus.needCheck);

		// 提交过录制并且审核不通过的，可以重新录制，替换上一次录制
		if (oldAudio != null) {
			delAudioFile(oldAudio.getUrl());

			audio.setId(oldAudio.getId());
			audioDao.updateAudio(audio);
		} else {
			audio.setId(UUID.randomUUID().toString());
			audio.setType(AudioType.audio.getValue());
			audio.setSource(AudioSource.task.getValue());
			audioDao.saveAudio(audio);

			addOneNewTask(teacher, audio);
		}
	}

	private void addOneNewTask(Teacher teacher, Audio audio) {
		// 移除待录制列表
		String teacherId = teacher.getId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);
		paramMap.put("questionId", audio.getQuestionId());
		paramMap.put("limit", 1);
		paramMap.put("gradeList", Grade.LIST_GRADE_ALL);
		paramMap.put("subjectIdList", Grade.LIST_SUBJECT_ALL);

		taskDao.deleteByTeacherIdQuestionId(paramMap);

		if (teacher.isOrgTeacher()) {// 机构教师优先获取机构的题目
			paramMap.put("orgId", teacher.getCurOrgId());

			int addSize = addOrgNewTaskList(paramMap, teacherId);

			if (addSize != 1) {// 题目不足，从大题库补充
				addNewTaskList(paramMap, teacherId);
			}
		} else {
			addNewTaskList(paramMap, teacherId);
		}
	}

	/**
	 * 根据不同星级处理积分和收入
	 * 
	 * @param audio
	 * @param teacherId
	 */
	private void dealEvaluationAndAudioFee(Audio audio, String teacherId) {
		Teacher teacher = teacherService.getRequiredTeacher(teacherId);

		evaluProcessorManager.getTaskProcessorByTeacher(teacher).postSubmitAudio(audio, teacher);
	}

	/**
	 * 通过id获取Audio
	 */
	public Audio queryAudio(String audioId) {
		return audioDao.queryAudioById(audioId);
	}

	public Map<String, Object> getQuestDetail(String teacherId, Long questionId) {
		Teacher teacher = teacherService.getRequiredTeacher(teacherId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);
		paramMap.put("questionId", questionId);

		Map<String, Object> result = new HashMap<String, Object>();
		AudioDetail detail = audioDao.getQuestDetail(paramMap);

		if (detail == null) {
			return result;
		}
		
		if (!StringUtils.isEmpty(detail.getId())) {
			if (detail.getStatus() != AudioStatus.waitAudio.getValue()) {
				AudioApprove approve = audioApproveDao.getApproveByAudioId(detail.getId());
				int point = audioStudentEvaluationService.getPointByAudioId(detail.getId());
				result.put("approve", approve);

				Evaluation evaluation = new Evaluation();
				evaluation.setPoint(point);
				result.put("evaluation", evaluation);// evaluationService.getEvaluationByLevel(approve.getEvalution(),
														// teacher.getTeacherIdentify())
			}
		}

		Organization org = organizationService.loadOrgByCode(teacher.getPhoneNumber());
		if (org != null && StringUtils.equals(org.getPlanType(), PlanFactory.PLAN_A)
				&& !StringUtils.equals(teacher.getId(), org.getTeacherId())) {
			detail.setOrgMaster(true);
			detail.setNickName(teacher.getNickname());
		}

		result.put("audio", detail);

		commonService.wrapperAudioEvaluateCount(Arrays.asList(detail));
		commonService.wrapperAudioBuyCount(Arrays.asList(detail));
		commonService.wrapperAudioMoney(Arrays.asList(detail), teacherId);

		if (detail.getStatus() == AudioStatus.offline.getValue()) {
			AudioEvalApprove audioEvalApprove = audioEvalApproveService.selectByAudioId(detail.getId());
			if (audioEvalApprove != null && audioEvalApprove.getStatus().intValue() == 1) {// 审核属实下线
				detail.setOfflineReason(audioEvalApprove.getContent());// 下线原因
				detail.setOfflineTime(audioEvalApprove.getApproveTime());// 下线时间

				detail.setDeductPoint(-Math.abs(audioEvalApprove.getDeductPoint()));
				detail.setIncome(-Math.abs(detail.getIncome()));
				if (detail.getDeductPoint() == 0) {
					detail.setDeductPoint(-Math.abs(audioEvalApprove.getTotalPoint()));
				}
			}
		}

		ErrorCorrection correct = errorCorrectionDao.getQuestionCorrection(teacherId, questionId);
		result.put("questionStatus", -1);
		if (correct != null) {
			result.put("questionStatus", correct.getCheckStatus());
		}

		result.put("orgId", teacher.getCurOrgId());

		return result;
	}

	/**
	 * 删除Audio
	 */
	public void delAudioFile(String url) {
		if (StringUtils.isNotBlank(url)) {
			String[] parts = StringUtils.split(url, "/");
			if (parts != null && parts.length == 2) {
				FileUtil.delete(parts[1]);
			}
		}
	}

	private String generateAudioName(Question question, String teacherId) {
		return question.getRealId() + "-" + teacherId;
	}
}
