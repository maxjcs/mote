package com.xuexibao.teacher.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.common.Status;
import com.xuexibao.teacher.dao.TeacherFollowedDao;
import com.xuexibao.teacher.dao.TeacherRechargeDao;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.CommonConfig;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.TeacherRecharge;
import com.xuexibao.webapi.student.client.T_UserService;
import com.xuexibao.webapi.student.model.User;

/**
 * 
 * @author oldlu
 *
 */
@Service
public class TeacherRechargeService {
	@Resource
	private TeacherRechargeDao teacherRechargeDao;
	@Resource
	private T_UserService t_UserService;
	@Resource
	private TeacherFollowedDao teacherFollowedDao;
	@Resource
	private CommonConfigService commonConfigService;
	@Resource
	private TeacherService teacherService;

	public TeacherRecharge loadTeacherRecharge(String id) {
		return teacherRechargeDao.loadTeacherRecharge(id);
	}

	public void addTeacherRecharge(TeacherRecharge recharge) {
		User student = t_UserService.getUserByMobile(recharge.getStudentPhoneNumber());
		if (student == null) {
			throw new BusinessException("该账号不存在，请核实账号是否正确");
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", recharge.getTeacherId());
		paramMap.put("userId", student.get_id());

		if (CollectionUtils.isEmpty(teacherFollowedDao.isFollowed(paramMap))) {
			throw new BusinessException("该账号未关注您，请通知学生先关注您");
		}

		TeacherRecharge exsits = teacherRechargeDao.getRechargeByTeacherIdStudentId(paramMap);

		if (exsits != null) {
			if (exsits.getStatus() == TeacherRecharge.STATUS_STUDENT_ALREADY_CHARGE) {
				throw new BusinessException("该学生已参加过活动，您可以继续邀请其他学生");
			}
			if (exsits.getStatus() == TeacherRecharge.STATUS_WAIT_STUDENT_CHARGE) {
				throw new BusinessException("您已提交过该账号，请通知学生尽快完成充值");
			}
		}

		recharge.setStudentId(student.get_id());
		recharge.setCreateTime(new Date());
		recharge.setUpdateTime(new Date());

		teacherRechargeDao.addTeacherRecharge(recharge);
	}

	public void deleteTeacherRecharge(String id) {
		teacherRechargeDao.deleteTeacherRecharge(id);
	}

	public List<TeacherRecharge> listTeacherRecharge(Map<String, Object> paramMap) {
		List<TeacherRecharge> list = teacherRechargeDao.listTeacherRecharge(paramMap);

		return list;
	}

	public Map<String, Object> getChargetNotice() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("show", false);

		CommonConfig config = commonConfigService.getRechargeReward();
		if (config != null) {
			if (config.getStatus() == Status.STATUS_Y) {
				result.put("show", true);
				result.put("message", String.format("学生上线冲%s，老师钱包添%s", config.getValue1(), config.getValue2()));
			}
		}
		return result;
	}

	public Map<String, Object> updateStudentRecharge(String teacherId, String studentId, Integer money) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("money", 0);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);
		paramMap.put("userId", studentId);

		if (CollectionUtils.isEmpty(teacherFollowedDao.isFollowed(paramMap))) {
			result.put("message", "该学生没有关注该老师");
			return result;
		}

		TeacherRecharge exsits = teacherRechargeDao.getRechargeByTeacherIdStudentId(paramMap);

		if (exsits == null) {
			result.put("message", "老师没有邀请学生参加活动");
			return result;
		}

		if (exsits.getStatus() == TeacherRecharge.STATUS_STUDENT_ALREADY_CHARGE) {
			result.put("message", "学生已经参加过该老师的活动，重复充值不返");
			return result;
		}

		CommonConfig config = commonConfigService.getRechargeReward();
		if (config == null) {
			result.put("message", "不存在充值赠送活动");
			return result;
		}

		if (config.getStatus() == Status.STATUS_N) {
			result.put("message", "充值赠送活动已经下架");
			return result;
		}

		if (money >= config.getValue1()) {
			result.put("message", "充值金额合规,老师返现成功");
			result.put("money", config.getValue2());
			exsits.setRechargeTime(new Date());
			exsits.setRechargeMoney(money);
			exsits.setIncomeMoney(config.getValue2());
			exsits.setStatus(TeacherRecharge.STATUS_STUDENT_ALREADY_CHARGE);

			teacherRechargeDao.updateStudentRecharge(exsits);

			return result;
		} else {
			result.put("message", "充值金额没达到" + config.getValue1() + "老师没有返现");

			return result;
		}
	}

	public Map<String, Object> getTeacherRewardOfMemberCharge(String phoneNumber, Integer type, Integer money) {
		Map<String, Object> result = new HashMap<String, Object>();

		Teacher teacher = teacherService.loadTeacherByPhoneNumber(phoneNumber);
		if (teacher == null) {
			throw new BusinessException("教师不存在", -2);
		}

		CommonConfig config = null;
		if (type == CommonConfig.TYPE_CHONGZHI_BAOYUE) {
			config = commonConfigService.getConfigByKey(CommonConfig.KEY_CHONGZHI_BAOYUE_FENGCHENG);
		}
		if (type == CommonConfig.TYPE_CHONGZHI_BAONIAN) {
			config = commonConfigService.getConfigByKey(CommonConfig.KEY_CHONGZHI_BAONIAN_FENGCHENG);
		}
		if (config == null) {
			throw new BusinessException("配置数据不存在", -2);
		}
		if (config.getStatus() == Status.STATUS_N) {
			throw new BusinessException("配置数据已关闭", -2);
		}
		result.put("teacherId", teacher.getId());
		result.put("money", money * config.getValue() / 100f);

		return result;
	}
}
