package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vdurmont.emoji.EmojiParser;
import com.xuexibao.teacher.core.GenericRedisTemplate;
import com.xuexibao.teacher.dao.TeacherDao;
import com.xuexibao.teacher.dao.TeacherFollowedDao;
import com.xuexibao.teacher.dao.TeacherGradeDao;
import com.xuexibao.teacher.dao.TeacherPayDailyRankDao;
import com.xuexibao.teacher.dao.TeacherSubjectDao;
import com.xuexibao.teacher.enums.PlanFactory;
import com.xuexibao.teacher.enums.TeacherIdentify;
import com.xuexibao.teacher.enums.TeacherStatus;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Organization;
import com.xuexibao.teacher.model.RecommendTeacherOrder;
import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.TeacherFollowed;
import com.xuexibao.teacher.model.TeacherGrade;
import com.xuexibao.teacher.model.TeacherSubject;
import com.xuexibao.teacher.model.vo.StudentVO;
import com.xuexibao.teacher.model.vo.TeacherStartAndPointVO;
import com.xuexibao.teacher.util.BusinessConstant;
import com.xuexibao.teacher.util.CipherUtil;
import com.xuexibao.teacher.util.RedisContstant;
import com.xuexibao.teacher.util.RedisUtil;
import com.xuexibao.teacher.util.UpYunUtil;
import com.xuexibao.webapi.teacher.client.T_DictService;
import com.xuexibao.webapi.teacher.model.City;
import com.xuexibao.webapi.teacher.model.School;

@Service
public class TeacherService {
	private static Logger logger = LoggerFactory.getLogger(TeacherService.class);

	private static Logger performLoggerWarn = LoggerFactory.getLogger("performLogWarn");
	
	private static Boolean IS_RUNNING_UPDATEAVART=false;

	@Resource
	private TeacherDao teacherDao;
	@Resource
	private VerifyCodeService verifyCodeService;
	@Resource
	private GenericRedisTemplate<Teacher> genericRedisTemplate;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private RedisTemplate redisTemplate;

	@Resource
	private TeacherGradeDao teacherGradeDao;
	@Resource
	private TeacherSubjectDao teacherSubjectDao;
	@Resource
	private TeacherFollowedDao teacherFollowedDao;
	@Resource
	private TeacherPayDailyRankDao teacherPayDailyRankDao;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private RedisUtil redisUtil;
	@Resource
	private T_DictService t_DictService;
	@Resource
	private AudioSetService audioSetService;
	@Resource
	private TeacherMessageService teacherMessageService;
	
	@Resource
	private FollowService followService;
	
	@Resource
	private StudentApiService studentApiService;

	/**
	 * 保存用户
	 */
	@Transactional
	public void save(Teacher teacher) {
		String teacherId = UUID.randomUUID().toString();
		teacher.setId(teacherId);
		teacher.setStatus(TeacherStatus.normal.getValue());

		teacherDao.addTeacher(teacher);

		refreshTeacherToRedis(teacherId);
	}

	/**
	 * 添加用户到Redis中
	 */
	public void setTeacher2Redis(Teacher teacher) {
		if (teacher != null) {
			genericRedisTemplate.set(RedisContstant.TEACHER_USER_CACHE_KEY + teacher.getId(), teacher);
		}
	}

	/**
	 * 从Redis中取出用户
	 */
	private Teacher getTeacherRedis(String teacherId) {
		return genericRedisTemplate.get(RedisContstant.TEACHER_USER_CACHE_KEY + teacherId, Teacher.class);
	}

	/**
	 * 校验是否存在该手机号注册的用户
	 */
	public boolean isExistUser(String phoneNumber) {
		return teacherDao.isExistTeacher(phoneNumber);
	}

	/**
	 * 通过电话查询用户
	 */
	public Teacher loadTeacherByPhoneNumber(String phoneNumber) {
		String teacherId = teacherDao.getIdByPhoneNumber(phoneNumber);

		if (StringUtils.isEmpty(teacherId)) {
			return null;
		}
		return getTeacher(teacherId);
	}

	/**
	 * 更新密码
	 */
	@Transactional
	public void updatePassword(Teacher teacher) {
		teacher.setPassword(CipherUtil.MD5(teacher.getPassword()));

		teacherDao.updatePassword(teacher);

		refreshTeacherToRedis(teacher.getId());
	}

