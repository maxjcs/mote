package com.longcity.modeler.util;

import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.longcity.modeler.constant.BusinessConstant;

public class VerifyCodeUtil {

	/**
	 * 生成n位验证码
	 */
	public static String getRandNum(int length) {
		String code = "";
		for (int i = 0; i < length; i++) {
			char c = (char) (randomInt(0, 10) + '0');
			code += String.valueOf(c);
		}
		return code;
	}

	private static int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}

	/**
	 * 发送短信
	 */
	public static void sendMessage(String phoneNumber, String verifyCode) throws Exception {
		String msg = String.format(BusinessConstant.VERIFY_CODE, verifyCode);
		String url = PropertyUtil.getProperty("sms_url");
		NameValuePair[] params = new NameValuePair[] { new NameValuePair("mobile", phoneNumber), new NameValuePair("message", msg) };
		send(url, params);
	}

	private static void send(String url, NameValuePair[] params) throws Exception {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		PostMethod method = new PostMethod(url);
		method.setRequestBody(params);
		client.executeMethod(method);
		System.out.println(method.getResponseBodyAsString());
		client.getHttpConnectionManager().closeIdleConnections(1);
	}

	public static void main(String[] args) throws Exception {
		String phoneNumber = "18758031357";
		String verifyCode = getRandNum(6);
		sendMessage(phoneNumber, verifyCode);
	}
}
