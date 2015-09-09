package com.xuexibao.teacher.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author oldlu
 *
 */
public class IOUtil {
	private static Logger log = LoggerFactory.getLogger(IOUtil.class);

	public interface LineFilter {
		boolean filter(String line, int num);
	}

	private static final int DEFAULT_BUFFER_SIZE = 4096;

	private static final File[] EMPTY = new File[0];

	public static byte[] asByteArray(File file) throws IOException {
		InputStream in = new FileInputStream(file);
		byte[] result = asByteArray(in, (int) file.length());
		in.close();
		return result;
	}

	/**
	 * 注意这个方法会将数据流全部读入到内存中，因此不适用于很大的数据对象
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] asByteArray(InputStream in) throws IOException {
		byte[] msg = asByteArray(in, -1);
		in.close();
		return msg;
	}

	/**
	 * 从流中读取
	 * 
	 * @param in
	 * @param length
	 *            要读取的字节数，-1表示不限制。（注意实际处理中-1的情况下最多读取2G数据，超过2G不会读取）
	 * @return
	 * @throws IOException
	 */
	public static byte[] asByteArray(InputStream in, int length) throws IOException {
		ByteArrayOutputStream out;
		if (length > 0) {
			out = new ByteArrayOutputStream(length);
		} else {
			out = new ByteArrayOutputStream(1024);
		}
		byte[] pBuffer = new byte[DEFAULT_BUFFER_SIZE];
		int left = (length > 0) ? length : Integer.MAX_VALUE;// 剩余字节数
		while (left > 0) {
			int n;
			if (left < DEFAULT_BUFFER_SIZE) {
				n = in.read(pBuffer, 0, left);
			} else {
				n = in.read(pBuffer);
			}
			if (n == -1)
				break;
			left -= n;
			out.write(pBuffer, 0, n);
		}
		out.close();
		byte[] message = out.toByteArray();
		return message;
	}

	public static InputStream asInputStream(byte[] bytes) {
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * 将输入流转化为String .(使用缺省的字符集)
	 */
	public static String asString(InputStream pStream) throws IOException {
		return asString(pStream, null, true);
	}

	/**
	 * 将输入流转化为String
	 * 
	 * @param pStream
	 *            The input stream to read.
	 * @param pEncoding
	 *            The character encoding, typically "UTF-8".
	 */
	public static String asString(InputStream pStream, String pEncoding, boolean close) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copy(pStream, baos, true);
		if (close)
			close(pStream);
		if (pEncoding == null) {
			return baos.toString();
		} else {
			return baos.toString(pEncoding);
		}
	}

	public static String asString(Reader reader) throws IOException {
		return asString(reader, true);
	}

	public static String asString(Reader reader, boolean close) throws IOException {
		StringBuilder sb = new StringBuilder();
		char[] buf = new char[1024];
		int n;
		while ((n = reader.read(buf)) > -1) {
			sb.append(buf, 0, n);
		}
		if (close)
			reader.close();
		return sb.toString();
	}

	private static void checkDir(File file) {
		File par = file.getParentFile();
		if (par != null && !par.exists())
			par.mkdirs();
	}

	/**
	 * 关闭指定的对象，不会抛出异常
	 */
	public static void close(Closeable c) {
		if (c == null)
			return;
		try {
			c.close();
		} catch (IOException e) {
		}
	}

	/**
	 * 第一种文件合并实现方式，使用新版本JDK中的Channel操作。
	 * 
	 * @param list
	 * @param path
	 * @param srcFileName
	 * @return
	 */
	public static boolean combine(Collection<String> list, String path, String srcFileName) {
		File outputFile = new File(path + "/" + srcFileName);
		outputFile = escapeExistFile(outputFile);
		try {
			FileOutputStream fou = new FileOutputStream(outputFile);
			FileChannel fco = fou.getChannel();
			long position = 0;
			for (String i : list) {// 按顺序获得各个文件名
				File file = new File(i);// 创建文件
				if (!file.exists()) {
					fou.close();
					return false;
				}
				FileInputStream fin = new FileInputStream(file);
				FileChannel fci = fin.getChannel();
				long len = file.length();
				fco.transferFrom(fci, position, len);// 接收数据到指定的位置
				position += len;
				fin.close();
				fci.close();
			}
			fou.close();
			fco.close();
			return true;
		} catch (Exception e) {
			log.error(StringUtils.EMPTY, e);
			return false;
		}
	}

