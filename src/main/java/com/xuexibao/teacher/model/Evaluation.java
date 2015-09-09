package com.xuexibao.teacher.model;

import java.io.Serializable;

/**
 * 
 * @author oldlu
 * 
 */
public class Evaluation implements Serializable {
	private static final long serialVersionUID = 8286152224720268491L;
	public static final int LEVEL_GOOD = 1;
	public static final int LEVEL_MEDIUM = 2;
	public static final int LEVEL_BAD = 3;
	private int id;
	private int level;
	private String name;
	private String description;
	private int point;
	private int teacherIdentify;

	public int getTeacherIdentify() {
		return teacherIdentify;
	}

	public void setTeacherIdentify(int teacherIdentify) {
		this.teacherIdentify = teacherIdentify;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

}
