package com.xuexibao.teacher.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.xuexibao.teacher.constant.RedisKeys;
import com.xuexibao.teacher.core.RedisOps;
import com.xuexibao.teacher.dao.FeudDetailWbDao;
import com.xuexibao.teacher.dao.FeudQuestDao;
import com.xuexibao.teacher.enums.AudioSource;
import com.xuexibao.teacher.enums.AudioStatus;
import com.xuexibao.teacher.enums.AudioWbType;
import com.xuexibao.teacher.enums.WhiteBoradVersion;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.FeudDetailWb;
import com.xuexibao.teacher.model.FeudQuest;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.vo.FeudTeacherIsFree;
import com.xuexibao.teacher.model.vo.FinishFeudAnswerDetailVO;
import com.xuexibao.teacher.model.vo.WaitFeudListVO;
import com.xuexibao.teacher.util.FileUtil;
import com.xuexibao.teacher.util.PropertyUtil;
import com.xuexibao.webapi.student.model.User;

@Service
@Transactional
public class CommonFeudService {

	@Resource
	private FeudQuestDao feudQuestDao;
	@Resource
	private RedisOps redisOps;
	@Resource
	private TeacherService teacherService;
	@Resource
	private StudentApiService studentApiService;
	@Resource
	protected QuestionService questionService;
	@Resource 
	private Wb2videoService wb2videoService;
	@Resource
	protected FeudDetailWbDao feudDetailWbDao;
	
	// 抢答队列大小
	int feudQueueSize = new Integer(PropertyUtil.getProperty("feudQueueSize"));
	//老师1小时内抢答超过一定数量时为繁忙，不响应学生解答请求
	int is_free_for_feud_count = new Integer(PropertyUtil.getProperty("is_free_for_feud_count"));
	String toVideoPostUrl = PropertyUtil.getProperty("toVideoPostUrl");
	String toVideoCallBackUrl = PropertyUtil.getProperty("toVideoCallBackUrl");
	private final String wbZipFileSuffix = "_wb.zip";
	//发起抢答的系统学生ID
	private final String feudSystemStudentId = "studentId:system";

	private static Logger logger = LoggerFactory
			.getLogger(CommonFeudService.class);

	/**
	 * 首页获取待抢答数
	 * 
	 * @return
	 */
	public int getFeudQuestCountForPageHome(String teacherId) {
		Teacher teacher = teacherService.getTeacher(teacherId);
		List<Integer> subids = teacher.getSubjectIds();
		if (subids.size() <= 0) {
			logger.error("teacher subject is null");
			return 0;
		}
		Map paramMap = new HashMap();
		paramMap.put("subjectIds", subids);
		paramMap.put("teacherId", teacherId);
		
	    List<WaitFeudListVO>  fqs  = feudQuestDao.getHomeFeudQuestList(paramMap);
		List<WaitFeudListVO> resultList = new ArrayList<WaitFeudListVO>();
		for (WaitFeudListVO vo : fqs) {
			String feudQueueKey = RedisKeys.feudQueueKey + vo.getFeudQuestId();
			long size = redisOps.getSetSize(feudQueueKey);
			if (size < feudQueueSize) {
				resultList.add(vo);
			}
		}
		
		return resultList.size();
	}
	
	
	/**
	 * 老师在当前时间是否有空对学生端做解答
	 * @param teacherIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  List<FeudTeacherIsFree> getFeudTeachersIsFree(Set<String> teacherIds){
	   if(teacherIds.size()<=0){
		   return new ArrayList();
	   }
	   if(teacherIds.size()>50){
		   throw new BusinessException("teacherIds is more than 50");
	   }
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("ids", teacherIds);
		List<FeudTeacherIsFree> teachers  = feudQuestDao.getFeudTeachersIsFree(map);
		for(FeudTeacherIsFree ff:teachers){
			if(ff.getFeudTotal()>is_free_for_feud_count){
				ff.setFree(false);
			}else{
				ff.setFree(true);
			}
		}
		return teachers;
	}
	/**
	 * 学生是否已经就一道题 对老师进行过请求讲解
	 * @param teacherIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  Map<String,Boolean> hasAskTeacherWithQuestion(String studentId,String questionId,Set<String> teacherIds){
		@SuppressWarnings("rawtypes")
		Map queryMap = new HashMap();
		queryMap.put("questionRealId", questionId);
		queryMap.put("studentId", studentId);
		
		Map<String,Boolean> resultMap = new HashMap<String,Boolean>();
		List<FeudQuest> feudQuests  = feudQuestDao.hasAskTeacherWithQuestion(queryMap);
		for(FeudQuest f:feudQuests){
			if(teacherIds.contains(f.getSourceTeacher())){
				resultMap.put(f.getSourceTeacher(), true);
			}
		}
	   for(Iterator<String> itr = teacherIds.iterator();itr.hasNext();){
		   String key = itr.next();
		   if(!resultMap.containsKey(key)){
			   resultMap.put(key, false); 
		   }
	   }
		return resultMap;
	}
	
	/**
	 * 当前老师定向请求数量
	 * @param teacherId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  int getDirectFeudWithTeacher(String teacherId){
		
		Teacher teacher = teacherService.getTeacher(teacherId);
		List<Integer> subids = teacher.getSubjectIds();
		if (subids.size() <= 0) {
			logger.error("teacher subject is null");
			return 0;
		}
		Map paramMap = new HashMap();
		paramMap.put("subjectIds", subids);
		paramMap.put("teacherId", teacherId);
		
		return feudQuestDao.getDirectFeudWithTeacher(paramMap);
	}
	public void setWaitFeudStudentInfo(List<WaitFeudListVO> waitFeudList,Set<String> usersSet){
		if(waitFeudList==null||usersSet ==null){
			return ;
		}
		//显示学生头像及昵称
		HashMap<String, User> studentsMap = studentApiService.getUsersInfo(usersSet);
		for (WaitFeudListVO vo : waitFeudList) {
			User user = studentsMap.get(vo.getStudentId());
			if (user != null) {
				String avatarUrl = user.getProfile_image_url();
				vo.setStudentAvatarUrl(avatarUrl);
				String loginname =user.getLoginname();
				vo.setStudentNick(loginname);
			}
		}
	}
	public void setFinishFeudStudentInfo(List<FinishFeudAnswerDetailVO> fdvs,Set<String> usersSet){
        if(fdvs == null || usersSet == null){
        	return ;
        }
      //显示学生头像及昵称
		HashMap<String, User> studentsMap = studentApiService.getUsersInfo(usersSet);
		for (FinishFeudAnswerDetailVO vo : fdvs) {
			vo.setStudentArea("北京");
			User user = studentsMap.get(vo.getStudentId());
			if (user != null) {
				String avatarUrl = user.getProfile_image_url();
				vo.setStudentAvatarUrl(avatarUrl);
				String loginname = user.getLoginname();
				vo.setStudentNick(loginname);
			}
		}
	}
	public void setFeudAndDirectAudioState(Audio au,String audioWhiteboardId,int feudType,Long questionRealId,String fullName,String teacherId,long duration,int realSubject, int teacherStar,String planType){
		au.setId(audioWhiteboardId);
		au.setSource(AudioSource.feud.getValue());
		//设置抢答类型为抢答
		//au.setFeudType(AudioFeudTypeStatus.feud.getValue());
		au.setFeudType(feudType);
		//au.setQuestionId(fq.getQuestionRealId());
		au.setQuestionId(questionRealId);
		au.setName(fullName);
		au.setTeacherId(teacherId);
		au.setStatus(AudioStatus.checked.getValue());
		au.setDuration(duration);
		au.setSubject(realSubject);
		//设置当时老师的星级及是否是planB
	    au.setTeacherStar(teacherStar);
	    au.setPlanType(planType);
	}
	/**
	 * 提交白板时的处理逻辑
	 * @param au
	 * @param wbVersion
	 * @param uuidFile
	 * @param file
	 * @param audioWhiteboardId
	 * @param teacherId
	 * @param fullName
	 * @param feudAnswerDetailId
	 */

