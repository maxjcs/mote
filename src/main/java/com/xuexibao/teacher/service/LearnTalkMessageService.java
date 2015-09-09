package com.xuexibao.teacher.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vdurmont.emoji.EmojiParser;
import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.constant.PushConstant;
import com.xuexibao.teacher.dao.AudioDao;
import com.xuexibao.teacher.dao.FeudAnswerDetailDao;
import com.xuexibao.teacher.dao.LearnTalkMessageDao;
import com.xuexibao.teacher.dao.QuestionV2Dao;
import com.xuexibao.teacher.enums.AudioSource;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.FeudAnswerDetail;
import com.xuexibao.teacher.model.LearnMessage;
import com.xuexibao.teacher.model.LearnTalk;
import com.xuexibao.teacher.model.LearnTalkCount;
import com.xuexibao.teacher.model.LearnTalkTeacher;
import com.xuexibao.teacher.model.Question;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.vo.AudioVO;
import com.xuexibao.teacher.model.vo.LearnMessageAudioVO;
import com.xuexibao.teacher.model.vo.LearnMessageDetailVO;
import com.xuexibao.teacher.model.vo.LearnMessageStudentVO;
import com.xuexibao.teacher.model.vo.LearnMessageTeacherVO;
import com.xuexibao.teacher.model.vo.LearnMessageVO;
import com.xuexibao.teacher.model.vo.PushMsgParamVO;
import com.xuexibao.teacher.pay.dao.PayAudioDao;
import com.xuexibao.teacher.pay.model.PayAudio;
import com.xuexibao.teacher.util.PropertyUtil;
import com.xuexibao.teacher.util.RedisContstant;
import com.xuexibao.teacher.util.StudentApiHttpUtil;
import com.xuexibao.webapi.student.client.T_UserService;
import com.xuexibao.webapi.student.model.User;

@Service
public class LearnTalkMessageService {
	private static Logger logger = LoggerFactory.getLogger(LearnTalkMessageService.class);

	@Resource
	private LearnTalkMessageDao learnTalkMessageDao;
	@Resource
	private TeacherService teacherService;
	@Resource
	private PushMsgService pushMsgService;
	@Resource
	private StudentApiService studentApiService;
	@Resource
	TeacherFollowedService teacherFollowedService;
	@Resource
	private AudioDao audioDao;
	@Resource
	private QuestionV2Dao questionV2Dao;
	@Resource
	private QuestionService questionService;
	@Resource
	private PayAudioDao payAudioDao;
	@Resource
	FeudAnswerDetailDao feudAnswerDetailDao;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private T_UserService t_UserService;

	private static final ExecutorService executor = Executors.newFixedThreadPool(10);

	private String studentApiSystemUrl = PropertyUtil.getProperty("studentApiSystemUrl");
	private String teacherSendMsgToStudentPath = "/teacher/api/learnTalk/teacherSendMsgToStudent";

	public List<LearnTalk> learnTalkList(String teacherId, int page) {
		List<LearnTalk> learnTalkList = new ArrayList<LearnTalk>();

		// 从数据库中返回10条数据
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("teacher_id", teacherId);
		param.put("pageSize", PageConstant.PAGE_SIZE_10);
		param.put("offset", (page - 1) * PageConstant.PAGE_SIZE_10);

		List<Long> idList = learnTalkMessageDao.learnTalkListSubIds(param);

		if (CollectionUtils.isEmpty(idList)) {
			return learnTalkList;
		}
		param.put("ids", idList);

		List<LearnMessage> list = learnTalkMessageDao.learnTalkformIds(param);

		if (CollectionUtils.isEmpty(list)) {
			return learnTalkList;
		}

		// 获取学生ID列表
		Set<String> userSet = getStudentList(list);

		Map<String, User> usersInfo = studentApiService.getUsersInfo(userSet);
		Teacher teacher = teacherService.getRequiredTeacher(teacherId);

		for (LearnMessage msg : list) {
			LearnTalk learnTalk = new LearnTalk();

			int unReadMessageNumber = countTeacherUnReadMessage(msg.getStudentId(), teacherId, msg.getTeacherOfferId())
					.getNumber();

			learnTalk.setUnReadMessageNumber(unReadMessageNumber);
			if (msg.getSendMsgUserType() == LearnMessage.TEACHER_SEND) {
				learnTalk.setStudentAvatarUrl(teacher.getAvatarUrl());
				learnTalk.setStudentNick(teacher.getWrapperNickname());
			} else if (usersInfo.containsKey(msg.getStudentId())) {
				User user = usersInfo.get(msg.getStudentId());
				if (user != null) {
					learnTalk.setStudentAvatarUrl(user.getProfile_image_url());
					learnTalk.setStudentNick(user.getLoginname());
				}
			}

			learnTalk.setId(msg.getId());
			learnTalk.setContent(msg.getEmojiContent());
			learnTalk.setMsgType(msg.getMsgType());
			learnTalk.setCreateTime(msg.getCreateTime());
			learnTalk.setStudentId(msg.getStudentId());
			learnTalk.setTeacherOfferId(msg.getTeacherOfferId());
			learnTalk.setTeacherOfferType(msg.getTeacherOfferType());
			learnTalk.setDialogType(msg.getDialogType());

			Question question = this.getQuestionByAudioId(msg.getTeacherOfferId());
			if (question != null) {
				learnTalk.setTeacherOfferDesc(question.getSub50Latex());
				learnTalk.setQuestionId(question.getRealId());
			}

			learnTalkList.add(learnTalk);
		}
		// 返回数据
		return learnTalkList;
	}

