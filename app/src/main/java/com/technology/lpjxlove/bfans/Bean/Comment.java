package com.technology.lpjxlove.bfans.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by LPJXLOVE on 2016/10/28.
 */

public class Comment extends BmobObject {
    private CircleEntity Post;
    private String Content;
    private User Author;

    public CircleEntity getPost() {
        return Post;
    }

    public void setPost(CircleEntity post) {
        Post = post;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public User getAuthor() {
        return Author;
    }

    public void setAuthor(User author) {
        Author = author;
    }
}
