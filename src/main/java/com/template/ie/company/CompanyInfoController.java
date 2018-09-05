package com.template.ie.company;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.template.ie.util.HttpResponseUtil;

@RestController
@RequestMapping("/company")
public class CompanyInfoController {
	
	@Resource(name="companyService")
	private CompanyService companyService;

	private void setHeader(HttpServletResponse response) {	//设置响应
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setDateHeader("Expires", 0);
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
	@RequestMapping(value = "updateCompany", method = RequestMethod.POST)
    @ResponseBody
    public String updateCompany(HttpServletRequest request, HttpServletResponse response) {
		String msg = "";
		setHeader(response);
		try {
	    	MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
	        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
	        
	        Map<String,String> para = new HashMap<String,String>();			//装载参数
	        para.put("id", UUID.randomUUID().toString().replace("-", ""));	//公司id
	        para.put("name", params.getParameter("name"));					//公司名称
	        para.put("address", params.getParameter("address"));			//公司地址
	        para.put("legal_person", "");
	        para.put("title", "");
	        para.put("phone", params.getParameter("phone"));				//公司电话
	        para.put("email", params.getParameter("email"));				//公司邮箱
	        para.put("weibo", params.getParameter("weibo"));				//公司微博
	        para.put("sort", "1");
	        String wechat_qrcode = params.getParameter("wechat_qrcode");	//微信二维码完整路径
        	
	        String filePath = "";
	        String fileName = "";
	        
	        MultipartFile file = null;
	        if (!files.isEmpty()) {
	        	for (int i = 0; i < files.size(); ++i) {
	        		file = files.get(i);
	        		if (!file.isEmpty()) {							//如果上传数据时也上传了图片，则图片路径变更
	        			File delFolder = new File(wechat_qrcode);	//删除原来的图片
	        			delFolder.delete();
	        			filePath = Configation.path + Configation.company;
	        			fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('\\')+1);
	        			String file_name = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
	        			FileUtil.uploadFile(file.getBytes(), filePath, file_name,false,0,0);
	        			para.put("wechat_qrcode", filePath+file_name);
	        		} else {										//如果只上传了数据，则用原来的图片地址
	        			para.put("wechat_qrcode", wechat_qrcode);
	        		}
	        	}
	        } else {
	        	para.put("wechat_qrcode", wechat_qrcode);
	        }
	        msg = companyService.updateCompany(para);
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
	 * 查询
	 */
	@RequestMapping(value = "selectCompany", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectCompany(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			msg = companyService.selectCompany();
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());		
		}
		return msg;
    }
}