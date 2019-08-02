package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Recharge;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;

import java.util.List;
import java.util.Map;

public interface RechargeService extends BaseService<Recharge, String> {
    /**
     * 充值
     * @param amount
     * @return
     */
    ApiResponse<String> saveRecharge(int amount);

    PageResponse<Recharge> getNewPager(Page<Recharge> page);

    ApiResponse<String> paySuc(String amount, String trans_id, String words,String data);

    ApiResponse<List<Map>> getPaymentChannel();

    ApiResponse<String> checkPayment(String id);

    ApiResponse<String> paySucTest(String id);
}