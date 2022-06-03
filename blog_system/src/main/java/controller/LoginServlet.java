package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import model.UserDao;

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
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(username == null || "".equals(username)||password == null || "".equals(password)){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("用户名或密码不能为空");
            return;
        }
        UserDao userDao = new UserDao();
        User user = userDao.selectByName(username);
        if(user == null || !user.getPassword().equals(password)){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("用户名或密码有误");
            return;
        }

        HttpSession httpSession = req.getSession(true);
        httpSession.setAttribute("user",user);

        resp.sendRedirect("blog_list.html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        HttpSession httpSession = req.getSession(false);
        if(httpSession == null){
            User user = new User();
            resp.getWriter().write(objectMapper.writeValueAsString(user));
            return;
        }
        User user = (User)httpSession.getAttribute("user");
        if(user == null){
            //有会话没对象
            user = new User();
            resp.getWriter().write(objectMapper.writeValueAsString(user));
            return;
        }
        //密码不能泄露
        user.setPassword("");
        resp.getWriter().write(objectMapper.writeValueAsString(user));
    }
}
