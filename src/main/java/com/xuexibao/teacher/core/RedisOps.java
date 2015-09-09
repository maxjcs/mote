package com.xuexibao.teacher.core;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * @author fengbin 2015-03-27 redis list操作
 */
@Component
public class RedisOps {
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 入队列
	 * @param key
	 * @param value
	 * @return
	 */
	public Long listPush(String key, String value) {
		return stringRedisTemplate.opsForList().rightPush(key, value);
	}

	/**
	 * 出队列
	 * @param key
	 * @return
	 */
	public String listPop(String key) {
		return stringRedisTemplate.opsForList().leftPop(key);
	}
	  /** 
     * 栈/队列长 
     *  
     * @param key 
     * @return 
     */  
    public Long getListSize(String key) {  
        return stringRedisTemplate.opsForList().size(key);  
    } 
    
    public long addSetValue(String key,String value){
    	return stringRedisTemplate.opsForSet().add(key, value);
    }
    public long removeSetValue(String key,String value){
    	return stringRedisTemplate.opsForSet().remove(key, value);
    }
    public long getSetSize(String key){
    	
    	return stringRedisTemplate.opsForSet().size(key);
    }
    public Set<String> getSetMembers(String key){
    	return stringRedisTemplate.opsForSet().members(key);
    }
    public boolean isSetMember(String key,Object o){
    	return stringRedisTemplate.opsForSet().isMember(key, o);
    }
    public boolean hasKey(String key){
    	return stringRedisTemplate.hasKey(key);
    }
    public void addKeyValue(String key,String value,long timeout,  TimeUnit unit){
    	 stringRedisTemplate.opsForValue().set(key, value,timeout,unit);
    }
    public void addKeyValue(String key,String value){
   	 stringRedisTemplate.opsForValue().set(key, value);
   }
    public Object getValue(String key){
    	
    	return stringRedisTemplate.opsForValue().get(key);
    }
    public void delete(String key){
    	 stringRedisTemplate.delete(key);
    }
    /**
     * redis自增长
     */
    public void redisIncr(String key){

    	
    }
    public Set<String> getKeys(String pattern){
    	return stringRedisTemplate.keys(pattern+"*");
    }

}
