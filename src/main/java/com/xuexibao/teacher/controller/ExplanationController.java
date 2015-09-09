package com.xuexibao.teacher.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.model.Explanation;
import com.xuexibao.teacher.model.vo.ExplanationVO;
import com.xuexibao.teacher.service.ExplanationService;
import com.xuexibao.teacher.util.JsonUtil;
import com.xuexibao.teacher.util.PropertyUtil;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("explanation")
public class ExplanationController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(ExplanationController.class);
	
	private static String liveaaImgUrl=PropertyUtil.getProperty("liveaa_img_url");
	
	@Resource
	private ExplanationService explanationService;
	
	/**
     * 上传音频
     */
	@ResponseBody
    @RequestMapping(value = "uploadAudio")
    public Object uploadAudio(HttpServletRequest request,MultipartFile file,String imgId,Long questionRealId,Integer duration,int uploadType,String wbVersion) throws Exception{
		Validator.validateFile(file, "file不能为空!");
		Validator.validateBlank(imgId, "图片ID不能为空!");
		Validator.validateBlank(questionRealId, "试题Id不能为空!");
		try{
        	String teacherId = AppContext.getTeacherId();
        	Integer result=explanationService.uploadAudio(teacherId, file, imgId,questionRealId,duration,uploadType,wbVersion);
        	if(result==0){
        		return dataJson(true, request);
        	}else if(result==-1){
        		return errorJson("已经录制过该题，不能重复录制", request);
        	}else{
        		return errorJson("服务器异常，请重试.", request);
        	}
        }catch(Exception e){
            logger.error("上传音频失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
    
    /**
     * 删除拍题
     */
	@ResponseBody
    @RequestMapping(value = "delete")
    public Object delete(HttpServletRequest request,Long id) throws Exception{
    	Validator.validateBlank(id, "拍题ID不能为空!");
        try{
        	explanationService.delete(id);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("新建拍题失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
    
	/**
     * 获取老师的待录制试题列表
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "getUnRecordedList")
    public Object getUnRecordedList(HttpServletRequest request,String lastImgId,String imgId,Long questionId1,Long questionId2,Long questionId3,Long liveImgCreateTime,Integer pageSize) throws Exception{
		try{
			String teacherId = AppContext.getTeacherId();
			if(StringUtils.isNotBlank(imgId)&&imgId.contains("'")){
				imgId=imgId.replace("'", "");
			}
			List<ExplanationVO> voList=explanationService.getUnRecordedList(teacherId,imgId,questionId1,questionId2,questionId3,liveImgCreateTime,pageSize==null?10:pageSize);
            int recordedCount=explanationService.getRecordedCount(teacherId);
            Map returnMap=new HashMap();
            returnMap.put("list", voList);
            returnMap.put("recordedCount", recordedCount);
			return dataJson(returnMap, request);
        }catch(Exception e){
            logger.error("获取老师的待录制试题列表失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 获取老师的已经录制试题列表
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "getRecordedList")
    public Object getRecordedList(HttpServletRequest request,Long audioCreateTime,Integer pageSize) throws Exception{
		try{
			String teacherId = AppContext.getTeacherId();
			List<ExplanationVO> voList=explanationService.getRecordedList(teacherId,audioCreateTime,pageSize);
            int recordedCount=explanationService.getRecordedCount(teacherId);
            Map returnMap=new HashMap();
            returnMap.put("list", voList);
            returnMap.put("recordedCount", recordedCount);
			return dataJson(returnMap, request);
        }catch(Exception e){
            logger.error("获取老师的已经录制试题列表失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 未录制详情
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "getUnRecordedDetail")
    public Object getUnRecordedDetail(HttpServletRequest request,String imgId) throws Exception{
		try{
			if(StringUtils.isNotBlank(imgId)&&imgId.contains("'")){
				imgId=imgId.replace("'", "");
			}
			Explanation explanation=explanationService.getUnRecordDetail(imgId);
            Map returnMap=new HashMap();
            returnMap.put("imgId", explanation.getImgId());
            returnMap.put("imgUrl", liveaaImgUrl+explanation.getImgId().replace("'", ""));
            returnMap.put("list", explanation.getVoList());
			return dataJson(returnMap, request);
        }catch(Exception e){
            logger.error("获取未录制详情失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 已录制详情
     */
	@ResponseBody
    @RequestMapping(value = "getRecordedDetail")
    public Object getRecordedDetail(HttpServletRequest request,String imgId) throws Exception{
		try{
			String teacherId = AppContext.getTeacherId();
			if(StringUtils.isNotBlank(imgId)&&imgId.contains("'")){
				imgId=imgId.replace("'", "");
			}
			ExplanationVO explanation=explanationService.getRecordDetail(imgId,teacherId);
			return dataJson(JsonUtil.obj2Map(explanation), request);
        }catch(Exception e){
            logger.error("获取已录制详情失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }

}
