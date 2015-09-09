/**
 * 
 */
package com.xuexibao.teacher.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.dao.AudioDao;
import com.xuexibao.teacher.dao.AudioUploadDao;
import com.xuexibao.teacher.dao.ErrorCorrectionDao;
import com.xuexibao.teacher.dao.ExplanationDao;
import com.xuexibao.teacher.dao.FeudDetailWbDao;
import com.xuexibao.teacher.dao.QuestionV2Dao;
import com.xuexibao.teacher.enums.AudioSource;
import com.xuexibao.teacher.enums.AudioStatus;
import com.xuexibao.teacher.enums.AudioType;
import com.xuexibao.teacher.enums.PlanFactory;
import com.xuexibao.teacher.enums.WhiteBoradVersion;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioEvalApprove;
import com.xuexibao.teacher.model.AudioUpload;
import com.xuexibao.teacher.model.ErrorCorrection;
import com.xuexibao.teacher.model.Explanation;
import com.xuexibao.teacher.model.FeudDetailWb;
import com.xuexibao.teacher.model.Organization;
import com.xuexibao.teacher.model.Question;
import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.rpcvo.AudioEvaluateCount;
import com.xuexibao.teacher.model.vo.ExplanationVO;
import com.xuexibao.teacher.pay.service.PayAudioService;
import com.xuexibao.teacher.service.evaluprocessor.explanation.ExplanationProcessor;
import com.xuexibao.teacher.util.DateUtils;
import com.xuexibao.teacher.util.FileUtil;
import com.xuexibao.teacher.util.PropertyUtil;
import com.xuexibao.teacher.util.StudentApiHttpUtil;
import com.xuexibao.webapi.teacher.client.T_DictService;
import com.xuexibao.webapi.teacher.model.Subject;

/**
 * @author maxjcs
 *
 */
@Service
public class ExplanationService {
	
	private static Logger logger = LoggerFactory.getLogger(ExplanationService.class);
	
	@Resource
	private ExplanationDao explanationDao;
	@Resource
	private AudioUploadDao audioUploadDao;
	@Resource
	private QuestionV2Dao questionV2Dao;
	@Resource
	private T_DictService t_DictService;
	@Resource
	private TeacherService teacherService;
	@Resource
	private QuestionService questionService;
	@Resource
	protected PayAudioService payAudioService;
	@Resource
	ExplanationProcessor explanationProcessor;
	@Resource
	private StudentApiService studentApiService;
	@Resource
	private AudioStudentEvaluationService audioStudentEvaluationService;
	
	@Resource
	private AudioUploadService audioUploadService;
	
	@Resource
	private OrganizationService organizationService;
	
	@Resource 
	private Wb2videoService wb2videoService;
	
	@Resource
	private AudioEvalApproveService audioEvalApproveService;
	
	@Resource
	protected AudioDao audioDao;
	
	@Resource
	private FeudDetailWbDao feudDetailWbDao;
	
	@Resource
	private ErrorCorrectionDao errorCorrectionDao;
	
	private static String paySystemUrl=PropertyUtil.getProperty("paySystemUrl");
	
	private static String toVideoPostUrl = PropertyUtil.getProperty("toVideoPostUrl");
	private static String toVideoCallBackUrl = PropertyUtil.getProperty("toVideoCallBackUrl");
	
	//获取总购买数和分成金额
	private String explainsPriceUriPath = "/api/teacher/getAudioTotalPriceAndNum";
	
	//获取总分成金额
	private String audioTotalPriceUriPath = "/api/teacher/audioTotalPrice";
	
    /**
     * 删除拍题
     * @param file
     * @return
     */
    public void delete(Long id)throws Exception{
    	Explanation explanation=explanationDao.selectByPrimaryKey(id);
    	if(explanation.getStatus().intValue()==2){
    		throw new BusinessException("explanationId=["+id+"]已经录制，不能删除！");
    	}
        explanationDao.deleteByPrimaryKey(id);
    }
    
