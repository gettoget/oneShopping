package com.ldz.util.bean;


import java.io.Serializable;

/**
 *  百度推送对象
 */
public class AndroidMsgBean implements Serializable {
    /**
     * 透传消息类型    1  商品上新  2 商品变更热门  3 商品待开奖 4 商品已开奖
     */
    private String type;
    /**
     *   json 数据    ProInfo 对象
     */
    private String json;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
