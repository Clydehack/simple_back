package com.template.ie.dict;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.template.ie.model.Dict;
import com.template.ie.util.HttpResponseUtil;

@Service("dictService")
public class DictService {

	@Autowired
	DictMapper dictMapper;
	
	/**
	 * 新增
	 */
	public String addDict(Map<String, String> para) {
		String msg = "";
		try {
			dictMapper.addDict(para);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 删除
	 */
	public String deleteDict(Map<String, String> para) {
		String msg = "";
		try {
			String id = para.get("id");
			id = "("+id+")"; 
			dictMapper.deleteDict(id);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 修改
	 */
	public String updateDict(Map<String, String> para) {
		String msg = "";
		try {
			dictMapper.updateDict(para);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 按条件查询+分页
	 */
	public String selectDict(Map<String, Object> para) {
		String msg = "";
		try {
			String name = (String) para.get("name");					//名称
			int page_number = (int) para.get("page_number");			//当前页数
			int page_size = (int) para.get("page_size");				//每页显示数量
			
			int pageStart = page_size*(page_number-1); 	//大于该条记录 不包含条该记录 
	    	int pageEnd = page_size*page_number; 		//不大于该记录 包含该条记录
	    	List<Dict> list = null;
			if(name.equals("")) {		//如果都是空，就查全部
				list = dictMapper.selectAllDict(pageEnd, pageStart);
				int count = dictMapper.selectAllCount();
				msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list, "count", count);
			} else {
				list = dictMapper.selectDict(name,pageEnd, pageStart);
				int count = dictMapper.selectCount(name);
				msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list, "count", count);
			}
//			else if(name.equals("") && !group_code.equals("")) {	//如果名称为空，查询此类型所有数据
//				List<Dict> list = dictMapper.selectGroupNameDict(group_code,pageEnd, pageStart);
//				int count = dictMapper.selectGroupNameCount();
//				msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list, "count", count);
//			}
//			else if(!name.equals("") && group_code.equals("")) {	//如果名称不为空，查询此名称数据
//				List<Dict> list = dictMapper.selectDict(name,pageEnd, pageStart);
//				int count = dictMapper.selectCount();
//				msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list, "count", count);
//			}
//			else if(!name.equals("") && !group_code.equals("")) {	//如果都不为空
//				List<Dict> list = dictMapper.selectTwoDict(name, group_code,pageEnd, pageStart);
//				int count = dictMapper.selectTwoCount();
//				msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list, "count", count);
//			}
			list = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 查询分类（图片分类、角色分类）+分页
	 */
	public String selectClassifDict(Map<String, Object> para) {
		String msg = "";
		try {
			List<Dict> list = dictMapper.selectClassifDict((String) para.get("group_code"));
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list);
			list = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 分组查询+分页
	 */
	public String selectGroupDict(Map<String, Object> para) {
		String msg = "";
		try {
			int page_number = (int) para.get("page_number");			//当前页数
			int page_size = (int) para.get("page_size");				//每页显示数量
			
			int pageStart = page_size*(page_number-1); 	//大于该条记录 不包含条该记录 
	    	int pageEnd = page_size*page_number; 		//不大于该记录 包含该条记录
			
			List<Dict> list = dictMapper.selectGroupDict(pageEnd, pageStart);
			int count = dictMapper.selectGroupCount();
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list, "count", count);
			list = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 查询全部+分页
	 */
	public String selectAllDict(Map<String, Object> para) {
		String msg = "";
		try {
			int page_number = (int) para.get("page_number");			//当前页数
			int page_size = (int) para.get("page_size");				//每页显示数量
			
			int pageStart = page_size*(page_number-1); 	//大于该条记录 不包含条该记录 
	    	int pageEnd = page_size*page_number; 		//不大于该记录 包含该条记录
			
			List<Dict> list = dictMapper.selectAllDict(pageEnd, pageStart);
			int count = dictMapper.selectAllCount();
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list, "count", count);
			list = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 启用、停用
	 */
	public String stateDict(Map<String, String> para) {
		String msg = "";
		try {
//			String id = para.get("id");
//			String state = para.get("state");
			dictMapper.stateDict(para.get("id"), para.get("state"));
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
}