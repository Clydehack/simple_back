package com.template.ie.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;

public class ImageThread extends Thread {

	private static Logger logger = LoggerFactory.getLogger(ImageThread.class); 
	
//	private byte[] file;
//	private String filePath;
//	private String fileName;
//	private Boolean bo;
//	private int height;
//	private int width;
//	
//	public ImageThread (byte[] file, String filePath, String fileName, 
//			Boolean bo, int height, int width) {
//		this.file = file;
//		this.filePath = filePath;
//		this.fileName = fileName;
//		this.bo = bo;
//		this.height = height;
//		this.width = width;
//	}
//	
//	@Override
//	public void run() {
//		try {
//			FileUtil.uploadFile(file, filePath, fileName, bo, height, width);
//			System.out.println("线程");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	private String filePath;
	private String fileName;
	private int height;
	private int width;
	
	public ImageThread (String filePath, String fileName, int height, int width) {
		this.filePath = filePath;
		this.fileName = fileName;
		this.height = height;
		this.width = width;
	}
	
	@Override
	public void run() {
		try {
			logger.info("开始");
			Thumbnails.of(filePath + fileName).size(height, width).keepAspectRatio(false).toFile(filePath + fileName);
			logger.info("停止");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}