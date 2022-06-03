package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author qiu
 * @version 1.8.0
 */

public class Blog {
    private int blogId;
    private String title;
    private String content;
    private int userId;
    private Timestamp postTime;

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPostTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd: HH-mm-ss");
        return simpleDateFormat.format(postTime);
    }

    public void setPostTime(Timestamp dataTime) {
        this.postTime = dataTime;
    }

    public int getBlogId() {
        return blogId;
    }
}
