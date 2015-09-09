/**
 * @author oldlu
 */
package com.xuexibao.teacher.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.xuexibao.webapi.teacher.client.T_DictService;
import com.xuexibao.webapi.teacher.model.City;
import com.xuexibao.webapi.teacher.model.Grade;
import com.xuexibao.webapi.teacher.model.School;
import com.xuexibao.webapi.teacher.model.Subject;

@Component
public class RedisUtil {
	@Resource
	private T_DictService t_DictService;// 中间件的 字典服务

	public String getGradeStringByIds(List<Integer> grades) {
		if (CollectionUtils.isEmpty(grades)) {
			return "";
		}
		StringBuilder buffer = new StringBuilder();
		for (Integer gradeId : grades) {
			if (buffer.length() > 0) {
				buffer.append("、");
			}
			Grade grade = t_DictService.getGradeById(gradeId.longValue());
			if (grade != null) {
				buffer.append(grade.getName());
			}
		}
		
		return buffer.toString();
	}

	public School getSchoolById(Integer schoolId) {
		return t_DictService.getSchoolById(schoolId);
	}

	public City getCityById(Long cityId) {
		return t_DictService.getCityById(cityId);
	}

	
	public List<String> getGradeNameListByIds(List<Integer> grades) {
		List<String> result = new ArrayList<String>();

		if (CollectionUtils.isEmpty(grades)) {
			return result;
		}
		for (Integer gradeId : grades) {
			Grade grade = t_DictService.getGradeById(gradeId.longValue());
			if (grade != null) {
				result.add(grade.getName());
			}
		}
		return result;
	}

	public String getGradeStringById(Integer gradeId) {
		if (gradeId == null) {
			return "";
		}
		Grade grade = t_DictService.getGradeById(gradeId.longValue());
		if (grade != null) {
			return grade.getName();
		}
		return "";
	}

	public String getSubjectStringByIds(List<Integer> subjects) {
		if (CollectionUtils.isEmpty(subjects)) {
			return "";
		}
		StringBuilder buffer = new StringBuilder();
		for (Integer subjectId : subjects) {
			if (buffer.length() > 0) {
				buffer.append("、");
			}
			Subject subject = t_DictService.getSubjectById(subjectId.longValue());
			if (subject != null) {
				buffer.append(subject.getName());
			}
		}
		return buffer.toString();
	}

	public String getSchoolNameById(Integer schoolId) {
		School school = t_DictService.getSchoolById(schoolId.longValue());
		if (school != null) {
			return school.getName();
		}
		return "";
	}

	public String getCityNameById(Integer cityId) {
		City city = t_DictService.getCityById(cityId.longValue());
		if (city != null) {
			return city.getName();
		}
		return "";
	}
}
