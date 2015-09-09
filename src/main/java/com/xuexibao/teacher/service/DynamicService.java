package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.xuexibao.teacher.common.CommonService;
import com.xuexibao.teacher.common.Status;
import com.xuexibao.teacher.core.GenericRedisTemplate;
import com.xuexibao.teacher.dao.AudioSetDao;
import com.xuexibao.teacher.dao.DynamicDao;
import com.xuexibao.teacher.dao.DynamicUpvoteDao;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.AudioSet;
import com.xuexibao.teacher.model.Dynamic;
import com.xuexibao.teacher.model.DynamicUpvote;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * 
 * @author oldlu
 *
 */
@Service
@Transactional
public class DynamicService {
	private static Logger logger = LoggerFactory.getLogger(DynamicService.class);

	@Resource
	private DynamicDao dynamicDao;
	@Resource
	private AudioSetService audioSetService;
	@Resource
	private CommonService commonService;
	@Resource
	private AudioSetDao audioSetDao;
	@Resource
	private DynamicUpvoteDao dynamicUpvoteDao;
	@Resource
	private GenericRedisTemplate<Dynamic> genericRedisTemplate;
	@Resource
	private DynamicMessageService dynamicMessageService;
	@Resource
	private DynamicUpvoteService dynamicUpvoteService;

	private Dynamic getDynamicFromRedis(Long id) {
		String key = RedisContstant.TEACHER_DYNAMIC_CACHE_KEY + id;
		Dynamic result = genericRedisTemplate.get(key, Dynamic.class);
		if (result == null) {
			result = dynamicDao.loadDynamic(id);
			if (result != null) {
				genericRedisTemplate.set(key, result);
			}
		}

		return result;
	}

	private List<Dynamic> listDynamicFromRedis(String[] dynamicIds) {
		List<Dynamic> list = genericRedisTemplate.multiGet(RedisContstant.TEACHER_DYNAMIC_CACHE_KEY,
				Arrays.asList(dynamicIds), Dynamic.class);
		List<String> noCacheIds = new ArrayList<String>();

		List<Dynamic> result = new ArrayList<Dynamic>();
		for (int i = 0, size = list.size(); i < size; i++) {
			Dynamic dynamic = list.get(i);
			String dynamicId = dynamicIds[i];
			if (dynamic == null) {
				noCacheIds.add(dynamicId);
			} else {
				result.add(dynamic);
			}
		}

		if (!CollectionUtils.isEmpty(noCacheIds)) {
			Map<String, Dynamic> nocacheMap = new HashMap<String, Dynamic>();

			List<Dynamic> dblist = dynamicDao.listDynamicByIds(noCacheIds.toArray(new String[] {}));
			for (Dynamic item : dblist) {
				nocacheMap.put(RedisContstant.TEACHER_DYNAMIC_CACHE_KEY + item.getId(), item);
			}

			genericRedisTemplate.multiSet(nocacheMap);

			result.addAll(dblist);
		}

		return result;
	}

	public Dynamic loadDynamic(Long id, String votorId) {
		Dynamic result = getDynamicFromRedis(id);

		if (result != null) {
			dynamicMessageService.wrapperDynamicCountComment(Arrays.asList(result));
			dynamicUpvoteService.wrapperDynamicCountUpVote(Arrays.asList(result));
			commonService.wrapperTeacher(Arrays.asList(result));

			wrapperCanUpVote(Arrays.asList(result), votorId);
			wrapperAudioSet(Arrays.asList(result));
			
			commonService.wrapperGradeStr(Arrays.asList(result), "的同学们");
		}
		return result;
	}

	public boolean hasDynamicForAudioSet(String setId) {
		return dynamicDao.hasDynamicForAudioSet(setId);
	}

	public void addPublishDynamic(Dynamic dynamic) {
		dynamic.setStatus(Status.STATUS_Y);

		dynamicDao.addDynamic(dynamic);
	}

	public void addAudioSetDynamic(Dynamic dynamic) {
		dynamic.setStatus(Status.STATUS_Y);

		String gradeIds = audioSetService.getGradeIdsBySetId(dynamic.getSetId());

		if (StringUtils.isEmpty(gradeIds)) {
			throw new BusinessException("习题集不存在");
		}
		if (dynamicDao.hasDynamicForAudioSet(dynamic.getSetId())) {
			throw new BusinessException("该习题集已经创建动态，不能重复创建");
		}

		dynamic.setGradeIds(gradeIds);

		dynamicDao.addDynamic(dynamic);
	}

	public void updateDynamic(Dynamic dynamic) {
		dynamicDao.updateDynamic(dynamic);
	}

