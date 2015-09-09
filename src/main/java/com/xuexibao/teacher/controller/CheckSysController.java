package com.xuexibao.teacher.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xuexibao.teacher.service.CheckSysService;

/**
 * 检查系统各重要组件部分是否正常
 * DB,redis,thrift,paysys,studentsys
 * @author fengbin   
 * @date  2015-4-23
 *
 */
@Controller
@RequestMapping("sysStatus")
public class CheckSysController {
	 @Resource
	    private CheckSysService checkSysService;
	
	@RequestMapping("/sysStatusList")
	public ModelAndView getSysComponentsStatus() {
		ModelAndView mv = new ModelAndView();
		Map<String,String> monitorMap = checkSysService.getSysComponentsStatus();
		mv.setViewName("sysStatus/sysStatusList");
		mv.addObject("monitorMap", monitorMap);
		return mv;
	}
	

}
