package com.xuexibao.teacher.core;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.util.GsonUtil;

/**
 * 
 * @author oldlu
 * 
 * @param <K>
 * @param <V>
 */
@Service
public class CollectionRedisTemplate {
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	public void set(String key, Collection<?> obj) {
		stringRedisTemplate.opsForValue().set(key, GsonUtil.obj2Json(obj));
	}

	public <V> List<V> get(String key, Class<V> clazz) {
		String value = stringRedisTemplate.opsForValue().get(key);
		if (value != null) {
			return GsonUtil.json2List(value, clazz);
		}
		return null;
	}
}
