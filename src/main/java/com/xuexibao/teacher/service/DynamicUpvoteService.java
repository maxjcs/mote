package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.xuexibao.teacher.dao.DynamicUpvoteDao;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Dynamic;
import com.xuexibao.teacher.model.DynamicUpvote;
import com.xuexibao.teacher.model.iter.IDynmicUpVoteCount;
import com.xuexibao.teacher.util.PublicUtil;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * 
 * @author oldlu
 *
 */
@Service
@Transactional
public class DynamicUpvoteService {
	@Resource
	private DynamicUpvoteDao dynamicUpvoteDao;
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	public void wrapperDynamicCountUpVote(List<? extends IDynmicUpVoteCount> list) {
		if (!CollectionUtils.isEmpty(list)) {
			List<Long> ids = new ArrayList<Long>();
			List<String> keys = new ArrayList<String>();
			List<Long> noCacheIds = new ArrayList<Long>();
			Map<Long, IDynmicUpVoteCount> map = new HashMap<Long, IDynmicUpVoteCount>();

			for (IDynmicUpVoteCount item : list) {
				ids.add(item.getDynamicId());
				keys.add(RedisContstant.TEACHER_DYNAMIC_UPVOTE_CACHE_KEY + item.getDynamicId());

				map.put(item.getDynamicId(), item);
			}

			List<String> redisList = stringRedisTemplate.opsForValue().multiGet(keys);
			Map<Object, Object> cacheMap = new HashMap<Object, Object>();

			for (int i = 0, size = redisList.size(); i < size; i++) {
				String redisValue = redisList.get(i);
				Long id = ids.get(i);
				if (PublicUtil.isEmpty(redisValue)) {
					noCacheIds.add(id);
					cacheMap.put(id, 0);
				} else {
					map.get(id).setCountUpVote(Integer.parseInt(redisValue));
				}
			}

			if (!CollectionUtils.isEmpty(noCacheIds)) {
				List<Dynamic> groupCounts = dynamicUpvoteDao.groupCountUpvoteByDynamicIds(noCacheIds);

				for (Dynamic item : groupCounts) {
					cacheMap.put(item.getDynamicId(), item.getCountUpVote());
					map.get(item.getDynamicId()).setCountUpVote(item.getCountUpVote());
				}

				Map<String, String> sendRedisMap = new HashMap<String, String>();
				for (Map.Entry<Object, Object> entry : cacheMap.entrySet()) {
					sendRedisMap.put(RedisContstant.TEACHER_DYNAMIC_UPVOTE_CACHE_KEY + entry.getKey(),
							entry.getValue() + "");
				}
				stringRedisTemplate.opsForValue().multiSet(sendRedisMap);
			}
		}
	}

	public void addUpvote(DynamicUpvote upvote) {
		DynamicUpvote exist = dynamicUpvoteDao.loadDynamicUpvote(upvote);

		if (exist != null) {
			throw new BusinessException("已经赞过，不能重复赞");
		}

		upvote.setCreateTime(new Date());
		dynamicUpvoteDao.addDynamicUpvote(upvote);
	}

	public void deleteUpvote(DynamicUpvote upvote) {
		DynamicUpvote exist = dynamicUpvoteDao.loadDynamicUpvote(upvote);

		if (exist == null) {
			throw new BusinessException("还未赞，不能取消赞");
		}

		dynamicUpvoteDao.deleteDynamicUpvote(upvote);
	}

	public void addSystemUpvote(Long dynamicId, Integer count) {
		DynamicUpvote upvote = new DynamicUpvote();
		upvote.setCreateTime(new Date());
		upvote.setDynamicId(dynamicId);
		upvote.setVotorId("xuexibao");

		for (int i = 0; i < count; i++) {
			dynamicUpvoteDao.addDynamicUpvote(upvote);
		}
	}
}