    public void submitProcessWithWhiteBoard(Audio au,String wbVersion,String uuidFile,MultipartFile file,String audioWhiteboardId,String teacherId,String fullName,long feudAnswerDetailId){
    	
		try {
			// 白板2.0 则白板转视频
			if (!StringUtils.isEmpty(wbVersion)
					&& new Integer(wbVersion) == WhiteBoradVersion.two
							.getValue()) {
				String fileNameWithZip = uuidFile + wbZipFileSuffix;
				File zipFile  = FileUtil.multipartFileCopyFile(file, fileNameWithZip);	
				//通知第三方系统做白板转视频操作 异步处理
				wb2videoService.uploadWBFile(toVideoPostUrl,
						zipFile, toVideoCallBackUrl,
						audioWhiteboardId,teacherId);
			}
		} catch (NumberFormatException e) {
			logger.error("wbVersion is not number:"
					,e);
		}  catch (IOException e2) {
			logger.error("IOException :"
					,e2);
		}	
		
		List<Map<String, String>> zipFiles = FileUtil.uploadZip(file,
				fullName);
		for (Map<String, String> s : zipFiles) {
			FeudDetailWb record = new FeudDetailWb();
			record.setFeudDetailId(feudAnswerDetailId);
			record.setFileUrl(s.get("fileUrl"));
			record.setFileType(s.get("fileType"));
			record.setWbId(audioWhiteboardId);
			try {
				record.setWbVersion(WhiteBoradVersion.one
						.getValue());
				if (!StringUtils.isEmpty(wbVersion)
						&& new Integer(wbVersion) == WhiteBoradVersion.two
								.getValue()) {
					record.setWbVersion(WhiteBoradVersion.two
							.getValue());
				}
			} catch (NumberFormatException e) {
				logger.error("wbVersion is not number:"
						,e);
			}
			au.setUrl(s.get("fileUrl"));
			feudDetailWbDao.insert(record);
		}
		// 白板
		au.setWbType(AudioWbType.whiteboard.getValue());
		au.setType(2);
		
	}
    public void notifyMessageToStudent(String strSubject,FeudQuest fq,int feudType,Audio au,String nickName){
    	StringBuilder sb = new StringBuilder();
		String studentId = fq.getStudentId();
		String questionRealId = fq.getQuestionRealId().toString();
		String imageId = fq.getImageId();
	//	String strSubject = q.getSubject();
		
		sb.append(studentId).append(":").append(imageId).append(":")
				.append(questionRealId).append(":").append(feudType)
				.append(":").append(au.getId());
		// 如果是系统生成的抢答则不需要通知
		if (!feudSystemStudentId.equals(studentId)) {
			studentApiService.pushAudioWtOnline(sb.toString(),
					nickName, strSubject);
		}
    }
	

}
