package com.xsyu.biz;

import com.xsyu.bean.PageBean;
import com.xsyu.po.Clazz;
import com.xsyu.po.Student;
import com.xsyu.service.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentBiz implements IStudentBiz {
	@Autowired
	private MapperService mapperService;

	@Override
	public boolean save(Student stu) {
		return stu!=null?this.mapperService.getStudentMapper().save(stu)>0:false;
	}
	@Override
	public boolean remove(Integer sid) {
		return sid!=null?this.mapperService.getStudentMapper().remove(sid)>0:false;
	}
	@Override
	public boolean update(Student stu) {
		return stu!=null?this.mapperService.getStudentMapper().update(stu)>0:false;
	}
	@Override
	public Student findById(Integer sid) {
		return sid!=null?this.mapperService.getStudentMapper().findById(sid):null;
	}
	@Override
	public List<Student> findAll() {
		return this.mapperService.getStudentMapper().findAll();
	}
	@Override
	public List<Clazz> doInit() {
		return this.mapperService.getClazzMapper().findAll();
	}
	@Override
	public void findByPage(PageBean pageBean) {
		pageBean.setPageData(this.mapperService.getStudentMapper().getPageData(pageBean));
	}

	@Override
	public void setPageNum(PageBean pageBean) {
		long rows=this.mapperService.getStudentMapper().getRows();
		int pageNum=(int) (rows%pageBean.getPageSize()==0?(rows/pageBean.getPageSize()):(rows/pageBean.getPageSize()+1));
		
		pageBean.setPageNum(pageNum);
	}

}
