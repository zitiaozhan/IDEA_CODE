package com.xsyu.action;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xsyu.bean.PageBean;
import com.xsyu.po.Clazz;
import com.xsyu.po.Edu;
import com.xsyu.po.Student;
import com.xsyu.service.BizService;
import com.xsyu.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
		PropertyFilter filter=JSONUtil.filteProperty("birthday","students");
		String json=JSONObject.toJSONString(oldStu,filter,SerializerFeature.DisableCircularReferenceDetect);
		JSONUtil.printString(response, json);
		return null;
	}

	@RequestMapping(value="findAll_Student.action")
	//@GetMapping("/student/")
	public String findAll(HttpServletResponse response) {
		List<Student> students=this.bizService.getStudentBiz().findAll();
		PropertyFilter filter=JSONUtil.filteProperty("birthday","students");
		String json=JSONObject.toJSONString(students,filter,SerializerFeature.DisableCircularReferenceDetect);
		JSONUtil.printString(response, json);
		return null;
	}
	@RequestMapping(value="findByPage_Student.action")
	//@GetMapping("/student/")
	public String findByPage(HttpServletResponse response,Integer nowPage,Integer pageSize) {
		nowPage=nowPage==null?1:nowPage;
		pageSize=pageSize==null?5:pageSize;
		PageBean pageBean = new PageBean();
		pageBean.setPageSize(pageSize);
		this.bizService.getStudentBiz().setDataNum(pageBean);
		pageBean.setNowPage(nowPage);
		this.bizService.getStudentBiz().findByPage(pageBean);
		
		PropertyFilter filter=JSONUtil.filteProperty("birthday","students");
		String json=JSONObject.toJSONString(pageBean.getPageData(),filter,SerializerFeature.DisableCircularReferenceDetect);
		JSONUtil.printString(response, json);
		
		return null;
	}

	@RequestMapping(value="doInit_Student.action")
	//@GetMapping("/clazz/")
	public String doInit(HttpServletResponse response) {
		List<Clazz> Clazzs=this.bizService.getStudentBiz().doInit();
		PropertyFilter filter=JSONUtil.filteProperty("birthday","students");
		String json=JSONObject.toJSONString(Clazzs,filter,SerializerFeature.DisableCircularReferenceDetect);
		JSONUtil.printString(response, json);
		return null;
	}

	@RequestMapping(value="initEdu_Student.action")
	//@GetMapping("/edus/")
	public String initEdu(HttpServletResponse response) {
		List<Edu> edus=new ArrayList<>();
		edus.add(new Edu(1,"小学"));
		edus.add(new Edu(2,"初中"));
		edus.add(new Edu(3,"高中"));
		edus.add(new Edu(4,"大专"));
		edus.add(new Edu(5,"本科"));
		edus.add(new Edu(6,"硕士"));
		edus.add(new Edu(7,"博士"));
		String json=JSONObject.toJSONString(edus,SerializerFeature.DisableCircularReferenceDetect);
		JSONUtil.printString(response, json);
		return null;
	}

}
