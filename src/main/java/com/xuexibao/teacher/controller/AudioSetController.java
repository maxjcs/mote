package com.xuexibao.teacher.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.constant.AppVersion;
import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.core.GenericRedisTemplate;
import com.xuexibao.teacher.enums.AudioSource;
import com.xuexibao.teacher.exception.ValidateException;
import com.xuexibao.teacher.model.AudioSet;
import com.xuexibao.teacher.model.Dynamic;
import com.xuexibao.teacher.service.AudioSetService;
import com.xuexibao.teacher.service.DynamicService;
import com.xuexibao.teacher.service.StudentApiService;
import com.xuexibao.teacher.util.JsonUtil;
import com.xuexibao.teacher.util.RedisContstant;
import com.xuexibao.teacher.validator.Validator;

/**
 * 
 * @author oldlu
 * 
 */
@Controller
@RequestMapping("audioset")
public class AudioSetController extends AbstractController {
	@Resource
	private AudioSetService audioSetService;
	@Resource
	private StudentApiService studentApiService;
	@Resource
	private GenericRedisTemplate<Dynamic> genericRedisTemplate;
	@Resource
	private DynamicService dynamicService;

	@ResponseBody
	@RequestMapping(value = "listAudioDate")
	public Object listAudioDate(Integer audioSource) {
		Validator.validateBlank(audioSource, "audioSource不能为空");

		return dataJson(audioSetService.listAudioDate(audioSource));
	}

	@ResponseBody
	@RequestMapping(value = "loadAudioSet")
	public Object loadAudioSet(String setId, String studentId) {
		Validator.validateBlank(setId, "setId不能为空");
		AudioSet audioset = audioSetService.loadAudioSet(setId);
		if (!StringUtils.isEmpty(studentId)) {

		}
		return dataJson(audioset);
	}

	@ResponseBody
	@RequestMapping(value = "addAudioSet")
	public Object addAudioSet(String audioset, String version) {
		Validator.validateBlank(audioset, "audioset不能为空");

		AudioSet set = JsonUtil.json2Obj(audioset, AudioSet.class);
		Validator.validateBlank(set.getName(), "习题集名称不能为空");
		Validator.validateLtZero(set.getPrice(), "请设置习题集价格");
		Validator.validateCollectionEmpty(set.getAudioIds(), "习题集明细不能为空");
		Validator.validateBlank(set.getGradeIds(), "年级不能为空");
		if (set.getPrice() > 999) {
			throw new ValidateException("价格不能高于999元");
		}
		if (set.getPrice() != set.getAudioIds().size()) {
			throw new ValidateException("价格计算有误");
		}
		if (set.getAudioIds().size()<3) {
			throw new ValidateException("习题集音频数不能小于3");
		}
		set.setPrice(set.getPrice() * 100);
		Validator.validateCollectionEmpty(set.getAudioIds(), "习题集音频明细不能为空");
		set.setTeacherId(AppContext.getTeacherId());
		audioSetService.addAudioSet(set);

		// 缓存中增加习题集的数量
		audioSetService.addAudioSetNumRedis(AppContext.getTeacherId());

		if (AppVersion.greatEqualThan14(version)) {
			return dataJson(set);
		}

		return dataJson(true);
	}

	@ResponseBody
	@RequestMapping(value = "listAudioSet")
	public Object listAudioSet(Integer pageNo) {
		if (pageNo == null) {
			pageNo = 0;
		}
		String teacherId = AppContext.getTeacherId();

		return dataJson(audioSetService.listAudioSet(teacherId, pageNo));
	}

	@ResponseBody
	@RequestMapping(value = "listAudioSetByTeacherId")
	public Object listAudioSetByTeacherId(String teacherId, Integer pageNo) {
		if (pageNo == null) {
			pageNo = 0;
		}
		Validator.validateBlank(teacherId, "teacherId不能为空");

		return dataJson(audioSetService.listAudioSet(teacherId, pageNo));
	}

	@ResponseBody
	@RequestMapping(value = "countAudioSet")
	public Object countAudioSet() {
		String teacherId = AppContext.getTeacherId();

		return dataJson(audioSetService.countAudioSet(teacherId));
	}

	@ResponseBody
	@RequestMapping(value = "listAudio")
	public Object listAudio(String filterDay, Integer audioSource, Integer pageNo) {
		if (pageNo == null) {
			pageNo = 0;
		}
		String teacherId = AppContext.getTeacherId();
		if (audioSource == null || (audioSource != AudioSource.feud.getValue()
				&& audioSource != AudioSource.task.getValue() && audioSource != AudioSource.explanation.getValue())) {
			throw new ValidateException("音频来源不对,只能是[1,2,3]");
		}

		return dataJson(audioSetService.listAudio(teacherId, filterDay, audioSource, pageNo));
	}

