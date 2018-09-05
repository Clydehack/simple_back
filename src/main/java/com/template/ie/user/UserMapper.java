package com.template.ie.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.template.ie.model.User;

@Mapper
public interface UserMapper {
	
	/**
	 * 新增
	 */
	@Insert("insert into USE_INFO(ID,NAME,PASSWORD,STATE) values (#{id},#{name},#{password},#{state})")
	int addUser(Map<String,String> map);
	
	/**
	 * 删除
	 */
	@Delete("delete from USE_INFO where ID in ${id}")
	int deleteUser(@Param("id") String id);

	/**
	 * 修改
	 */
	@Update("update USE_INFO set ID=#{id},NAME=#{name},PASSWORD=#{password},STATE=#{state} where ID = #{id}")
	int updateUser(Map<String,String> map);
	
	/**
	 * 查询用户是否存在
	 */
	@Select("select count(*) from USE_INFO where NAME = #{name} and PASSWORD = #{password}")
	int selectUserExist(@Param("name")String name,@Param("password")String password);
	
	/**
	 * 查询所有用户
	 */
	@Select("select * from USE_INFO")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "password", column = "PASSWORD"),
        @Result(property = "state", column = "STATE")
	})
	List<User> selectAllUser();
	
	/**
	 * 启用、停用
	 */
	@Update("update USE_INFO set STATE=#{state} where where a.TITLE like concat(concat('%',#{title}),'%')")
	int stateUser(@Param("id") String id, @Param("state") String state);
}