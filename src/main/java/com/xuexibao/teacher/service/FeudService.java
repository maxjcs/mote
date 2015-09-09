package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.xuexibao.teacher.constant.RedisKeys;
import com.xuexibao.teacher.core.RedisOps;
import com.xuexibao.teacher.dao.AudioDao;
import com.xuexibao.teacher.dao.AudioUploadDao;
import com.xuexibao.teacher.dao.ErrorCorrectionDao;
import com.xuexibao.teacher.dao.FeudAnswerDetailDao;
import com.xuexibao.teacher.dao.FeudDetailWbDao;
import com.xuexibao.teacher.dao.FeudQuestDao;
import com.xuexibao.teacher.dao.QuestionV2Dao;
import com.xuexibao.teacher.dao.TaskDao;
import com.xuexibao.teacher.enums.AudioFeudTypeStatus;
import com.xuexibao.teacher.enums.AudioSource;
import com.xuexibao.teacher.enums.AudioStatus;
import com.xuexibao.teacher.enums.FeudAnswerDetailStatus;
import com.xuexibao.teacher.enums.FeudEvaluateStatus;
import com.xuexibao.teacher.enums.FeudQuestStatus;
import com.xuexibao.teacher.enums.PlanFactory;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioEvalApprove;
import com.xuexibao.teacher.model.AudioUpload;
import com.xuexibao.teacher.model.ErrorCorrection;
import com.xuexibao.teacher.model.FeudAnswerDetail;
import com.xuexibao.teacher.model.FeudDetailWb;
import com.xuexibao.teacher.model.FeudPointFeeConf;
import com.xuexibao.teacher.model.FeudQuest;
import com.xuexibao.teacher.model.Organization;
import com.xuexibao.teacher.model.Question;
import com.xuexibao.teacher.model.QuestionAllot;
import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.rpcvo.AudioBuyTotalNumVO;
import com.xuexibao.teacher.model.rpcvo.AudioEvaluateCount;
import com.xuexibao.teacher.model.rpcvo.AudioTotalPriceVO;
import com.xuexibao.teacher.model.vo.FeudQuestDetailVO;
import com.xuexibao.teacher.model.vo.FinishFeudAnswerDetailVO;
import com.xuexibao.teacher.model.vo.WaitFeudListVO;
import com.xuexibao.teacher.service.evaluprocessor.EvaluProcessorManager;
import com.xuexibao.teacher.util.DateUtils;
import com.xuexibao.teacher.util.FileUtil;
import com.xuexibao.teacher.util.PropertyUtil;

/**
 * 
 * @author fengbin
 * @date 2015-03-28
 * 
 */
@SuppressWarnings("restriction")
@Service
@Transactional
public class FeudService {
	@Resource
	private FeudQuestDao feudQuestDao;
	@Resource
	private TaskDao taskDao;
	@Resource
	private FeudAnswerDetailDao feudAnswerDetailDao;
	@Resource
	private QuestionV2Dao questionV2Dao;
	@Resource
	private EvaluProcessorManager evaluProcessorManager;
	@Resource
	private TeacherService teacherService;
	@Resource
	private StudentApiService studentApiService;
	@Resource
	protected EvaluationService evaluationService;
	@Resource
	protected AudioDao audioDao;
	@Resource
	protected AudioUploadDao audioUploadDao;
	@Resource
	protected FeudPointFeeConfService feudPointFeeConfService;
	@Resource
	protected FeudDetailWbDao feudDetailWbDao;
	@Resource
	protected QuestionService questionService;
	@Resource
	protected PointLogService pointLogService;
	@Resource
	private ErrorCorrectionDao errorCorrectionDao;
	@Resource 
	private OrganizationService organizationService;
	@Resource 
	private CommonFeudService commonFeudService;
	@Resource 
	private AudioStudentEvaluationService audioStudentEvaluationService;
	@Resource 
	private AudioEvalApproveService audioEvalApproveService;
	String questionUrl = PropertyUtil.getProperty("question_url");
	String toVideoPostUrl = PropertyUtil.getProperty("toVideoPostUrl");
	String toVideoCallBackUrl = PropertyUtil.getProperty("toVideoCallBackUrl");
	// 抢答队列大小
	int feudQueueSize = new Integer(PropertyUtil.getProperty("feudQueueSize"));
	// 题目抢答过期时间 单位为分钟
	int configfeudTimeOut = new Integer(PropertyUtil.getProperty("feudTimeOut"));
	// 单位为毫秒
	long feudTimeOutMs = configfeudTimeOut * 60 * 1000;
	private final String overplusTime = "overplusTime";
	//发起抢答的系统学生ID
	private final String feudSystemStudentId = "studentId:system";
	//发起抢答的系统图片ID
	private final String feudSystemImageId = "imageId:system";
	//普通发起抢答--请求所有老师
	public final String FEUD_SOURCETEACHER_COMMON = "common";
	@Resource
	private RedisOps redisOps;
	private static Logger logger = LoggerFactory.getLogger(FeudService.class);