	public void changePasswordByVerifyCode(String phoneNumber, String password, String verifyCode) {
		Teacher teacher = loadTeacherByPhoneNumber(phoneNumber);
		if (teacher == null) {
			throw new BusinessException("用户不存在或已停用.");
		}

		verifyCodeService.validateVerifyCode(phoneNumber, verifyCode, BusinessConstant.VERIFY_CODE_EXPIRATION_TIME);

		teacher.setPassword(password);
		updatePassword(teacher);
	}

	@Transactional
	public Teacher login(Teacher teacherParam) {
		Teacher teacher = loadTeacherByPhoneNumber(teacherParam.getPhoneNumber());
		if (teacher == null) {
			throw new BusinessException("手机号尚未注册.");
		}

		if (TeacherStatus.abnormal.getValue() == teacher.getStatus()) {
			throw new BusinessException("您的账号因为录题规范问题已被停用.");
		}

		if (!StringUtils.equalsIgnoreCase(CipherUtil.MD5(teacherParam.getPassword()), teacher.getPassword())) {
			throw new BusinessException("密码输入有误.");
		}

		teacher.setLastDeviceId(teacherParam.getLastDeviceId());
		teacher.setLastDeviceType(teacherParam.getLastDeviceType());
		teacher.setOnlineStatus(Teacher.ONLINE_STATUS_Y);
		teacherDao.setOnlineTeacher(teacher);

		setTeacher2Redis(teacher);

		updateDeviceStatus(teacher.getLastDeviceId(), teacher.getId());

		return teacher;
	}

	public void register(Teacher teacher) {
		String phoneNumber = teacher.getPhoneNumber();
		if (isExistUser(phoneNumber)) {
			throw new BusinessException("该手机号已被注册.");
		}

		verifyCodeService.validateVerifyCode(phoneNumber, teacher.getVerifyCode(), BusinessConstant.VERIFY_CODE_EXPIRATION_TIME);

		teacher.setPassword(CipherUtil.MD5(teacher.getPassword()));

		save(teacher);

		updateDeviceStatus(teacher.getLastDeviceId(), teacher.getId());
	}

	@Transactional
	public void updateTeacher(Teacher teacher) {
		if (!StringUtils.isEmpty(teacher.getSelfDescription())) {
			teacher.setSelfDescription(EmojiParser.parseToAliases(teacher.getSelfDescription()));
		}

		Teacher cacheTeacher = getRequiredTeacher(teacher.getId());

		Integer teacherIdentify = teacher.getTeacherIdentify();
		if (teacherIdentify == null) {
			teacherIdentify = cacheTeacher.getTeacherIdentify();
		}
		if (teacherIdentify == TeacherIdentify.teacher.getValue() && teacher.getCityId() > 0) {// 专职教师通过城市冗余省份
			City city = redisUtil.getCityById(Long.valueOf(teacher.getCityId()));

			if (city != null && city.getPid() != null) {
				teacher.setProvinceId(city.getPid().intValue());
			}
		}

		if (teacherIdentify == TeacherIdentify.student.getValue() && teacher.getSchoolId() > 0) {// 学生教师通过学校冗余城市、省份
			School school = redisUtil.getSchoolById(teacher.getSchoolId());
			if (school != null) {
				teacher.setCityId(school.getRealcityid());
				City city = redisUtil.getCityById(Long.valueOf(teacher.getCityId()));

				if (city != null && city.getPid() != null) {
					teacher.setProvinceId(city.getPid().intValue());
				}
			}
		}

		if (teacher.getCurOrgId() > 0) {
			if (cacheTeacher.getCurOrgId() > 0 && teacher.getCurOrgId() != cacheTeacher.getCurOrgId()) {
				throw new BusinessException("已经设置归属机构，不能修改");
			}

			if (teacherIdentify == TeacherIdentify.student.getValue()) {
				throw new BusinessException("只有社会教师才能设置所属机构");
			}
		}

		teacherDao.updateTeacher(teacher);

		updateTeacherGrade(teacher);

		updateTeacherSubject(teacher);

		refreshTeacherToRedis(teacher.getId());
	}

