package com.ldz.biz.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "reffer_record")
public class RefferRecord implements Serializable {
    @Id
    private String id;

    /**
     * 推荐人id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 被推人id
     */
    @Column(name = "apply_id")
    private String applyId;

    /**
     * 推荐时间
     */
    private String cjsj;

    /**
     * 推荐人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 被推人姓名
     */
    @Column(name = "apply_name")
    private String applyName;

    /**
     * 备注一
     */
    private String bz1;

    /**
     * 备注 二
     */
    private String bz2;

    /**
     * 备注三
     */
    private String bz3;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取推荐人id
     *
     * @return user_id - 推荐人id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置推荐人id
     *
     * @param userId 推荐人id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取被推人id
     *
     * @return apply_id - 被推人id
     */
    public String getApplyId() {
        return applyId;
    }

    /**
     * 设置被推人id
     *
     * @param applyId 被推人id
     */
    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    /**
     * 获取推荐时间
     *
     * @return cjsj - 推荐时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置推荐时间
     *
     * @param cjsj 推荐时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取推荐人姓名
     *
     * @return user_name - 推荐人姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置推荐人姓名
     *
     * @param userName 推荐人姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取被推人姓名
     *
     * @return apply_name - 被推人姓名
     */
    public String getApplyName() {
        return applyName;
    }

    /**
     * 设置被推人姓名
     *
     * @param applyName 被推人姓名
     */
    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    /**
     * 获取备注一
     *
     * @return bz1 - 备注一
     */
    public String getBz1() {
        return bz1;
    }

    /**
     * 设置备注一
     *
     * @param bz1 备注一
     */
    public void setBz1(String bz1) {
        this.bz1 = bz1;
    }

    /**
     * 获取备注 二
     *
     * @return bz2 - 备注 二
     */
    public String getBz2() {
        return bz2;
    }

    /**
     * 设置备注 二
     *
     * @param bz2 备注 二
     */
    public void setBz2(String bz2) {
        this.bz2 = bz2;
    }

    /**
     * 获取备注三
     *
     * @return bz3 - 备注三
     */
    public String getBz3() {
        return bz3;
    }

    /**
     * 设置备注三
     *
     * @param bz3 备注三
     */
    public void setBz3(String bz3) {
        this.bz3 = bz3;
    }

    public enum InnerColumn {
        id("id"),
        userId("user_id"),
        applyId("apply_id"),
        cjsj("cjsj"),
        userName("user_name"),
        applyName("apply_name"),
        bz1("bz1"),
        bz2("bz2"),
        bz3("bz3");

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