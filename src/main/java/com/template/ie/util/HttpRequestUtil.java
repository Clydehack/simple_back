package com.template.ie.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：入参转换工具类
 */
public class HttpRequestUtil {
	
	private static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class); 
	
	/**
	 * 入参转Map
	 */
	public static Map<String, String> getParameter(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
//	    Enumeration e = request.getParameterNames();
	    Enumeration<String> e = request.getParameterNames();
	    while (e.hasMoreElements()) {
//	    	String key = (String)e.nextElement();
	    	String key = e.nextElement();
	    	String[] list = request.getParameterValues(key);
	    	if (list.length == 1) { 
	    		map.put(key, list[0]);
	    	} else if (list.length > 1) {
	    		String value = null;
	    		for (int i = 0; i < list.length; i++) {
	    			if (i == 0) {
	    				value = list[i];
	    			} else
	    				value = value + ',' + list[i];
	    		}
	    		map.put(key, value);
	    	}
	    }
	    logger.info("接收前端传入数据：map==>{}", map);
	    return map;
	}
}