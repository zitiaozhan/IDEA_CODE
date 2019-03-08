/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PassportInterceptor
 * Author:   郭新晔
 * Date:     2019/1/18 0018 21:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.aleaf.wenda.mapper.LoginTicketMapper;
import top.aleaf.wenda.mapper.UserMapper;
import top.aleaf.wenda.model.HostHolder;
import top.aleaf.wenda.model.LoginTicket;
import top.aleaf.wenda.util.GeneralExample;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 〈〉
 *
 * @create 2019/1/18 0018
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PassportInterceptor.class);
    @Autowired
    private LoginTicketMapper ticketMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String ticket = "";
        for (Cookie item : request.getCookies()) {
            if (item.getName().equals("ticket")) {
                ticket = item.getValue();
                break;
            }
        }
        if (ticket != null) {
            LoginTicket loginTicket = this.ticketMapper.selectOneByExample(
                    GeneralExample.getBaseAndConditionExample(
                            LoginTicket.class, " and ticket='" + ticket+"'", true));
            if (loginTicket != null) {
                if (loginTicket.getExpired().after(new Date())) {
                    this.hostHolder.setUser(this.userMapper.selectByPrimaryKey(loginTicket.getUserId()));
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o,
                           ModelAndView modelAndView) throws Exception {
        //页面渲染之前调用
        if (modelAndView != null) {
            if (hostHolder.getUser() != null){
                modelAndView.addObject("user", hostHolder.getUser());
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        this.hostHolder.clear();
    }
}