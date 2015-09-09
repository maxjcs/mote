package com.xuexibao.teacher.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.common.CommonService;
import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.enums.AudioType;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioSet;
import com.xuexibao.teacher.model.AudioSetRank;
import com.xuexibao.teacher.model.AudioStudentEvaluation;
import com.xuexibao.teacher.model.FeudDetailWb;
import com.xuexibao.teacher.model.vo.AudioTeacherVO;
import com.xuexibao.teacher.pay.dao.PayAudioDao;
import com.xuexibao.teacher.pay.model.PayAudio;
import com.xuexibao.teacher.service.AudioSetService;
import com.xuexibao.teacher.service.AudioStudentEvaluationService;
import com.xuexibao.teacher.service.FeudDetailWbService;
import com.xuexibao.teacher.service.StudentService;
import com.xuexibao.teacher.service.TeacherService;
import com.xuexibao.teacher.validator.Validator;
import com.xuexibao.webapi.student.client.T_UserService;
import com.xuexibao.webapi.student.model.User;
import com.xuexibao.webapi.teacher.client.T_DictService;
import com.xuexibao.webapi.teacher.model.City;
import com.xuexibao.webapi.teacher.model.Grade;
import com.xuexibao.webapi.teacher.model.GradeSubject;
import com.xuexibao.webapi.teacher.model.School;
import com.xuexibao.webapi.teacher.model.Subject;

/**
 * 
 * @author oldlu
 * 
 */
@Controller
@RequestMapping("student")
public class StudentController extends AbstractController {
	private static Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Resource
	private TeacherService teacherService;
	@Resource
	private StudentService studentService;
	@Resource
	private AudioSetService audioSetService;

	@Resource
	T_DictService t_DictService;// 中间件的 字典服务

	@Resource
	private FeudDetailWbService feudDetailWbService;
	@Resource
	private CommonService commonService;
	@Resource
	private AudioStudentEvaluationService audioStudentEvaluationService;
	
	@Resource
	T_UserService t_UserService;
	
	@Resource
	PayAudioDao payAudioDao;

