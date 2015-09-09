package com.xuexibao.teacher.util;

import java.net.URL;


/**
 * 
 * @author oldlu
 *
 */
public final class URLUtil {
	private URLUtil() {
	}

	/**
	 * 获取类所在ClassLoader的类绝对路径
	 */
	public static String getClassPath() {
		return getResourcePath("");
	}

	/**
	 * 获取类路径下资源
	 */
	public static String getResourcePath(String resource) {
		URL url = getResourceUrl(resource);
		if (url != null)
			return url.getPath();
		return "";
	}

	/**
	 * 获取类路径下资源
	 */
	public static URL getResourceUrl(String resource) {
		return URLUtil.class.getClassLoader().getResource(resource);
	}

	public static String getWebappUrl(String resource) {
		String url = getResourceUrl("").getPath();
		url = url.substring(0, url.indexOf("WEB-INF"));
		return url + resource;
	}
}
