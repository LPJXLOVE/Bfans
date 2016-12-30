package com.technology.lpjxlove.bfans.Bean;

import java.util.List;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public class CircleEntity extends Entity {
    private String Content;
    private int LikeCount;
    private int CommentCount;
    private User Author;
    private boolean isLike;
    private List<String> BitmapUrl;
    private Like Like;
    private Comment Comment;
    private String CreateAt;

    public String getCreateTime() {
        return CreateAt;
    }

    public void setCreateAt(String createAt) {
        CreateAt = createAt;
    }

    public com.technology.lpjxlove.bfans.Bean.Like getLike() {
        return Like;
    }

    public void setLike(com.technology.lpjxlove.bfans.Bean.Like like) {
        Like = like;
    }

    public com.technology.lpjxlove.bfans.Bean.Comment getComment() {
        return Comment;
    }

    public void setComment(com.technology.lpjxlove.bfans.Bean.Comment comment) {
        Comment = comment;
    }

    public User getAuthor() {
        return Author;
    }

    public void setAuthor(User author) {
        Author = author;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public List<String> getBitmapUrl() {
        return BitmapUrl;
    }

    public void setBitmapUrl(List<String> bitmapUrl) {
        BitmapUrl = bitmapUrl;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(int likeCount) {
        LikeCount = likeCount;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }
}
