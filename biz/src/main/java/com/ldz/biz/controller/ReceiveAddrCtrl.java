package com.ldz.biz.controller;

import com.ldz.biz.model.ReceiveAddr;
import com.ldz.biz.service.ReceiveAddrService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receiveaddr")
public class ReceiveAddrCtrl extends BaseController<ReceiveAddr, String> {

    @Autowired
    private ReceiveAddrService service;

    @Override
    protected BaseService<ReceiveAddr, String> getBaseService() {
        return service;
    }
}
