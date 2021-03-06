package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Table(name = "user")
public class User implements Serializable {
    @Id
    private String id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 支付密码
     */
    @Column(name = "pay_pwd")
    private String payPwd;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 来源。区分Android注册用户和机器人用户   0 人 1 机
     */
    private String source;

    /**
     * 最近登录时间
     */
    @Column(name = "last_time")
    private String lastTime;

    /**
     * 最近登录终端
     */
    @Column(name = "last_imei")
    private String lastImei;

    /**
     * 用户状态。正常、锁定   0 正常  1 锁定
     */
    private String zt;

    /**
     * 注册终端imei
     */
    @Column(name = "reg_imei")
    private String regImei;

    /**
     * 头像地址
     */
    @Column(name = "h_img")
    private String hImg;

    /**
     * 金币余额
     */
    private String balance;

    /**
     * 注册时间
     */
    private String cjsj;

    /**
     * 推荐码
     */
    @Column(name = "ref_code")
    private String refCode;

    /**
     * 积分
     */
    private String score;

    /**
     * 中奖次数
     */
    private String zjcs;

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

    @Column(name = "INVITE_NUMBER")
    private String inviteNumber;

    @Column(name = "INVITE_NUM")
    private Integer inviteNum;

    @Column(name = "INVITED_NUMBER")
    private String invitedNumber;

    /**
     * 充值和消费记录
     */
    @Transient
    private List<Map<String,String>> record;

    @Transient
    private String cz;

    @Transient
    private String xf;

    @Transient
    private String cy;

    @Transient
    private String count;

    @Transient
    private String czcg;

    public String getCzcg() {
        return czcg;
    }

    public void setCzcg(String czcg) {
        this.czcg = czcg;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Integer getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum(Integer inviteNum) {
        this.inviteNum = inviteNum;
    }

    public String getInvitedNumber() {
        return invitedNumber;
    }

    public void setInvitedNumber(String invitedNumber) {
        this.invitedNumber = invitedNumber;
    }

    public String getInviteNumber() {
        return inviteNumber;
    }

    public void setInviteNumber(String inviteNumber) {
        this.inviteNumber = inviteNumber;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }

    public String getXf() {
        return xf;
    }

    public void setXf(String xf) {
        this.xf = xf;
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy;
    }

    private static final long serialVersionUID = 1L;

    public List<Map<String, String>> getRecord() {
        return record;
    }

    public void setRecord(List<Map<String, String>> record) {
        this.record = record;
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
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取密码
     *
     * @return pwd - 密码
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * 设置密码
     *
     * @param pwd 密码
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取来源。区分Android注册用户和机器人用户
     *
     * @return source - 来源。区分Android注册用户和机器人用户
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置来源。区分Android注册用户和机器人用户
     *
     * @param source 来源。区分Android注册用户和机器人用户
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 获取最近登录时间
     *
     * @return last_time - 最近登录时间
     */
    public String getLastTime() {
        return lastTime;
    }

    /**
     * 设置最近登录时间
     *
     * @param lastTime 最近登录时间
     */
    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * 获取最近登录终端
     *
     * @return last_imei - 最近登录终端
     */
    public String getLastImei() {
        return lastImei;
    }

    /**
     * 设置最近登录终端
     *
     * @param lastImei 最近登录终端
     */
    public void setLastImei(String lastImei) {
        this.lastImei = lastImei;
    }

    /**
     * 获取用户状态。正常、锁定
     *
     * @return zt - 用户状态。正常、锁定
     */
    public String getZt() {
        return zt;
    }

    /**
     * 设置用户状态。正常、锁定   0  正常  1 锁定
     *
     * @param zt 用户状态。正常、锁定
     */
    public void setZt(String zt) {
        this.zt = zt;
    }

    /**
     * 获取注册终端imei
     *
     * @return reg_imei - 注册终端imei
     */
    public String getRegImei() {
        return regImei;
    }

    /**
     * 设置注册终端imei
     *
     * @param regImei 注册终端imei
     */
    public void setRegImei(String regImei) {
        this.regImei = regImei;
    }

    /**
     * 获取头像地址
     *
     * @return h_img - 头像地址
     */
    public String gethImg() {
        return hImg;
    }

    /**
     * 设置头像地址
     *
     * @param hImg 头像地址
     */
    public void sethImg(String hImg) {
        this.hImg = hImg;
    }

    /**
     * 获取金币余额
     *
     * @return balance - 金币余额
     */
    public String getBalance() {
        return balance;
    }

    /**
     * 设置金币余额
     *
     * @param balance 金币余额
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }

    /**
     * 获取注册时间
     *
     * @return cjsj - 注册时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置注册时间
     *
     * @param cjsj 注册时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取推荐码
     *
     * @return ref_code - 推荐码
     */
    public String getRefCode() {
        return refCode;
    }

    /**
     * 设置推荐码
     *
     * @param refCode 推荐码
     */
    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    /**
     * 获取积分
     *
     * @return score - 积分
     */
    public String getScore() {
        return score;
    }

    /**
     * 设置积分
     *
     * @param score 积分
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * 获取中奖次数
     *
     * @return zjcs - 中奖次数
     */
    public String getZjcs() {
        return zjcs;
    }

    /**
     * 设置中奖次数
     *
     * @param zjcs 中奖次数
     */
    public void setZjcs(String zjcs) {
        this.zjcs = zjcs;
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
        phone("phone"),
        pwd("pwd"),
        userName("user_name"),
        source("source"),
        lastTime("last_time"),
        lastImei("last_imei"),
        zt("zt"),
        regImei("reg_imei"),
        hImg("h_img"),
        balance("balance"),
        cjsj("cjsj"),
        refCode("ref_code"),
        score("score"),
        zjcs("zjcs"),
        bz1("bz1"),
        bz2("bz2"),
        bz3("bz3"),
        inviteNumber("INVITE_NUMBER"),
        inviteNum("INVITE_NUM"),
        invitedNumber("INVITED_NUMBER");

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