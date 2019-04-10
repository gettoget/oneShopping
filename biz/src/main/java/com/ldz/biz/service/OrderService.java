package com.ldz.biz.service;

import com.ldz.biz.model.Order;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface OrderService extends BaseService<Order, String> {

    ApiResponse<String> saveEntity(Order entity);

    ApiResponse<String> payOrder(String id);
}