/**
 * 
 */
package com.longcity.modeler.filter;

import java.util.HashSet;
import java.util.Set;

/**
 * 请求学生端的url，需要进行加密串校验
 * @author maxjcs
 *
 */
public class StudentApiUrl {
	
	public static Set<String> apiUrls = new HashSet<String>();

	static {
		
		//学生端使用的
		apiUrls.add("/student/getProvinceList");
		apiUrls.add("/student/getCityList");
		apiUrls.add("/student/getGradeList");
		apiUrls.add("/student/getSubjectListByGrade");
		apiUrls.add("/student/getSchoolListByProvId");
		apiUrls.add("/student/getGradeSubjectList");
		apiUrls.add("/student/getSubjectList");
		//名师关注，未关注
		apiUrls.add("/follow/add");
		apiUrls.add("/follow/addAll");
		apiUrls.add("/follow/cancel");
		apiUrls.add("/student/getRecommTeacher");
		apiUrls.add("/student/getUserById");
		apiUrls.add("/follow/getFollowListByUserId");
		apiUrls.add("/student/queryUnFollowedTeachers");
		apiUrls.add("/student/queryTeacherWithFollow");
		apiUrls.add("/student/queryTeacherWithFollow2");
		apiUrls.add("/student/queryAudiosByQuestionId");
		apiUrls.add("/student/queryTeacherWithUserId");
		apiUrls.add("/follow/getTotalFollowedByUserId");
		
		//附近的老师
		apiUrls.add("/teacherLocation/getNearTeacherList");
		apiUrls.add("/teacherLocation/getNearTeacherCount");
		
		apiUrls.add("/student/evaluateAudio");
		
		apiUrls.add("/push/pushAudioWtOnline"); 
		
		//friend 接口，中转客户端请求，不要验证token
		apiUrls.add("/student/getAudioMoney"); 
		apiUrls.add("/student/queryWbDownloadUrl"); 
	//	apiUrls.add("/feud/studentFeudQuest");
		//明宝
		apiUrls.add("/learnTalk/studentSendMsgWithoutToken");
		apiUrls.add("/learnTalk/learnTalkStudentList");
		apiUrls.add("/learnTalk/learnTalkStudentDetail");
		apiUrls.add("/learnTalk/learnTalkStudentUpdate");
		apiUrls.add("/learnTalk/learnTalkStudentRecentMessage");		
		apiUrls.add("/learnTalk/queryWbDownloadUrlsForStudent");
		apiUrls.add("/learnTalk/getQuestionIdForStudent");
		apiUrls.add("/learnTalk/queryVideoDownloadUrl");
		
		
		apiUrls.add("/student/dynamicupvote/addUpvote");
		apiUrls.add("/student/dynamicupvote/addSystemUpvote");
		apiUrls.add("/student/dynamicupvote/removeUpvote");
		apiUrls.add("/student/dynamicmessage/addMessage");
		apiUrls.add("/student/dynamicmessage/listMessage");
		apiUrls.add("/student/dynamicmessage/removeMessage");
		apiUrls.add("/student/dynamicmessage/systemRemoveMessage");
		apiUrls.add("/student/dynamicmessage/systemRestoreMessage");
		apiUrls.add("/student/dynamic/listDynamicByIds");
		apiUrls.add("/student/dynamic/removeDynamic");
		apiUrls.add("/student/dynamic/restoreDynamic");
		apiUrls.add("/teacherrecharge/getTeacherRewardOfMemberCharge");
		
		
		
	}
	
}
