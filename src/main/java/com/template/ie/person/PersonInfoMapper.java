package com.template.ie.person;

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

import com.template.ie.model.PersonInfo;

@Mapper
public interface PersonInfoMapper {

	/**
	 * 新增
	 */
	@Insert("insert into PERSON_INFO(ID,NAME,ROLE_CODE,TITLE,IMAGE,PRODUCE,STATE,SORT) "
			+ "values (#{id},#{name},#{role_code},#{title},#{image},#{produce},#{state},#{sort})")
	int addPersonInfo(Map<String,String> map);
	
	/**
	 * 删除
	 */
	@Delete("delete from PERSON_INFO where ID in ${id}")
	int deletePersonInfo(@Param("id") String id);
	
	/**
	 * 修改
	 */
	@Update("update PERSON_INFO set ID=#{id},NAME=#{name},ROLE_CODE=#{role_code},TITLE=#{title},"
			+ "IMAGE=#{image},PRODUCE=#{produce},STATE=#{state},SORT=#{sort} where ID = #{id}")
	int updatePersonInfo(Map<String,String> map);
	
	/**
	 * 按id查询
	 */
	@Select("select * from PERSON_INFO where ID = #{id}")
//	@Select("select a.ID,a.NAME,a.TITLE,a.IMAGE,a.PRODUCE,a.STATE,a.SORT,b.NAME as ROLE_CODE "
//			+ "from PERSON_INFO a left join DICT b on a.ROLE_CODE = b.CODE where a.ID = #{id}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "role_code", column = "ROLE_CODE"),
        @Result(property = "title", column = "TITLE"),
        @Result(property = "image", column = "IMAGE"),
        @Result(property = "produce", column = "PRODUCE"),
        @Result(property = "sort", column = "SORT"),
        @Result(property = "state", column = "STATE")
	})
	List<PersonInfo> selectPersonInfo(@Param("id") String id);
	
	/**
	 * 查询全部+分页
	 */
//	@Select("select * from PERSON_INFO")
	@Select("select * from ( select c.*, rownum rn from "
			+ "(select a.ID,a.NAME,a.TITLE,a.IMAGE,a.PRODUCE,a.STATE,a.SORT,b.NAME as ROLE_CODE "
			+ "from PERSON_INFO a join DICT b on a.ROLE_CODE = b.CODE order by sort asc) c "
			+ "where rownum <= #{pageEnd}) where rn > #{pageStart}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "role_code", column = "ROLE_CODE"),
        @Result(property = "title", column = "TITLE"),
        @Result(property = "image", column = "IMAGE"),
        @Result(property = "produce", column = "PRODUCE"),
        @Result(property = "sort", column = "SORT"),
        @Result(property = "state", column = "STATE")
	})
	List<PersonInfo> selectAllPersonInfo(@Param("pageEnd") int pageEnd,@Param("pageStart") int pageStart);
	
	@Select("select count(*) from (select a.ID,a.NAME,a.TITLE,a.IMAGE,a.PRODUCE,"
			+ "a.STATE,a.SORT,b.NAME as ROLE_CODE "
			+ "from PERSON_INFO a join DICT b on a.ROLE_CODE = b.CODE order by sort asc) ")
	int selectAllCount();
	
	/**
	 * 启用
	 */
	@Update("update PERSON_INFO set STATE=#{state} where ID = #{id}")
	int statePersonInfo(@Param("id") String id, @Param("state") String state);
}