package com.xuexibao.teacher.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.core.CollectionRedisTemplate;
import com.xuexibao.teacher.dao.DictDao;
import com.xuexibao.teacher.enums.DictType;
import com.xuexibao.teacher.model.Dict;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class DictService {
	@Resource
	private DictDao dictDao;

	@Resource
	private CollectionRedisTemplate collectionRedisTemplate;

	public List<Dict> listDictByType(DictType type) {
		String key = RedisContstant.TEACHER_DICT_CACHE_KEY + type.getValue();
		List<Dict> list = collectionRedisTemplate.get(key, Dict.class);

		if (CollectionUtils.isEmpty(list)) {
			list = dictDao.listDictByType(type.getValue());

			collectionRedisTemplate.set(key, list);
		}

		return list;
	}
}
