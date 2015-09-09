package com.xuexibao.teacher.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xuexibao.teacher.dao.RattingApplyDao;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Rating;
import com.xuexibao.teacher.model.RattingApply;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.rpcvo.BuyAndCommentCount;
import com.xuexibao.teacher.util.BusinessConstant;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class RattingApplyService {
	@Resource
	private RattingApplyDao rattingApplyDao;
	@Resource
	private AudioUploadService audioUploadService;
	@Resource
	private TeacherService teacherService;
	@Resource
	private StudentApiService studentApiService;

	public void addRattingApply(RattingApply apply) {
		String teacherId = apply.getTeacherId();
		Teacher teacher = teacherService.getRequiredTeacher(teacherId);
		Date startTime = teacher.getCreateTime();

		RattingApply last = rattingApplyDao.getLastRattingApply(teacherId);
		if (last != null) {
			startTime = last.getCreateTime();
			if (last.getStatus() == RattingApply.STATUS_APPING) {
				throw new BusinessException("上次升星申请还未审核通过，不能重复申请");
			}
		}
		List<String> audioIds = getListAudioId(teacherId, startTime, new Date());
		int countAudio = audioIds.size();
		if (countAudio < RattingApply.APPLY_AUDIO_COUNT) {
			throw new BusinessException("您录制的讲解不足10条");
		}

		rattingApplyDao.addRattingApply(apply);
	}

	public RattingApply getLastRattingApply(String teacherId) {
		return rattingApplyDao.getLastRattingApply(teacherId);
	}

	public List<RattingApply> listRattingApply(RattingApply apply) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("teacherId", apply.getTeacherId());

		return rattingApplyDao.listRattingApply(map);
	}

	/**
	 * @param teacherId
	 * @return
	 */
	public Map<String, Object> getRattingInfo(String teacherId) {
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("distanceTime", 0);
		result.put("status", 4);
		result.put("text", "我要升星");
		result.put("promptText", "账号注册");

		Teacher teacher = teacherService.getRequiredTeacher(teacherId);
		Date startTime = teacher.getCreateTime();
		result.put("distanceTime", new Date().getTime() - startTime.getTime());

		RattingApply last = rattingApplyDao.getLastRattingApply(teacherId);

		if (last != null) {
			startTime = last.getCreateTime();
			result.put("promptText", "上次请求");
			result.put("distanceTime", new Date().getTime() - last.getCreateTime().getTime());

			long hours = (new Date().getTime() - last.getCreateTime().getTime()) / 3600 / 1000;

			if (hours < BusinessConstant.REAPPLY_RATTING_HOURS) {
				result.put("status", last.getStatus());
			}

			if (RattingApply.STATUS_APPING == last.getStatus() && !"4".equals(result.get("status"))) {
				result.put("text", "等待专家审核中...");
				result.put("auditPrompt", "学习宝专家将从已录制讲解中抽取10道题进行审核");
			} else {
				result.put("auditContent", last.getAuditContent());

				if (RattingApply.STATUS_REDUCE_STAR == last.getStatus()) {
					result.put("statusText", "很遗憾，被降星了");
				}
				if (RattingApply.STATUS_UNAUDIT == last.getStatus()) {
					result.put("statusText", "请求被驳回");
				}
				if (RattingApply.STATUS_AUDITED == last.getStatus()) {
					result.put("statusText", "恭喜你升级到" + Rating.getRatingText(last.getStar()) + "星");
				}
				if (hours < BusinessConstant.REAPPLY_RATTING_HOURS) {
					result.put("auditPrompt", (BusinessConstant.REAPPLY_RATTING_HOURS - hours) + "小时后才能继续申请");
				}
			}
		}

		List<String> audioIds = getListAudioId(teacherId, startTime, new Date());

		int countAudio = audioIds.size();

		BuyAndCommentCount.Item item = studentApiService.getAudioBuyAndCommentCount(audioIds, teacherId, false);
		if (item != null) {
			result.put("buyCount", item.buyCount);
			result.put("commentCount", item.commentCount);
		}
		result.put("countAudio", countAudio);
		result.put("popularRate", 0);
		if (countAudio != 0 && item.buyCount != 0 & item.commentCount != 0) {
			DecimalFormat df = new DecimalFormat("######0.00");
			double popularRate = item.commentCount * 1.0 / item.buyCount;

			result.put("popularRate", Double.valueOf(df.format(popularRate)));
		}

		return result;
	}

	private List<String> getListAudioId(String teacherId, Date startTime, Date endTime) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("teacherId", teacherId);

		return rattingApplyDao.listAudioIdByTime(paramMap);
	}
}
