package com.xuexibao.teacher.model;

import java.io.Serializable;

/**
 * 
 * @author oldlu
 *
 */
public class PointOffline implements Serializable  {
	private static final long serialVersionUID = 8286152224720268491L;
	
	private int id;
	private String name;
	private String description;
	private int money;
	private int point;
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description=description;
	}
	
	public int getMoney(){
		return money;
	}
	
	public void setMoney(int money){
		this.money=money;
	}
	
	public int getPoint(){
		return point;
	}
	
	public void setPoint(int point){
		this.point=point;
	}
	
}
