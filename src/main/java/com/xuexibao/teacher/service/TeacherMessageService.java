package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vdurmont.emoji.EmojiParser;
import com.xuexibao.teacher.common.CommonService;
import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.dao.TeacherMessageDao;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.TeacherMessage;
import com.xuexibao.teacher.model.vo.TeacherMessageVO;
import com.xuexibao.teacher.util.RedisContstant;
import com.xuexibao.webapi.student.model.User;

@Service
public class TeacherMessageService {
	@Resource
	private TeacherMessageDao teacherMessageDao;
	@Resource
	private TeacherService teacherService;
	@Resource
	private StudentApiService studentApiService;
	@Resource
	private CommonService commonService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	public TeacherMessage addTeacherMessage(String teacherId, String studentId, Long replyId, String content,
			Integer type) {
		TeacherMessage last = teacherMessageDao.getLastMessageByTeacherId(teacherId);
		TeacherMessage message = new TeacherMessage();
		message.setStudentId(studentId);
		message.setTeacherId(teacherId);
		message.setContent(EmojiParser.parseToAliases(content));
		message.setType(type);
		message.setCreateTime(new Date());
		message.setUpdateTime(new Date());
		if (last != null) {
			message.setFloor(last.getFloor() + 1);
		} else {
			message.setFloor(1);
		}
		if (replyId != null && replyId > 0) {
			TeacherMessage reply = teacherMessageDao.getMessageById(replyId);
			message.setReplyId(replyId);
			if (reply == null) {
				throw new BusinessException("回复的楼层数据不存在");
			}
			message.setReplyFloor(reply.getFloor());
		}

		teacherMessageDao.addTeacherMessage(message);
		return message;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> getTeacherMessage(String teacherId, Integer pageNo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Teacher teacher = teacherService.getRequiredTeacher(teacherId);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);
		paramMap.put("pageSize", PageConstant.PAGE_SIZE_10);
		paramMap.put("offset", (pageNo - 1) * PageConstant.PAGE_SIZE_10);
		List<TeacherMessage> resultList = teacherMessageDao.getTeacherMessage(paramMap);
		List<TeacherMessageVO> resultVOList = new ArrayList<TeacherMessageVO>();

		if (!CollectionUtils.isEmpty(resultList)) {
			
			Set<String> studentIds = getStudentList(resultList);

			HashMap<String, User> usersInfo = studentApiService.getUsersInfo(studentIds);

			for (TeacherMessage tm : resultList) {
				TeacherMessageVO vo = new TeacherMessageVO();
				vo.setId(tm.getId());
				vo.setContent(tm.getEmojiContent());
				vo.setCreateTime(tm.getCreateTime());
				vo.setUpdateTime(tm.getUpdateTime());
				vo.setType(tm.getType());
				if (TeacherMessage.STUDENT_SEND == tm.getType()) {
					if (usersInfo.containsKey(tm.getStudentId())) {
						User user=usersInfo.get(tm.getStudentId());
						if(user!=null){
							vo.setName(user.getLoginname());
							vo.setImageUrl(user.getProfile_image_url());
							vo.setGender(user.getGender());// 1:男；//
						}
					}
				} else if (TeacherMessage.TEACHER_SEND == tm.getType()) {
					vo.setName(teacher.getWrapperNickname());
					vo.setImageUrl(teacher.getAvatarUrl());
					vo.setGender(teacher.getGender()); // '性别 1：男 2：女',
				}
				resultVOList.add(vo);
			}
			resultMap.put("teacherMessage", resultVOList);
		} else {
			resultMap.put("teacherMessage", new ArrayList<TeacherMessageVO>());
		}
		resultMap.put("teacherMessageCount", teacherMessageDao.countMessageByTeacherId(teacherId));
		return resultMap;
	}

	
	private static Set<String> getStudentList(List<TeacherMessage> list) {
		Set<String> set = new HashSet<String>();
		for (TeacherMessage item : list) {
			if(StringUtils.isNotEmpty(item.getStudentId())){
				set.add(item.getStudentId());
			}
		}
		return set;
	}

	public Map<String, Object> summaryTeacherMessage(String teacherId, String studentId) {
		Map<String, Object> result = teacherMessageDao.summaryTeacherMessage(teacherId);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);
		paramMap.put("firstIdOfLastPage", TeacherMessage.LAST_ITEM_ID);
		paramMap.put("limit", 5);

		List<TeacherMessage> list = teacherMessageDao.nextPageTeacherMessage(paramMap);
		commonService.wrapperStudent(list);
		commonService.wrapperTeacher(list);
		commonService.wrapperReplyCount(list);

