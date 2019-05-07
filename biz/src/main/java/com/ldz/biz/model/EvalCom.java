package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "eval_com")
public class EvalCom {



    @Id
    private String id;
    /**
     * 评论id
     */
    @Column(name = "eval_id")
    private String evalId;

    /**
     * 评论人id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 商品id
     * @return
     */
    @Column(name = "pro_id")
    private String proId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvalId() {
        return evalId;
    }

    public void setEvalId(String evalId) {
        this.evalId = evalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public enum InnerColumn{
        id("id"),
        evalId("eval_id"),
        userId("user_id"),
        proId("pro_id");

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