	private static Set<String> getStudentList(List<LearnMessage> list) {
		Set<String> userSet = new HashSet<String>();
		for (LearnMessage lm : list) {
			userSet.add(lm.getStudentId());
		}
		return userSet;
	}

	public List<LearnTalkTeacher> learnTalkStudentList(String studentId, int page) {
		List<LearnTalkTeacher> result = new ArrayList<LearnTalkTeacher>();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("studentId", studentId);
		param.put("pageSize", PageConstant.PAGE_SIZE_10);
		param.put("offset", (page - 1) * PageConstant.PAGE_SIZE_10);

		List<Long> idList = learnTalkMessageDao.learnTalkStudentListSubIds(param);

		if (CollectionUtils.isEmpty(idList)) {
			return result;
		}

		param.put("ids", idList);

		List<LearnMessage> list = learnTalkMessageDao.learnTalkformIds(param);
		if (CollectionUtils.isEmpty(list)) {
			return result;
		}

		User user = t_UserService.getUserById(studentId);

		for (LearnMessage lm : list) {
			LearnTalkTeacher ltt = new LearnTalkTeacher();
			int unReadMessageNumber = countStudentUnReadMessage(lm.getStudentId(), lm.getTeacherId(),
					lm.getTeacherOfferId()).getNumber();

			ltt.setUnReadMessageNumber(unReadMessageNumber);
			if (lm.getSendMsgUserType() == LearnMessage.TEACHER_SEND) {
				Teacher teacher = teacherService.getTeacher(lm.getTeacherId());
				if (teacher != null) {
					ltt.setTeacherAvatarUrl(teacher.getAvatarUrl());
					ltt.setTeacherName(teacher.getWrapperNickname());
					if (StringUtils.isEmpty(ltt.getTeacherName())) {
						ltt.setTeacherName(teacher.getWrapperName());
					}
				} else {
					logger.error("教师ID：" + lm.getTeacherId() + "不存在！");
				}
			} else if (StringUtils.equals(lm.getStudentId(), studentId)) {
				if (user != null) {
					ltt.setTeacherAvatarUrl(user.getProfile_image_url());
					ltt.setTeacherName(user.getLoginname());
				}
			}

			ltt.setId(lm.getId());
			ltt.setContent(lm.getEmojiContent());
			ltt.setMsgType(lm.getMsgType());
			ltt.setDialogType(lm.getDialogType());
			ltt.setCreateTime(lm.getCreateTime());
			ltt.setTeacherId(lm.getTeacherId());
			ltt.setTeacherOfferId(lm.getTeacherOfferId());
			ltt.setTeacherOfferType(lm.getTeacherOfferType());
			Question question = this.getQuestionByAudioId(lm.getTeacherOfferId());
			if (question != null) {
				ltt.setTeacherOfferDesc(question.getSub50Latex());
				ltt.setQuestionId(question.getRealId());
			}

			result.add(ltt);
		}

		return result;
	}

