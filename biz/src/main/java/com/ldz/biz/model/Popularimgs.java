package com.ldz.biz.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "popularimgs")
public class Popularimgs implements Serializable {
    @Id
    private String id;

    /**
     * 商品id
     */
    @Column(name = "pro_id")
    private String proId;

    /**
     * 图片url
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 轮播图类型。商品推荐、活动页面  1 商品推荐  2 活动页面
     */
    @Column(name = "img_lx")
    private String imgLx;

    /**
     * 访问url
     */
    private String fwurl;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 状态。有效、无效 0 有效  1 无效
     */
    private String zt;

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
     * 获取图片url
     *
     * @return img_url - 图片url
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置图片url
     *
     * @param imgUrl 图片url
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 获取轮播图类型。商品推荐、活动页面
     *
     * @return img_lx - 轮播图类型。商品推荐、活动页面
     */
    public String getImgLx() {
        return imgLx;
    }

    /**
     * 设置轮播图类型。商品推荐、活动页面
     *
     * @param imgLx 轮播图类型。商品推荐、活动页面
     */
    public void setImgLx(String imgLx) {
        this.imgLx = imgLx;
    }

    /**
     * 获取访问url
     *
     * @return fwurl - 访问url
     */
    public String getFwurl() {
        return fwurl;
    }

    /**
     * 设置访问url
     *
     * @param fwurl 访问url
     */
    public void setFwurl(String fwurl) {
        this.fwurl = fwurl;
    }

    /**
     * 获取创建时间
     *
     * @return cjsj - 创建时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置创建时间
     *
     * @param cjsj 创建时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取状态。有效、无效
     *
     * @return zt - 状态。有效、无效
     */
    public String getZt() {
        return zt;
    }

    /**
     * 设置状态。有效、无效
     *
     * @param zt 状态。有效、无效
     */
    public void setZt(String zt) {
        this.zt = zt;
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
        imgUrl("img_url"),
        imgLx("img_lx"),
        fwurl("fwurl"),
        cjsj("cjsj"),
        zt("zt"),
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