	private void updateTeacherGrade(Teacher teacher) {
		if (!CollectionUtils.isEmpty(teacher.getGradeIds())) {
			teacherGradeDao.deleteByTeacherId(teacher.getId());

			List<TeacherGrade> batchInsert = new ArrayList<TeacherGrade>();
			for (Integer gradeId : teacher.getGradeIds()) {
				TeacherGrade item = new TeacherGrade();
				item.setGradeId(gradeId);
				item.setTeacherId(teacher.getId());
				batchInsert.add(item);
			}
			teacherGradeDao.batchInsert(batchInsert);
		}
	}

	private void updateTeacherSubject(Teacher teacher) {
		if (!CollectionUtils.isEmpty(teacher.getSubjectIds())) {
			teacherSubjectDao.deleteByTeacherId(teacher.getId());

			List<TeacherSubject> batchInsert = new ArrayList<TeacherSubject>();
			for (Integer subjectId : teacher.getSubjectIds()) {
				TeacherSubject item = new TeacherSubject();
				item.setSubjectId(subjectId);
				item.setTeacherId(teacher.getId());
				batchInsert.add(item);
			}
			teacherSubjectDao.batchInsert(batchInsert);
		}
	}

	@Transactional
	public void updateSelfDescription(Teacher teacher) {
		if (!StringUtils.isEmpty(teacher.getSelfDescription())) {
			teacher.setSelfDescription(EmojiParser.parseToAliases(teacher.getSelfDescription()));
		}

		teacherDao.updateSelfDescription(teacher);

		refreshTeacherToRedis(teacher.getId());
	}

	public Teacher getTeacher(String teacherId) {
		Teacher teacher = getTeacherRedis(teacherId);
		if (teacher == null) {
			refreshTeacherToRedis(teacherId);

			return getTeacherRedis(teacherId);
		}
		return teacher;
	}

	@SuppressWarnings("unchecked")
	public Teacher getRequiredTeacher(String teacherId) {
		Teacher teacher = getTeacher(teacherId);
		if (teacher == null) {
			redisTemplate.opsForValue().set(RedisContstant.NOTEXIST_TEACHER_CACHE_KEY + teacherId, 0);
			throw new BusinessException("不存在ID为[" + teacherId + "]的教师");
		}
		return teacher;
	}

	public void refreshTeacherToRedis(String teacherId) {
		Teacher teacher = teacherDao.loadTeacherById(teacherId);
		if (teacher != null) {
			teacher.setGradeIds(teacherGradeDao.queryGradeIdByTeacherId(teacherId));
			teacher.setSubjectIds(teacherSubjectDao.querySubjectIdByTeacherId(teacherId));
			// 年级
			teacher.setGrades(redisUtil.getGradeStringByIds(teacher.getGradeIds()));
			// 学科
			teacher.setSubjects(redisUtil.getSubjectStringByIds(teacher.getSubjectIds()));
			// 城市
			City city = t_DictService.getCityById(new Long(teacher.getCityId()));
			if (city != null) {
				teacher.setCityName(city.getName());
			}
			// 学校
			School school = t_DictService.getSchoolById(teacher.getSchoolId());
			if (school != null) {
				teacher.setSchoolName(school.getName());
			}
			setTeacher2Redis(teacher);
		}
	}

	public void appendTeacherInfo(Map<String, Object> map, String teacherId) {
		Teacher redisTeacher = getRequiredTeacher(teacherId);

		map.put("countAudio", audioSetService.countAudio(teacherId));
		map.put("isOrgTeacher", redisTeacher.isOrgTeacher());
		map.put("planType", redisTeacher.getPlanType());
		
		boolean isOrg = organizationService.isOrgTeacher(teacherId);
		map.put("isOrg", isOrg);
		
		Organization org = organizationService.loadOrganization(redisTeacher.getCurOrgId());

		if (isOrg) {
			org = organizationService.loadOrgByCode(redisTeacher.getPhoneNumber());
		}
		
		if (org != null) {
			map.put("orgName", org.getOrganizationName());
		}
		map.put("countAudioSet", audioSetService.countAudioSet(teacherId));
		map.put("totalFollowed", followService.getTotalFollowedByTid(teacherId));
	}

