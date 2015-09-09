package com.xuexibao.teacher.service;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuexibao.teacher.constant.PushConstant;
import com.xuexibao.teacher.dao.PointLogDao;
import com.xuexibao.teacher.dao.RattingApplyDao;
import com.xuexibao.teacher.dao.TeacherDao;
import com.xuexibao.teacher.model.PointLog;
import com.xuexibao.teacher.model.Rating;
import com.xuexibao.teacher.model.RattingApply;
import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.vo.PushMsgParamVO;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class PointLogService {
	private static Logger logger = LoggerFactory.getLogger(PointLogService.class);

	@Resource
	private PointLogDao pointLogDao;
	@Resource
	private TeacherService teacherService;
	@Resource
	private TeacherDao teacherDao;
	@Resource
	private RatingService ratingService;
	@Resource
	private PushMsgService pushMsgService;
	
    @Resource
    private RattingApplyDao rattingApplyDao;

	/**
	 * 
	 * @param teacherId
	 *            教师ID
	 * @param point
	 *            积分变化，可为正负数
	 * @param description
	 *            积分变化描述， 如：你的音频被评价为差评，扣N积分
	 */
	@Transactional
	public void addPoint(String teacherId, int point, String description) {
//		StarPoint starPoint=teacherService.getStarPointByTeacherId(teacherId);
//		
//		PointLog pointlog = new PointLog();
//		pointlog.setPoint(point);
//		pointlog.setDescription(description);
//		pointlog.setTeacherId(teacherId);
//		pointlog.setRemainPoint(starPoint.getPoint() + point);
//
//		pointLogDao.addPointLog(pointlog);// 插入积分日志
//		logger.info("[" + teacherId + "]积分变化，" + description);

		teacherDao.updatePoint(teacherId, point);// 更新教师积分

		upgradeTeacherStar(teacherId);// 判断是否升星、降星
	}

	/**
	 * @param teacherId
	 */
	private void upgradeTeacherStar(String teacherId) {
		Teacher teacher = teacherService.getRequiredTeacher(teacherId);
		StarPoint starPoint=teacherService.getStarPointByTeacherId(teacherId);
		Rating rating = ratingService.getRatingByPoint(starPoint.getPoint());

		int oldStar = starPoint.getStar();
		int pointStar = 0;
		if (rating != null) {
			pointStar = rating.getStar();
		}
		if (pointStar != oldStar) {
			teacherDao.updateStar(teacherId, pointStar);
			//如果降为0星
			if(pointStar==0){
				RattingApply rattingapply=new RattingApply();
	            rattingapply.setTeacherId(teacher.getId());
	            rattingapply.setStatus(3);
	            rattingapply.setStar(0);
	            rattingapply.setAuditContent("运营后台-修改星级为0星");
	            rattingapply.setAuditId("admin");
	            rattingapply.setAuditTime(new Date());
	            rattingApplyDao.addRattingApply(rattingapply);
			}

			if (pointStar > oldStar) {
				logger.info(teacherId + " 恭喜你升级到" + pointStar + "星");

				String content = "恭喜你升级" + pointStar + "星";
				PushMsgParamVO msg = PushMsgParamVO.createMessage(teacher, PushConstant.TYPE_SYSTEM, content);
				pushMsgService.pushMsg(msg);
			} else {
				logger.info(teacherId + " 你的积分不足，下降为" + pointStar + "星");

				Rating oldRating = ratingService.getRatingByStar(oldStar);
				if (oldRating != null) {
					String content = "你的积分不足" + oldRating.getPoints() + "，下降为" + pointStar + "星";
					PushMsgParamVO msg = PushMsgParamVO.createMessage(teacher, PushConstant.TYPE_SYSTEM, content);
					pushMsgService.pushMsg(msg);
				}
			}
		}
	}
}
