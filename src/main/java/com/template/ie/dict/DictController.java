package com.template.ie.dict;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.template.ie.util.HttpResponseUtil;

@RestController
@RequestMapping("/dict")
public class DictController {
	
	@Resource(name="dictService")
	private DictService dictService;
	
	private void setHeader(HttpServletResponse response) {	//设置响应
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setDateHeader("Expires", 0);
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "addDict", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String addDict(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			
			Map<String, String> para = new HashMap<String, String>();
			para.put("id", UUID.randomUUID().toString().replace("-", ""));
			para.put("group_code", request.getParameter("group_code"));
			para.put("group_name", request.getParameter("group_name"));
			para.put("name", request.getParameter("name"));
			para.put("code", request.getParameter("code"));
			para.put("key", request.getParameter("key"));
			para.put("state", request.getParameter("state"));
			para.put("sort", request.getParameter("sort"));
			
			msg = dictService.addDict(para);
			para = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }

	/**
	 * 删除
	 */
	@RequestMapping(value = "deleteDict", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String deleteDict(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = new HashMap<String, String>();
			para.put("id", request.getParameter("id"));
			msg = dictService.deleteDict(para);
			para = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "updateDict", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String updateDict(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			
			Map<String, String> para = new HashMap<String, String>();
			para.put("id", request.getParameter("id"));
			para.put("group_code", request.getParameter("group_code"));
			para.put("group_name", request.getParameter("group_name"));
			para.put("name", request.getParameter("name"));
			para.put("code", request.getParameter("code"));
			para.put("key", request.getParameter("key"));
			para.put("state", request.getParameter("state"));
			para.put("sort", request.getParameter("sort"));
			
			msg = dictService.updateDict(para);
			para = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 按条件查询+分页
	 */
	@RequestMapping(value = "selectDict", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectDict(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			String pageNumber = request.getParameter("page_number"); 	//当前页数
			String pageSize = request.getParameter("page_size"); 		//每页显示数量
//			Integer page_number = Integer.valueOf(pageNumber);
//			Integer page_size = Integer.valueOf(pageSize);
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("name", request.getParameter("name"));
			para.put("page_number", Integer.valueOf(pageNumber));
			para.put("page_size", Integer.valueOf(pageSize));
			msg = dictService.selectDict(para);
			para = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 查询分类（图片分类、角色分类）
	 */
	@RequestMapping(value = "selectClassifDict", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectClassifDict(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
//			String group_code = request.getParameter("group_code");
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("group_code", request.getParameter("group_code"));
			msg = dictService.selectClassifDict(para);
			para = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 分组查询+分页
	 */
	@RequestMapping(value = "selectGroupDict", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectGroupDict(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			String pageNumber = request.getParameter("page_number"); 	//当前页数
			String pageSize = request.getParameter("page_size"); 		//每页显示数量
//			Integer page_number = Integer.valueOf(pageNumber);
//			Integer page_size = Integer.valueOf(pageSize);
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("page_number", Integer.valueOf(pageNumber));
			para.put("page_size", Integer.valueOf(pageSize));
			msg = dictService.selectGroupDict(para);
			para = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 查询全部+分页
	 */
	@RequestMapping(value = "selectAllDict", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectAllDict(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			String pageNumber = request.getParameter("page_number"); 	//当前页数
			String pageSize = request.getParameter("page_size"); 		//每页显示数量
//			Integer page_number = Integer.valueOf(pageNumber);
//			Integer page_size = Integer.valueOf(pageSize);
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("page_number", Integer.valueOf(pageNumber));
			para.put("page_size", Integer.valueOf(pageSize));
			msg = dictService.selectAllDict(para);
			para = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 启用、停用
	 */
	@RequestMapping(value = "stateDict", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String stateDict(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
//			String id = request.getParameter("id");
//			String state = request.getParameter("state");
			Map<String, String> para = new HashMap<String, String>();
			para.put("id", request.getParameter("id"));
			para.put("state", request.getParameter("state"));
			msg = dictService.stateDict(para);
			para = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
}