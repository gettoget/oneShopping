package com.ldz.biz.controller;

import com.ldz.biz.model.Recharge;
import com.ldz.biz.service.RechargeService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recharge")
public class RechargeCtrl extends BaseController<Recharge,String> {

    @Autowired
    private RechargeService service;

    @Override
    protected BaseService<Recharge, String> getBaseService() {
        return service;
    }
}
