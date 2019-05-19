package com.xsyu.po;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("all")
public class Clazz implements Serializable {
	private Integer cid;
	private String cname;
	private List<Student> students;
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname.trim();
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
}