    /**
     * 
     * @param teacherId
     * @param file
     * @param audioName
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
    public Integer uploadAudio(String teacherId,MultipartFile file,String imgId,Long questionRealId,Integer duration,int uploadType,String wbVersion) throws Exception{
    	
    	 Explanation explanation = explanationDao.queryByImgId(imgId);
    	 if(explanation.getStatus().intValue()==2){//已经录制完成
    		 return -1;
    	 }
    	
    	//如果已经上传过，直接返回。
    	Map paramMap=new HashMap();
    	paramMap.put("teacherId", teacherId);
    	paramMap.put("questionRealId",questionRealId);
    	if(audioUploadDao.queryByTeacherIdAndQuestionId(paramMap)!=null){
    		return -1;
    	}
    	
    	String uuidFile = UUID.randomUUID().toString();
		String fullName = FileUtil.generateFileName(file, uuidFile);
		// .file 改为.mp3
		if (fullName.indexOf(".file") != -1) {
			fullName = uuidFile + ".mp3";
		}
		Teacher teacher=teacherService.getTeacher(teacherId);
        //audio_upload 插入一条记录。
        Audio au = new Audio();
        String uuid=UUID.randomUUID().toString();
        au.setId(uuid);
        au.setQuestionId(questionRealId);
        au.setTeacherId(teacherId);
        au.setName(fullName);
        au.setSource(AudioSource.explanation.getValue());
        au.setStatus(AudioStatus.checked.getValue());//直接审核通过
        au.setDuration(duration);
        //设置教师星级
        StarPoint startPoint=teacherService.getStarPointByTeacherId(teacherId);
        au.setTeacherStar(startPoint.getStar());
        if(teacher.getCurOrgId()!=0&&!StringUtils.equals(teacher.getPlanType(), PlanFactory.PLAN_C)){
        	Organization org=organizationService.loadOrganization(teacher.getCurOrgId());
	        au.setOrgId(new Long(teacher.getCurOrgId()).intValue());
	        au.setOrgTeacherId(org.getTeacherId());
	        //机构类型
	        au.setPlanType(teacher.getPlanType());
        }
        
        Question q = questionService.getQuestion(questionRealId);
		au.setSubject(q.getRealSubject());
        //音频
		if(uploadType==1){
        	 String audioUrl = FileUtil.upload(file, fullName);
        	 au.setUrl(audioUrl);
        	 au.setType(AudioType.audio.getValue());
        }else if (uploadType == 2){//白板
        	try {
				//上传白板转换视频
					// 白板2.0 则白板转视频
					if (!StringUtils.isEmpty(wbVersion)
							&& new Integer(wbVersion) == WhiteBoradVersion.two
									.getValue()) {
								//通知第三方系统做白板转视频操作 异步处理
						String fileNameWithZip = uuidFile + "_wb.zip";
						File zipFile  = FileUtil.multipartFileCopyFile(file, fileNameWithZip);
						wb2videoService.uploadWBFile(toVideoPostUrl,zipFile, toVideoCallBackUrl,uuid,teacherId);
					}
					
			} catch (NumberFormatException e) {
					logger.error("wbVersion is not number:",e);
			}  catch (IOException e2) {
					logger.error("IOException :",e2);
			}
        	logger.info("file name:" + file.getName() + " size:" + file.getSize());
    		List<Map<String, String>> zipFiles = FileUtil.uploadZip(file, fullName);
			for (Map<String, String> s : zipFiles) {
				FeudDetailWb record = new FeudDetailWb();
				record.setFeudDetailId(-999L);
				record.setFileUrl(s.get("fileUrl"));
				record.setFileType(s.get("fileType"));
				record.setWbId(uuid);
				// 防止白板流转到公共库时URL为空。。 todo
				au.setUrl(s.get("fileUrl"));
				try {
					record.setWbVersion(WhiteBoradVersion.one.getValue());
					if (!StringUtils.isEmpty(wbVersion)&& new Integer(wbVersion) == WhiteBoradVersion.two.getValue()) {
						record.setWbVersion(WhiteBoradVersion.two.getValue());
					}
				} catch (NumberFormatException e) {
					logger.error("wbVersion is not number:"+ e.getMessage());
				}
				feudDetailWbDao.insert(record);
//				logger.info("file record:" + record.getFileUrl());
			}
			// 白板
			au.setType(AudioType.whiteboard.getValue());
        }
		
    	explanation.setAudioWhiteboardId(au.getId());
    	explanation.setQuestionRealId1(questionRealId);
    	explanation.setQuestionRealId2(null);
    	explanation.setQuestionRealId3(null);
    	explanation.setAudioCreateTime(new Date());
    	explanation.setStatus(2);//已经录制音频
    	explanationDao.updateByPrimaryKey(explanation);
    	// 默认审核通过，直接进入音频库
        // 保存audioUpload,保存音频或者白板
		audioDao.saveAudio(au);

		explanationProcessor.postSubmitAudio(au, teacher);
        
        //更新question表的音频上传状态为已经上传
        questionV2Dao.updateQuestionStatus(questionRealId, AudioStatus.checked.getValue());
        //成功
        return 0;
    }
    
    /**
     * 获取待录制的列表
     * @param teacherId
     * @param cuurent
     * @param pageSize
     */
	public List<ExplanationVO> getUnRecordedList(String teacherId,String imgId,Long questionId1, Long questionId2,Long questionId3,Long liveImgCreateTime,Integer pageSize) throws Exception{
        //是否需要到liveaaImg取
    	Boolean fetchSuccess=false;
    	if(StringUtils.isNotEmpty(imgId)){
    		//判断lastImgId在库中存在不
    		Explanation existExplanation=explanationDao.queryByImgId(imgId);//历史原因，加引号去查询
        	if(existExplanation==null){
        		fetchSuccess=savetoDB(teacherId,imgId,questionId1,questionId2,questionId3);
        	}
    	}
    	List<Explanation> list= new ArrayList<Explanation>();
    	List<ExplanationVO> volist= new ArrayList<ExplanationVO>();
    	//直接后台库取
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("teacherId", teacherId);
    	if(liveImgCreateTime!=null&&liveImgCreateTime!=0){
    		paramMap.put("liveImgCreateTime", new Timestamp(liveImgCreateTime));
    	}
    	paramMap.put("pageSize", pageSize==null?PageConstant.PAGE_SIZE_10:pageSize);
		list= explanationDao.getUnRecordedList(paramMap);
    	//取出对应的question信息
    	volist=getExplanationVO(list);
    	//打是isNew标志
    	if(fetchSuccess){
    		for(ExplanationVO vo:volist){
    			if(vo.getImgId().contains(imgId)){
    				vo.setIsNew(true);
    				break;
    			}
    		}
    	}
		return volist;
    }
    
    
    /**
     *  保存到DB中
     * @param imageId
     * 
     */
    public boolean savetoDB(String teacherId,String imgId,Long questionId1, Long questionId2,Long questionId3) throws Exception{
    	Explanation explanation=new Explanation();
    	explanation.setImgId(imgId);
    	explanation.setLiveImgCreateTime(new Date());
    	explanation.setStatus(1);
    	explanation.setIsNew(true);
    	explanation.setDealStatus(1);//正常
    	if(questionId1!=null){
    		loadQuestion(questionId1);
    		explanation.setQuestionRealId1(questionId1);
    	}
    	if(questionId2!=null){
    		loadQuestion(questionId2);
    		explanation.setQuestionRealId2(questionId2);
    	}
    	if(questionId3!=null){
    		loadQuestion(questionId3);
    		explanation.setQuestionRealId3(questionId3);
    	}
    	explanation.setTeacherId(teacherId);
    	explanationDao.insert(explanation);
    	return true;
    }
    
    
    /**
     * 把qustion保存到本地库中
     * @param questionId
     */
    public Boolean loadQuestion(Long questionId){
    	try{
    		Question question=questionV2Dao.queryByRealId(questionId);
    		if(question==null){
	    		question=questionService.loadQuestion(questionId);
	    		if (question == null) {
					// 查找题目出错
					logger.error("远程拉取题目时，无法查找题目-realId:" + questionId);
				}
	    		question.setEmergencyCount(1);
				question.setAllotCount(0);
				question.setSource(String.valueOf(AudioSource.explanation.getValue()));
				question.setLearnPhase("未知");
				questionV2Dao.insert(question);
    		}
    		return true;
		}catch(Exception ex){
			logger.error("questionService.loadQuestion() error!", ex);
		}
    	return false;
    }
    
