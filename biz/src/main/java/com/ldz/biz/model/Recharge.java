package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Table(name = "recharge")
public class Recharge implements Serializable {
    @Id
    private String id;

    /**
     * 充值人id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 充值状态。待确认、充值成功、充值失败、充值取消  1 待确认 2 成功 3 失败  4 取消
     */
    private String czzt;

    /**
     * 充值金额
     */
    private String amonut;

    /**
     * 充值金币。金币是根据充值金额按一定比例自动兑换
     */
    private String czjb;

    /**
     * 充值时间
     */
    private String cjsj;

    /**
     * 充值渠道。支付通道或活动奖励  1 支付 2 活动
     */
    private String czqd;

    /**
     * 充值订单号
     */
    private String czddh;

    /**
     * 确认时间。支付通道回调确认成功时间
     */
    private String qrsj;

    /**
     * 确认报文
     */
    private String qrbw;

    /**
     * 充值报文
     */
    private String czbw;
    /**
     * 充值终端IMEI
     */
    private String imei;

    /**
     * 充值前金币数
     */
    private String czqjbs;

    /**
     * 充值后金币数
     */
    private String czhjbs;

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

    @Transient
    private String username;

    @Transient
    private String himg;

    public String getCzbw() {
        return czbw;
    }

    public void setCzbw(String czbw) {
        this.czbw = czbw;
    }

    public String getHimg() {
        return himg;
    }

    public void setHimg(String himg) {
        this.himg = himg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
     * 获取充值人id
     *
     * @return user_id - 充值人id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置充值人id
     *
     * @param userId 充值人id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取充值状态。待确认、充值成功、充值失败、充值取消
     *
     * @return czzt - 充值状态。待确认、充值成功、充值失败、充值取消
     */
    public String getCzzt() {
        return czzt;
    }

    /**
     * 设置充值状态。待确认、充值成功、充值失败、充值取消
     *
     * @param czzt 充值状态。待确认、充值成功、充值失败、充值取消
     */
    public void setCzzt(String czzt) {
        this.czzt = czzt;
    }

    /**
     * 获取充值金额
     *
     * @return amonut - 充值金额
     */
    public String getAmonut() {
        return amonut;
    }

    /**
     * 设置充值金额
     *
     * @param amonut 充值金额
     */
    public void setAmonut(String amonut) {
        this.amonut = amonut;
    }

    /**
     * 获取充值金币。金币是根据充值金额按一定比例自动兑换
     *
     * @return czjb - 充值金币。金币是根据充值金额按一定比例自动兑换
     */
    public String getCzjb() {
        return czjb;
    }

    /**
     * 设置充值金币。金币是根据充值金额按一定比例自动兑换
     *
     * @param czjb 充值金币。金币是根据充值金额按一定比例自动兑换
     */
    public void setCzjb(String czjb) {
        this.czjb = czjb;
    }

    /**
     * 获取充值时间
     *
     * @return cjsj - 充值时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置充值时间
     *
     * @param cjsj 充值时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取充值渠道。支付通道或活动奖励
     *
     * @return czqd - 充值渠道。支付通道或活动奖励
     */
    public String getCzqd() {
        return czqd;
    }

    /**
     * 设置充值渠道。支付通道或活动奖励
     *
     * @param czqd 充值渠道。支付通道或活动奖励
     */
    public void setCzqd(String czqd) {
        this.czqd = czqd;
    }

    /**
     * 获取充值订单号
     *
     * @return czddh - 充值订单号
     */
    public String getCzddh() {
        return czddh;
    }

    /**
     * 设置充值订单号
     *
     * @param czddh 充值订单号
     */
    public void setCzddh(String czddh) {
        this.czddh = czddh;
    }

    /**
     * 获取确认时间。支付通道回调确认成功时间
     *
     * @return qrsj - 确认时间。支付通道回调确认成功时间
     */
    public String getQrsj() {
        return qrsj;
    }

    /**
     * 设置确认时间。支付通道回调确认成功时间
     *
     * @param qrsj 确认时间。支付通道回调确认成功时间
     */
    public void setQrsj(String qrsj) {
        this.qrsj = qrsj;
    }

    /**
     * 获取确认报文
     *
     * @return qrbw - 确认报文
     */
    public String getQrbw() {
        return qrbw;
    }

    /**
     * 设置确认报文
     *
     * @param qrbw 确认报文
     */
    public void setQrbw(String qrbw) {
        this.qrbw = qrbw;
    }

    /**
     * 获取充值终端IMEI
     *
     * @return imei - 充值终端IMEI
     */
    public String getImei() {
        return imei;
    }

    /**
     * 设置充值终端IMEI
     *
     * @param imei 充值终端IMEI
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * 获取充值前金币数
     *
     * @return czqjbs - 充值前金币数
     */
    public String getCzqjbs() {
        return czqjbs;
    }

    /**
     * 设置充值前金币数
     *
     * @param czqjbs 充值前金币数
     */
    public void setCzqjbs(String czqjbs) {
        this.czqjbs = czqjbs;
    }

    /**
     * 获取充值后金币数
     *
     * @return czhjbs - 充值后金币数
     */
    public String getCzhjbs() {
        return czhjbs;
    }

    /**
     * 设置充值后金币数
     *
     * @param czhjbs 充值后金币数
     */
    public void setCzhjbs(String czhjbs) {
        this.czhjbs = czhjbs;
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
        czzt("czzt"),
        amonut("amonut"),
        czjb("czjb"),
        cjsj("cjsj"),
        czqd("czqd"),
        czddh("czddh"),
        qrsj("qrsj"),
        qrbw("qrbw"),
        imei("imei"),
        czqjbs("czqjbs"),
        czhjbs("czhjbs"),
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