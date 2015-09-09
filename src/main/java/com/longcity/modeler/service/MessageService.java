/**
 * 
 */
package com.longcity.modeler.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.longcity.modeler.dao.MessageDao;
import com.longcity.modeler.model.Message;

/**
 * @author maxjcs
 *
 */
@Service
public class MessageService {
	
	private static Logger logger = LoggerFactory.getLogger(MessageService.class);
	
	@Resource
	MessageDao messageDao;
	
	/**
	 * 发布消息
	 * @param message
	 */
	public void add(String title,String content,Integer type){
		Message message=new Message();
		message.setTitle(title);
		message.setContent(content);
		message.setType(type);
		messageDao.insert(message);
	}
	
	/**
	 * 消息详情
	 * @param message
	 */
	public Message detail(Integer id){
		Message message = messageDao.selectByPrimaryKey(id);
		return message;
	}
	
	/**
	 * 消息列表
	 * @param message
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void list(Integer pageNo,Integer pageSize){
		Map paraMap=new HashMap();
		paraMap.put("start", (pageNo-1)*pageSize);
		paraMap.put("limit", pageSize);
		List<Message> list=messageDao.list(paraMap);
	}
	
	

}
