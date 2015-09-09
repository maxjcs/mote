package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vdurmont.emoji.EmojiParser;
import com.xuexibao.teacher.constant.AppVersion;
import com.xuexibao.teacher.enums.TeacherIdentify;

public class Teacher implements Serializable {
	private static final long serialVersionUID = 3220024053005683170L;

	public static final int COMPLETE_NEW_USER_TASK_DOWN = 1;
	public static final int NOT_COMPLETE_NEW_USER_TASK = 0;

	public static final String ONLINE_STATUS_N = "N";
	public static final String ONLINE_STATUS_Y = "Y";

	public static final int CAPACITY_TEST_IS_JOINED = 1;
	@JsonIgnore
	private String verifyCode;
	@JsonIgnore
	private int undoTaskDays;
	@JsonIgnore
	private MultipartFile idCardFile;
	@JsonIgnore
	private MultipartFile avatarFile;
	@JsonIgnore
	private MultipartFile studentCardFile;
	private List<Integer> gradeIds;
	private List<Integer> subjectIds;

	@JsonIgnore
	private String id;
	private String name;
	private String phoneNumber;
	@JsonIgnore
	private String password;
	private String idNumber;
	private String qq;
	private String weixin;
	private int schoolId;
	private String idCardImageUrl;
	private String studentCardImageUrl;
	private int status;
	private int provinceId;
	private int cityId;
	private Date createTime;
	private Date updateTime;
	private String avatarUrl;
	private Integer gender;
	private Integer courseYear;
	private String selfDescription;
	private Integer teacherIdentify;
	private String qrcodeUrl;
	private String subjects;
	private String grades;
	private String bankCard;
	private String bank;
	private String alipay;
	private String courseTime;
	private String courseArea;
	private int mingshihui;
	private String xingjihua;
	private String operator;
	private int completeNewUserTask;
	private String iosToken;

	private String lastDeviceId;
	private String lastDeviceType;
	private String onlineStatus;
	private long curOrgId;

	private int capacityTestStatus;
	private Date capacityTestCompleteTime;
	private int capacityTestGivePoint;
	private int capacityTestIsJoined;
	private String nickname;
	private String bankUserName;
	private String cityName;
	private String schoolName;
	private String planType;

	private Integer teacherStar;

	@JsonIgnore
	public Integer getTeacherStar() {
		return teacherStar;
	}

	public void setTeacherStar(Integer teacherStar) {
		this.teacherStar = teacherStar;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getBankUserName() {
		return bankUserName;
	}

	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}

	public String getIosToken() {
		return iosToken;
	}

	public void setIosToken(String iosToken) {
		this.iosToken = iosToken;
	}

	public int getCapacityTestStatus() {
		return capacityTestStatus;
	}

	public void setCapacityTestStatus(int capacityTestStatus) {
		this.capacityTestStatus = capacityTestStatus;
	}

	public Date getCapacityTestCompleteTime() {
		return capacityTestCompleteTime;
	}

	public void setCapacityTestCompleteTime(Date capacityTestCompleteTime) {
		this.capacityTestCompleteTime = capacityTestCompleteTime;
	}

	public int getCapacityTestGivePoint() {
		return capacityTestGivePoint;
	}

	public void setCapacityTestGivePoint(int capacityTestGivePoint) {
		this.capacityTestGivePoint = capacityTestGivePoint;
	}

	public int getCapacityTestIsJoined() {
		return capacityTestIsJoined;
	}

	public void setCapacityTestIsJoined(int capacityTestIsJoined) {
		this.capacityTestIsJoined = capacityTestIsJoined;
	}

	public long getCurOrgId() {
		return curOrgId;
	}

	public void setCurOrgId(long curOrgId) {
		this.curOrgId = curOrgId;
	}

	@JsonIgnore
	public boolean isPerfectInfo10() {// 1.0版本
		return !StringUtils.isEmpty(name) && gender != null && !StringUtils.isEmpty(idNumber) && teacherIdentify != null
				&& courseYear != null && !CollectionUtils.isEmpty(gradeIds) && !CollectionUtils.isEmpty(subjectIds)
				&& !StringUtils.isEmpty(selfDescription);
	}

	@JsonIgnore
	public boolean isPerfectInfo11() {// 1.1版本
		return !StringUtils.isEmpty(nickname) && gender != null && teacherIdentify != null && courseYear != null
				&& !CollectionUtils.isEmpty(gradeIds) && !CollectionUtils.isEmpty(subjectIds)
				&& !StringUtils.isEmpty(selfDescription);
	}

