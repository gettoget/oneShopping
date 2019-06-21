package com.ldz.biz.controller;

import com.ldz.biz.model.OrderList;
import com.ldz.biz.service.OrderListService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/orderlist")
public class OrderListContrl extends BaseController<OrderList,String> {

    @Autowired
    private OrderListService service;


    @Override
    protected BaseService<OrderList, String> getBaseService() {
        return service;
    }
}
