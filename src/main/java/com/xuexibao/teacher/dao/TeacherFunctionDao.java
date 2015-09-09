package com.xuexibao.teacher.dao;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.AppVersionsInfoPojo;
import com.xuexibao.teacher.model.TeacherFunctionPojo;
import org.springframework.stereotype.Repository;

/**
 * 作者：付国庆
 * 时间：15/4/11－上午10:18
 * 描述：teacher_function 表操作 dao
 */
@MybatisMapper
@Repository
public interface TeacherFunctionDao {

    /**
     * 根据teacherId 查询模块状态
     *
     * @param id
     * @return
     */
    TeacherFunctionPojo selectById(String id);

    /**
     * 新增
     *
     * @param pojo
     */
    void insert(TeacherFunctionPojo pojo);


    /**
     * 根据教师id更新教师状态
     *
     * @param pojo
     */
    void updateByTeacherId(TeacherFunctionPojo pojo);
}