	public Object queryTeacherByMap(Map<String, Object> paramMap) {
		List<String> listTeacherId = teacherDao.queryTeacherIdByMap(paramMap);
		// 星级积分
		List<TeacherStartAndPointVO> starList = teacherDao.getTeachersWithStarAndPointByIds(listTeacherId);

		List<Map<String, Object>> resultList = getTeacherBaseInfoList(listTeacherId);

		Map<String, Map<String, Object>> resultMap = new LinkedHashMap<String, Map<String, Object>>();
		for (Map<String, Object> item : resultList) {
			// 星级
			for (TeacherStartAndPointVO startTeacher : starList) {
				if (StringUtils.equals((String) item.get("teacherId"), startTeacher.getId())) {
					item.put("star", startTeacher.getStar());
					item.put("point", startTeacher.getPoint());
					break;
				}
			}
			resultMap.put((String) item.get("teacherId"), item);
		}
		return resultMap.values();
	}

	/**
	 * 查询老师及跟老师个关注关系
	 * 
	 * @param paramMap
	 * @param userId
	 * @return
	 */
	public Object queryTeacherByMapWithUserId(Map<String, Object> paramMap, String userId) {
		List<String> listTeacherId = teacherDao.queryTeacherIdByMap(paramMap);
		if (CollectionUtils.isEmpty(listTeacherId)) {
			return listTeacherId;
		}
		List<Map<String, Object>> resultList = queryTeacherWithFollow(listTeacherId, userId);
		//展示更多附加的信息,显示留言总数，习题集总数，习题集好评率，每周动态
		resultList = showAppenderInfo(resultList,true,true,true);
		return resultList;
	}

	/**
	 * 查询学生未关注的老师列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public Object queryUnFollowedTeacherIdByMap(Map<String, Object> paramMap) {
		List<String> listTeacherId = teacherDao.queryUnFollowedTeacherIdByMap(paramMap);
		List<Map<String, Object>> resultList = getTeacherBaseInfoList(listTeacherId);
		return resultList;
	}

	public StarPoint getStarPointByTeacherId(String teacherId) {
		StarPoint starPoint = teacherDao.getPointAndRatingByTeacherId(teacherId);
		if (starPoint != null) {
			return starPoint;
		}
		return new StarPoint();
	}

	/**
	 * 获取老师的信息
	 * 
	 * @param teacherId
	 * @return
	 */
	public Map<String, Object> getTeacherInfo(String teacherId) {
		try {
			Teacher teacher = getRequiredTeacher(teacherId);
			StarPoint starPoint = getStarPointByTeacherId(teacherId);

			Map<String, Object> item = new HashMap<String, Object>();
			item.put("teacherId", teacher.getId());
			item.put("name", teacher.getWrapperName());
			item.put("nickname", teacher.getWrapperNickname());
			item.put("subjects", teacher.getSubjects());
			item.put("grades", teacher.getGrades());
			item.put("avatarUrl", teacher.getAvatarUrl());
			item.put("star", starPoint.getStar());
			item.put("courseYear", teacher.getCourseYear());
			item.put("selfDescription", teacher.getSelfDescription());
			item.put("gender", teacher.getGender());
			item.put("teacherIdentify", teacher.getTeacherIdentify());
			item.put("point", starPoint.getPoint());
			if (teacher.getTeacherIdentify() == TeacherIdentify.teacher.getValue()) {
				item.put("cityName", teacher.getCityName());
			} else {
				item.put("schoolName", teacher.getSchoolName());
			}
			appendTeacherInfo(item, teacherId);
			return item;
		} catch (Exception ex) {

		}
		return null;
	}

	/**
	 * 获取老师基础的信息
	 * 
	 * @param teacherId
	 * @return
	 */
	public Map<String, Object> getTeacherBaseInfo(String teacherId) {
		try {
			Teacher teacher = getRequiredTeacher(teacherId);
			return getBaseInfoByTeacher(teacher);
		} catch (Exception ex) {
			logger.error("TeacherService.getTeacherBaseInfo", ex);
		}
		return null;
	}

