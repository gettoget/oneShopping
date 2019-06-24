package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Table(name = "question")
public class Question {

    @Id
    private String id;

    /**
     * 内容
     */
    private String content;

    /**
     * 类型 1 问  2 答
     */
    private String type;
    /**
     * 留言时间 或 回复时间
     */
    private String cjsj;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 是否回复   1 已回复  2 未回复
     */
    private String hf;


    @Transient
    private String himg;
    @Transient
    private String username;
    @Transient
    private List<Question> replyList;

    public String getHf() {
        return hf;
    }

    public void setHf(String hf) {
        this.hf = hf;
    }

    public List<Question> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Question> replyList) {
        this.replyList = replyList;
    }

    public String getHimg() {
        return himg;
    }

    public void setHimg(String himg) {
        this.himg = himg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public enum InnerColumn {
        id("id"),
        userId("user_id"),
        cjsj("cjsj"),
        content("content"),
        type("type"),
        parentId("parent_id"),
        hf("hf");

        private final String column;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        InnerColumn(String column) {
            this.column = column;
        }

        public String desc() {
            return this.column + " DESC";
        }

        public String asc() {
            return this.column + " ASC";
        }
    }

}
