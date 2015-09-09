package com.xuexibao.teacher.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.core.GenericRedisTemplate;
import com.xuexibao.teacher.model.Dynamic;
import com.xuexibao.teacher.service.DynamicService;
import com.xuexibao.teacher.service.FollowService;
import com.xuexibao.teacher.service.StudentApiService;
import com.xuexibao.teacher.util.FileUtil;
import com.xuexibao.teacher.util.RedisContstant;
import com.xuexibao.teacher.util.UpYunUtil;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("dynamic")
public class DynamicController extends AbstractController {
	@Resource
	private DynamicService dynamicService;
	@Resource
	private FollowService followService;
	@Resource
	private StudentApiService studentApiService;
	@Resource
	private GenericRedisTemplate<Dynamic> genericRedisTemplate;

	@ResponseBody
	@RequestMapping(value = "getFollowedStudentCount")
	public Object getFollowedStudentCount(Dynamic dynamic) {
		String teacherId = AppContext.getTeacherId();

		Integer totalFollowed = followService.getTotalFollowedByTid(teacherId);

		return dataJson(totalFollowed);
	}

	@ResponseBody
	@RequestMapping(value = "addAudioSetDynamic")
	public Object addAudioSetDynamic(Dynamic dynamic) {
		Validator.validateBlank(dynamic.getSetId(), "习题集ID不能为空");

		dynamic.setDynamicType(Dynamic.DYNAMIC_TYPE_ADUIO_SET);
		dynamic.setTeacherId(AppContext.getTeacherId());
		dynamic.setCreateTime(new Date());
		dynamic.setUpdateTime(new Date());

		dynamicService.addAudioSetDynamic(dynamic);

		studentApiService.publishTeacherTrend(dynamic.getId(), dynamic.getTeacherId(),"1");

		return dataJson(dynamic);
	}

	@ResponseBody
	@RequestMapping(value = "addPublishDynamic")
	public Object addPublishDynamic(Dynamic dynamic, MultipartFile image1, MultipartFile image2, MultipartFile image3,
			MultipartFile image4) {
		Validator.validateBlank(dynamic.getGradeIds(), "年段ID不能为空");
		Validator.validateBlank(dynamic.getDescription(), "输入描述不能为空");

		saveDynamicImage(dynamic, image1, image2, image3, image4);

		dynamic.setDynamicType(Dynamic.DYNAMIC_TYPE_PUBLISH);
		dynamic.setTeacherId(AppContext.getTeacherId());
		dynamic.setCreateTime(new Date());
		dynamic.setUpdateTime(new Date());

		dynamicService.addPublishDynamic(dynamic);

		studentApiService.publishTeacherTrend(dynamic.getId(), dynamic.getTeacherId(),"2");

		return dataJson(dynamic);
	}

	@ResponseBody
	@RequestMapping(value = "removeDynamic")
	public Object removeDynamic(Long dynamicId) {
		Validator.validateBlank(dynamicId, "动态ID不能为空");

		dynamicService.removevDynamic(dynamicId, AppContext.getTeacherId());

		studentApiService.deleteTeacherTrend(dynamicId, AppContext.getTeacherId());

		genericRedisTemplate.delete(RedisContstant.TEACHER_DYNAMIC_CACHE_KEY + dynamicId);
		
		return dataJson(new HashMap<String, Object>());
	}

	private void saveDynamicImage(Dynamic dynamic, MultipartFile image1, MultipartFile image2, MultipartFile image3,
			MultipartFile image4) {
		Validator.validateImageFile(image1);
		Validator.validateImageFile(image2);
		Validator.validateImageFile(image3);
		Validator.validateImageFile(image4);

		if (!FileUtil.isEmpty(image1)) {
			String url = UpYunUtil.upload(image1);
			dynamic.setImageUrl1(url);
		}
		if (!FileUtil.isEmpty(image2)) {
			String url = UpYunUtil.upload(image2);
			dynamic.setImageUrl2(url);
		}
		if (!FileUtil.isEmpty(image3)) {
			String url = UpYunUtil.upload(image3);
			dynamic.setImageUrl3(url);
		}
		if (!FileUtil.isEmpty(image4)) {
			String url = UpYunUtil.upload(image4);
			dynamic.setImageUrl4(url);
		}
	}

	@ResponseBody
	@RequestMapping(value = "listDynamic")
	public Object listDynamic(Long lastId) {
		if (lastId == null || lastId == 0) {
			lastId = Long.MAX_VALUE;
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastId", lastId);
		paramMap.put("teacherId", AppContext.getTeacherId());
		paramMap.put("pageSize", PageConstant.PAGE_SIZE_10);

		List<Dynamic> list = dynamicService.listDynamic(paramMap, AppContext.getTeacherId());

		return dataJson(list);
	}

	@ResponseBody
	@RequestMapping(value = "loadDynamic")
	public Object loadDynamic(Long id) {
		Validator.validateBlank(id, "id不能为空");

		return dataJson(dynamicService.loadDynamic(id, AppContext.getTeacherId()));
	}
	
	@ResponseBody
	@RequestMapping(value = "loadDynamicForH5")
	public Object loadDynamicForH5(Long id) {
		Validator.validateBlank(id, "id不能为空");

		return dataJson(dynamicService.loadDynamic(id, ""));
	}

}
