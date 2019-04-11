package com.ldz.biz.service;

import com.ldz.biz.model.Order;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

/**
 * @author slu
 */
public interface OrderService extends BaseService<Order, String> {

    /**
     * 新增订单
     * @param entity
     * @return
     */
    ApiResponse<String> saveEntity(Order entity);

    /**
     * 订单支付
     * @param id
     * @return
     */
    ApiResponse<String> payOrder(String id, String payPwd);
}