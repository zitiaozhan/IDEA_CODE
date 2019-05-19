package testDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 郭新晔 on 2018/5/7 0007.
 */
@WebServlet(name = "SimpleServlet")
public class SimpleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("textpost");
        request.setAttribute("method","post");
        request.getSession().setAttribute("text",text);
        request.getRequestDispatcher(request.getContextPath()+"/index.jsp").forward(request,response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("textget");
        request.setAttribute("method","get");
        request.getSession().setAttribute("text",text);
        request.getRequestDispatcher(request.getContextPath()+"/index.jsp").forward(request,response);
    }
}
