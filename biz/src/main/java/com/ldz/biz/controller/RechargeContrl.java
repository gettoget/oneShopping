package com.ldz.biz.controller;

import com.ldz.biz.model.Recharge;
import com.ldz.biz.service.RechargeService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/recharge")
public class RechargeContrl extends BaseController<Recharge,String> {

    @Autowired
    private RechargeService service;


    @Override
    protected BaseService<Recharge, String> getBaseService() {
        return service;
    }
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(Recharge entity){
        return ApiResponse.success();
    }

    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        return ApiResponse.success();
    }

    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(Recharge entity){
        return ApiResponse.success();
    }

    /**
     * 获取充值两次以上的用户人数 和最后一次累计充值的金额
     */
    @GetMapping("/getTwo")
    public ApiResponse<Map<String,String>>  getMoreThanTwo(){
        return service.getMoreThanTwo();
    }

    /**
     * 根据商品id  补送消费金币超过 5 金币的用户返回Gcoin
     * @param id
     * @return
     */
    @GetMapping("/return")
    public ApiResponse<String> returnGcoin(String id){
        return service.returnGcoin(id);
    }

}