	public Map<String, Object> learnTalkDetail(String teacherId, String studentId, String teacherOfferId,
			int teacherOfferTpye, Integer dialogType, Long timestamp, Integer pageSize) {
		return this.learnTalkDetail(1, teacherId, studentId, teacherOfferId, teacherOfferTpye, dialogType, timestamp,
				pageSize);
	}

	public Map<String, Object> learnTalkStudentRecentMessage(String teacherId, String studentId, String teacherOfferId,
			int teacherOfferTpye, Integer dialogType, Long timestamp) {
		return this.learnTalkDetail(2, teacherId, studentId, teacherOfferId, teacherOfferTpye, dialogType, timestamp,
				null);
	}

	private Map<String, Object> learnTalkDetail(int type, String teacherId, String studentId, String teacherOfferId,
			int teacherOfferTpye, Integer dialogType, Long timestamp, Integer pageSize) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<LearnMessageDetailVO> learnMessageVOs = new ArrayList<LearnMessageDetailVO>();

		boolean isFollowed = teacherFollowedService.isFollowed(teacherId, studentId);
		returnMap.put("isFollowed", isFollowed);

		Teacher teacher = teacherService.getTeacher(teacherId);
		LearnMessageTeacherVO teacherVO = new LearnMessageTeacherVO();
		teacherVO.setTeacherId(teacherId);
		if (null != teacher) {
			teacherVO.setTeacherImgUrl(teacher.getAvatarUrl());
			teacherVO.setTeacherName(teacher.getWrapperNickname());
			teacherVO.setTeacherGender(teacher.getGender());
		}
		returnMap.put("teacher", teacherVO);

		LearnMessageStudentVO studentVO = new LearnMessageStudentVO();
		// 获取学生对象
		User user = t_UserService.getUserById(studentId);
		studentVO.setStudentId(studentId);
		if (user != null) {
			studentVO.setStudentName(user.getLoginname());
			studentVO.setStudentImgUrl(user.getProfile_image_url());
			studentVO.setStudentGender(user.getGender());
		}
		returnMap.put("student", studentVO);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dialogType", dialogType);
		param.put("teacher_id", teacherId);
		param.put("student_id", studentId);
		param.put("teacher_offer_id", teacherOfferId);
		param.put("teacher_offer_type", teacherOfferTpye);
		param.put("timestamp", new Timestamp(timestamp));
		if (null == pageSize)
			param.put("pageSize", PageConstant.PAGE_SIZE_10);
		else
			param.put("pageSize", pageSize);

		List<LearnMessage> learnMessages;
		if (type == 1) {
			learnMessages = learnTalkMessageDao.learnTalkDetail(param);
		} else {
			learnMessages = learnTalkMessageDao.learnTalkStudentRecentMessage(param);
		}

		Collections.reverse(learnMessages);

		for (LearnMessage msg : learnMessages) {
			LearnMessageDetailVO msgVO = new LearnMessageDetailVO();
			msgVO.setId(msg.getId());
			msgVO.setTeacherId(msg.getTeacherId());
			msgVO.setStudentId(msg.getStudentId());
			msgVO.setSendMsgUserType(msg.getSendMsgUserType());
			msgVO.setContent(msg.getEmojiContent());
			msgVO.setMsgType(msg.getMsgType());
			msgVO.setCreateTime(msg.getCreateTime());
			learnMessageVOs.add(msgVO);
		}
		returnMap.put("learnMessages", learnMessageVOs);

		LearnMessageAudioVO audioVO = new LearnMessageAudioVO();
		audioVO.setTeacherOfferId(teacherOfferId);
		audioVO.setTeacherOfferType(teacherOfferTpye);

		Question question = this.getQuestionByAudioId(teacherOfferId);
		if (question != null) {
			audioVO.setTeacherOfferDesc(question.getSub50Latex());
			audioVO.setQuestionId(question.getRealId());
		}

		Audio audio = this.getAudio(teacherOfferId);
		if (audio != null) {
			audioVO.setQuestionId(audio.getQuestionId());
		}
		for (LearnMessage lm : learnMessages) {
			if (null != lm.getImageId()) {
				audioVO.setImageId(lm.getImageId());
				break;
			}
		}

		if (StringUtils.isEmpty(audioVO.getImageId())) {
			audioVO.setImageId(learnTalkMessageDao.learnTalkImageId(param));
		}