	/**
	 * 处理学生抢答请求--所有老师
	 * 
	 * @param feudQuest
	 * @return
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean studentFeudQuest(FeudQuest feudQuest) {
		// 查询是否有该学生的问题请求
		Map queryMap = new HashMap();
		queryMap.put("questionRealId", feudQuest.getQuestionRealId());
		queryMap.put("studentId", feudQuest.getStudentId());
		
		List<FeudQuest> fqList = feudQuestDao.hasAskTeacherWithQuestion(queryMap);
		//随机请求名师
		if(FEUD_SOURCETEACHER_COMMON.equals(feudQuest.getSourceTeacher())){
			for(FeudQuest fq:fqList){
				if(FEUD_SOURCETEACHER_COMMON.equals(fq.getSourceTeacher())){
					// 已经有该学生的问题请求
					logger.info("已经有该学生的问题请求-the student have feudQuest:" + feudQuest.getId());
					return false;
				}
			}
		}else{
			for(FeudQuest fq:fqList){
				if(feudQuest.getSourceTeacher().equals(fq.getSourceTeacher())){
					logger.info("已经有该学生的问题请求该名师-the student have feudQuest:" + feudQuest.getId()+" sourceTeacher:"+fq.getSourceTeacher());
					return false;
				}
			}
		}
			return dealSaveFeudQuest(feudQuest);
	}
	
	
	/**
	 * 获取定向名师请求列表
	 * 
	 * @param feudQuest
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<WaitFeudListVO> getDirectFeudQuestList(Map map) {
		List<WaitFeudListVO> fqs = feudQuestDao.getFeudQuestListSelf(map);
		Set<String> usersSet = new HashSet<String>();
		List<WaitFeudListVO> resultList = new ArrayList<WaitFeudListVO>();
		for (WaitFeudListVO vo : fqs) {
			resultList.add(vo);
			usersSet.add(vo.getStudentId());
		}
	    commonFeudService.setWaitFeudStudentInfo(resultList, usersSet);	
		return resultList;
	}

	/**
	 * 获取抢答列表
	 * 
	 * @param feudQuest
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<WaitFeudListVO> getFeudQuestList(Map map) {
		
		long commonFeudQuestListStart = System.currentTimeMillis();
		List<WaitFeudListVO> fqs = feudQuestDao.getCommonFeudQuestList(map);
		logger.info("commonFeudQuestList time:"+(System.currentTimeMillis()-commonFeudQuestListStart));
		Set<String> usersSet = new HashSet<String>();
		String teacherId = (String) map.get("teacherId");
		List<WaitFeudListVO> resultList = new ArrayList<WaitFeudListVO>();
		// 教师抢答个人KEY
		String feudTeacherTimeKey = RedisKeys.feudTeacherTimeKey + teacherId;
		boolean hasTeacherKey = redisOps.hasKey(feudTeacherTimeKey);
		int offset =(Integer) map.get("offset");
		//处理非正常退出已抢答题目，后续再进去该抢答题
		if (hasTeacherKey && offset ==0) {
			long getPersonResumeFeudStart = System.currentTimeMillis();
			WaitFeudListVO resumeRecord = getPersonResumeFeud(teacherId);
			logger.info("getPersonResumeFeud time:"+(System.currentTimeMillis()-getPersonResumeFeudStart));
			if (resumeRecord != null) {
				resultList.add(resumeRecord);
				usersSet.add(resumeRecord.getStudentId());
			}
		}
		//只列出未被正在抢答的数据
		for (WaitFeudListVO vo : fqs) {
			String feudQueueKey = RedisKeys.feudQueueKey + vo.getFeudQuestId();
			long size = redisOps.getSetSize(feudQueueKey);
			if (size < feudQueueSize) {
				resultList.add(vo);
				usersSet.add(vo.getStudentId());
			}
		}
       //显示学生头像及昵称
		long setWaitFeudStudentInfoStart = System.currentTimeMillis();
       commonFeudService.setWaitFeudStudentInfo(resultList, usersSet);
       logger.info("setWaitFeudStudentInfoStart time:"+(System.currentTimeMillis()-setWaitFeudStudentInfoStart));
		return resultList;
	}
	

	/**
	 * feudType = 1 音频，2白板 提交 抢答题目 return -1 放弃提交,-2 抢答已过期，-3，对应学生发起的抢答记录不存在，-4
	 * 无此抢答详情，-5，抢答明细表 抢答状态不对，不是待抢答状态
	 */
	@SuppressWarnings("rawtypes")
	public HashMap submitFeud(long feudQuestId, Integer submitType, int feudType, MultipartFile file, String teacherId,
	long feudAnswerDetailId, long duration,String wbVersion) {
		String feudQueueKey = RedisKeys.feudQueueKey + feudQuestId;
		String feudTeacherTimeKey = RedisKeys.feudTeacherTimeKey + teacherId;
		HashMap resultData = new HashMap();
		Teacher teacher = teacherService.getTeacher(teacherId);
		StarPoint starPoint = teacherService.getStarPointByTeacherId(teacherId);
		if (starPoint.getStar() <= 2) {
			logger.error("submitFeud error,teacher star is less 3:" + feudQuestId);
			return dealResultData(-6, "submitFeud error,teacher star is less 3:, feudQuestId:" + feudQuestId);

		}
		FeudQuest fq = feudQuestDao.selectByPrimaryKey(feudQuestId);
		if (fq == null) {
			logger.error("submitFeud error,feudQuestId is not exist, feudQuestId:" + feudQuestId);
			return dealResultData(-3, "submitFeud error,feudQuestId is not exist, feudQuestId:" + feudQuestId);
		}
		//判断老师是否对同一题目进行多次录音

		Audio audio=audioDao.queryAudioByTeacherIdQuestId(teacherId, fq.getQuestionRealId());
		if(audio !=null){
			logger.error("question' audio has commit,teacherId:" + teacherId+ " questionId:"+fq.getQuestionRealId());
			return dealResultData(-7, "question' audio has commit,teacherId :" + teacherId+ " questionId:"+fq.getQuestionRealId());
		}
		
		// 判断如果已经是成功提交状态则不能再次提交
		FeudAnswerDetail feudDetail = feudAnswerDetailDao.selectByPrimaryKey(feudAnswerDetailId);
		if (feudDetail == null) {
			logger.error("submitFeud error,feudDetail is not exist, feudAnswerDetailId:" + feudAnswerDetailId);
			return dealResultData(-4, "submitFeud error,feudDetail is not exist, feudAnswerDetailId:" + feudAnswerDetailId);
		}
		
		if (feudDetail.getStatus() == FeudAnswerDetailStatus.complete.getValue() ) {
			logger.error("feudAnswerDetailStatus is complete status, feudAnswerDetailId:" + feudAnswerDetailId);
			return dealResultData(-7, "feudAnswerDetailStatus is complete status,feudAnswerDetailId :" + feudAnswerDetailId);
		}

		int feudAnswerDetailStatus = FeudAnswerDetailStatus.prepare.getValue();
		if (feudDetail.getStatus() != feudAnswerDetailStatus) {
			logger.error("submitFeud error,feudAnswerDetailStatus is not prepare status, feudAnswerDetailId:" + feudAnswerDetailId);
			return dealResultData(-5, "submitFeud error,feudDetail is not prepare status, feudAnswerDetailId:" + feudAnswerDetailId);
		}
		boolean isFirstfeud = false;
		String audioWhiteboardId = null;
		// 默认金额
		int fee = 0;
		// 默认评价
		int evaluate = -1;
		// 积分
		int point = 0;
		String audioWhiteboardUrl = null;
		FeudPointFeeConf fPoint = feudPointFeeConfService.getFeudPointByStarAndTeacherIdentify(-1, teacher.getTeacherIdentify());
		// 终止抢答
		if (submitType != null && submitType == -1) {
			logger.info("give up feudQuestId:" + feudQuestId + " teacherId:" + teacherId);
			resultData = dealResultData(-1, "give up feudQuestId:" + feudQuestId);
			// feudType = -1代表是放弃
			feudType = -1;
			audioWhiteboardId = "-999";
			feudAnswerDetailStatus = FeudAnswerDetailStatus.giveup.getValue();
			point = fPoint.getValue();
			evaluate = FeudEvaluateStatus.bad.getValue();
		   pointLogService.addPoint(teacherId, point, "抢答过期扣积分");
		} else {
			// 时间是否过期
			long opt = (Long) getFeudOverplusTime(teacherId).get(overplusTime);
			if (opt > 0) {
				logger.info("submitFeud feudQuestId:" + feudQuestId + " teacherId:" + teacherId + " is ok");
				String uuidFile = UUID.randomUUID().toString();
				String fullName = FileUtil.generateFileName(file, uuidFile);
				// .file 改为.mp3
				if (fullName.indexOf(".file") != -1) {
					fullName = uuidFile + ".mp3";
				}

				audioWhiteboardUrl = "";
				Audio au = new Audio();
				audioWhiteboardId = UUID.randomUUID().toString();
				Question q = questionService.getQuestion(fq.getQuestionRealId());
				commonFeudService.setFeudAndDirectAudioState(au, audioWhiteboardId, AudioFeudTypeStatus.feud.getValue(), fq.getQuestionRealId(), fullName, teacherId,duration, q.getRealSubject(),starPoint.getStar(), teacher.getPlanType());
				// 音频
				if (feudType == 1) {
					audioWhiteboardUrl = FileUtil.upload(file, fullName);
					// 音频
					au.setUrl(audioWhiteboardUrl);
					au.setType(1);
				} else if (feudType == 2) {
					commonFeudService.submitProcessWithWhiteBoard(au, wbVersion, uuidFile, file, audioWhiteboardId, teacherId, fullName, feudAnswerDetailId);
				}
				// 保存audioUpload,保存音频或者白板
				audioDao.saveAudio(au);
				feudAnswerDetailStatus = FeudAnswerDetailStatus.complete.getValue();
				// 第一位抢答
				isFirstfeud = true;
				// 录题费
				FeudPointFeeConf feeConf = feudPointFeeConfService.getFeudFeeByStarAndTeacherIdentify(starPoint.getStar(),
				teacher.getTeacherIdentify());
				if (feeConf == null) {
					logger.error("teacher:" + teacher.getId() + " FeudPointFeeConf fee conf error:");
					fee = 0;
				} else {
					fee = feeConf.getValue();
				}
				if(starPoint.getStar()<3){
					//三星以下无积分
					fee = 0;
				}
				// 默认好评
				evaluate = FeudEvaluateStatus.good.getValue();
				// 支付抢答费用，自动好评，支付积分
				evaluProcessorManager.getFeudProcessorByTeacher(teacher).postSubmitAudio(au, teacher);
				// 第一位答题完成后 新增KEY，同时通知教师端
				String feudQuestIdKey = RedisKeys.feudQuestIdKey + feudQuestId;
				if (!redisOps.hasKey(feudQuestIdKey)) {
					redisOps.addKeyValue(feudQuestIdKey, teacherId);
					logger.info("first teacher feud quest。feudQuestIdKey：" + feudQuestIdKey + " teacherId:" + teacherId + " feudQuestId:"
					+ feudQuestId);
					// 通知学生端该问题已有老师抢答完成
					commonFeudService.notifyMessageToStudent(q.getSubject(), fq, feudType, au, teacher.getWrapperName());
				}

				resultData = dealResultData(0, "ok");
			} else {
				logger.info("submitFeud feudQuestId:" + feudQuestId + " teacherId:" + teacherId + " is expire");
				audioWhiteboardId = "-999";
				point = fPoint.getValue();
				pointLogService.addPoint(teacherId, point, "抢答过期扣积分");
				feudAnswerDetailStatus = FeudAnswerDetailStatus.expire.getValue();
				resultData = dealResultData(-2, "feud submit expire");
			}

		}
		// 更新抢答明细表
		long updateState = updateFeudAnswerDetail(audioWhiteboardId, feudType, teacherId, feudAnswerDetailStatus, feudAnswerDetailId, fee,
		evaluate, point, audioWhiteboardUrl);
		if (isFirstfeud && updateState != -1) {
			// 更新抢答表
			updateFeudQuest(feudQuestId, feudAnswerDetailId, teacherId);
		}
		boolean hasTeacherKey = redisOps.hasKey(feudTeacherTimeKey);
		// 销毁老师抢答KEY
		if (hasTeacherKey) {
			redisOps.delete(feudTeacherTimeKey);
		}
		boolean hasQueueKey = redisOps.hasKey(feudQueueKey);
		// 已经有队列,老师从队列中删除
		if (hasQueueKey) {
			redisOps.removeSetValue(feudQueueKey, teacherId);
		}
		return resultData;
	}
	/**
	 * 定向推题 音频白板提交
	 * @param feudQuestId
	 * @param submitType
	 * @param feudType
	 * @param file
	 * @param teacherId
	 * @param feudAnswerDetailId
	 * @param duration
	 * @return
	 */
	public HashMap submitDirectFeud(long feudQuestId, Integer submitType,
			int feudType, MultipartFile file, String teacherId,
			long feudAnswerDetailId, long duration,String wbVersion) {
		HashMap resultData = new HashMap();
		Teacher teacher = teacherService.getTeacher(teacherId);
		StarPoint starPoint = teacherService.getStarPointByTeacherId(teacherId);
		FeudQuest fq = feudQuestDao.selectByPrimaryKey(feudQuestId);

		if (fq == null) {
			logger.error("submitDirectFeud error,feudQuestId is not exist, feudQuestId:"
					+ feudQuestId);
			return dealResultData(-3,
					"submitDirectFeud error,feudQuestId is not exist, feudQuestId:"
							+ feudQuestId);
		}
		
		//同一老师对同一题目只能录一次音频
		Audio audio=audioDao.queryAudioByTeacherIdQuestId(teacherId, fq.getQuestionRealId());
		if(audio !=null){
			logger.error("question' audio has commit,teacherId:" + teacherId+ " questionId:"+fq.getQuestionRealId());
			return dealResultData(-7, "question' audio has commit,teacherId :" + teacherId+ " questionId:"+fq.getQuestionRealId());
		}
			
		// 判断如果已经是成功提交状态则不能再次提交
		FeudAnswerDetail feudDetail = feudAnswerDetailDao
				.selectByPrimaryKey(feudAnswerDetailId);
		if (feudDetail == null) {
			logger.error("submitDirectFeud error,feudDetail is not exist, feudAnswerDetailId:"
					+ feudAnswerDetailId);
			return dealResultData(-4,
					"submitDirectFeud error,feudDetail is not exist, feudAnswerDetailId:"
							+ feudAnswerDetailId);
		}

		if (feudDetail.getStatus() == FeudAnswerDetailStatus.complete
				.getValue()) {
			logger.error("feudAnswerDetailStatus is complete status, feudAnswerDetailId:"
					+ feudAnswerDetailId);
			return dealResultData(-7,
					"feudAnswerDetailStatus is complete status,feudAnswerDetailId :"
							+ feudAnswerDetailId);
		}

		int feudAnswerDetailStatus = FeudAnswerDetailStatus.prepare.getValue();
		if (feudDetail.getStatus() != feudAnswerDetailStatus) {
			logger.error("submitDirectFeud error,feudAnswerDetailStatus is not prepare status, feudAnswerDetailId:"
					+ feudAnswerDetailId);
			return dealResultData(-5,
					"submitDirectFeud error,feudDetail is not prepare status, feudAnswerDetailId:"
							+ feudAnswerDetailId);
		}
	
		String audioWhiteboardId = null;
		// 默认金额
		int fee = 0;
		// 默认评价
		int evaluate = -1;
		// 积分
		int point = 0;
		String audioWhiteboardUrl = null;
		// 放弃定向抢答
		if (submitType != null && submitType == -1) {
			logger.info("submitDirectFeud give up feudQuestId:" + feudQuestId
					+ " teacherId:" + teacherId);
			return dealResultData(-1, "give up feudQuestId:" + feudQuestId);

		} else {
			String uuidFile = UUID.randomUUID().toString();
			String fullName = FileUtil.generateFileName(file, uuidFile);
			// .file 改为.mp3
			if (fullName.indexOf(".file") != -1) {
				fullName = uuidFile + ".mp3";
			}
			audioWhiteboardUrl = "";
			Audio au = new Audio();
			audioWhiteboardId = UUID.randomUUID().toString();
			Question q = questionService.getQuestion(fq.getQuestionRealId());
			commonFeudService.setFeudAndDirectAudioState(au, audioWhiteboardId, AudioFeudTypeStatus.direct.getValue(), fq.getQuestionRealId(), fullName, teacherId,duration, q.getRealSubject(),starPoint.getStar(), teacher.getPlanType());
			// 音频
			if (feudType == 1) {
				audioWhiteboardUrl = FileUtil.upload(file, fullName);
				// 音频
				au.setUrl(audioWhiteboardUrl);
				au.setType(1);
			} else if (feudType == 2) {
				//上传白板转换视频
				commonFeudService.submitProcessWithWhiteBoard(au, wbVersion, uuidFile, file, audioWhiteboardId, teacherId, fullName, feudAnswerDetailId);
			}
			// 保存audioUpload,保存音频或者白板
			audioDao.saveAudio(au);
			feudAnswerDetailStatus = FeudAnswerDetailStatus.complete.getValue();
		// 录题费
			FeudPointFeeConf feeConf = feudPointFeeConfService
					.getFeudFeeByStarAndTeacherIdentify(starPoint.getStar(),
							teacher.getTeacherIdentify());
			if (feeConf == null) {
				logger.error("teacher:" + teacher.getId()
						+ " FeudPointFeeConf fee conf error:");
				fee = 0;
			} else {
				fee = feeConf.getValue();
			}
			// 默认好评
			evaluate = FeudEvaluateStatus.good.getValue();
			// 支付抢答费用，自动好评，支付积分
			evaluProcessorManager.getFeudProcessorByTeacher(teacher)
					.postSubmitAudio(au, teacher);
			// 第一位答题完成后 新增KEY，同时通知教师端
			// 通知学生端该问题已有老师抢答完成
			commonFeudService.notifyMessageToStudent(q.getSubject(), fq, feudType, au, teacher.getWrapperName());
			resultData = dealResultData(0, "ok");
		}
		// 更新抢答明细表
		long updateState = updateFeudAnswerDetail(audioWhiteboardId, feudType,
				teacherId, feudAnswerDetailStatus, feudAnswerDetailId, fee,
				evaluate, point, audioWhiteboardUrl);
		if (updateState != -1) {
			// 更新抢答表
			updateFeudQuest(feudQuestId, feudAnswerDetailId, teacherId);
		}
		return resultData;
	}
	
