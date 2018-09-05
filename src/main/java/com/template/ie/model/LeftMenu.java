package com.template.ie.model;

public class LeftMenu {
	
    private String id;				//模块id
    private String name;			//模块名称
    private String sort;			//排序ID
    private String state;			//模块状态 （启用：0  停用：1）
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}