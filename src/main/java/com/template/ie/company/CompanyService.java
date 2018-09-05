package com.template.ie.company;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.template.ie.model.Company;
import com.template.ie.util.HttpResponseUtil;

@Service("companyService")
public class CompanyService {

	@Autowired
	CompanyMapper companyMapper;
	
	/**
	 * 修改
	 */
	public String updateCompany(Map<String, String> para) {
		String msg = "";
		try {
			companyMapper.updateCompany(para);
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
	
	/**
	 * 查询
	 */
	public String selectCompany() {
		String msg = "";
		try {
			Company company = companyMapper.selectCompany();
			msg = HttpResponseUtil.convertCommonJsonString(0, "成功", "data", company);
		} catch (Exception e) {
			e.printStackTrace();
			msg = HttpResponseUtil.convertCommonJsonString(1, "失败", "data", e.getMessage());
		}
		return msg;
	}
}