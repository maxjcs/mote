package com.longcity.modeler.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

	private static Properties prop;

	static {
		prop = new Properties();
		InputStream in = PropertyUtil.class.getResourceAsStream("/ApplicationResources.properties");
		if (in == null) {
			in = Object.class.getResourceAsStream("/ApplicationResources.properties");
		}

		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return prop.getProperty(key).trim();
	}

	public static String getDownloadUrl() {
		return getProperty("file_download");
	}

	public static void main(String[] args) {
		System.out.println(getProperty("sms_url"));
	}
}
