package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuexibao.teacher.common.CommonService;
import com.xuexibao.teacher.common.Status;
import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.core.GenericRedisTemplate;
import com.xuexibao.teacher.dao.AudioDao;
import com.xuexibao.teacher.dao.AudioSetDao;
import com.xuexibao.teacher.enums.AudioType;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.AudioBelongSet;
import com.xuexibao.teacher.model.AudioSet;
import com.xuexibao.teacher.model.AudioSetDetail;
import com.xuexibao.teacher.model.AudioSetRank;
import com.xuexibao.teacher.model.AudioUpload;
import com.xuexibao.teacher.model.Grade;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.vo.AudioBelongAudioSetCount;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * 
 * @author oldlu
 * 
 */
@Service
@Transactional
public class AudioSetService {
	@Resource
	private AudioSetDao audioSetDao;
	@Resource
	private AudioDao audioDao;
	@Resource
	private CommonService commonService;
	@Resource
	private StudentApiService studentApiService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private TeacherService teacherService;
	@Resource
	private GenericRedisTemplate<AudioSet> genericRedisTemplate;
	@Resource
	private OrganizationService organizationService;

	public List<AudioSet> listAudioSetFromRedis(String[] ids) {
		List<AudioSet> list = genericRedisTemplate.multiGet(RedisContstant.TEACHER_AUDIO_SET_CACHE_KEY,
				Arrays.asList(ids), AudioSet.class);
		List<String> noCacheIds = new ArrayList<String>();

		List<AudioSet> result = new ArrayList<AudioSet>();
		for (int i = 0, size = list.size(); i < size; i++) {
			AudioSet item = list.get(i);
			String id = ids[i];
			if (item == null) {
				noCacheIds.add(id);
			} else {
				result.add(item);
			}
		}

		if (!CollectionUtils.isEmpty(noCacheIds)) {
			Map<String, AudioSet> nocacheMap = new HashMap<String, AudioSet>();

			List<AudioSet> dblist = audioSetDao.listAudioSetByIds(noCacheIds.toArray(new String[] {}));
			for (AudioSet item : dblist) {
				item.setGradeIds(audioSetDao.getGradeIdsBySetId(item.getId()));
				item.setSubjectIds(audioSetDao.getSubjectIdsBySetId(item.getId()));

				nocacheMap.put(RedisContstant.TEACHER_AUDIO_SET_CACHE_KEY + item.getId(), item);
			}

			genericRedisTemplate.multiSet(nocacheMap);

			result.addAll(dblist);
		}

		return result;
	}

	public List<String> listAudioDate(Integer source) {
		return audioSetDao.listAudioDate(source);
	}

	public void setTopAudioSet(AudioSet set, String[] setIds) {
		audioSetDao.clearTopAudioSet(set);
		int i = 0;
		int size = setIds.length;
		for (String setId : setIds) {
			set.setOrderNo(-size + (i++));
			set.setId(setId);
			audioSetDao.setTopAudioSet(set);
		}
	}

	public AudioSet getAudioSetById(String setId) {
		AudioSet item = audioSetDao.loadAudioSet(setId);

		if (item != null) {
			commonService.wrapperAudioSetBuyCount(Arrays.asList(item));
		}
		return item;
	}

	public AudioSet loadAudioSet(String setId) {
		AudioSet set = audioSetDao.loadAudioSet(setId);

		if (set != null) {
			List<AudioSetDetail> items = audioSetDao.listAudioSetDetailBySetId(setId);

			commonService.wrapperExplanation(items);
			commonService.wrapperFeudAnswer(items);
			commonService.wrapperAudioGoodTag(items);

			set.setItems(items);

			set.setGradeIds(audioSetDao.getGradeIdsBySetId(setId));
			set.setSubjectIds(audioSetDao.getSubjectIdsBySetId(setId));

			commonService.wrapperAudioSetBuyCount(Arrays.asList(set));
			commonService.wrapperAudioSetCommentCount(Arrays.asList(set));
		}

		return set;
	}

