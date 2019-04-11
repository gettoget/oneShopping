package com.ldz.biz.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Table(name = "order_form")
public class Order implements Serializable {
    /**
     * 订单编号
     */
    @Id
    private String id;

    /**
     * 订单类型( 直接购买/ 参与抽奖)  1 直接购买  2 参与抽奖
     */
    @Column(name = "order_type")
    private String orderType;

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
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 购买份数
     */
    private String gmfs;

    /**
     * 支付金额
     */
    private String zfje;

    /**
     * 支付时间
     */
    private String cjsj;

    /**
     * IMEI
     */
    private String imei;

    /**
     * 订单状态。待开奖、已中奖、未中奖  待支付   0 待开奖 1 已中奖 2 未中奖 3 待支付 4 已支付 5 取消支付
     */
    private String ddzt;

    /**
     * 中奖号码
     */
    private String zjhm;

    /**
     * 备注一
     */
    private String bz1;

    /**
     * 备注二
     */
    private String bz2;

    /**
     * 备注三
     */
    private String bz3;

    /**
     * 抽奖订单详情
     */
    @Transient
    private List<OrderList> orderLists;

    @Transient
    private String userName;

    private static final long serialVersionUID = 1L;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<OrderList> getOrderLists() {
        return orderLists;
    }

    public void setOrderLists(List<OrderList> orderLists) {
        this.orderLists = orderLists;
    }

    /**
     * 获取订单编号
     *
     * @return id - 订单编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置订单编号
     *
     * @param id 订单编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取订单类型( 直接购买/ 参与抽奖)
     *
     * @return order_type - 订单类型( 直接购买/ 参与抽奖)
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * 设置订单类型( 直接购买/ 参与抽奖)
     *
     * @param orderType 订单类型( 直接购买/ 参与抽奖)
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
     * 获取购买份数
     *
     * @return gmfs - 购买份数
     */
    public String getGmfs() {
        return gmfs;
    }

    /**
     * 设置购买份数
     *
     * @param gmfs 购买份数
     */
    public void setGmfs(String gmfs) {
        this.gmfs = gmfs;
    }

    /**
     * 获取支付金额
     *
     * @return zfje - 支付金额
     */
    public String getZfje() {
        return zfje;
    }

    /**
     * 设置支付金额
     *
     * @param zfje 支付金额
     */
    public void setZfje(String zfje) {
        this.zfje = zfje;
    }

    /**
     * 获取支付时间
     *
     * @return cjsj - 支付时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置支付时间
     *
     * @param cjsj 支付时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取IMEI
     *
     * @return imei - IMEI
     */
    public String getImei() {
        return imei;
    }

    /**
     * 设置IMEI
     *
     * @param imei IMEI
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * 获取订单状态。待开奖、已中奖、未中奖
     *
     * @return ddzt - 订单状态。待开奖、已中奖、未中奖
     */
    public String getDdzt() {
        return ddzt;
    }

    /**
     * 设置订单状态。待开奖、已中奖、未中奖
     *
     * @param ddzt 订单状态。待开奖、已中奖、未中奖
     */
    public void setDdzt(String ddzt) {
        this.ddzt = ddzt;
    }

    /**
     * 获取中奖号码
     *
     * @return zjhm - 中奖号码
     */
    public String getZjhm() {
        return zjhm;
    }

    /**
     * 设置中奖号码
     *
     * @param zjhm 中奖号码
     */
    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
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
     * 获取备注二
     *
     * @return bz2 - 备注二
     */
    public String getBz2() {
        return bz2;
    }

    /**
     * 设置备注二
     *
     * @param bz2 备注二
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
        orderType("order_type"),
        proId("pro_id"),
        proName("pro_name"),
        userId("user_id"),
        gmfs("gmfs"),
        zfje("zfje"),
        cjsj("cjsj"),
        imei("imei"),
        ddzt("ddzt"),
        zjhm("zjhm"),
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