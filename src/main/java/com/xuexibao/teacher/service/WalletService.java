package com.xuexibao.teacher.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xuexibao.teacher.util.HttpsUtil;
import com.xuexibao.teacher.util.PropertyUtil;
import com.xuexibao.teacher.util.StudentApiHttpUtil;

@Service
public class WalletService {

	private String paySystemUrl = PropertyUtil.getProperty("paySystemUrl");

	private final static String walletBalancePath = "/api/teacher/balance";
	private final static String walletPaymentUriPath = "/api/teacher/wallet";
	private final static String walletNewWalletUriPath = "/api/teacher/newWallet";

	private final String URL_TEACHER_NEW_WALLET_V14 = "/api/teacher/newWalletV14";

	private final String URL_TEACHER_GET_WALLET_DETAIL = "/api/teacher/getWalletDetail";

	public Map<String, Object> getBalance(String teacherId) {
		String url = paySystemUrl + walletBalancePath;

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("teacherId", teacherId);

		return HttpsUtil.httpPost(url, param);
	}

	public Map<String, Object> getPayment(String teacherId, int pageno, int count) {
		String url = paySystemUrl + walletPaymentUriPath;

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("teacherId", teacherId);
		param.put("pageno", pageno);
		param.put("count", count);

		return HttpsUtil.httpPost(url, param);
	}

	public Map<String, Object> newWallet(String teacherId, Integer pageNo, Integer count) {
		String url = paySystemUrl + walletNewWalletUriPath;

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("teacherId", teacherId);
		param.put("pageno", pageNo);
		param.put("count", count);

		return HttpsUtil.httpPost(url, param);
	}

	public Map<String, Object> newWalletV14(String teacherId, Integer pageNo, Integer type, Integer count) {
		String url = paySystemUrl + URL_TEACHER_NEW_WALLET_V14;

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("teacherId", teacherId);
		param.put("pageno", pageNo);
		param.put("count", count);
		param.put("type", type);

		return StudentApiHttpUtil.httpPost(url, param);
	}

	public Map<String, Object> walletDetail(String teacherId, Long monthTime, Integer type) {
		String url = paySystemUrl + URL_TEACHER_GET_WALLET_DETAIL;

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("teacherId", teacherId);
		param.put("monthTime", monthTime);
		param.put("type", type);

		return StudentApiHttpUtil.httpPost(url, param);
	}
}