	public String getGradeIdsBySetId(String setId) {
		return audioSetDao.getGradeIdsBySetId(setId);
	}

	public List<AudioSetDetail> listAudioBySetId(String setId) {
		List<AudioSetDetail> list = audioSetDao.listAudioSetDetailBySetId(setId);

		commonService.wrapperLatex(list);
		commonService.wrapperAudioGoodTag(list);

		return list;
	}

	public void removeAudioSet(AudioSet set) {
		AudioSet exists = audioSetDao.loadAudioSet(set.getId());
		if (exists == null) {
			return;
		}
		if (exists.getStatus() == Status.STATUS_N) {
			throw new BusinessException("习题集已经删除不能重复删除");
		}

		List<AudioSetDetail> items = audioSetDao.listAudioSetDetailBySetId(set.getId());

		if (!CollectionUtils.isEmpty(items)) {
			List<String> redisKeyAudioIds = new ArrayList<String>();

			for (AudioSetDetail item : items) {
				redisKeyAudioIds.add(RedisContstant.TEACHER_AUDIO_BELONG_AUDIOSET_COUNT_CACHE_KEY + item.getAudioId());
			}

			// 更新音频归属习题集缓存数
			List<String> redisAudioCountValueList = stringRedisTemplate.opsForValue().multiGet(redisKeyAudioIds);
			for (int i = 0, size = redisAudioCountValueList.size(); i < size; i++) {
				String value = redisAudioCountValueList.get(i);
				String key = redisKeyAudioIds.get(i);
				if (!StringUtils.isEmpty(value)) {
					stringRedisTemplate.opsForValue().increment(key, -1);
				}
			}
		}

		audioSetDao.removeAudioSet(set);
	}

	public void addAudioSet(AudioSet audioset) {
		audioset.setId(UUID.randomUUID().toString());

		audioSetDao.addAudioSet(audioset);

		List<AudioSetDetail> items = new ArrayList<AudioSetDetail>();
		int orderNo = 1;

		List<String> redisKeyAudioIds = new ArrayList<String>();
		List<String> audioIds = audioset.getAudioIds();
		for (String audioId : audioIds) {
			AudioSetDetail item = new AudioSetDetail();
			item.setAudioId(audioId);
			item.setSetId(audioset.getId());
			item.setOrderNo(orderNo++);
			items.add(item);

			redisKeyAudioIds.add(RedisContstant.TEACHER_AUDIO_BELONG_AUDIOSET_COUNT_CACHE_KEY + audioId);
		}

		commonService.wrapperAudio(items);
		commonService.wrapperLatex(items);

		audioSetDao.batchAddAudioSetDetail(items);

		// 保存习题集和年级关系
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("setId", audioset.getId());

		if (!StringUtils.isEmpty(audioset.getGradeIds())) {
			paramMap.put("gradeIds", audioset.getGradeIds().split(","));
			audioSetDao.addAudioSetGrade(paramMap);
		}

		// 保存习题集与科目的关系
		List<Integer> subjectIds = audioSetDao.getSubjectIdsByQuesiton(items);
		if (!CollectionUtils.isEmpty(subjectIds)) {
			paramMap.put("subjectIds", subjectIds);
			audioSetDao.addAudioSetSubject(paramMap);
		}

		// 更新音频归属习题集缓存数
		List<String> redisAudioCountValueList = stringRedisTemplate.opsForValue().multiGet(redisKeyAudioIds);
		for (int i = 0, size = redisAudioCountValueList.size(); i < size; i++) {
			String value = redisAudioCountValueList.get(i);
			String key = redisKeyAudioIds.get(i);
			if (!StringUtils.isEmpty(value)) {
				stringRedisTemplate.opsForValue().increment(key, 1);
			}
		}
	}

