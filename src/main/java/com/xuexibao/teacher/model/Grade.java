package com.xuexibao.teacher.model;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Grade {
	public static final Integer FILTER_ALL = 0;

	public static final List<String> LIST_GRADE_ALL = Arrays.asList("小学", "初中", "高中", "未知");
	public static final List<Integer> LIST_SUBJECT_ALL = Arrays.asList(1, 2, 4, 5, 6, 7, 8, 9, 99);
	public static Map<String, String> GRADE_MAP = new HashMap<String, String>();

	static{
		GRADE_MAP.put("1","一年级");
		GRADE_MAP.put("2","二年级");
		GRADE_MAP.put("3","三年级");
		GRADE_MAP.put("4","四年级");
		GRADE_MAP.put("5","五年级");
		GRADE_MAP.put("6","六年级");
		GRADE_MAP.put("7","初一");
		GRADE_MAP.put("8","初二");
		GRADE_MAP.put("9","初三");
		GRADE_MAP.put("10","高一");
		GRADE_MAP.put("11","高二");
		GRADE_MAP.put("12","高三");
	} 
	
	private Long id;

	private String name;

	@JsonIgnore
	private Long sortNumber;
	@JsonIgnore
	private Date createTime;
	@JsonIgnore
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Long getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Long sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}