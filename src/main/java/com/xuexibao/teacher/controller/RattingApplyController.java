package com.xuexibao.teacher.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.model.RattingApply;
import com.xuexibao.teacher.service.RattingApplyService;

/**
 * 
 * @author oldlu
 * 
 */
@Controller
@RequestMapping("rattingapply")
public class RattingApplyController extends AbstractController {
	@Resource
	private RattingApplyService rattingApplyService;
 
	@ResponseBody
	@RequestMapping(value = "addRattingApply")
	public Object addRattingApply() {
		
		RattingApply apply=new RattingApply();
		apply.setTeacherId(AppContext.getTeacherId());


		rattingApplyService.addRattingApply(apply);

		return dataJson(true);
	}
 

	@ResponseBody
	@RequestMapping(value = "listRattingApply")
	public Object listRattingApply() {
		RattingApply apply=new RattingApply();
		apply.setTeacherId(AppContext.getTeacherId());

		return dataJson(rattingApplyService.listRattingApply(apply));
	}
	
	@ResponseBody
	@RequestMapping(value = "getRattingInfo")
	public Object getRattingInfo() {
		return dataJson(rattingApplyService.getRattingInfo(AppContext.getTeacherId()));
	}
}
