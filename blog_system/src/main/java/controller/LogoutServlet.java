package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author qiu
 * @version 1.8.0
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取到会话信息并删除
        HttpSession httpSession = req.getSession(false);
        resp.setContentType("text/html;charset=utf-8");
        if(httpSession == null){
            resp.getWriter().write("当前用户尚未登录!");
            return;
        }
        httpSession.removeAttribute("user");
        resp.sendRedirect("blog_login.html");
    }
}
