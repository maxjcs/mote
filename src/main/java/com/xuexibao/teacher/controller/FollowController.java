package com.xuexibao.teacher.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.service.FollowService;
import com.xuexibao.teacher.service.TeacherFollowedService;
import com.xuexibao.teacher.service.TeacherService;
import com.xuexibao.teacher.validator.Validator;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("follow")
public class FollowController extends AbstractController{
	
    private static Logger logger = LoggerFactory.getLogger(FollowController.class);
    @Resource
    private FollowService followService;
    
    @Resource
    TeacherFollowedService teacherFollowedService;
    
	@Resource
	private TeacherService teacherService;
    
    /**
     * 学生关注老师
     */
	@ResponseBody
    @RequestMapping(value = "add")
    public Object add(HttpServletRequest request,String teacherId,String userId) throws Exception{
        try{
        	if(!teacherFollowedService.isFollowed(teacherId, userId)){
        		followService.addFollow(teacherId, userId);
        	}
            return dataStudentJson(true);
        }catch(Exception e){
            logger.error("学生关注老师失败.", e);
            return errorStudentJson("服务器异常，请重试.");
        }
    }
	
    /**
     * 批量学生关注老师
     */
	@ResponseBody
    @RequestMapping(value = "addAll")
    public Object addAll(HttpServletRequest request,String teacherIds,String userId) throws Exception{
        try{
        	String[] teacherIdArray=teacherIds.split(",");
        	for(String teacherId:teacherIdArray){
	        	if(StringUtils.isNotBlank(teacherId)&&!teacherFollowedService.isFollowed(teacherId, userId)){
	        		followService.addFollow(teacherId, userId);
	        	}
        	}
            return dataStudentJson(true);
        }catch(Exception e){
            logger.error("批量学生关注老师失败.", e);
            return errorStudentJson("服务器异常，请重试.");
        }
    }
    
    /**
     * 学生取消关注老师
     */
	@ResponseBody
    @RequestMapping(value = "cancel")
    public Object cancel(HttpServletRequest request,String teacherId,String userId) throws Exception{
        try{
        	followService.cancelFollow(teacherId, userId);
            return dataStudentJson(true);
        }catch(Exception e){
            logger.error("学生关注老师失败.", e);
            return errorStudentJson("服务器异常，请重试.");
        }
    }
	
    /**
     * 获取老师的关注数量
     */
	@ResponseBody
    @RequestMapping(value = "getTotalFollowedNum")
    public Object getTotalFollowedNum(HttpServletRequest request) throws Exception{
        try{
        	String teacherId = AppContext.getTeacherId();
        	Integer totalFollowed = followService.getTotalFollowedByTid(teacherId);
            return dataJson(totalFollowed, request);
        }catch(Exception e){
            logger.error("获取老师的关注数量失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
    /**
     * 获取学生的关注数量
     */
	@ResponseBody
    @RequestMapping(value = "getTotalFollowedByUserId")
    public Object getTotalFollowedByUserId(String userId) throws Exception{
		Validator.validateBlank(userId, "学生ID不能为空!");
        try{
        	Integer totalFollowed = teacherFollowedService.getTotalFollowedByUserId(userId);
            return dataStudentJson(totalFollowed);
        }catch(Exception e){
            logger.error("获取学生的关注数量失败.", e);
            return errorJson("服务器异常，请重试.");
        }
    }
	
    /**
     * 获取学生关注的老师
     */
	@ResponseBody
    @RequestMapping(value = "getFollowListByUserId")
    public Object getFollowListByUserId(HttpServletRequest request,String studentId,String requestionId,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	List<Map<String, Object>>  resultList = followService.getFollowListByUserId(studentId,requestionId,pageNo,pageSize);
        	//展示更多附加的信息,显示留言总数，习题集总数，习题集好评率
    		teacherService.showAppenderInfo(resultList, true, true, true);
        	return dataStudentJson(resultList);
        }catch(Exception e){
            logger.error("获取学生关注的老师失败.", e);
            return errorStudentJson("服务器异常，请重试.");
        }
    }

}
