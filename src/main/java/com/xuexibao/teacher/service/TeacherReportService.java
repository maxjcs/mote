package com.xuexibao.teacher.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xuexibao.teacher.dao.TeacherReportDao;
import com.xuexibao.teacher.model.TeacherReport;


/**
 * 
 * @author oldlu
 *
 */
@Service
public class TeacherReportService {
 	@Resource
	private TeacherReportDao teacherReportDao;

	public TeacherReport loadTeacherReport(String id) {
		return teacherReportDao.loadTeacherReport(id);
	}

	public void addTeacherReport(TeacherReport report) {
		report.setCreateTime(new Date());
		report.setUpdateTime(new Date());
		
		teacherReportDao.addTeacherReport(report);
	}
 

	public void deleteTeacherReport(String id) {
		teacherReportDao.deleteTeacherReport(id);
	}
}
