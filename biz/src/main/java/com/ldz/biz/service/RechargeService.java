package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Recharge;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;

public interface RechargeService extends BaseService<Recharge, String> {
    /**
     * 充值
     * @param amount
     * @return
     */
    ApiResponse<String> saveRecharge(int amount);

    PageResponse<Recharge> getNewPager(Page<Recharge> page);
}