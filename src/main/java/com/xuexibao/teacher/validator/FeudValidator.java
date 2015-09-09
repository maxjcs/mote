package com.xuexibao.teacher.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FeudValidator {
	@SuppressWarnings("rawtypes")
	
	public static String validateGenFeudQuest(String genToken,int genNum) {
		String msg = null;
		if (StringUtils.isBlank(genToken)) {
			msg = "genToken 不能为空";
			return msg;
		}
		if (!("F4455CE8C4A45DB5FFB547F2650884FE9346EB989DC233D61E7242AA58BDB87AE907906D516F368B316F5066C5BFF04F54088D5FF8FE949A9B751B6BC52147F8B3B03BA4DB4317C4F689252E9B7106F6A2C9C3143A2A61B9A4CCB9056DC885E0".equals(genToken))) {
			msg = "genToken 错误";
			return msg;
		}
		if(genNum>5001||genNum<0){
			msg = "genNum 必须大于0且小于或等于500";
			return msg;
		}
	
		
		return null;
	}
	public static String validateStudentFeudQuest(String studentId,
			String questRealId,String imageId) {

		String msg = null;
		if (StringUtils.isBlank(studentId)) {
			msg = "studentId 不能为空";
			return msg;
		}
		if (StringUtils.isBlank(questRealId)) {
			msg = "questRealId 不能为空";
			return msg;
		}if (StringUtils.isBlank(imageId)) {
			msg = "imageId 不能为空";
			return msg;
		}
		return null;
	}
	public static String validateStudentFeudQuestToTeacher(String studentId,
			String questRealId,String imageId,String teacherId) {

		String msg = null;
		if (StringUtils.isBlank(studentId)) {
			msg = "studentId 不能为空";
			return msg;
		}
		if (StringUtils.isBlank(questRealId)) {
			msg = "questRealId 不能为空";
			return msg;
		}if (StringUtils.isBlank(imageId)) {
			msg = "imageId 不能为空";
			return msg;
		}
		if (StringUtils.isBlank(teacherId)) {
			msg = "teacherId 不能为空";
			return msg;
		}
		return null;
	}
	public static String validateGetFeudQuestList(Integer pageNo){
		String msg = null;
		if (pageNo == null) {
			msg = "pageNo 不能为空";
			return msg;
		}
		if(pageNo<0){
			msg = "pageNo 不能小于0";
			return msg;
		}
		return null;
	}
	public static String validateGetNewFeudQuestList(Integer pageNo,Integer orderType){
		String msg = null;
		if (pageNo == null) {
			msg = "pageNo 不能为空";
			return msg;
		}
		if(pageNo<0){
			msg = "pageNo 不能小于0";
			return msg;
		}
		if (orderType == null) {
			orderType = 0;
		}
		if(orderType!=0 && orderType!=1){
			msg = "orderType 类型只能为0或者1 ";
			return msg;
		}
		return null;
	}
	
	public static String validateCompleteFeudList(Integer pageNo){
		String msg = null;
		if (pageNo == null) {
			msg = "pageNo 不能为空";
			return msg;
		}
		if(pageNo<0){
			msg = "pageNo 不能小于0";
			return msg;
		}
		return null;
	}

	public static String validateGetFeudQuestDetail(String feudQuestId) {
		String msg = null;
		if (StringUtils.isBlank(feudQuestId)) {
			msg = "feudQuestId 不能为空";
			return msg;
		}
		return null;
	}

	public static String validateEnterFeud(String feudQuestId) {
		String msg = null;
		if (StringUtils.isBlank(feudQuestId)) {
			msg = "feudQuestId 不能为空";
			return msg;
		}
		return null;
	}

	public static String validateSubmitFeud(String feudQuestId,
			String submitType, String feudType, MultipartFile file,String feudAnswerDetailId,String duration) {
		String msg = null;
		if (StringUtils.isBlank(feudQuestId)) {
			msg = "feudQuestId 不能为空";
			return msg;
		}
		if (StringUtils.isBlank(submitType)
				) {
			msg = "submitType 不能为空";
			return msg;
		}
		if (StringUtils.isBlank(duration)
				) {
			msg = "duration 不能为空";
			return msg;
		}
		
		if ((!submitType.equals("0")&&!submitType.equals("-1"))) {
			msg = "submitType 类型只能是0或者-1";
			return msg;
		}
		if (StringUtils.isBlank(feudType)) {
			msg = "feudType 不能为空";
			return msg;
		}
		if (!feudType.equals("1") && !feudType.equals("2")) {
			msg = "feudType 类型只能是1或者2";
			return msg;
		}
		if (file == null&&submitType.equals("0")) {
			msg = "必须上传音频或白板";
			return msg;
		}
		
		if (StringUtils.isBlank(feudAnswerDetailId)) {
			msg = "feudAnswerDetailId 不能为空";
			return msg;
		}
		return null;
	}
	public static String validateCompleteFeudDetail(String feudAnswerDetailId){
		String msg = null;
		if (StringUtils.isBlank(feudAnswerDetailId)) {
			msg = "feudAnswerDetailId 不能为空";
			return msg;
		}
		return null;
	}
	
}
