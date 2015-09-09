package com.xuexibao.teacher.model;

import java.sql.Timestamp;

/**
 * 作者：付国庆
 * 时间：15/4/11－上午10:14
 * 描述：app_version_info 表 pojo
 */

public class AppVersionsInfoPojo {
    //表主键
    private Integer id;
    //版本code int值
    private Integer versionCode;
    //版本name string
    private String versionName;
    //是否需要升级，Y：需要，N：不需要
    private String needUpdate;
    //是否需要强制升级，Y：需要强制升级 N：不需要强制升级
    private String forceUpdate;
    //版本描述信息
    private String description;
    //版本对应的app类型，1:安卓，2:IOS
    private Integer appType;
    //该版本新增时间
    private Timestamp addTime;
    //app 下载路径
    private String downloadUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(String needUpdate) {
        this.needUpdate = needUpdate;
    }

    public String getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