		returnMap.put("teacherOffer", audioVO);

		return returnMap;
	}

	/**
	 * 
	 * @param teacherId
	 *            教师ID
	 * @param course_ware_id
	 *            课件ID
	 * @param sendToStudentId
	 *            学生ID
	 * @param msg
	 *            消息内容
	 * @param img
	 *            图片URL
	 * @return
	 */
	public LearnMessageVO insertTeacherSendMsg(String teacherId, String teacherOfferId, int teacherOfferType,
			String teacherOfferDesc, Integer dialogType, String sendToStudentId, String msg, String img) {

		LearnMessage learnMessage = new LearnMessage();
		learnMessage.setCreateTime(new Date());
		if (!StringUtils.isBlank(msg)) {
			learnMessage.setMsgType(1); // 如果文本消息 msgType = 1
			learnMessage.setContent(EmojiParser.parseToAliases(msg));
		} else if (!StringUtils.isBlank(img)) {
			learnMessage.setMsgType(2); // 如果是图片 msgType = 2
			learnMessage.setContent(img);
		}
		learnMessage.setSendMsgUserType(LearnMessage.TEACHER_SEND);// 1代表Teacher发送消息
		learnMessage.setUpdateTime(new Date());
		learnMessage.setCreateTime(new Date());
		learnMessage.setTeacherId(teacherId);
		learnMessage.setStudentId(sendToStudentId);
		learnMessage.setTeacherOfferId(teacherOfferId);
		learnMessage.setTeacherOfferType(teacherOfferType);
		learnMessage.setTeacherOfferDesc(teacherOfferDesc);
		learnMessage.setIsRead("N");
		learnMessage.setIsStudentRead("N");
		learnMessage.setDialogType(dialogType);

		learnTalkMessageDao.insertLearnMsg(learnMessage);

		// 调用学生的服务端push消息的http接口
		Teacher teacher = teacherService.getTeacher(teacherId);

		doSendPushToStudent(teacher, sendToStudentId, teacherOfferId, teacherOfferType, msg, img);

		// 封装返回数据
		LearnMessageVO msgVO = new LearnMessageVO();
		BeanUtils.copyProperties(learnMessage, msgVO);
		if (null != teacher) {
			msgVO.setTeacherName(teacher.getWrapperNickname());
			msgVO.setTeacherImgUrl(teacher.getAvatarUrl());
		}
		// 获取user对象
		User user = t_UserService.getUserById(msgVO.getStudentId());
		if (null != user) {
			msgVO.setStudentName(user.getLoginname());
		} else {
			logger.error(" 请求学生端的服务接口，学生ID：" + msgVO.getStudentId() + " 不存在！");
		}
		return msgVO;
	}

	@SuppressWarnings("unused")
	private void doSendPushToStudent(Teacher teacher, String sendToStudentId, String teacherOfferId,
			int teacherOfferType, String msg, String img) {
		String url = studentApiSystemUrl + teacherSendMsgToStudentPath;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("studentId", sendToStudentId);
		paramMap.put("teacherOfferId", teacherOfferId);
		paramMap.put("teacherOfferType", teacherOfferType);
		paramMap.put("msg", msg);
		paramMap.put("img", img);
		paramMap.put("teacherId", teacher.getId());

		if (null != teacher) {
			String teacherName = teacher.getWrapperNickname() == null ? "default" : teacher.getWrapperNickname();
			paramMap.put("teacherName", teacherName);
		} else {
			paramMap.put("teacherName", "default");
		}

		// 请求学生端的服务接口，发送推送消息
		try {
			StudentApiHttpUtil.httpPost(url, paramMap);
		} catch (Exception e) {
			logger.error(" 请求学生端的服务接口，发送推送消息失败！参数：" + paramMap);
		}
	}

	/**
	 * 
	 * @param sendToTeacherId
	 *            老师ID
	 * @param studentId
	 *            学生ID
	 * @param course_ware_id
	 *            课件ID
	 * @param msg
	 *            文本消息
	 * @param img
	 *            图片URL
	 * @return
	 */
	public LearnMessageVO insertStudentSendMsg(LearnMessage message) {
		if (!StringUtils.isBlank(message.getMsg())) {
			message.setMsgType(LearnMessage.MSG_TYPE_CONTENT);
			message.setContent(EmojiParser.parseToAliases(message.getMsg()));
		} else if (!StringUtils.isBlank(message.getImg())) {
			message.setMsgType(LearnMessage.MSG_TYPE_IMAGE);
			message.setContent(message.getImg());
		}
		message.setSendMsgUserType(LearnMessage.STUDENT_SEND);
		message.setUpdateTime(new Date());
		message.setCreateTime(new Date());
		message.setIsRead("N");
		message.setIsStudentRead("N");

		learnTalkMessageDao.insertLearnMsg(message);

		LearnTalk learnTalk = new LearnTalk();
		BeanUtils.copyProperties(message, learnTalk);
		// 获取user对象
		User user = t_UserService.getUserById(learnTalk.getStudentId());
		if (user != null) {
			learnTalk.setStudentNick(user.getLoginname());
			learnTalk.setStudentAvatarUrl(user.getProfile_image_url());
		} else {
			logger.error(" 请求中间件的服务接口，学生ID：" + learnTalk.getStudentId() + " 不存在！");
		}
		// 调用发送PUSH消息的接口给对应老师
		Teacher teacher = teacherService.getTeacher(message.getTeacherId());
		if (null != teacher) {
			doPushMsgToTeacher(teacher, learnTalk);
		} else {
			logger.error("发送PUSH消息失败，老师的ID是：" + message.getTeacherId());
		}
		// 封装返回数据
		LearnMessageVO msgVO = new LearnMessageVO();
		BeanUtils.copyProperties(message, msgVO);

		if (null != teacher) {
			msgVO.setTeacherName(teacher.getWrapperNickname());
			msgVO.setTeacherImgUrl(teacher.getAvatarUrl());
		}
		if (null != user) {
			msgVO.setStudentName(user.getLoginname());
		} else {
			logger.error(" 请求中间件的服务接口，学生ID：" + msgVO.getStudentId() + " 不存在！");
		}
		return msgVO;
	}

	private void doPushMsgToTeacher(Teacher teacher, LearnTalk learnMessage) {
		PushMsgParamVO pushMsgParamVO = new PushMsgParamVO();
		pushMsgParamVO.setContent(learnMessage);
		pushMsgParamVO.setTitle(getContentMessage(learnMessage));
		pushMsgParamVO.setCreateTime(new Date().toLocaleString());
		pushMsgParamVO.setUserId(teacher.getId()); // "550f94de3243e071665be10f"
		if (("1").equals(teacher.getLastDeviceType())) {
			pushMsgParamVO.setPhoneType("android");// android or iphone
			pushMsgParamVO.setDeviceUniqueId(teacher.getLastDeviceId()); // "888a12a440e9c47164e83dc58d49ec3f""ab408679125fe8231478deb8080f0af5"
		} else {
			pushMsgParamVO.setPhoneType("iphone");// android or iphone
			pushMsgParamVO.setDeviceUniqueId(teacher.getIosToken());
		}
		pushMsgParamVO.setType(PushConstant.TYPE_HUDONG); // 4010:学生端发送
															// 4020:教师端发送

		this.asynPushMsg(pushMsgParamVO);

	}

	private static String getContentMessage(LearnTalk learnMessage) {
		if (2 == learnMessage.getMsgType()) {
			return "[图片]";
		}
		return learnMessage.getContent();
	}

	public LearnTalkCount countTeacherUnReadMessage(String studentId, String teacherId, String teacherOfferId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("teacher_id", teacherId);
		param.put("student_id", studentId);
		param.put("teacher_offer_id", teacherOfferId);

		return learnTalkMessageDao.countTeacherUnReadMessage(param);
	}

	public LearnTalkCount countStudentUnReadMessage(String student_id, String teacher_id, String teacher_offer_id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("teacher_id", teacher_id);
		param.put("student_id", student_id);
		param.put("teacher_offer_id", teacher_offer_id);

		return learnTalkMessageDao.countStudentUnReadMessage(param);
	}

	public int learnTalkUpdate(String student_id, String teacher_id, String teacher_offer_id, int teacher_offer_type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("teacher_id", teacher_id);
		param.put("student_id", student_id);
		param.put("teacher_offer_id", teacher_offer_id);

		return learnTalkMessageDao.learnTalkUpdate(param);
	}

	public int learnTalkStudentUpdate(String student_id, String teacher_id, String teacher_offer_id,
			int teacher_offer_type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("teacher_id", teacher_id);
		param.put("student_id", student_id);
		param.put("teacher_offer_id", teacher_offer_id);
		param.put("teacher_offer_type", teacher_offer_type);

		return learnTalkMessageDao.learnTalkStudentUpdate(param);
	}

	public Boolean asynPushMsg(PushMsgParamVO pushMsgParamVO) {
		return asyncCall(pushMsgParamVO, 1000);
	}

	private <T> T asyncCall(final PushMsgParamVO pushMsgParamVO, long timeout) {
		Future<T> result = executor.submit(new Callable<T>() {

			@SuppressWarnings("unchecked")
			public T call() throws Exception {
				return (T) pushMsgService.pushMsg(pushMsgParamVO);
			}
		});
		T returnObject = null;
		try {
			returnObject = result.get(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.error("线程池中发现异常，被中断", e);
		} catch (ExecutionException e) {
			logger.error("ExecutionException异常，执行过程异常", e);
		} catch (TimeoutException e) {
			logger.error("超时异常", e);
		}
		return returnObject;
	}

	public int studentNumber(String teacherId) {
		List<Long> ids = learnTalkMessageDao.studentNumber(teacherId);
		return ids.size();
	}

	public Audio getAudio(String audioId) {
		return audioDao.queryAudioById(audioId);
	}

	public Question queryByRealId(Long realId) {
		return questionV2Dao.queryByRealId(realId);
	}

	public FeudAnswerDetail getFeudAnswerDetailByWhiteBoardId(String whiteBoardId) {
		return feudAnswerDetailDao.getFeudAnswerDetailByWhiteBoardId(whiteBoardId);
	}

	/**
	 * 通过应音频ID或者白板的ID获取对应题目的题干前50个字符
	 * 
	 * @param audioId
	 *            对应音频ID或者白板的ID
	 * @param type
	 *            1是音频，2是白板
	 * @return 返回对应题目的前50个字符
	 */

	public Question getQuestionByAudioId(String audioId) {
		// 音频对应的题目的的题干
		Audio audio = this.getAudio(audioId);
		if (audio == null) {
			logger.info("音频ID：" + audioId + " 在audio_upload中为空");
			return getAudioContentFromRemoteServer(audioId);
		}

		Question question = this.queryByRealId(audio.getQuestionId());
		if (question == null) {
			logger.info("音频ID：" + audioId + "对应的QuestionID:" + audio.getQuestionId() + "为空");
			return getQuestionFromRemoteServer(audio.getQuestionId());
		}

		return question;
	}

	private Question getAudioContentFromRemoteServer(String audioId) {
		PayAudio audio = payAudioDao.queryAudioById(audioId);
		if (audio == null) {
			logger.info("音频ID：" + audioId + " 在audio表中为空");
			return null;
		}

		// 先查询是否答案已经拉过来
		Question qus = questionV2Dao.queryByRealId(audio.getQuestionId());
		if (qus == null) {
			return getQuestionFromRemoteServer(audio.getQuestionId());
		}

		return qus;
	}

	/**
	 * 查找Question表中对应的question，如果没有则从远程拉取题目。
	 * 
	 * @param questionId
	 *            题目ID
	 * @return 返回题目的题干，最多50个字符。
	 */
	private Question getQuestionFromRemoteServer(long questionId) {
		Question question = null;
		try {
			question = questionService.loadQuestion(questionId);

			if (question == null) {
				// 查找题目出错
				logger.error("远程拉取题目时，无法查找题目-realId:" + questionId);
				return null;
			}
			question.setEmergencyCount(1);
			question.setAllotCount(0);
			question.setSource(String.valueOf(AudioSource.hudong.getValue()));
			question.setLearnPhase("未知");

			questionV2Dao.insert(question);

			return question;
		} catch (Exception e) {
			logger.error("插入question异常。", e);
		}
		return null;
	}

	public AudioVO getQuestionId(String audioId) {
		Audio audio = this.getAudio(audioId);
		if (null == audio) {
			logger.info("音频ID在Audio_upload中不存在: " + audioId);
			PayAudio la = payAudioDao.queryAudioById(audioId);
			if (null != la) {
				AudioVO audioVO = new AudioVO();
				audioVO.setQuestionId(la.getQuestionId());
				audioVO.setUrl(la.getUrl());
				return audioVO;
			}
			return null;
		}
		AudioVO audioVO = new AudioVO();
		audioVO.setQuestionId(audio.getQuestionId());
		audioVO.setUrl(audio.getUrl());
		return audioVO;
	}

	public Map<Long, Integer> queryIsHudong(String userId, String questionIds) {
		Map<Long, Integer> result = new HashMap<Long, Integer>();

		String[] questionIdArray = questionIds.split(",");

		List<String> redisKeyList = new ArrayList<String>();
		for (String item : questionIdArray) {
			redisKeyList.add(RedisContstant.TEACHER_STUDENT_QUESTION_HUDONG_CACHE_KEY + userId + "," + item);
		}
		// 从缓存中先读
		List<Long> unCacheQuestionIdList = new ArrayList<Long>();
		Set<Long> unCacheQuestionIdSet = new HashSet<Long>();
		List<String> valueList = stringRedisTemplate.opsForValue().multiGet(redisKeyList);
		for (int i = 0, size = valueList.size(); i < size; i++) {
			String redisValue = valueList.get(i);
			Long questionId = Long.parseLong(questionIdArray[i]);
			if (StringUtils.isEmpty(redisValue)) {
				unCacheQuestionIdList.add(questionId);
				unCacheQuestionIdSet.add(questionId);
			} else {
				if (StringUtils.equals(redisValue, "Y")) {
					result.put(questionId, 1);
				} else {
					result.put(questionId, 0);
				}
			}
		}
		// 从数据库中读取并设置缓存
		if (!CollectionUtils.isEmpty(unCacheQuestionIdList)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("questionIdList", unCacheQuestionIdList);
			paramMap.put("userId", userId);

			List<Audio> audioList = audioDao.queryAudioByQuestionIdList(paramMap);// 业务音频表和音频库表老数据ID是不一样的
			List<PayAudio> payAudioList = payAudioDao.queryAudioByQuestionIdList(paramMap);

			List<String> allIdList = new ArrayList<String>();
			Map<String, Long> allIdMap = new HashMap<String, Long>();

			for (Audio audio : audioList) {
				allIdList.add(audio.getId());
				allIdMap.put(audio.getId(), audio.getQuestionId());
			}

			for (Audio audio : payAudioList) {
				allIdList.add(audio.getId());
				allIdMap.put(audio.getId(), audio.getQuestionId());
			}

			paramMap.put("userId", userId);

			Map<String, String> redisKVMap = new HashMap<String, String>();

			if (!CollectionUtils.isEmpty(allIdList)) {
				paramMap.put("allIdList", allIdList);

				List<String> teacherOfferIdList = learnTalkMessageDao.queryIsHudong(paramMap);

				for (String teacherOfferId : teacherOfferIdList) {// 已互动的设置关系
					unCacheQuestionIdSet.remove(teacherOfferId);
					Long questionIdKey = allIdMap.get(teacherOfferId);
					String redisKey = RedisContstant.TEACHER_STUDENT_QUESTION_HUDONG_CACHE_KEY + userId + ","
							+ questionIdKey;
					redisKVMap.put(redisKey, "Y");
					result.put(questionIdKey, 1);
				}
			}

			for (Long questionId : unCacheQuestionIdSet) {// 未互动的设置标记
				String redisKey = RedisContstant.TEACHER_STUDENT_QUESTION_HUDONG_CACHE_KEY + userId + "," + questionId;
				redisKVMap.put(redisKey, "N");
				result.put(questionId, 0);
			}

			stringRedisTemplate.opsForValue().multiSet(redisKVMap);
		}

		return result;
	}

	public void updateStudentQuestionHuDongCache(String studentId, String teacherOfferId) {
		Audio audio = audioDao.queryAudioById(teacherOfferId);
		if (audio != null && audio.getQuestionId() > 0) {
			String redisKey = RedisContstant.TEACHER_STUDENT_QUESTION_HUDONG_CACHE_KEY + studentId + ","
					+ audio.getQuestionId();
			String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
			if (!StringUtils.equals(redisValue, "Y")) {
				stringRedisTemplate.opsForValue().set(redisKey, "Y");
			}
		}
	}
}
