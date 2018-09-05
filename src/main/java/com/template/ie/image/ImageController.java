package com.template.ie.image;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.template.ie.util.Configation;
import com.template.ie.util.FileUtil;
import com.template.ie.util.HttpRequestUtil;
import com.template.ie.util.HttpResponseUtil;
import com.template.ie.util.ImageThread;
import com.template.ie.util.ImageUtil;

@RestController
@RequestMapping("/img")
public class ImageController {

	@Resource(name = "imageService")
	private ImageService imageService;						//service服务接口

	private void setHeader(HttpServletResponse response) {	//设置响应
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setDateHeader("Expires", 0);
	}
	
	/**
	 * 功能：新增图片
	 * 
	 * 逻辑：
	 * 		共有四种类别：	（已改，数据库变动）
	 * 		①360度旋转图		code=01
	 * 		②banner轮播图	code=02
	 * 		③产品图			code=03
	 * 		④合作公司logo	code=04
	 */
	@RequestMapping(value = "addImage", method = RequestMethod.POST)
    @ResponseBody
    public String addImage(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
//		setHeader(response);
		try {
			
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setDateHeader("Expires", 0);
			response.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS");
			
	    	MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
	        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
	        
	        String id = UUID.randomUUID().toString().replace("-", "");		//id
	        String name = params.getParameter("name");						//文件名 多个文件名时用,分隔
	        String code = params.getParameter("code");						//类别
	        SimpleDateFormat fo = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
	        String time = fo.format(new Date());
	        Date date = fo.parse(time);										//上传时间
	        
	        Map<String,Object> para = new HashMap<String,Object>();			//装载参数
	        para.put("id", id);												//id
	        para.put("title", params.getParameter("title"));				//标题
	        para.put("introduce", params.getParameter("introduce"));		//简介
	        para.put("code", code);
	        para.put("state", params.getParameter("state"));				//状态
	        para.put("time", date);
	        para.put("sort", params.getParameter("sort"));					//优先级
	        
	        String image = Configation.path + code + "/" + id + "/";		//图片路径
        	String fileName = "";
        	MultipartFile file = null;
	        if (!files.isEmpty()) {
	        	
	        	final int MAX_THREADS = 3; //定义线程数最大值
	        	ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
	        	
	        	for (int i = 0; i < files.size(); ++i) {
	        		file = files.get(i);
	        		if (!file.isEmpty()) {
	        			fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('\\')+1);
	        			String file_name = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
	        			if(code.equals(Configation.banner_code)) {		//如果是banner轮播大图，则不进行压缩处理
	        				FileUtil.uploadFile(file.getBytes(), image, file_name,false,0,0);
	        			} else {
	        				FileUtil.uploadFile(file.getBytes(), image, file_name,true,1024,768);
	        				executorService.submit(new ImageThread(image, file_name, 1024, 768));
	        			}
	        			name = name + "," + file_name;
	        		}
	        	}
	        	
	        	System.out.println("executor.shutdown();");
	            executorService.shutdown();
	        	
        		String newName = name.substring(5, name.length());	//删除前五个,
    			para.put("name", newName);
    			para.put("image", image);
    			msg = imageService.addImage(para);
	        } else {
 	        	msg = HttpResponseUtil.convertCommonJsonString(1, "失败", null, null);
	        }
	        params = null;
	        files = null;
	        file = null;
	        para = null;
		} catch (Exception e) {
	        msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());		
		}
		return msg;
    }

	/**
	 * 删除
	 */
	@RequestMapping(value = "deleteImage", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String deleteImage(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = new HashMap<String, String>();
			para.put("id", request.getParameter("id"));
			para.put("image", request.getParameter("image"));
			msg = imageService.deleteImage(para);
			para = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());		
		}
		return msg;
    }
	
	/**
	 * 功能：修改
	 * 
	 * 逻辑：
	 * 		共有四种类别：	（已改）
	 * 		①360度旋转图		code=01
	 * 		②banner轮播图	code=02
	 * 		③产品图			code=03
	 * 		④合作公司logo	code=04
	 */
	@RequestMapping(value = "updateImage", method = RequestMethod.POST)
    @ResponseBody
    public String updateImage(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
//		setHeader(response);
		try {
			
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setDateHeader("Expires", 0);
			response.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS");
			
	    	MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
	        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
	        
	        String id = params.getParameter("id");							//id 传进来的
//	        String id = UUID.randomUUID().toString().replace("-", "");		//id 
	        String name = params.getParameter("name");						//名称
	        String haldname = name;											//用于判断
	        String title = params.getParameter("title");					//标题
	        String image = params.getParameter("image");					//图片路径
	        String introduce = params.getParameter("introduce");			//简介
	        String code = params.getParameter("code");						//类别
	        String state = params.getParameter("state");					//状态
	        SimpleDateFormat fo = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
	        String time = fo.format(new Date());
	        Date date = fo.parse(time);										//上传时间
	        String sort = params.getParameter("sort");						//优先级
	        
	        String delete_name = params.getParameter("delete_name");		//需要删除的图片
	        if(!delete_name.equals("")) {									//!=""
	        	if(name.equals("")) {											//如果是编辑单个图片，其他图片
	        		//根据发来的删除名称 删除图片
//	        		String[] test = delete_name.split("'");						//删除单引号
	        		String[] test = delete_name.split(",");						
	        		for (int i = 0; i < test.length; i++) {
//	        			if(!test[i].equals(",") && !test[i].equals("")) {		
        				if(!test[i].equals(",")) {		
		        			//File delFolder = new File(image + test[i]);		//取出要删除的文件
		        			//delFolder.delete();
	        				FileUtil.delFolder(image + test[i]);
	        			}
	        		}
	        	} else {														//如果编辑多张图片，360旋转图片
	        		String[] test = delete_name.split(",");
	        		for (int i = 0; i < test.length; i++) {
	        			if(!test[i].equals(",")) {								//如果不是逗号且不为空
							//String oldname = test[i];							//取出的文件名
	        				File delFolder = new File(image + test[i]);			//取出要删除的文件
	        				delFolder.delete();
	        			}
	        		}
	        	}
	        }
	        
	        Map<String,Object> para = new HashMap<String,Object>();			//装载参数
	        para.put("id", id);
	        para.put("title", title);
	        para.put("image", image);
	        para.put("introduce", introduce);
	        para.put("code", code);
	        para.put("state", state);
	        para.put("time", date);
	        para.put("sort", sort);
	        
        	String fileName = "";
        	String file_name = "";
	        MultipartFile file = null;
	        if (!files.isEmpty()) {
	        	
	        	final int MAX_THREADS = 3; //定义线程数最大值
	        	ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
	        	
	        	for (int i = 0; i < files.size(); ++i) {
	        		file = files.get(i);
	        		if (!file.isEmpty()) {
	        			fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('\\')+1);
	        			file_name = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
	        			if(code.equals(Configation.banner_code)) {		//如果是banner轮播大图，则不进行压缩处理
	        				FileUtil.uploadFile(file.getBytes(), image, file_name,false,0,0);
	        			} else {
	        				FileUtil.uploadFile(file.getBytes(), image, file_name,true,1024,768);
//	        				FileUtil.uploadFile(file.getBytes(), image, file_name,true,1280,720);
	        				executorService.submit(new ImageThread(image, file_name, 1024, 768));

	        			}
//	        			fileName = file_name + "," + name;
	        			//为何不用数组呢？ 优化时再说吧，忙死了
	        			name = name + "," + file_name;
	        		}
	        	}
	        	
	        	System.out.println("executor.shutdown();");
	            executorService.shutdown();
	        	
	        	if(haldname.equals("")) {										//如果是编辑单个图片
//	        		fileName = fileName.substring(0, fileName.length() - 1);	//删除最后的,
	        		name = name.substring(1, name.length());					//删除第一个,
	        		para.put("name", name);
    			} else {
    				para.put("name", name);
    			}
	        	msg = imageService.updateImage(para);
	        } else {
	        	para.put("name", name);
	        	msg = imageService.updateImage(para);
	        }
		} catch (Exception e) {
	        msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 按条件查询+分页
	 */
	@RequestMapping(value = "selectImage", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectImage(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			String code = request.getParameter("code");
			String title = request.getParameter("title");
			String pageNumber = request.getParameter("page_number"); 	//当前页数
			String pageSize = request.getParameter("page_size"); 		//每页显示数量
			Integer page_number = Integer.valueOf(pageNumber);
			Integer page_size = Integer.valueOf(pageSize);
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("code", code);
			para.put("title", title);
			para.put("page_number", page_number);
			para.put("page_size", page_size);
			msg = imageService.selectImage(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());	
		}
		return msg;
    }
	
	/**
	 * 按id查询（点击编辑按钮时）
	 */
	@RequestMapping(value = "selectImageById", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectImageById(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = HttpRequestUtil.getParameter(request);
			msg = imageService.selectImageById(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());	
		}
		return msg;
    }
	
	/**
	 * 启用、停用
	 */
	@RequestMapping(value = "stateImage", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String stateImage(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = HttpRequestUtil.getParameter(request);
			msg = imageService.stateImage(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());	
		}
		return msg;
    }
	
	/**
	 * 高清大图处理
	 */
	
}