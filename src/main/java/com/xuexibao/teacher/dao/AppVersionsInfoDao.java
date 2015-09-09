package com.xuexibao.teacher.dao;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.AppVersionsInfoPojo;
import org.springframework.stereotype.Repository;

/**
 * 作者：付国庆
 * 时间：15/4/11－上午10:18
 * 描述：app_version_info 表操作 dao
 */
@MybatisMapper
@Repository
public interface AppVersionsInfoDao {

    /**
     * 新增版本信息描述记录
     *
     * @param pojo
     * @return
     */
    Integer insert(AppVersionsInfoPojo pojo);

    /**
     * 查询 appType类型的最新的版本信息
     *
     * @param appType
     * @return
     */
    AppVersionsInfoPojo getLastVersionInfoByAppType(Integer appType);

    /**
     * 查询在versionCode版本之后的版本里有多少个强制更新，只需要有一个就决定返回给用户必须要强制更新
     *
     * @param versionCode
     * @return
     */
    Integer getAfterNowVersionCodeForceCount(Integer versionCode);


}