	private static void compress(ZipOutputStream src, File file, String fileName) {
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream(file);
			src.putNextEntry(new ZipEntry(fileName));
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = fileIn.read(buffer)) != -1) {
				src.write(buffer, 0, len);
			}
			src.closeEntry();
			fileIn.close();
		} catch (FileNotFoundException e) {
			log.error(StringUtils.EMPTY, e);
			throw new RuntimeException("文件 Not Found!!!", e);
		} catch (IOException e) {
			log.error(StringUtils.EMPTY, e);
			throw new RuntimeException("IOException!!!", e);
		}
	}

	/**
	 * 流之间拷贝
	 * 
	 * @param pInputStream
	 * @param pOutputStream
	 * @param pClose
	 *            完成后是否关闭输出流
	 * @return
	 * @throws IOException
	 */
	public static long copy(InputStream pInputStream, OutputStream pOutputStream, boolean pClose) throws IOException {
		return copy(pInputStream, pOutputStream, true, pClose, new byte[DEFAULT_BUFFER_SIZE]);
	}

	/**
	 * 流之间拷贝
	 * 
	 * @param in
	 *            输入
	 * @param out
	 *            输出
	 * @param inClose
	 *            关闭输入流？
	 * @param outClose
	 *            关闭输出流?
	 * @return
	 * @throws IOException
	 */
	public static long copy(InputStream in, OutputStream out, boolean inClose, boolean outClose) throws IOException {
		return copy(in, out, inClose, outClose, new byte[DEFAULT_BUFFER_SIZE]);
	}

	/**
	 * Copies the contents of the given {@link InputStream} to the given
	 * {@link OutputStream}.
	 * 
	 * @param pIn
	 *            The input stream, which is being read. It is guaranteed, that
	 *            {@link InputStream#close()} is called on the stream.
	 *            关于InputStram在何时关闭的问题，我一直认为应当是成对操作的（即在哪个方法中生成Stream，就要在使用完后关闭），
	 *            因此不打算在这里使用close方法。
	 *            但是后来我又考虑到，InputStream在使用完后，其内部标记已经发生了变化，无法再次使用。
	 *            (reset方法的效果和实现有关，并不能保证回复到Stream使用前的状态。)
	 *            因此考虑这里统一关闭以防止疏漏，外面再关一次也不会有问题(作为好习惯，还是应该成对打开和关闭)。
	 * @param pOut
	 *            输出流，可以为null,此时输入流中的相应数据将丢弃
	 * @param pClose
	 *            True guarantees, that {@link OutputStream#close()} is called
	 *            on the stream. False indicates, that only
	 *            {@link OutputStream#flush()} should be called finally.
	 * @param pBuffer
	 *            Temporary buffer, which is to be used for copying data.
	 * @return Number of bytes, which have been copied.
	 * @throws IOException
	 *             An I/O error occurred.
	 */
	private static long copy(InputStream in, OutputStream out, boolean inClose, boolean outClose, byte[] pBuffer) throws IOException {
		if (in == null)
			throw new NullPointerException();
		long total = 0;
		try {
			int res;
			while ((res = in.read(pBuffer)) != -1) {
				if (out != null) {
					out.write(pBuffer, 0, res);
				}
				total += res;
			}
			if (out != null)
				out.flush();
		} finally {
			if (outClose && out != null)
				out.close();
			if (inClose)
				in.close();
		}
		return total;
	}

	private static long copy(Reader in, Writer out, boolean inClose, boolean outClose, char[] pBuffer) throws IOException {
		if (in == null)
			throw new NullPointerException();
		long total = 0;
		try {
			int res;
			while ((res = in.read(pBuffer)) != -1) {
				if (out != null) {
					out.write(pBuffer, 0, res);
				}
				total += res;
			}
			if (out != null)
				out.flush();
		} finally {
			if (outClose && out != null)
				out.close();
			if (inClose)
				in.close();
		}
		return total;
	}

	/**
	 * 文件1拷贝到文件2
	 * 
	 * @param file
	 * @param newFile
	 * @return
	 */
	public static boolean copyFile(File file, File newFile) {
		FileChannel fc2 = null;
		FileInputStream in = null;
		FileOutputStream fou = null;
		boolean flag = false;
		checkDir(file);
		try {
			in = new FileInputStream(file);
			fou = new FileOutputStream(newFile);
			fc2 = fou.getChannel();
			in.getChannel().transferTo(0, file.length(), fc2);
			flag = true;
		} catch (IOException e) {
			log.error(StringUtils.EMPTY, e);
		} finally {
			close(fc2);
			close(fou);
			close(in);
		}
		return flag;
	}

	/**
	 * 将文件拷贝到指定目录下
	 * 
	 * @param tmpFile
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static File copyToFolder(File tmpFile, String path) throws IOException {
		path = path.replace('\\', '/');
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();
		if (!path.endsWith("/"))
			path = path + "/";
		File targetFile = new File(path + tmpFile.getName());
		InputStream in = new FileInputStream(tmpFile);
		copy(in, new FileOutputStream(targetFile), true);
		return targetFile;
	}

	/**
	 * 创建/检查 文件夹
	 * 
	 * @param path
	 */
	public static void createFolder(String path) {
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			throw new RuntimeException("已经有同名的文件存在，不能创建目录。");
		} else if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 文件切割，使用Channel方式
	 * 
	 * @param openFile
	 * @param path
	 * @param size
	 * @return
	 */
	public static int cut(File openFile, String path, int size) {
		try {
			if (!openFile.exists())
				return -1;
			// 创建文件输入流
			FileInputStream fin = new FileInputStream(openFile);
			FileChannel fc1 = fin.getChannel();
			int cnt = 0;// 存储每次读取的字节数
			long position = 0;// 位置
			int nth = 1;
			long len = openFile.length();
			while (position < len) {
				String name = path + "/" + openFile.getName() + "@" + nth;
				FileOutputStream fou = new FileOutputStream(name);
				FileChannel fc2 = fou.getChannel();
				cnt = (int) fc1.transferTo(position, size, fc2);
				fou.close();
				fc2.close();
				nth++;
				position += cnt;
			}
			fin.close();
			fc1.close();
			return nth - 1;
		} catch (IOException e) {
			log.error(StringUtils.EMPTY, e);
			return -1;
		}
	}

	/**
	 * 删除指定文件
	 * 
	 * @param f
	 *            文件
	 * @param includeSub
	 *            是否包含子目录
	 * @return
	 */
	public static final boolean delete(File f, boolean includeSub) {
		if (!f.exists())
			return true;
		if (includeSub) {
			for (File sub : listFolders(f)) {
				if (!delete(sub, true))
					return false;
			}
			for (File sub : listFiles(f)) {
				if (!sub.delete())
					return false;
			}
		}
		return f.delete();
	}

	/**
	 * 确保文件可以创建
	 * 
	 * @param file
	 */
	public static void ensureFileExists(File file) {
		String str = getPath(file);
		File parent = new File(StringUtils.substringBeforeLast(str, "\\"));
		if (!parent.exists()) {
			parent.mkdirs();
		}
		if (!file.exists()) {
			// file.createNewFile();
		}
	}

	/**
	 * 给定一个File,确认这个其不存在于在磁盘上，如果存在就改名以回避
	 * 
	 * @param outputFile
	 * @return
	 */
	public static File escapeExistFile(File outputFile) {
		if (!outputFile.exists())
			return outputFile;
		int pos = outputFile.getName().lastIndexOf(".");
		String path = outputFile.getParent();
		if (StringUtils.isEmpty(path)) {
			throw new RuntimeException(outputFile.getAbsolutePath() + "的getParent返回" + path);
		}
		String baseFilename = null;
		String extName = null;
		if (pos > -1) {
			baseFilename = outputFile.getName().substring(0, pos);
			extName = outputFile.getName().substring(pos + 1);
		} else {
			baseFilename = outputFile.getName();
		}
		int n = 1;
		while (outputFile.exists()) {
			outputFile = new File(path + "/" + baseFilename + "(" + n + ")" + ((extName == null) ? "" : "." + extName));
			n++;
		}
		return outputFile;
	}

	/**
	 * 压缩文件
	 * 
	 * @param destPath
	 * @param srcFile
	 * @return
	 */
	public static String exportZip(String destPath, File srcFile) {
		FileOutputStream fileOut = null;
		String path = destPath + "/" + srcFile.getName() + ".zip";
		try {
			fileOut = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			log.error(StringUtils.EMPTY, e);
			throw new RuntimeException("文件 Not Found!!!", e);
		}
		ZipOutputStream outputStream = new ZipOutputStream(fileOut);
		File[] files = srcFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = (File) files[i];
			if (file.isFile()) {
				compress(outputStream, file, file.getName());
			} else if (file.isDirectory()) {
				File[] subFiles = file.listFiles();
				for (int j = 0; j < subFiles.length; j++) {
					File subFile = (File) subFiles[j];
					if (subFile.isFile()) {
						compress(outputStream, subFile, file.getName() + "/" + subFile.getName());
					}
				}
			}
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			throw new RuntimeException("IOException!!!", e);
		}
		return path;
	}

	/**
	 * 文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean fileExists(String path) {
		File f = new File(path);
		return f.exists();
	}

	public static ObjectInputStream getBlobByte(Blob blob) {
		try {
			InputStream input = blob.getBinaryStream();
			if (input == null) {
				return null;
			}
			return new ObjectInputStream(input);
		} catch (Exception e) {
			log.error(StringUtils.EMPTY, e);
			throw new RuntimeException("Blob读取错误", e);
		}
	}

	public static String getBlobString(Blob blob) {
		try {
			InputStream input = blob.getBinaryStream();
			if (input == null) {
				return null;
			}
			StringBuffer sb = new StringBuffer();
			byte[] charbuf = new byte[4096];
			for (int i = input.read(charbuf); i > 0; i = input.read(charbuf)) {
				sb.append(new String(charbuf, 0, i));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException("Blob读取错误", e);
		}
	}

	/**
	 * 得到文件的扩展名（小写如果没有则返回空字符串）
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtName(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos > -1) {
			return fileName.substring(pos + 1).toLowerCase();
		} else {
			return "";
		}
	}

	/**
	 * 返回文件路径。<BR>
	 * getAbsolutePath 并不是唯一的，比如同一个文件， getAbsolutePath()可以返回 C:/TEMP/../book.exe
	 * ,也可以是 C:/book.exe。 而getCanonicalPath()才可以返回真正的文件路径。 <BR>
	 * 问题是这个方法需要抛出一个受检异常，很多时候影响 代码风格的简洁美观。
	 * 
	 * @param file
	 */
	public static String getPath(File file) {
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			log.error(StringUtils.EMPTY, e);
			return file.getAbsolutePath();
		}
	}

	/**
	 * 获得一个供写入文本的Writer
	 * 
	 * @param target
	 * @param charSet
	 * @param append
	 * @return
	 * @throws IOException
	 */
	public static BufferedWriter getWriter(File target, String charSet, boolean append) throws IOException {
		File f = target.getParentFile();
		if (f != null && !f.exists()) {
			f.mkdirs();
		} else if (f != null && f.isFile()) {
			throw new IOException("路径" + f.getAbsolutePath() + "是一个文件，无法创建目录");
		} else {
			ensureFileExists(target);
		}
		OutputStream os = new FileOutputStream(target, append);
		if (charSet == null)
			charSet = Charset.defaultCharset().name();
		OutputStreamWriter osw = new OutputStreamWriter(os, charSet);
		return new BufferedWriter(osw);
	}

	/**
	 * 列出指定目录下的文件
	 * 
	 * @param file
	 * @param extnames
	 * @return
	 */
	public static File[] listFiles(File file, final String... extnames) {
		File[] r = file.listFiles(new FileFilter() {
			public boolean accept(File f) {
				boolean isAll = extnames.length == 0;
				if (f.isFile() && (isAll || ArrayUtils.contains(extnames, getExtName(f.getName())))) {
					return true;
				}
				return false;
			}
		});
		return r == null ? EMPTY : r;
	}

	/**
	 * 列出指定目录下的文件和文件夹，其中文件只列出符合扩展名的文件。
	 * 
	 * @param file
	 * @param extnames
	 *            ，允许列出的扩展名，必须小写。不含.号
	 * @return
	 */
	public static File[] listFilesAndFolders(File file, final String... extnames) {
		File[] r = file.listFiles(new FileFilter() {
			public boolean accept(File f) {
				boolean isAll = extnames.length == 0;
				if (f.isDirectory()) {
					return true;
				}
				if (isAll || ArrayUtils.contains(extnames, getExtName(f.getName()))) {
					return true;
				}
				return false;
			}
		});
		return r == null ? EMPTY : r;
	}

	/**
	 * 递归列出指定目录下的文件（不含文件夹）
	 * 
	 * @param file
	 *            目录
	 * @param extnames
	 *            允许列出的扩展名，必须小写，不含.号
	 * @return
	 */
	public static File[] listFilesRecursive(File file, final String... extnames) {
		List<File> files = new ArrayList<File>();
		for (File folder : listFolders(file)) {
			files.addAll(Arrays.asList(listFilesRecursive(folder, extnames)));
		}
		files.addAll(Arrays.asList(listFiles(file, extnames)));
		return files.toArray(new File[files.size()]);
	}

	/**
	 * 列出指定目录下的文件夹
	 * 
	 * @param file
	 * @return
	 */
	public static File[] listFolders(File file) {
		File[] r = file.listFiles(new FileFilter() {
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				return false;
			}
		});
		return r == null ? EMPTY : r;
	}

	/**
	 * 将文件移动为指定的新文件
	 * 
	 * @param oldFile
	 * @param newFile
	 * @return
	 */
	public static boolean move(File oldFile, File newFile) {
		createFolder(newFile.getParent());
		return oldFile.renameTo(newFile);
	}

	/**
	 * 将文件移动到指定目录下
	 * 
	 * @param oldFile
	 * @param path
	 * @return
	 */
	public static boolean moveToFolder(File oldFile, File folder, boolean autoEscape) {
		if (folder.exists() && folder.isFile()) {
			throw new IllegalArgumentException("Target is a file.(" + folder.getAbsolutePath() + ")");
		}
		if (!folder.exists())
			folder.mkdirs();
		File target = new File(folder, oldFile.getName());
		if (target.exists()) {
			if (autoEscape) {
				target = escapeExistFile(target);
			} else {
				return false;
			}
		}
		if (oldFile.equals(target)) {
			return true;
		}
		return move(oldFile, target);
	}

	/**
	 * 得到文本文件的某几行，使用后文件会关闭 如果指定的行号小于1，会返回第一行
	 * 
	 * @param inName
	 *            要读的文本文件
	 * @param num
	 *            指定的行号,可以指定多行，必须按顺序(如果只指定一个-1，则表示读取全部)
	 * @return
	 * @throws IOException
	 */
	public static String readLine(File inName, int... num) throws IOException {
		BufferedReader is = new BufferedReader(new FileReader(inName));
		String line = null;
		if (num.length == 0)
			num = new int[] { 1 };
		boolean isAll = num[0] == -1;
		StringBuilder sb = new StringBuilder();
		int n = 0;
		while ((line = is.readLine()) != null) {
			n++;
			if (isAll || ArrayUtils.contains(num, n)) {
				if (sb.length() > 0)
					sb.append('\n');
				sb.append(line);
			}
			if (!isAll && n >= num[num.length - 1])
				break;
		}
		is.close();
		return sb.toString();
	}

	public static String[] readLines(File inName, LineFilter filter) throws IOException {
		BufferedReader is = new BufferedReader(new FileReader(inName));
		String line = null;
		List<String> sb = new ArrayList<String>();
		int n = 0;
		while ((line = is.readLine()) != null) {
			if (filter.filter(line, n++)) {
				sb.add(line);
			}
		}
		is.close();
		return sb.toArray(new String[sb.size()]);
	}

	public static void saveAsFile(byte[] data, File file) throws IOException {
		OutputStream out = new FileOutputStream(file);
		out.write(data);
		out.flush();
		out.close();
	}

	/**
	 * 将输入流保存为文件
	 * 
	 * @param is
	 * @param file
	 * @throws IOException
	 */
	public static void saveAsFile(InputStream is, File file) throws IOException {
		checkDir(file);
		BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
		copy(is, os, true);
	}

	/**
	 * 将reader内容保存为文件
	 * 
	 * @param reader
	 * @param file
	 * @throws IOException
	 */
	public static void saveAsFile(Reader reader, File file) throws IOException {
		BufferedWriter os = new BufferedWriter(new FileWriter(file));
		copy(reader, os, true, true, new char[2048]);
	}

	/**
	 * 将文字写入文件
	 * 
	 * @param text
	 * @param file
	 * @param append
	 * @throws IOException
	 */
	public static void saveAsFile(String text, File file, boolean append) throws IOException {
		BufferedWriter os = new BufferedWriter(new FileWriter(file, append));
		os.write(text);
		os.flush();
		os.close();
	}

	/**
	 * 将指定的流保存为临时文件
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static File saveAsTempFile(InputStream is) throws IOException {
		File f = File.createTempFile("~tmp", ".io");
		saveAsFile(is, f);
		return f;
	}

	public static File saveAsTempFile(Reader reader) throws IOException {
		File f = File.createTempFile("~tmp", ".io");
		saveAsFile(reader, f);
		return f;
	}

	/**
	 * 将文件名拆成两部分，后者为扩展名
	 * 
	 * @param name
	 * @return
	 */
	public static String[] splitExt(String name) {
		int n = name.lastIndexOf('.');
		if (n == -1) {
			return new String[] { name, "" };
		} else {
			return new String[] { name.substring(0, n), name.substring(n + 1).toLowerCase() };
		}
	}

	/**
	 * 将URL转换为本地路径
	 * 
	 * @param url
	 * @return
	 */
	public static String urlToPath(URL url) {
		try {
			String path = url.toURI().getPath();
			return path.substring(1);
		} catch (URISyntaxException e) {
			log.error(StringUtils.EMPTY, e);
			throw new RuntimeException(e);
		}
	}
}
