package com.xsyu.action;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xsyu.bean.PageBean;
import com.xsyu.po.Clazz;
import com.xsyu.po.Student;
import com.xsyu.service.BizService;
import com.xsyu.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Controller

public class StudentAction {
	@Autowired
	private BizService bizService;

	@RequestMapping(value="save_Student.action")
	//@PostMapping("/student/")
	public String save(Student stu, HttpServletResponse response) {
		JSONUtil.printString(response, this.bizService.getStudentBiz().save(stu)?"1":"0");
		return null;
	}

	@RequestMapping(value="remove_Student.action")
	//@DeleteMapping("/student/{sid}")
	public String remove(Integer sid, HttpServletResponse response) {
		JSONUtil.printString(response, this.bizService.getStudentBiz().remove(sid)?"1":"0");
		return null;
	}

	@RequestMapping(value="update_Student.action")
	//@PutMapping("/student/")
	public String update(Student stu,HttpServletResponse response) {
		JSONUtil.printString(response, this.bizService.getStudentBiz().update(stu)?"1":"0");
		return null;
	}

	@RequestMapping(value="findById_Student.action")
	//@GetMapping("/student/{sid}")
	public String findById(Integer sid,HttpServletResponse response) {
		Student oldStu=this.bizService.getStudentBiz().findById(sid);
		PropertyFilter filter=JSONUtil.propertyFilter("birthday","students");
		String json=JSONObject.toJSONString(oldStu,filter,SerializerFeature.DisableCircularReferenceDetect);
		JSONUtil.printString(response, json);
		return null;
	}

	@RequestMapping(value="findAll_Student.action")
	//@GetMapping("/student/")
	public String findAll(HttpServletResponse response) {
		List<Student> students=this.bizService.getStudentBiz().findAll();
		PropertyFilter filter=JSONUtil.propertyFilter("birthday","students");
		String json=JSONObject.toJSONString(students,filter,SerializerFeature.DisableCircularReferenceDetect);
		JSONUtil.printString(response, json);
		return null;
	}
	@RequestMapping(value="findByPage_Student.action")
	//@GetMapping("/student/")
	public String findByPage(HttpServletResponse response,Integer nowPage,Integer pageSize) {
		PageBean pageBean = new PageBean();
		pageBean.setPageSize(pageSize);
		this.bizService.getStudentBiz().setPageNum(pageBean);
		pageBean.setNowPage(nowPage);
		this.bizService.getStudentBiz().findByPage(pageBean);
		
		PropertyFilter filter=JSONUtil.propertyFilter("birthday","students");
		String json=JSONObject.toJSONString(pageBean,filter,SerializerFeature.DisableCircularReferenceDetect);
		JSONUtil.printString(response, json);
		
		return null;
	}

	@RequestMapping(value="doInit_Student.action")
	//@GetMapping("/clazz/")
	public String doInit(HttpServletResponse response) {
		List<Clazz> Clazzs=this.bizService.getStudentBiz().doInit();
		PropertyFilter filter=JSONUtil.propertyFilter("birthday","students");
		String json=JSONObject.toJSONString(Clazzs,filter,SerializerFeature.DisableCircularReferenceDetect);
		JSONUtil.printString(response, json);
		return null;
	}

}
