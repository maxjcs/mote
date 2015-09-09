package com.xuexibao.teacher.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.core.CollectionRedisTemplate;
import com.xuexibao.teacher.dao.PointOfflineDao;
import com.xuexibao.teacher.model.PointOffline;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class PointOfflineService {
	@Resource
	private PointOfflineDao pointofflineDao;

	@Resource
	private CollectionRedisTemplate collectionRedisTemplate;

	public List<PointOffline> allPointOffline() {
		String key = RedisContstant.TEACHER_POINTOFFLINE_CACHE_KEY_ALL;
		List<PointOffline> list = collectionRedisTemplate.get(key, PointOffline.class);

		if (CollectionUtils.isEmpty(list)) {
			list = pointofflineDao.allPointOffline();

			if (!CollectionUtils.isEmpty(list)) {
				for (PointOffline item : list) {
					String desc = String.format(item.getDescription(), item.getMoney() / 100, item.getPoint());
					item.setDescription(desc);
				}
				collectionRedisTemplate.set(key, list);
			}
		}

		return list;
	}

	public void updatePointOffline(PointOffline pointoffline) {
		pointofflineDao.updatePointOffline(pointoffline);
	}
}
