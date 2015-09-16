/**
 * 
 */
package com.longcity.manage.model.param;

import com.longcity.manage.model.vo.MoteVO;
import com.longcity.manage.model.vo.QueryBaseVO;

/**
 * @author maxjcs
 *
 */
public class QueryMoteParamVO extends QueryBaseVO<MoteVO>{
	
	String registerBegin;
	
	String registerEnd;
	
	Integer registerSort;
	
	String nickname;
	
	Integer gender;
	
	Integer status;
	
	Integer ageBegin;
	
	Integer ageEnd;
	
	Integer heigthBegin;
	
	Integer heigthEnd;
	
	Integer shape;
	
	String areaIds;
	
	Integer remindFeeBegin;
	
	Integer remindFeeEnd;
	
	Integer remindFeeSort;
	
	Integer moteTaskFeeBegin;
	
	Integer moteTaskFeeEnd;
	
	Integer moteTaskFeeSort;
	
	public String getRegisterBegin() {
		return registerBegin;
	}

	public void setRegisterBegin(String registerBegin) {
		this.registerBegin = registerBegin;
	}

	public String getRegisterEnd() {
		return registerEnd;
	}

	public void setRegisterEnd(String registerEnd) {
		this.registerEnd = registerEnd;
	}

	public Integer getRegisterSort() {
		return registerSort;
	}

	public void setRegisterSort(Integer registerSort) {
		this.registerSort = registerSort;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAgeBegin() {
		return ageBegin;
	}

	public void setAgeBegin(Integer ageBegin) {
		this.ageBegin = ageBegin;
	}

	public Integer getAgeEnd() {
		return ageEnd;
	}

	public void setAgeEnd(Integer ageEnd) {
		this.ageEnd = ageEnd;
	}

	public Integer getHeigthBegin() {
		return heigthBegin;
	}

	public void setHeigthBegin(Integer heigthBegin) {
		this.heigthBegin = heigthBegin;
	}

	public Integer getHeigthEnd() {
		return heigthEnd;
	}

	public void setHeigthEnd(Integer heigthEnd) {
		this.heigthEnd = heigthEnd;
	}

	public Integer getShape() {
		return shape;
	}

	public void setShape(Integer shape) {
		this.shape = shape;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public Integer getRemindFeeBegin() {
		return remindFeeBegin;
	}

	public void setRemindFeeBegin(Integer remindFeeBegin) {
		this.remindFeeBegin = remindFeeBegin;
	}

	public Integer getRemindFeeEnd() {
		return remindFeeEnd;
	}

	public void setRemindFeeEnd(Integer remindFeeEnd) {
		this.remindFeeEnd = remindFeeEnd;
	}

	public Integer getRemindFeeSort() {
		return remindFeeSort;
	}

	public void setRemindFeeSort(Integer remindFeeSort) {
		this.remindFeeSort = remindFeeSort;
	}

	public Integer getMoteTaskFeeBegin() {
		return moteTaskFeeBegin;
	}

	public void setMoteTaskFeeBegin(Integer moteTaskFeeBegin) {
		this.moteTaskFeeBegin = moteTaskFeeBegin;
	}

	public Integer getMoteTaskFeeEnd() {
		return moteTaskFeeEnd;
	}

	public void setMoteTaskFeeEnd(Integer moteTaskFeeEnd) {
		this.moteTaskFeeEnd = moteTaskFeeEnd;
	}

	public Integer getMoteTaskFeeSort() {
		return moteTaskFeeSort;
	}

	public void setMoteTaskFeeSort(Integer moteTaskFeeSort) {
		this.moteTaskFeeSort = moteTaskFeeSort;
	}

}
