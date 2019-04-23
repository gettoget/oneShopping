package com.ldz.biz.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "win_record")
public class WinRecord implements Serializable {
    @Id
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 中奖人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 投注份数。中奖人一共买了多少份
     */
    private String zjfs;

    /**
     * 商品id
     */
    @Column(name = "pro_id")
    private String proId;

    /**
     * 中奖时间
     */
    private String cjsj;

    /**
     * 中奖号码
     */
    private String num;

    /**
     * 中间类型。用户中奖、机器人中奖
     */
    private String zjlx;

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
     * 商品名称
     */
    @Transient
    private String proName;

    private static final long serialVersionUID = 1L;

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
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
     * 获取中奖人姓名
     *
     * @return user_name - 中奖人姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置中奖人姓名
     *
     * @param userName 中奖人姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取投注份数。中奖人一共买了多少份
     *
     * @return zjfs - 投注份数。中奖人一共买了多少份
     */
    public String getZjfs() {
        return zjfs;
    }

    /**
     * 设置投注份数。中奖人一共买了多少份
     *
     * @param zjfs 投注份数。中奖人一共买了多少份
     */
    public void setZjfs(String zjfs) {
        this.zjfs = zjfs;
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
     * 获取中奖时间
     *
     * @return cjsj - 中奖时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置中奖时间
     *
     * @param cjsj 中奖时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取中奖号码
     *
     * @return num - 中奖号码
     */
    public String getNum() {
        return num;
    }

    /**
     * 设置中奖号码
     *
     * @param num 中奖号码
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * 获取中间类型。用户中奖、机器人中奖
     *
     * @return zjlx - 中间类型。用户中奖、机器人中奖
     */
    public String getZjlx() {
        return zjlx;
    }

    /**
     * 设置中间类型。用户中奖、机器人中奖
     *
     * @param zjlx 中间类型。用户中奖、机器人中奖
     */
    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
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
        userName("user_name"),
        zjfs("zjfs"),
        proId("pro_id"),
        cjsj("cjsj"),
        num("num"),
        zjlx("zjlx"),
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