	@SuppressWarnings("rawtypes")
	public List<FinishFeudAnswerDetailVO> completeDirectFeudList(Map<String, Object> paramMap) {
		List<FinishFeudAnswerDetailVO> fdvs = feudAnswerDetailDao.completeDirectFeudList(paramMap);
		Set<String> usersSet = new HashSet<String>();
		List<String> audioIds = new ArrayList<String>(); 
		for (FinishFeudAnswerDetailVO vo : fdvs) {
			usersSet.add(vo.getStudentId());
			audioIds.add(vo.getAudioId());
		}
		//设置学生头像及昵称
		commonFeudService.setFinishFeudStudentInfo(fdvs, usersSet);
		//设置音频下线状态
		setCompleteFeudListOffline(audioIds, fdvs);
		setFeudTotalPoint(audioIds, fdvs);
		//设置音频购买量
		setAudioBuyTotalNum(audioIds, fdvs);
		//设置是否planA显示积分和费用
		setShowPlanAPointFee(audioIds, fdvs);
		return fdvs;
	}
	//设置是否planA显示积分和费用
	private void setShowPlanAPointFee(List<String>audioIds,List<FinishFeudAnswerDetailVO> fdvs){
		if(audioIds.size()>0){
			Map<String,Object> audioIdsMap = new HashMap<String,Object>();
			audioIdsMap.put("audioIds", audioIds);
			List<AudioUpload> audios = audioUploadDao.queryByAudioIds(audioIdsMap);
			for (FinishFeudAnswerDetailVO vo : fdvs) {
				for(AudioUpload audio:audios){
					if(audio!=null && PlanFactory.PLAN_A.equals(audio.getPlanType())){
						vo.setShowPlanAPointFee(false);
					}else{
						vo.setShowPlanAPointFee(true);	
					}
				}
			}
		}
	}
	
	
	private void setCompleteFeudListOffline(List<String> audioIds,List<FinishFeudAnswerDetailVO> fdvs){
		//获取音频列表是否下线
		if(audioIds.size()>0){
			Map<String,Object> audioIdsMap = new HashMap<String,Object>();
			audioIdsMap.put("audioIds", audioIds);
			List<AudioUpload> audios = audioUploadDao.queryByAudioIds(audioIdsMap);
			for (FinishFeudAnswerDetailVO vo : fdvs) {
				for(AudioUpload a:audios){
					if(vo.getAudioId()!=null&&vo.getAudioId().equals(a.getId())&&a.getStatus()==AudioStatus.offline.getValue()){
						//设置音频下线
						vo.setOffline(true);
					}
				}
			}
		}
	}
	public int completeDirectFeudListCount(Map<String, Object> paramMap) {
		return feudAnswerDetailDao.completeDirectFeudListCount(paramMap);

	}