	@JsonIgnore
	public Object isPerfectInfo(String version) {
		if (AppVersion.VERSION_11.equals(version)) {
			return isPerfectInfo11();
		}
		return isPerfectInfo10();
	}

	@JsonIgnore
	public boolean isOnline() {
		return StringUtils.equals(onlineStatus, "Y");
	}

	@JsonIgnore
	public boolean isOrgTeacher() {
		return curOrgId > 0;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getIdCardImageUrl() {
		return idCardImageUrl;
	}

	public void setIdCardImageUrl(String idCardImageUrl) {
		this.idCardImageUrl = idCardImageUrl;
	}

	public String getStudentCardImageUrl() {
		return studentCardImageUrl;
	}

	public void setStudentCardImageUrl(String studentCardImageUrl) {
		this.studentCardImageUrl = studentCardImageUrl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
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

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getCourseYear() {
		return courseYear;
	}

	public void setCourseYear(Integer courseYear) {
		this.courseYear = courseYear;
	}

	public String getSelfDescription() {
		return selfDescription;
	}

	@JsonIgnore
	public String getEmojiSelfDescription() {
		if (StringUtils.isEmpty(selfDescription)) {
			return "";
		}
		return EmojiParser.parseToUnicode(selfDescription);
	}

	public void setSelfDescription(String selfDescription) {
		this.selfDescription = selfDescription;
	}

	public Integer getTeacherIdentify() {
		return teacherIdentify;
	}

	public void setTeacherIdentify(Integer teacherIdentify) {
		this.teacherIdentify = teacherIdentify;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public String getSubjects() {
		return subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}

	public String getGrades() {
		return grades;
	}

	public void setGrades(String grades) {
		this.grades = grades;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}

	public String getCourseArea() {
		return courseArea;
	}

	public void setCourseArea(String courseArea) {
		this.courseArea = courseArea;
	}

	public int getMingshihui() {
		return mingshihui;
	}

	public void setMingshihui(int mingshihui) {
		this.mingshihui = mingshihui;
	}

	public String getXingjihua() {
		return xingjihua;
	}

	public void setXingjihua(String xingjihua) {
		this.xingjihua = xingjihua;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public MultipartFile getIdCardFile() {
		return idCardFile;
	}

	public void setIdCardFile(MultipartFile idCardFile) {
		this.idCardFile = idCardFile;
	}

	public MultipartFile getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(MultipartFile avatarFile) {
		this.avatarFile = avatarFile;
	}

	public MultipartFile getStudentCardFile() {
		return studentCardFile;
	}

	public void setStudentCardFile(MultipartFile studentCardFile) {
		this.studentCardFile = studentCardFile;
	}

	public List<Integer> getGradeIds() {
		return gradeIds;
	}

	public void setGradeIds(List<Integer> gradeIds) {
		this.gradeIds = gradeIds;
	}

	public List<Integer> getSubjectIds() {
		return subjectIds;
	}

	public void setSubjectIds(List<Integer> subjectIds) {
		this.subjectIds = subjectIds;
	}

	public int getUndoTaskDays() {
		return undoTaskDays;
	}

	public void setUndoTaskDays(int undoTaskDays) {
		this.undoTaskDays = undoTaskDays;
	}

	public int getCompleteNewUserTask() {
		return completeNewUserTask;
	}

	public void setCompleteNewUserTask(int completeNewUserTask) {
		this.completeNewUserTask = completeNewUserTask;
	}

	public String getLastDeviceId() {
		return lastDeviceId;
	}

	public void setLastDeviceId(String lastDeviceId) {
		this.lastDeviceId = lastDeviceId;
	}

	public String getLastDeviceType() {
		return lastDeviceType;
	}

	public void setLastDeviceType(String lastDeviceType) {
		this.lastDeviceType = lastDeviceType;
	}

	@JsonIgnore
	public boolean isTeacher() {
		return teacherIdentify != null && teacherIdentify == TeacherIdentify.teacher.getValue();
	}

	public boolean isJoinCapacityTest() {
		return completeNewUserTask == COMPLETE_NEW_USER_TASK_DOWN || capacityTestIsJoined == CAPACITY_TEST_IS_JOINED;
	}

	public String getNickname() {
		return nickname;
	}

	@JsonIgnore
	public String getWrapperNickname() {
		if (StringUtils.isEmpty(nickname)) {
			return name;
		}

		return nickname;
	}

	@JsonIgnore
	public String getWrapperName() {
		if (StringUtils.isEmpty(name)) {
			return nickname;
		}
		return name;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isBindBank() {
		return !StringUtils.isEmpty(bank) && !StringUtils.isEmpty(bankCard);
	}
}
