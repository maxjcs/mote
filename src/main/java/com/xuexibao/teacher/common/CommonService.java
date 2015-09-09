package com.xuexibao.teacher.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xuexibao.teacher.dao.CommonMapper;
import com.xuexibao.teacher.model.AudioApprove;
import com.xuexibao.teacher.model.AudioEvalApprove;
import com.xuexibao.teacher.model.AudioSet;
import com.xuexibao.teacher.model.AudioSetCount;
import com.xuexibao.teacher.model.AudioUpload;
import com.xuexibao.teacher.model.DynamicMessage;
import com.xuexibao.teacher.model.Explanation;
import com.xuexibao.teacher.model.FeudAnswerDetail;
import com.xuexibao.teacher.model.MessageReplyCount;
import com.xuexibao.teacher.model.Question;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.iter.IAudio;
import com.xuexibao.teacher.model.iter.IAudioApprove;
import com.xuexibao.teacher.model.iter.IAudioBuyCount;
import com.xuexibao.teacher.model.iter.IAudioEvaluateCount;
import com.xuexibao.teacher.model.iter.IAudioGoodTag;
import com.xuexibao.teacher.model.iter.IAudioPoint;
import com.xuexibao.teacher.model.iter.IAudioSetBuyCount;
import com.xuexibao.teacher.model.iter.IAudioSetBuyStatus;
import com.xuexibao.teacher.model.iter.IAudioSetCount;
import com.xuexibao.teacher.model.iter.IAudioSetEvaluateCount;
import com.xuexibao.teacher.model.iter.IAudioSetStudentComment;
import com.xuexibao.teacher.model.iter.IAudioTotalPrice;
import com.xuexibao.teacher.model.iter.IExplanation;
import com.xuexibao.teacher.model.iter.IFeudAnswer;
import com.xuexibao.teacher.model.iter.IGradeStr;
import com.xuexibao.teacher.model.iter.ILatex;
import com.xuexibao.teacher.model.iter.IQuestion;
import com.xuexibao.teacher.model.iter.IStudent;
import com.xuexibao.teacher.model.iter.ITeacher;
import com.xuexibao.teacher.model.iter.ITeacherMessageReplyCount;
import com.xuexibao.teacher.model.rpcvo.AudioBuyTotalNumVO;
import com.xuexibao.teacher.model.rpcvo.AudioEvaluateCount;
import com.xuexibao.teacher.model.rpcvo.AudioGoodTag;
import com.xuexibao.teacher.model.rpcvo.AudioSetEvaluateCount;
import com.xuexibao.teacher.model.rpcvo.AudioSetStudentComment;
import com.xuexibao.teacher.service.AudioStudentEvaluationService;
import com.xuexibao.teacher.service.StudentApiService;
import com.xuexibao.teacher.service.TeacherService;
import com.xuexibao.webapi.student.model.User;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class CommonService {
	@Autowired
	private CommonMapper commonMapper;
	@Resource
	private StudentApiService studentApiService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private TeacherService teacherServcie;
	@Autowired
	private AudioStudentEvaluationService audioStudentEvaluationService;

	public void wrapperAudioSetCount(List<? extends IAudioSetCount> list) {
		List<String> ids = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(list)) {
			for (IAudioSetCount item : list) {
				ids.add(item.getSetId());
			}

			List<AudioSetCount> items = commonMapper.listAudioSetDetailCount(ids);

			if (!CollectionUtils.isEmpty(items)) {
				Map<String, AudioSetCount> map = new HashMap<String, AudioSetCount>();
				for (AudioSetCount item : items) {
					map.put(item.getSetId(), item);
				}

				for (IAudioSetCount item : list) {
					AudioSetCount explan = map.get(item.getSetId());
					if (explan != null) {
						item.setAudioSetCount(explan.getCount());
					}
				}
			}
		}
	}

	public void wrapperExplanation(List<? extends IExplanation> list) {
		List<String> ids = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(list)) {
			for (IExplanation item : list) {
				ids.add(item.getAudioId());
			}

			List<Explanation> explans = commonMapper.listExplantationByAudioIds(ids);

			if (!CollectionUtils.isEmpty(explans)) {
				Map<String, Explanation> map = new HashMap<String, Explanation>();
				for (Explanation item : explans) {
					map.put(item.getAudioWhiteboardId(), item);
				}

				for (IExplanation item : list) {
					Explanation explan = map.get(item.getAudioId());
					if (explan != null) {
						item.setImageId(explan.getImgId());
					}
				}
			}
		}
	}

	public void wrapperFeudAnswer(List<? extends IFeudAnswer> list) {
		List<String> ids = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(list)) {
			for (IFeudAnswer item : list) {
				ids.add(item.getAudioId());
			}

			List<FeudAnswerDetail> explans = commonMapper.listFeudAnswerDetailByAudioIds(ids);

			if (!CollectionUtils.isEmpty(explans)) {
				Map<String, FeudAnswerDetail> map = new HashMap<String, FeudAnswerDetail>();
				for (FeudAnswerDetail item : explans) {
					map.put(item.getAudioWhiteboardId(), item);
				}

				for (IFeudAnswer item : list) {
					FeudAnswerDetail explan = map.get(item.getAudioId());
					if (explan != null) {
						item.setFeudAnswerDetailId(explan.getId());
					}
				}
			}
		}
	}

	public void wrapperQuestion(List<? extends IQuestion> list) {
		List<Long> ids = new ArrayList<Long>();
		if (!CollectionUtils.isEmpty(list)) {
			for (IQuestion item : list) {
				ids.add(item.getQuestionId());
			}

			List<Question> questions = commonMapper.listQuestionByRealIds(ids);

			if (!CollectionUtils.isEmpty(questions)) {
				Map<Long, Question> map = new HashMap<Long, Question>();
				for (Question question : questions) {
					map.put(question.getRealId(), question);
				}

				for (IQuestion item : list) {
					Question question = map.get(item.getQuestionId());
					if (question != null) {
						item.setGradeName(question.getLearnPhase());
						// item.setSubjectName(redisUtil.get.getSubjectName(question.getRealSubject()));
					}
				}
			}
		}
	}

	public void wrapperApprove(List<? extends IAudioApprove> list) {
		List<String> ids = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(list)) {
			for (IAudioApprove item : list) {
				ids.add(item.getAudioId());
			}

			List<AudioApprove> approves = commonMapper.listApproveByAudioIds(ids);

			if (!CollectionUtils.isEmpty(approves)) {
				Map<String, AudioApprove> map = new HashMap<String, AudioApprove>();
				for (AudioApprove approve : approves) {
					map.put(approve.getAudioId(), approve);
				}

				for (IAudioApprove item : list) {
					AudioApprove approve = map.get(item.getAudioId());
					if (approve != null) {
						item.setEvalution(approve.getEvalution());
					}
				}
			}
		}
	}

	public void wrapperAudioPoint(List<? extends IAudioPoint> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		List<String> listId = new ArrayList<String>();

		Map<String, IAudioPoint> audioMap = new HashMap<String, IAudioPoint>();
		for (IAudioPoint item : list) {
			listId.add(item.getAudioId());
			audioMap.put(item.getAudioId(), item);
		}

		Map<String, Object> queryAudiosMap = new HashMap<String, Object>();
		queryAudiosMap.put("audioIds", listId);
		List<AudioEvalApprove> aeaList = audioStudentEvaluationService.getPointByAudioIds(queryAudiosMap);

		if (!CollectionUtils.isEmpty(aeaList)) {
			for (AudioEvalApprove aea : aeaList) {
				IAudioPoint rawItem = audioMap.get(aea.getAudioId());
				rawItem.setTotalPoint(aea.getTotalPoint() + aea.getDeductPoint());
			}
		}
	}

	public void wrapperAudioBuyCount(List<? extends IAudioBuyCount> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		List<String> listId = new ArrayList<String>();

		Map<String, IAudioBuyCount> audioMap = new HashMap<String, IAudioBuyCount>();
		for (IAudioBuyCount item : list) {
			listId.add(item.getAudioId());
			audioMap.put(item.getAudioId(), item);
		}

		Map<String, AudioBuyTotalNumVO.Item> map = studentApiService.getAudioTotalPriceAndNum(listId, false);
		if (map != null) {
			for (IAudioBuyCount rawItem : list) {
				AudioBuyTotalNumVO.Item realItem = map.get(rawItem.getAudioId());
				if (realItem != null) {
					rawItem.setSaleNum(realItem.getTotalNum());
					rawItem.setIncome(realItem.getPrice());
				}
			}
		}
	}

	public void wrapperAudioMoney(List<? extends IAudioTotalPrice> list, String teacherId) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		List<String> listId = new ArrayList<String>();

		Map<String, IAudioTotalPrice> audioMap = new HashMap<String, IAudioTotalPrice>();
		for (IAudioTotalPrice item : list) {
			listId.add(item.getAudioId());
			audioMap.put(item.getAudioId(), item);
		}

		Map<String, Integer> map = studentApiService.getAudioTotalPrice(listId, teacherId, false);
		if (map != null) {
			for (IAudioTotalPrice rawItem : list) {
				audioMap.get(rawItem.getAudioId()).setIncome(map.get(rawItem.getAudioId()));
			}
		}
	}

	public void wrapperAudioSetBuyCount(List<? extends IAudioSetBuyCount> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		List<String> listSetId = new ArrayList<String>();

		Map<String, IAudioSetBuyCount> audioSetMap = new HashMap<String, IAudioSetBuyCount>();
		for (IAudioSetBuyCount item : list) {
			listSetId.add(item.getId());
			audioSetMap.put(item.getId(), item);
		}

		Map<String, Integer> map = studentApiService.getAudioSetBuyCount(listSetId, false);
		if (map != null) {
			for (IAudioSetBuyCount item : audioSetMap.values()) {
				item.setCountSales(map.get(item.getId()));
			}
		}
	}

	public void wrapperAudioSetBuyStatus(List<? extends IAudioSetBuyStatus> list, String studentId,
			boolean buyThenRemove) {
		if (StringUtils.isEmpty(studentId)) {
			return;
		}
		List<String> listSetId = new ArrayList<String>();

		Map<String, IAudioSetBuyStatus> audioSetMap = new HashMap<String, IAudioSetBuyStatus>();
		for (IAudioSetBuyStatus item : list) {
			listSetId.add(item.getId());
			audioSetMap.put(item.getId(), item);
		}

		Map<String, Boolean> map = studentApiService.getAudioSetBuyStatus(listSetId, studentId, false);
		if (map != null) {
			List<IAudioSetBuyStatus> removes = new ArrayList<IAudioSetBuyStatus>();
			for (IAudioSetBuyStatus item : list) {
				item.setBuyStatus(map.get(item.getId()));
				if (map.get(item.getId())) {
					removes.add(item);
				}
			}
			if (buyThenRemove) {
				list.removeAll(removes);
			}
		}
	}

	public void wrapperAudioGoodTag(List<? extends IAudioGoodTag> list) {
		List<String> ids = new ArrayList<String>();

		Map<String, IAudioGoodTag> audioMap = new HashMap<String, IAudioGoodTag>();
		for (IAudioGoodTag item : list) {
			ids.add(item.getAudioId());
			audioMap.put(item.getAudioId(), item);
		}

		List<AudioGoodTag.Item> remoteList = studentApiService.getAudioGoodTag(ids, false);
		if (!CollectionUtils.isEmpty(remoteList)) {
			for (AudioGoodTag.Item item : remoteList) {
				IAudioGoodTag rawItem = audioMap.get(item.id);
				if (rawItem != null) {
					rawItem.setGoodTag(item.praise);
				}
			}
		}
	}

	public void wrapperAudio(List<? extends IAudio> list) {
		List<String> ids = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(list)) {
			for (IAudio item : list) {
				ids.add(item.getAudioId());
			}

			List<AudioUpload> audios = commonMapper.listAudioByIds(ids);

			if (!CollectionUtils.isEmpty(audios)) {
				Map<String, AudioUpload> map = new HashMap<String, AudioUpload>();
				for (AudioUpload audio : audios) {
					map.put(audio.getId(), audio);
				}

				for (IAudio item : list) {
					AudioUpload audio = map.get(item.getAudioId());
					if (audio != null) {
						item.setQuestionId(audio.getQuestionId());
						item.setAudioType(audio.getSource());
					}
				}
			}
		}
	}

	public void wrapperLatex(List<? extends ILatex> list) {
		List<Long> ids = new ArrayList<Long>();
		if (!CollectionUtils.isEmpty(list)) {
			for (ILatex item : list) {
				ids.add(item.getQuestionId());
			}

			List<Question> questions = commonMapper.listQuestionByRealIds(ids);

			if (!CollectionUtils.isEmpty(questions)) {
				Map<Long, Question> map = new HashMap<Long, Question>();
				for (Question question : questions) {
					map.put(question.getRealId(), question);
				}

				for (ILatex item : list) {
					Question question = map.get(item.getQuestionId());
					if (question != null) {
						item.setLatex(question.getLatex());
					}
				}
			}
		}
	}

	public void wrapperStudent(List<? extends IStudent> list) {
		Set<String> ids = new HashSet<String>();
		if (!CollectionUtils.isEmpty(list)) {
			for (IStudent item : list) {
				if (!StringUtils.isEmpty(item.getStudentId())) {
					ids.add(item.getStudentId());
				}
			}
		}
		if (!CollectionUtils.isEmpty(ids)) {
			HashMap<String, User> studentMap = studentApiService.getUsersInfo(ids);
			if (studentMap == null) {
				return;
			}
			for (IStudent student : list) {
				User user = studentMap.get(student.getStudentId());
				if (user != null) {
					if (StringUtils.isNotBlank(user.getProfile_image_url())) {
						student.setStudentAvatarUrl(user.getProfile_image_url());
					}
					if (StringUtils.isNotBlank(user.getLoginname())) {
						student.setStudentNickname(user.getLoginname());
					}

					Object gender = user.getGender();

					if (gender != null && !StringUtils.isEmpty(gender.toString())) {
						student.setStudentGender(Integer.parseInt(gender.toString()));
					}
				}
			}
		}
	}

	public void wrapperTeacher(List<? extends ITeacher> list) {
		Set<String> ids = new HashSet<String>();
		if (!CollectionUtils.isEmpty(list)) {
			for (ITeacher item : list) {
				ids.add(item.getTeacherId());
			}

			List<Teacher> teachers = commonMapper.listTeacherByIds(ids.toArray(new String[] {}));

			if (!CollectionUtils.isEmpty(teachers)) {
				Map<String, Teacher> map = new HashMap<String, Teacher>();
				for (Teacher teacher : teachers) {
					map.put(teacher.getId(), teacher);
				}

				for (ITeacher item : list) {
					Teacher teacher = map.get(item.getTeacherId());
					if (teacher != null) {
						item.setPhoneNumber(teacher.getPhoneNumber());
						item.setTeacherName(teacher.getWrapperNickname());
						item.setTeacherAvatarUrl(teacher.getAvatarUrl());
						item.setTeacherGender(teacher.getGender());
						item.setTeacherCourseYear(teacher.getCourseYear());
						item.setTeacherStar(teacher.getTeacherStar());
					}
				}
			}
		}
	}

	public void wrapperReplyCount(List<? extends ITeacherMessageReplyCount> list) {
		List<Long> ids = new ArrayList<Long>();
		if (!CollectionUtils.isEmpty(list)) {
			for (ITeacherMessageReplyCount item : list) {
				ids.add(item.getMsgId());
			}

			List<MessageReplyCount> listCount = commonMapper.listTeacherReplyCount(ids);

			if (!CollectionUtils.isEmpty(listCount)) {
				Map<Long, MessageReplyCount> map = new HashMap<Long, MessageReplyCount>();
				for (MessageReplyCount item : listCount) {
					map.put(item.getReplyId(), item);
				}

				for (ITeacherMessageReplyCount item : list) {
					MessageReplyCount message = map.get(item.getMsgId());
					if (message != null) {
						item.setReplyCount(message.getCount());
					}
				}
			}
		}
	}

	public void wrapperAudioSetStudentCommentList(List<? extends IAudioSetStudentComment> list, String studentId) {
		List<String> listId = new ArrayList<String>();

		Map<String, IAudioSetStudentComment> setMap = new HashMap<String, IAudioSetStudentComment>();
		for (IAudioSetStudentComment item : list) {
			listId.add(item.getSetId());
			setMap.put(item.getSetId(), item);
		}

		List<AudioSetStudentComment.Item> remoteList = studentApiService.getAudioSetStudentCommentList(listId,
				studentId, false);

		if (!CollectionUtils.isEmpty(remoteList)) {
			for (AudioSetStudentComment.Item item : remoteList) {
				IAudioSetStudentComment rawItem = setMap.get(item.exercisesId);
				if (rawItem != null) {
					rawItem.setStudentComment(item);
				}
			}
		}
	}

	public void wrapperAudioEvaluateCount(List<? extends IAudioEvaluateCount> list) {
		List<String> listId = new ArrayList<String>();

		Map<String, IAudioEvaluateCount> audioMap = new HashMap<String, IAudioEvaluateCount>();
		for (IAudioEvaluateCount item : list) {
			listId.add(item.getAudioId());
			audioMap.put(item.getAudioId(), item);
		}

		List<AudioEvaluateCount.Item> remoteList = studentApiService.getAudioEvaluateList(listId, false);
		if (!CollectionUtils.isEmpty(remoteList)) {
			for (AudioEvaluateCount.Item item : remoteList) {
				IAudioEvaluateCount rawItem = audioMap.get(item.audioId);
				if (rawItem != null) {
					rawItem.setMediumEvaNum(item.midCounts);
					rawItem.setBadEvaNum(item.badCounts);
					rawItem.setGoodEvaNum(item.goodCounts);
				}
			}
		}
	}

	public void wrapperAudioSetCommentCount(List<AudioSet> list) {
		List<String> listId = new ArrayList<String>();

		Map<String, IAudioSetEvaluateCount> audioSetMap = new HashMap<String, IAudioSetEvaluateCount>();

		for (IAudioSetEvaluateCount item : list) {
			listId.add(item.getSetId());
			audioSetMap.put(item.getSetId(), item);
		}

		Map<String, AudioSetEvaluateCount.Item> remoteMap = studentApiService.getAudioSetEvaluateDetail(listId, false);
		if (!CollectionUtils.isEmpty(remoteMap)) {
			for (IAudioSetEvaluateCount rawItem : list) {
				AudioSetEvaluateCount.Item remoteItem = remoteMap.get(rawItem.getSetId());
				if (remoteItem != null) {
					rawItem.setMidCounts(remoteItem.getMidCounts());
					rawItem.setBadCounts(remoteItem.getBadCounts());
					rawItem.setGoodCounts(remoteItem.getGoodCounts());
				}
			}
		}
	}

	public void wrapperGradeStr(List<? extends IGradeStr> list, String append) {
		if (!CollectionUtils.isEmpty(list)) {
			for (IGradeStr item : list) {
				item.setGradeStr(GradeUtil.getGradeString(item.getGradeIdsStr(), append));
			}
		}
	}

	public void wrapperDynamicMessageRepliedUserName(List<DynamicMessage> list) {
		if (!CollectionUtils.isEmpty(list)) {
			Set<String> teacherIds = new HashSet<String>();
			Set<String> studentIds = new HashSet<String>();

			for (DynamicMessage item : list) {
				if (!StringUtils.isEmpty(item.getRepliedUserId())) {
					if (item.getType() == DynamicMessage.TYPE_TEACHER_SEND) {
						teacherIds.add(item.getRepliedUserId());
					}
					if (item.getType() == DynamicMessage.TYPE_STUDENT_SEND) {
						studentIds.add(item.getRepliedUserId());
					}
				}
			}

			Map<String, String> nameMap = new HashMap<String, String>();

			if (!CollectionUtils.isEmpty(teacherIds)) {
				for (String teacherId : teacherIds) {
					Teacher teacher = teacherServcie.getTeacher(teacherId);
					if (teacher != null) {
						nameMap.put(teacherId, teacher.getWrapperNickname());
					}
				}
			}

			if (!CollectionUtils.isEmpty(studentIds)) {
				HashMap<String, User> studentMap = studentApiService.getUsersInfo(studentIds);
				if (studentMap != null) {
					for (String studentId : studentMap.keySet()) {
						User user = studentMap.get(studentId);
						if (user != null) {
							nameMap.put(studentId, user.getLoginname());
						}
					}
				}
			}

			for (DynamicMessage item : list) {
				item.setRepliedUserName(nameMap.get(item.getRepliedUserId()));
			}
		}

	}
}