    /**
     * 在answer为空的情况下，重新load answer到本地库中
     * @param questionId
     * @return
     */
    public Question reloadQuestionAnswer(Long questionId){
    	try{
    	 
    		Question question=questionService.loadQuestion(questionId);
    		if(question!=null){
    			if(StringUtils.isEmpty(question.getAnswer())){
    				question.setAnswer("");
    			}
    			questionV2Dao.updateAnswerByRealId(question);
    		}
    		return question;
    	
    	}catch(Exception ex){
    		
    	}
    	return null;
    }
    
    /**
     * 获取待录制列表的vo
     * @param list
     * @return
     */
    public List<ExplanationVO> getExplanationVO(List<Explanation> list){
    	List<ExplanationVO> volist= new ArrayList<ExplanationVO>();
    	List<Long> questionIds= new ArrayList<Long>();
    	if(list.size()>0){
			for(Explanation explanation:list){
				questionIds.add(explanation.getQuestionRealId1());
			}
			List<Question> questions= new ArrayList<Question>();
			if(questionIds.size()>0){
				questions=questionV2Dao.getQuestionsByRealIds(questionIds);
			}
			for(Explanation explanation:list){
				ExplanationVO vo = new ExplanationVO();
				vo.setExplanationId(explanation.getId());
				if(explanation.getLiveImgCreateTime()!=null){
					vo.setLiveImgCreateTime(explanation.getLiveImgCreateTime().getTime());
				}
				vo.setImgId(explanation.getImgId());
				for(Question question : questions){
					if(question.getRealId().equals(explanation.getQuestionRealId1())){
						vo.setLatex(question.getLatex());
						Subject subject = t_DictService.getSubjectById(new Long(question.getRealSubject()));
						if(subject!=null){
							vo.setSubject(subject.getName());
						}
						break;
					}
				}
				volist.add(vo);
			}
		}
    	return volist;
    }
    
    
    /**
     * 获取待录制的列表
     * @param teacherId
     * @param cuurent
     * @param pageSize
     */
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public List<ExplanationVO> getRecordedList(String teacherId,Long audioCreateTime,Integer pageSize){
    	Map paramMap = new HashMap();
    	paramMap.put("teacherId", teacherId);
    	if(audioCreateTime!=null&&audioCreateTime!=0){
    		paramMap.put("audioCreateTime",new Timestamp(audioCreateTime));
    	}
    	paramMap.put("pageSize", pageSize==null?PageConstant.PAGE_SIZE_10:pageSize);
    	List<Explanation> list= explanationDao.getRecordedList(paramMap);
    	if(list==null||list.size()==0){
    		return new ArrayList<ExplanationVO>();
    	}
    	
        List<Long> questionIds= new ArrayList<Long>();
		
		List<ExplanationVO> volist= new ArrayList<ExplanationVO>();
		List<String> auidoIdList=new ArrayList<String>();
		String audioIds="";
		for(Explanation explanation:list){
			auidoIdList.add(explanation.getAudioWhiteboardId());
			questionIds.add(explanation.getQuestionRealId1());
			audioIds+=explanation.getAudioWhiteboardId()+",";
		}
		
		//获取音频
		List<AudioUpload> audioUploads=audioUploadService.queryByAudioIds(auidoIdList);
		
		List<Question> questions=new ArrayList<Question>();
		if(questionIds.size()>0){
			questions=questionV2Dao.getQuestionsByRealIds(questionIds);
		}
		
		//获取分成
		Map dataMap=new HashMap();
		if(StringUtils.isNotBlank(audioIds)){
			dataMap=getExplainsPrice(audioIds.substring(0, audioIds.length()-1));
		}
		
		//获取音频的评价积分
		Map<String, Object> queryAudiosMap = new HashMap<String, Object>();
		queryAudiosMap.put("audioIds", auidoIdList);
		List<AudioEvalApprove> aeaList = audioStudentEvaluationService.getPointByAudioIds(queryAudiosMap);
		
		for(Explanation explanation:list){
			ExplanationVO vo = new ExplanationVO();
			vo.setExplanationId(explanation.getId());
			vo.setImgId(explanation.getImgId());
			vo.setAudioCreateTime(explanation.getAudioCreateTime().getTime());
			vo.setLiveImgCreateTime(explanation.getLiveImgCreateTime().getTime());
			vo.setIsNew(false);
			for(Question question : questions){
				if(question.getRealId().equals(explanation.getQuestionRealId1())){
					vo.setLatex(question.getLatex());
					vo.setQuestionId(explanation.getQuestionRealId1());
					Subject subject = t_DictService.getSubjectById(new Long(question.getRealSubject()));
					if(subject!=null){
						vo.setSubject(subject.getName());
					}
					break;
				}
			}
			//planA教师不显示积分和分成
			if(StringUtils.equals(explanation.getPlanType(), PlanFactory.PLAN_A)&&StringUtils.equals(explanation.getTeacherId(), teacherId)){
				vo.setShowPlanAPointFee(false);
			}
			//获取总收入
			Map totalPriceMap=(Map)dataMap.get(explanation.getAudioWhiteboardId());
			if(totalPriceMap!=null){
				vo.setIncome((Integer)totalPriceMap.get("price"));//总收入
				vo.setSaleNum((Integer)totalPriceMap.get("totalNum"));//购买总数
			}
			//是否下线
			for(AudioUpload audio:audioUploads){
				if(StringUtils.equals(audio.getId(), explanation.getAudioWhiteboardId())){
					if(audio.getStatus()==AudioStatus.offline.getValue() || audio.getStatus()==AudioStatus.notPassCheck.getValue()){
						vo.setOffline(true);
					}
					break;
				}
			}
			//设置音频积分
			for(AudioEvalApprove approve:aeaList){
				if(StringUtils.equals(approve.getAudioId(), explanation.getAudioWhiteboardId())){
					vo.setTotalPoint(approve.getTotalPoint()+approve.getDeductPoint());
					break;
				}
			}
			volist.add(vo);
		}
		return volist;
    }
    
