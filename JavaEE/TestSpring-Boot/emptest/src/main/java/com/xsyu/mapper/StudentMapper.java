package com.xsyu.mapper;

import com.xsyu.bean.PageBean;
import com.xsyu.po.Clazz;
import com.xsyu.po.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StudentMapper")
@Mapper
public interface StudentMapper {
	int save(Student stu);
	int remove(Integer sid);
	int update(Student stu);
	Student findById(Integer sid);
	Clazz findClazzByCid(Integer cid);
	List<Student> findAll();
	long getRows();
	List<Student> getPageData(PageBean pageBean);
}
