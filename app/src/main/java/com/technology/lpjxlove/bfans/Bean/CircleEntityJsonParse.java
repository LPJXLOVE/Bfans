package com.technology.lpjxlove.bfans.Bean;

import java.util.List;

/**
 * Created by LPJXLOVE on 2016/10/30.
 */

public class CircleEntityJsonParse {

    /**
     * Author : {"AvatarUrl":"http://bmob-cdn-6992.b0.upaiyun.com/2016/10/28/3eb07041e78a4db5a43905225e731cb3.png","LevelCount":0,"__type":"Object","className":"_User","createdAt":"2016-10-21 22:32:44","nickName":"LOVE","objectId":"b75b0467fe","updatedAt":"2016-10-28 22:32:42","username":"13539454496"}
     * BitmapUrl : ["http://bmob-cdn-6992.b0.upaiyun.com/2016/10/28/6cb45240fdf44c7cb3caac0a8e29d07f.jpg"]
     * Comment : {"__type":"Relation","className":"Comment"}
     * CommentCount : 2
     * Like : {"__type":"Relation","className":"_User"}
     * LikeCount : 1
     * createdAt : 2016-10-28 15:22:58
     * isLike : false
     * objectId : b30d91d891
     * updatedAt : 2016-10-29 22:07:36
     */

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * AvatarUrl : http://bmob-cdn-6992.b0.upaiyun.com/2016/10/28/3eb07041e78a4db5a43905225e731cb3.png
         * LevelCount : 0
         * __type : Object
         * className : _User
         * createdAt : 2016-10-21 22:32:44
         * nickName : LOVE
         * objectId : b75b0467fe
         * updatedAt : 2016-10-28 22:32:42
         * username : 13539454496
         */

        private AuthorBean Author;
        /**
         * __type : Relation
         * className : Comment
         */

        private CommentBean Comment;
        private int CommentCount;
        /**
         * __type : Relation
         * className : _User
         */

        private LikeBean Like;
        private int LikeCount;
        private String createdAt;
        private boolean isLike;
        private String objectId;
        private String updatedAt;
        private List<String> BitmapUrl;
        private String Content;

        public AuthorBean getAuthor() {
            return Author;
        }

        public void setAuthor(AuthorBean Author) {
            this.Author = Author;
        }

        public CommentBean getComment() {
            return Comment;
        }

        public void setComment(CommentBean Comment) {
            this.Comment = Comment;
        }

        public int getCommentCount() {
            return CommentCount;
        }

        public void setCommentCount(int CommentCount) {
            this.CommentCount = CommentCount;
        }

        public LikeBean getLike() {
            return Like;
        }

        public void setLike(LikeBean Like) {
            this.Like = Like;
        }

        public int getLikeCount() {
            return LikeCount;
        }

        public void setLikeCount(int LikeCount) {
            this.LikeCount = LikeCount;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public boolean isIsLike() {
            return isLike;
        }

        public void setIsLike(boolean isLike) {
            this.isLike = isLike;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<String> getBitmapUrl() {
            return BitmapUrl;
        }

        public void setBitmapUrl(List<String> BitmapUrl) {
            this.BitmapUrl = BitmapUrl;
        }


        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public static class AuthorBean {
            private String AvatarUrl;
            private int LevelCount;
            private String __type;
            private String className;
            private String createdAt;
            private String nickName;
            private String objectId;
            private String updatedAt;
            private String username;

            public String getAvatarUrl() {
                return AvatarUrl;
            }

            public void setAvatarUrl(String AvatarUrl) {
                this.AvatarUrl = AvatarUrl;
            }

            public int getLevelCount() {
                return LevelCount;
            }

            public void setLevelCount(int LevelCount) {
                this.LevelCount = LevelCount;
            }

            public String get__type() {
                return __type;
            }

            public void set__type(String __type) {
                this.__type = __type;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getObjectId() {
                return objectId;
            }

            public void setObjectId(String objectId) {
                this.objectId = objectId;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public static class CommentBean {
            private String __type;
            private String className;

            public String get__type() {
                return __type;
            }

            public void set__type(String __type) {
                this.__type = __type;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }
        }

        public static class LikeBean {
            private String __type;
            private String className;

            public String get__type() {
                return __type;
            }

            public void set__type(String __type) {
                this.__type = __type;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }
        }
    }
}
