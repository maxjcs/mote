package com.xuexibao.teacher.service;

import com.xuexibao.teacher.dao.TeacherFunctionDao;
import com.xuexibao.teacher.model.TeacherFunctionPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 作者：付国庆
 * 时间：15/4/16－下午10:27
 * 描述：教师可用的模块配置service
 */
@Service
public class TeacherFunctionService {

    private Logger logger = LoggerFactory.getLogger(TeacherFunctionService.class);

    //默认每日任务模块的开启状态
    private final Integer DEFAULT_EVERYDAY_TASK_MODULE_STATUS = 1;
    //默认抢答模块的开启状态
    private final Integer DEFAULT_FEUD_MODULE_STATUS = 1;
    //默认互动学习模块的开启状态
    private final Integer DEFAULT_LEARN_TALK_MODULE_STATUS = 1;
    //默认支付模块的开启状态
    private final Integer DEFAULT_PAY_MODULE_STATUS = 1;

    @Autowired
    private TeacherFunctionDao teacherFunctionDao;

    /**
     * 根据教师id查询模块功能开启关闭设置
     * <p>
     * 如果没有教师对应的配置，初始化默认数据，返回默认模块开机关闭状态
     * <p>
     * 调用接口时，必须要保证teacherId的有效性
     *
     * @param teacherId
     * @return
     */
    public TeacherFunctionPojo selectByTeacherId(String teacherId) {
        if (!StringUtils.hasLength(teacherId)) {
            logger.error("teacherId is null");
            return null;
        }
        TeacherFunctionPojo pojo = teacherFunctionDao.selectById(teacherId);
        //默认认为非空的teacherId 是有效的，初始化对应的该教师的模块开机关闭配置
        if (pojo == null) {
            pojo = new TeacherFunctionPojo();
            pojo.setId(teacherId);
            pojo.setEverydayTask(DEFAULT_EVERYDAY_TASK_MODULE_STATUS);
            pojo.setFeud(DEFAULT_FEUD_MODULE_STATUS);
            pojo.setLearnTalk(DEFAULT_LEARN_TALK_MODULE_STATUS);
            pojo.setPay(DEFAULT_PAY_MODULE_STATUS);
            teacherFunctionDao.insert(pojo);
        }
        return pojo;
    }

    /**
     * 更新用户模块权限
     *
     * @param pojo
     */
    public void updateTeacherFunction(TeacherFunctionPojo pojo) {
        if (pojo == null || !StringUtils.hasLength(pojo.getId())) {
            logger.error("pojo or teacherId is null");
            return;
        }
        if (pojo.getFeud() == null && pojo.getEverydayTask() == null && pojo.getLearnTalk() == null && pojo.getPay() == null) {
            logger.error("all status cant all be null");
            return;
        }
        TeacherFunctionPojo odlPojo = teacherFunctionDao.selectById(pojo.getId());
        //FIXME check all value is 0 or 1

        //如果查询没有 则根据参数补齐缺失属性，新增
        if (odlPojo == null) {
            if (pojo.getLearnTalk() == null) {
                pojo.setLearnTalk(DEFAULT_LEARN_TALK_MODULE_STATUS);
            }
            if (pojo.getPay() == null) {
                pojo.setPay(DEFAULT_PAY_MODULE_STATUS);
            }
            if (pojo.getEverydayTask() == null) {
                pojo.setLearnTalk(DEFAULT_LEARN_TALK_MODULE_STATUS);
            }
            if (pojo.getFeud() == null) {
                pojo.setFeud(DEFAULT_FEUD_MODULE_STATUS);
            }
            teacherFunctionDao.insert(pojo);
            return;
        } else {
            teacherFunctionDao.updateByTeacherId(pojo);
        }
    }
}
