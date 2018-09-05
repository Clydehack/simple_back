package com.template.ie.model;

/**
 * @author Clyde
 *
 * 功能：公司信息
 */
public class Company {

	private String id;						//id
	private String name;					//名称
	private String address;					//地址
	private String legal_person;			//保留字段
	private String title;					//保留字段
	private String wechat_qrcode;			//微信二维码地址
	private String phone;					//电话号码
	private String email;					//电子邮件
	private String weibo;					//微博名称
	private String sort;					//排序
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLegal_person() {
		return legal_person;
	}
	public void setLegal_person(String legal_person) {
		this.legal_person = legal_person;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWechat_qrcode() {
		return wechat_qrcode;
	}
	public void setWechat_qrcode(String wechat_qrcode) {
		this.wechat_qrcode = wechat_qrcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeibo() {
		return weibo;
	}
	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}