	/**
	 * 
	 * 
	 * @param teacherId
	 * @return
	 */
	public List<FinishFeudAnswerDetailVO> completeFeudList(Map<String, Object> paramMap) {
		List<FinishFeudAnswerDetailVO> fdvs = feudAnswerDetailDao.completeFeudList(paramMap);
		Set<String> usersSet = new HashSet<String>();
		List<String> audioIds = new ArrayList<String>(); 
		for (FinishFeudAnswerDetailVO vo : fdvs) {
			usersSet.add(vo.getStudentId());
			audioIds.add(vo.getAudioId());
		}
      commonFeudService.setFinishFeudStudentInfo(fdvs, usersSet);
		//设置音频下线状态
	   setCompleteFeudListOffline(audioIds, fdvs);
	   //批量设置积分
	   setFeudTotalPoint(audioIds, fdvs);
	   //设置购买数
	   setAudioBuyTotalNum(audioIds, fdvs);
	  //设置是否planA显示积分和费用
	  setShowPlanAPointFee(audioIds, fdvs);
		return fdvs;
	}
   //设置音频购买总数
	private void setAudioBuyTotalNum(List<String>audioIds,List<FinishFeudAnswerDetailVO> fdvs){
		if (audioIds.size() > 0) {
			Map<String, Object> queryAudiosMap = new HashMap<String, Object>();
			queryAudiosMap.put("audioIds", audioIds);
			 Map<String,AudioBuyTotalNumVO.Item> result = studentApiService.getAudioTotalPriceAndNum(audioIds, false);
			 if(result == null){
				 logger.error("getAudioTotalPriceAndNum is null");
				 return ;
			 }
			 for(FinishFeudAnswerDetailVO vo : fdvs) {
				 for(Map.Entry<String, AudioBuyTotalNumVO.Item> entry:result.entrySet()){ 
					 if(entry.getKey().equals(vo.getAudioId())){
						 vo.setTotalBuyers(entry.getValue().getTotalNum());
					 }
				 }
			 }
		}  
	}
	