	public List<AudioSet> listAudioSet(String teacherId, Integer pageNo) {
		int limit = PageConstant.PAGE_SIZE_20;
		int start = pageNo * limit;
		List<AudioSet> list = audioSetDao.listAudioSet(teacherId, start, limit);
		commonService.wrapperAudioSetCommentCount(list);
		commonService.wrapperAudioSetBuyCount(list);
		commonService.wrapperAudioSetCount(list);

		return list;
	}

	public int countAudioSet(String teacherId) {
		return audioSetDao.countAudioSet(teacherId);
	}

	public List<AudioSet> listAudioSetByIds(String[] aryIds) {
		List<AudioSet> list = listAudioSetFromRedis(aryIds);// audioSetDao.listAudioSetByIds(aryIds);

		commonService.wrapperAudioSetCount(list);
		commonService.wrapperAudioSetCommentCount(list);
		return list;
	}

	public List<AudioUpload> listAudio(String teacherId, String filterDay, Integer audioSource, Integer pageNo) {
		int limit = PageConstant.PAGE_SIZE_20;
		int start = pageNo * limit;
		Teacher teacher = teacherService.getRequiredTeacher(teacherId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);
		paramMap.put("filterDay", filterDay);
		paramMap.put("filterDayBegin", filterDay + " 00:00:00");
		paramMap.put("filterDayEnd", filterDay + " 23:59:59");
		paramMap.put("audioSource", audioSource);
		paramMap.put("start", start);
		paramMap.put("limit", limit);
		paramMap.put("isPlanATeacher", organizationService.isPlanATeacher(teacher.getPhoneNumber()));

		List<AudioUpload> list = audioSetDao.listAudio(paramMap);

		commonService.wrapperLatex(list);
		commonService.wrapperExplanation(list);
		commonService.wrapperFeudAnswer(list);
		commonService.wrapperAudioGoodTag(list);
		wrapperChooseInfo(list);

		return list;
	}

	private void wrapperChooseInfo(List<AudioUpload> list) {
		List<String> redisKeyAudioIds = new ArrayList<String>();
		List<String> audioIds = new ArrayList<String>();
		Map<String, AudioUpload> map = new HashMap<String, AudioUpload>();
		for (AudioUpload audio : list) {
			audio.setCanSelect(true);

			if (audio.getType() == AudioType.audio.getValue() && audio.getDuration() < AudioUpload.DURATION_2_MIN) {// 判断时长限制
				audio.setCanSelect(false);
				audio.setSelectMessage(AudioUpload.SELECT_TEXT_DURATION);
			} else {
				audioIds.add(audio.getAudioId());
				map.put(audio.getAudioId(), audio);
				redisKeyAudioIds.add(RedisContstant.TEACHER_AUDIO_BELONG_AUDIOSET_COUNT_CACHE_KEY + audio.getAudioId());
			}
		}

		if (!CollectionUtils.isEmpty(redisKeyAudioIds)) {
			List<String> noCacheAudioIds = new ArrayList<String>();
			List<String> redisAudioCountValueList = stringRedisTemplate.opsForValue().multiGet(redisKeyAudioIds);
			for (int i = 0, size = redisAudioCountValueList.size(); i < size; i++) {
				String value = redisAudioCountValueList.get(i);
				String audioId = audioIds.get(i);
				if (StringUtils.isEmpty(value)) {
					noCacheAudioIds.add(audioId);
				} else {// 从cache中读
					Integer count = Integer.parseInt(value);
					if (count >= AudioUpload.BELONG_AUDUI_SET_LIMIT) {
						AudioUpload audio = map.get(audioId);
						audio.setCanSelect(false);
						audio.setSelectMessage(AudioUpload.SELECT_TEXT_LIMIT);
					}
				}
			}

			if (!CollectionUtils.isEmpty(noCacheAudioIds)) {// 从DB初始化,并放入redis
				List<AudioBelongAudioSetCount> listAudio = audioSetDao.listAudioBelongAudioSetCount(noCacheAudioIds);
				Map<String, String> setRedisCacheMap = new HashMap<String, String>();
				for (AudioBelongAudioSetCount item : listAudio) {
					String key = RedisContstant.TEACHER_AUDIO_BELONG_AUDIOSET_COUNT_CACHE_KEY + item.getAudioId();
					setRedisCacheMap.put(key, item.getCount() + "");
				}

				stringRedisTemplate.opsForValue().multiSet(setRedisCacheMap);
			}
		}
	}

