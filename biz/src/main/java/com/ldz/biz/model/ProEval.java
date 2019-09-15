package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Table(name = "pro_eval")
public class ProEval implements Serializable {
    @Id
    private String id;

    /**
     * 商品id
     */
    @Column(name = "pro_id")
    private String proId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 评论人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private String cjsj;

    /**
     * 图片url
     */
    private String img;

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

    /**
     * 登录用户是否点赞 0 未点  1 已点
     */
    @Transient
    private String thumbs;
    /**
     * 点赞数
     */
    @Transient
    private int thumbsSum;
    /**
     * 用户头像
     */
    @Transient
    private String himg;

    private static final long serialVersionUID = 1L;

    public String getHimg() {
        return himg;
    }

    public void setHimg(String himg) {
        this.himg = himg;
    }

    public int getThumbsSum() {
        return thumbsSum;
    }

    public void setThumbsSum(int thumbsSum) {
        this.thumbsSum = thumbsSum;
    }

    public String getThumbs() {
        return thumbs;
    }

    public void setThumbs(String thumbs) {
        this.thumbs = thumbs;
    }

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
     * 获取商品id
     *
     * @return pro_id - 商品id
     */
    public String getProId() {
        return proId;
    }

    /**
     * 设置商品id
     *
     * @param proId 商品id
     */
    public void setProId(String proId) {
        this.proId = proId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取评论人姓名
     *
     * @return user_name - 评论人姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置评论人姓名
     *
     * @param userName 评论人姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取评论内容
     *
     * @return content - 评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评论内容
     *
     * @param content 评论内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取评论时间
     *
     * @return cjsj - 评论时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置评论时间
     *
     * @param cjsj 评论时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取图片url
     *
     * @return img - 图片url
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置图片url
     *
     * @param img 图片url
     */
    public void setImg(String img) {
        this.img = img;
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
        proId("pro_id"),
        userId("user_id"),
        userName("user_name"),
        content("content"),
        cjsj("cjsj"),
        img("img"),
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