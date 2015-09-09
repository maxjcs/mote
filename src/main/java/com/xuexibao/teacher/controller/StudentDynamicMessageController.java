package com.xuexibao.teacher.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.exception.ValidateException;
import com.xuexibao.teacher.model.DynamicMessage;
import com.xuexibao.teacher.service.DynamicMessageService;
import com.xuexibao.teacher.util.PublicUtil;
import com.xuexibao.teacher.util.RedisContstant;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("student/dynamicmessage")
public class StudentDynamicMessageController extends AbstractController {
	@Resource
	private DynamicMessageService dynamicMessageService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@ResponseBody
	@RequestMapping(value = "addMessage")
	public Object addMessage(DynamicMessage message) {
		Validator.validateBlank(message.getDynamicId(), "dynamicId不能为空");
		Validator.validateBlank(message.getStudentId(), "studentId不能为空");

		if (StringUtils.isEmpty(message.getImageUrl()) && StringUtils.isEmpty(message.getContent())) {
			throw new ValidateException("图片和内容不能同时为空");
		}

		message.setType(DynamicMessage.TYPE_STUDENT_SEND);

		dynamicMessageService.addMessage(message);

		String key = RedisContstant.TEACHER_DYNAMIC_COMMENT_CACHE_KEY + message.getDynamicId();
		if (!PublicUtil.isEmpty(stringRedisTemplate.opsForValue().get(key))) {
			stringRedisTemplate.opsForValue().increment(key, 1);
		}

		return dataStudentJson(message);
	}

	@ResponseBody
	@RequestMapping(value = "listMessage")
	public Object listMessage(String studentId, Long dynamicId, Long lastId) {
		Validator.validateBlank(dynamicId, "dynamicId不能为空");
		if (lastId == null || lastId == 0) {
			lastId = Long.MAX_VALUE;
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastId", lastId);
		paramMap.put("dynamicId", dynamicId);
		paramMap.put("pageSize", PageConstant.PAGE_SIZE_10);

		List<DynamicMessage> list = dynamicMessageService.listMessage(paramMap, null, studentId);

		return dataStudentJson(list);
	}

	@ResponseBody
	@RequestMapping(value = "removeMessage")
	public Object removeMessage(String studentId, Long id) {
		Validator.validateBlank(id, "动态ID不能为空");
		Validator.validateBlank(studentId, "studentId不能为空");

		DynamicMessage message = dynamicMessageService.removeMessage(id, null, studentId);

		String key = RedisContstant.TEACHER_DYNAMIC_COMMENT_CACHE_KEY + message.getDynamicId();
		if (!PublicUtil.isEmpty(stringRedisTemplate.opsForValue().get(key))) {
			stringRedisTemplate.opsForValue().increment(key, -1);
		}
		return dataStudentJson(new HashMap<String, Object>());
	}

	@ResponseBody
	@RequestMapping(value = "systemRemoveMessage")
	public Object systemRemoveMessage(Long id) {
		Validator.validateBlank(id, "id不能为空");

		DynamicMessage message = dynamicMessageService.systemRemoveMessage(id);

		String key = RedisContstant.TEACHER_DYNAMIC_COMMENT_CACHE_KEY + message.getDynamicId();
		if (!PublicUtil.isEmpty(stringRedisTemplate.opsForValue().get(key))) {
			stringRedisTemplate.opsForValue().increment(key, -1);
		}
		return dataStudentJson(new HashMap<String, Object>());
	}
	
	@ResponseBody
	@RequestMapping(value = "systemRestoreMessage")
	public Object systemRestoreMessage(Long id) {
		Validator.validateBlank(id, "id不能为空");

		DynamicMessage message = dynamicMessageService.systemRestoreMessage(id);

		String key = RedisContstant.TEACHER_DYNAMIC_COMMENT_CACHE_KEY + message.getDynamicId();
		if (!PublicUtil.isEmpty(stringRedisTemplate.opsForValue().get(key))) {
			stringRedisTemplate.opsForValue().increment(key, 1);
		}
		return dataStudentJson(new HashMap<String, Object>());
	}
}
