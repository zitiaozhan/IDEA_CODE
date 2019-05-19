package com.xsyu.service;

import com.xsyu.mapper.ClazzMapper;
import com.xsyu.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("MapperService")
public class MapperService {
	@Resource(name="StudentMapper")
	private StudentMapper studentMapper;
	@Resource(name="ClazzMapper")
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
