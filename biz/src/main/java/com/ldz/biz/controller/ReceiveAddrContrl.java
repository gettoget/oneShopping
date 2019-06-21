package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.ldz.biz.model.ReceiveAddr;
import com.ldz.biz.service.ReceiveAddrService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receiveaddr")
public class ReceiveAddrContrl extends BaseController<ReceiveAddr,String> {

    @Autowired
    private ReceiveAddrService service;


    @Override
    protected BaseService<ReceiveAddr, String> getBaseService() {
        return service;
    }
}
