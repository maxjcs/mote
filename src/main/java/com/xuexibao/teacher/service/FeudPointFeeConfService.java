package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.core.CollectionRedisTemplate;
import com.xuexibao.teacher.dao.FeudPointFeeConfDao;
import com.xuexibao.teacher.enums.FeudPointFeeConfType;
import com.xuexibao.teacher.model.Dict;
import com.xuexibao.teacher.model.FeudPointFeeConf;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * 
 * @author fengbin
 * 
 */
@Service
public class FeudPointFeeConfService {
	@Resource
	private RedisTemplate<String, List<FeudPointFeeConf>> redisTemplate;
	@Resource
	private CollectionRedisTemplate collectionRedisTemplate;
	@Resource
	private FeudPointFeeConfDao feudPointFeeConfDao;

	/**
	 * 根据用户角色大学生或者教师 + 星级或者评价等级 返回 积分
	 * 
	 * @param starEvaluate
	 * @param teacherIdentify
	 * @return
	 */

	public FeudPointFeeConf getFeudPointByStarAndTeacherIdentify(int starEvaluate, int teacherIdentify) {
		List<FeudPointFeeConf> list = allPointFeeConfig();
		if (!CollectionUtils.isEmpty(list)) {
			for (FeudPointFeeConf ffc : list) {
				if (ffc.getConfType() == FeudPointFeeConfType.point.getValue() && starEvaluate == ffc.getStarEvaluate()
						&& teacherIdentify == ffc.getTeacherIdentify()) {
					return ffc;
				}
			}
		}
		return null;			
	}
	
	/**
	 * 根据用户角色大学生或者教师 返回 放弃抢答 扣的积分
	 * 
	 * @param starEvaluate
	 * @param teacherIdentify
	 * @return
	 */

	public FeudPointFeeConf getFeudPointByGiveupAndTeacherIdentify(int starEvaluate, int teacherIdentify) {
		List<FeudPointFeeConf> list = allPointFeeConfig();
		if (!CollectionUtils.isEmpty(list)) {
			for (FeudPointFeeConf ffc : list) {
				if (ffc.getConfType() == FeudPointFeeConfType.point.getValue() && starEvaluate == ffc.getStarEvaluate()
						&& teacherIdentify == ffc.getTeacherIdentify()) {
					return ffc;
				}
			}
		}
		return null;			
	}

	/**
	 * 根据用户角色大学生或者教师 + 星级或者评价等级 返回 费用
	 * 
	 * @param starEvaluate
	 * @param teacherIdentify
	 * @return
	 */

	public FeudPointFeeConf getFeudFeeByStarAndTeacherIdentify(int starEvaluate, int teacherIdentify) {
		List<FeudPointFeeConf> list = allPointFeeConfig();
		// if (!CollectionUtils.isEmpty(list)) {
		for (FeudPointFeeConf ffc : list) {
			if (ffc.getConfType() == FeudPointFeeConfType.fee.getValue() && starEvaluate == ffc.getStarEvaluate()
					&& teacherIdentify == ffc.getTeacherIdentify()) {
				return ffc;
			}
		}
		// }
		return null;
	}

	public List<FeudPointFeeConf> allPointFeeConfig() {
//		String key = RedisContstant.FEUD_POINT_FEE_CONF_ALL;
//		List<FeudPointFeeConf> list = redisTemplate.opsForValue().get(key);
//		if (CollectionUtils.isEmpty(list)) {
//			list = feudPointFeeConfDao.selectAll();
//			if (!CollectionUtils.isEmpty(list)) {
//				redisTemplate.opsForValue().set(key, list);
//			}
//		}
//		List<FeudPointFeeConf> list = feudPointFeeConfDao.selectAll();
//		return list;
		
		String key = RedisContstant.FEUD_POINT_FEE_CONF_ALL;
		List<FeudPointFeeConf> list = collectionRedisTemplate.get(key, FeudPointFeeConf.class);
		if (CollectionUtils.isEmpty(list)) {
			list = feudPointFeeConfDao.selectAll();
			collectionRedisTemplate.set(key, list);
		}
		return list;	
	}

	public List<FeudPointFeeConf> getRatingByTeacherIdentify(int teacherIdentify) {
		List<FeudPointFeeConf> result = new ArrayList<FeudPointFeeConf>();
		List<FeudPointFeeConf> list = allPointFeeConfig();
		if (!CollectionUtils.isEmpty(list)) {
			for (FeudPointFeeConf ffc : list) {
				if (ffc.getConfType() == FeudPointFeeConfType.fee.getValue() && teacherIdentify == ffc.getTeacherIdentify()) {
					result.add(ffc);
				}
			}
		}
		return result;
	}

	public Object getPointByTeacherIdentify(int teacherIdentify) {
		List<FeudPointFeeConf> result = new ArrayList<FeudPointFeeConf>();
		List<FeudPointFeeConf> list = allPointFeeConfig();
		if (!CollectionUtils.isEmpty(list)) {
			for (FeudPointFeeConf ffc : list) {
				if (ffc.getConfType() == FeudPointFeeConfType.point.getValue() && teacherIdentify == ffc.getTeacherIdentify()) {
					result.add(ffc);
				}
			}
		}
		return result;
	}
}

