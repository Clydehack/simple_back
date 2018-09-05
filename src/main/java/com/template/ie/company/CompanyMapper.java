package com.template.ie.company;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;

import com.template.ie.model.Company;

@Mapper
public interface CompanyMapper {

	/**
	 * 修改
	 */
	@Update("update COMPANY_INFO set ID=#{id},NAME=#{name},ADDRESS=#{address},"
			+ "LEGAL_PERSON=#{legal_person},TITLE=#{title},WECHAT_QRCODE=#{wechat_qrcode},"
			+ "PHONE=#{phone},EMAIL=#{email},WEIBO=#{weibo},SORT=#{sort}")
	int updateCompany(Map<String,String> map);
	
	/**
	 * 查询
	 */
	@Select("select * from COMPANY_INFO")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "address", column = "ADDRESS"),
        @Result(property = "legal_person", column = "LEGAL_PERSON"),
        @Result(property = "title", column = "TITLE"),
        @Result(property = "wechat_qrcode", column = "WECHAT_QRCODE"),
        @Result(property = "phone", column = "PHONE"),
        @Result(property = "email", column = "EMAIL"),
        @Result(property = "weibo", column = "WEIBO"),
        @Result(property = "sort", column = "SORT")
	})
	Company selectCompany();
}