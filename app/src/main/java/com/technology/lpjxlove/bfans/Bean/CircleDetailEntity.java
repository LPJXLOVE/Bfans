package com.technology.lpjxlove.bfans.Bean;

import java.util.List;

/**
 * Created by LPJXLOVE on 2016/10/29.
 */

public class CircleDetailEntity {
    private List<Comment> comments;
    private List<User> likes;
    private CircleEntity circleEntity;

    public CircleEntity getCircleEntity() {
        return circleEntity;
    }

    public void setCircleEntity(CircleEntity circleEntity) {
        this.circleEntity = circleEntity;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


}
