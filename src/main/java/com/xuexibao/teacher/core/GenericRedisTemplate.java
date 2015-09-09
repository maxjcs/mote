package com.xuexibao.teacher.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.xuexibao.teacher.util.GsonUtil;

/**
 * 
 * @author oldlu
 * 
 * @param <K>
 * @param <V>
 */
@Service
public class GenericRedisTemplate<V> {
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	public void set(String key, V v) {
		String value = GsonUtil.obj2Json(v);
		stringRedisTemplate.opsForValue().set(key, value);
	}

	public V get(String key, Class<V> clazz) {
		String value = stringRedisTemplate.opsForValue().get(key);

		if (!StringUtils.isEmpty(value)) {
			return new Gson().fromJson(value, clazz);
		}
		return null;
	}

	public void delete(String key) {
		stringRedisTemplate.delete(key);
	}

	/**
	 * 批量清除缓存
	 * 
	 * @param keys
	 */
	public void deleteAll(List<String> keys) {
		stringRedisTemplate.delete(keys);
	}

	/**
	 * 批量获取redis缓存
	 * 
	 * @param keys
	 * @param clazz
	 * @return
	 */
	public List<V> multiGet(List<String> keys, Class<V> clazz) {
		List<String> valueList = stringRedisTemplate.opsForValue().multiGet(keys);
		if (valueList != null && valueList.size() > 0) {
			List<V> objList = new ArrayList<V>();
			for (String value : valueList) {
				if (StringUtils.isNotEmpty(value)) {
					objList.add(new Gson().fromJson(value, clazz));
				} else {
					objList.add(null);
				}
			}
			return objList;
		}
		return new ArrayList<V>();
	}

	public List<V> multiGet(String prefix, List<String> strs, Class<V> clazz) {
		List<String> keys = new ArrayList<String>();
		for (String str : strs) {
			keys.add(prefix + str);
		}
		return multiGet(keys, clazz);
	}

	public void multiSet(Map<String, ?> map) {
		Map<String, String> cache = new HashMap<String, String>();
		for (String key : map.keySet()) {
			cache.put(key, GsonUtil.obj2Json(map.get(key)));
		}
		stringRedisTemplate.opsForValue().multiSet(cache);
	}
}
