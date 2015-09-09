package com.xuexibao.teacher.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuexibao.teacher.constant.RedisKeys;
import com.xuexibao.teacher.core.RedisOps;
import com.xuexibao.teacher.dao.FeudAnswerDetailDao;
import com.xuexibao.teacher.dao.FeudQuestDao;
import com.xuexibao.teacher.enums.FeudAnswerDetailStatus;
import com.xuexibao.teacher.enums.FeudEvaluateStatus;
import com.xuexibao.teacher.enums.FeudQuestStatus;
import com.xuexibao.teacher.model.FeudAnswerDetail;
import com.xuexibao.teacher.model.FeudPointFeeConf;
import com.xuexibao.teacher.model.FeudQuest;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.util.PropertyUtil;

/**
 * 
 * @author feng bin
 * @date 2015-4-16
 * 抢答相关的定时钟
 */
@Service
@Transactional
public class FeudScheduleTaskService {
	private static Logger logger = LoggerFactory.getLogger(FeudScheduleTaskService.class);
	
	@Resource
	private RedisOps redisOps;
	@Resource
	private TeacherService teacherService;
	@Resource
	protected FeudPointFeeConfService feudPointFeeConfService;
	@Resource
	protected PointLogService pointLogService;
	@Resource
	private FeudAnswerDetailDao feudAnswerDetailDao;
	@Resource
	private FeudQuestDao feudQuestDao;
	
	// 题目抢答过期时间 单位为分钟
	int configfeudTimeOut = new Integer(PropertyUtil.getProperty("feudTimeOut"));
	long feudTimeOutMs = configfeudTimeOut * 60 * 1000;
	//学生发起抢答过期时间 单位为分钟
	int studentFeudTimeOut = new Integer(PropertyUtil.getProperty("studentFeudTimeOut"));
	long studentFeudTimeOutMs = studentFeudTimeOut  * 60 * 1000;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 定期让待抢答的题目失效
	 */
	public void checkStudentFeudQuestExpire(){
		
			logger.info(" start task checkStudentFeudQuestExpire ");
			long currentTime = System.currentTimeMillis();
			List<FeudQuest> expireFeudQuestList =  feudQuestDao.getExpireStudentQuest();
			for(FeudQuest fq:expireFeudQuestList){
				Date ct = fq.getCreateTime();
				String timeStr = sdf.format(ct);
				try {
					long fqTime = sdf.parse(timeStr).getTime();
					if(currentTime - fqTime > studentFeudTimeOutMs){
						fq.setStatus(FeudQuestStatus.expire.getValue());
						feudQuestDao.updateByPrimaryKey(fq);
						logger.info("update FeudQuestExpire :"+fq.getId());
					}
				} catch (ParseException e) {
					e.printStackTrace();
					logger.error("checkStudentFeudQuestExpire tran time error:"+e,e.getMessage());
				}
			}
		
	}
	
	/**
	 * 定时清除老师抢答时间过期的脏数据
	 */
	public void checkTeacherExpireFeud() {
			logger.info(" start task checkTeacherExpireFeud ");
			Set<String> feudTeacherTimeKeys = redisOps
					.getKeys(RedisKeys.feudTeacherTimeKey);
			// 老师抢答KEY集合
			Iterator<String> itrFtt = feudTeacherTimeKeys.iterator();
			Set<String> feudQueueKeys = redisOps.getKeys(RedisKeys.feudQueueKey);
			Iterator<String> itrfqk = feudQueueKeys.iterator();

			// 遍历每个老师抢题KEY
			while (itrFtt.hasNext()) {
				String teacherFeudKey = itrFtt.next();
				String teacherFeudTime = (String) redisOps.getValue(teacherFeudKey);
				long currentTime = System.currentTimeMillis();
				long payTime = currentTime - Long.valueOf(teacherFeudTime);
				logger.info("teacherFeudKey:" + teacherFeudKey + " has payTime:"
						+ payTime + " feudTimeOutMs:" + feudTimeOutMs);
				// 代表老师抢题已过期
				if (payTime > feudTimeOutMs) {
					logger.info("teacher feud has expire:" + teacherFeudKey);
					String teacherId = teacherFeudKey
							.substring(RedisKeys.feudTeacherTimeKey.length());
					// 先看下相关队列是否有这个KEY，有则出队列
					while (itrfqk.hasNext()) {
						String queueKey = itrfqk.next();
						boolean isInQueue = redisOps.isSetMember(queueKey,
								teacherId);
						// 是否在队列中
						if (isInQueue) {
							// 出队列
							redisOps.removeSetValue(queueKey, teacherId);
							logger.info("delete queue member queue:" + queueKey
									+ " member:" + teacherFeudKey);
						}
						// 判断队列是否为空，空则删除队列
						int queueSize = redisOps.getSetMembers(queueKey).size();
						logger.info("queueSize queueKey:" + queueSize);
						if (queueSize <= 0) {
							redisOps.delete(queueKey);
							logger.info("delete queueKey:" + queueKey);
						}
					}
					// 删除老师这个抢题key
					redisOps.delete(teacherFeudKey);
					logger.info("delete teacherFeudKey:" + teacherFeudKey);
					// 给老师扣分，更新抢题明细表抢题失败
					//扣分逻辑
					feudAnswerDetailDao.updateExpireByTeacherId(teacherId);
					updateFeudDetailExpireByTeacherId(teacherId);
				}
			}
	}
	/**
	 * 抢答题目过期
	 * 根据教师ID
	 * @param teacherId
	 */
	private  void updateFeudDetailExpireByTeacherId(String teacherId){
		//更新老师抢答明细表上的扣积分。
		Teacher teacher = teacherService.getTeacher(teacherId);
		FeudPointFeeConf fPoint = feudPointFeeConfService.getFeudPointByStarAndTeacherIdentify(-1, teacher.getTeacherIdentify());
		List<FeudAnswerDetail> list = feudAnswerDetailDao.seletExpireByTeacherId(teacherId);
		for(FeudAnswerDetail ds:list){
			int point = fPoint.getValue();
			ds.setPoint(point);
			ds.setEvaluate(FeudEvaluateStatus.bad.getValue());
			ds.setStatus(FeudAnswerDetailStatus.expire.getValue());
			feudAnswerDetailDao.updateByPrimaryKey(ds);
			pointLogService.addPoint(teacherId, point, "抢答过期扣积分");
		}
	}

}
