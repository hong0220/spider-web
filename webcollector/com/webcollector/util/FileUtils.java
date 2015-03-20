package com.webcollector.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

	public static void deleteDir(File dir) {
		File[] filelist = dir.listFiles();
		for (File file : filelist) {
			if (file.isFile()) {
				file.delete();
			} else {
				deleteDir(file);
			}
		}
		dir.delete();
	}

	public static void copy(File origin, File newfile)
			throws FileNotFoundException, IOException {
		if (!newfile.getParentFile().exists()) {
			newfile.getParentFile().mkdirs();
		}
		FileInputStream fis = new FileInputStream(origin);
		FileOutputStream fos = new FileOutputStream(newfile);
		byte[] buf = new byte[2048];
		int read;
		while ((read = fis.read(buf)) != -1) {
			fos.write(buf, 0, read);
		}
		fis.close();
		fos.close();
	}

	public static void writeFile(String filename, byte[] content)
			throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(filename);
		fos.write(content);
		fos.close();
	}

	public static void writeFileWithParent(String filename, byte[] content)
			throws FileNotFoundException, IOException {
		File file = new File(filename);
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(content);
		fos.close();
	}

	public static void writeFileWithParent(File file, byte[] content)
			throws FileNotFoundException, IOException {
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(content);
		fos.close();
	}

	public static byte[] readFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[2048];
		int read;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((read = fis.read(buf)) != -1) {
			bos.write(buf, 0, read);
		}
		fis.close();
		return bos.toByteArray();
	}

	// 读取某个文件夹下的所有文件
	public static String[] readfile(String filepath)
			throws FileNotFoundException, IOException {
		File file = new File(filepath);
		if (!file.isDirectory()) {
			System.out.println("文件");
			System.out.println("path=" + file.getPath());
			System.out.println("absolutepath=" + file.getAbsolutePath());
			System.out.println("name=" + file.getName());
		} else if (file.isDirectory()) {
			System.out.println("文件夹");
			String[] fileList = file.list();
			// for (int i = 0; i < fileList.length; i++) {
			// System.out.println(fileList[i]);
			// File readList = new File(filepath + "\\" + fileList[i]);
			// if (!readList.isDirectory()) {
			// System.out.println("path=" + readList.getPath());
			// System.out.println("absolutepath="
			// + readList.getAbsolutePath());
			// System.out.println("name=" + readList.getName());
			// } else if (readList.isDirectory()) {
			// readfile(filepath + "\\" + fileList[i]);
			// }
			// }
			return fileList;
		}
		return null;
	}

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 */
	/*
	 * public static boolean deletefile(String delpath) throws
	 * FileNotFoundException, IOException { try {
	 * 
	 * File file = new File(delpath); if (!file.isDirectory()) {
	 * System.out.println("1"); file.delete(); } else if (file.isDirectory()) {
	 * System.out.println("2"); String[] filelist = file.list(); for (int i = 0;
	 * i < filelist.length; i++) { File delfile = new File(delpath + "\\" +
	 * filelist[i]); if (!delfile.isDirectory()) { System.out.println("path=" +
	 * delfile.getPath()); System.out.println("absolutepath=" +
	 * delfile.getAbsolutePath()); System.out.println("name=" +
	 * delfile.getName()); delfile.delete(); System.out.println("删除文件成功"); }
	 * else if (delfile.isDirectory()) { deletefile(delpath + "\\" +
	 * filelist[i]); } } file.delete(); } } catch (FileNotFoundException e) {
	 * System.out.println("deletefile()   Exception:" + e.getMessage()); }
	 * return true; }
	 */

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		String[] fileList = readfile("xml");
		for (int i = 0; i < fileList.length; i++) {
			File readfile = new File("xml" + "\\" + fileList[i]);
			System.out.println(readfile.getPath());
		}
		// deletefile("D:/file");
	}
}
