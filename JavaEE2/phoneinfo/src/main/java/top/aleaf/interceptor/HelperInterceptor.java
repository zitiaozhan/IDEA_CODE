/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HelperInterceptor
 * Author:   郭新晔
 * Date:     2018/12/21 0021 20:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.aleaf.model.HostHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 〈〉
 *
 * @create 2018/12/21 0021
 */
@Component
public class HelperInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelperInterceptor.class);
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        hostHolder.setSpace(new HashMap<String, Object>());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //完成之后进行收尾工作
        hostHolder.clear();
    }
}