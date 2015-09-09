package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author oldlu
 * 
 */
public class Rating implements Serializable {
	private static final long serialVersionUID = 8286152224720268491L;

	private int id;
	private int star;
	private String name;
	private String description;
	private int points;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	private static Map<Integer, String> STATUS_MAP = new HashMap<Integer, String>();
	static {
		STATUS_MAP.put(1, "一");
		STATUS_MAP.put(2, "二");
		STATUS_MAP.put(3, "三");
		STATUS_MAP.put(4, "四");
		STATUS_MAP.put(5, "五");
	}
	public static String getRatingText(int star2) {
		return STATUS_MAP.get(star2);
	}
}
 
