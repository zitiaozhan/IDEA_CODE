package top.aleaf.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @author 郭新晔
 * @Description: 转义所有form数据的过滤器
 */
public class XSSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =  (HttpServletRequest)servletRequest;
        filterChain.doFilter(new XSSRequestWrapper(request) , servletResponse);
    }
 
    @Override
    public void destroy() {
 
    }
}
