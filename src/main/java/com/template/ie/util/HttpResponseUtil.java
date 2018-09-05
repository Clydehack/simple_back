package com.template.ie.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 功能：应答消息工具类
 */
public class HttpResponseUtil{

	private static Logger logger = LoggerFactory.getLogger(HttpResponseUtil.class);
	
	/**
	 * 错误应答信息：包含错误代码 responseCode
	 */
	public static String convertCommonJsonString(String state, String stateInfo, String responseCode, String dataKey, Object data){
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> temp = new HashMap<String,Object>();
			temp.put("state", state);
			temp.put("desc", stateInfo);
			temp.put("responseCode", responseCode);
			if(data!=null) temp.put(dataKey, data);
			return mapper.writeValueAsString(temp);
		} catch (Exception e) {
			logger.error("convertCommonJsonString", e);
		}
		return null;
	}
	
	/**
	 * 正确应答信息
	 */
	public static String convertCommonJsonString(int state, String stateInfo, String dataKey, Object data){
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> temp = new HashMap<String,Object>();
			temp.put("state", state);
			temp.put("desc", stateInfo);
			if(data!=null) temp.put(dataKey, data);
			return mapper.writeValueAsString(temp);
		} catch (Exception e) {
			logger.error("convertCommonJsonString", e);
		}
		return null;
	}
	/**
	 * 正确应答信息
	 */
	public static String convertCommonJsonString(int state, String stateInfo, String dataKey, Object data,String count, int countNum){
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> temp = new HashMap<String,Object>();
			temp.put("state", state);
			temp.put("desc", stateInfo);
			if(data!=null) temp.put(dataKey, data);
			if(count!=null) temp.put(count, countNum);
			return mapper.writeValueAsString(temp);
		} catch (Exception e) {
			logger.error("convertCommonJsonString", e);
		}
		return null;
	}
}