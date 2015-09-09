package com.xuexibao.teacher.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xuexibao.teacher.dao.AudioDao;
import com.xuexibao.teacher.enums.AudioSource;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioSet;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.pay.dao.PayAudioDao;
import com.xuexibao.teacher.pay.model.PayAudio;
import com.xuexibao.teacher.service.evaluprocessor.EvaluProcessor;
import com.xuexibao.teacher.service.evaluprocessor.EvaluProcessorManager;
import com.xuexibao.teacher.service.evaluprocessor.task.AudioMonyUtil;
import com.xuexibao.teacher.util.JsonUtil;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class StudentService {
	@Resource
	private TeacherService teacherService;
	@Resource
	private AudioDao audioDao;
	@Resource
	private PayAudioDao payAudioDao;
	@Resource
	private EvaluProcessorManager evaluProcessorManager;
	@Resource
	private AudioSetService audioSetService;
	@Autowired
	protected AudioMonyUtil audioMonyUtil;

	public Map<String, Object> getTeacher(String teacherId, String studentId) {
		Teacher teacher = teacherService.getRequiredTeacher(teacherId);

		Map<String, Object> result = JsonUtil.obj2Map(teacher);
		List ralations = teacherService.queryTeacherWithFollow(Arrays.asList(teacherId), studentId);
		result.put("isFollowed", !CollectionUtils.isEmpty(ralations));

		return result;
	}

	public Map<String, Object> getAudioSetMoneyV13(String id) {
		AudioSet set = audioSetService.getAudioSetById(id);
		if (set != null) {
			return audioMonyUtil.getAudioSetIncomeByPlanType(set,
					teacherService.getRequiredTeacher(set.getTeacherId()));
		}
		return new HashMap<String, Object>();
	}

	public Map<String, Object> getAudioOrgTeacherMoneyV13(String teacherId, String audioId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Teacher teacher = teacherService.getTeacher(teacherId);
		if (teacher == null) {
			return result;
		}

		PayAudio audio = payAudioDao.queryAudioById(audioId);
		if (audio == null) {
			throw new BusinessException("音频[" + audioId + "]不存在");
		}

		result.put("source", audio.getSource());

		result.putAll(audioMonyUtil.getAudioIncomeByPlanType(audio, teacher));
		return result;
	}

}
