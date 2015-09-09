package com.xuexibao.teacher.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.core.GenericRedisTemplate;
import com.xuexibao.teacher.model.Dynamic;
import com.xuexibao.teacher.service.DynamicService;
import com.xuexibao.teacher.service.StudentApiService;
import com.xuexibao.teacher.util.RedisContstant;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("/student/dynamic")
public class StudentDynamicController extends AbstractController {
	@Resource
	private DynamicService dynamicService;
	@Resource
	private StudentApiService studentApiService;
	@Resource
	private GenericRedisTemplate<Dynamic> genericRedisTemplate;

	@ResponseBody
	@RequestMapping(value = "listDynamicByIds")
	public Object listDynamicByIds(String dynamicIds,String studentId) {
		Validator.validateBlank(dynamicIds, "ids不能为空");
		String[] ids = dynamicIds.split(",");

		List<Dynamic> list = dynamicService.listDynamicByIds(ids,studentId);
		for (Dynamic dynamic : list) {
			dynamic.setImageUrls();
		}
		return dataStudentJson(list);
	}

	@ResponseBody
	@RequestMapping(value = "loadDynamic")
	public Object loadDynamic(Long id, String teacherId) {
		Validator.validateBlank(id, "id不能为空");
		Dynamic dynamic = dynamicService.loadDynamic(id, teacherId);
		dynamic.setImageUrls();
		return dataStudentJson(dynamic);
	}

	@ResponseBody
	@RequestMapping(value = "removeDynamic")
	public Object removeDynamic(Long dynamicId) {
		Validator.validateBlank(dynamicId, "动态ID不能为空");

		Dynamic dynamic = dynamicService.removevDynamic(dynamicId);

		studentApiService.deleteTeacherTrend(dynamicId, dynamic.getTeacherId());

		genericRedisTemplate.delete(RedisContstant.TEACHER_DYNAMIC_CACHE_KEY + dynamicId);

		return dataJson(new HashMap<String, Object>());
	}

	@ResponseBody
	@RequestMapping(value = "restoreDynamic")
	public Object restoreDynamic(Long dynamicId) {
		Validator.validateBlank(dynamicId, "动态ID不能为空");

		Dynamic dynamic = dynamicService.restoreDynamic(dynamicId);

		if (StringUtils.isEmpty(dynamic.getSetId())) {
			studentApiService.publishTeacherTrend(dynamicId, dynamic.getTeacherId(), "2");
		} else {
			studentApiService.publishTeacherTrend(dynamicId, dynamic.getTeacherId(), "1");
		}

		return dataJson(new HashMap<String, Object>());
	}

}
