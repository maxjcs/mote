package com.longcity.modeler.filter;

import java.util.HashSet;
import java.util.Set;

public class EscapeUrl {

	public static Set<String> escapeUrls = new HashSet<String>();

	static {
		escapeUrls.add("/user/sendVerifyCode"); // 发送验证码
		escapeUrls.add("/user/register"); // 注册
		escapeUrls.add("/user/register4Seller"); // 注册
		escapeUrls.add("/user/login"); // 登录
		escapeUrls.add("/user/login4Seller"); // 登录
		escapeUrls.add("/user/changePasswordByVerifyCode"); // 重置密码
		escapeUrls.add("/user/getDeviceId"); // 获取设备号
		escapeUrls.add("/user/changeStar"); // 修改星级
		escapeUrls.add("/user/getOrgListByCityId"); //
		escapeUrls.add("/user/loginReact"); // 登录
		escapeUrls.add("/user/approve"); // 登录
		escapeUrls.add("/user/hasRegistered"); //是否已经登陆
		escapeUrls.add("/common/h5Url"); //是否已经登陆
		
		
	}
}
