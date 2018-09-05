package com.template.ie.dict;

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

import com.template.ie.model.Dict;

@Mapper
public interface DictMapper {

	/**
	 * 新增
	 */
	@Insert("insert into DICT(id,group_code,group_name,name,code,key,state,sort) "
			+ "values (#{id},#{group_code},#{group_name},#{name},#{code},#{key},#{state},#{sort})")
	int addDict(Map<String,String> map);
	
	/**
	 * 删除
	 */
	@Delete("delete from DICT where ID in ${id}")
	int deleteDict(@Param("id") String id);

	/**
	 * 修改
	 */
	@Update("update DICT set id=#{id},group_code=#{group_code},group_name=#{group_name},"
			+ "name=#{name},code=#{code},key=#{key},state=#{state},sort=#{sort} where ID = #{id}")
	int updateDict(Map<String,String> map);
	
	/**
	 * 按名称查询
	 */
	@Select("select * from ( select c.*, rownum rn from "
			+ "(select * from DICT where NAME like concat(concat('%',#{name}),'%') "
			+ "order by sort asc) c where rownum <= #{pageEnd}) where rn > #{pageStart}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "group_code", column = "GROUP_CODE"),
        @Result(property = "group_name", column = "GROUP_NAME"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "code", column = "CODE"),
        @Result(property = "key", column = "KEY"),
        @Result(property = "state", column = "STATE"),
        @Result(property = "sort", column = "SORT")
	})
	List<Dict> selectDict(@Param("name") String name,@Param("pageEnd") int pageEnd,
			@Param("pageStart") int pageStart);
	
	@Select("select count(*) from DICT where NAME like concat(concat('%',#{name}),'%') "
			+ "order by sort asc")
	int selectCount(@Param("name") String name);
	
	/**
	 * 按类型查询+分页
	 */
	@Select("select * from ( select c.*, rownum rn from "
			+ "(select * from DICT where GROUP_NAME like concat(concat('%',#{group_name}),'%') "
			+ "order by sort asc) c where rownum <= #{pageEnd}) where rn > #{pageStart}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "group_code", column = "GROUP_CODE"),
        @Result(property = "group_name", column = "GROUP_NAME"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "code", column = "CODE"),
        @Result(property = "key", column = "KEY"),
        @Result(property = "state", column = "STATE"),
        @Result(property = "sort", column = "SORT")
	})
	List<Dict> selectGroupNameDict(@Param("group_name") String group_name,
			@Param("pageEnd") int pageEnd,@Param("pageStart") int pageStart);
	
	@Select("select count(*) from DICT where GROUP_NAME like concat(concat('%',#{group_name}),'%') "
			+ "order by sort asc")
	int selectGroupNameCount();
	
	/**
	 * 按名称和类型查询
	 */
	@Select("select * from ( select c.*, rownum rn from "
			+ "(select * from DICT where GROUP_NAME like concat(concat('%',#{group_name}),'%') "
			+ "and NAME like concat(concat('%',#{name}),'%') order by sort asc) c "
			+ "where rownum <= #{pageEnd}) where rn > #{pageStart}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "group_code", column = "GROUP_CODE"),
        @Result(property = "group_name", column = "GROUP_NAME"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "code", column = "CODE"),
        @Result(property = "key", column = "KEY"),
        @Result(property = "state", column = "STATE"),
        @Result(property = "sort", column = "SORT")
	})
	List<Dict> selectTwoDict(@Param("name") String name, @Param("group_name") 
	String group_name,@Param("pageEnd") int pageEnd,@Param("pageStart") int pageStart);
	
	@Select("select count(*) from DICT where GROUP_NAME like concat(concat('%',#{group_name}),'%') "
			+ "and NAME like concat(concat('%',#{name}),'%') order by sort asc")
	int selectTwoCount();
	
	/**
	 * 查询分类（图片分类、角色分类）
	 */
	@Select("select * from DICT where GROUP_CODE = #{group_code} order by sort asc")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "group_code", column = "GROUP_CODE"),
        @Result(property = "group_name", column = "GROUP_NAME"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "code", column = "CODE"),
        @Result(property = "key", column = "KEY"),
        @Result(property = "state", column = "STATE"),
        @Result(property = "sort", column = "SORT")
	})
	List<Dict> selectClassifDict(@Param("group_code") String group_code);
	
	/**
	 * 分组查询
	 */
	@Select("select * from ( select c.*, rownum rn from "
			+ "(select GROUP_CODE,GROUP_NAME from DICT group by GROUP_CODE,GROUP_NAME "
			+ "order by GROUP_CODE asc) c where rownum <= #{pageEnd}) where rn > #{pageStart}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "group_code", column = "GROUP_CODE"),
        @Result(property = "group_name", column = "GROUP_NAME"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "code", column = "CODE"),
        @Result(property = "key", column = "KEY"),
        @Result(property = "state", column = "STATE"),
        @Result(property = "sort", column = "SORT")
	})
	List<Dict> selectGroupDict(@Param("pageEnd") int pageEnd,@Param("pageStart") int pageStart);
	
	@Select("select count(*) from (select GROUP_CODE,GROUP_NAME from DICT group by GROUP_CODE,GROUP_NAME "
			+ "order by GROUP_CODE asc)")
	int selectGroupCount();
	
	/**
	 * 查询全部+分页
	 */
	@Select("select * from ( select c.*, rownum rn from "
			+ "(select * from DICT order by sort asc) c where rownum <= #{pageEnd}) "
			+ "where rn > #{pageStart}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "group_code", column = "GROUP_CODE"),
        @Result(property = "group_name", column = "GROUP_NAME"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "code", column = "CODE"),
        @Result(property = "key", column = "KEY"),
        @Result(property = "state", column = "STATE"),
        @Result(property = "sort", column = "SORT")
	})
	List<Dict> selectAllDict(@Param("pageEnd") int pageEnd,@Param("pageStart") int pageStart);
	
	@Select("select count(*) from DICT order by sort asc")
	int selectAllCount();
	
	/**
	 * 启用、停用
	 */
	@Update("update DICT set STATE=#{state} where ID = #{id}")
	int stateDict(@Param("id") String id, @Param("state") String state);
}