package com.xuexibao.teacher.model;

import java.sql.Timestamp;

/**
 * 作者：付国庆
 * 时间：15/4/10－下午7:45
 * 描述：表 push_msg_persistent pojo
 */

public class PushMsgPersistentPojo {
    //表主键
    private String id;
    //消息推送内容
    private String content;
    //消息内容缩略,ios push 会用到
    private String title;
    //消息类型
    private Integer msgType;


//    public static final int TYPE_RUSH = 5010;//抢答
//    public static final int TYPE_SETTING = 5020;//设置
//    public static final int TYPE_DAILY_TASK = 5030;//每日任务
//    public static final int TYPE_FAMOUS = 5040;//名师汇
//    public static final int TYPE_WALLET = 5050;//钱包
//    public static final int TYPE_SHOP = 5060;//微店
//    public static final int TYPE_SYSTEM = 5070;//系统消息
//    public static final int TYPE_HUDONG = 4010;//互动

    //消息类型名称
    private String name;

    //已读 Y 未读 N
    private String isRead;
    //推送消息是否已经成功发出给用户：Y:是，N:否
    private String isSend;
    //消息发送时间
    private Timestamp addTime;
    //接受消息的用户id
    private String userId;
    //接收手机的类型
    private String phoneType;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getName() {
        if (msgType != null) {
            // fixme 目前写死，后续独立到db维护
            switch (msgType) {
                case 4010: {
                    return "互动";
                }
                case 5010: {
                    return "抢答";
                }
                case 5020: {
                    return "设置";
                }
                case 5030: {
                    return "每日任务";
                }
                case 5040: {
                    return "名师汇";
                }
                case 5050: {
                    return "钱包";
                }
                case 5060: {
                    return "微店";
                }
                case 5070: {
                    return "运营消息";
                }
                default:
                    return "";
            }
        }
        return "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