    /**
     * 获取已经录制的数量
     * @param teacherId
     * @return
     */
    public int getRecordedCount(String teacherId){
    	return explanationDao.getRecordedCount(teacherId);
    }
    
    /**
     * 获取未录制详情页
     * @param expalantionId
     */
    public Explanation getUnRecordDetail(String imgId){
    	Explanation explanation=explanationDao.queryByImgId(imgId);
    	List<Long> realIds= new ArrayList<Long>();
    	if(explanation.getQuestionRealId1()!=null){
    		realIds.add(explanation.getQuestionRealId1());
    	}
    	if(explanation.getQuestionRealId2()!=null){
    		realIds.add(explanation.getQuestionRealId2());
    	}
    	if(explanation.getQuestionRealId3()!=null){
    		realIds.add(explanation.getQuestionRealId3());
    	}
    	if(realIds.size()>0){
    		List<Question> questions=questionV2Dao.getQuestionsByRealIds(realIds);
        	List<ExplanationVO> voList= new ArrayList<ExplanationVO>();
        	if(explanation.getQuestionRealId1()!=null){
        		voList.add(getVoByQuestion(explanation.getTeacherId(),questions,explanation.getQuestionRealId1()));
        	}
        	if(explanation.getQuestionRealId2()!=null){
        		voList.add(getVoByQuestion(explanation.getTeacherId(),questions,explanation.getQuestionRealId2()));
        	}
        	if(explanation.getQuestionRealId3()!=null){
        		voList.add(getVoByQuestion(explanation.getTeacherId(),questions,explanation.getQuestionRealId3()));
        	}
        	explanation.setVoList(voList);
    	}
    	
    	return explanation;
    }
    
