package com.template.ie.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {

	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	/* SpringBoot Async注解 */
	@Async("thumbnails_img")	 //如果不指定名字，会使用缺省的“asyncExecutor”
	public void thumbnailsImg(String filePath, String fileName, int height, int width) throws IOException {
		logger.info("开始");
		Thumbnails.of(filePath + fileName).size(height, width).keepAspectRatio(false).toFile(filePath + fileName);
		logger.info("停止");
	}
	
    /**
     * 
     * @param args
     * @throws IOException
     */
//    public static void main(String[] args) throws IOException {
//    	ImageUtil thumbnailatorTest = new ImageUtil();
//        thumbnailatorTest.test1();
//        thumbnailatorTest.test2();
//        thumbnailatorTest.test3();
//        thumbnailatorTest.test4();
//        thumbnailatorTest.test5();
//        thumbnailatorTest.test6();
//        thumbnailatorTest.test7();
//        thumbnailatorTest.test8();
//        thumbnailatorTest.test9();
//    }

    /**
     * 指定大小进行缩放
     * 
     * @throws IOException
     */
    private void test1() throws IOException {
        /*
         * size(width,height) 若图片横比200小，高比300小，不变
         * 若图片横比200小，高比300大，高缩小到300，图片比例不变 若图片横比200大，高比300小，横缩小到200，图片比例不变
         * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
         */
        Thumbnails.of("images/test.jpg").size(200, 300).toFile("C:/image_200x300.jpg");
        Thumbnails.of("images/test.jpg").size(2560, 2048).toFile("C:/image_2560x2048.jpg");
    }

    /**
     * 按照比例进行缩放
     * 
     * @throws IOException
     */
    private void test2() throws IOException {
        /**
         * scale(比例)
         */
        Thumbnails.of("images/test.jpg").scale(0.25f).toFile("d:/image_25%.jpg");
        Thumbnails.of("images/test.jpg").scale(1.10f).toFile("d:/image_110%.jpg");
    }

    /**
     * 不按照比例，指定大小进行缩放
     * 
     * @throws IOException
     */
    private void test3() throws IOException {
        /**
         * keepAspectRatio(false) 默认是按照比例缩放的
         */
        Thumbnails.of("d:/DSC_8813.jpg").size(1024, 768).keepAspectRatio(false).toFile("d:/DSC_8813.jpg");
    }

    /**
     * 旋转
     * 
     * @throws IOException
     */
    private void test4() throws IOException {
        /**
         * rotate(角度),正数：顺时针 负数：逆时针
         */
        Thumbnails.of("images/test.jpg").size(1280, 1024).rotate(90).toFile("C:/image+90.jpg");
        Thumbnails.of("images/test.jpg").size(1280, 1024).rotate(-90).toFile("C:/iamge-90.jpg");
    }

    /**
     * 水印
     * 
     * @throws IOException
     */
    private void test5() throws IOException {
        /**
         * watermark(位置，水印图，透明度)
         */
        Thumbnails.of("images/test.jpg").size(1280, 1024).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("images/watermark.png")), 0.5f)
                .outputQuality(0.8f).toFile("C:/image_watermark_bottom_right.jpg");
        Thumbnails.of("images/test.jpg").size(1280, 1024).watermark(Positions.CENTER, ImageIO.read(new File("images/watermark.png")), 0.5f)
                .outputQuality(0.8f).toFile("C:/image_watermark_center.jpg");
    }

    /**
     * 裁剪
     * 
     * @throws IOException
     */
    private void test6() throws IOException {
        /**
         * 图片中心400*400的区域
         */
        Thumbnails.of("images/test.jpg").sourceRegion(Positions.CENTER, 400, 400).size(200, 200).keepAspectRatio(false)
                .toFile("C:/image_region_center.jpg");
        /**
         * 图片右下400*400的区域
         */
        Thumbnails.of("images/test.jpg").sourceRegion(Positions.BOTTOM_RIGHT, 400, 400).size(200, 200).keepAspectRatio(false)
                .toFile("C:/image_region_bootom_right.jpg");
        /**
         * 指定坐标
         */
        Thumbnails.of("images/test.jpg").sourceRegion(600, 500, 400, 400).size(200, 200).keepAspectRatio(false).toFile("C:/image_region_coord.jpg");
    }

    /**
     * 转化图像格式
     * 
     * @throws IOException
     */
    private void test7() throws IOException {
        /**
         * outputFormat(图像格式)
         */
        Thumbnails.of("images/test.jpg").size(1280, 1024).outputFormat("png").toFile("C:/image_1280x1024.png");
        Thumbnails.of("images/test.jpg").size(1280, 1024).outputFormat("gif").toFile("C:/image_1280x1024.gif");
    }

    /**
     * 输出到OutputStream
     * 
     * @throws IOException
     */
    private void test8() throws IOException {
        /**
         * toOutputStream(流对象)
         */
        OutputStream os = new FileOutputStream("C:/image_1280x1024_OutputStream.png");
        Thumbnails.of("images/test.jpg").size(1280, 1024).toOutputStream(os);
    }

    /**
     * 输出到BufferedImage
     * 
     * @throws IOException
     */
    private void test9() throws IOException {
        /**
         * asBufferedImage() 返回BufferedImage
         */
        BufferedImage thumbnail = Thumbnails.of("images/test.jpg").size(1280, 1024).asBufferedImage();
        ImageIO.write(thumbnail, "jpg", new File("C:/image_1280x1024_BufferedImage.jpg"));
    }
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
//	public static String IMAGE_TYPE_GIF = "gif";	// 图形交换格式
//    public static String IMAGE_TYPE_JPG = "jpg";	// 联合照片专家组
//    public static String IMAGE_TYPE_JPEG = "jpeg";	// 联合照片专家组
//    public static String IMAGE_TYPE_BMP = "bmp";	// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
//    public static String IMAGE_TYPE_PNG = "png";	// 可移植网络图形
//    public static String IMAGE_TYPE_PSD = "psd";	// Photoshop的专用格式Photoshop
//    
//    public final static void scale(String srcImageFile, String result,
//            int scale, boolean flag) {
//        try {
//            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
//            int width = src.getWidth(); // 得到源图宽
//            int height = src.getHeight(); // 得到源图长
//            if (flag) {// 放大
//                width = width * scale;
//                height = height * scale;
//            } else {// 缩小
//                width = width / scale;
//                height = height / scale;
//            }
//            Image image = src.getScaledInstance(width, height,
//                    Image.SCALE_DEFAULT);
//            BufferedImage tag = new BufferedImage(width, height,
//                    BufferedImage.TYPE_INT_RGB);
//            Graphics g = tag.getGraphics();
//            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
//            g.dispose();
//            ImageIO.write(tag, "PNG", new File(result));// 输出到文件流
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @SuppressWarnings("static-access")
//	public final static void scale2(String srcImageFile, String result, int height, int width, boolean bb) {
//        try {
//            double ratio = 0.0; // 缩放比例
//            File f = new File(srcImageFile);
//            BufferedImage bi = ImageIO.read(f);
//            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
//            // 计算比例
//            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
//                if (bi.getHeight() > bi.getWidth()) {
//                    ratio = (new Integer(height)).doubleValue()
//                            / bi.getHeight();
//                } else {
//                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
//                }
//                AffineTransformOp op = new AffineTransformOp(AffineTransform
//                        .getScaleInstance(ratio, ratio), null);
//                itemp = op.filter(bi, null);
//            }
//            if (bb) {//补白
//                BufferedImage image = new BufferedImage(width, height,
//                        BufferedImage.TYPE_INT_RGB);
//                Graphics2D g = image.createGraphics();
//                g.setColor(Color.white);
//                g.fillRect(0, 0, width, height);
//                if (width == itemp.getWidth(null))
//                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
//                            itemp.getWidth(null), itemp.getHeight(null),
//                            Color.white, null);
//                else
//                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
//                            itemp.getWidth(null), itemp.getHeight(null),
//                            Color.white, null);
//                g.dispose();
//                itemp = image;
//            }
//            ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    
//   
//    public final static void cut(String srcImageFile, String result,
//            int x, int y, int width, int height) {
//        try {
//            // 读取源图像
//            BufferedImage bi = ImageIO.read(new File(srcImageFile));
//            int srcWidth = bi.getHeight(); // 源图宽度
//            int srcHeight = bi.getWidth(); // 源图高度
//            if (srcWidth > 0 && srcHeight > 0) {
//                Image image = bi.getScaledInstance(srcWidth, srcHeight,
//                        Image.SCALE_DEFAULT);
//                // 四个参数分别为图像起点坐标和宽高
//                // 即: CropImageFilter(int x,int y,int width,int height)
//                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
//                Image img = Toolkit.getDefaultToolkit().createImage(
//                        new FilteredImageSource(image.getSource(),
//                                cropFilter));
//                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//                Graphics g = tag.getGraphics();
//                g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
//                g.dispose();
//                // 输出为文件
//                ImageIO.write(tag, "JPEG", new File(result));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//   
//    public final static void cut2(String srcImageFile, String descDir,
//            int rows, int cols) {
//        try {
//            if(rows<=0||rows>20) rows = 2; // 切片行数
//            if(cols<=0||cols>20) cols = 2; // 切片列数
//            // 读取源图像
//            BufferedImage bi = ImageIO.read(new File(srcImageFile));
//            int srcWidth = bi.getHeight(); // 源图宽度
//            int srcHeight = bi.getWidth(); // 源图高度
//            if (srcWidth > 0 && srcHeight > 0) {
//                Image img;
//                ImageFilter cropFilter;
//                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
//                int destWidth = srcWidth; // 每张切片的宽度
//                int destHeight = srcHeight; // 每张切片的高度
//                // 计算切片的宽度和高度
//                if (srcWidth % cols == 0) {
//                    destWidth = srcWidth / cols;
//                } else {
//                    destWidth = (int) Math.floor(srcWidth / cols) + 1;
//                }
//                if (srcHeight % rows == 0) {
//                    destHeight = srcHeight / rows;
//                } else {
//                    destHeight = (int) Math.floor(srcWidth / rows) + 1;
//                }
//                // 循环建立切片
//                // 改进的想法:是否可用多线程加快切割速度
//                for (int i = 0; i < rows; i++) {
//                    for (int j = 0; j < cols; j++) {
//                        // 四个参数分别为图像起点坐标和宽高
//                        // 即: CropImageFilter(int x,int y,int width,int height)
//                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight,
//                                destWidth, destHeight);
//                        img = Toolkit.getDefaultToolkit().createImage(
//                                new FilteredImageSource(image.getSource(),
//                                        cropFilter));
//                        BufferedImage tag = new BufferedImage(destWidth,
//                                destHeight, BufferedImage.TYPE_INT_RGB);
//                        Graphics g = tag.getGraphics();
//                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
//                        g.dispose();
//                        // 输出为文件
//                        ImageIO.write(tag, "JPEG", new File(descDir
//                                + "_r" + i + "_c" + j + ".jpg"));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//   
//    public final static void cut3(String srcImageFile, String descDir,
//            int destWidth, int destHeight) {
//        try {
//            if(destWidth<=0) destWidth = 200; // 切片宽度
//            if(destHeight<=0) destHeight = 150; // 切片高度
//            // 读取源图像
//            BufferedImage bi = ImageIO.read(new File(srcImageFile));
//            int srcWidth = bi.getHeight(); // 源图宽度
//            int srcHeight = bi.getWidth(); // 源图高度
//            if (srcWidth > destWidth && srcHeight > destHeight) {
//                Image img;
//                ImageFilter cropFilter;
//                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
//                int cols = 0; // 切片横向数量
//                int rows = 0; // 切片纵向数量
//                // 计算切片的横向和纵向数量
//                if (srcWidth % destWidth == 0) {
//                    cols = srcWidth / destWidth;
//                } else {
//                    cols = (int) Math.floor(srcWidth / destWidth) + 1;
//                }
//                if (srcHeight % destHeight == 0) {
//                    rows = srcHeight / destHeight;
//                } else {
//                    rows = (int) Math.floor(srcHeight / destHeight) + 1;
//                }
//                // 循环建立切片
//                // 改进的想法:是否可用多线程加快切割速度
//                for (int i = 0; i < rows; i++) {
//                    for (int j = 0; j < cols; j++) {
//                        // 四个参数分别为图像起点坐标和宽高
//                        // 即: CropImageFilter(int x,int y,int width,int height)
//                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight,
//                                destWidth, destHeight);
//                        img = Toolkit.getDefaultToolkit().createImage(
//                                new FilteredImageSource(image.getSource(),
//                                        cropFilter));
//                        BufferedImage tag = new BufferedImage(destWidth,
//                                destHeight, BufferedImage.TYPE_INT_RGB);
//                        Graphics g = tag.getGraphics();
//                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
//                        g.dispose();
//                        // 输出为文件
//                        ImageIO.write(tag, "JPEG", new File(descDir
//                                + "_r" + i + "_c" + j + ".jpg"));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//   
//    public final static void convert(String srcImageFile, String formatName, String destImageFile) {
//        try {
//            File f = new File(srcImageFile);
//            f.canRead();
//            f.canWrite();
//            BufferedImage src = ImageIO.read(f);
//            ImageIO.write(src, formatName, new File(destImageFile));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//   
//    public final static void gray(String srcImageFile, String destImageFile) {
//        try {
//            BufferedImage src = ImageIO.read(new File(srcImageFile));
//            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
//            ColorConvertOp op = new ColorConvertOp(cs, null);
//            src = op.filter(src, null);
//            ImageIO.write(src, "JPEG", new File(destImageFile));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//   
//    public final static void pressText(String pressText,
//            String srcImageFile, String destImageFile, String fontName,
//            int fontStyle, Color color, int fontSize,int x,
//            int y, float alpha) {
//        try {
//            File img = new File(srcImageFile);
//            Image src = ImageIO.read(img);
//            int width = src.getWidth(null);
//            int height = src.getHeight(null);
//            BufferedImage image = new BufferedImage(width, height,
//                    BufferedImage.TYPE_INT_RGB);
//            Graphics2D g = image.createGraphics();
//            g.drawImage(src, 0, 0, width, height, null);
//            g.setColor(color);
//            g.setFont(new Font(fontName, fontStyle, fontSize));
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
//                    alpha));
//            // 在指定坐标绘制水印文字
//            g.drawString(pressText, (width - (getLength(pressText) * fontSize))
//                    / 2 + x, (height - fontSize) / 2 + y);
//            g.dispose();
//            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//   
//    public final static void pressText2(String pressText, String srcImageFile,String destImageFile,
//            String fontName, int fontStyle, Color color, int fontSize, int x,
//            int y, float alpha) {
//        try {
//            File img = new File(srcImageFile);
//            Image src = ImageIO.read(img);
//            int width = src.getWidth(null);
//            int height = src.getHeight(null);
//            BufferedImage image = new BufferedImage(width, height,
//                    BufferedImage.TYPE_INT_RGB);
//            Graphics2D g = image.createGraphics();
//            g.drawImage(src, 0, 0, width, height, null);
//            g.setColor(color);
//            g.setFont(new Font(fontName, fontStyle, fontSize));
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
//                    alpha));
//            // 在指定坐标绘制水印文字
//            g.drawString(pressText, (width - (getLength(pressText) * fontSize))
//                    / 2 + x, (height - fontSize) / 2 + y);
//            g.dispose();
//            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//   
//    public final static void pressImage(String pressImg, String srcImageFile,String destImageFile,
//            int x, int y, float alpha) {
//        try {
//            File img = new File(srcImageFile);
//            Image src = ImageIO.read(img);
//            int wideth = src.getWidth(null);
//            int height = src.getHeight(null);
//            BufferedImage image = new BufferedImage(wideth, height,
//                    BufferedImage.TYPE_INT_RGB);
//            Graphics2D g = image.createGraphics();
//            g.drawImage(src, 0, 0, wideth, height, null);
//            // 水印文件
//            Image src_biao = ImageIO.read(new File(pressImg));
//            int wideth_biao = src_biao.getWidth(null);
//            int height_biao = src_biao.getHeight(null);
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
//                    alpha));
//            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
//                    (height - height_biao) / 2, wideth_biao, height_biao, null);
//            // 水印文件结束
//            g.dispose();
//            ImageIO.write((BufferedImage) image,  "JPEG", new File(destImageFile));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public final static int getLength(String text) {
//        int length = 0;
//        for (int i = 0; i < text.length(); i++) {
//            if (new String(text.charAt(i) + "").getBytes().length > 1) {
//                length += 2;
//            } else {
//                length += 1;
//            }
//        }
//        return length / 2;
//    }
//    
////    public static void main(String[] args) {
//    /*
//		由于项目中出现了上传图片变色问题，项目采用的是Thumbnails，
//		在网上查资料才知道，使用java自带的图片处理方法就会出现这些问题（失真，变色，支持格式少）
//     */
////    	//1-缩放图像：
////    	//方法一：按比例缩放
////    	ImageUtil.scale("d:/abc.jpg", "d:/abc.png", 5, false);//  放大true、缩小false
////    	//方法二：按高度和宽度缩放
////    	ImageUtil.scale2("d:/abc.jpg", "d:/abcs.jpg", 768, 1024, false);	//缩放后，图像变形，应先剪裁，再缩放
////
////
////        // 2-切割图像：
////        // 方法一：按指定起点坐标和宽高切割
////        ImageUtil.cut("d:/abc.jpg", "d:/abc_cut.jpg", 0, 0, 400, 400 );//测试OK
////        // 方法二：指定切片的行数和列数
////        ImageUtil.cut2("d:/abc.jpg", "d:/", 2, 2 );//测试OK
////        // 方法三：指定切片的宽度和高度
////        ImageUtil.cut3("d:/abc.jpg", "d:/", 300, 300 );//测试OK
//
////    	ImageUtil.convert("d:/abc.jpg", "GIF", "d:/abc.gif");//图像类型转换  效果一般
////
////
////        // 4-彩色转黑白：
////        ImageUtil.gray("d:/abc.jpg", "d:/abc_gray.jpg");//测试OK
////
////
////        // 5-给图片添加文字水印：
////        // 方法一：
////        ImageUtil.pressText("我是水印文字","d:/abc.jpg","d:/abc_pressText.jpg","宋体",Font.BOLD,Color.white,80, 0, 0, 0.5f);//测试OK
////        // 方法二：
////        ImageUtil.pressText2("我也是水印文字", "d:/abc.jpg","d:/abc_pressText2.jpg", "黑体", 36, Color.white, 80, 0, 0, 0.5f);//测试OK
////        
////        // 6-给图片添加图片水印：
////        ImageUtil.pressImage("d:/abc2.jpg", "d:/abc.jpg","d:/abc_pressImage.jpg", 0, 0, 0.5f);//测试OK
////    }
}