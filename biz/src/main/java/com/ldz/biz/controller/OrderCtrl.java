package com.ldz.biz.controller;

import com.ldz.biz.model.Order;
import com.ldz.biz.service.OrderService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderCtrl extends BaseController<Order,String> {

    @Autowired
    private OrderService service;

    @Override
    protected BaseService<Order, String> getBaseService() {
        return service;
    }

    /**
     * 新增一条订单接口
     * @param entity
     * @return
     */
    @Override
    @PostMapping("/save")
    public ApiResponse<String> save(Order entity){
        return service.saveEntity(entity);
    }

    /**
     * 支付订单接口
     * @param id 订单id
     * @return
     */
    @PostMapping("/payOrder")
    public ApiResponse<String> payOrder(String id, String payPwd){
        return  service.payOrder(id,payPwd);
    }



}
