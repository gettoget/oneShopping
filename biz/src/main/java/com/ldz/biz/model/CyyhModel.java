package com.ldz.biz.model;


import lombok.Data;

@Data
/**
 * 参与用户模型
 */
public class CyyhModel {
    /**\
     * 用户Id
     */
    private String  userId;
    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String himg;

    /**
     * 用户购买份数
     */
    private String gmfs;

    /**
     * 用户购买时间
     */
    private String gmsj;

}
