package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Recharge;
import com.ldz.biz.service.RechargeService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
