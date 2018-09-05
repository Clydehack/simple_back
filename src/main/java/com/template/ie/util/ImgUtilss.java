package com.template.ie.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import net.coobird.thumbnailator.Thumbnails;

public class ImgUtilss {

private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	/* SpringBoot Async注解 */
	@Async("thumbnails_img")	 //如果不指定名字，会使用缺省的“asyncExecutor”
	public static void thumbnailsImg(String filePath, String fileName, int height, int width) throws IOException {
		logger.info("开始");
		Thumbnails.of(filePath + fileName).size(height, width).keepAspectRatio(false).toFile(filePath + fileName);
		logger.info("停止");
	}
}