	public int countAudio(String teacherId) {
		return audioDao.countAudio(teacherId);
	}

	public void updateAudioSetName(AudioSet set) {
		AudioSet dbSet = getAudioSetById(set.getId());
		if (dbSet == null) {
			throw new BusinessException("习题集不存在");
		}
		if (!StringUtils.equals(set.getTeacherId(), dbSet.getTeacherId())) {
			throw new BusinessException("教师ID不一致");
		}
		audioSetDao.updateAudioSet(set);
	}

	public void updateAudioSetGrade(AudioSet set) {
		AudioSet dbSet = getAudioSetById(set.getId());
		if (dbSet == null) {
			throw new BusinessException("习题集不存在");
		}
		audioSetDao.deleteGradeBySetId(set.getId());

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("setId", set.getId());
		paramMap.put("gradeIds", set.getGradeIds().split(","));
		audioSetDao.addAudioSetGrade(paramMap);
	}

	public void updateAudioSetDescription(AudioSet set) {
		AudioSet dbSet = getAudioSetById(set.getId());
		if (dbSet == null) {
			throw new BusinessException("习题集不存在");
		}
		if (!StringUtils.equals(set.getTeacherId(), dbSet.getTeacherId())) {
			throw new BusinessException("教师ID不一致");
		}
		audioSetDao.updateAudioSetDescription(set);
	}

