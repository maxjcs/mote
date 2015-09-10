package com.longcity.modeler.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipFileUtil {
	private static Logger logger = LoggerFactory.getLogger(ZipFileUtil.class);
	static final int BUFFER = 2048;

	@SuppressWarnings("rawtypes")
	public static File[] unzip(File wbZipFile, String baseDir) {
		if (wbZipFile == null || StringUtils.isEmpty(baseDir)) {
			logger.error("wbFile or baseDir is null");
			return null;
		}
		String distDir = null;
		try {
			String name = wbZipFile.getName();
			String fullDir = baseDir + File.separator + name;
			File fileDir = new File(fullDir);
			if (fileDir.exists()) {
				fileDir.delete();
			}
			fileDir.mkdir();
			//创建zip文件同名目录
			distDir = fileDir.getAbsolutePath();
			ZipFile zipFile = new ZipFile(wbZipFile);
			Enumeration emu = zipFile.entries();
			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();
				BufferedInputStream bis = new BufferedInputStream(
						zipFile.getInputStream(entry));
				File file = new File(fullDir + File.separator + entry.getName());
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
				int count;
				byte data[] = new byte[BUFFER];
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					bos.write(data, 0, count);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
			zipFile.close();
		} catch (Exception e) {
			logger.error("unzip fail:", e);
			return null;
		}
		if (distDir != null) {
			File listDir = new File(distDir);
			File[] files = listDir.listFiles();
			listDir.delete();
			return files;
		}
		return null;
	}

	public void testUnzip() {

	}
}
