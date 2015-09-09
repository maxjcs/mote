package com.xuexibao.teacher.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.exception.ValidateException;
import com.xuexibao.teacher.model.TeacherReport;
import com.xuexibao.teacher.service.TeacherReportService;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("teacherreport")
public class TeacherReportController extends AbstractController {
	@Resource
	private TeacherReportService teacherReportService;

	@ResponseBody
	@RequestMapping(value = "addTeacherReport")
	public Object addTeacherReport(TeacherReport report) {
		Validator.validateBlank(report.getReportType(), "举报类型不能为空");
		Validator.validateBlank(report.getMsgId(), "被举报的消息ID不能为空");
		if (report.getMsgType() != TeacherReport.MSG_TYPE_DYNAMIC_MESSAGE
				&& report.getMsgType() != TeacherReport.MSG_TYPE_TEACHER_MESSAGE) {
			throw new ValidateException("消息类型枚举值不正确");
		}
		
		report.setTeacherId(AppContext.getTeacherId());

		teacherReportService.addTeacherReport(report);

		return dataJson(report);
	}
}
