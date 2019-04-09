package com.ldz.biz.controller;

import com.ldz.biz.model.Order;
import com.ldz.biz.service.OrderService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
