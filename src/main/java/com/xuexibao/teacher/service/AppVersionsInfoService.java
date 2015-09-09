package com.xuexibao.teacher.service;

import com.xuexibao.teacher.dao.AppVersionsInfoDao;
import com.xuexibao.teacher.model.AppVersionsInfoPojo;
import com.xuexibao.teacher.model.vo.AppVersionsInfoResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/**
 * 作者：付国庆
 * 时间：15/4/11－上午10:40
 * 描述：版本信息描述操作服务类
 */
@Service
public class AppVersionsInfoService {

    private final Logger log = LoggerFactory.getLogger(AppVersionsInfoService.class);

    @Autowired
    private AppVersionsInfoDao appVersionsInfoDao;

    private final String NEED_UPDATE_YES = "Y";
    private final String NEED_UPDATE_NO = "N";
    private final String FORCE_UPDATE_YES = "Y";
    private final String FORCE_UPDATE_NO = "N";

    //默认该版本是否需要更新 是的
    private final String DEFAULT_NEED_UPDATE = NEED_UPDATE_YES;
    //默认该版本是否需要强制 不是的
    private final String DEFAULT_FORCE_UPDATE = FORCE_UPDATE_NO;

    //app类型－安卓
    private final Integer APP_TYPE_ANDROID = 1;
    //app类型－IOS
    private final Integer APP_TYPE_IOS = 2;

    //默认app类型 1:安卓
    private final Integer DEFAULT_APP_TYPE = APP_TYPE_ANDROID;


    /**
     * 新增版本描述
     *
     * @param pojo
     * @return 新增成功返回对应的主键，失败返回null
     */
    public Integer addVersionInfo(AppVersionsInfoPojo pojo) {

        //param check
        if (pojo == null) {
            log.error("addVersionInfo param pojo is null");
            return null;
        }
        if (pojo.getVersionCode() == null || pojo.getVersionCode().intValue() <= 0) {
            log.error("addVersionInfo param pojo.VersionCode is Illegal");
            return null;
        }
        if (!StringUtils.hasLength(pojo.getVersionName())) {
            log.error("addVersionInfo param pojo.VersionNam is null");
            return null;
        }
        if (pojo.getAppType() != null && pojo.getAppType().intValue() != APP_TYPE_ANDROID.intValue()
                && pojo.getAppType().intValue() != APP_TYPE_IOS.intValue()) {
            log.error("addVersionInfo param pojo.AppType is Illegal");
            return null;
        }

        //add default value
        if (!StringUtils.hasLength(pojo.getNeedUpdate())) {
            pojo.setNeedUpdate(DEFAULT_NEED_UPDATE);
        }
        if (!StringUtils.hasLength(pojo.getForceUpdate())) {
            pojo.setForceUpdate(DEFAULT_FORCE_UPDATE);
        }
        if (pojo.getAppType() == null) {
            pojo.setAppType(DEFAULT_APP_TYPE);
        }
        if (pojo.getAddTime() == null) {
            pojo.setAddTime(new Timestamp(System.currentTimeMillis()));
        }
        appVersionsInfoDao.insert(pojo);

        return pojo.getId();
    }

    public AppVersionsInfoResponseVo getLastVersionInfoByAppType(Integer appType, Integer appNowVersionCode) {
        if (appType == null
                || (appType.intValue() != APP_TYPE_IOS && appType.intValue() != APP_TYPE_ANDROID)) {
            log.error("appType is Illegal");
            return null;
        }

        AppVersionsInfoPojo lastOne = appVersionsInfoDao.getLastVersionInfoByAppType(appType);

        AppVersionsInfoResponseVo responseVo = new AppVersionsInfoResponseVo();
        responseVo.setDescription(lastOne.getDescription());
        responseVo.setDownloadUrl(lastOne.getDownloadUrl());

        if (lastOne.getVersionCode().intValue() > appNowVersionCode) {
            //需要更新
            responseVo.setNeedUpdate(NEED_UPDATE_YES);

            Integer afterThisVersionCodeForceCount = appVersionsInfoDao.getAfterNowVersionCodeForceCount(appNowVersionCode);
            if (afterThisVersionCodeForceCount > 0) {
                //需要强制更新
                responseVo.setForceUpdate(FORCE_UPDATE_YES);
            } else {
                //不需要强制更新
                responseVo.setForceUpdate(FORCE_UPDATE_NO);
            }
            return responseVo;

        } else {
            //不需要更新
            responseVo.setNeedUpdate(NEED_UPDATE_NO);
            responseVo.setForceUpdate(FORCE_UPDATE_NO);
            return responseVo;
        }


    }
}
