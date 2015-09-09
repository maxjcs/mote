package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.core.CollectionRedisTemplate;
import com.xuexibao.teacher.dao.RatingIncomeConfigDao;
import com.xuexibao.teacher.model.RatingIncomeConfig;
import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * 
 * @author fengbin
 * 
 */
@Service
public class RatingIncomeConfigService {
	@Resource
	private RatingIncomeConfigDao ratingIncomeConfigDao;

	@Resource
	private CollectionRedisTemplate collectionRedisTemplate;
	@Autowired
	private TeacherService teacherService;

	public RatingIncomeConfig getRatingByTeacher(Teacher teacher) {
		List<RatingIncomeConfig> list = allRatingIncomeConfig();
		StarPoint starPoint=teacherService.getStarPointByTeacherId(teacher.getId());
		if (!CollectionUtils.isEmpty(list)) {
			for (RatingIncomeConfig rating : list) {
				if (starPoint.getStar() == rating.getStar() && teacher.getTeacherIdentify() == rating.getTeacherIdentify()) {
					return rating;
				}
			}
		}
		return null;
	}

	public List<RatingIncomeConfig> allRatingIncomeConfig() {
		String key = RedisContstant.TEACHER_RATING_INCOME_CONFIG_CACHE_KEY_ALL;
		List<RatingIncomeConfig> list = collectionRedisTemplate.get(key, RatingIncomeConfig.class);
		if (CollectionUtils.isEmpty(list)) {
			list = ratingIncomeConfigDao.selectAll();
			if (!CollectionUtils.isEmpty(list)) {
				collectionRedisTemplate.set(key, list);
			}
		}
		return list;
	}

	/**
	 * @param teacherIdentify
	 * @return
	 */
	public List<RatingIncomeConfig> getRatingByTeacherIdentify(Integer teacherIdentify) {
		List<RatingIncomeConfig> result = new ArrayList<RatingIncomeConfig>();
		List<RatingIncomeConfig> list = allRatingIncomeConfig();
		if (!CollectionUtils.isEmpty(list)) {
			for (RatingIncomeConfig rating : list) {
				if (teacherIdentify == rating.getTeacherIdentify()) {
					result.add(rating);
				}
			}
		}
		return result;
	}
}
