package com.xuexibao.teacher.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xuexibao.teacher.enums.Wb2videoProcessStatus;
import com.xuexibao.teacher.service.Wb2videoService;
import com.xuexibao.teacher.util.FileUtil;
import com.xuexibao.teacher.util.PropertyUtil;

@Controller
@RequestMapping("whiteBoard")
public class WhiteBoardController extends AbstractController {
	
	private static Logger logger = LoggerFactory.getLogger(WhiteBoardController.class);
	@Resource 
	private Wb2videoService wb2videoService;

	
	/**
	 * 视频转换后把白板id和视频转换后的url传递过来
	 * @param wbId
	 * @param videoUrl
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "wbVideoUrl")
	public Object wbVideoUrl(String wbId, String wbVideoUrl) {
		logger.info("wbVideoUrl--- wbId:"+wbId+" wbVideoUrl:"+wbVideoUrl);
		int status = Wb2videoProcessStatus.processFailed.getValue();
		if(!StringUtils.isEmpty(wbId)&&!StringUtils.isEmpty(wbVideoUrl)){
			status = Wb2videoProcessStatus.processOk.getValue();
		}
		wb2videoService.updateWb2Video(wbId, wbVideoUrl, status);
		String resultData = "ok";
		return dataJson(resultData);
	}
	@ResponseBody
	@RequestMapping(value = "testUploadWBFile")
	public Object testUploadWBFile(MultipartFile file,String wbId) throws InterruptedException, ExecutionException, IOException {
		System.out.println("testUploadWBFile file:" + file+" wbId:"+wbId);
	    String uuidFile = UUID.randomUUID().toString();
	    File wb2VideoServiceFile  = FileUtil.multipartFileCopyFile(file, uuidFile+"_wb2V.zip");
	    String toVideoPostUrl = PropertyUtil.getProperty("toVideoPostUrl");
		String toVideoCallBackUrl = PropertyUtil.getProperty("toVideoCallBackUrl");
		String teacherId = "teacher_id:hehe";
		wb2videoService.uploadWBFile(toVideoPostUrl, wb2VideoServiceFile, toVideoCallBackUrl, wbId,teacherId);
		return "ok";
	}

}
