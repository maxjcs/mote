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
	
	Integer registerSort=0;
	
	String nickname;
	
	String shopName;
	
	String referee;
	
	Integer status;
	
	Integer projectNumBegin;
	
	Integer projectNumEnd;
	
	Integer projectNumSort=0;
	
	Integer moteTaskNumBegin;
	
	Integer moteTaskNumEnd;
	
	Integer moteTaskNumSort=0;
	
	Integer remindFeeBegin;
	
	Integer remindFeeEnd;
	
	Integer remindFeeSort=0;
	
	Integer moteTaskFeeBegin;
	
	Integer moteTaskFeeEnd;
	
	Integer moteTaskSort=0;

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

	public Integer getProjectNumSort() {
		return projectNumSort;
	}

	public void setProjectNumSort(Integer projectNumSort) {
		this.projectNumSort = projectNumSort;
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

	public Integer getMoteTaskNumSort() {
		return moteTaskNumSort;
	}

	public void setMoteTaskNumSort(Integer moteTaskNumSort) {
		this.moteTaskNumSort = moteTaskNumSort;
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

	public Integer getMoteTaskSort() {
		return moteTaskSort;
	}

	public void setMoteTaskSort(Integer moteTaskSort) {
		this.moteTaskSort = moteTaskSort;
	}
	
}
