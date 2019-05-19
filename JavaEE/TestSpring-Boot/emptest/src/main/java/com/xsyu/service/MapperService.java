package com.xsyu.service;

import com.xsyu.mapper.ClazzMapper;
import com.xsyu.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapperService {
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private ClazzMapper clazzMapper;
	public StudentMapper getStudentMapper() {
		return studentMapper;
	}
	public void setStudentMapper(StudentMapper studentMapper) {
		this.studentMapper = studentMapper;
	}
	public ClazzMapper getClazzMapper() {
		return clazzMapper;
	}
	public void setClazzMapper(ClazzMapper clazzMapper) {
		this.clazzMapper = clazzMapper;
	}
}
