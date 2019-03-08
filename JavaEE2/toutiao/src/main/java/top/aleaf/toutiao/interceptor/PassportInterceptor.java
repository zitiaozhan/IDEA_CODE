/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PassportInterceptor
 * Author:   郭新晔
 * Date:     2018/12/3 0003 14:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.aleaf.toutiao.dao.LoginTicketDAO;
import top.aleaf.toutiao.dao.UserDAO;
import top.aleaf.toutiao.model.HostHolder;
import top.aleaf.toutiao.model.LoginTicket;
import top.aleaf.toutiao.model.User;
import top.aleaf.toutiao.utils.ToutiaoUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 〈〉
 *
 * @create 2018/12/3 0003
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PassportInterceptor.class);
    @Autowired
    private LoginTicketDAO ticketDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String ticket = ToutiaoUtil.parseLoginTicket(request);
        LOGGER.info(PassportInterceptor.class.toString() + ":preHandle: ticket==null?" + (ticket == null));
        if (ticket != null) {
            LoginTicket loginTicket = this.ticketDAO.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;
            }
            User user = this.userDAO.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //页面渲染之前调用
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //完成之后进行收尾工作
        hostHolder.clear();
    }
}