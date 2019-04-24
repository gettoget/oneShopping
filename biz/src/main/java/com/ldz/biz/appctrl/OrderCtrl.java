package com.ldz.biz.appctrl;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Order;
import com.ldz.biz.service.OrderService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/order")
public class OrderCtrl  {

    @Autowired
    private OrderService service;


    /**
     * 新增一条订单接口
     * @param entity
     * @return
     */
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

    /**
     * 订单查询
     * @return
     */
    @GetMapping("/newPager")
    public PageResponse<Order> pager(Page<Order> page){
        return service.getPageInfo(page);
    }

    /**
     * 订单取消接口
     */
    @PostMapping("/orderCanel")
    public ApiResponse<String> orderCancel(String id){
        return service.updateOrderCancel(id);
    }




}
