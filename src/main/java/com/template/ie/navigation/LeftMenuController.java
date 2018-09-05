package com.template.ie.navigation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.template.ie.util.HttpRequestUtil;
import com.template.ie.util.HttpResponseUtil;

@RestController
@RequestMapping("/leftMenu")
public class LeftMenuController {

	@Resource(name="leftMenuService")
	private LeftMenuService leftMenuService;
	
	private void setHeader(HttpServletResponse response) {	//设置响应
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setDateHeader("Expires", 0);
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "addLeftMenu", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String addLeftMenu(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = HttpRequestUtil.getParameter(request);
			msg = leftMenuService.addLeftMenu(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }

	/**
	 * 删除
	 */
	@RequestMapping(value = "deleteLeftMenu", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String deleteLeftMenu(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			String id = request.getParameter("id");
			Map<String, String> para = new HashMap<String, String>();
			para.put("id", id);
			msg = leftMenuService.deleteLeftMenu(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "updateLeftMenu", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String updateLeftMenu(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = HttpRequestUtil.getParameter(request);
			msg = leftMenuService.updateLeftMenu(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 按id查询
	 */
	@RequestMapping(value = "selectLeftMenu", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectLeftMenu(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = HttpRequestUtil.getParameter(request);
			msg = leftMenuService.selectLeftMenu(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 类型选择查询全部
	 */
	@RequestMapping(value = "selectAllLeftMenu", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectAllLeftMenu(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			msg = leftMenuService.selectAllLeftMenu();
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 分页显示查询全部
	 */
	@RequestMapping(value = "selectAllMenu", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectAllMenu(HttpServletRequest request,HttpServletResponse response) {
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
			msg = leftMenuService.selectAllMenu(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
	
	/**
	 * 启用、停用
	 */
	@RequestMapping(value = "stateLeftMenu", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String stateLeftMenu(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = HttpRequestUtil.getParameter(request);
			msg = leftMenuService.stateLeftMenu(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
    }
}