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
import org.springframework.web.multipart.MultipartFile;

import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.exception.ValidateException;
import com.xuexibao.teacher.model.DynamicMessage;
import com.xuexibao.teacher.service.DynamicMessageService;
import com.xuexibao.teacher.util.FileUtil;
import com.xuexibao.teacher.util.PublicUtil;
import com.xuexibao.teacher.util.RedisContstant;
import com.xuexibao.teacher.util.UpYunUtil;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("dynamicmessage")
public class DynamicMessageController extends AbstractController {
	@Resource
	private DynamicMessageService dynamicMessageService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@ResponseBody
	@RequestMapping(value = "addMessageForTeacher")
	public Object addMessageForTeacher(DynamicMessage message, MultipartFile image) {
		Validator.validateBlank(message.getDynamicId(), "动态ID不能为空");

		if (FileUtil.isEmpty(image) && StringUtils.isEmpty(message.getContent())) {
			throw new ValidateException("图片和内容不能同时为空");
		}

		saveImage(message, image);

		message.setTeacherId(AppContext.getTeacherId());
		message.setType(DynamicMessage.TYPE_TEACHER_SEND);

		dynamicMessageService.addMessage(message);

		String key = RedisContstant.TEACHER_DYNAMIC_COMMENT_CACHE_KEY + message.getDynamicId();
		if (!PublicUtil.isEmpty(stringRedisTemplate.opsForValue().get(key))) {
			stringRedisTemplate.opsForValue().increment(key, 1);
		}

		return dataJson(message);
	}

	private void saveImage(DynamicMessage message, MultipartFile image) {
		Validator.validateImageFile(image);

		if (!FileUtil.isEmpty(image)) {
			String url = UpYunUtil.upload(image);
			message.setImageUrl(url);
		}
	}

	@ResponseBody
	@RequestMapping(value = "listMessageForTeacher")
	public Object listMessageForTeacher(Long dynamicId, Long lastId) {
		Validator.validateBlank(dynamicId, "动态ID不能为空");
		if (lastId == null || lastId == 0) {
			lastId = Long.MAX_VALUE;
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastId", lastId);
		paramMap.put("dynamicId", dynamicId);
		paramMap.put("pageSize", PageConstant.PAGE_SIZE_10);

		List<DynamicMessage> list = dynamicMessageService.listMessage(paramMap, AppContext.getTeacherId(), null);

		return dataJson(list);
	}

	@ResponseBody
	@RequestMapping(value = "listMessageForH5")
	public Object listMessageForH5(Long dynamicId) {
		Validator.validateBlank(dynamicId, "动态ID不能为空");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastId", Long.MAX_VALUE);
		paramMap.put("dynamicId", dynamicId);
		paramMap.put("pageSize", PageConstant.PAGE_SIZE_5);

		List<DynamicMessage> list = dynamicMessageService.listMessage(paramMap, null, null);

		return dataJson(list);
	}

	@ResponseBody
	@RequestMapping(value = "removeMessage")
	public Object removeMessage(Long id) {
		Validator.validateBlank(id, "动态ID不能为空");

		DynamicMessage dynamic=dynamicMessageService.removeMessage(id, AppContext.getTeacherId(), null);

		String key = RedisContstant.TEACHER_DYNAMIC_COMMENT_CACHE_KEY + dynamic.getDynamicId();
		if (!PublicUtil.isEmpty(stringRedisTemplate.opsForValue().get(key))) {
			stringRedisTemplate.opsForValue().increment(key, -1);
		}
		
		return dataJson(new HashMap<String, Object>());
	}
}
