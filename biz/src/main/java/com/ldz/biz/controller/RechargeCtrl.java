package com.ldz.biz.controller;

import com.ldz.biz.model.Recharge;
import com.ldz.biz.service.RechargeService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/recharge")
public class RechargeCtrl extends BaseController<Recharge,String> {

    @Autowired
    private RechargeService service;

    @Override
    protected BaseService<Recharge, String> getBaseService() {
        return service;
    }

    /**
     * 用户充值接口
     * @param amount
     * @return
     */
    @PostMapping("/addBalance")
    public ApiResponse<String> recharge(int amount){
        return service.recharge(amount);
    }


}