	private void setFeudTotalPoint(List<String>audioIds,List<FinishFeudAnswerDetailVO> fdvs){
		if (audioIds.size() > 0) {
			Map<String, Object> queryAudiosMap = new HashMap<String, Object>();
			queryAudiosMap.put("audioIds", audioIds);
			List<AudioEvalApprove> aeaList = audioStudentEvaluationService
					.getPointByAudioIds(queryAudiosMap);
			for (FinishFeudAnswerDetailVO vo : fdvs) {
				for (AudioEvalApprove aea : aeaList) {
					if (aea.getAudioId().equals(vo.getAudioId())) {
						// 设置总积分
						vo.setScore(aea.getTotalPoint()+aea.getDeductPoint());
					}
				}
			}
		}  
	}
	
	
	public int completeFeudListCount(Map<String, Object> paramMap) {
		return  feudAnswerDetailDao.completeFeudListCount(paramMap);
		
	}

	@SuppressWarnings("rawtypes")
	public FinishFeudAnswerDetailVO completeFeudDetail(String feudAnswerDetailId,String loginTeacherId) {
		FinishFeudAnswerDetailVO vo = feudAnswerDetailDao.completeFeudDetail(feudAnswerDetailId);
		if (vo != null) {
			vo.setAudioWbUrl(vo.getAudioWbUrl());
			// 获取音频总价格及总购买人数
			// 007b97b7-3fca-4edb-9192-429d830faa35
			//
			String audioId = vo.getAudioId();
			Audio audio = audioDao.queryAudioById(audioId);
			if(audio!=null&&audio.getStatus()==AudioStatus.offline.getValue()){
				//音频已下线
				vo.setOffline(true);
			}
			
			//获取下线原因
			if(audio!=null&&audio.getStatus()==AudioStatus.offline.getValue()){
				AudioEvalApprove audioEvalApprove=audioEvalApproveService.selectByAudioId(vo.getAudioId());
				if(audioEvalApprove!=null&&audioEvalApprove.getStatus().intValue()==1){//审核属实下线
					vo.setOfflineReason(audioEvalApprove.getContent());//下线原因
					int score = 0;
					if(audioEvalApprove.getDeductPoint().intValue()<0){//扣除积分
						score = audioEvalApprove.getDeductPoint();//如果有扣除积分，显示扣除积分
					}else{
						score = audioEvalApprove.getTotalPoint();//如果没有扣除积分，总积分。为负数
					}
					vo.setScore(score);//扣除积分
					vo.setOfflineTime(audioEvalApprove.getApproveTime().getTime());//下线时间
					vo.setRevenue(-vo.getRevenue());
					vo.setOfflineTimeStr(DateUtils.formatDetail(audioEvalApprove.getApproveTime()));//下线时间str
				}
			}
			if(audio!=null && PlanFactory.PLAN_A.equals(audio.getPlanType())){
				vo.setShowPlanAPointFee(false);
			}else{
				vo.setShowPlanAPointFee(true);	
			}
			if (audioId != null) {
				AudioTotalPriceVO.Item result = studentApiService.getAudioTotoalPrice(audioId, false);
				vo.setTotalBuyers(result.totalNum);
			}
			// 如果是白板，则显示下载地址
			if (vo.getFeudType() == 2) {
				List<FeudDetailWb> fdws = feudDetailWbDao.selectAllByWbId(vo.getAudioId());
				for (FeudDetailWb wb : fdws) {
					String wbUrl = wb.getFileUrl();
					wb.setFileUrl(wbUrl);
				}
				vo.setWbUrls(fdws);
			}
			
			List<String> audioIds=new ArrayList<String>();
			if(vo.getAudioId()!=null){
				audioIds.add(vo.getAudioId());	
			}
			if(audioIds.size()>0){
				List<AudioEvaluateCount.Item> list = studentApiService.getAudioEvaluateList(audioIds, false);
				if(list==null){
					vo.setGoodEvaNum(0);
					vo.setMediumEvaNum(0);
					vo.setBadEvaNum(0);
				}else{
					for(AudioEvaluateCount.Item item:list){
						if(StringUtils.equals(item.audioId,vo.getAudioId())){
							vo.setGoodEvaNum(item.goodCounts);
							vo.setMediumEvaNum(item.midCounts);
							vo.setBadEvaNum(item.badCounts);
							break;
						}
					}
				}
			}
			//设置是否机构主账号
			Teacher loginTeacher=teacherService.getRequiredTeacher(loginTeacherId);
			String audioTeacherNickname="";
			if(audio!=null&&StringUtils.isNotEmpty(audio.getTeacherId())&&!StringUtils.equals(audio.getTeacherId(), loginTeacherId)){
				Teacher audioTeacher=teacherService.getRequiredTeacher(audio.getTeacherId());
				if(audioTeacher!=null){
					audioTeacherNickname=audioTeacher.getNickname();
				}
			}
			Organization org=organizationService.loadOrgByCode(loginTeacher.getPhoneNumber());
			if(org!=null&&StringUtils.equals(org.getPlanType(), PlanFactory.PLAN_A)){
				vo.setIsOrgMaster(true);
				vo.setNickName(audioTeacherNickname);
			}
			//设置积分
			if(vo.getStatus()==FeudAnswerDetailStatus.complete.getValue()){
				int point = audioStudentEvaluationService.getPointByAudioId(vo.getAudioId());
				vo.setScore(point);
			}
			ErrorCorrection correct = errorCorrectionDao.getQuestionCorrection(vo.getTeacherId(), vo.getQuestRealId());
			vo.setQuestionStatus(-1);
			if (correct != null) {
				vo.setQuestionStatus(correct.getCheckStatus());
			}		
		}
		//设置好中差 评价数
		
		return vo;
	}

