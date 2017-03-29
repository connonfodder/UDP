package com.aadhk.product.util;

import android.content.Context;
import android.util.Log;
import android.webkit.MimeTypeMap;

import org.acra.ACRA;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

	private static final String TAG = "FileUtil";

	private static final int BUFFER_IMAGE = 2048;
	
	private static final String key = "aadhk";

	public static void copyRaw(Context context, int rawId, String outputPath) {

		try {
			InputStream input = context.getResources().openRawResource(rawId);

			// Path to the external backup
			File outputFile = new File(outputPath);
			if (!outputFile.getParentFile().exists())
			{
				outputFile.getParentFile().mkdirs();
			}
			OutputStream output = new FileOutputStream(outputFile);

			// transfer bytes from the Input File to the Output File
			byte[] buffer = new byte[BUFFER_IMAGE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			output.flush();
			output.close();
			input.close();

		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
	}

	public static boolean isExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	public static File createNewFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		return file;
	}

	public static void deleteExistFile(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

	public static void deleteFilesMoreThan(String folderPath, int limit) {
		File dir = new File(folderPath);
		File[] files = dir.listFiles();
		Arrays.sort(files);
		for (int i = 0; i < files.length - limit; i++) {
			files[i].delete();
		}
	}

	public static void deleteFileByDay(String folderPath, int day) {
		File dir = new File(folderPath);
		File[] files = dir.listFiles();
		Arrays.sort(files);
		for (int i = 0; i < files.length - day; i++) {
			if (files[i].exists()) {
				if (files[i].toString().endsWith("_autobackup.db")) {
					Calendar time = Calendar.getInstance();
					time.add(Calendar.DAY_OF_YEAR, -day);
					Log.i("jack", "files[i].lastModified()===" + files[i].lastModified());
					//上次修改的时间  + 预设删除的时间   > 当前时间   => 上次修改时间  > 当前时间  - 预设删除时间
					Date lastModified = new Date(files[i].lastModified());
					//					Log.i("jack","time.getTime()****>" + time.getTime());     
					//					Log.i("jack","lastModified--->" + lastModified);
					//					Log.i("jack",lastModified.before(time.getTime())+"");
					if (lastModified.before(time.getTime())) {
						files[i].delete();
						Log.i("jack", "delete" + day);
					}
				}
			}
		}
	}

	public static String[] fileNames(String folderPath) {
		File dir = new File(folderPath);
		File[] files = dir.listFiles();
		String[] names = new String[files.length];
		Arrays.sort(files);
		for (int i = 0; i < files.length; i++) {
			names[i] = files[i].getName();
		}
		return names;
	}

	public static String[] listFilesMatching(File root, String regex) {

		if (!root.isDirectory()) {
			throw new IllegalArgumentException(root + " is no directory.");
		}
		final Pattern p = Pattern.compile(regex); // careful: could also throw
													// an exception!
		File[] files = root.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return p.matcher(file.getName()).matches();
			}
		});
		String[] result = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			result[i] = files[i].getName();
		}
		return result;
	}

	public static void zip(String zipFile, String folder, String[] fileNames) {
		try {
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(zipFile);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

			byte data[] = new byte[BUFFER_IMAGE];

			for (int i = 0; i < fileNames.length; i++) {
				Log.v("Compress", "Adding: " + fileNames[i]);
				FileInputStream fi = new FileInputStream(folder + fileNames[i]);
				origin = new BufferedInputStream(fi, BUFFER_IMAGE);
				ZipEntry entry = new ZipEntry(fileNames[i].substring(fileNames[i].lastIndexOf("/") + 1));
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER_IMAGE)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
			dest.close();
		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
	}

	public static byte[] getZipFile(String path) {
		File file = new File(path);
		byte[] data = null;
		if (file.exists()) {
			BufferedInputStream input = null;
			ByteArrayOutputStream outPut = null;
			byte[] buffer = new byte[1024];
			int length;
			try {
				input = new BufferedInputStream(new FileInputStream(file));
				outPut = new ByteArrayOutputStream();
				while ((length = input.read(buffer)) != -1) {
					outPut.write(buffer, 0, length);
				}
				data = outPut.toByteArray();
				input.close();
				outPut.close();
			} catch (IOException e) {
				ACRA.getErrorReporter().handleException(e);
				e.printStackTrace();
			}
		}
		return data;
	}

	//獲取圖片數據
	public static byte[] getImageFile(String path) {
		File file = new File(path);
		byte[] data = null;
		if (file.exists()) {
			BufferedInputStream input = null;
			ByteArrayOutputStream outPut = null;
			byte[] buffer = new byte[1024];
			int length;
			try {
				input = new BufferedInputStream(new FileInputStream(file));
				outPut = new ByteArrayOutputStream();
				while ((length = input.read(buffer)) != -1) {
					outPut.write(buffer, 0, length);
				}
				data = outPut.toByteArray();
				input.close();
				outPut.close();
			} catch (IOException e) {
				ACRA.getErrorReporter().handleException(e);
				e.printStackTrace();
			}
		}
		return data;
	}

	/*
	 * AADHK_20121001-10121031
	 */
	public static String filterFileName(String value) {
		// 表示任何一個字元與數字以及 '_' 以外的字元
		if (value != null) {
			return value.replaceAll("[\\W]", "");
		}
		return value;
	}

	/*
	 * public static String filterFileName(String fileName) { String
	 * reservedChars = "|\\?*<\":>+[]/'"; char replaceChar = '_'; for(char c
	 * :reservedChars.toCharArray()){ int index = fileName.indexOf(c);
	 * if(index>-1){ fileName = fileName.replace(c, replaceChar); } } return
	 * fileName; }
	 */

	public static String getDateFileName() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.FILE_DATE_FORMAT, Locale.US);
		return sdf.format(cal.getTime());
	}

	public static String getFileNameByDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
		return sdf.format(cal.getTime());
	}

	public static String getCameraFileName() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
		String fileName = sdf.format(cal.getTime());
		return String.format("%s.jpg", fileName);
	}

	public static void copyFile(String sourcePath, String targetPath ) throws IOException {

		File sourceFile = new File(sourcePath);
		if (sourceFile.exists()) {

			// Local database
			InputStream input = new FileInputStream(sourceFile);

			// Path to the external backup
			OutputStream output = new FileOutputStream(targetPath);

			try {
				// transfer bytes from the Input File to the Output File
				byte[] buffer = new byte[1024];
				int length;
				while ((length = input.read(buffer)) > 0) {
//					encrypt(buffer, length, key);
					output.write(buffer, 0, length);
				}
				output.flush();
			} finally {
				if (output != null) {
					output.close();
				}
				if (input != null) {
					input.close();
				}
			}
		}
	}
	
	private static void encrypt(byte[] data, int length, String key) {
		char[] keys = key.toCharArray();
		int len = keys.length;
		int seek = 0;
		for (int i = 0; i < length; i++) {
			data[i] ^= keys[seek];
			seek = (seek == len - 1) ? 0 : ++seek;
		}
	}
	
	/*
	 * 解密, 供开发人员使用
	 */
