package com.template.ie.person;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

@RestController
@RequestMapping("/person")

public class PersonInfoController {
	
	@Resource(name="personInfoService")
	private PersonInfoService personInfoService;
	
	private void setHeader(HttpServletResponse response) {	//设置响应
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setDateHeader("Expires", 0);
	}
	
	/**
	 * 功能：新增
	 * 
	 * 逻辑：	
	 * 		①获取文本数据
	 * 		②获取图片文件
	 * 		③存储图片到文件夹
	 * 		④存储数据到数据库
	 */
	@RequestMapping(value = "addPersonInfo", method = RequestMethod.POST)
    @ResponseBody
    public String addPersonInfo(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		setHeader(response);
		try {
	    	MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
	        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
	        
	        String id = UUID.randomUUID().toString().replace("-", "");		//id
	        String name = params.getParameter("name");						//名称
	        String role_code = params.getParameter("role_code");			//角色编码
	        String image = params.getParameter("image");					//头像图片完整路径
	        String produce = params.getParameter("produce");				//代表作
	        String state = params.getParameter("state");					//状态
	        String sort = params.getParameter("sort");						//优先级
	        
	        Map<String,String> para = new HashMap<String,String>();			//装载参数
	        para.put("id", id);
	        para.put("name", name);
	        para.put("role_code", role_code);
	        para.put("title", "-");
	        para.put("produce", produce);
	        para.put("state", state);
	        para.put("sort", sort);
	        
	        image = Configation.path + Configation.person;
	        String fileName = "";
	        String file_name = "";
	        
	        MultipartFile file = null;
			if (!files.isEmpty()) {
	        	
	        	final int MAX_THREADS = 3; //定义线程数最大值
	        	ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
	        	
	        	for (int i = 0; i < files.size(); ++i) {
	        		file = files.get(i);
	        		if (!file.isEmpty()) {							//如果上传数据时也上传了图片，则图片路径变更
	        			fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('\\')+1);
	        			file_name = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
	        			FileUtil.uploadFile(file.getBytes(), image, file_name,true,1024,768);
						executorService.submit(new ImageThread(image, file_name, 1024, 768));
	        		}
	        	}
	        	System.out.println("executor.shutdown();");
	            executorService.shutdown();
	        	para.put("image", image+file_name);
	        	msg = personInfoService.addPersonInfo(para);
	        } else {
	        	msg = HttpResponseUtil.convertCommonJsonString(1, "失败", null, null);
	        }
		} catch (Exception e) {
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "deletePersonInfo", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String deletePersonInfo(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			String id = request.getParameter("id");
			String image = request.getParameter("image");
			Map<String, String> para = new HashMap<String, String>();
			para.put("id", id);
			para.put("image", image);
			msg = personInfoService.deletePersonInfo(para);
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
	 * 		①获取文本数据
	 * 		②获取图片文件
	 * 		③存储图片到文件夹
	 * 		④存储数据到数据库
	 */
	@RequestMapping(value = "updatePersonInfo", method = RequestMethod.POST)
    @ResponseBody
    public String updatePersonInfo(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		setHeader(response);
		try {
	    	MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
	        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
	        
	        String id = params.getParameter("id");							//id
	        String name = params.getParameter("name");						//名称
	        String role_code = params.getParameter("role_code");			//角色编码
	        String image = params.getParameter("image");					//头像图片完整路径
	        String produce = params.getParameter("produce");				//代表作
	        String state = params.getParameter("state");					//状态
	        String sort = params.getParameter("sort");						//优先级
	        
	        //根据发来的删除名称 删除图片
	        if(!files.isEmpty()) {
    			File delFolder = new File(image);							//删除原文件
    			delFolder.delete();
	        }
	        
	        Map<String,String> para = new HashMap<String,String>();			//装载参数
	        para.put("id", id);
	        para.put("name", name);
	        para.put("role_code", role_code);
	        para.put("title", "-");
	        para.put("produce", produce);
	        para.put("state", state);
	        para.put("sort", sort);
	        
	        String fileName = "";
	        String file_name = "";
	        String file_path = Configation.path + Configation.person;
	        
	        MultipartFile file = null;
	        if (!files.isEmpty()) {
	        	
	        	final int MAX_THREADS = 3; //定义线程数最大值
	        	ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
	        	
	        	for (int i = 0; i < files.size(); ++i) {
	        		file = files.get(i);
	        		if (!file.isEmpty()) {							//如果上传数据时也上传了图片，则图片路径变更
	        			fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('\\')+1);
//	        			String test = fileName.substring(0, fileName.length()-4);
//	        			test = test + ".png";
	        			file_name = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
	        			FileUtil.uploadFile(file.getBytes(), file_path, file_name,true,1024,768);
						executorService.submit(new ImageThread(file_path, file_name, 1024, 768));
	        		}
	        	}
	        	System.out.println("executor.shutdown();");
	            executorService.shutdown();
	        	para.put("image", file_path+file_name);
	        	msg = personInfoService.updatePersonInfo(para);
	        } else {
	        	para.put("image", image);
	        	msg = personInfoService.updatePersonInfo(para);
	        }
		} catch (Exception e) {
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 按id查询
	 */
	@RequestMapping(value = "selectPersonInfo", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectPersonInfo(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = HttpRequestUtil.getParameter(request);
			msg = personInfoService.selectPersonInfo(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 查询全部+分页
	 */
	@RequestMapping(value = "selectAllPersonInfo", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectAllPersonInfo(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			String pageNumber = request.getParameter("page_number"); 	//当前页数
			String pageSize = request.getParameter("page_size"); 		//每页显示数量
			Integer page_number = Integer.valueOf(pageNumber);
			Integer page_size = Integer.valueOf(pageSize);
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("page_number", page_number);
			para.put("page_size", page_size);
			msg = personInfoService.selectAllPersonInfo(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 启用、停用
	 */
	@RequestMapping(value = "statePersonInfo", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String statePersonInfo(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = HttpRequestUtil.getParameter(request);
			msg = personInfoService.statePersonInfo(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
}