package com.xuexibao.teacher.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.model.FeudQuest;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.vo.FeudQuestDetailVO;
import com.xuexibao.teacher.model.vo.FinishFeudAnswerDetailVO;
import com.xuexibao.teacher.model.vo.WaitFeudListVO;
import com.xuexibao.teacher.service.CommonFeudService;
import com.xuexibao.teacher.service.FeudService;
import com.xuexibao.teacher.service.TeacherService;
import com.xuexibao.teacher.validator.FeudValidator;

/**
 * 
 * @author fengbin
 * 
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping("feud")
public class FeudController extends AbstractController {
	@Resource
	private FeudService feudService;
	
	@Resource
	private TeacherService teacherService;
	@Resource
	private CommonFeudService commonFeudService;
	
	
	
	private static Logger logger = LoggerFactory.getLogger(FeudController.class);

	/**
	 * 系统生成抢答数据
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "genFeudQuest")
	public Object genFeudQuest(String genToken,int realSubject,int genNum) {

		String validateStr = FeudValidator.validateGenFeudQuest(genToken,genNum);
		if(validateStr!=null){
			return errorJson(validateStr);
		}
		
		int count = feudService.genFeudQuest(realSubject,genNum);
		return dataJson(count);
	}
	
	/**
	 * 学生端发起抢答--定向老师能解答
	 * questRealId 题目真实ID
	 */
	@ResponseBody
	@RequestMapping(value = "studentFeudQuestToTeacher")
	public Object studentFeudQuestToTeacher(String studentId, String questRealId,String imageId,String teacherId) {
		String validateStr = FeudValidator.validateStudentFeudQuestToTeacher(studentId, questRealId,imageId,teacherId);
		if(validateStr!=null){
			return errorStudentJson(validateStr);
		}
		FeudQuest fq = new FeudQuest();
		fq.setStudentId(studentId);
		fq.setQuestionRealId(new Long(questRealId));
		fq.setImageId(imageId);
		fq.setSourceTeacher(teacherId);
		boolean result = feudService.studentFeudQuest(fq);
		if (result) {
			return dataStudentJson(result);
		} else {
			logger.error("不能多次请求音频白板解答 questRealId:"+questRealId);
			return errorStudentJson("不能多次请求音频白板解答 questRealId:"+questRealId);
		}

	}
	
	

	/**
	 * 学生端发起音频抢答--所有老师能解答
	 * questRealId 题目真实ID
	 */
	@ResponseBody
	@RequestMapping(value = "studentFeudQuest")
	public Object studentFeudQuest(String studentId, String questRealId,String imageId) {
		String validateStr = FeudValidator.validateStudentFeudQuest(studentId, questRealId,imageId);
		if(validateStr!=null){
			return errorJson(validateStr);
		}
		FeudQuest fq = new FeudQuest();
		fq.setStudentId(studentId);
		fq.setQuestionRealId(new Long(questRealId));
		fq.setImageId(imageId);
		fq.setSourceTeacher(feudService.FEUD_SOURCETEACHER_COMMON);
		boolean result = feudService.studentFeudQuest(fq);
		if (result) {
			return dataJson(result);
		} else {
			logger.error("不能多次请求音频白板解答 questRealId:"+questRealId);
			return errorJson("不能多次请求音频白板解答 questRealId:"+questRealId);
		}

	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "getDirectFeudQuestList")
	public Object getDirectFeudQuestList(Integer pageNo,String version) {
		String teacherId = AppContext.getTeacherId();
		String validateStr = FeudValidator.validateGetFeudQuestList(pageNo);
		if (validateStr != null) {
			return errorJson(validateStr);
		}

		Teacher teacher = teacherService.getTeacher(teacherId);

		List<Integer> subids = teacher.getSubjectIds();
		if (subids.size() <= 0) {
			logger.error("teacher subject is null");
			return errorJson("请设置教师的学科:");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("subjectIds", subids);
		paramMap.put("pageSize", PageConstant.PAGE_SIZE_20);
		paramMap.put("offset", (pageNo) * PageConstant.PAGE_SIZE_20);
		paramMap.put("teacherId", teacherId);
		if (version != null && version.equals("v1.3")) {
			List<WaitFeudListVO> resultList = feudService
					.getDirectFeudQuestList(paramMap);
			//已请求讲解的数量
			int count = feudService.completeDirectFeudListCount(paramMap);
			Map resultData = new HashMap<String, Object>();
			resultData.put("count", count);
			resultData.put("resultList", resultList);
			return dataJson(resultData);
		} else {
			List<WaitFeudListVO> fqs = feudService
					.getDirectFeudQuestList(paramMap);
			return dataJson(fqs);
		}
	}
	

/**
 * 获取抢答问题列表
 * @return
 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "getFeudQuestList")
	public Object getFeudQuestList(Integer pageNo,Integer orderType,String version) {
		String teacherId = AppContext.getTeacherId();
	    String validateStr = FeudValidator.validateGetNewFeudQuestList(pageNo,orderType);
			if(validateStr!=null){
				return errorJson(validateStr);
			}
		Teacher teacher = teacherService.getTeacher(teacherId);
		List<Integer> subids = teacher.getSubjectIds();
        if(subids.size()<=0){
        	logger.error("teacher subject is null");
        	return errorJson("请设置教师的学科:");
        }
       if(orderType == null){
    	   orderType = 0;
       }
        
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("subjectIds", subids);
    	paramMap.put("pageSize", PageConstant.PAGE_SIZE_20);
    	paramMap.put("offset", (pageNo)*PageConstant.PAGE_SIZE_20);
    	paramMap.put("teacherId", teacherId);
    	paramMap.put("orderType", orderType);
    	
    	if(version!=null && version.equals("v1.3")){
    		List<WaitFeudListVO> resultList = feudService.getFeudQuestList(paramMap);
    		//已抢答数
    		int count = feudService.completeFeudListCount(paramMap);
    		Map resultData = new HashMap<String,Object>();
    		//已抢答数
    		resultData.put("count", count);
    		resultData.put("resultList", resultList);
    	    return dataJson(resultData);
    	}else{
    		List<WaitFeudListVO> fqs = feudService.getFeudQuestList(paramMap);	
    		return dataJson(fqs);
    	}

	}
	/**
	 * 获取抢答问题详情
	 * @param questRealId  题目真实ID
	 * @param feudQuestId 抢答ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getFeudQuestDetail")
	public Object getFeudQuestDetail(String questRealId,String feudQuestId ) {
		String validateStr = FeudValidator.validateGetFeudQuestDetail(feudQuestId);
		if(validateStr!=null){
			return errorJson(validateStr);
		}
		String teacherId = AppContext.getTeacherId();
		FeudQuestDetailVO fdv = feudService.getFeudQuestDetail(feudQuestId,teacherId);
		if (fdv == null) {
			logger.error("无该抢答问题 feudQuestId:"+feudQuestId);
			return errorJson("无该抢答问题 feudQuestId:"+feudQuestId);
		} else {
			return dataJson(fdv);
		}
	}
	
	/**
	 * 抢答问题
	 * @param questRealId  题目真实ID
	 * @param feudQuestId 抢答ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "enterFeud")
	public Object enterFeud(String feudQuestId) {
		String validateStr = FeudValidator.validateEnterFeud(feudQuestId);
		if(validateStr!=null){
			return errorJson(validateStr);
		}
		String teacherId = AppContext.getTeacherId();
		HashMap resultData = feudService.enterFeud(new Long(feudQuestId),teacherId);
		return dataJson(resultData);
	}
	/**
	 * 抢答问题
	 * @param questRealId  题目真实ID
	 * @param feudQuestId 抢答ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "enterDirectFeud")
	public Object enterDirectFeud(String feudQuestId) {
		String validateStr = FeudValidator.validateEnterFeud(feudQuestId);
		if(validateStr!=null){
			return errorJson(validateStr);
		}
		String teacherId = AppContext.getTeacherId();
		HashMap resultData = feudService.enterDirectFeud(new Long(feudQuestId),teacherId);
		return dataJson(resultData);
	}
	/**
	 * 获取老师抢答问题还剩时间 以秒为单位--
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "getFeudOverplusTime")
	public Object getFeudOverplusTime() {
		String teacherId = AppContext.getTeacherId();
		HashMap resultData  = feudService.getFeudOverplusTime(teacherId);
		return dataJson(resultData);
	}
	/**
	 * 提交抢答题目
	 * todo 类型为空
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "submitFeud")
	public Object submitFeud(String feudQuestId,String submitType,String feudType,MultipartFile file,String feudAnswerDetailId,String duration,String wbVersion) {
		String teacherId = AppContext.getTeacherId();
		String validateStr = FeudValidator.validateSubmitFeud(feudQuestId, submitType, feudType, file,feudAnswerDetailId,duration);
		if(validateStr!=null){
			return errorJson(validateStr);
		}
		Map resultData = feudService.submitFeud(new Long(feudQuestId),new Integer(submitType),new Integer(feudType),file,teacherId,new Long(feudAnswerDetailId),new Long(duration),wbVersion);
		return dataJson(resultData);
	}
	
	/**
	 * 定向请求 提交
	 * @param feudQuestId
	 * @param submitType
	 * @param feudType
	 * @param file
	 * @param feudAnswerDetailId
	 * @param duration
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "submitDirectFeud")
	public Object submitDirectFeud(String feudQuestId,String submitType,String feudType,MultipartFile file,String feudAnswerDetailId,String duration,String wbVersion) {
		String teacherId = AppContext.getTeacherId();
		String validateStr = FeudValidator.validateSubmitFeud(feudQuestId, submitType, feudType, file,feudAnswerDetailId,duration);
		if(validateStr!=null){
			return errorJson(validateStr);
		}
		Map resultData = feudService.submitDirectFeud(new Long(feudQuestId),new Integer(submitType),new Integer(feudType),file,teacherId,new Long(feudAnswerDetailId),new Long(duration),wbVersion);
		return dataJson(resultData);
	}
	
	/**
	 * 已抢答题目列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "completeFeudList")
	public Object completeFeudList(Integer pageNo,String version) {
		String validateStr = FeudValidator.validateCompleteFeudList(pageNo);
		if(validateStr!=null){
			return errorJson(validateStr);
		}
		String teacherId = AppContext.getTeacherId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("pageSize", PageConstant.PAGE_SIZE_20);
    	paramMap.put("offset", (pageNo)*PageConstant.PAGE_SIZE_20);
    	paramMap.put("teacherId", teacherId);
    	if(version!=null && version.equals("v1.3")){
    		List<FinishFeudAnswerDetailVO> resultList = feudService.completeFeudList(paramMap);
    		int count = feudService.completeFeudListCount(paramMap);
    		Map resultData = new HashMap<String,Object>();
    		resultData.put("count", count);
    		resultData.put("resultList", resultList);
    		return dataJson(resultData);
    	}else{
    		List<FinishFeudAnswerDetailVO> resultData = feudService.completeFeudList(paramMap);
    		return dataJson(resultData);
    	}
    
	}
	
	/**
	 * 已抢答题目列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "completeDirectFeudList")
	public Object completeDirectFeudList(Integer pageNo,String version) {
		String validateStr = FeudValidator.validateCompleteFeudList(pageNo);
		if(validateStr!=null){
			return errorJson(validateStr);
		}
		String teacherId = AppContext.getTeacherId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("pageSize", PageConstant.PAGE_SIZE_20);
    	paramMap.put("offset", (pageNo)*PageConstant.PAGE_SIZE_20);
    	paramMap.put("teacherId", teacherId);
    	if(version!=null && version.equals("v1.3")){
    		List<FinishFeudAnswerDetailVO> resultList = feudService.completeDirectFeudList(paramMap);
    		int count = feudService.completeDirectFeudListCount(paramMap);
    		Map resultData = new HashMap<String,Object>();
    		resultData.put("count", count);
    		resultData.put("resultList", resultList);
    		return dataJson(resultData);
    	}else{
    		List<FinishFeudAnswerDetailVO> resultData = feudService.completeDirectFeudList(paramMap);
    		return dataJson(resultData);
    	}

	}
	
	
	
	/**
	 * 已抢答题目详情
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "completeFeudDetail")
	public Object completeFeudDetail(String feudAnswerDetailId) {
		String validateStr = FeudValidator.validateCompleteFeudDetail(feudAnswerDetailId);
		if(validateStr!=null){
			return errorJson(validateStr);
		}
		String loginTeacherId = AppContext.getTeacherId();
		FinishFeudAnswerDetailVO resultData = feudService.completeFeudDetail(feudAnswerDetailId,loginTeacherId);
		return dataJson(resultData);
	}
	
	/**
	 * 测试DB
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "testDB")
	public Object testDB() {
		logger.info("testDB:");
		//feudService.testDb();
		Set teacherIds = new HashSet();
		teacherIds.add("24f9b2d4-d85a-4d01-8dc7-82d3bda6a772");
		teacherIds.add("a741b94f-a830-4eac-8b58-430288f22d62");
		teacherIds.add("d8a4fc0e-2ee7-44de-be6c-4cd234ab6b73");
		String studentId = "547ed64fe123908d6d78423f";
		String questionId = "32000165";
		Map<String,Boolean>resultMap = commonFeudService.hasAskTeacherWithQuestion(studentId, questionId, teacherIds);
		return dataJson(resultMap);
	}

	
	
	
	
	
	

}

