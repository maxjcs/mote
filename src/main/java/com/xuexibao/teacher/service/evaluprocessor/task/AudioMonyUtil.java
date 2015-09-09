/**
 * @author oldlu
 */
package com.xuexibao.teacher.service.evaluprocessor.task;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.xuexibao.teacher.enums.AudioSource;
import com.xuexibao.teacher.enums.Plan;
import com.xuexibao.teacher.enums.PlanFactory;
import com.xuexibao.teacher.enums.TeacherIdentify;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioSet;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.service.OrganizationService;

@Component
public class AudioMonyUtil {
	@Resource
	private OrganizationService organizationService;

	public Map<String, Object> getAudioIncomeByPlanType(Audio audio, Teacher teacher) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(audio.getSource()!=AudioSource.explanation.getValue()&&audio.getSource()!=AudioSource.task.getValue()){
			return result;
		}
		if (teacher.getTeacherIdentify() == TeacherIdentify.teacher.getValue()) {
			Plan plan = PlanFactory.getPlanD();
			if (StringUtils.equals(audio.getPlanType(), PlanFactory.PLAN_C)) {
				plan = PlanFactory.getPlanC();
			}
			if (StringUtils.equals(audio.getPlanType(), PlanFactory.PLAN_B)) {
				plan = PlanFactory.getPlanB();
			}
			if (StringUtils.equals(audio.getPlanType(), PlanFactory.PLAN_A)) {
				plan = PlanFactory.getPlanA();
			}

			if(!StringUtils.isEmpty(audio.getOrgTeacherId())){
				result.put("orgTeacherId", audio.getOrgTeacherId());
				result.put("orgProfit", plan.getOrgProfit());
			}
			
			result.put("teacherId", audio.getTeacherId());
			result.put("teacherProfit", plan.getTeacherProfit());
			
			
		}else if(teacher.getTeacherIdentify() == TeacherIdentify.student.getValue()){
			Plan plan = null;
			if (StringUtils.equals(audio.getPlanType(), PlanFactory.PLAN_C)) {
				plan = PlanFactory.getPlanC();
			}
			if (StringUtils.equals(audio.getPlanType(), PlanFactory.PLAN_B)) {
				plan = PlanFactory.getPlanB();
			}
			if (StringUtils.equals(audio.getPlanType(), PlanFactory.PLAN_A)) {
				plan = PlanFactory.getPlanA();
			}

			if(plan!=null){
				result.put("orgTeacherId", audio.getOrgTeacherId());
				result.put("orgProfit", plan.getOrgProfit());
			}
			
		}

		return result;
	}

	public Map<String, Object> getAudioSetIncomeByPlanType(AudioSet set, Teacher teacher) {
		Map<String, Object> result = new HashMap<String, Object>();
		Plan plan = PlanFactory.getPlanD();
		if (StringUtils.equals(teacher.getPlanType(), PlanFactory.PLAN_B)) {
			plan = PlanFactory.getPlanB();
		}
		if (StringUtils.equals(teacher.getPlanType(), PlanFactory.PLAN_A)) {
			plan = PlanFactory.getPlanA();
		}
		float price = set.getPrice() / 100;
		result.put("orgTeacherId", organizationService.getTeacherIdByOrgId(teacher.getCurOrgId()));
		result.put("teacherId", teacher.getId());
		result.put("orgProfit", plan.getOrgProfit() * price);
		result.put("teacherProfit", plan.getTeacherProfit() * price);
		result.put("isFree", set.getIsFree());
		
		return result;
	}
}
