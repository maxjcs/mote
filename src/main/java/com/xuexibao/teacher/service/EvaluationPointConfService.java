/**
 * 
 */
package com.xuexibao.teacher.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.dao.EvaluationPointConfDao;
import com.xuexibao.teacher.enums.FeudEvaluateStatus;
import com.xuexibao.teacher.model.EvaluationPointConf;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * @author maxjcs
 *
 */
@Service
public class EvaluationPointConfService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private RedisTemplate redisTemplate;
	
	@Resource
	private EvaluationPointConfDao evaluationPointConfDao;
	
	/**
	 * 根据教师的审核和评价值，返回积分。
	 * @param teacherIdentify
	 * @param evalution
	 * @return
	 */
	public Integer getAudioEvalPoint(int teacherIdentify,Integer evalution){
		List<EvaluationPointConf> confList=getAllConf();
		for(EvaluationPointConf conf:confList){
			if(conf.getType().intValue()==teacherIdentify){
				if(evalution==FeudEvaluateStatus.good.getValue()){
					return conf.getGood();
				}else if(evalution==FeudEvaluateStatus.medium.getValue()){
					return conf.getMiddle();
				}else if(evalution==FeudEvaluateStatus.bad.getValue()){
					return conf.getBad();
				}
				break;
			}
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<EvaluationPointConf> getAllConf(){
		List<EvaluationPointConf> confList=(List<EvaluationPointConf>)redisTemplate.opsForValue().get(RedisContstant.EVALUATION_POINT_CONF_CACHE_KEY);
		if(confList==null){
			confList=evaluationPointConfDao.selectAll();
			redisTemplate.opsForValue().set(RedisContstant.EVALUATION_POINT_CONF_CACHE_KEY, confList);
		}
		return confList;
	}

}
