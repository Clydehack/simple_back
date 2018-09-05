package com.template.ie.navigation;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.template.ie.model.Dict;
import com.template.ie.model.LeftMenu;
import com.template.ie.util.HttpResponseUtil;

@Service("leftMenuService")
public class LeftMenuService {

	private static Logger logger = LoggerFactory.getLogger(LeftMenuService.class);
	
	@Autowired
	LeftMenuMapper leftMenuMapper;
	
	/**
	 * 新增
	 */
	public String addLeftMenu(Map<String, String> para) {
		String msg = "";
		logger.info("开始addLeftMenu-service");
		try {
			String uuid = UUID.randomUUID().toString().replace("-", "");
			para.put("id", uuid);
			leftMenuMapper.addLeftMenu(para);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		logger.info("完成addLeftMenu-service");
		return msg;
	}
	
	/**
	 * 删除
	 */
	public String deleteLeftMenu(Map<String, String> para) {
		String msg = "";
		logger.info("开始deleteLeftMenu-service");
		try {
			String id = para.get("id");
			String make = "";
			String[] testId = id.split(",");
			
			System.out.println(testId.length);
			for (int i = 0; i < testId.length; i++) {
				System.out.println(testId[i]);
				make = make + "," + "'" + testId[i] + "'";
			}
			String oldId = make.substring(1, make.length());	//删除第一个,
			id = "(" + oldId + ")"; 
			leftMenuMapper.deleteLeftMenu(id);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		logger.info("完成deleteLeftMenu-service");
		return msg;
	}
	
	/**
	 * 修改
	 */
	public String updateLeftMenu(Map<String, String> para) {
		String msg = "";
		try {
			leftMenuMapper.updateLeftMenu(para);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 按id查询
	 */
	public String selectLeftMenu(Map<String, String> para) {
		String msg = "";
		try {
			String id = para.get("id");
			List<LeftMenu> list = leftMenuMapper.selectLeftMenu(id);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 查询全部
	 */
	public String selectAllLeftMenu() {
		String msg = "";
		try {
			List<LeftMenu> list = leftMenuMapper.selectAllLeftMenu();
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 分页显示查询全部
	 */
	public String selectAllMenu(Map<String, Object> para) {
		String msg = "";
		try {
			String id = "acf05bb4402f4f139f8aaeada1a203ed";				//不显示合作品牌
			String id0 = "f9076684f86141348964703ca7b5e454";			//不显示广告轮播
			int page_number = (int) para.get("page_number");			//当前页数
			int page_size = (int) para.get("page_size");				//每页显示数量
			
			int pageStart = page_size*(page_number-1); 	//大于该条记录 不包含条该记录 
	    	int pageEnd = page_size*page_number; 		//不大于该记录 包含该条记录
			
			List<Dict> list = leftMenuMapper.selectAllDict(id,id0,pageEnd, pageStart);
			int count = leftMenuMapper.selectAllCount(id,id0);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list, "count", count);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 启用、停用
	 */
	public String stateLeftMenu(Map<String, String> para) {
		String msg = "";
		try {
			String id = para.get("id");
			String state = para.get("state");
			leftMenuMapper.stateLeftMenu(id, state);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
}