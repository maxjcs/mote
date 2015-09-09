package com.xuexibao.teacher.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.core.CollectionRedisTemplate;
import com.xuexibao.teacher.dao.CommonConfigDao;
import com.xuexibao.teacher.model.CommonConfig;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.util.BusinessConstant;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class CommonConfigService {
	@Resource
	private CommonConfigDao commonConfigDao;
	@Resource
	private CollectionRedisTemplate collectionRedisTemplate;

	public List<CommonConfig> allCommonConfig() {
		String key = RedisContstant.TEACHER_COMMON_CFG_CACHE_KEY_ALL;
		List<CommonConfig> list = collectionRedisTemplate.get(key, CommonConfig.class);

//		if (CollectionUtils.isEmpty(list)) {
			list = commonConfigDao.allCfg();
			if (!CollectionUtils.isEmpty(list)) {
				collectionRedisTemplate.set(key, list);
			}
//		}

		return list;
	}

	public CommonConfig getConfigByKey(String key) {
		List<CommonConfig> list = allCommonConfig();
		for (CommonConfig item : list) {
			if (StringUtils.equals(item.getKey(), key)) {
				return item;
			}
		}
		return null;
	}

	public Integer getAudioSalePercent(Teacher teacher) {
		if (teacher.isTeacher()) {
			CommonConfig result = getConfigByKey(CommonConfig.KEY_AUDIO_SALE_INFO_TEACHER);
			if (result != null) {
				return result.getValue();
			}
			return BusinessConstant.AUDIO_SALE_TEACHER_PERCENT;
		} else {
			CommonConfig result = getConfigByKey(CommonConfig.KEY_AUDIO_SALE_INFO_STUDENT);
			if (result != null) {
				return result.getValue();
			}
			return BusinessConstant.AUDIO_SALE_STUDENT_PERCENT;
		}
	}

	public Integer getCompleteTaskReward() {
		CommonConfig result = getConfigByKey(CommonConfig.KEY_COMPATE_TASK_REWARD);
		if (result != null) {
			return result.getValue();
		}
		return 10;
	}
	
	public CommonConfig getRechargeReward() {
		return getConfigByKey(CommonConfig.KEY_RECHARGE_REWARD);
	}
}
