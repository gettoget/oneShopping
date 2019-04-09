package com.ldz.biz.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "receive_addr")
public class ReceiveAddr implements Serializable {
    @Id
    private String id;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 收货人姓名
     */
    @Column(name = "rec_name")
    private String recName;

    /**
     * 收货人电话
     */
    @Column(name = "rec_phone")
    private String recPhone;

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
     * 获取收货地址
     *
     * @return address - 收货地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置收货地址
     *
     * @param address 收货地址
     */
    public void setAddress(String address) {
        this.address = address;
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
     * 获取收货人姓名
     *
     * @return rec_name - 收货人姓名
     */
    public String getRecName() {
        return recName;
    }

    /**
     * 设置收货人姓名
     *
     * @param recName 收货人姓名
     */
    public void setRecName(String recName) {
        this.recName = recName;
    }

    /**
     * 获取收货人电话
     *
     * @return rec_phone - 收货人电话
     */
    public String getRecPhone() {
        return recPhone;
    }

    /**
     * 设置收货人电话
     *
     * @param recPhone 收货人电话
     */
    public void setRecPhone(String recPhone) {
        this.recPhone = recPhone;
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
        address("address"),
        userId("user_id"),
        cjsj("cjsj"),
        recName("rec_name"),
        recPhone("rec_phone"),
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