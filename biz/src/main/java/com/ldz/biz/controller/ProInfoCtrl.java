package com.ldz.biz.controller;

import com.ldz.biz.model.ProInfo;
import com.ldz.biz.service.ProInfoService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proinfo")
public class ProInfoCtrl extends BaseController<ProInfo, String> {

    @Autowired
    private ProInfoService service;

    @Override
    protected BaseService<ProInfo, String> getBaseService() {
        return service;
    }
}