	/**
	 * 批量获取教师基本信息
	 * 
	 * @param teacherIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTeacherBaseInfoList(List<String> teacherIds) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		// 构造keys
		List<String> teacherKeys = new ArrayList<String>();
		List<String> orgTeacherKeys = new ArrayList<String>();
		for (String teacherid : teacherIds) {
			teacherKeys.add(RedisContstant.TEACHER_USER_CACHE_KEY + teacherid);
			orgTeacherKeys.add(RedisContstant.TEACHER_ORG_TEACHER_CACHE_KEY + teacherid);
		}
		// mget教师
		List<Teacher> teacherList = genericRedisTemplate.multiGet(teacherKeys, Teacher.class);
		// mget 是否是机构老师
		List<String> orgTeacherList = stringRedisTemplate.opsForValue().multiGet(orgTeacherKeys);

		for (int index = 0; index < teacherIds.size(); index++) {
			Teacher teacher = teacherList.get(index);
			String isOrgString = orgTeacherList.get(index);
			// 返回的item map
			Map<String, Object> item = new HashMap<String, Object>();
			if (teacher == null) {
				String teacherId = teacherIds.get(index);
				// 如果该教师id不存在，直接跳过
				if (redisTemplate.hasKey(RedisContstant.NOTEXIST_TEACHER_CACHE_KEY + teacherId)) {
					continue;
				}
				Teacher newTeacher = null;
				try {
					newTeacher = getRequiredTeacher(teacherId);
				} catch (Exception e) {
				}
				if (newTeacher == null) {
					continue;
				}
				item = getBaseInfoByTeacher(newTeacher);
				if (StringUtils.isEmpty(isOrgString)) {
					Boolean isOrg = organizationService.isOrgTeacher(teacherId);
					item.put("isOrgTeacher", isOrg);
					item.put("isOrg", isOrg);
				} else if (StringUtils.equalsIgnoreCase(isOrgString, "true")) {
					item.put("isOrgTeacher", Boolean.TRUE);
					item.put("isOrg", Boolean.TRUE);
				} else {
					item.put("isOrgTeacher", Boolean.FALSE);
					item.put("isOrg", Boolean.FALSE);
				}
				teacherList.set(index, newTeacher);
			} else {
				item = getBaseInfoByTeacher(teacher);
				if (StringUtils.isEmpty(isOrgString)) {
					Boolean isOrg = organizationService.isOrgTeacher(teacher.getId());
					item.put("isOrgTeacher", isOrg);
					item.put("isOrg", isOrg);
				} else if (StringUtils.equalsIgnoreCase(isOrgString, "true")) {
					item.put("isOrgTeacher", Boolean.TRUE);
					item.put("isOrg", Boolean.TRUE);
				} else {
					item.put("isOrgTeacher", Boolean.FALSE);
					item.put("isOrg", Boolean.FALSE);
				}
			}
			resultList.add(item);
		}
		//获取积分和星级
		if(teacherIds.size()>0){
			List<TeacherStartAndPointVO> startPointList=teacherDao.getTeachersWithStarAndPointByIds(teacherIds);
			for(Map<String, Object> item:resultList){
				for(TeacherStartAndPointVO startPoint:startPointList){
					if(StringUtils.equals((String)item.get("teacherId"), startPoint.getId())){
						item.put("star", startPoint.getStar());
						item.put("point", startPoint.getPoint());
						break;
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * 获取老师基础的信息
	 * 
	 * @param teacherId
	 * @return
	 */
	public Map<String, Object> getBaseInfoByTeacher(Teacher teacher) {
		try {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("teacherId", teacher.getId());
			item.put("name", teacher.getWrapperName());
			item.put("nickname", teacher.getWrapperNickname());
			item.put("subjects", teacher.getSubjects());
			item.put("grades", teacher.getGrades());
			item.put("avatarUrl", teacher.getAvatarUrl());
			item.put("courseYear", teacher.getCourseYear());
			item.put("selfDescription", teacher.getSelfDescription());
			item.put("gender", teacher.getGender());
			item.put("teacherIdentify", teacher.getTeacherIdentify());
			item.put("courseYear", teacher.getCourseYear());
			if (teacher.getTeacherIdentify() != null) {
				if (teacher.getTeacherIdentify() == TeacherIdentify.teacher.getValue()) {
					item.put("cityName", teacher.getCityName());
				} else {
					item.put("schoolName", teacher.getSchoolName());
				}
			}
			return item;
		} catch (Exception ex) {
			logger.error("TeacherService.getBaseInfoByTeacher", ex);
		}
		return null;
	}
	
