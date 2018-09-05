package com.template.ie.user;

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
@RequestMapping("/user")
public class UserController {

	@Resource(name="userService")
	private UserService userService;
	
	private void setHeader(HttpServletResponse response) {	//设置响应
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setDateHeader("Expires", 0);
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "addUser", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String addUser(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = HttpRequestUtil.getParameter(request);
			msg = userService.addUser(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString("1", "失败", "0001", null, null);		
		}
		return msg;
    }

	/**
	 * 删除
	 */
	@RequestMapping(value = "deleteUser", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String deleteUser(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			String id = request.getParameter("id");
			Map<String, String> para = new HashMap<String, String>();
			para.put("id", id);
			msg = userService.deleteUser(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString("1", "失败", "0001", null, null);		
		}
		return msg;
    }
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "updateUser", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String updateUser(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = HttpRequestUtil.getParameter(request);
			msg = userService.updateUser(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString("1", "失败", "0001", null, null);		
		}
		return msg;
    }
	
	/**
	 * 查询用户是否存在
	 */
	@RequestMapping(value = "selectUserExist", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectUserExist(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			Map<String, String> para = new HashMap<String, String>();
			para.put("name", name);
			para.put("password", password);
			msg = userService.selectUserExist(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());		
		}
		return msg;
    }
	
	/**
	 * 查询所有用户
	 */
	@RequestMapping(value = "selectAllUser", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String selectAllUser(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			msg = userService.selectAllUser();
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());		
		}
		return msg;
    }
	
	/**
	 * 启用、停用
	 */
	@RequestMapping(value = "stateUser", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String stateUser(HttpServletRequest request,HttpServletResponse response) {
		String msg = "";
		try {
			setHeader(response);
			Map<String, String> para = HttpRequestUtil.getParameter(request);
			msg = userService.stateUser(para);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString("1", "失败", "0001", null, null);		
		}
		return msg;
    }
}