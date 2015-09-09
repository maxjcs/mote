package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.xuexibao.teacher.common.CommonService;
import com.xuexibao.teacher.common.Status;
import com.xuexibao.teacher.dao.DynamicMessageDao;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Dynamic;
import com.xuexibao.teacher.model.DynamicMessage;
import com.xuexibao.teacher.model.iter.IDynmicCommentCount;
import com.xuexibao.teacher.util.PublicUtil;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * 
 * @author oldlu
 *
 */
@Service
@Transactional
public class DynamicMessageService {
	@Resource
	private DynamicMessageDao dynamicMessageDao;
	@Resource
	private CommonService commonService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	public void wrapperDynamicCountComment(List<? extends IDynmicCommentCount> list) {
		if (!CollectionUtils.isEmpty(list)) {
			List<Long> ids = new ArrayList<Long>();
			List<String> keys = new ArrayList<String>();
			List<Long> noCacheIds = new ArrayList<Long>();
			Map<Long, IDynmicCommentCount> map = new HashMap<Long, IDynmicCommentCount>();

			for (IDynmicCommentCount item : list) {
				ids.add(item.getDynamicId());
				keys.add(RedisContstant.TEACHER_DYNAMIC_COMMENT_CACHE_KEY + item.getDynamicId());

				map.put(item.getDynamicId(), item);
			}

			List<String> redisList = stringRedisTemplate.opsForValue().multiGet(keys);
			Map<Object, Object> cacheMap = new HashMap<Object, Object>();

			for (int i = 0, size = redisList.size(); i < size; i++) {
				String redisValue = redisList.get(i);
				Long id = ids.get(i);
				if (PublicUtil.isEmpty(redisValue)) {
					noCacheIds.add(id);
					cacheMap.put(id, 0);
				} else {
					map.get(id).setCountComment(Integer.parseInt(redisValue));
				}
			}

			if (!CollectionUtils.isEmpty(noCacheIds)) {
				List<Dynamic> groupCounts = dynamicMessageDao.groupCountMesasgeByDynamicIds(noCacheIds);

				for (Dynamic item : groupCounts) {
					cacheMap.put(item.getDynamicId(), item.getCountComment());
					map.get(item.getDynamicId()).setCountComment(item.getCountComment());
				}

				Map<String, String> sendRedisMap = new HashMap<String, String>();
				for (Map.Entry<Object, Object> entry : cacheMap.entrySet()) {
					sendRedisMap.put(RedisContstant.TEACHER_DYNAMIC_COMMENT_CACHE_KEY + entry.getKey(),
							entry.getValue() + "");
				}
				stringRedisTemplate.opsForValue().multiSet(sendRedisMap);
			}
		}
	}

	public DynamicMessage loadDynamicMessage(Long id) {
		return dynamicMessageDao.loadDynamicMessage(id);
	}

	public void addMessage(DynamicMessage message) {
		message.setCreateTime(new Date());
		message.setUpdateTime(new Date());
		message.setStatus(Status.STATUS_Y);

		if (message.getReplyId() != null && message.getReplyId() > 0) {
			DynamicMessage replyMsg = dynamicMessageDao.loadDynamicMessage(message.getReplyId());
			if (replyMsg != null) {
				message.setRepliedType(replyMsg.getType());

				if (replyMsg.getType() == DynamicMessage.TYPE_TEACHER_SEND) {
					message.setRepliedUserId(replyMsg.getTeacherId());
				}
				if (replyMsg.getType() == DynamicMessage.TYPE_STUDENT_SEND) {
					message.setRepliedUserId(replyMsg.getStudentId());
				}
			}
		}

		dynamicMessageDao.addDynamicMessage(message);
	}

	public List<DynamicMessage> listMessage(Map<String, Object> paramMap, String teacherId, String studentId) {
		List<DynamicMessage> list = dynamicMessageDao.listDynamicMessage(paramMap);

		commonService.wrapperDynamicMessageRepliedUserName(list);
		commonService.wrapperTeacher(list);
		commonService.wrapperStudent(list);
		wrapperMessageDeletable(list, teacherId, studentId);
		return list;
	}

	private void wrapperMessageDeletable(List<DynamicMessage> list, String teacherId, String studentId) {
		if (!CollectionUtils.isEmpty(list)) {
			for (DynamicMessage item : list) {
				if (item.getType() == DynamicMessage.TYPE_TEACHER_SEND && teacherId != null
						&& StringUtils.equals(teacherId, item.getTeacherId())) {
					item.setCanDelete(true);
				}
				if (item.getType() == DynamicMessage.TYPE_STUDENT_SEND && studentId != null
						&& StringUtils.equals(studentId, item.getStudentId())) {
					item.setCanDelete(true);
				}
			}
		}
	}

	public DynamicMessage removeMessage(Long id, String teacherId, String studentId) {
		DynamicMessage message = dynamicMessageDao.loadDynamicMessage(id);
		if (message == null) {
			throw new BusinessException("评论消息不存在");
		}
		if (message.getStatus() == Status.STATUS_N) {
			throw new BusinessException("评论消息已经删除");
		}
		if (!StringUtils.isEmpty(teacherId) && !(StringUtils.equals(teacherId, message.getTeacherId())
				&& message.getType() == DynamicMessage.TYPE_TEACHER_SEND)) {
			throw new BusinessException("评论消不属于该老师不能删除");
		}
		if (!StringUtils.isEmpty(studentId) && !(StringUtils.equals(teacherId, message.getTeacherId())
				&& message.getType() == DynamicMessage.TYPE_STUDENT_SEND)) {
			throw new BusinessException("评论消息不属于该学生不能删除");
		}
		dynamicMessageDao.deleteDynamicMessage(id);

		return message;
	}

	public DynamicMessage systemRemoveMessage(Long id) {
		DynamicMessage message = dynamicMessageDao.loadDynamicMessage(id);
		if (message == null) {
			throw new BusinessException("评论消息不存在");
		}
		if (message.getStatus() == Status.STATUS_N) {
			throw new BusinessException("评论消息已经删除");
		}

		dynamicMessageDao.deleteDynamicMessage(id);

		return message;
	}

	public DynamicMessage systemRestoreMessage(Long id) {
		DynamicMessage message = dynamicMessageDao.loadDynamicMessage(id);
		if (message == null) {
			throw new BusinessException("评论消息不存在");
		}
		if (message.getStatus() == Status.STATUS_Y) {
			throw new BusinessException("评论消息已经恢复");
		}

		dynamicMessageDao.restoreDynamicMessage(id);

		return message;
	}

}
