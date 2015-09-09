package com.xuexibao.teacher.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuexibao.teacher.dao.AudioDao;
import com.xuexibao.teacher.enums.AudioWbType;
import com.xuexibao.teacher.enums.Wb2videoProcessStatus;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.Wb2videoProcess;
import com.xuexibao.teacher.pay.model.PayAudio;
import com.xuexibao.teacher.pay.service.PayAudioService;
@Transactional
@Service
public class Wb2videoService {
	private static Logger logger = LoggerFactory
			.getLogger(Wb2videoService.class);

	
	@Resource
	private Wb2videoProcessService wb2videoProcessService;	
	
	@Resource 
	private AudioDao audioDao;
	@Resource
	private PayAudioService payAudioService;
	
/**
 * 上传白板至第三方系统,以异步调用方式上传
 * todo 记录上传情况， 是否ok 返回情况
 * @param postUrl
 * @param file
 * @param callBackUrl
 * @param wbIdString
 */
	@Async
	public void uploadWBFile(String postUrl, File file,
			 String callBackUrl, String wbIdString,String teacherId) {
		logger.info("uploadWBFile:" + file 
				+ " callBackUrl:" + callBackUrl + " wbIdString:" + wbIdString);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		Wb2videoProcess  wp = new Wb2videoProcess();
		wp.setTeacherId(teacherId);
		wp.setWbId(wbIdString);
		try {
			HttpPost httppost = new HttpPost(postUrl);
			StringBody wbId = new StringBody(wbIdString, ContentType.TEXT_PLAIN);
			StringBody cbUrl = new StringBody(callBackUrl,
					ContentType.TEXT_PLAIN);
			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addPart("callBackUrl", cbUrl).addPart("wbId", wbId)
					.addBinaryBody("file", file).build();
			httppost.setEntity(reqEntity);
			
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity resEntity = response.getEntity();
				int statusCode =  response.getStatusLine().getStatusCode();
				if(statusCode==200){
					wp.setStatus(Wb2videoProcessStatus.uploadOk.getValue());
					logger.info("response is ok");
				}else{
					logger.error("response is error:"+statusCode);
					wp.setStatus(Wb2videoProcessStatus.uploadFailed.getValue());
				}
				if (resEntity != null) {
					logger.info("Response content length:"
							+ resEntity.getContentLength()+" code:"+response.getStatusLine().getStatusCode());
				}
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}
		} catch (IllegalStateException ii) {
			logger.error("IllegalStateException:",ii);
		} catch (ClientProtocolException ce) {
			logger.error("ClientProtocolException:",ce);
		} catch (IOException ie) {
			logger.error("IOException:",ie);
		}catch (Exception ex) {
			logger.error("Exception:",ex);
		}
		finally {
			wb2videoProcessService.insert(wp);
			try {
				httpclient.close();
			} catch (IOException fe) {
				logger.error("IOException:",fe);
			}
		}
	}
	
	public void updateWb2Video(String wbId, String wbVideoUrl,int status){
		
		Wb2videoProcess wp = wb2videoProcessService.selectByWbId(wbId);
		if(wp == null){
			logger.error("wbId not exists wbId:"+wbId);
			return ;
		}
		wp.setWbId(wbId);
		wp.setVideoUrl(wbVideoUrl);
		wp.setStatus(status);
		wb2videoProcessService.updateByPrimaryKey(wp);
		logger.info("update Wb2videoProcess wb video_url and status:"+status+" wbId:"+wbId);
		if(status==Wb2videoProcessStatus.processOk.getValue()){
			Audio au = new Audio();
			au.setId(wbId);
			au.setUrl(wbVideoUrl);
			au.setWbType(AudioWbType.video.getValue());
			audioDao.updateWbTypeUrlPayAudio(au);
			logger.info("update audio wb video_url");
			PayAudio pa = new PayAudio();
			pa.setId(wbId);
			pa.setUrl(wbVideoUrl);
			pa.setWbType(AudioWbType.video.getValue());
			payAudioService.updateWbTypeUrlPayAudio(pa);
			logger.info("update pay.audio wb video_url");
		}
		
	}

}
