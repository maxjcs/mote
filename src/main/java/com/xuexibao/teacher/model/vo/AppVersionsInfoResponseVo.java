package com.xuexibao.teacher.model.vo;

/**
 * 作者：付国庆
 * 时间：15/4/11－下午3:01
 * 描述：app 版本信息返回给手机端必须的属性信息
 */

public class AppVersionsInfoResponseVo {

    //是否需要更新
    private String needUpdate;
    //是否需要强制更新
    private String forceUpdate;
    //版本对应的描述
    private String description;
    //下载地址
    private String downloadUrl;

    //是否开启xmpp 1:关闭，0:开启
    private Integer isOpenXmpp;

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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getIsOpenXmpp() {
        return isOpenXmpp;
    }

    public void setIsOpenXmpp(Integer isOpenXmpp) {
        this.isOpenXmpp = isOpenXmpp;
    }
}
