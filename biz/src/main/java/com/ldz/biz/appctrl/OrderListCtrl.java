package com.ldz.biz.appctrl;

import com.ldz.biz.model.OrderList;
import com.ldz.biz.service.OrderListService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/orderlist")
public class OrderListCtrl extends BaseController<OrderList,String> {

    @Autowired
    private OrderListService service;

    @Override
    protected BaseService<OrderList, String> getBaseService() {
        return service;
    }

    @Override
    @PostMapping("/save")
    public ApiResponse<String> save(OrderList entity){
        return null;
    }

    /**
     * 数据修改方法
     * 如果对数据要求高，请重写该方法或是不直接继承该类，防止数据泄露
     * @param entity
     * @return
     */
    @Override
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(OrderList entity){
        return null;
    }

    /**
     * 数据删除方法
     * 如果对数据要求高，请重写该方法或是不直接继承该类，防止数据泄露
     * @param id
     * @return
     */
    @Override
    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        return  null;
    }

    /**
     * 批量删除操作
     * @param ids
     * @return
     */
    @Override
    @RequestMapping(value="/removeIds", method={RequestMethod.POST})
    public ApiResponse<String> remove(String[] ids){
        return null;
    }


}
