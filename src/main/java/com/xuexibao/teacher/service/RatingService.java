package com.xuexibao.teacher.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.core.CollectionRedisTemplate;
import com.xuexibao.teacher.dao.RatingDao;
import com.xuexibao.teacher.model.Rating;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class RatingService {
	@Resource
	private RatingDao ratingDao;
	@Resource
	private CollectionRedisTemplate collectionRedisTemplate;

	public List<Rating> allRating() {
		String key = RedisContstant.TEACHER_RATING_CACHE_KEY_ALL;
		List<Rating> list = collectionRedisTemplate.get(key, Rating.class);

		if (CollectionUtils.isEmpty(list)) {
			list = ratingDao.allRating();
			if (!CollectionUtils.isEmpty(list)) {
				collectionRedisTemplate.set(key, list);
			}
		}

		return list;
	}

	public void updateRating(Rating rating) {
		ratingDao.updateRating(rating);
	}

	public Rating nextRating(int curStar) {
		List<Rating> list = allRating();
		if (!CollectionUtils.isEmpty(list)) {
			boolean findCur = false;
			for (Rating rating : list) {
				if (rating.getStar() == curStar) {
					findCur = true;
					continue;
				}
				if (findCur) {
					return rating;
				}
			}
		}
		return null;
	}

	/**
	 * 查找积分对应的星级
	 * 
	 * @param point
	 * @return
	 */
	public Rating getRatingByPoint(int point) {
		List<Rating> list = allRating();
		if (!CollectionUtils.isEmpty(list)) {
			for (int i = list.size() - 1; i > 0; i--) {
				Rating rating = list.get(i);
				if (point >= rating.getPoints()) {
					return rating;
				}
			}
		}

		return null;
	}

	public Rating getRatingByStar(int star) {
		List<Rating> list = allRating();
		if (!CollectionUtils.isEmpty(list)) {
			for (Rating rating : list) {
				if (star == rating.getStar()) {
					return rating;
				}
			}
		}

		return null;
	}
}
