package com.ldz.biz.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "store")
public class Store implements Serializable {
    @Id
    private String id;

    /**
     * 商品id
     */
    @Column(name = "pro_id")
    private String proId;

    /**
     * 收藏人id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 收藏时间
     */
    private String cjsj;

    /**
     * 商品名称
     */
    @Column(name = "pro_name")
    private String proName;

    @Transient
    private ProInfo proInfo;




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


    public ProInfo getProInfo() {
        return proInfo;
    }

    public void setProInfo(ProInfo proInfo) {
        this.proInfo = proInfo;
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
     * 获取收藏人id
     *
     * @return user_id - 收藏人id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置收藏人id
     *
     * @param userId 收藏人id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取收藏时间
     *
     * @return cjsj - 收藏时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置收藏时间
     *
     * @param cjsj 收藏时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取商品名称
     *
     * @return pro_name - 商品名称
     */
    public String getProName() {
        return proName;
    }

    /**
     * 设置商品名称
     *
     * @param proName 商品名称
     */
    public void setProName(String proName) {
        this.proName = proName;
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
        cjsj("cjsj"),
        proName("pro_name"),
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