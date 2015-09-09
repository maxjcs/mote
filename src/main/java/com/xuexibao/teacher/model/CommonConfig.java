/**
 * @author oldlu
 */
package com.xuexibao.teacher.model;

import java.io.Serializable;

public class CommonConfig implements Serializable {
	public static final String KEY_AUDIO_SALE_INFO_TEACHER = "audio_sales_info_teacher";
	public static final String KEY_AUDIO_SALE_INFO_STUDENT = "audio_sales_info_student";
	public static final String KEY_EVERYDAY_TASK_ANSWER_NUM = "everyday_task_answer_num";
	public static final String KEY_COMPATE_TASK_REWARD = "complate_task_reward";
	public static final String KEY_RECHARGE_REWARD = "recharge_reward";
	public static final String KEY_CHONGZHI_BAOYUE_FENGCHENG = "baoyue_chongzhi_fencheng";
	public static final String KEY_CHONGZHI_BAONIAN_FENGCHENG = "baonian_chongzhi_fencheng";

	public static final int TYPE_CHONGZHI_BAONIAN = 2;
	public static final int TYPE_CHONGZHI_BAOYUE = 1;

	private String key;
	private String id;
	private Integer value;
	private String desc;

	private Integer value1;
	private Integer value2;
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getValue1() {
		return value1;
	}

	public void setValue1(Integer value1) {
		this.value1 = value1;
	}

	public Integer getValue2() {
		return value2;
	}

	public void setValue2(Integer value2) {
		this.value2 = value2;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
