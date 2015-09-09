package com.xuexibao.teacher.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.service.WalletService;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("wallet")
public class WalletController extends AbstractController {
	@Resource
	private WalletService walletService;

	private static Logger logger = LoggerFactory.getLogger(WalletController.class);

	/**
	 * 钱包余额接口
	 */
	@ResponseBody
	@RequestMapping(value = "balance")
	public Object balance(String teacherId) throws Exception {
		Validator.validateBlank(teacherId, "teacherId不能为空");
		try {
			Map<String, Object> returnMap = walletService.getBalance(teacherId);
			return dataJson(returnMap.get("data"));

		} catch (Exception e) {
			logger.error("获取钱包余额接口出错.", e);
			return errorJson("服务器异常，请重试.");
		}
	}

	/**
	 * 钱包最近7天，30天收入以及收入详细条目
	 */
	@ResponseBody
	@RequestMapping(value = "payment")
	public Object payment(Integer pageNo, Integer count) throws Exception {
		Validator.validateBlank(pageNo, "pageNo不能为空");
		Validator.validateBlank(count, "count不能为空");
		try {
			String teacher_id = AppContext.getTeacherId();
			Map<String, Object> returnMap = walletService.getPayment(teacher_id, pageNo, count);
			return dataJson(returnMap.get("data"));

		} catch (Exception e) {
			logger.error("获取钱包最近7天，30天收入以及收入详细条目出错.", e);
			return errorJson("服务器异常，请重试.");
		}
	}

	/**
	 * 钱包最近7天，30天收入以及收入详细条目
	 */
	@ResponseBody
	@RequestMapping(value = "newWallet")
	public Object newWallet(Integer pageNo, Integer count) throws Exception {
		Validator.validateBlank(pageNo, "pageNo不能为空");
		Validator.validateBlank(count, "count不能为空");

		String teacher_id = AppContext.getTeacherId();
		Map<String, Object> returnMap = walletService.newWallet(teacher_id, pageNo, count);
		return dataJson(returnMap.get("data"));
	}

 
	@ResponseBody
	@RequestMapping(value = "newWalletV14")
	public Object newWalletV14(Integer pageNo, Integer count, Integer type) throws Exception {
		Validator.validateBlank(pageNo, "pageNo不能为空");
		Validator.validateBlank(count, "count不能为空");
		Validator.validateBlank(type, "type不能为空");

		String teacher_id = AppContext.getTeacherId();
		Map<String, Object> returnMap = walletService.newWalletV14(teacher_id, pageNo, type, count);
		if(returnMap!=null){
			return dataJson(returnMap.get("data"));
		}
		return dataJson(new HashMap<String,String>());
	}
	
	@ResponseBody
	@RequestMapping(value = "walletDetail")
	public Object walletDetail(Integer pageNo, Long monthTime, Integer type) throws Exception {
		Validator.validateBlank(monthTime, "monthTime不能为空");
		Validator.validateBlank(type, "type不能为空");

		String teacher_id = AppContext.getTeacherId();
		Map<String, Object> returnMap = walletService.walletDetail(teacher_id, monthTime, type);
		if(returnMap!=null){
			return dataJson(returnMap.get("data"));
		}
		return dataJson(new HashMap<String,String>());
	}
}
