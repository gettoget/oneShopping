package com.ldz.biz.appctrl;

import com.ldz.biz.model.OrderList;
import com.ldz.biz.service.OrderListService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/user/orderlist")
public class OrderListCtrl  {

    @Autowired
    private OrderListService service;




}
