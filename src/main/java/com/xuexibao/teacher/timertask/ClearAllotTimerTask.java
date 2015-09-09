package com.xuexibao.teacher.timertask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.dao.QuestionAllotDao;
import com.xuexibao.teacher.dao.TaskDao;
import com.xuexibao.teacher.model.OrgQuest;
import com.xuexibao.teacher.model.QuestionAllot;
import com.xuexibao.teacher.util.BusinessConstant;

@Service
public class ClearAllotTimerTask {

	private static Logger logger = LoggerFactory.getLogger(ClearAllotTimerTask.class);

	private Lock lock = new ReentrantLock();

	@Resource
	private QuestionAllotDao questionAllotDao;
	@Resource
	private TaskDao taskDao;

	private final int LIMIT = 1000;

	public void doSchedule() {
		logger.info("[Thread-{}]开始清理teacher_allot表", Thread.currentThread().getId());

		if (lock.tryLock()) {
			try {
				doClear();
			} catch (Exception e) {
				logger.error("任务异常", e);
			} finally {
				lock.unlock();
			}
		} else {
			logger.error("上一个定时任务正在执行，终止");
		}

		logger.info("[Thread-{}]清理teacher_allot表结束", Thread.currentThread().getId());
	}

	private void doClear() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limit", LIMIT);
		paramMap.put("day", BusinessConstant.ALLOT_EXPIRED_TIME);

		List<QuestionAllot> list = questionAllotDao.queryExpiredId(paramMap);
		while (CollectionUtils.isNotEmpty(list)) {
			logger.info("清理teacher_allot表的数据{}条.", list.size());
			for (QuestionAllot allot : list) {
				questionAllotDao.deleteQuestionAllotById(allot.getId());
				questionAllotDao.releaseQuestionAllotByRealId(allot.getQuestionId());
				if(allot.getOrgId()>0){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("questids", Arrays.asList(allot.getQuestionId()));
					map.put("orgId", allot.getOrgId());
					map.put("status", OrgQuest.STATUS_FREE);

					taskDao.updateOrgQuestAllot(map);
				}
			}

			list = questionAllotDao.queryExpiredId(paramMap);
		}
	}
}
