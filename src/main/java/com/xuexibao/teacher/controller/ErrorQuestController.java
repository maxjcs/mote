package com.xuexibao.teacher.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.model.ErrorQuest;
import com.xuexibao.teacher.service.ErrorQuestService;
import com.xuexibao.teacher.util.GsonUtil;
import com.xuexibao.teacher.validator.Validator;

/**
 * 
 * @author oldlu
 * 
 */
@Controller
@RequestMapping("errorquest")
public class ErrorQuestController extends AbstractController {
	@Resource
	private ErrorQuestService errorQuestService;

	@ResponseBody
	@RequestMapping(value = "getErrorQuestList")
	public Object getErrorQuestList(Integer pageNo, Integer pageSize) {

		Map<String, Object> paramMap = getPageParamMap(pageNo, pageSize);

		List<ErrorQuest> list = errorQuestService.getErrorQuestList(paramMap);

		return dataJson(list);
	}

	@ResponseBody
	@RequestMapping(value = "updateQuestReadStatus")
	public Object updateQuestReadStatus(String questIds) {
		Validator.validateBlank(questIds, "题目ID不能为空");
		String[] str = questIds.split(",");

		errorQuestService.updateQuestReadStatus(str);

		return dataJson("数据状态已经更新");
	}
	
	@ResponseBody
	@RequestMapping(value = "audioErrorQuests")
	public Object audioErrorQuests(String quests) {
		Validator.validateBlank(quests, "题目信息不能为空");
		
		List<ErrorQuest> list=GsonUtil.json2List(quests, ErrorQuest.class);
		
		errorQuestService.audioErrorQuests(list);

		return dataJson("错题数据已审核");
	}
}