		wrapperMessageDeletable(list, teacherId, studentId);
		wrapperParentMessage(list, teacherId, studentId);

		result.put("messages", list);
		return result;
	}

	private void wrapperParentMessage(List<TeacherMessage> list, String teacherId, String studentId) {
		if (!CollectionUtils.isEmpty(list)) {
			List<Long> ids = new ArrayList<Long>();
			Map<Long, TeacherMessage> sonMap = new HashMap<Long, TeacherMessage>();

			for (TeacherMessage item : list) {
				ids.add(item.getReplyId());
				sonMap.put(item.getId(), item);
			}

			List<TeacherMessage> parents = teacherMessageDao.listMesageByIds(ids);

			if (!CollectionUtils.isEmpty(parents)) {
				Map<Long, TeacherMessage> parentMap = new HashMap<Long, TeacherMessage>();
				for (TeacherMessage item : parents) {
					parentMap.put(item.getId(), item);
				}

				for (TeacherMessage item : list) {
					TeacherMessage son = sonMap.get(item.getId());
					item.setParentMessage(parentMap.get(son.getReplyId()));
				}
			}
		}
	}

	private void wrapperMessageDeletable(List<TeacherMessage> list, String teacherId, String studentId) {
		if (!CollectionUtils.isEmpty(list)) {
			for (TeacherMessage item : list) {
				if (!StringUtils.isEmpty(studentId)) {
					if (item.getType() == TeacherMessage.STUDENT_SEND
							&& StringUtils.equals(studentId, item.getStudentId())) {
						item.setCanDelete(true);
					}
				} else {
					if (item.getType() == TeacherMessage.TEACHER_SEND
							&& StringUtils.equals(teacherId, item.getTeacherId())) {
						item.setCanDelete(true);
					}
				}
			}
		}
	}

	public void removeTeacherMessage(String teacherId, Long messgseId) {
		TeacherMessage message = teacherMessageDao.getMessageById(messgseId);
		if (message == null) {
			throw new BusinessException("消息不存在");
		}
		if (!StringUtils.equals(teacherId, message.getTeacherId())
				|| message.getType() != TeacherMessage.TEACHER_SEND) {
			throw new BusinessException("这条留言不是你这老师的，不能删除");
		}

		teacherMessageDao.removeTeacherMessage(messgseId);
	}

	public void removeStudentMessage(String studentId, Long messgseId) {
		TeacherMessage message = teacherMessageDao.getMessageById(messgseId);
		if (message == null) {
			throw new BusinessException("消息不存在");
		}
		if (!StringUtils.equals(studentId, message.getStudentId())
				|| message.getType() != TeacherMessage.STUDENT_SEND) {
			throw new BusinessException("这条留言不是你这学生的，不能删除");
		}

		teacherMessageDao.removeTeacherMessage(messgseId);
	}

	public Map<String, Object> firstPageMessageForTeacher(String teacherId, String studentId) {
		Map<String, Object> result = teacherMessageDao.summaryTeacherMessage(teacherId);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);
		paramMap.put("limit", PageConstant.PAGE_SIZE_10);
		paramMap.put("firstIdOfLastPage", TeacherMessage.LAST_ITEM_ID);

		List<TeacherMessage> list = teacherMessageDao.nextPageTeacherMessage(paramMap);
		commonService.wrapperStudent(list);
		commonService.wrapperTeacher(list);
		commonService.wrapperReplyCount(list);
		wrapperMessageDeletable(list, teacherId, studentId);
		wrapperParentMessage(list, teacherId, studentId);

		Long hotMsgId = teacherMessageDao.getHotMessageIdByTeacherId(teacherId);
		if (hotMsgId != null) {
			TeacherMessage hotMessage = wrapperMessage(teacherMessageDao.getMessageById(hotMsgId), teacherId,
					studentId);
			result.put("hotMessage", hotMessage);
		}

		result.put("messages", list);
		return result;
	}

	public Map<String, Object> firstPageReplyMessageForTeacher(String teacherId, String studentId, Long replyId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);
		paramMap.put("limit", PageConstant.PAGE_SIZE_10);
		paramMap.put("replyId", replyId);
		paramMap.put("firstIdOfLastPage", TeacherMessage.LAST_ITEM_ID);

		Map<String, Object> result = teacherMessageDao.summaryReplyMessage(paramMap);

		List<TeacherMessage> list = teacherMessageDao.nextPageReplyMessage(paramMap);
		commonService.wrapperStudent(list);
		commonService.wrapperTeacher(list);
		commonService.wrapperReplyCount(list);
		wrapperMessageDeletable(list, teacherId, studentId);

		TeacherMessage topMessage = wrapperMessage(teacherMessageDao.getMessageById(replyId), teacherId, studentId);
		result.put("topMessage", topMessage);
		result.put("messages", list);

		return result;
	}

	public TeacherMessage wrapperMessage(TeacherMessage message, String teacherId, String studentId) {
		if (message != null) {
			List<TeacherMessage> list = Arrays.asList(message);

			commonService.wrapperStudent(list);
			commonService.wrapperTeacher(list);
			commonService.wrapperReplyCount(list);

			wrapperMessageDeletable(list, teacherId, studentId);
			wrapperParentMessage(list, teacherId, studentId);
		}

		return message;
	}

	public List<TeacherMessage> nextPageTeacherMessage(Map<String, Object> paramMap, String teacherId,
			String studentId) {
		List<TeacherMessage> list = teacherMessageDao.nextPageTeacherMessage(paramMap);
		commonService.wrapperStudent(list);
		commonService.wrapperTeacher(list);
		commonService.wrapperReplyCount(list);

		wrapperMessageDeletable(list, teacherId, studentId);
		wrapperParentMessage(list, teacherId, studentId);
		return list;
	}

	public List<TeacherMessage> nextPageReplyMessage(Map<String, Object> paramMap, String teacherId, String studentId) {
		List<TeacherMessage> list = teacherMessageDao.nextPageReplyMessage(paramMap);
		commonService.wrapperStudent(list);
		commonService.wrapperTeacher(list);
		commonService.wrapperReplyCount(list);

		wrapperMessageDeletable(list, teacherId, studentId);
		return list;
	}

	/**
	 * redis增加教师留言数
	 * 
	 * @param teacherId
	 */
	public void addMessageNumRedis(String teacherId) {
		String messageNum = stringRedisTemplate.opsForValue()
				.get(RedisContstant.TEACHER_MESSAGENUM_CACHE_KEY + teacherId);
		if (StringUtils.isBlank(messageNum)) {
			Integer newMessageNum = teacherMessageDao.getMessageNumByTeacherId(teacherId);
			stringRedisTemplate.opsForValue().increment(RedisContstant.TEACHER_MESSAGENUM_CACHE_KEY + teacherId,
					new Long(newMessageNum));
		} else {
			stringRedisTemplate.opsForValue().increment(RedisContstant.TEACHER_MESSAGENUM_CACHE_KEY + teacherId, 1L);
		}
	}
	
	/**
	 * redis删除教师留言数
	 * 
	 * @param teacherId
	 */
	public void removeMessageNumRedis(String teacherId) {
		String messageNum = stringRedisTemplate.opsForValue()
				.get(RedisContstant.TEACHER_MESSAGENUM_CACHE_KEY + teacherId);
		if (StringUtils.isBlank(messageNum)) {
			Integer newMessageNum = teacherMessageDao.getMessageNumByTeacherId(teacherId);
			stringRedisTemplate.opsForValue().increment(RedisContstant.TEACHER_MESSAGENUM_CACHE_KEY + teacherId,
					new Long(newMessageNum));
		} else {
			stringRedisTemplate.opsForValue().increment(RedisContstant.TEACHER_MESSAGENUM_CACHE_KEY + teacherId, -1L);
		}
	}

	/**
	 * 获取留言总数
	 * 
	 * @param teacherIds
	 * @return
	 */
	public Map<String, Long> getMessageNum(List<String> teacherIdList) {
		Set<String> teacherIdSet=new HashSet<String>();
		teacherIdSet.addAll(teacherIdList);
		List<String> teacherIds=new ArrayList<String>();
		teacherIds.addAll(teacherIdSet);
		List<String> keys = new ArrayList<String>();
		for (String teacherId : teacherIds) {
			keys.add(RedisContstant.TEACHER_MESSAGENUM_CACHE_KEY + teacherId);
		}
		List<String> messageNums = stringRedisTemplate.opsForValue().multiGet(keys);
		Map<String, Long> resultlMap = new HashMap<String, Long>();
		for (int i = 0; i < teacherIds.size(); i++) {
			String messageNum = messageNums.get(i);
			if (StringUtils.isBlank(messageNum)) {
				Integer newMessageNum = teacherMessageDao.getMessageNumByTeacherId(teacherIds.get(i));
				stringRedisTemplate.opsForValue().increment(
						RedisContstant.TEACHER_MESSAGENUM_CACHE_KEY + teacherIds.get(i), new Long(newMessageNum));
				resultlMap.put(teacherIds.get(i), new Long(newMessageNum));
			} else {
				resultlMap.put(teacherIds.get(i), new Long(messageNum));
			}
		}
		return resultlMap;
	}
}
