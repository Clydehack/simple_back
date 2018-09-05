package com.template.ie.user;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.template.ie.model.User;
import com.template.ie.util.HttpResponseUtil;

@Service("userService")
public class UserService {

	@Autowired
	UserMapper userMapper;
	
	/**
	 * 新增
	 */
	public String addUser(Map<String, String> para) {
		String msg = "";
		try {
			String uuid = UUID.randomUUID().toString().replace("-", "");
			para.put("id", uuid);
			userMapper.addUser(para);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 删除
	 */
	public String deleteUser(Map<String, String> para) {
		String msg = "";
		try {
			String id = para.get("id");
			String newId = "("+id+")"; 
			userMapper.deleteUser(newId);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 修改
	 */
	public String updateUser(Map<String, String> para) {
		String msg = "";
		try {
			userMapper.updateUser(para);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 查询用户是否存在
	 */
	public String selectUserExist(Map<String, String> para) {
		String msg = "";
		try {
			String name = para.get("name");
			String password = para.get("password");
			int exist = userMapper.selectUserExist(name, password);
			if(exist == 1) {
				msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", 0);
			} else {
				msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 查询所有用户
	 */
	public String selectAllUser() {
		String msg = "";
		try {
			List<User> list = userMapper.selectAllUser();
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 启用、停用
	 */
	public String stateUser(Map<String, String> para) {
		String msg = "";
		try {
			String id = para.get("id");
			String state = para.get("state");
			userMapper.stateUser(id, state);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
}