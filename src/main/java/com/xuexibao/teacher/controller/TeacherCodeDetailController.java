package com.xuexibao.teacher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xuexibao.teacher.util.PropertyUtil;

@Controller
@RequestMapping("teacherDetail")
public class TeacherCodeDetailController {
	String api_host = PropertyUtil.getProperty("api_host");
	String IOS_STUDENT_DOWN_URL = PropertyUtil
			.getProperty("IOS_STUDENT_DOWN_URL");
	String ANDROID_STUDENT_DOWN_URL = PropertyUtil
			.getProperty("ANDROID_STUDENT_DOWN_URL");

	@RequestMapping("/teacherCode")
	public ModelAndView teacherCode() {

		ModelAndView mv = new ModelAndView();
		mv.addObject("IOS_STUDENT_DOWN_URL", IOS_STUDENT_DOWN_URL);
		mv.addObject("ANDROID_STUDENT_DOWN_URL", ANDROID_STUDENT_DOWN_URL);
		mv.addObject("api_host", api_host);
		mv.setViewName("teacherDetail/teacherCode");

		return mv;
	}

}
