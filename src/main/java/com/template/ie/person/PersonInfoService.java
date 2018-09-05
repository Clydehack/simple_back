package com.template.ie.person;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.template.ie.model.PersonInfo;
import com.template.ie.util.FileUtil;
import com.template.ie.util.HttpResponseUtil;

@Service("personInfoService")
public class PersonInfoService {

	private static Logger logger = LoggerFactory.getLogger(PersonInfoService.class);
	
	@Autowired
	PersonInfoMapper personInfoMapper;
	
	/**
	 * 新增
	 */
	public String addPersonInfo(Map<String, String> para) {
		String msg = "";
		try {
			personInfoMapper.addPersonInfo(para);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		logger.info("完成addPersonInfo-service");
		return msg;
	}
	
	/**
	 * 删除
	 */
	public String deletePersonInfo(Map<String, String> para) {
		String msg = "";
		try {
			String image = para.get("image");
			String[] testImage = image.split("'");			//删除单引号	如果是单个文件路径，没有引号呢？
			for (int i = 0; i < testImage.length; i++) {
				System.out.println(testImage[i]);
				if(!testImage[i].equals(",") && !testImage[i].equals("")) {		//如果不是逗号
//					File delFolder = new File(testImage[i]);	//删除取出的文件
//					delFolder.delete();
					FileUtil.delFolder(testImage[i]);
				}
	        }
			
//			String id = para.get("id");
//			String make = "";
//			String[] testId = id.split(",");
//			
//			System.out.println(testId.length);
//			for (int i = 0; i < testId.length; i++) {
//				System.out.println(testId[i]);
//				make = make + "," + "'" + testId[i] + "'";
//			}
//			String oldId = make.substring(1, make.length());	//删除第一个,
//			id = "(" + oldId + ")"; 
			String id = para.get("id");
			String newId = "("+id+")"; 
			personInfoMapper.deletePersonInfo(newId);
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
	public String updatePersonInfo(Map<String, String> para) {
		String msg = "";
		try {
			personInfoMapper.updatePersonInfo(para);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 按id查询
	 */
	public String selectPersonInfo(Map<String, String> para) {
		String msg = "";
		try {
			String id = para.get("id");
			List<PersonInfo> list = personInfoMapper.selectPersonInfo(id);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 查询全部+分页
	 */
	public String selectAllPersonInfo(Map<String, Object> para) {
		String msg = "";
		try {
			int page_number = (int) para.get("page_number");			//当前页数
			int page_size = (int) para.get("page_size");				//每页显示数量
			
			int pageStart = page_size*(page_number-1); 	//大于该条记录 不包含条该记录 
	    	int pageEnd = page_size*page_number; 		//不大于该记录 包含该条记录
	    	
			List<PersonInfo> list = personInfoMapper.selectAllPersonInfo(pageEnd, pageStart);
			int count = personInfoMapper.selectAllCount();
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
	public String statePersonInfo(Map<String, String> para) {
		String msg = "";
		try {
			String id = (String) para.get("id");
			String state = (String) para.get("state");
			personInfoMapper.statePersonInfo(id, state);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
}