    /**
     * 从question中获取对应的vo
     * @param question
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public ExplanationVO getVoByQuestion(String teacherId,List<Question> questions,Long realQuestionId){
    	ExplanationVO vo = new ExplanationVO();
    	//如果已经上传过，直接返回。
    	Map paramMap=new HashMap();
    	paramMap.put("teacherId", teacherId);
    	paramMap.put("questionRealId",realQuestionId);
    	if(audioUploadDao.queryByTeacherIdAndQuestionId(paramMap)!=null){
    		vo.setHasRecorded(true);
    	}
    	for(Question question:questions){
    		if(question.getRealId().equals(realQuestionId)){
    			//如果answer为空，重新reload question
    			if(StringUtils.isBlank(question.getAnswer())){
    				Question reloadQuestion=reloadQuestionAnswer(question.getRealId());
    				if(reloadQuestion!=null){
    					vo.setAnswer(reloadQuestion.getAnswer());
    				}
    			}
    			vo.setLatex(question.getLatex());
    			vo.setContent(question.getContent());
    			vo.setKnowledge(question.getKnowledge());
    			vo.setSolution(question.getSolution());
    			vo.setSubject(question.getSubject());
    			vo.setQuestionId(question.getRealId());
    			//是否报错
    			ErrorCorrection correct = errorCorrectionDao.getQuestionCorrection(teacherId,question.getRealId());
    			vo.setQuestionStatus(-1);
    			if (correct != null) {
    				vo.setQuestionStatus(correct.getCheckStatus());
    			}
    			break;
    		}
    	}
		return vo;
    }
    
    /**
     * 获取已经录制详情页
     * @param expalantionId
     */
    @SuppressWarnings("rawtypes")
	public ExplanationVO getRecordDetail(String imgId,String teacherId){
    	Explanation explanation=explanationDao.queryByImgId(imgId);
    	AudioUpload audioUpload=audioUploadDao.selectByPrimaryKey(explanation.getAudioWhiteboardId());
    	Question question=questionV2Dao.queryByRealId(explanation.getQuestionRealId1());
		ExplanationVO vo = new ExplanationVO();
		vo.setQuestionId(explanation.getQuestionRealId1());
		vo.setLatex(question.getLatex());
		vo.setContent(question.getContent());
		//如果answer为空，重新reload question
		if(StringUtils.isBlank(question.getAnswer())){
			Question reloadQuestion=reloadQuestionAnswer(question.getRealId());
			if(reloadQuestion!=null){
				vo.setAnswer(reloadQuestion.getAnswer());
			}
		}
		vo.setKnowledge(question.getKnowledge());
		vo.setSolution(question.getSolution());
		vo.setSubject(question.getSubject());
		vo.setAudioCreateTime(audioUpload.getCreateTime().getTime());
		vo.setUploadType(audioUpload.getType());
		//音频下线
		if(audioUpload.getStatus()==AudioStatus.offline.getValue() || audioUpload.getStatus()==AudioStatus.notPassCheck.getValue()){
			vo.setOffline(true);
		}
		//获取下线原因
		if(audioUpload.getStatus()==AudioStatus.offline.getValue()){
			AudioEvalApprove audioEvalApprove=audioEvalApproveService.selectByAudioId(explanation.getAudioWhiteboardId());
			if(audioEvalApprove!=null&&audioEvalApprove.getStatus().intValue()==1){//审核属实下线
				vo.setOfflineReason(audioEvalApprove.getContent());//下线原因
				if(audioEvalApprove.getDeductPoint().intValue()<0){//扣除积分
					vo.setDeductPoint(audioEvalApprove.getDeductPoint());//如果有扣除积分，显示扣除积分
				}else{
					vo.setDeductPoint(audioEvalApprove.getTotalPoint());//如果没有扣除积分，总积分。为负数
				}
				vo.setOfflineTime(audioEvalApprove.getApproveTime().getTime());//下线时间
				vo.setOfflineTimeStr(DateUtils.formatDetail(audioEvalApprove.getApproveTime()));//下线时间str
			}
		}
		//planA 不显示积分和收入
		if(StringUtils.equals(audioUpload.getTeacherId(), teacherId)&&StringUtils.equals(audioUpload.getPlanType(), PlanFactory.PLAN_A)){
			vo.setShowPlanAPointFee(false);
		}
		
		if(audioUpload!=null&&audioUpload.getType()==1){
			vo.setAudioUrl(audioUpload.getUrl());
		}else{
			List<FeudDetailWb> wbDetailList = feudDetailWbDao.selectAllByWbId(audioUpload.getId());
			vo.setWbDetailList(wbDetailList);
		}
		
		//获取总购买数和总分成
		Map dataMap=getTotalPrice(audioUpload.getId());
		if(dataMap.get("price")!=null){
			if(vo.getOffline()){//下线，扣除分成，显示为负数
				vo.setIncome((Integer)dataMap.get("price")*-1);
			}else{
				vo.setIncome((Integer)dataMap.get("price"));
			}
		}
		if(dataMap.get("totalNum")!=null){
			vo.setSaleNum((Integer)dataMap.get("totalNum"));
		}

		//获取好中差评数
		List<String> audioIds=new ArrayList<String>();
		audioIds.add(explanation.getAudioWhiteboardId());
		List<AudioEvaluateCount.Item> list = studentApiService.getAudioEvaluateList(audioIds, false);
		if(list==null){
			vo.setGoodEvaNum(0);
			vo.setMediumEvaNum(0);
			vo.setBadEvaNum(0);
		}else{
			for(AudioEvaluateCount.Item item:list){
				if(StringUtils.equals(item.audioId,explanation.getAudioWhiteboardId())){
					vo.setGoodEvaNum(item.goodCounts);
					vo.setMediumEvaNum(item.midCounts);
					vo.setBadEvaNum(item.badCounts);
					break;
				}
			}
		}
		
		if(vo.getShowPlanAPointFee()){
			//积分
			vo.setTotalPoint(audioStudentEvaluationService.getPointByAudioId(explanation.getAudioWhiteboardId()));
		}
		
		//是否报错
		ErrorCorrection correct = errorCorrectionDao.getQuestionCorrection(explanation.getTeacherId(), explanation.getQuestionRealId1());
		vo.setQuestionStatus(-1);
		if (correct != null) {
			vo.setQuestionStatus(correct.getCheckStatus());
		}
		
		//是否是机构主账号,取录题人nickname
		Teacher loginTeacher=teacherService.getRequiredTeacher(teacherId);
		String audioTeacherNickname="";
		if(StringUtils.isNotEmpty(audioUpload.getTeacherId())&&!StringUtils.equals(audioUpload.getTeacherId(), teacherId)){
			Teacher audioTeacher=teacherService.getRequiredTeacher(audioUpload.getTeacherId());
			if(audioTeacher!=null){
				audioTeacherNickname=audioTeacher.getNickname();
			}
		}
		Organization org=organizationService.loadOrgByCode(loginTeacher.getPhoneNumber());
		if(org!=null&&StringUtils.equals(org.getPlanType(), PlanFactory.PLAN_A)){
			vo.setIsOrgMaster(true);
			vo.setNickName(audioTeacherNickname);
		}
    	return vo;
    }
    
    
    /**
     * 批量获取音频的总购买数和总分成
     * @param audioId
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getExplainsPrice(String audioIds){
    	Map paramMap=new HashMap();
    	paramMap.put("audioIds", audioIds);
    	Map map =StudentApiHttpUtil.httpPost(paySystemUrl+explainsPriceUriPath, paramMap);
    	if(map!=null&&(Boolean)map.get("success")){
    		return (Map)map.get("data");
    	}
    	return new HashMap();
    }
    
    /**
     * 批量获取音频的总购买数和总分成
     * @param audioId
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public  Map getTotalPrice(String audioId){
    	Map paramMap=new HashMap();
    	paramMap.put("audioId", audioId);
    	Map map =StudentApiHttpUtil.httpPost(paySystemUrl+audioTotalPriceUriPath, paramMap);
    	if(map!=null&&(Boolean)map.get("success")){
    		return (Map)map.get("data");
    	}
    	return new HashMap();
    }
    
}

