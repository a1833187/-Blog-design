package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qiu
 * @version 1.8.0
 */
@WebServlet("/authorInfo")
public class AuthorServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        String blogId = req.getParameter("blogId");
        if(blogId == null || "".equals("blogId")){
            resp.getWriter().write("{\"ok\" : false, \"reason\" : \"参数缺失\"}");
            return;
        }
        BlogDao blogDao = new BlogDao();
        Blog blog = blogDao.selectBlogById(Integer.parseInt(blogId));
        if(blog == null){
            resp.getWriter().write("{\"ok\" : false, \"reason\" : \"查询的博客不存在\"}");
            return;
        }
        UserDao userDao = new UserDao();
        User user = userDao.selectById(blog.getUserId());
        if(user == null){
            resp.getWriter().write("{\"ok\" : false, \"reason\" : \"查询的用户不存在\"}");
            return;
        }
        user.setPassword("");
        resp.getWriter().write(objectMapper.writeValueAsString(user));
    }
}
