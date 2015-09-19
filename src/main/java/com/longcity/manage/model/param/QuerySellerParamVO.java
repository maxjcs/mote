/**
 * 
 */
package com.longcity.manage.model.param;

import com.longcity.manage.model.vo.QueryBaseVO;
import com.longcity.manage.model.vo.SellerVO;

/**
 * @author maxjcs
 *
 */
public class QuerySellerParamVO extends QueryBaseVO<SellerVO>{
	
	String registerBegin;
	
	String registerEnd;
	
	String nickname;
	
	String shopName;
	
	String referee;
	
	Integer status;
	
	Integer projectNumBegin;
	
	Integer projectNumEnd;
	
	Integer moteTaskNumBegin;
	
	Integer moteTaskNumEnd;
	
	Integer remindFeeBegin;
	
	Integer remindFeeEnd;
	
	Integer moteTaskFeeBegin;
	
	Integer moteTaskFeeEnd;
	
	Integer sort;
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getReferee() {
		return referee;
	}

	public void setReferee(String referee) {
		this.referee = referee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getProjectNumBegin() {
		return projectNumBegin;
	}

	public void setProjectNumBegin(Integer projectNumBegin) {
		this.projectNumBegin = projectNumBegin;
	}

	public Integer getProjectNumEnd() {
		return projectNumEnd;
	}

	public void setProjectNumEnd(Integer projectNumEnd) {
		this.projectNumEnd = projectNumEnd;
	}

	public Integer getMoteTaskNumBegin() {
		return moteTaskNumBegin;
	}

	public void setMoteTaskNumBegin(Integer moteTaskNumBegin) {
		this.moteTaskNumBegin = moteTaskNumBegin;
	}

	public Integer getMoteTaskNumEnd() {
		return moteTaskNumEnd;
	}

	public void setMoteTaskNumEnd(Integer moteTaskNumEnd) {
		this.moteTaskNumEnd = moteTaskNumEnd;
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

}
