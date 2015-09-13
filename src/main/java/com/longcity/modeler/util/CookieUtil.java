package com.longcity.modeler.util;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.longcity.modeler.constant.CookieConstants;


public class CookieUtil {
    
    /**
     * 移出cookie
     *
     * @param response
     * @param key
     */
    public static void removeCookie(HttpServletResponse response, Cookie cookie) {
        cookie.setValue(null);
        cookie.setMaxAge(0);
        cookie.setPath(CookieConstants.PATH);
        cookie.setDomain(CookieConstants.DOMAIN_NAME);
        cookie.setVersion(0);
        response.addCookie(cookie);
    }

    /**
     * 移出cookie
     *
     * @param response
     * @param key
     */
    public static void removeCookie(HttpServletResponse response, Cookie cookie,String domain) {
        cookie.setValue(null);
        cookie.setMaxAge(0);
        cookie.setPath(CookieConstants.PATH);
        cookie.setDomain(domain);
        cookie.setVersion(0);
        response.addCookie(cookie);
    }


    /**
     * 写入cookie
     *
     * @param key
     * @param value
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response,String cookieDomain, String key,
            String value, int maxAge) {
        Cookie cookie = new Cookie(key, value);
        if (maxAge >= 0)
            cookie.setMaxAge(maxAge * 60 * 60 * 24);
        else
            cookie.setMaxAge(-1);
        cookie.setPath(CookieConstants.PATH);
        cookie.setDomain(cookieDomain);
        cookie.setVersion(0);
        response.addCookie(cookie);
    }

    
    public static String getCookie(HttpServletRequest request, String key) {
    	Cookie[] cookies = request.getCookies();
    	if(cookies==null || cookies.length ==0){
    	    return null;
    	}
    	for (Cookie cookie : cookies) {
			if(StringUtils.equals(cookie.getName(), key)){
				return cookie.getValue();
			}
		}
    	return null;
    }

    
    
    /**
     * 获取客户端IP
     *
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String clientIp = null;
        String strClientIp = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(strClientIp)
                || StringUtils.equalsIgnoreCase(strClientIp, "unknown")) {
            clientIp = request.getRemoteAddr();
        } else {
            String[] strIps = StringUtils.split(strClientIp, ",");  
            String strIp = null;
            for (int i = 0; i < strIps.length; i++) {
                strIp = strIps[i];
                if (!StringUtils.equalsIgnoreCase(strIp, "unknown")) {
                    clientIp = strIp;
                    break;
                }
            }
        }
        return clientIp;
    }

}