package com.template.ie.image;

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

import com.template.ie.model.Image;

@Mapper
public interface ImageMapper {

	/**
	 * 新增
	 */
	@Insert("insert into IMAGE(id,name,title,image,introduce,code,state,time,sort) "
			+ "values (#{id},#{name},#{title},#{image},#{introduce},#{code},#{state},#{time},#{sort})")
	int addImage(Map<String,Object> map);
	
	/**
	 * 删除
	 */
	@Delete("delete from IMAGE where ID = #{id}")
	int deleteImageById(@Param("id") String id);
	
	/**
	 * 批量删除
	 */
	@Delete("delete from IMAGE where ID in ${id}")
	int deleteImage(@Param("id") String id);
	
	/**
	 * 修改
	 */
	@Update("update IMAGE set id=#{id},name=#{name},title=#{title},image=#{image},"
			+ "introduce=#{introduce},code=#{code},state=#{state},"
			+ "time=#{time},sort=#{sort} where id = #{id}")
	int updateImage(Map<String,Object> map);
	
	/**
	 * 查询全部+分页
	 */
	@Select("select * from ( select c.*, rownum rn from "
			+ "(select a.ID,a.NAME,a.TITLE,a.IMAGE,a.INTRODUCE,a.STATE,a.TIME,a.SORT,b.NAME as code from"
			+ " IMAGE a join LEFT_MENU b on a.CODE = b.ID order by sort asc,time desc) c where rownum <= #{pageEnd}) "
			+ "where rn > #{pageStart}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "title", column = "TITLE"),
        @Result(property = "image", column = "IMAGE"),
        @Result(property = "introduce", column = "INTRODUCE"),
        @Result(property = "code", column = "CODE"),
        @Result(property = "state", column = "STATE"),
        @Result(property = "time", column = "TIME"),
        @Result(property = "sort", column = "SORT")
	})
	List<Image> selectAllImage(@Param("pageEnd") int pageEnd,@Param("pageStart") int pageStart);
	
	@Select("select count(*) from (select a.ID,a.NAME,a.TITLE,a.IMAGE,a.INTRODUCE,a.STATE,a.TIME,a.SORT,"
			+ "b.NAME as code from IMAGE a join LEFT_MENU b on a.CODE = b.ID order by sort asc,time desc)")
	int selectAllCount();
	
	/**
	 * 查询此类型所有数据+分页
	 */
	@Select("select * from ( select c.*, rownum rn from "
			+ "(select a.ID,a.NAME,a.TITLE,a.IMAGE,a.INTRODUCE,a.STATE,a.TIME,a.SORT,b.NAME as code "
			+ "from IMAGE a join LEFT_MENU b on a.CODE = b.ID "
			+ "where a.CODE=#{code} order by sort asc,time desc) c "
			+ "where rownum <= #{pageEnd}) "
			+ "where rn > #{pageStart}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "title", column = "TITLE"),
        @Result(property = "image", column = "IMAGE"),
        @Result(property = "introduce", column = "INTRODUCE"),
        @Result(property = "code", column = "CODE"),
        @Result(property = "state", column = "STATE"),
        @Result(property = "time", column = "TIME"),
        @Result(property = "sort", column = "SORT")
	})
	List<Image> selectGroupNameImage(@Param("code") String code,@Param("pageEnd") int pageEnd,
			@Param("pageStart") int pageStart);
	
	@Select("select count(*) from (select a.ID,a.NAME,a.TITLE,a.IMAGE,a.INTRODUCE,a.STATE,a.TIME,a.SORT,"
			+ "b.NAME as code from IMAGE a join LEFT_MENU b on a.CODE = b.ID "
			+ "where a.CODE=#{code} order by sort asc,time desc)")
	int selectGroupNameCount(@Param("code") String code);
	
	/**
	 * 输入图片title查询+分页
	 */
	@Select("select * from ( select c.*, rownum rn from "
			+ "(select a.ID,a.NAME,a.TITLE,a.IMAGE,a.INTRODUCE,a.STATE,a.TIME,a.SORT,b.NAME as code "
			+ "from IMAGE a join LEFT_MENU b on a.CODE = b.ID "
			+ "where a.TITLE like concat(concat('%',#{title}),'%') order by sort asc,time desc) c "
			+ "where rownum <= #{pageEnd}) "
			+ "where rn > #{pageStart}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "title", column = "TITLE"),
        @Result(property = "image", column = "IMAGE"),
        @Result(property = "introduce", column = "INTRODUCE"),
        @Result(property = "code", column = "CODE"),
        @Result(property = "state", column = "STATE"),
        @Result(property = "time", column = "TIME"),
        @Result(property = "sort", column = "SORT")
	})
	List<Image> selectImage(@Param("title") String title,@Param("pageEnd") int pageEnd,
			@Param("pageStart") int pageStart);
	
	@Select("select count(*) from (select a.ID,a.NAME,a.TITLE,a.IMAGE,a.INTRODUCE,a.STATE,a.TIME,a.SORT,"
			+ "b.NAME as code from IMAGE a join LEFT_MENU b on a.CODE = b.ID "
			+ "where a.TITLE like concat(concat('%',#{title}),'%') order by sort asc,time desc)")
	int selectCount(@Param("title") String title);
	
	/**
	 * 查询此类型此title的数据+分页
	 */
	@Select("select * from (select c.*, rownum rn from "
			+ "(select a.ID,a.NAME,a.TITLE,a.IMAGE,a.INTRODUCE,a.STATE,a.TIME,a.SORT,b.NAME as code "
			+ "from IMAGE a join LEFT_MENU b on a.CODE = b.ID "
			+ "where a.TITLE like concat(concat('%',#{title}),'%') and a.CODE=#{code} order by sort asc,time desc) c "
			+ "where rownum <= #{pageEnd}) "
			+ "where rn > #{pageStart}")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "title", column = "TITLE"),
        @Result(property = "image", column = "IMAGE"),
        @Result(property = "introduce", column = "INTRODUCE"),
        @Result(property = "code", column = "CODE"),
        @Result(property = "state", column = "STATE"),
        @Result(property = "time", column = "TIME"),
        @Result(property = "sort", column = "SORT")
	})
	List<Image> selectTwoImage(@Param("title") String title,@Param("code") String code,
			@Param("pageEnd") int pageEnd,@Param("pageStart") int pageStart);
	
	@Select("select count(*) from (select a.ID,a.NAME,a.TITLE,a.IMAGE,a.INTRODUCE,a.STATE,a.TIME,a.SORT,"
			+ "b.NAME as code from IMAGE a join LEFT_MENU b on a.CODE = b.ID "
			+ "where a.TITLE like concat(concat('%',#{title}),'%') and a.CODE=#{code} order by sort asc,time desc)")
	int selectTwoCount(@Param("title") String title,@Param("code") String code);
	
	/**
	 * 查询此id
	 */
	@Select("select * from IMAGE where ID = #{id} order by sort asc,time desc")
	@Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "name", column = "NAME"),
        @Result(property = "title", column = "TITLE"),
        @Result(property = "image", column = "IMAGE"),
        @Result(property = "introduce", column = "INTRODUCE"),
        @Result(property = "code", column = "CODE"),
        @Result(property = "state", column = "STATE"),
        @Result(property = "time", column = "TIME"),
        @Result(property = "sort", column = "SORT")
	})
	List<Image> selectImageById(@Param("id") String id);
	
	/**
	 * 启用、停用
	 */
	@Update("update IMAGE set STATE=#{state} where ID = #{id}")
	int stateImage(@Param("id") String id, @Param("state") String state);
}