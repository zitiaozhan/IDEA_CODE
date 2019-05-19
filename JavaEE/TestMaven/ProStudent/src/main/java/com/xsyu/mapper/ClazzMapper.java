package com.xsyu.mapper;

import com.xsyu.po.Clazz;
import com.xsyu.po.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ClazzMapper")
public interface ClazzMapper {
	int save(Clazz clazz);
	int remove(Integer cid);
	int update(Clazz clazz);
	Clazz findById(Integer cid);
	List<Clazz> findAll();
	List<Student> findStudentByCid(Integer cid);
}
