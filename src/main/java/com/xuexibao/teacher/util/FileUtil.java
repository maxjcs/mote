package com.xuexibao.teacher.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.xuexibao.teacher.core.ZipFileUtil;
import com.xuexibao.teacher.exception.BusinessException;

public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	public static boolean isEmpty(MultipartFile mfile) {
		return mfile == null || mfile.isEmpty();
	}

	public static String upload(MultipartFile mfile, String fileName) {
		File file = transfer(mfile, fileName);
		return upload(file);
	}

	public static String upload(MultipartFile mfile) {
		String fileName = UUID.randomUUID().toString();
		File file = transfer(mfile, fileName);
		return upload(file);
	}

	public static List<Map<String, String>> uploadZip(MultipartFile mfile , String fileName) {
		String baseDir = PropertyUtil.getProperty("zip_store_path");
		File zipFile = transfer(mfile, fileName);
		File[] files = ZipFileUtil.unzip(zipFile, baseDir);
		List<Map<String, String>> filesList = new ArrayList<Map<String, String>>();
		for (File f : files) {
			String url = upload(f);
			Map<String, String> fileMap = new HashMap<String, String>();
			String fName = f.getName();
			fileMap.put("fileType", fName);
			fileMap.put("fileUrl", url);

			filesList.add(fileMap);
		}
		return filesList;
	}
	public static File multipartFileCopyFile(MultipartFile mfile,String uniqfileNameWithZip) throws IOException{
		if(mfile == null || StringUtils.isEmpty(uniqfileNameWithZip)){
			logger.error("mfile or uniqfileNameWithZip is null");
			return null;
		}
		String baseDir = PropertyUtil.getProperty("zip_store_path");
		File destFile = new File(baseDir + File.separator + uniqfileNameWithZip);
		FileCopyUtils.copy(mfile.getBytes(), destFile);
		return destFile;
	}

	/**
	 * 在指定文件服务器上上传文件
	 */
	public static String upload(File file) {
		try {
			String url = PropertyUtil.getProperty("file_upload");
			String downUrl = PropertyUtil.getProperty("file_download");
			PostMethod filePost = new PostMethod(url);
			Part[] parts = new Part[] { new FilePart(file.getName(), file) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));

			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			client.executeMethod(filePost);
			String responseStr = filePost.getResponseBodyAsString();
			if (StringUtils.isNotBlank(responseStr)) {
				Map<String, Object> responseMap = new ObjectMapper().readValue(responseStr,
						new TypeReference<Map<String, Object>>() {
						});
				return downUrl + responseMap.get("fid").toString();
			}
		} catch (Exception e) {
			logger.error("upload error:",e);
			throw new BusinessException("文件上传失败", e);
		}
		return null;
	}

	

	/**
	 * 在指定文件服务器上删除文件
	 */
	public static void delete(String fileId) {
		try {
			String url = PropertyUtil.getProperty("file_download");
			DeleteMethod httpDelete = new DeleteMethod(url + fileId);

			HttpClient client = new HttpClient();
			client.executeMethod(httpDelete);
		} catch (Exception e) {
			logger.error("file delete error:",e);
			throw new BusinessException("文件删除错误", e);
		}
	}

	private   static File transfer(MultipartFile mfile, String fileName) {
		try {
			File tempDir = new File(System.getProperty("java.io.tmpdir"));
			File file = new File(tempDir, fileName);
			mfile.transferTo(file);
			return file;
		} catch (Exception e) {
			logger.error("transfer error:",e);
			throw new BusinessException("文件转换错误", e);
		}
	}

	public static String generateFileName(MultipartFile mfile, String name) {
		String extendName = getExtendName(mfile.getOriginalFilename());
		if (StringUtils.isNotBlank(extendName)) {
			return name + "." + extendName;
		}
		return name;
	}

	public static String getExtendName(String name) {
		String[] parts = StringUtils.split(name, ".");
		if (parts != null && parts.length > 0) {
			int length = parts.length;
			return parts[length - 1];
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 用base64编码后的串转成file
	 */
	public static File stringToFile(String byteStr, String[] alowType, String fileName, long maxSize) {
		String[] studentCardArray = StringUtils.splitByWholeSeparator(byteStr, "base64,");
		if (studentCardArray.length != 2) {
			return null;
		} else {
			String[] mimePart = StringUtils.split(fileName, ".");
			if (mimePart.length != 2) {
				return null;
			} else {
				String fileContent = studentCardArray[1];
				String extendName = mimePart[1];

				if (!isAllowType(alowType, extendName)) {
					return null;
				}

				byte[] fileBytes = Base64.decode(fileContent);
				String uuid = UUID.randomUUID().toString();
				File file = FileUtil.byteToFile(fileBytes, uuid, extendName);

				if (file.length() > maxSize) {
					return null;
				}

				return file;
			}
		}
	}

	public static boolean isAllowType(String[] fileType, String extendName) {
		for (String type : fileType) { 
			if (StringUtils.endsWith(StringUtils.lowerCase(extendName), type)) {
				return true;
			}
		}
		return false;
	}

	public static File byteToFile(byte[] bytes, String newFileName, String extendName) {
		String fullName = newFileName;
		if (StringUtils.isNotBlank(extendName)) {
			fullName = newFileName + "." + extendName;
		}

		String filePath = System.getProperty("java.io.tmpdir");
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fullName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bytes);
		} catch (Exception e) {
			logger.error("byteToFile error:",e);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					logger.error("IOException error:",e1);	
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e2) {
					logger.error("IOException error:",e2);	
				}
			}
		}
		return file;
	}
}
