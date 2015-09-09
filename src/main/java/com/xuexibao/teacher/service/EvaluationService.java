package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.core.CollectionRedisTemplate;
import com.xuexibao.teacher.dao.EvaluationDao;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Evaluation;
import com.xuexibao.teacher.util.RedisContstant;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class EvaluationService {
	@Resource
	private EvaluationDao evaluationDao;

	@Resource
	private CollectionRedisTemplate collectionRedisTemplate;

	public List<Evaluation> allEvaluation() {
		String key = RedisContstant.TEACHER_EVALUATION_CACHE_KEY_ALL;
		List<Evaluation> list = collectionRedisTemplate.get(key, Evaluation.class);

		if (CollectionUtils.isEmpty(list)) {
			list = evaluationDao.allEvaluation();
			if (!CollectionUtils.isEmpty(list)) {
				for (Evaluation item : list) {
					String desc = String.format(item.getDescription(), item.getPoint());
					item.setDescription(desc);
				}
				collectionRedisTemplate.set(key, list);
			}
		}

		return list;
	}

	public void updateEvaluation(Evaluation evaluation) {
		evaluationDao.updateEvaluation(evaluation);
	}

	public Evaluation getEvaluationByLevel(int evaluation, Integer identify) {
		List<Evaluation> list = allEvaluation();
		for (Evaluation item : list) {
			if (item.getLevel() == evaluation && item.getTeacherIdentify() == identify) {
				return item;
			}
		}
		throw new BusinessException("评价定义数据缺失，缺少[" + evaluation + "]级[" + identify + "]的定义数据");
	}

	/**
	 * @param teacherIdentify
	 * @return
	 */
	public List<Evaluation> listEvaluationByIdentify(int teacherIdentify) {
		List<Evaluation> result = new ArrayList<Evaluation>();
		List<Evaluation> list = allEvaluation();
		for (Evaluation item : list) {
			if (item.getTeacherIdentify() == teacherIdentify) {
				result.add(item);
			}
		}

		return result;
	}
}
