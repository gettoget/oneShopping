package com.ldz.biz.appctrl;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Order;
import com.ldz.biz.service.OrderService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/user/order")
public class OrderCtrl  {

    @Autowired
    private OrderService service;


    /**
     * 新增一条订单接口 创建订单后直接支付
     * @param entity
     * @return
     */
    @PostMapping("/save")
    public ApiResponse<String> save(Order entity){
        return service.saveEntity(entity);
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

    @GetMapping("/getMyOrder")
    public PageResponse<Order> getMyOrder(@RequestParam(defaultValue = "1")int pageNum, @RequestParam(defaultValue = "8")int pageSize){
        return service.getMyOrder(pageNum,pageSize);
    }




}
