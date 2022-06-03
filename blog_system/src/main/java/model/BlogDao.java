package model;

import javafx.scene.Parent;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiu
 * @version 1.8.0
 */
public class BlogDao {
    /*
    1. 往博客表中插入一个博客
    2. 获取到博客表中的所有博客(博客列表页)
    3. 根据博客Id获取到指定的博客内容(博客详情页)
    4. 根据博客Id删除一个博客

    实现了增删查,没有改.
     */

    public void insert(Blog blog){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();

            String sql = "insert into blog values(null,?,?,?,now())";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,blog.getTitle());
            preparedStatement.setString(2,blog.getContent());
            preparedStatement.setInt(3,blog.getUserId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭 ??
            DBUtil.close(connection,preparedStatement,resultSet);
        }

    }
    public List<Blog> selectAll(){
        List<Blog> blogs = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();

            String sql = "select * from blog order by postTime desc";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                //这里需要对正文进行截断
                String content =resultSet.getString("content");
                if(content.length() > 50){
                    content = content.substring(0,50)+"...";
                }
                blog.setContent(content);
                blog.setUserId(resultSet.getInt("userId"));
                blog.setPostTime(resultSet.getTimestamp("postTime"));
                blogs.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭
            DBUtil.close(connection,preparedStatement,resultSet);
        }
        return blogs;
    }
    public Blog selectBlogById(int blogId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();

            String sql = "select * from blog where blogId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,blogId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setUserId(resultSet.getInt("userId"));
                blog.setPostTime(resultSet.getTimestamp("postTime"));
                return blog;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭
            DBUtil.close(connection,preparedStatement,resultSet);
        }
        return null;
    }
    public void deleteBlogById(int blogId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "delete from blog where blogId = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,blogId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,preparedStatement,null);
        }
    }
}
