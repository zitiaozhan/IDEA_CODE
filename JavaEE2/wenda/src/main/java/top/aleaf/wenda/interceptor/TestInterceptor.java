/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestInterceptor
 * Author:   郭新晔
 * Date:     2019/1/20 0020 16:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈〉
 * @create 2019/1/20 0020
 */
@Component
public class TestInterceptor implements HandlerInterceptor {
    /**
     * 页面加载前调用
     * @param request
     * @param response
     * @param handler
     * @return
     *      返回true：表示拦截器执行链尚未执行完成
     *      返回false：表示拦截器执行链已经执行完成，之后的所有都不会再执行，项目会停止
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return false;
    }

    /**
     * 页面渲染时调用
     * @param request
     * @param response
     * @param handler
     * @param modelAndView  页面渲染使用的ModelAndView对象
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 页面渲染完成后调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}