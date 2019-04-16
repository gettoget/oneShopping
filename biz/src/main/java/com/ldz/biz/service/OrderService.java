package com.ldz.biz.service;

import com.github.pagehelper.Page;
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

    ApiResponse<String> getPageInfo(Page<Order> page);

    ApiResponse<String> orderCancel(String id);
}