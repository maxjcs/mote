package com.xuexibao.teacher.controller;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.model.DynamicUpvote;
import com.xuexibao.teacher.service.DynamicUpvoteService;
import com.xuexibao.teacher.util.PublicUtil;
import com.xuexibao.teacher.util.RedisContstant;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("dynamicupvote")
public class DynamicUpvoteController extends AbstractController {
	@Resource
	private DynamicUpvoteService dynamicupvoteService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@ResponseBody
	@RequestMapping(value = "addUpvote")
	public Object addUpvote(DynamicUpvote upvote) {
		Validator.validateBlank(upvote.getDynamicId(), "动态ID不能为空");
		upvote.setVotorId(AppContext.getTeacherId());

		dynamicupvoteService.addUpvote(upvote);

		String key = RedisContstant.TEACHER_DYNAMIC_UPVOTE_CACHE_KEY + upvote.getDynamicId();
		if (!PublicUtil.isEmpty(stringRedisTemplate.opsForValue().get(key))) {
			stringRedisTemplate.opsForValue().increment(key, 1);
		}

		return dataJson(upvote);
	}

	@ResponseBody
	@RequestMapping(value = "removeUpvote")
	public Object removeUpvote(DynamicUpvote upvote) {
		Validator.validateBlank(upvote.getDynamicId(), "动态ID不能为空");
		upvote.setVotorId(AppContext.getTeacherId());

		dynamicupvoteService.deleteUpvote(upvote);

		String key = RedisContstant.TEACHER_DYNAMIC_UPVOTE_CACHE_KEY + upvote.getDynamicId();
		if (!PublicUtil.isEmpty(stringRedisTemplate.opsForValue().get(key))) {
			stringRedisTemplate.opsForValue().increment(key, -1);
		}

		return dataJson(upvote);
	}
}
