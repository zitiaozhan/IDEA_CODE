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
package top.aleaf.interceptor;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.aleaf.mapper.LoginTicketMapper;
import top.aleaf.mapper.RoleMapper;
import top.aleaf.mapper.UserMapper;
import top.aleaf.model.HostHolder;
import top.aleaf.model.LoginTicket;
import top.aleaf.model.Role;
import top.aleaf.model.User;
import top.aleaf.service.MessageService;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.GeneralExample;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/1/18 0018
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Resource
    private LoginTicketMapper ticketMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MessageService messageService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private ConstantUtil constantUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String ticket = "";
        if (request.getCookies() != null) {
            for (Cookie item : request.getCookies()) {
                if (item.getName().equals(ConstantUtil.TOKEN)) {
                    ticket = item.getValue();
                    break;
                }
            }
        }
        if (!Strings.isNullOrEmpty(ticket)) {
            LoginTicket loginTicket = this.ticketMapper.selectOneByExample(
                    GeneralExample.getBaseAndConditionExample(
                            LoginTicket.class, " and ticket='" + ticket + "'", true));
            if (loginTicket != null) {
                if (loginTicket.getExpired().after(new Date())) {
                    User localUser = this.userMapper.selectByPrimaryKey(loginTicket.getUserId());
                    if (localUser != null) {
                        Role selectRole = new Role();
                        selectRole.setId(localUser.getRoleId());
                        Role localRole = this.roleMapper.selectOne(selectRole);
                        this.hostHolder.setUser(localUser);
                        this.hostHolder.setRole(localRole);
                    }
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
            modelAndView.addObject("optionDatas", constantUtil.optionDatas);

            if (hostHolder.getUser() != null) {
                modelAndView.addObject("localUser", hostHolder.getUser());
                modelAndView.addObject("localRole", hostHolder.getRole());
                modelAndView.addObject("unreadSum", this.messageService.getAllUnreadCount(hostHolder.getUser().getId()));
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        this.hostHolder.clear();
    }
}