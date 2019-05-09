package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Order;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;

import java.util.List;

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
     * 分页
     * @param page
     * @return
     */
    PageResponse<Order> getPageInfo(Page<Order> page);

    /**
     * 取消订单
     * @param id
     * @return
     */
    ApiResponse<String> updateOrderCancel(String id);

    /**
     * 开奖
     * @param id
     */
    void fenpei(String id);

    /**
     * save list
     * @param orders
     */
    void saveList(List<Order>orders);
}