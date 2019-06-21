package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.ldz.biz.model.Store;
import com.ldz.biz.service.StoreService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store")
public class StoreContrl extends BaseController<Store,String> {

    @Autowired
    private StoreService service;


    @Override
    protected BaseService<Store, String> getBaseService() {
        return service;
    }
}