	/**
	 * 教师发起抢答
	 * 
	 * @param feudQuestId
	 *            0 正常进入抢答队列，-1 题目已抢答完毕， -2 当前老师手上已有抢答题目，-3 队列已满，-4
	 *            当前老师对该题已过期,-5 抢答题目不存在
	 */
	@SuppressWarnings("rawtypes")
	public HashMap enterFeud(long feudQuestId, String teacherId) {
		// 判断该学生请求的题目是否已抢答
		long feudAnswerDetailId = -1;
	    FeudQuest fq = feudQuestDao.selectByPrimaryKey(feudQuestId);
		if (fq == null) {
			logger.error("enterFeud error,feudQuestId is not exist, feudQuestId:" + feudQuestId);
			return dealResultData(-5, "enterFeud error,feudQuestId is not exist, feudQuestId:" + feudQuestId);
		}
		
		Audio audio=audioDao.queryAudioByTeacherIdQuestId(teacherId, fq.getQuestionRealId());
		if(audio !=null){
			logger.error("enterFeud question' audio has commit,teacherId:" + teacherId+ " questionId:"+fq.getQuestionRealId());
			return dealResultData(-6, "enterFeud question' audio has commit,teacherId :" + teacherId+ " questionId:"+fq.getQuestionRealId());
		}
		
		
		long questionRealId = fq.getQuestionRealId();

		// 题目抢答队列KEY 按日期来生成
		String feudQueueKey = RedisKeys.feudQueueKey + feudQuestId;
		// 教师抢答个人KEY
		String feudTeacherTimeKey = RedisKeys.feudTeacherTimeKey + teacherId;
		boolean hasTeacherKey = redisOps.hasKey(feudTeacherTimeKey);
		//再次进入另外一个抢答，但之前抢答过题目后退出APP且该题目已经有人提交。
		
		// 该老师已经在抢答题目
		if (hasTeacherKey) {
			// 曾经进入过队列
			boolean isSameTeacher = redisOps.isSetMember(feudQueueKey, teacherId);
			if (isSameTeacher) {
				logger.info("feudQueueKey:" + feudQueueKey + " feudQuestId:" + feudQuestId + " teacherId:  " + teacherId
				+ " enter queue is same teacher");
				// 查询原来的抢答记录
				return sameTeacherEnterFeudQuest(teacherId, feudQuestId);
			}
			// 可能是抢答的不同的一道题
			logger.info("the teacher had enter one feudQuest ,can't enter another.feudTeacherTimeKey:" + feudTeacherTimeKey);
			return dealResultDataWithFeudAnswerDetailId(-2, "the teacher:" + teacherId + " already have one feudQuest", feudAnswerDetailId,
			null);
		}
		

		
		boolean hasQueueKey = redisOps.hasKey(feudQueueKey);
		// 初始白板或音频类型为0，标示还未知类型
		int feudType = 0;
		// 已经有队列
		if (hasQueueKey) {
			long size = redisOps.getSetSize(feudQueueKey);
			logger.info("feudQueueKey:" + feudQueueKey + " size:" + size);
			if (size >= feudQueueSize) {
				// 队列已满，无法进行抢答
				return dealResultDataWithFeudAnswerDetailId(-3, "the feudQueue is full", feudAnswerDetailId, null);
			} else {
				long redisResult = redisOps.addSetValue(feudQueueKey, teacherId);
				if (redisResult == 0) {
					long opt = (Long) getFeudOverplusTime(teacherId).get(overplusTime);
					if (opt <= 0) {
						logger.error("feudQueueKey:" + feudQueueKey + " is expire  teacherId:" + teacherId + " feudQuestId:" + feudQuestId);
						return dealResultDataWithFeudAnswerDetailId(-4, "抢答已过期", feudAnswerDetailId, null);
					}

					// 当前老师已对该题进行抢答
					logger.info("time is over --- feudQueueKey:" + feudQueueKey + " feudQuestId:" + feudQuestId + " teacherId:  "
					+ teacherId + " enter queue is same teacher");
					return sameTeacherEnterFeudQuest(teacherId, feudQuestId);

				} else {
					recordFeudTeacherTime(feudTeacherTimeKey);
					feudAnswerDetailId = createFeudAnswerDetail(feudQuestId, null, feudType, teacherId,
					FeudAnswerDetailStatus.prepare.getValue(), questionRealId);
					return dealResultDataWithFeudAnswerDetailId(0, "ok", feudAnswerDetailId, teacherId);
				}
			}
		} else {
			// 第一个老师进入队列,创建题目队列
			logger.info("feudQueueKey:" + feudQueueKey + " first enter feudQueue");
			redisOps.addSetValue(feudQueueKey, teacherId);
			feudAnswerDetailId = createFeudAnswerDetail(feudQuestId, null, feudType, teacherId, FeudAnswerDetailStatus.prepare.getValue(),
			questionRealId);
			// 创建个人抢题redis_key
			recordFeudTeacherTime(feudTeacherTimeKey);
			return dealResultDataWithFeudAnswerDetailId(0, "ok", feudAnswerDetailId, teacherId);
		}
	}
	
	
	/**
	 * 教师进入定向推题
	 * 
	 * @param feudQuestId
	 *            0 正常进入抢答队列，-1 题目已抢答完毕， -2 当前老师手上已有抢答题目，-3 队列已满，-4
	 *            当前老师对该题已过期,-5 抢答题目不存在
	 */
	@SuppressWarnings("rawtypes")
	public HashMap enterDirectFeud(long feudQuestId, String teacherId) {
		// 判断该学生请求的题目是否已抢答
		long feudAnswerDetailId = -1;
		// 初始白板或音频类型为0，标示还未知类型
				int feudType = 0;
	    FeudQuest fq = feudQuestDao.selectByPrimaryKey(feudQuestId);
		if (fq == null) {
			logger.error("enterFeud error,feudQuestId is not exist, feudQuestId:" + feudQuestId);
			return dealResultData(-5, "enterFeud error,feudQuestId is not exist, feudQuestId:" + feudQuestId);
		}
		//判断该老师是否已经对该题目录制过音频
		Audio audio=audioDao.queryAudioByTeacherIdQuestId(teacherId, fq.getQuestionRealId());
		if(audio !=null){
			logger.error("enterDirectFeud question' audio has commit,teacherId:" + teacherId+ " questionId:"+fq.getQuestionRealId());
			return dealResultData(-6, "enterDirectFeud question' audio has commit,teacherId :" + teacherId+ " questionId:"+fq.getQuestionRealId());
		}
		
		
		long questionRealId = fq.getQuestionRealId();
		FeudAnswerDetail record = new FeudAnswerDetail();
		record.setTeacherId(teacherId);
		record.setFeudQuestId(feudQuestId);
		List<FeudAnswerDetail> lists = feudAnswerDetailDao.selectPrepareByFeudQuestIdAndTeacherId(record);
		if (lists.size() > 0) {
			FeudAnswerDetail fad = lists.get(0);
		    feudAnswerDetailId = fad.getId();
			return dealResultDataWithFeudAnswerDetailId(0, "ok", feudAnswerDetailId, teacherId);
			
		}
		 else {
			// 第一个老师进入队列,创建题目队列
			logger.info("first enter enterDirectFeud");
			feudAnswerDetailId = createFeudAnswerDetail(feudQuestId, null, feudType, teacherId, FeudAnswerDetailStatus.prepare.getValue(),
			questionRealId);
			return dealResultDataWithFeudAnswerDetailId(0, "ok", feudAnswerDetailId, teacherId);
		}
	}
	
	
	
