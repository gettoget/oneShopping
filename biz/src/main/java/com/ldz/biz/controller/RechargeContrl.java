package com.ldz.biz.controller;

import com.ldz.biz.model.Recharge;
import com.ldz.biz.service.RechargeService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


}