	public List<AudioSetRank> queryAudioSetBySortType(String studentId, String subjectIds, String gradeIds,
			Integer type, Integer count, Integer pageno) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("start", (pageno - 1) * count);
		paramMap.put("limit", count);
		if (!StringUtils.isEmpty(gradeIds)) {
			paramMap.put("gradeIds", gradeIds.split(","));
		}
		if (!StringUtils.isEmpty(subjectIds)) {
			paramMap.put("subjectIds", subjectIds.split(","));
		}
		List<AudioSetRank> list = audioSetDao.queryAudioSetBySortType(paramMap);
		commonService.wrapperAudioSetBuyStatus(list, studentId, false);
		commonService.wrapperAudioSetCount(list);
		return list;
	}

	public Object queryAudioSetByAudioIds(String audioIdsStr) {
		String[] audioIds = audioIdsStr.split(",");
		List<AudioBelongSet> list = audioSetDao.queryAudioSetByAudioIds(audioIds);

		Map<String, List<AudioBelongSet>> result = new HashMap<String, List<AudioBelongSet>>();
		if (!CollectionUtils.isEmpty(list)) {
			commonService.wrapperTeacher(list);

			List<Map<String, String>> listSetGrade = audioSetDao.getGradeIdsBySetIds(list);
			Map<String, List<String>> setGradeMap = new HashMap<String, List<String>>();
			for (Map<String, String> item : listSetGrade) {
				String gradeIds = item.get("grade_ids");
				if (!StringUtils.isEmpty(gradeIds)) {
					List<String> listGradeStr = new ArrayList<String>();
					for (String gradeId : gradeIds.split(",")) {
						listGradeStr.add(Grade.GRADE_MAP.get(gradeId));
					}
					setGradeMap.put(item.get("set_id"), listGradeStr);
				}
			}

			for (AudioBelongSet item : list) {
				item.setGrades(setGradeMap.get(item.getSetId()));

				String audioId = item.getAudioId();
				List<AudioBelongSet> items = result.get(audioId);
				if (CollectionUtils.isEmpty(items)) {
					items = new ArrayList<AudioBelongSet>();
					result.put(audioId, items);
				}
				items.add(item);
			}
		}

		return result;
	}

	public void setAudioSetFreeStatus(String setId, String teacherId, Integer isFree) {
		AudioSet set = audioSetDao.loadAudioSet(setId);

		if (set == null) {
			throw new BusinessException("习题集不存在");
		}

		if (isFree == AudioSet.IS_FREE_YES && set.getIsFree() == AudioSet.IS_FREE_YES) {
			throw new BusinessException("习题集已经设置为免费，不能重复设置");
		}

		if (isFree == AudioSet.IS_FREE_NO && set.getIsFree() == AudioSet.IS_FREE_NO) {
			throw new BusinessException("习题集已经设置为收费，不能重复设置");
		}

		if (!StringUtils.equals(set.getTeacherId(), teacherId)) {
			throw new BusinessException("习题集不属于该老师，不能设置");
		}

		set.setIsFree(isFree);

		audioSetDao.setAudioSetFreeStatus(set);
	}

	/**
	 * redis增加教师习题集数
	 * 
	 * @param teacherId
	 */
	public void addAudioSetNumRedis(String teacherId) {
		String audioSetNum = stringRedisTemplate.opsForValue()
				.get(RedisContstant.TEACHER_AUDIOSETNUM_CACHE_KEY + teacherId);
		if (StringUtils.isBlank(audioSetNum)) {
			Integer newAudioSetNum = audioSetDao.countAudioSet(teacherId);
			stringRedisTemplate.opsForValue().increment(RedisContstant.TEACHER_AUDIOSETNUM_CACHE_KEY + teacherId,
					new Long(newAudioSetNum));
		} else {
			stringRedisTemplate.opsForValue().increment(RedisContstant.TEACHER_AUDIOSETNUM_CACHE_KEY + teacherId, 1L);
		}
	}

	/**
	 * redis教师删除习题集数
	 * 
	 * @param teacherId
	 */
	public void removeAudioSetNumRedis(String teacherId) {
		String audioSetNum = stringRedisTemplate.opsForValue()
				.get(RedisContstant.TEACHER_AUDIOSETNUM_CACHE_KEY + teacherId);
		if (StringUtils.isBlank(audioSetNum)) {
			Integer newAudioSetNum = audioSetDao.countAudioSet(teacherId);
			stringRedisTemplate.opsForValue().increment(RedisContstant.TEACHER_AUDIOSETNUM_CACHE_KEY + teacherId,
					new Long(newAudioSetNum));
		} else {
			stringRedisTemplate.opsForValue().increment(RedisContstant.TEACHER_AUDIOSETNUM_CACHE_KEY + teacherId, -1L);
		}
	}

	/**
	 * 获取习题集总数
	 * 
	 * @param teacherIds
	 * @return
	 */
	public Map<String, Long> getAudioSetNum(List<String> teacherIdList) {
		Set<String> teacherIdSet = new HashSet<String>();
		teacherIdSet.addAll(teacherIdList);
		List<String> teacherIds = new ArrayList<String>();
		teacherIds.addAll(teacherIdSet);
		List<String> keys = new ArrayList<String>();
		for (String teacherId : teacherIds) {
			keys.add(RedisContstant.TEACHER_AUDIOSETNUM_CACHE_KEY + teacherId);
		}
		List<String> audioSetNums = stringRedisTemplate.opsForValue().multiGet(keys);
		Map<String, Long> resultlMap = new HashMap<String, Long>();
		for (int i = 0; i < teacherIds.size(); i++) {
			String audioSetNum = audioSetNums.get(i);
			if (StringUtils.isBlank(audioSetNum)) {
				Integer newAudioSetNum = audioSetDao.countAudioSet(teacherIds.get(i));
				stringRedisTemplate.opsForValue().increment(
						RedisContstant.TEACHER_AUDIOSETNUM_CACHE_KEY + teacherIds.get(i),
						new Long(newAudioSetNum == null ? 0 : newAudioSetNum));
				resultlMap.put(teacherIds.get(i), new Long(newAudioSetNum));
			} else {
				resultlMap.put(teacherIds.get(i), new Long(Integer.parseInt(audioSetNum)));
			}
		}
		return resultlMap;
	}
}
