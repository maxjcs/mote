/**
 * 
 */
package com.longcity.modeler.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longcity.modeler.service.MessageService;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("message")
public class MessageController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Resource
	MessageService messageService;

	/**
     * 发布站内信
     */
	@ResponseBody
    @RequestMapping(value = "add")
    public Object add(HttpServletRequest request,String title,String content,Integer type) throws Exception{
        try{
        	messageService.add(title, content,type);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("发布站内信失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 详情
     */
	@ResponseBody
    @RequestMapping(value = "detail")
    public Object detail(HttpServletRequest request,Integer id) throws Exception{
        try{
        	messageService.detail(id);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("站内信详情失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 发布站内信
     */
	@ResponseBody
    @RequestMapping(value = "list")
    public Object list(HttpServletRequest request,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	messageService.list(pageNo,pageSize);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("发布站内信失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
}