	/**
	 * 获取推荐名师列表，名师排行榜
	 * 
	 * @param id
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getRecommTeacher")
	public Object getRecommTeacher(String userId, Integer current, Integer pageSize) {
		if (current == null) {
			current = 0;
		}
		if (pageSize == null) {
			pageSize = PageConstant.PAGE_SIZE_10;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("current", current);
		paramMap.put("pageSize", pageSize);
		List<Map<String, Object>> mapList = teacherService.getRecommTeacher(userId, paramMap);
		if (mapList != null) {
			return dataStudentJson(mapList);
		} else {
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	@ResponseBody
	@RequestMapping(value = "queryTeacher")
	public Object queryTeacher(Integer pageNo, Integer gradeId, Integer cityId, Integer pageSize, Integer subjectId, String name) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = PageConstant.PAGE_SIZE_10;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", (pageNo - 1) * pageSize);
		paramMap.put("limit", pageSize);
		paramMap.put("subjectId", subjectId);
		paramMap.put("cityId", cityId);
		paramMap.put("gradeId", gradeId);
		paramMap.put("name", name);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 0);
		result.put("msg", "ok");

		try {
			result.put("result", teacherService.queryTeacherByMap(paramMap));

			return result;
		} catch (Exception e) {
			logger.error("queryTeacher", e);
			result.put("status", -1);
			result.put("msg", "服务器异常，请重试.");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "queryTeacherWithUserId")
	public Object queryTeacherWithUserId(Integer pageNo, Integer gradeId, Integer cityId, Integer pageSize, Integer subjectId, String name,
			String userId) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = PageConstant.PAGE_SIZE_10;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", (pageNo - 1) * pageSize);
		paramMap.put("limit", pageSize);
		paramMap.put("subjectId", subjectId);
		paramMap.put("cityId", cityId);
		paramMap.put("gradeId", gradeId);
		paramMap.put("name", name);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 0);
		result.put("msg", "ok");

		try {
			result.put("result", teacherService.queryTeacherByMapWithUserId(paramMap, userId));
			return result;
		} catch (Exception e) {
			logger.error("queryTeacher", e);
			result.put("status", -1);
			result.put("msg", "服务器异常，请重试.");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "queryUnFollowedTeachers")
	public Object queryUnFollowedTeachers(Integer pageNo, Integer gradeId, Integer pageSize, Integer subjectId, String studentId,
			Integer sortType) {
		Validator.validateBlank(studentId, "学生ID不能为空!");
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = PageConstant.PAGE_SIZE_10;
		}
		if(sortType==null){//默认按星级排序
			sortType=4;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", (pageNo - 1) * pageSize);
		paramMap.put("limit", pageSize);
		paramMap.put("subjectId", subjectId);
		paramMap.put("gradeId", gradeId);
		paramMap.put("studentId", studentId);
		paramMap.put("sortType", sortType);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 0);
		result.put("msg", "ok");

		try {
			result.put("result", teacherService.queryUnFollowedTeacherIdByMap(paramMap));
			return result;
		} catch (Exception e) {
			logger.error("queryUnFollowedTeachers", e);
			result.put("status", -1);
			result.put("msg", "服务器异常，请重试.");
			return result;
		}
	}


	@ResponseBody
	@RequestMapping(value = "getAudioSetMoneyV13")
	public Object getAudioSetMoneyV13(String id) {
		Validator.validateBlank(id, "习题集Id不能为空!");

		Map<String, Object> data = studentService.getAudioSetMoneyV13(id);

		return dataStudentJson(data);
	}

	@ResponseBody
	@RequestMapping(value = "getAudioMoneyV13")
	public Object getAudioMoneyV13(String teacherId, String studentId, String audioId) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 0);
		result.put("msg", "ok");
		if (StringUtils.isEmpty(teacherId)) {
			result.put("status", -1);
			result.put("msg", "教师ID不能为空!");
			return result;
		}
		if (StringUtils.isEmpty(studentId)) {
			result.put("status", -1);
			result.put("msg", "学生ID不能为空!");
			return result;
		}
		if (StringUtils.isEmpty(audioId)) {
			result.put("status", -1);
			result.put("msg", "音频ID不能为空!");
			return result;
		}

		try {
			Map<String, Object> data = studentService.getAudioOrgTeacherMoneyV13(teacherId, audioId);

			result.put("result", data);

			return result;
		} catch (Exception e) {
			logger.error("getAudioMoney", e);
			result.put("status", -1);
			result.put("msg", e.getMessage());
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "getTeacherByStudentId")
	public Object getTeacherByStudentId(String teacherId, String studentId) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 0);
		result.put("msg", "ok");
		if (StringUtils.isEmpty(teacherId)) {
			return errorStudentJson("教师ID不能为空!");
		}
		if (StringUtils.isEmpty(studentId)) {
			result.put("status", -1);
			result.put("msg", "学生ID不能为空!");
			return result;
		}

		try {
			Map<String, Object> data = studentService.getTeacher(teacherId, studentId);
			result.put("result", data);

			return result;
		} catch (Exception e) {
			logger.error("getTeacherByStudentId", e);
			result.put("status", -1);
			result.put("msg", "服务器异常，请重试.");
			return result;
		}
	}

	/**
	 * 查询老师的详情及跟某个学生的follow情况
	 * 
	 * @param teacherIds
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryTeacherWithFollow")
	public Object queryTeacherWithFollow(String teacherIds, String userId) {
		Validator.validateBlank(teacherIds, "教师ID不能为空!");
		Validator.validateBlank(userId, "学生ID不能为空!");
		try{
			List<String> tidList = new ArrayList<String>();
			String[] ids = teacherIds.split(",");
			for (String id : ids) {
				if (StringUtils.isNotBlank(id)) {
					tidList.add(id);
				}
			}
			List<Map<String, Object>> resultList=teacherService.queryTeacherWithFollow(tidList, userId);
			//展示更多附加的信息,显示留言总数，习题集总数，习题集好评率,每周动态数
			teacherService.showAppenderInfo(resultList, true, true, true);
			return dataJson(resultList);
		}catch(Exception ex){
			logger.error("queryTeacherWithFollow", ex);
		}
		return errorJson("服务器异常，请重试.");
		
	}
	
	/**
	 * 查询老师的详情及跟某个学生的follow情况
	 * 
	 * @param teacherIds
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryTeacherWithFollow2")
	public Object queryTeacherWithFollow2(String teacherIds, String userId) {
		Validator.validateBlank(teacherIds, "教师ID不能为空!");
		Validator.validateBlank(userId, "学生ID不能为空!");
		try{
			List<String> tidList = new ArrayList<String>();
			String[] ids = teacherIds.split(",");
			for (String id : ids) {
				if (StringUtils.isNotBlank(id)) {
					tidList.add(id);
				}
			}
			List<Map<String, Object>> resultList=teacherService.queryTeacherWithFollow(tidList, userId);
			
			for (int i = 0; i < resultList.size(); i++) {
				Map<String, Object> item = resultList.get(i);
				if (item != null) {
					item.remove("subjects");
					item.remove("isOrg");
					item.remove("cityName");
					item.remove("isOrgTeacher");
					item.remove("totalFollowed");
					item.remove("avatarUrl");
					item.remove("courseYear");
					item.remove("gender");
					item.remove("selfDescription");
					item.remove("grades");
					item.remove("schoolName");
					
					//
					item.put("idf", item.get("teacherIdentify"));
					item.remove("teacherIdentify");
					//
					item.put("cn", item.get("nickname"));
					item.remove("nickname");
					//
					item.put("n", item.get("name"));
					item.remove("name");
					//
					item.put("s", item.get("star"));
					item.remove("star");
					//
					item.put("ifd", item.get("isFollowed"));
					item.remove("isFollowed");
					//
					item.put("id", item.get("teacherId"));
					item.remove("teacherId");
					//
					item.put("p", item.get("point"));
					item.remove("point");
				}
			}
			logger.info("teacherIds="+teacherIds);
			logger.info("queryTeacherWithFollow2="+dataJson(resultList));
			return dataJson(resultList);
		}catch (Exception e) {
			logger.error("queryTeacherWithFollow2", e);
		}
		return errorJson("服务器异常，请重试");
	}
	
	/**
	 * 根据题目id获取音频列表
	 * 
	 * @param teacherIds
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryAudiosByQuestionId")
	public Object queryAudiosByQuestionId(Integer questionId,String audioIds,String userId,Integer pageNo,Integer pageSize) {
		Validator.validateBlank(questionId, "题目ID不能为空!");
		Validator.validateBlank(userId, "学生ID不能为空!");
		try{
			Map<String, Object> returnMap=new HashMap<String, Object>();
			//学生关注老师的列表
			List<String> teacherFollowedList = teacherService.getUserFollowedList(userId);
			//根据questionId获取音频list
			List<PayAudio> audios=payAudioDao.queryAudiosByQuestionId(questionId);
			//总记录数，减去audioIds的长度
			Integer totalNum=audios.size();
			//要排序的音频id
			List<String> audioIdList=new ArrayList<String>();
			//老师对应的teacherIds
			Set<String> teacherIdsSet=new HashSet<String>();
			for(PayAudio audio:audios){
				audioIdList.add(audio.getId());
				teacherIdsSet.add(audio.getTeacherId());
			}
			
			if(StringUtils.isNotEmpty(audioIds)){
				String[] audioIdArry=audioIds.split(",");
				for(String audioId:audioIdArry){
					if(StringUtils.isNotEmpty(audioId)){
						totalNum-=1;
					}
				}
				//排除传过来的audioIds
				audioIdList.removeAll(Arrays.asList(audioIdArry));
			}
			//如果为空，直接返回
			if(audioIdList.isEmpty()){
				return dataJson(new ArrayList<AudioTeacherVO>());
			}
			
			//获取教师教师信息
			List<String> teacherIds=new ArrayList<String>();
			teacherIds.addAll(teacherIdsSet);
			List<Map<String, Object>> resultList=teacherService.getTeacherBaseInfoList(teacherIds);
			
			//要返回的列表
			List<AudioTeacherVO> voList=new ArrayList<AudioTeacherVO>();
			
			for(String audioId:audioIdList){
				for(PayAudio audio:audios){
					if(StringUtils.equals(audioId, audio.getId())){
						AudioTeacherVO vo=new AudioTeacherVO();
						vo.setAudioId(audioId);
						vo.setAudioType(audio.getType());
						vo.setTeacherId(audio.getTeacherId());
						//处理异常数据，createtime为空的情况
						if(audio.getCreateTime()!=null){
							vo.setCreateTime(audio.getCreateTime());
						}else{
							vo.setCreateTime(new Date());
						}
						vo.setGold(audio.getGold());
						vo.setUrl(audio.getUrl());
						vo.setDuration(audio.getDuration());
						vo.setAudioName(audio.getName());
						vo.setWbType(audio.getWbType());
						//是否关注
						if(teacherFollowedList.contains(audio.getTeacherId())){
							vo.setIsFollowed(true);
						}
						//获取mediaText和星级信息
						for(Map<String, Object> item:resultList){
							if(StringUtils.equals((String)item.get("teacherId"), audio.getTeacherId())){
								String nickname=(String)item.get("nickname");
								String name=(String)item.get("name");
								Integer star=(Integer)item.get("star");
								//mediaText
								StringBuilder mediaText=new StringBuilder();
								mediaText.append(!StringUtils.isEmpty(nickname)?nickname:name);
								mediaText.append("老师");
								if(AudioType.audio.getValue()==audio.getType()){
									mediaText.append("音频");
								}else{
									mediaText.append("白板");
								}
								mediaText.append("讲解");
								vo.setMediaText(mediaText.toString());
								vo.setStar(star==null?0:star);
								vo.setNickname(!StringUtils.isEmpty(nickname)?nickname:name);
							}
						}
						voList.add(vo);
					}
				}
			}
			
			//计算排序值
			for(AudioTeacherVO vo:voList){
				Integer sortNumber=0;
				//是否关注
				if(vo.getIsFollowed()){
					sortNumber+=10000;
				}
				//音频类型，1音频，2白板
				sortNumber+=vo.getAudioType()*1000;
				//星级
				sortNumber+=vo.getStar()*100;
				//音频录制时间排序
				Integer timeOrder=0;
				for(AudioTeacherVO vo1:voList){
					if(!StringUtils.equals(vo.getAudioId(), vo1.getAudioId())){
						if(vo.getCreateTime().getTime()>vo1.getCreateTime().getTime()){
							timeOrder++;
						}
					}
				}
				sortNumber+=timeOrder;
				vo.setSortNumber(sortNumber);
			}
			//按sortNumber排序
			Collections.sort(voList);
			Integer startIndex=(pageNo-1)*pageSize;
			Integer endIndex=startIndex+pageSize;
			
			//返回
			returnMap.put("totalNum", totalNum);//总记录数
			if(startIndex>voList.size()){
				returnMap.put("list", new ArrayList<AudioTeacherVO>());
			}else{
				voList=voList.subList(startIndex, endIndex>voList.size()?voList.size():endIndex);
				returnMap.put("list", voList);
			}
			return dataJson(returnMap);
		}catch (Exception e) {
			logger.error("queryAudiosByQuestionId", e);
		}
		return errorJson("服务器异常，请重试");
	}

	/**
	 * 根据白板ID获取白板对应的下载地址
	 * 
	 * @param teacherIds
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryWbDownloadUrl")
	public Object queryWbDownloadUrl(String wbId) {
		Validator.validateBlank(wbId, "白板ID不能为空!");
		List<FeudDetailWb> wbs = feudDetailWbService.queryWbDownloadUrls(wbId);
		return dataStudentJson(wbs);
	}

	/**
	 * 获取省份list
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getProvinceList")
	public Object getProvinceList(HttpServletRequest request) {
		List<City> list = t_DictService.getProvinceList();
		if (list != null) {
			return dataStudentJson(list);
		} else {
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	/**
	 * 获取学科list
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSubjectList")
	public Object getSubjectList(HttpServletRequest request) {
		List<Subject> list = t_DictService.getSubjectList();
		if (list != null) {
			return dataStudentJson(list);
		} else {
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	/**
	 * 获取城市list
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getCityList")
	public Object getCityList(HttpServletRequest request) {
		List<City> list = t_DictService.getCityList();
		if (list != null) {
			return dataStudentJson(list);
		} else {
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	/**
	 * 根据省份id获取学校列表
	 * 
	 * @param provId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSchoolListByProvId")
	public Object getSchoolListByProvId(HttpServletRequest request, int provId) {
		Validator.validateBlank(provId, "省份Id不能为空!");
		List<School> list = t_DictService.getSchoolListByProvId(provId);
		if (list != null) {
			return dataStudentJson(list);
		} else {
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	/**
	 * 获取年级列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getGradeList")
	public Object getGradeList(HttpServletRequest request) {
		List<Grade> list = t_DictService.getGradeList();
		if (list != null) {
			return dataStudentJson(list);
		} else {
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	/**
	 * 根据年级id获取科目List
	 * 
	 * @param gradeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSubjectListByGrade")
	public Object getSubjectListByGrade(HttpServletRequest request, int gradeId) {
		Validator.validateBlank(gradeId, "年级Id不能为空!");
		List<GradeSubject> list = t_DictService.getSubjectListByGrade(gradeId);
		if (list != null) {
			return dataStudentJson(list);
		} else {
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	/**
	 * 获取年纪、学科、关系
	 * 
	 * @param gradeId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "getGradeSubjectList")
	public Object getGradeSubjectList(HttpServletRequest request) {
		Map map = t_DictService.getGradeSubjectList();
		if (map != null) {
			return dataStudentJson(map);
		} else {
			return errorStudentJson("服务器异常，请重试.");
		}
	}

	@ResponseBody
	@RequestMapping(value = "listAudioSet")
	public Object listAudioSet(String teacherId, String studentId,Integer pageNo) {
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		pageNo = pageNo - 1;
		Validator.validateBlank(teacherId, "教师Id不能为空!");
//		Validator.validateBlank(studentId, "学生Id不能为空!");
		
		List<AudioSet> list=audioSetService.listAudioSet(teacherId, pageNo);
		
//		if(!StringUtils.isEmpty(studentId)){
//			commonService.wrapperAudioSetStudentCommentList(list,studentId);
//		}
		return dataStudentJson(list);
	}

	@ResponseBody
	@RequestMapping(value = "getAudioSetById")
	public Object getAudioSetById(String id, String studentId) {
		Validator.validateBlank(id, "习题集Id不能为空!");

		AudioSet set = audioSetService.getAudioSetById(id);

		if (!StringUtils.isEmpty(studentId) && set != null) {
			commonService.wrapperAudioSetBuyStatus(Arrays.asList(set), studentId, false);
		}
		return dataStudentJson(set);
	}

	@ResponseBody
	@RequestMapping(value = "listAudioBySetId")
	public Object listAudioBySetId(String id) {
		Validator.validateBlank(id, "习题集Id不能为空!");
		return dataStudentJson(audioSetService.listAudioBySetId(id));
	}

	@ResponseBody
	@RequestMapping(value = "listAudioSetByIds")
	public Object listAudioSetByIds(String ids, String studentId) {
		Validator.validateBlank(ids, "习题集Id不能为空!");
		// Validator.validateBlank(studentId, "studentId不能为空!");
		String[] aryIds = ids.split(",");

		List<AudioSet> list = audioSetService.listAudioSetByIds(aryIds);
		// commonService.wrapperAudioSetBuyStatus(list, studentId);

		return dataStudentJson(list);
	}

	@ResponseBody
	@RequestMapping(value = "queryAudioSetByAudioIds")
	public Object queryAudioSetByAudioIds(String audioIds) {
		Validator.validateBlank(audioIds, "audioIds不能为空!");
		return dataStudentJson(audioSetService.queryAudioSetByAudioIds(audioIds));
	}

	@ResponseBody
	@RequestMapping(value = "queryAudioSetBySortType")
	public Object queryAudioSetBySortType(String studentId, Integer type, Integer count, Integer pageno, String subjectIds, String gradeIds) {
		if (type == null
				|| (type != AudioSet.SORT_TYPE_PRICE_ASC && type != AudioSet.SORT_TYPE_PRICE_DESC && type != AudioSet.SORT_TYPE_STAR_DESC && type != AudioSet.SORT_TYPE_XL_DESC)) {
			return errorStudentJson("排序类型不对");
		}
		if (pageno == null || pageno < 1) {
			pageno = 1;
		}
		if (count == null || count > 50) {
			count = 20;
		}

		List<AudioSetRank> list = audioSetService.queryAudioSetBySortType(studentId, subjectIds, gradeIds, type, count, pageno);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", list);
		return dataStudentJson(result);
	}

	/**
	 * 
	 * @param audioId
	 *            音频id
	 * @param userId
	 *            用户Id
	 * @param evaluation
	 *            评价值（1好评，2中评，3差评）
	 * @param content
	 *            评价内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "evaluateAudio")
	public Object evaluateAudio(String audioId, String userId, Integer evaluation, String content) {
		AudioStudentEvaluation record = new AudioStudentEvaluation();
		record.setAudioId(audioId);
		record.setContent(content);
		record.setUserId(userId);
		record.setEvaluation(evaluation);
		int code = audioStudentEvaluationService.insert(record);

		if (code == 1) {
			return dataStudentJson(true);
		} else if (code == -1) {
			return errorStudentJson("audioId[" + audioId + "]不存在!");
		} else if (code == 2) {
			return errorStudentJson("学生已经对音频进行了评价！");
		} else {
			return errorStudentJson("服务器异常，请重试.");
		}

	}
	
	/**
	 * 
	 * @param audioId
	 *            音频id
	 * @param userId
	 *            用户Id
	 * @param evaluation
	 *            评价值（1好评，2中评，3差评）
	 * @param content
	 *            评价内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getUserById")
	public Object getUserById(String userId) {
		User user=t_UserService.getUserById(userId);
		if (user != null) {
			return dataStudentJson(user);
		}
	    return errorStudentJson("服务器异常，请重试.");
	}

}