	/**
	 * 是否附加显示更多信息
	 * @param resuList
	 * @param messageNum  留言总数
	 * @param audioSetNum 习题集总数
	 * @param audioSetGoodEval 好评率
	 */
	public List<Map<String, Object>> showAppenderInfo(List<Map<String, Object>> resultList,Boolean messageNum,Boolean audioSetNum,Boolean audioSetGoodEval){
		if(resultList==null||resultList.size()==0){
			return resultList;
		}
		List<String> teacherIds=new ArrayList<String>();
		for(Map<String, Object> item:resultList){
    		String teacherId=(String)item.get("teacherId");
    		teacherIds.add(teacherId);
    	}
		//留言总数
		Map<String, Long> messageNumMap=null;
		if(messageNum){
			messageNumMap=teacherMessageService.getMessageNum(teacherIds);
		}
		//习题集总数
		Map<String, Long> audioSetNuMap=null;
		if(audioSetNum){
			audioSetNuMap=audioSetService.getAudioSetNum(teacherIds);
		}
		
		//习题集的好评率
		Map<String,String> rateMap=studentApiService.getExercisesRate(StringUtils.join(teacherIds, ","));
		
		for(Map<String, Object> item:resultList){
    		String teacherId=(String)item.get("teacherId");
    		if(messageNum){
    			item.put("messageNum", messageNumMap.get(teacherId));
    		}
    		if(audioSetNum){
    			item.put("audioSetNum", audioSetNuMap.get(teacherId));
    		}
			if(audioSetGoodEval){
				item.put("goodEvalRate", rateMap.get(teacherId));
			}
    	}
		return  resultList;
	}

	/**
	 * 查询教师及跟某个学生的关注关系
	 * 
	 * @param teacherIds
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, Object>> queryTeacherWithFollow(List<String> teacherIds, String userId) {


		// 查询跟学生的follow关系
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("teacherIds", teacherIds);

		List<String> teacherFollowedList = getUserFollowedList(userId);

		// 关注数量
//		Map queyrmap = new HashMap();
//		queyrmap.put("teacherIds", teacherIds);
//		List<String> followNumList = getTeacherFollowedNumList(teacherIds);
		
		// 批量获取信息
		List<Map<String, Object>> resultList = getTeacherBaseInfoList(teacherIds);

		for (int i = 0; i < resultList.size(); i++) {
			Map<String, Object> item = resultList.get(i);
			if (item != null) {
				String teacherId = (String) item.get("teacherId");
				item.put("isFollowed", false);
				for (String f : teacherFollowedList) {
					if (teacherId.equalsIgnoreCase(f)) {
						item.put("isFollowed", true);
						break;
					}
				}
				// 关注数量
//				Integer followedNum = Integer.parseInt(followNumList.get(i));
//				item.put("totalFollowed", followedNum);
			}
		}
		return resultList;
	}

	/**
	 * 获取学生关注的老师列表
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> getUserFollowedList(String userId) {
		List<String> teacherIds = (List<String>) redisTemplate.opsForValue().get(RedisContstant.USER_FOLLOWED_CACHE_KEY + userId);
		if (teacherIds == null) {
			Map paramMap = new HashMap();
			paramMap.put("studentId", userId);
			paramMap.put("startIndex", 0);
			paramMap.put("pageSize", Integer.MAX_VALUE);
			List<TeacherFollowed> teacherFollowdList = teacherFollowedDao.getFollowListByUserId(paramMap);
			teacherIds = new ArrayList<String>();
			for (TeacherFollowed followed : teacherFollowdList) {
				teacherIds.add(followed.getTeacherId());
			}
			redisTemplate.opsForValue().set(RedisContstant.USER_FOLLOWED_CACHE_KEY + userId, teacherIds);
		}
		return teacherIds;
	}

	/**
	 * 获取老师被关注的数量
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getTeacherFollowedNumList(List<String> teacherIds) {
		List<String> keysList = new ArrayList<String>();
		for (String teacherId : teacherIds) {
			keysList.add(RedisContstant.TEACHER_FOLLOWEDNUM_CACHE_KEY + teacherId);
		}

		List<String> followedNumList = (List<String>) stringRedisTemplate.opsForValue().multiGet(keysList);
		for (int i = 0; i < teacherIds.size(); i++) {
			String teacherId = teacherIds.get(i);
			String followedNum = followedNumList.get(i);
			if (StringUtils.isEmpty(followedNum)) {
				Integer followed = teacherFollowedDao.getTotalFollowedByTid(teacherId);
				followedNumList.set(i, String.valueOf(followed == null ? 0 : followed));
				stringRedisTemplate.opsForValue().set(RedisContstant.TEACHER_FOLLOWEDNUM_CACHE_KEY + teacherId,
						String.valueOf(followed == null ? 0 : followed));
			}
		}
		return followedNumList;
	}

	public void updateDeviceStatus(String lastDeviceId, String teacherId) {
		List<String> listTeacherId = teacherDao.listTeacherIdByDeviceId(lastDeviceId);
		if (!CollectionUtils.isEmpty(listTeacherId)) {
			for (String item : listTeacherId) {
				if (!StringUtils.equals(item, teacherId)) {
					teacherDao.setOfflineTeacher(item);

					Teacher teacher = getTeacherRedis(item);
					if (teacher != null) {
						teacher.setOnlineStatus(Teacher.ONLINE_STATUS_N);

						setTeacher2Redis(teacher);
					}
				}
			}
		}
	}

	public String changeStar(String phoneNumber, Integer star, Integer point) {
		Teacher teacher = loadTeacherByPhoneNumber(phoneNumber);
		if (teacher == null) {
			throw new BusinessException("教师不存在");
		}
		teacherDao.updatePoint2(teacher.getId(), point);
		teacherDao.updateStar(teacher.getId(), star);

		return teacher.getId();
	}

	/**
	 * @param teacherId
	 */
	public void unjoinCapacityTest(String teacherId) {
		teacherDao.unjoinCapacityTest(teacherId);

		Teacher teacher = getRequiredTeacher(teacherId);

		teacher.setCapacityTestIsJoined(Teacher.CAPACITY_TEST_IS_JOINED);

		setTeacher2Redis(teacher);

		logger.info("[" + teacherId + "]不参加能力测试");
	}

