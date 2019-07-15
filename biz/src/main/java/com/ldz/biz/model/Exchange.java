package com.ldz.biz.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Table(name = "exchange")
public class Exchange implements Serializable {
    /**
     * 主键ID
     */
    @Id
    private String id;

    /**
     * 消费用户id
     */
    private String userid;

    /**
     * 消费时间
     */
    private String xfsj;

    /**
     * 消费金币
     */
    private String xfjb;

    /**
     * 商品id
     */
    private String proid;

    /**
     * 商品名称
     */
    private String promc;

    /**
     * 消费订单号
     */
    private String xfddh;

    /**
     * 消费前金币数
     */
    private String xfqjbs;

    /**
     * 消费后金币数
     */
    private String xfhjbs;

    /**
     * bz1
     */
    private String bz1;

    /**
     * bz2
     */
    private String bz2;

    /**
     * bz3
     */
    private String bz3;

    @Transient
    private String username;

    @Transient
    private String himg;

    @Transient
    private String time;

    @Transient
    private String je;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getJe() {
        return je;
    }

    public void setJe(String je) {
        this.je = je;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHimg() {
        return himg;
    }

    public void setHimg(String himg) {
        this.himg = himg;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取消费用户id
     *
     * @return userid - 消费用户id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置消费用户id
     *
     * @param userid 消费用户id
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取消费时间
     *
     * @return xfsj - 消费时间
     */
    public String getXfsj() {
        return xfsj;
    }

    /**
     * 设置消费时间
     *
     * @param xfsj 消费时间
     */
    public void setXfsj(String xfsj) {
        this.xfsj = xfsj;
    }

    /**
     * 获取消费金币
     *
     * @return xfjb - 消费金币
     */
    public String getXfjb() {
        return xfjb;
    }

    /**
     * 设置消费金币
     *
     * @param xfjb 消费金币
     */
    public void setXfjb(String xfjb) {
        this.xfjb = xfjb;
    }

    /**
     * 获取商品id
     *
     * @return proid - 商品id
     */
    public String getProid() {
        return proid;
    }

    /**
     * 设置商品id
     *
     * @param proid 商品id
     */
    public void setProid(String proid) {
        this.proid = proid;
    }

    /**
     * 获取商品名称
     *
     * @return promc - 商品名称
     */
    public String getPromc() {
        return promc;
    }

    /**
     * 设置商品名称
     *
     * @param promc 商品名称
     */
    public void setPromc(String promc) {
        this.promc = promc;
    }

    /**
     * 获取消费订单号
     *
     * @return xfddh - 消费订单号
     */
    public String getXfddh() {
        return xfddh;
    }

    /**
     * 设置消费订单号
     *
     * @param xfddh 消费订单号
     */
    public void setXfddh(String xfddh) {
        this.xfddh = xfddh;
    }

    /**
     * 获取消费前金币数
     *
     * @return xfqjbs - 消费前金币数
     */
    public String getXfqjbs() {
        return xfqjbs;
    }

    /**
     * 设置消费前金币数
     *
     * @param xfqjbs 消费前金币数
     */
    public void setXfqjbs(String xfqjbs) {
        this.xfqjbs = xfqjbs;
    }

    /**
     * 获取消费后金币数
     *
     * @return xfhjbs - 消费后金币数
     */
    public String getXfhjbs() {
        return xfhjbs;
    }

    /**
     * 设置消费后金币数
     *
     * @param xfhjbs 消费后金币数
     */
    public void setXfhjbs(String xfhjbs) {
        this.xfhjbs = xfhjbs;
    }

    /**
     * 获取bz1
     *
     * @return bz1 - bz1
     */
    public String getBz1() {
        return bz1;
    }

    /**
     * 设置bz1
     *
     * @param bz1 bz1
     */
    public void setBz1(String bz1) {
        this.bz1 = bz1;
    }

    /**
     * 获取bz2
     *
     * @return bz2 - bz2
     */
    public String getBz2() {
        return bz2;
    }

    /**
     * 设置bz2
     *
     * @param bz2 bz2
     */
    public void setBz2(String bz2) {
        this.bz2 = bz2;
    }

    /**
     * 获取bz3
     *
     * @return bz3 - bz3
     */
    public String getBz3() {
        return bz3;
    }

    /**
     * 设置bz3
     *
     * @param bz3 bz3
     */
    public void setBz3(String bz3) {
        this.bz3 = bz3;
    }

    public enum InnerColumn {
        id("id"),
        userid("userid"),
        xfsj("xfsj"),
        xfjb("xfjb"),
        proid("proid"),
        promc("promc"),
        xfddh("xfddh"),
        xfqjbs("xfqjbs"),
        xfhjbs("xfhjbs"),
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