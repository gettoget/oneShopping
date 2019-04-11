package com.ldz.biz.service;

import com.ldz.biz.model.Recharge;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface RechargeService extends BaseService<Recharge, String> {
    /**
     * 充值
     * @param amount
     * @return
     */
    ApiResponse<String> recharge(int amount);
}