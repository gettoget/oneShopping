package com.ldz.biz.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
@Data
public class UserModel implements Serializable {
    private String id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户名
     */
    private String userName;

    private String token;

    /**
     * 头像地址
     */
    private String hImg;

    /**
     * 金币余额
     */
    private String balance;

    /**
     * 推荐码
     */
    private String refCode;

    /**
     * 积分
     */
    private String score;

    /**
     * 充值和消费记录
     */
    private List<Map<String,String>> record;

    private String  inviteNumber;

    private String inviteNum;

    private String inviteCoin;

    public UserModel(User user) {
        this.id = user.getId();
        this.phone = user.getPhone();
        this.userName = user.getUserName();
        this.hImg = user.gethImg();
        this.balance = user.getBalance();
        this.refCode = user.getRefCode();
        this.score = user.getScore();
        this.inviteNum = user.getInviteNum()+"";
        this.inviteNumber = user.getInviteNumber();
    }

    public UserModel() {
    }
}
