package com.ldz.biz.model;

import com.ldz.util.commonUtil.DateUtils;

import java.io.Serializable;
import javax.jws.soap.SOAPBinding;
import javax.persistence.*;

@Table(name = "order_list")
public class OrderList implements Serializable {
    @Id
    private String id;

    /**
     * 订单Id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 商品id
     */
    @Column(name = "pro_id")
    private String proId;

    /**
     * 商品名称
     */
    @Column(name = "pro_name")
    private String proName;

    /**
     * 购买人id
     */
    private String userid;

    /**
     * 购买人姓名
     */
    @Column(name = "user_name")
    private String userName;


    /**
     * 用户类型。注册用户、机器人
     */
    private String yhlx;

    /**
     * 号码
     */
    private String num;

    /**
     * 购买时间
     */
    private String cjsj;

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
     * 获取订单Id
     *
     * @return order_id - 订单Id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单Id
     *
     * @param orderId 订单Id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
     * 获取购买人id
     *
     * @return userid - 购买人id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置购买人id
     *
     * @param userid 购买人id
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取购买人姓名
     *
     * @return user_name - 购买人姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置购买人姓名
     *
     * @param userName 购买人姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户类型。注册用户、机器人
     *
     * @return yhlx - 用户类型。注册用户、机器人
     */
    public String getYhlx() {
        return yhlx;
    }

    /**
     * 设置用户类型。注册用户、机器人
     *
     * @param yhlx 用户类型。注册用户、机器人
     */
    public void setYhlx(String yhlx) {
        this.yhlx = yhlx;
    }

    /**
     * 获取号码
     *
     * @return num - 号码
     */
    public String getNum() {
        return num;
    }

    /**
     * 设置号码
     *
     * @param num 号码
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * 获取购买时间
     *
     * @return cjsj - 购买时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置购买时间
     *
     * @param cjsj 购买时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
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


    public OrderList() {
    }

    public OrderList(Order order, String num, User user){
        this.orderId = order.getId();
        this.proId = order.getProId();
        this.proName = order.getProName();
        this.userid = order.getUserId();
        this.num = num;
        this.userName = user.getUserName();
        this.yhlx = user.getScore();
        this.cjsj = DateUtils.getNowTime();


    }

    public enum InnerColumn {
        id("id"),
        orderId("order_id"),
        proId("pro_id"),
        proName("pro_name"),
        userid("userid"),
        userName("user_name"),
        yhlx("yhlx"),
        num("num"),
        cjsj("cjsj"),
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