	@ResponseBody
	@RequestMapping(value = "getAudioSetEvaluateList")
	public Object getAudioSetEvaluateList(String setId, Integer evaluteType, Integer pageNo) {
		Validator.validateBlank(setId, "习题集ID不能为空");
		if (evaluteType == null) {
			evaluteType = 0;
		}

		return dataJson(studentApiService.getAudioSetEvaluateList(setId, evaluteType, pageNo, false));
//		return dataJson(new ArrayList());
	}

	@ResponseBody
	@RequestMapping(value = "updateAudioSetName")
	public Object updateAudioSetName(String setId, String name) {
		Validator.validateBlank(setId, "习题集ID不能为空");
		Validator.validateBlank(name, "习题集名称不能为空");

		AudioSet set = new AudioSet();
		set.setId(setId);
		set.setName(name);
		set.setTeacherId(AppContext.getTeacherId());

		audioSetService.updateAudioSetName(set);

		genericRedisTemplate.delete(RedisContstant.TEACHER_AUDIO_SET_CACHE_KEY + setId);

		return dataJson(true);
	}

	@ResponseBody
	@RequestMapping(value = "updateAudioSetGrade")
	public Object updateAudioSetGrade(String setId, String gradeIds) {
		Validator.validateBlank(setId, "习题集ID不能为空");
		Validator.validateBlank(gradeIds, "习题集年级不能为空");

		AudioSet set = new AudioSet();
		set.setId(setId);
		set.setGradeIds(gradeIds);
		set.setTeacherId(AppContext.getTeacherId());

		audioSetService.updateAudioSetGrade(set);

		genericRedisTemplate.delete(RedisContstant.TEACHER_AUDIO_SET_CACHE_KEY + setId);

		return dataJson(true);
	}

	@ResponseBody
	@RequestMapping(value = "setTopAudioSet")
	public Object setTopAudioSet(String setIds) {
		Validator.validateBlank(setIds, "setIds习题集ID不能为空");

		AudioSet set = new AudioSet();
		set.setTeacherId(AppContext.getTeacherId());

		String[] setIdsAry = setIds.split(",");
		audioSetService.setTopAudioSet(set, setIdsAry);

		for (String item : setIdsAry) {
			genericRedisTemplate.delete(RedisContstant.TEACHER_AUDIO_SET_CACHE_KEY + item);
		}

		return dataJson(true);
	}

	@ResponseBody
	@RequestMapping(value = "updateAudioSetDescription")
	public Object updateAudioSetDescription(String setId, String description) {
		Validator.validateBlank(setId, "习题集ID不能为空");
		Validator.validateBlank(description, "习题集简介description不能为空");
		if (description.length() > 512) {
			throw new ValidateException("习题集简介description长度不能超过512字符");
		}

		AudioSet set = new AudioSet();
		set.setId(setId);
		set.setDescription(description);
		set.setTeacherId(AppContext.getTeacherId());

		audioSetService.updateAudioSetDescription(set);

		genericRedisTemplate.delete(RedisContstant.TEACHER_AUDIO_SET_CACHE_KEY + setId);

		return dataJson(true);
	}

	@ResponseBody
	@RequestMapping(value = "removeAudioSet")
	public Object removeAudioSet(String setId) {
		Validator.validateBlank(setId, "习题集ID不能为空");

		AudioSet set = new AudioSet();
		set.setId(setId);
		set.setTeacherId(AppContext.getTeacherId());

		audioSetService.removeAudioSet(set);

		// 缓存中减少习题集的数量
		audioSetService.removeAudioSetNumRedis(AppContext.getTeacherId());

		genericRedisTemplate.delete(RedisContstant.TEACHER_AUDIO_SET_CACHE_KEY + setId);

		removeDynamicBySetId(setId);

		return dataJson(true);
	}

	private void removeDynamicBySetId(String setId) {
		Long dynamicId = dynamicService.getDynamicIdBySetId(setId);

		if (dynamicId != null && dynamicId > 0) {
			dynamicService.removevDynamic(dynamicId, AppContext.getTeacherId());

			studentApiService.deleteTeacherTrend(dynamicId, AppContext.getTeacherId());

			genericRedisTemplate.delete(RedisContstant.TEACHER_DYNAMIC_CACHE_KEY + dynamicId);
		}
	}

	@ResponseBody
	@RequestMapping(value = "setAudioSetFreeStatus")
	public Object setAudioSetFreeStatus(String setId, Integer isFree) {
		Validator.validateBlank(setId, "setId不能为空");
		if (isFree == null || (isFree != AudioSet.IS_FREE_YES && isFree != AudioSet.IS_FREE_NO)) {
			throw new ValidateException("isFree枚举值不正确");
		}
		audioSetService.setAudioSetFreeStatus(setId, AppContext.getTeacherId(), isFree);

		genericRedisTemplate.delete(RedisContstant.TEACHER_AUDIO_SET_CACHE_KEY + setId);

		return dataJson(new HashMap<String, Object>());
	}
}