/*	public static void decrypt(String path) throws IOException{
		int breakPoint = path.lastIndexOf(".");
		String target = path.substring(0, breakPoint) + "_decrypt" + path.substring(breakPoint, path.length());
		FileInputStream iis = new FileInputStream(new File(path));
		System.out.println(target);
		File tem = new File(target);
		if(tem.exists()){
			tem.delete();
		}
		tem.createNewFile();
		FileOutputStream oos = new FileOutputStream(tem);
		byte[] buff = new byte[1024];
		int hasRead = 0;
		while ((hasRead = iis.read(buff)) > 0) {
			encrypt(buff, hasRead, key);
			oos.write(buff, 0, hasRead);
		}
		oos.flush();
		oos.close();
		iis.close();
	}
*/
	public static void writeFile(String content, String filePath) throws IOException {
		//Log.i(TAG, "content:"+content);
		File file = new File(filePath);
		file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		OutputStreamWriter outWriter = new OutputStreamWriter(out);
		try {
			outWriter.append(content);
		} finally {
			if (outWriter != null) {
				outWriter.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	public static void writeFile(InputStream inputStream, String filePath) throws IOException {
		OutputStream outputStream = new FileOutputStream(filePath);
		byte[] buffer = new byte[8 * 1024];
		int length;

		try {
			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}
			outputStream.flush();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	public static void writeString(String data, String filePath) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(data);

			writer.flush();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public static void writeByte(byte[] byteArray, String filePath) throws IOException {
		FileOutputStream fos = new FileOutputStream(filePath);
		try {
			//			Arrays.toString(byteArray)
			fos.write(byteArray);
			fos.flush();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	public static void writeStream(OutputStream outputStream, String filePath) throws IOException {
		FileInputStream is = new FileInputStream(filePath);
		BufferedInputStream in = new BufferedInputStream(is);
		BufferedOutputStream out = new BufferedOutputStream(outputStream);

		byte[] buffer = new byte[8 * 1024];

		try {
			int n = 0;
			while ((n = in.read(buffer)) > 0) {
				out.write(buffer, 0, n);
			}
			out.flush();
			out.close();
			in.close();
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	public static String readFile(String filePath) throws IOException {
		String aBuffer = "";
		File file = new File(filePath);
		if (file.exists()) {

			FileInputStream in = new FileInputStream(file);
			BufferedReader inReader = new BufferedReader(new InputStreamReader(in));
			try {
				String aDataRow = "";
				while ((aDataRow = inReader.readLine()) != null) {
					aBuffer += aDataRow;
				}
			} finally {
				if (inReader != null) {
					inReader.close();
				}
			}

		}
		return aBuffer;
	}

	public static String getMimeType(String url) {
		return MimeTypeMap.getSingleton().getMimeTypeFromExtension(url);
	}

	public static String getFileSize(String filePath) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			long size = file.length();
			if (size >= gb) {
				return String.format("%.1f GB", (float) size / gb);
			} else if (size >= mb) {
				float f = (float) size / mb;
				return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
			} else if (size >= kb) {
				float f = (float) size / kb;
				return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
			} else
				return String.format("%d B", size);
		} else {
			return "";
		}
	}
}
