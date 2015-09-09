/**
 * 
 */
package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuexibao.teacher.dao.TeacherFollowedDao;
import com.xuexibao.teacher.model.TeacherFollowed;
import com.xuexibao.teacher.model.vo.FeudTeacherIsFree;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * @author maxjcs
 *
 */
@Service
public class FollowService {
	
    @Resource
    private TeacherFollowedDao teacherFollowedDao;
    
    @Resource
    TeacherService teacherService;
    
    @Resource
    CommonFeudService commonFeudService;
    
	@Resource
	private RedisTemplate redisTemplate;
	
	@Resource
	private StringRedisTemplate stringRedisTemplate;
    
    /**
     * 学生关注老师
     * @param teacherId
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
	@Transactional
    public void addFollow(String teacherId,String userId){
    	TeacherFollowed follow = new TeacherFollowed();
    	follow.setTeacherId(teacherId);
    	follow.setUserId(userId);
    	teacherFollowedDao.insert(follow);
    	//关注数量加一
    	if(stringRedisTemplate.opsForValue().get(RedisContstant.TEACHER_FOLLOWEDNUM_CACHE_KEY+teacherId)==null){
    		Integer followNum=getTotalFollowedByTid(teacherId);
    		stringRedisTemplate.opsForValue().set(RedisContstant.TEACHER_FOLLOWEDNUM_CACHE_KEY+teacherId,String.valueOf(followNum==null?1:followNum+1));
    	}else{
    		String followNum=stringRedisTemplate.opsForValue().get(RedisContstant.TEACHER_FOLLOWEDNUM_CACHE_KEY+teacherId);
    		stringRedisTemplate.opsForValue().set(RedisContstant.TEACHER_FOLLOWEDNUM_CACHE_KEY+teacherId,String.valueOf(Integer.parseInt(followNum)+1));
    	}
    	//学生关注列表加上该老师
    	List<String> followdteacherIds=teacherService.getUserFollowedList(userId);
    	followdteacherIds.add(teacherId);
    	redisTemplate.opsForValue().set(RedisContstant.USER_FOLLOWED_CACHE_KEY+userId,followdteacherIds);
    }
    
    /**
     * 学生取消老师关注
     * @param teacherId
     * @param userId
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Transactional
	public void cancelFollow(String teacherId,String userId){
    	Map map = new HashMap();
    	map.put("teacherId", teacherId);
    	map.put("userId", userId);
        teacherFollowedDao.deleteByTeacherIdAndUserId(map);
        //关注数量减一
        if(stringRedisTemplate.opsForValue().get(RedisContstant.TEACHER_FOLLOWEDNUM_CACHE_KEY+teacherId)==null){
    		Integer followNum=getTotalFollowedByTid(teacherId);
    		stringRedisTemplate.opsForValue().set(RedisContstant.TEACHER_FOLLOWEDNUM_CACHE_KEY+teacherId,String.valueOf((followNum==null||followNum<=0) ?0:followNum-1));
    	}else{
    		String followNum=stringRedisTemplate.opsForValue().get(RedisContstant.TEACHER_FOLLOWEDNUM_CACHE_KEY+teacherId);
    		stringRedisTemplate.opsForValue().set(RedisContstant.TEACHER_FOLLOWEDNUM_CACHE_KEY+teacherId,String.valueOf((Integer.parseInt(followNum)-1)<=0?0:(Integer.parseInt(followNum)-1)));
    	}
    	//学生关注列表删除该老师
    	List<String> followdteacherIds=teacherService.getUserFollowedList(userId);
    	if(followdteacherIds==null||followdteacherIds.size()==0){
    		return;
    	}
    	followdteacherIds.remove(teacherId);
    	redisTemplate.opsForValue().set(RedisContstant.USER_FOLLOWED_CACHE_KEY+userId,followdteacherIds);
    }
    
    /**
     * 获取老师的总关注数
     * @param teacherId
     * @return
     */
    public Integer getTotalFollowedByTid(String teacherId){
    	return teacherFollowedDao.getTotalFollowedByTid(teacherId);
    }
    
    /**
     * 获取学生关注的老师
     * @param studentId
     * @param pageno
     * @param pageSize
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> getFollowListByUserId(String studentId,String requestionId,Integer pageno,Integer pageSize){
    	Map paramMap = new HashMap();
    	paramMap.put("studentId",studentId);
    	paramMap.put("startIndex", pageno==null?0:(pageno-1)*pageSize);
    	paramMap.put("pageSize", pageSize);
    	
    	
    	List<TeacherFollowed> teacherList = teacherFollowedDao.getFollowListByUserId(paramMap);
    	
    	//增加是否有空
    	List<FeudTeacherIsFree> teacherFreesList=new ArrayList<FeudTeacherIsFree>();
    	Set<String> teacherSet= new HashSet<String>();
    	if(teacherList.size()>0){
    		for(TeacherFollowed followed:teacherList){
    			teacherSet.add(followed.getTeacherId());
    		}
    		teacherFreesList=commonFeudService.getFeudTeachersIsFree(teacherSet);
    	}
    	
    	//是否有讲解请求
    	Map<String,Boolean> hasAskMap=new HashMap<String, Boolean>();
    	if(StringUtils.isNotBlank(requestionId)){
	    	hasAskMap=commonFeudService.hasAskTeacherWithQuestion(studentId,requestionId,teacherSet);
    	}
    	
    	//获取老师详细信息
    	List<String> teacherIds=new ArrayList<String>();
    	for(TeacherFollowed teacherFollowed:teacherList){
    		teacherIds.add(teacherFollowed.getTeacherId());
    	}
    	List<Map<String, Object>> resultList = teacherService.getTeacherBaseInfoList(teacherIds);
    	
    	for(Map item:resultList){
    		if(item!=null){
    			if(StringUtils.isNotBlank(requestionId)){
    				item.put("hasAsk",hasAskMap.get((String)item.get("teacherId")));
    			}
    			item.put("isFree",Boolean.TRUE);
	    		for(FeudTeacherIsFree teacherFree:teacherFreesList){
	    			if(StringUtils.equals((String)item.get("teacherId"), teacherFree.getTeacherId())){
	    				item.put("isFree", teacherFree.isFree());
	    				if(!teacherFree.isFree()){
	    					item.put("feudTotal", teacherFree.getFeudTotal());
	    				}
	    			}
	    		}
    		}
    	}
    	return resultList;
    }

}