	//获取曾经抢答但未提交或未失效的数据
	private WaitFeudListVO getPersonResumeFeud(String teacherId){
		String feudTeacherTimeKey = RedisKeys.feudTeacherTimeKey + teacherId;
		boolean hasTeacherKey = redisOps.hasKey(feudTeacherTimeKey);
		if(!hasTeacherKey){
			return null;
		}
		Set<String> feudQueueKeys = redisOps.getKeys(RedisKeys.feudQueueKey);
		for(Iterator<String> itr = feudQueueKeys.iterator();itr.hasNext();){
			String queueKey = itr.next();
			boolean isInQueue = redisOps.isSetMember(queueKey,
					teacherId);
			//老师在该队列
			if(isInQueue){
				long feudId = Long.valueOf(queueKey.substring(RedisKeys.feudQueueKey.length()));
				 List<WaitFeudListVO> results = feudQuestDao.getResumeFeudQuestById(feudId);
				 if(results.size()>0){
					 WaitFeudListVO vo  = results.get(0);
					 vo.setFeuding(true);
					 return vo;
				 }
			}
		}
		return null;
	}
	
	

	@SuppressWarnings("rawtypes")
	private HashMap sameTeacherEnterFeudQuest(String teacherId, Long feudQuestId) {
		// 查询原来的抢答记录
		FeudAnswerDetail record = new FeudAnswerDetail();
		record.setTeacherId(teacherId);
		record.setFeudQuestId(feudQuestId);
		List<FeudAnswerDetail> lists = feudAnswerDetailDao.selectPrepareByFeudQuestIdAndTeacherId(record);
		if (lists.size() > 0) {
			FeudAnswerDetail fad = lists.get(0);
			long feudAnswerDetailId = fad.getId();
			logger.info("sameTeacher enter is ok");
			return dealResultDataWithFeudAnswerDetailId(0, "ok", feudAnswerDetailId, teacherId);
		} else {
			logger.error("sameTeacher enter is error,can't find record");
			return dealResultDataWithFeudAnswerDetailId(-5, "same teacher enter error,can't find record", -1, teacherId);
		}
	}

	/**
	 * 新增抢答题目详情
	 */
	private long createFeudAnswerDetail(long feudQuestId, String audioWhiteboardId, long feudType, String teacherId, int status,
	long questRealId) {
		FeudAnswerDetail fad = new FeudAnswerDetail();
		fad.setFeudQuestId(feudQuestId);
		fad.setAudioWhiteboardId(audioWhiteboardId);
		fad.setFeudType(feudType);
		fad.setTeacherId(teacherId);
		fad.setStatus(status);
		fad.setQuestRealId(questRealId);
		feudAnswerDetailDao.insert(fad);
		fad.setId(feudAnswerDetailDao.selectLastInsertId());
		long pk = fad.getId();
		return pk;
	}

	/**
	 * 更新抢答题目详情
	 */
	private long updateFeudAnswerDetail(String audioWhiteboardId, long feudType, String teacherId, int status, long feudAnswerDetailId,
	int fee, int evaluate, int point, String audioWhiteboardUrl) {

		FeudAnswerDetail fad = feudAnswerDetailDao.selectByPrimaryKey(feudAnswerDetailId);
		if (fad == null) {
			return -1;
		}
		fad.setAudioWhiteboardId(audioWhiteboardId);
		fad.setFeudType(feudType);
		fad.setTeacherId(teacherId);
		fad.setStatus(status);
		fad.setFee(fee);
		fad.setEvaluate(evaluate);
		fad.setPoint(point);
		fad.setAudioWhiteboardUrl(audioWhiteboardUrl);
		int updateState = feudAnswerDetailDao.updateByPrimaryKey(fad);
		return updateState;
	}

