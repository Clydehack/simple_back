package com.template.ie.image;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.template.ie.model.Image;
import com.template.ie.util.FileUtil;
import com.template.ie.util.HttpResponseUtil;

@Service("imageService")
public class ImageService {

	@Resource
	ImageMapper imageMapper;
	
	/**
	 * 新增
	 */
	public String addImage(Map<String, Object> para) {
		String msg = "";
		try {
			imageMapper.addImage(para);
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
	public String deleteImage(Map<String, String> para) {
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
			imageMapper.deleteImage(id);
			
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	/**
	 * 删除
	 
	public String deleteImage(Map<String, String> para) {
		String msg = "";
		try {
			String image = para.get("image");
			String name = para.get("name");
			String[] testImage = image.split("'");			//删除单引号	如果是单个文件路径，没有引号呢？
			String[] testName = name.split("'");			//删除单引号
			if(testImage.length == testName.length) {
				for (int i = 0; i < testImage.length; i++) {
					if(!testImage[i].equals(",") && !testName[i].equals(",")) {	//如果不是逗号
						File delFolder = new File(testImage[i]+testName[i]);	//删除取出的文件
						delFolder.delete();
					}
		        }
				String id = para.get("id");
				String newId = "("+id+")"; 
				imageMapper.deleteImage(newId);
				msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", null);
			} else {
				msg = HttpResponseUtil.convertCommonJsonString(1, "失败", null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	*/
	/**
	 * 修改
	 */
	public String updateImage(Map<String, Object> para) {
		String msg = "";
		try {
			imageMapper.updateImage(para);
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
	public String selectImage(Map<String, Object> para) {
		String msg = "";
		try {
			String title = (String) para.get("title");					//名称
			String code = (String) para.get("code");					//类型
			int page_number = (int) para.get("page_number");			//当前页数
			int page_size = (int) para.get("page_size");				//每页显示数量
			
			int pageStart = page_size*(page_number-1); 	//大于该条记录 不包含条该记录 
	    	int pageEnd = page_size*page_number; 		//不大于该记录 包含该条记录
			
			if(title.equals("") && code.equals("")) {				//如果都是空，就查全部
				List<Image> list = imageMapper.selectAllImage(pageEnd, pageStart);
				int count = imageMapper.selectAllCount();
				msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list, "count", count);
			}
			else if(title.equals("") && !code.equals("")) {			//如果名称为空，查询此类型所有数据
				List<Image> list = imageMapper.selectGroupNameImage(code, pageEnd, pageStart);
				int count = imageMapper.selectGroupNameCount(code);
				msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list, "count", count);
			}
			else if(!title.equals("") && code.equals("")) {			//如果名称不为空，查询此名称数据
				List<Image> list = imageMapper.selectImage(title, pageEnd, pageStart);
				int count = imageMapper.selectCount(title);
				msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list, "count", count);
			}
			else if(!title.equals("") && !code.equals("")) {			//如果都不为空
				List<Image> list = imageMapper.selectTwoImage(title, code, pageEnd, pageStart);
				int count = imageMapper.selectTwoCount(title,code);
				msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", list, "count", count);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 按id查询
	 */
	public String selectImageById(Map<String, String> para) {
		String msg = "";
		try {
			String id = para.get("id");
			List<Image> list = imageMapper.selectImageById(id);
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
	public String stateImage(Map<String, String> para) {
		String msg = "";
		try {
			String id = para.get("id");
			String state = para.get("state");
			imageMapper.stateImage(id, state);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
}