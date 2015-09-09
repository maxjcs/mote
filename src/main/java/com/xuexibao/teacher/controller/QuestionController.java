package com.xuexibao.teacher.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.model.ErrorCorrection;
import com.xuexibao.teacher.service.QuestionService;
import com.xuexibao.teacher.validator.Validator;
import com.xuexibao.webapi.teacher.client.T_QuestionService;
import com.xuexibao.webapi.teacher.model.Question;


@Controller
@RequestMapping("question")
public class QuestionController extends AbstractController {

	@Resource
	private QuestionService questionService;
	
	@Resource
	private T_QuestionService t_QuestionService;

	/**
	 * 根据试题的realId获取试题详情
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getQuestionById")
	public Object getQuestionById(HttpServletRequest request,Long realId) {
		Validator.validateBlank(realId, "试题RealId不能为空!");
		Question question = t_QuestionService.getQuestionById(realId);
		if(question!=null){
			return dataJson(question,request);
		}else{
			return errorJson("服务器异常，请重试.", request);
		}
	}

	/**
	 * 题目纠错
	 */
	@ResponseBody
	@RequestMapping(value = "errorCorrection")
	public Object errorCorrection(ErrorCorrection params) throws Exception {
		Validator.validateBlank(params.getQuestionId(), "题目ID能为空");
		Validator.validateBlank(params.getReasonType(), "错误类型");
//		Validator.validateBlank(params.getReason(), "出错原因不能为空");
	
		params.setTeacherId(AppContext.getTeacherId());

		questionService.errorCorrection(params);

		return dataJson(true);
	}
	
	/**
	 * 分享音频详情
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "shareAudioDetail")
	public Object shareAudioDetail(String teacherId,long questionId) throws Exception {
		Validator.validateBlank(teacherId, "教师ID能为空");
		Validator.validateBlank(questionId, "试题ID不能为空");

		Map map=questionService.shareAudioDetail(teacherId,questionId);
		if(map!=null){
			return dataJson(map);
		}else{
			return errorJson("服务器异常，请重试.");
		}
	}
}