	/**
	 * 更新抢答题目
	 */
	private void updateFeudQuest(long feudQuestId, long feudAnswerDetailId, String teacherId) {
		FeudQuest fq = feudQuestDao.selectByPrimaryKey(feudQuestId);
		if (fq != null) {
			fq.setFeudAnswerDetailId(feudAnswerDetailId);
			fq.setFeudAnswerTeacherId(teacherId);
			fq.setStatus(FeudQuestStatus.complete.getValue());
			feudQuestDao.updateByPrimaryKey(fq);
		} else {
			logger.error("FeudQuest is null,feudQuestId:" + feudQuestId);
		}

	}

	/**
	 * 
	 * @param teacherId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap getFeudOverplusTime(String teacherId) {
		String feudTeacherTimeKey = RedisKeys.feudTeacherTimeKey + teacherId;
		boolean hasfeudTeacherTimeKey = redisOps.hasKey(feudTeacherTimeKey);
		long duration = 0;
		if (hasfeudTeacherTimeKey) {
			Long initTime = Long.valueOf((String) redisOps.getValue(feudTeacherTimeKey));
			long pay = System.currentTimeMillis() - initTime;
			if (pay > feudTimeOutMs) {
				duration = 0;
			} else {
				duration = (feudTimeOutMs - (System.currentTimeMillis() - initTime)) / 1000;
			}
		}
		HashMap data = new HashMap();
		data.put(overplusTime, duration);
		return data;
	}

	/**
	 * @todo 需要同步服务器时间 记录老师开启抢答的时间，timeout 时间后过期
	 * 
	 * @param feudTeacherTimeKey
	 */
	private void recordFeudTeacherTime(String feudTeacherTimeKey) {
		long currentTime = System.currentTimeMillis();
		redisOps.addKeyValue(feudTeacherTimeKey, String.valueOf(currentTime));
	}

	/**
	 * 处理返回结果
	 * 
	 * @param resultCode
	 * @param resultMsg
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HashMap dealResultData(int resultCode, String resultMsg) {
		HashMap data = new HashMap();
		data.put("resultCode", resultCode);
		data.put("resultMsg", resultMsg);
		return data;
	}

	/**
	 * 处理WithFeudAnswerDetailId返回结果
	 * 
	 * @param resultCode
	 * @param resultMsg
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HashMap dealResultDataWithFeudAnswerDetailId(int resultCode, String resultMsg, long feudAnswerDetailId, String teacherId) {
		HashMap data = new HashMap();
		data.put("resultCode", resultCode);
		data.put("resultMsg", resultMsg);
		data.put("feudAnswerDetailId", feudAnswerDetailId);
		long durTime = 0;
		if (teacherId != null) {
			durTime = (Long) getFeudOverplusTime(teacherId).get(overplusTime);
		}
		data.put(overplusTime, durTime);
		return data;
	}

	/**
	 * 获取抢答问题详情
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public FeudQuestDetailVO getFeudQuestDetail(String feudQuestId,String teacherId) {
		List<FeudQuestDetailVO> fqs = feudQuestDao.getFeudQuestDetail(new Long(feudQuestId));
		if (fqs == null || fqs.size() <= 0) {
			logger.error("getFeudQuestDetail record size is 0,feudQuestId:" + feudQuestId);
			return null;
		}
		if (fqs.size() > 1) {
			logger.error("getFeudQuestDetail record size more than 1,feudQuestId:" + feudQuestId);
		}
		FeudQuestDetailVO vo = fqs.get(0);
		ErrorCorrection correct = errorCorrectionDao.getQuestionCorrection(teacherId, vo.getQuestRealId());
		vo.setQuestionStatus(-1);
		if (correct != null) {
			vo.setQuestionStatus(correct.getCheckStatus());
		}
		Map paramMap=new HashMap();
    	paramMap.put("teacherId", teacherId);
    	paramMap.put("questionRealId",vo.getQuestRealId());
    	if(audioUploadDao.queryByTeacherIdAndQuestionId(paramMap)!=null){
    		vo.setHasRecorded(true);
    	}
		return vo;
	}

	/**
	 * 新增学生过来的抢答记录
	 * 
	 * @param feudQuest
	 * @return
	 */
	private boolean dealSaveFeudQuest(FeudQuest feudQuest) {
		Long realId = new Long(feudQuest.getQuestionRealId());
		// 先查询是否答案已经拉过来
		Question qus = questionV2Dao.queryByRealId(realId);
		if (qus == null) {
			Question question = null;
			try {
				question = questionService.loadQuestion(realId);
				if (question == null) {
					// 查找题目出错
					logger.error("远程拉取题目时无法查找题目-realId:" + realId);
					return false;
				}
				question.setEmergencyCount(1);
				question.setAllotCount(0);
				question.setSource(String.valueOf(AudioSource.feud.getValue()));
				question.setLearnPhase("未知");
		 		questionV2Dao.insert(question);
			} catch (NumberFormatException e) {
				logger.error("远程拉取题目时类型转换出错-realId:" + realId);
				return false;

			} catch (Exception e) {
				logger.error("远程拉取题目时异常-realId:" + realId,e);
				return false;
			}
		}
		// 保存学生问题请求
		feudQuest.setStatus(FeudQuestStatus.wait.getValue());
		feudQuestDao.insert(feudQuest);
		logger.info("student feudQuest insert success realId:" + realId + " student:" + feudQuest.getStudentId());
		return true;
	}

	public void testDb() {
		feudDetailWbDao.selectAll();
	}
	/**
	 * 系统运行生成固定的抢答数据
	 */
	public int genFeudQuest(int realSubject,int limit){
		//获取每日任务里limit条记录
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("realSubject", realSubject);
		paramMap.put("limit", limit);
		List<QuestionAllot> questList = taskDao.getTaskListWithFeud(paramMap);
		List<Long> questids = new ArrayList<Long>();
		for(QuestionAllot qa:questList){
			FeudQuest feudQuest = new FeudQuest();
			//设置不存在的学生
			feudQuest.setStudentId(feudSystemStudentId);
			feudQuest.setImageId(feudSystemImageId);
			feudQuest.setQuestionRealId(qa.getQuestionId());
			studentFeudQuest(feudQuest);
			questids.add(qa.getQuestionId());
		}
		taskDao.lockQuestionAllotStatus(questids);
		return questList.size();
		
	}

}
