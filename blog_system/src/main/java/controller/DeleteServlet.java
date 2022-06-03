package controller;

import model.Blog;
import model.BlogDao;
import model.User;

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
@WebServlet("/blogDelete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        HttpSession httpSession = req.getSession(false);
        if(httpSession == null){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("当前用户尚未登录,没有删除权限");
            return;
        }
        User user = (User)httpSession.getAttribute("user");
        if(user == null){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("当前用户尚未登录,没有删除权限");
            return;
        }
        String blogId = req.getParameter("blogId");
        if(blogId == null || "".equals(blogId)){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("博客的参数不对,无法删除");
            return;
        }
        BlogDao blogDao = new BlogDao();
        Blog blog = blogDao.selectBlogById(Integer.parseInt(blogId));
        if(blog == null || "".equals(blog)){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("当前博客不存在");
            return;
        }
        if(user.getUserId() != blog.getUserId()){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("当前用户不是该文章的作者,没有删除权限");
            return;
        }
        blogDao.deleteBlogById(Integer.parseInt(blogId));
        resp.sendRedirect("blog_list.html");
    }
}
