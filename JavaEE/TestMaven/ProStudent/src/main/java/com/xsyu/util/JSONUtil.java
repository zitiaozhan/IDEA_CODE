package com.xsyu.util;

import com.alibaba.fastjson.serializer.PropertyFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JSONUtil {
	/**
	 * 向客户端打印JSON字符串
	 * @param response
	 * @param str
	 */
	public static void printString(HttpServletResponse response, String str){
		response.setCharacterEncoding("utf-8");
		PrintWriter writer=null;
		try {
			writer=response.getWriter();
			writer.print(str);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			writer.close();
		}
	}
	/**
	 * 过滤属性
	 * @param params
	 * @return
	 */
	public static PropertyFilter propertyFilter(final String...params){
		PropertyFilter pf=new PropertyFilter(){
			@Override
			public boolean apply(Object arg0, String proName, Object arg2) {
				for (String pro : params) {
					if(proName.equals(pro)){
						return false;
					}
				}
				return true;
			}
		};
		return pf;
	}
}
