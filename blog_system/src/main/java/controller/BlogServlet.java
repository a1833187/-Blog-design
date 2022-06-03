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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author qiu
 * @version 1.8.0
 */
@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    //用来获取数据库中的博客列表
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String flag = req.getParameter("blogId");
        BlogDao blogDao = new BlogDao();
        if (flag == null) {
            //获取到数据库中的博客列表
            List<Blog> blogs = blogDao.selectAll();
            //将列表转为JSON
            String respJson = objectMapper.writeValueAsString(blogs);
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(respJson);
        }else{
            //获取某一篇博客的blogId从而得到对应的博客详情页的信息
            int blogId = Integer.parseInt(flag);
            Blog blog = blogDao.selectBlogById(blogId);
            String respJson = objectMapper.writeValueAsString(blog);
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(respJson);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession httpSession = req.getSession(false);
        if(httpSession == null){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("当前用户未登录,不能发布博客");
            return;
        }
        BlogDao blogDao =new BlogDao();
        User user = (User) httpSession.getAttribute("user");
        if(user == null){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("当前用户不存在,不能发布博客");
            return;
        }
        Blog blog = new Blog();
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if(title == null || "".equals(title) || content == null || "".equals(content)){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("缺乏必要参数,不能发布博客");
            return;
        }
        int userId = user.getUserId();
        blog.setUserId(userId);
        blog.setTitle(title);
        blog.setContent(content);
        blogDao.insert(blog);
        resp.sendRedirect("blog_list.html");
    }
}
