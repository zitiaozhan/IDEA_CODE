package com.xsyu.bean;

import java.util.ArrayList;
import java.util.List;
import java.lang.Long;

public class PageBean {
	private Integer nowPage=1;
	private Integer pageSize=5;
	private Long dataNum=1L;
	private List<?> pageData=null;
	
	public Integer getNowPage() {
		return nowPage<1?1:nowPage;
	}
	/**
	 * 设置当前页之前必须先设置好总的页数,否则无法比较当前页与总页数的大小
	 * @param nowPage
	 */
	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage==null||nowPage<1?this.nowPage:nowPage;
	}
	public Integer getPageSize() {
		return pageSize<1?1:pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize==null||pageSize<1?this.pageSize:pageSize;
	}

	public Long getDataNum() {
		return dataNum;
	}

	public void setDataNum(Long dataNum) {
		this.dataNum = dataNum!=null&&dataNum>0?dataNum:this.dataNum;
	}

	public List<?> getPageData() {
		return pageData;
	}
	public void setPageData(List<?> pageData) {
		this.pageData = pageData==null?new ArrayList<Object>():pageData;
	}
	
}
