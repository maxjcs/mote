package com.xuexibao.teacher.service.evaluprocessor;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.service.TeacherService;

/**
 * @author oldlu
 */
@Component
public class EvaluProcessorManager {
	private static int NSTAR = 3;
	// 每日任务处理
	@Resource
	private EvaluProcessor taskStudentGreatNStarProcessor;
	@Resource
	private EvaluProcessor taskStudentLessNStarProcessor;
	@Resource
	private EvaluProcessor taskTeacherGreatNStarProcessor;
	@Resource
	private EvaluProcessor taskTeacherLessNStarProcessor;

	@Resource
	private EvaluProcessor feudStudentProcessor;
	@Resource
	private EvaluProcessor feudTeacherProcessor;

	@Resource
	private EvaluProcessor explanationProcessor;
	@Autowired
	private TeacherService teacherService;

	public EvaluProcessor getTaskProcessorByTeacher(Teacher teacher) {
		StarPoint starPoint = teacherService.getStarPointByTeacherId(teacher.getId());
		if (teacher.isTeacher()) {
			if (starPoint.getStar() >= NSTAR) {
				return taskTeacherGreatNStarProcessor;
			}
			return taskTeacherLessNStarProcessor;
		} else {
			if (starPoint.getStar() >= NSTAR) {
				return taskStudentGreatNStarProcessor;
			}
			return taskStudentLessNStarProcessor;
		}
	}

	public EvaluProcessor getFeudProcessorByTeacher(Teacher teacher) {
		if (teacher.isTeacher()) {
			return feudTeacherProcessor;
		} else {
			return feudStudentProcessor;
		}
	}

	public EvaluProcessor getExplanationProcessorByTeacher(Teacher teacher) {
		if (teacher.isTeacher()) {
			return explanationProcessor;
		} else {
			return explanationProcessor;
		}
	}
}
