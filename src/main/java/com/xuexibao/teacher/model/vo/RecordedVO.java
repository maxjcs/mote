package com.xuexibao.teacher.model.vo;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import com.xuexibao.teacher.enums.AudioStatus;
import com.xuexibao.teacher.enums.PlanFactory;
import com.xuexibao.teacher.model.iter.IAudioBuyCount;
import com.xuexibao.teacher.model.iter.IAudioPoint;
import com.xuexibao.teacher.model.iter.IAudioTotalPrice;

public class RecordedVO implements IAudioBuyCount, IAudioPoint,IAudioTotalPrice {

	private String id;
	private String latex;
	private String learnPhase;
	private String subjectText;
	private long questionId;
	private Date createTime;
	private String createTimeStr;
	private int status;
	private int evalution;
	private long orgId;

	private boolean offline;
	private int saleNum;
	private int totalPoint;

	private int income;

	private boolean showPlanAPointFee;
	private String planType;

	public boolean isShowPlanAPointFee() {
		return !StringUtils.equals(PlanFactory.PLAN_A, planType);
	}

	public void setShowPlanAPointFee(boolean showPlanAPointFee) {
		this.showPlanAPointFee = showPlanAPointFee;
	}

	public int getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public boolean isOffline() {
		return status == AudioStatus.offline.getValue();
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLatex() {
		return StringUtils.trim(latex);
	}

	public void setLatex(String latex) {
		this.latex = latex;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;

		if (createTime != null) {
			createTimeStr = DATE_FORMAT.format(createTime);
		}
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSubjectText() {
		return subjectText;
	}

	public void setSubjectText(String subjectText) {
		this.subjectText = subjectText;
	}

	public String getLearnPhase() {
		return learnPhase;
	}

	public void setLearnPhase(String learnPhase) {
		this.learnPhase = learnPhase;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public int getEvalution() {
		return evalution;
	}

	public void setEvalution(int evalution) {
		this.evalution = evalution;
	}

	@Override
	public String getAudioId() {
		return id;
	}
}
