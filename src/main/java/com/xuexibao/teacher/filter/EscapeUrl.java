package com.xuexibao.teacher.filter;

import java.util.HashSet;
import java.util.Set;

public class EscapeUrl {

	public static Set<String> escapeUrls = new HashSet<String>();

	static {
		escapeUrls.add("/user/sendVerifyCode"); // 发送验证码
		escapeUrls.add("/user/sendRegisterVerifyCode"); // 发送验证码
		escapeUrls.add("/user/register"); // 注册
		escapeUrls.add("/user/login"); // 登录
		escapeUrls.add("/user/changePasswordByVerifyCode"); // 重置密码
		escapeUrls.add("/user/getDeviceId"); // 获取设备号
		escapeUrls.add("/user/changeStar"); // 修改星级
		escapeUrls.add("/user/getOrgListByCityId"); //
		escapeUrls.add("/user/loginReact"); // 登录
		
		//字典相关
		escapeUrls.add("/dict/getGenderList"); 
		escapeUrls.add("/dict/getTeacherIdentifyList"); 
		escapeUrls.add("/dict/getTeachingTimeList");
		escapeUrls.add("/dict/getProvinceList");
		escapeUrls.add("/dict/getCityList");
		escapeUrls.add("/dict/getGradeList");
		escapeUrls.add("/dict/getSubjectListByGrade");
		escapeUrls.add("/dict/getSchoolListByProvId");
		escapeUrls.add("/dict/getGradeSubjectList");
		escapeUrls.add("/dict/getSubjectList");
		//学生端使用的
		escapeUrls.add("/student/getProvinceList");
		escapeUrls.add("/student/getCityList");
		escapeUrls.add("/student/getGradeList");
		escapeUrls.add("/student/getSubjectListByGrade");
		escapeUrls.add("/student/getSchoolListByProvId");
		escapeUrls.add("/student/getGradeSubjectList");
		escapeUrls.add("/student/getSubjectList");
		escapeUrls.add("/student/getUserById");
		
		//名师关注，未关注
		escapeUrls.add("/follow/add");
		escapeUrls.add("/follow/cancel");
		escapeUrls.add("/follow/addAll");
		escapeUrls.add("/student/getRecommTeacher");
		escapeUrls.add("/follow/getFollowListByUserId");
		escapeUrls.add("/user/getTeacherById");
		escapeUrls.add("/student/getTeacherByStudentId");
		escapeUrls.add("/student/queryTeacher");
		escapeUrls.add("/student/queryUnFollowedTeachers");
		escapeUrls.add("/student/queryTeacherWithFollow");
		escapeUrls.add("/student/queryTeacherWithFollow2");
		escapeUrls.add("/student/queryAudiosByQuestionId");
		escapeUrls.add("/student/queryTeacherWithUserId");
		escapeUrls.add("/follow/getTotalFollowedByUserId");
		escapeUrls.add("/student/evaluateAudio");
		escapeUrls.add("/student/listAudioSet");
		
		escapeUrls.add("/student/listAudioSetByIds");
		escapeUrls.add("/student/getAudioSetById");
		escapeUrls.add("/student/listAudioBySetId");
		escapeUrls.add("/student/getAudioSetMoney");
		escapeUrls.add("/student/getAudioSetMoneyV13");
		escapeUrls.add("/student/queryAudioSetBySortType");
		escapeUrls.add("/student/getAudioOrgTeacherMoney");
		escapeUrls.add("/student/queryAudioSetByAudioIds");
		
		escapeUrls.add("/audioset/listAudioSetByTeacherId");
		
		
		escapeUrls.add("/push/pushAudioWtOnline"); 
		
		//附近的老师
		escapeUrls.add("/teacherLocation/getNearTeacherList");
		escapeUrls.add("/teacherLocation/getNearTeacherCount");
//		
//		//friend 接口，中转客户端请求，不要验证token
//		escapeUrls.add("/friend/tutorRecord"); 
		escapeUrls.add("/student/getAudioMoney"); 
		escapeUrls.add("/student/getAudioMoneyV13"); 
		escapeUrls.add("/student/queryWbDownloadUrl"); 
		escapeUrls.add("/feud/studentFeudQuest");
		escapeUrls.add("/feud/studentFeudQuestToTeacher");
		escapeUrls.add("/feud/genFeudQuest");
		escapeUrls.add("/feud/testDB");
		escapeUrls.add("/learnTalk/studentSendMsgWithoutToken");
		escapeUrls.add("/learnTalk/learnTalkStudentList");
		escapeUrls.add("/learnTalk/learnTalkStudentDetail");
		escapeUrls.add("/learnTalk/learnTalkStudentUpdate");
		escapeUrls.add("/learnTalk/learnTalkStudentRecentMessage");
		escapeUrls.add("/learnTalk/getQuestionIdForStudent");
		escapeUrls.add("/learnTalk/queryWbDownloadUrlsForStudent");
		escapeUrls.add("/version/getLastVersionInfo");
		
		escapeUrls.add("/learnTalk/queryIsHudong");
		escapeUrls.add("/teacherMessage/addTeacherMessageForStudent");
		escapeUrls.add("/teacherMessage/getTeacherMessageForStudent");
		escapeUrls.add("/teacherMessage/getTeacherMessageForH5");
		escapeUrls.add("/teacherMessage/summaryTeacherMessageForStudent");
		escapeUrls.add("/teacherMessage/removeStudentMessage");
		escapeUrls.add("/teacherMessage/firstPageTeacherMessageForStudent");
		escapeUrls.add("/teacherMessage/nextPageTeacherMessageForStudent");
		escapeUrls.add("/teacherMessage/firstPageReplyMessageForStudent");
		escapeUrls.add("/teacherMessage/nextPageReplyMessageForStudent");
		escapeUrls.add("/teacherMessage/summaryTeacherMessageForH5");
		
		
		//测试系统各组件是否OK
		escapeUrls.add("/sysStatus/sysStatusList");
		//白板转视频接收文件上传test
		escapeUrls.add("/whiteBoard/wb2videoUpload");
		//白板转视频成功后，第三方系统通知教师系统
		escapeUrls.add("/whiteBoard/wbVideoUrl");
		
		
		
		
		//分享音频
		escapeUrls.add("/question/shareAudioDetail");
		
		escapeUrls.add("/errorquest/getErrorQuestList");
		escapeUrls.add("/errorquest/updateQuestReadStatus");
		escapeUrls.add("/errorquest/audioErrorQuests");
		escapeUrls.add("/dynamic/loadDynamicForH5");
		escapeUrls.add("/dynamicmessage/listMessageForH5");
		
		
		escapeUrls.addAll(StudentApiUrl.apiUrls);
	}
}
