package com.xuexibao.teacher.controller;

import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.exception.ValidateException;
import com.xuexibao.teacher.model.DynamicUpvote;
import com.xuexibao.teacher.service.DynamicUpvoteService;
import com.xuexibao.teacher.util.PublicUtil;
import com.xuexibao.teacher.util.RedisContstant;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("student/dynamicupvote")
public class StudentUpvoteController extends AbstractController {
	private static Logger logger = LoggerFactory.getLogger(StudentUpvoteController.class);

	@Resource
	private DynamicUpvoteService dynamicupvoteService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@ResponseBody
	@RequestMapping(value = "addSystemUpvote")
	public Object addSystemUpvote(Integer count, Long dynamicId) {
		Validator.validateBlank(dynamicId, "动态ID不能为空");
		Validator.validateBlank(count, "赞数不能为空");
		if(count<1){
			throw new ValidateException("赞数不能为空");
		}
		if(count>100){
			throw new ValidateException("赞数不能超过100");
		}

		dynamicupvoteService.addSystemUpvote(dynamicId, count);

		String key = RedisContstant.TEACHER_DYNAMIC_UPVOTE_CACHE_KEY + dynamicId;
		if (!PublicUtil.isEmpty(stringRedisTemplate.opsForValue().get(key))) {
			stringRedisTemplate.opsForValue().increment(key, count);
		}

		logger.info("系统添赞" + dynamicId + "[" + count + "]");

		return dataStudentJson(new HashMap<String, String>());
	}

	@ResponseBody
	@RequestMapping(value = "addUpvote")
	public Object addUpvote(DynamicUpvote upvote, String studentId) {
		Validator.validateBlank(upvote.getDynamicId(), "动态ID不能为空");

		upvote.setVotorId(studentId);

		dynamicupvoteService.addUpvote(upvote);

		String key = RedisContstant.TEACHER_DYNAMIC_UPVOTE_CACHE_KEY + upvote.getDynamicId();
		if (!PublicUtil.isEmpty(stringRedisTemplate.opsForValue().get(key))) {
			stringRedisTemplate.opsForValue().increment(key, 1);
		}

		return dataStudentJson(upvote);
	}

	@ResponseBody
	@RequestMapping(value = "removeUpvote")
	public Object removeUpvote(DynamicUpvote upvote, String studentId) {
		Validator.validateBlank(upvote.getDynamicId(), "动态ID不能为空");

		upvote.setVotorId(studentId);

		dynamicupvoteService.deleteUpvote(upvote);

		String key = RedisContstant.TEACHER_DYNAMIC_UPVOTE_CACHE_KEY + upvote.getDynamicId();
		if (!PublicUtil.isEmpty(stringRedisTemplate.opsForValue().get(key))) {
			stringRedisTemplate.opsForValue().increment(key, -1);
		}

		return dataStudentJson(upvote);
	}
}