	public List<Dynamic> listDynamicByIds(String[] dynamicIds, String voterId) {
		List<Dynamic> list = listDynamicFromRedis(dynamicIds);// dynamicDao.listDynamicByIds(dynamicIds);111

		dynamicMessageService.wrapperDynamicCountComment(list);
		dynamicUpvoteService.wrapperDynamicCountUpVote(list);
		commonService.wrapperTeacher(list);
		
		wrapperCanUpVote(list, voterId);
		wrapperAudioSet(list);
		commonService.wrapperGradeStr(list, "的同学们");

		return list;
	}

	public List<Dynamic> listDynamic(Map<String, Object> paramMap, String teacherId) {
		List<Dynamic> list = dynamicDao.listDynamic(paramMap);

		dynamicMessageService.wrapperDynamicCountComment(list);
		dynamicUpvoteService.wrapperDynamicCountUpVote(list);
		commonService.wrapperTeacher(list);
		
		wrapperCanUpVote(list, teacherId);
		wrapperAudioSet(list);
		commonService.wrapperGradeStr(list, "的同学们");
		
		return list;
	}

	private void wrapperCanUpVote(List<Dynamic> list, String votorId) {
		if (!CollectionUtils.isEmpty(list) && !StringUtils.isEmpty(votorId)) {
			Map<Long, Dynamic> dynamicMap = new HashMap<Long, Dynamic>();
			for (Dynamic item : list) {
				dynamicMap.put(item.getId(), item);
			}

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("dynamicIds", dynamicMap.keySet().toArray(new Long[] {}));
			paramMap.put("votorId", votorId);

			List<DynamicUpvote> upvotes = dynamicUpvoteDao.listUpVoteByIds(paramMap);

			if (!CollectionUtils.isEmpty(upvotes)) {
				for (DynamicUpvote item : upvotes) {
					dynamicMap.get(item.getDynamicId()).setCanUpVote(false);
				}
			}
		}
	}

	private void wrapperAudioSet(List<Dynamic> list) {
		Set<String> ids = new HashSet<String>();
		if (!CollectionUtils.isEmpty(list)) {
			for (Dynamic item : list) {
				if (!StringUtils.isEmpty(item.getSetId())) {
					ids.add(item.getSetId());
				}
			}

			List<AudioSet> audioSets = audioSetService.listAudioSetFromRedis(ids.toArray(new String[] {}));// audioSetDao.listAudioSetByIds(ids.toArray(new
																											// String[]
																											// {}));

			if (!CollectionUtils.isEmpty(audioSets)) {
				commonService.wrapperAudioSetCommentCount(audioSets);

				Map<String, AudioSet> map = new HashMap<String, AudioSet>();
				for (AudioSet item : audioSets) {
					map.put(item.getId(), item);
				}

				for (Dynamic item : list) {
					AudioSet audioset = map.get(item.getSetId());
					if (audioset != null) {
						item.setGradeIds(audioset.getGradeIds());
						item.setAudioSet(audioset);
					}
				}
			}
		}
	}

	public void removevDynamic(Long dynamicId, String teacherId) {
		Dynamic dynamic = dynamicDao.loadDynamic(dynamicId);
		if (dynamic == null) {
			throw new BusinessException("动态不存在!");
		}

		if (!StringUtils.equals(teacherId, dynamic.getTeacherId())) {
			throw new BusinessException("动态不属于你这老师，不能删除动态");
		}

		if (dynamic.getStatus() == Status.STATUS_N) {
			return;
		}

		dynamicDao.removeDynamic(dynamicId);
	}

	public Dynamic removevDynamic(Long dynamicId) {
		Dynamic dynamic = dynamicDao.loadDynamic(dynamicId);
		if (dynamic == null) {
			throw new BusinessException("动态不存在!");
		}

		if (dynamic.getStatus() == Status.STATUS_N) {
			throw new BusinessException("动态已经删除，不能重复删除");
		}

		dynamicDao.removeDynamic(dynamicId);

		logger.info("学生端接口-》删除动态[" + dynamicId + "]");

		return dynamic;
	}

	public Dynamic restoreDynamic(Long dynamicId) {
		Dynamic dynamic = dynamicDao.loadDynamic(dynamicId);
		if (dynamic == null) {
			throw new BusinessException("动态不存在!");
		}

		if (dynamic.getStatus() == Status.STATUS_Y) {
			throw new BusinessException("动态已经恢复，不能重复恢复");
		}

		dynamicDao.restoreDynamic(dynamicId);

		logger.info("学生端接口-》恢复动态[" + dynamicId + "]");

		return dynamic;
	}

	public Long getDynamicIdBySetId(String setId) {
		return dynamicDao.getDynamicIdBySetId(setId);
	}
}
