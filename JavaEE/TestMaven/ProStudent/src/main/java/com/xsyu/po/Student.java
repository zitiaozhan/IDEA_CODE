package com.xsyu.po;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("all")
public class Student implements Serializable {
	private Integer sid;
	private String sname;
	private String sex;
	private String address;
	private String edu;
	private Date birthday;
	private Integer cid;
	private Clazz clazz;
	
	private String birthdayStr;
	
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname.trim();
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address.trim();
	}
	public String getEdu() {
		return edu;
	}
	public void setEdu(String edu) {
		this.edu = edu.trim();
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Clazz getClazz() {
		return clazz;
	}
	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}
	public String getBirthdayStr() {
		return birthday!=null?new SimpleDateFormat("yyyy-MM-dd").format(birthday):null;
	}
	public void setBirthdayStr(String birthdayStr) {
		try {
			this.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthdayStr));
		} catch (ParseException e) {
			System.err.println("实体类日期属性转化出错!");
		}
	}
}
