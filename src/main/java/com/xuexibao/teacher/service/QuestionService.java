package com.xuexibao.teacher.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuexibao.teacher.dao.AudioDao;
import com.xuexibao.teacher.dao.ErrorCorrectionDao;
import com.xuexibao.teacher.dao.ErrorQuestDao;
import com.xuexibao.teacher.dao.QuestionV2Dao;
import com.xuexibao.teacher.enums.AudioStatus;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.ErrorCorrection;
import com.xuexibao.teacher.model.Question;
import com.xuexibao.teacher.util.PropertyUtil;
import com.xuexibao.webapi.teacher.client.T_DictService;
import com.xuexibao.webapi.teacher.model.Subject;

@Service
public class QuestionService {
	@Resource
	private ErrorCorrectionDao errorCorrectionDao;
	@Resource
	private ErrorQuestDao errorQuestDao;
	@Resource
	private QuestionV2Dao questionV2Dao;
	@Resource
	private AudioDao audioDao;
	@Resource
	private T_DictService t_DictService;// 中间件的 字典服务

	@Resource
	private TeacherService teacherService;

	private String questionUrl = PropertyUtil.getProperty("question_url");
	private static Logger logger = LoggerFactory.getLogger(QuestionService.class);

	public Map<String, Object> shareAudioDetail(String teacherId, long questionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Question question = getQuestion(questionId);

		if (question == null) {
			return map;
		}
		map.put("content", question.getContent());
		map.put("solution", question.getSolution());
		map.put("knowledge", question.getKnowledge());
		map.put("answer", question.getAnswer());
		// 科目
		int realSubject = question.getRealSubject();
		Subject dicSubject = t_DictService.getSubjectById(Long.valueOf(realSubject));
		map.put("subjectName", dicSubject.getName());
		// 年级
		map.put("gradeName", question.getLearnPhase());
		// 音频
		Audio audio = audioDao.queryAudioByTeacherIdQuestId(teacherId, questionId);
		if (audio != null) {
			map.put("audioUrl", audio.getUrl());
			map.put("audioType", audio.getType());
		}
		// 老师名
		Map<String, Object> teacherInfo = teacherService.getTeacherBaseInfo(teacherId);
		if (teacherInfo != null) {
			map.put("nickname", teacherInfo.get("nickname"));
			map.put("name", teacherInfo.get("name"));
		}
		return map;
	}

	/**
	 * 获取题目
	 */
	public Question getQuestion(long realId) {
		return questionV2Dao.queryByRealId(realId);
	}

	/**
	 * 保存纠错
	 */
	@Transactional
	public void errorCorrection(ErrorCorrection parms) {
		Question question = getQuestion(parms.getQuestionId());
		if (question == null) {
			throw new BusinessException("题目不存在.");
		}

		if (errorCorrectionDao.hasErrorCorrection(parms.getTeacherId(), parms.getQuestionId())) {
			throw new BusinessException("该题已经纠错");
		}
		questionV2Dao.updateErrorNumber(parms.getQuestionId());
		errorCorrectionDao.addErrorCorrection(parms);
		errorQuestDao.addErrorQuest(question);
	}

	public void updateQuestionStatus(long questionId, AudioStatus status) {
		questionV2Dao.updateQuestionStatus(questionId, status.getValue());
	}

	/**
	 * 远程获取question
	 * 
	 * @param questionId
	 * @return
	 * @throws Exception
	 */
	public Question loadQuestion(long questionId) throws Exception {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		GetMethod method = new GetMethod(questionUrl + questionId);
		client.executeMethod(method);
		client.getHttpConnectionManager().closeIdleConnections(1);
		String responseStr = method.getResponseBodyAsString();
		if (StringUtils.isNotBlank(responseStr)) {
			logger.info(questionUrl + questionId + " =》 " + responseStr);
			Map<String, Object> responseMap = new ObjectMapper().readValue(responseStr,
					new TypeReference<Map<String, Object>>() {
					});
			if (responseMap == null)
				return null;

			@SuppressWarnings("unchecked")
			Map<String, Object> result = (Map<String, Object>) responseMap.get("result");
			if (result == null)
				return null;

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("answers");
			if (CollectionUtils.isNotEmpty(list)) {
				Map<String, Object> map = list.get(0);
				if (map != null) {
					Question question = new Question();
					if (map.get("question_id") != null) {
						question.setRealId(Long.parseLong(map.get("question_id").toString()));
					} else {
						return null;
					}
					if (map.get("question_body") != null) {
						question.setLatex(map.get("question_body").toString());
					}
					if (map.get("question_body_html") != null) {
						question.setContent(map.get("question_body_html").toString());
					}
					if (map.get("answer_analysis") != null) {
						question.setSolution(map.get("answer_analysis").toString());
					}
					// 增加answer
					if (map.get("question_answer") != null) {
						question.setAnswer(map.get("question_answer").toString());
					}
					if (map.get("subject") != null) {
						int realSubject = Integer.parseInt(map.get("subject").toString());
						question.setRealSubject(realSubject);
						try {
							Subject dicSubject = t_DictService.getSubjectById(Long.valueOf(realSubject));
							question.setSubject(dicSubject.getName());
						} catch (Exception e) {
							logger.error("t_DictService.getSubjectById error:" + e.getMessage());
							logger.error("t_DictService.getSubjectById error--realSubject:" + realSubject);
						}
					} else {
						return null;
					}
					return question;
				}
			}
		}
		return null;
	}

}