	public void bindIosToken(String teacherId, String iosToken) {
		Teacher teacher = getRequiredTeacher(teacherId);
		teacher.setIosToken(iosToken);

		teacherDao.updateIosToken(teacher);

		setTeacher2Redis(teacher);
	}

	public void logout(String teacherId) {
		teacherDao.setOfflineTeacher(teacherId);

		Teacher teacher = getRequiredTeacher(teacherId);
		teacher.setOnlineStatus(Teacher.ONLINE_STATUS_N);

		setTeacher2Redis(teacher);
	}

	/**
	 * 获取推荐名师
	 * 
	 * @param paramMap
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map<String, Object>> getRecommTeacher(String userId, Map paramMap) {
		List<RecommendTeacherOrder> teachers = teacherDao.getRecommTeacher(paramMap);
		return getRankListTeacherDetail(userId, teachers);
	}

	/**
	 * 获取排行榜老师详细信息
	 * 
	 * @param userId
	 * @param teacherIds
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> getRankListTeacherDetail(String userId, List<RecommendTeacherOrder> teachers) {

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<String> teacherIds = new ArrayList<String>();
		if (teachers.size() > 0) {
			for (RecommendTeacherOrder teacherOrder : teachers) {
				teacherIds.add(teacherOrder.getTeacherId());
			}
			mapList = getTeacherBaseInfoList(teacherIds);
			for (Map<String, Object> item : mapList) {
				String nickName = (String) item.get("nickname");
				item.put("nick", nickName);
				item.remove("nickname");
				item.put("isFollowed", Boolean.FALSE);
			}
			// 关注关系
			Map paramMap = new HashMap();
			paramMap.put("userId", userId);
			paramMap.put("teacherIds", teacherIds);
			List<TeacherFollowed> followList = teacherFollowedDao.queryByTeacherIdsAndUserId(paramMap);
			for (TeacherFollowed follow : followList) {
				for (Map map : mapList) {
					if (StringUtils.equals((String) map.get("teacherId"), follow.getTeacherId())) {
						map.put("isFollowed", Boolean.TRUE);
						break;
					}
				}
			}
			// 关注数量
			Map queyrmap = new HashMap();
			queyrmap.put("teacherIds", teacherIds);
			List<TeacherFollowed> followNumList = teacherFollowedDao.getFollowedNumByTeacherIds(queyrmap);
			for (TeacherFollowed followNum : followNumList) {
				for (Map map : mapList) {
					if (StringUtils.equals((String) map.get("teacherId"), followNum.getTeacherId())) {
						map.put("followedNum", followNum.getTeacherFollowedNum());
						break;
					}
				}
			}
		}
		return mapList;
	}

	public void bindBank(Teacher teacher) {
		if (teacherDao.isExistIdNumber(teacher)) {
			throw new BusinessException("该身份证已使用,请重新填写");
		}

		if (teacherDao.isExistBankCard(teacher)) {
			throw new BusinessException("该银行卡号已使用,请更换卡片");
		}

		teacherDao.bindBank(teacher);
	}
	
	public void unbindBank(Teacher teacher) {
		teacherDao.unbindBank(teacher);
	}

	public void sendOrgInvitationCode(String teacherId, String code) {
		Teacher teacher = getRequiredTeacher(teacherId);
		if (teacher.getCurOrgId() > 0) {
			throw new BusinessException("已经有归属机构，不能发送邀请码");
		}

//		if (!StringUtils.isEmpty(teacher.getPlanType())) {
//			throw new BusinessException("已经属于plan老师，不能发送邀请码");
//		}

		Organization org = organizationService.loadOrgByCode(code);
		if (org == null) {
			throw new BusinessException("邀请码错误，邀请码不存在");
		}

		if (!StringUtils.equals(PlanFactory.PLAN_B, org.getPlanType())) {
			throw new BusinessException("该机构不属于planB不能绑定邀请码");
		}

		teacher.setCurOrgId(org.getId());
		teacher.setPlanType(PlanFactory.PLAN_B);

		teacherDao.updateTeacherPlanType(teacher);
	}
	
	/**
	 * 获取关注我的学生列表
	 * @param sortType，1关注时间降序，2购买数量从高到低，3购买数量从低到高
	 * @param gradeId 年级 -1标示全部
	 * @param pageNo 第几页从1开始
	 * @param pageSize 每页的记录数
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> getMyStudentList(String teacherId,Integer sortType,Integer gradeId,Integer pageNo,Integer pageSize){
		List<StudentVO> studentVOs=new ArrayList<StudentVO>();
		Map resutlMap=new HashMap<String, Object>();
		
		Map dataMap=studentApiService.getFollowList(teacherId, sortType, gradeId, pageNo, pageSize);
		
		List<Map> mapList=(List<Map>)dataMap.get("studentList");
		
		
		//学生列表
		for(Map map:mapList){
			StudentVO vo=new StudentVO();
			if(map.get("profile_image_url")!=null){
				vo.setAvartUrl((String)map.get("profile_image_url"));
			}
			if(map.get("buy_count")!=null){
				vo.setBuyNum((Integer)map.get("buy_count"));
			}
			if(map.get("gender")!=null){
				vo.setGender((Integer)map.get("gender"));
			}
			if(map.get("edu_grade")!=null){
				vo.setGradeName((String)map.get("edu_grade"));
			}
			if(map.get("loginname")!=null){
				vo.setNickName((String)map.get("loginname"));
			}
			if(map.get("edu_school")!=null){
				vo.setSchoolName((String)map.get("edu_school"));
			}
			studentVOs.add(vo);
		}
		//年级列表
		Map gradeMap=(Map)dataMap.get("grades");
		
		//数量
		resutlMap.put("studentNum", dataMap.get("studentNum"));
		resutlMap.put("studentList", studentVOs);
		resutlMap.put("grades", gradeMap);
		return resutlMap;
		
	}
	
	
	/**
	 * 批量更新教师头像图片，上传到又拍云，url地址更换成又拍云地址
	 */
	public void updateTeacherAvart(){
		
		if(!IS_RUNNING_UPDATEAVART){
		  try{	
			  IS_RUNNING_UPDATEAVART=true;
			  while(true){
				  List<Teacher> teacherList = teacherDao.getTeachersForAvartImg();
				  if(teacherList==null||teacherList.size()==0){
					  break;
				  }
				  for(Teacher teacher:teacherList){
					  String avartUrl=teacher.getAvatarUrl();
					  if(StringUtils.contains(avartUrl, "http://xuexibao1.b0.upaiyun.com")){
						  continue;
					  }
					  if(StringUtils.isNotEmpty(avartUrl)){
						  String upYunUrl=UpYunUtil.uploadAvartImg(avartUrl);
						  //上传成功
						  if(StringUtils.isNotBlank(upYunUrl)){
							  teacherDao.updateAvartImg(teacher.getId(),upYunUrl);
							  //刷新缓存
							  refreshTeacherToRedis(teacher.getId());
						  }else{
							  System.out.println("----教师:"+teacher.getId()+",更新头像失败！");
						  }
					  }
				  }
			  }
			  }catch (Exception e) {
				logger.error("更新教师头像出错", e);
			  }finally{
				  IS_RUNNING_UPDATEAVART=false;
			  }
		}
	}
	
}
