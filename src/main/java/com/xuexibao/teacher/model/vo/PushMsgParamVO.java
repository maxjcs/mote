package com.xuexibao.teacher.model.vo;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.xuexibao.teacher.model.PushMessage;
import com.xuexibao.teacher.model.Teacher;

/**
 * @author fuguoqing 推送消息请求变量
 *         <p>
 *         type属性，学生端现有类型定义 as_answer: 3010,//我的求助中的回答 deleteask: 3020,//删除我的问题
 *         accept: 3030,//采纳 an_answer: 3040,//我的抢答中的回答 reply: 1010,//评论
 *         support: 1020,//赞 deletetopic: 1030,//删除逛逛帖子 operation: 1040,//运营消息
 *         exchange: 2010,//兑换 invite: 2030,//邀请 invited: 2040//被邀请
 */
public class PushMsgParamVO {

	// 推送目的终端类型，值必须选择 "android" or "iphone"
	private String phoneType;

	// 设备唯一标识，如果是 iphone,则 deviceUniqueId 代表
	// pushtoken；如果是android，则deviceUniqueId代表devid
	private String deviceUniqueId;

	// 设备关联用户的id
	private String userId;

	// 推送内容
	private Object content;

	// 消息创建时间
	private String createTime;

	// 见上
	private Integer type;

	// 帖子id,目前v3版本 iphone中作为请求参数
	private String topicId;

	// 消息title
	private String title;

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getDeviceUniqueId() {
		return deviceUniqueId;
	}

	public void setDeviceUniqueId(String deviceUniqueId) {
		this.deviceUniqueId = deviceUniqueId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public static PushMsgParamVO createMessage(Teacher teacher, Integer type, String content) {
		PushMsgParamVO msg = new PushMsgParamVO();
		msg.setCreateTime(new Date().toLocaleString());
		msg.setContent(new PushMessage(content));
		msg.setDeviceUniqueId(getPhoneToken(teacher));
		msg.setUserId(teacher.getId());
		msg.setTitle(content);
		msg.setPhoneType(getPhoneType(teacher.getLastDeviceType()));
		msg.setType(type);

		return msg;
	}

	private static String getPhoneToken(Teacher teacher) {
		if (StringUtils.equals("1", teacher.getLastDeviceType())) {
			return teacher.getLastDeviceId();
		}
		if (StringUtils.equals("2", teacher.getLastDeviceType())) {
			return teacher.getIosToken();
		}
		return "";

	}

	public static String getPhoneType(String type) {
		if (StringUtils.equals("1", type)) {
			return "android";
		}
		if (StringUtils.equals("2", type)) {
			return "iphone";
		}
		return type;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}