package com.xsyu.biz;

import com.xsyu.bean.PageBean;
import com.xsyu.po.Clazz;
import com.xsyu.po.Student;

import java.util.List;

public interface IStudentBiz {
	boolean save(Student stu);
	boolean remove(Integer sid);
	boolean update(Student stu);
	Student findById(Integer sid);
	List<Student> findAll();
	void findByPage(PageBean pageBean);
	void setPageNum(PageBean pageBean);
	List<Clazz> doInit();
}
