package com.template.ie.navigation;

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
import com.template.ie.model.LeftMenu;

@Mapper
public interface LeftMenuMapper {

	/**
	 * 新增
	 */
	@Insert("insert into LEFT_MENU(ID,NAME,SORT,STATE) values (#{id},#{name},#{sort},#{state})")
	int addLeftMenu(Map<String,String> map);
	
	/**
	 * 删除
	 */
	@Delete("delete from LEFT_MENU where ID in ${id}")
	int deleteLeftMenu(@Param("id") String id);

	/**
	 * 修改
	 */
	@Update("update LEFT_MENU set ID=#{id},NAME=#{name},SORT=#{sort},STATE=#{state} where ID = #{id}")
	int updateLeftMenu(Map<String,String> map);
	
	/**
	 * 按id查询
	 */
	@Select("select * from LEFT_MENU where ID = #{id}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "sort", column = "SORT"),
        @Result(property = "state", column = "STATE")
	})
	List<LeftMenu> selectLeftMenu(@Param("id") String id);
	
	/**
	 * 查询全部
	 */
	@Select("select * from LEFT_MENU order by sort asc")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "sort", column = "SORT"),
        @Result(property = "state", column = "STATE")
	})
	List<LeftMenu> selectAllLeftMenu();
	
	/**
	 * 分页显示查询全部
	 */
//	@Select("select * from ( select c.*, rownum rn from "
//			+ "(select * from LEFT_MENU where ID != #{id} and ID != #{id0} order by sort asc) c where rownum <= #{pageEnd}) "
//			+ "where rn > #{pageStart}")
	@Select("select * from "
			+ "(select c.*, rownum rn from LEFT_MENU c where ID != #{id} and ID != #{id0} "
			+ "and rownum <= #{pageEnd} order by sort asc) "
			+ "where rn > #{pageStart}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "sort", column = "SORT"),
        @Result(property = "state", column = "STATE")
	})
	List<Dict> selectAllDict(@Param("id") String id,@Param("id0") String id0,@Param("pageEnd") int pageEnd,@Param("pageStart") int pageStart);

	@Select("select count(*) from LEFT_MENU where ID != #{id} and ID != #{id0} order by sort asc")
	int selectAllCount(@Param("id") String id,@Param("id0") String id0);
	
	/**
	 * 启用、停用
	 */
	@Update("update LEFT_MENU set STATE=#{state} where ID = #{id}")
	int stateLeftMenu(@Param("id") String id, @Param("state") String state);
}