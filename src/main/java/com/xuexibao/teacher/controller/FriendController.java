/**
 * 
 */
package com.xuexibao.teacher.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.util.HttpsUtil;
import com.xuexibao.teacher.util.PropertyUtil;

/**
 * 
 *  中转教师手机端对学生端后台的请求
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("friend")
public class FriendController extends AbstractController{
	
    private static Logger logger = LoggerFactory.getLogger(FriendController.class);
    
    private String paySystemUrl = PropertyUtil.getProperty("paySystemUrl");
    
    private String tutorRecord = "/api/teacher/tutorRecord";
    
  //  private String userAgent ="{\"app_name\":\"学习宝\",\"app_ver\":\"2.3.5\",\"dev_model\":\"HM NOTE 1LTETD\",\"sys_name\":\"android_os\",\"sys_ver\":\"4.4.2\",\"version\":\"1\"}";
    
    /**
     * 取名师汇辅导纪录
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "tutorRecord")
    public Object tutorRecord(HttpServletRequest request,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Map paramMap = new HashMap();
//        	paramMap.put("teacherId",AppContext.getTeacherId());
        	paramMap.put("teacherId","402883904beda714014bedc628af0005");
        	paramMap.put("pageno", pageNo);
        	paramMap.put("count", pageSize);
			Map<String, Object> map = HttpsUtil.httpPost(paySystemUrl+tutorRecord,paramMap);
			Map dataMap = getDataMap(map);
			if(dataMap!=null){
				return dataJson(getDataMap(map), request);
			}else{
				return errorJson("远程服务器异常，请重试.", request);
			}
        }catch(Exception e){
            logger.error("取名师汇辅导纪录失败.", e);
            return errorJson("远程服务器异常，请重试.", request);
        }
    }
	
	private Map getDataMap(Map<String, Object> map){
		if(StringUtils.equals((String)map.get("msg"), "ok")){
			return (Map)map.get("data");
		}else{
			return null;
		}
	}
}
