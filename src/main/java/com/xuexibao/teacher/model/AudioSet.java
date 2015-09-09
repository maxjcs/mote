package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.xuexibao.teacher.common.GradeUtil;
import com.xuexibao.teacher.model.iter.IAudioSetBuyCount;
import com.xuexibao.teacher.model.iter.IAudioSetBuyStatus;
import com.xuexibao.teacher.model.iter.IAudioSetCount;
import com.xuexibao.teacher.model.iter.IAudioSetEvaluateCount;
import com.xuexibao.teacher.model.iter.IAudioSetStudentComment;
import com.xuexibao.teacher.model.rpcvo.AudioSetStudentComment;

/**
 * 
 * @author oldlu
 * 
 */
public class AudioSet implements Serializable, IAudioSetBuyCount, IAudioSetCount, IAudioSetBuyStatus,
		IAudioSetEvaluateCount, IAudioSetStudentComment {
	private static final long serialVersionUID = 8286152224720268491L;
	public static final int SORT_TYPE_XL_DESC = 1;
	public static final int SORT_TYPE_STAR_DESC = 2;
	public static final int SORT_TYPE_PRICE_DESC = 3;
	public static final int SORT_TYPE_PRICE_ASC = 4;
	public static final Integer IS_FREE_YES = 1;
	public static final Integer IS_FREE_NO = 0;
	private String id;
	private Integer price;
	private int countSales;
	private String name;
	private String teacherId;
	private Date createTime;
	private Date updateTime;
	private List<AudioSetDetail> items;
	private List<String> audioIds;
	private int countAudio;
	private boolean buyStatus;
	private Integer status;
	private String description;
	private int orderNo;
	private int topTag;

	private int goodCounts;
	private int midCounts;
	private int badCounts;

	private String gradeIds;
	private String subjectIds;
	private Integer isFree;
	//
	// private String teacherName;
	// private String phoneNumber;
	// private String teacherNickName;
	// private String teacherAvatarUrl;
	// private Integer teacherGender;
	//

	private AudioSetStudentComment.Item studentComment;

	public Integer getIsFree() {
		return isFree;
	}

	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}

	public AudioSetStudentComment.Item getStudentComment() {
		return studentComment;
	}

	public void setStudentComment(AudioSetStudentComment.Item studentComment) {
		this.studentComment = studentComment;
	}

	public String getGradeIds() {
		return GradeUtil.reverseGrade(gradeIds);
	}

	public void setGradeIds(String gradeIds) {
		this.gradeIds = gradeIds;
	}

	public String getSubjectIds() {
		return subjectIds;
	}

	public void setSubjectIds(String subjectIds) {
		this.subjectIds = subjectIds;
	}

	public int getGoodCounts() {
		return goodCounts;
	}

	public void setGoodCounts(int goodCounts) {
		this.goodCounts = goodCounts;
	}

	public int getMidCounts() {
		return midCounts;
	}

	public void setMidCounts(int midCounts) {
		this.midCounts = midCounts;
	}

	public int getBadCounts() {
		return badCounts;
	}

	public void setBadCounts(int badCounts) {
		this.badCounts = badCounts;
	}

	public int getTopTag() {
		return topTag;
	}

	public void setTopTag(int topTag) {
		this.topTag = topTag;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getCountAudio() {
		return countAudio;
	}

	public void setCountAudio(int countAudio) {
		this.countAudio = countAudio;
	}

	public List<String> getAudioIds() {
		return audioIds;
	}

	public void setAudioIds(List<String> audioIds) {
		this.audioIds = audioIds;
	}

	public int getCountSales() {
		return countSales;
	}

	public void setCountSales(int countSales) {
		this.countSales = countSales;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
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

	public List<AudioSetDetail> getItems() {
		return items;
	}

	public void setItems(List<AudioSetDetail> items) {
		this.items = items;
	}

	@JsonIgnore
	public String getSetId() {
		return id;
	}

	public boolean isBuyStatus() {
		return buyStatus;
	}

	public void setBuyStatus(boolean buyStatus) {
		this.buyStatus = buyStatus;
	}

	public void setAudioSetCount(int count) {
		this.countAudio = count;
	}

}
