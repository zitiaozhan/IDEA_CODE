package com.xsyu.service;

import com.xsyu.biz.IStudentBiz;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("BizService")
public class BizService {
	@Resource(name="StudentBiz")
	private IStudentBiz studentBiz;

	public IStudentBiz getStudentBiz() {
		return studentBiz;
	}

	public void setStudentBiz(IStudentBiz studentBiz) {
		this.studentBiz = studentBiz;
	}
	
}
