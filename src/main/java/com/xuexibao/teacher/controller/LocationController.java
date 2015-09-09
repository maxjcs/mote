/**
 * 
 */
package com.xuexibao.teacher.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.model.TeacherLocation;
import com.xuexibao.teacher.service.LocationService;
import com.xuexibao.teacher.service.TeacherService;
import com.xuexibao.teacher.util.RedisContstant;
import com.xuexibao.teacher.validator.Validator;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("teacherLocation")
public class LocationController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(LocationController.class);
	
	@Resource
	LocationService locationService;
	
	@Resource
	TeacherService teacherService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private RedisTemplate redisTemplate;
	
	//距离的偏移值，用于翻页
	private static double OFFSET_DISTANCE=0.00000001;
	//每次mongodb中取数据条数
	private static Integer PAGE_COUNT=500;
	
    /**
     * 保存教师位置
     */
	@ResponseBody
    @RequestMapping(value = "saveLocation",method = {RequestMethod.POST, RequestMethod.GET})
    public Object saveLocation(HttpServletRequest request,Double longitude,Double latitude) throws Exception{
		Validator.validateBlank(longitude, "经度不能为空!");
		Validator.validateBlank(latitude, "纬度不能为空!");
        try{
        	String teacherId = AppContext.getTeacherId();
        	locationService.save(teacherId, longitude, latitude);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("保存教师位置失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
	 * 查询附近老师
	 * @param userId
	 * @param longitude
	 * @param latitude
	 * @param minDistance
	 * @param maxDistance
	 * @param count
	 * @param onlyShowUnfollowed 是否只显示未关注的老师
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "unused", "rawtypes"})
	@ResponseBody
    @RequestMapping(value = "getNearTeacherList")
    public Object getNearTeacherList(String userId,Double longitude,Double latitude,Double minDistance,Double maxDistance,int count,boolean onlyShowUnfollowed) throws Exception{
		Validator.validateBlank(longitude, "经度不能为空!");
		Validator.validateBlank(latitude, "纬度不能为空!");
    	try{
			Point point=new Point(longitude,latitude);
			List<TeacherLocation> teacherLocations=new ArrayList<TeacherLocation>();
			List<String> teacherIds=new ArrayList<String>();
			if(onlyShowUnfollowed){
				//仅仅显示未关注的老师
				//学生已关注的教师列表
				List<String> followedList=(List<String>)redisTemplate.opsForValue().get(RedisContstant.USER_FOLLOWED_CACHE_KEY+userId);
		    	//循环，取多页数据，拼成一页未关注的记录。
				while(teacherIds.size()<count){
					List<TeacherLocation> newLocations=locationService.findCircleNear(point, minDistance+OFFSET_DISTANCE, maxDistance, PAGE_COUNT);
		    		List<String> newTeacherIds=getTeacherIdsByLocations(newLocations);
		    		if(newTeacherIds.size()==0){
		    			break;
		    		}
		    		newTeacherIds.removeAll(followedList);
		    		teacherIds.addAll(newTeacherIds);
		    		//获取本页的最大距离，用于翻页。
		    		minDistance=newLocations.get(newLocations.size()-1).getDistance();
		    		teacherLocations.addAll(newLocations);
		    	}
				
			}else{
				teacherLocations=locationService.findCircleNear(point, minDistance+OFFSET_DISTANCE, maxDistance, count);
				teacherIds=getTeacherIdsByLocations(teacherLocations);
			}
			
	    	List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
	    	List<String> teacherIdsList=new ArrayList<String>();
	    	teacherIdsList.addAll(teacherIds);
	    	if(teacherIdsList.size()>0){
	    		if(teacherIdsList.size()>count){
	    			teacherIdsList=teacherIdsList.subList(0, count);
	    		}
	    		resultList = teacherService.queryTeacherWithFollow(teacherIdsList,userId);
	    		
		    	for(Map<String, Object> item:resultList){
		    		String teacherIdString=(String)item.get("teacherId");
		    		//加上距离
		    		for(TeacherLocation location:teacherLocations){
		    			if(StringUtils.equals(teacherIdString, location.get_id())){
		    				item.put("distance", location.getDistance());
		    				break;
		    			}
		    		}
		    	}
		    	//展示更多附加的信息,显示留言总数，习题集总数，习题集好评率。
		    	teacherService.showAppenderInfo(resultList,true,true,true);
	    	}
	    	
	    	Integer totalNum=locationService.findCircleNearCount(point,maxDistance);
	    	Map resultMap=new HashMap();
	    	resultMap.put("data", resultList);
	    	resultMap.put("totalNum", totalNum);
	    	
	    	if (resultList != null) {
				return dataStudentJson(resultMap);
			} else {
				return errorStudentJson("服务器异常，请重试.");
			}
    	}catch(Exception ex){
    		logger.error("getNearTeacherList is error!",ex);
    		return errorStudentJson("服务器异常，请重试.");
    	}
    }
	
    /**
     * 查询附近老师个数
     */
	@ResponseBody
    @RequestMapping(value = "getNearTeacherCount")
    public Object getNearTeacherCount(String userId,Double longitude,Double latitude,Double maxDistance) throws Exception{
		Validator.validateBlank(longitude, "经度不能为空!");
		Validator.validateBlank(latitude, "纬度不能为空!");
    	try{
			Point point=new Point(longitude,latitude);
	    	Integer count=locationService.findCircleNearCount(point,maxDistance);  
	    	
	    	if (count != null) {
				return dataStudentJson(count);
			} else {
				return errorStudentJson("服务器异常，请重试.");
			}
    	}catch(Exception ex){
    		logger.error("getNearTeacherList is error!",ex);
    		return errorStudentJson("服务器异常，请重试.");
    	}
    }
	
	/**
	 * 根据location获取teacherIds
	 * @param teacherLocations
	 * @return
	 */
	private List<String> getTeacherIdsByLocations(List<TeacherLocation> teacherLocations){
		List<String> teacherIds=new ArrayList<String>();
    	for(TeacherLocation location:teacherLocations){
    		if(!teacherIds.contains(location.get_id())){
    			teacherIds.add(location.get_id());
    		}
    	}
    	return teacherIds;
	}

}
