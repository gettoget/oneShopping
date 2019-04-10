package com.ldz.biz.model;

import lombok.Data;

/**
 *
 * @author slu
 */
@Data
public class PaymentBean {

    /**
     * 充值/消费记录 id
     */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 充值/消费 时间
     */
    private String sj;
    /**
     * 充值/消费金额
     */
    private String amount;
    /**
     * 充值 消费 类型   1 消费  2 充值
     */
    private String type;




}
