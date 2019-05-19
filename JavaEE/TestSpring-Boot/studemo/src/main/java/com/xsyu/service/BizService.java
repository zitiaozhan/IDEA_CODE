package com.xsyu.service;

import com.xsyu.biz.IStudentBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BizService {
	@Autowired
	private IStudentBiz studentBiz;

	public IStudentBiz getStudentBiz() {
		return studentBiz;
	}

	public void setStudentBiz(IStudentBiz studentBiz) {
		this.studentBiz = studentBiz;
	}

}
